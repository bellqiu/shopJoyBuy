package com.spshop.admin.client;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.spshop.admin.client.businessui.CategoryManager;
import com.spshop.admin.client.businessui.ComponentQuery;
import com.spshop.admin.client.businessui.DashboardSellingManager;
import com.spshop.admin.client.businessui.DeliveryManager;
import com.spshop.admin.client.businessui.HTMLCreation;
import com.spshop.admin.client.businessui.ImageBatchCreation;
import com.spshop.admin.client.businessui.ImageCreation;
import com.spshop.admin.client.businessui.PostMessage;
import com.spshop.admin.client.businessui.ProductCreation;
import com.spshop.admin.client.businessui.SiteManager;
import com.spshop.admin.client.businessui.TopSellingManager;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.admin.client.businessui.callback.SelectedCallBack;
import com.spshop.model.Country;
import com.spshop.model.Image;
import com.spshop.model.Message;
import com.spshop.model.Order;
import com.spshop.model.OrderItem;
import com.spshop.model.Product;
import com.spshop.model.Site;
import com.spshop.model.TabProduct;
import com.spshop.model.TabSelling;
import com.spshop.model.User;
import com.spshop.model.UserOption;
import com.spshop.model.enums.ImageSizeType;
import com.spshop.model.enums.ImageType;

public class CommandFactory {
	
	private static PopWindow loading ;
	private static PopWindow error ;
	
	public static Command createProduct(){
		return new CommandAdapter() {
			@Override
			public void execute() {
				AdminWorkspace.contentPanel.body.clear();
				ProductCreation creation = new ProductCreation(new Product());
				AdminWorkspace.contentPanel.body.add(creation);
			}
		};
	}
	
	public static Command createImage(){
		return new CommandAdapter() {
			@Override
			public void execute() {
				AdminWorkspace.contentPanel.body.clear();
				Image image =new Image();
				image.setSizeType(ImageSizeType.PRODUCT_NORMAL);
				image.setType(ImageType.INTERNAL);
				AdminWorkspace.contentPanel.body.add(new ImageCreation(image));
			}
		};
	}
	
	public static Command createBatchImage(){
		return new CommandAdapter() {
			@Override
			public void execute() {
				AdminWorkspace.contentPanel.body.clear();
				AdminWorkspace.contentPanel.body.add(new ImageBatchCreation());
			}
		};
	}
	
	public static Command categoryManager(){
		return new CommandAdapter() {
			@Override
			public void execute() {
				AdminWorkspace.contentPanel.body.clear();
				AdminWorkspace.contentPanel.body.add(new CategoryManager());
			}
		};
	}
	
	public static Command emptyCommand(){
		return new CommandAdapter() {
			@Override
			public boolean isEmpty() {
				return true;
			}
		};
	}

	public static Command queryImage() {
		return new CommandAdapter() {
			@Override
			public void execute() {
				AdminWorkspace.contentPanel.body.clear();
				ComponentQuery componentQuery = new ComponentQuery("Image Query",Image.class);
				componentQuery.getQueryCondition().setAsc(false);
				componentQuery.getQueryCondition().setOrderBy("createDate");
				AdminWorkspace.contentPanel.body.add(componentQuery);
			}
		};
	}
	
	public static Command popUpShowOrder(final boolean multiSelect, final User user, final SelectedCallBack callBack) {
	    return new CommandAdapter(){
	        @Override
	        public void execute() {
                final ComponentQuery componentQuery = new ComponentQuery("My Order", Order.class);
                String hql = "select o from Order as o join o.user as ou where ou.id = " + user.getId() + " and o.status <> 'ONSHOPPING' order by o.id desc";
                componentQuery.getQueryCondition().setAsc(false);
                componentQuery.setEnableMultiSelect(multiSelect);
                componentQuery.getQueryCondition().setOrderBy("createDate");
                componentQuery.getQueryCondition().setHql(hql);
                HTMLPanel content = new HTMLPanel("<div></div>");
                content.setSize("960px", "500px");
                content.clear();
                content.add(componentQuery);
                
                final PopWindow popWindow = new PopWindow("My Order", content, true, true);
                popWindow.center();
	        }
	    };
	}
	
	public static Command popUpShowDetails(final boolean multiSelect, final OrderItem item) {
        return new CommandAdapter(){
            @Override
            public void execute() {
                HTMLPanel content = new HTMLPanel("<div>"+populateDetailsHtml()+"</div>");
                content.setSize("800px", "500px");
                content.clear();
                
                final PopWindow popWindow = new PopWindow("Order Item Details", content, true, true);
                popWindow.center();
            }
            
            private String populateDetailsHtml(){
                Site site = AdminWorkspace.loginInfo.getSite();
                StringBuilder html = new StringBuilder("<a target='blank' href='"+ "http://" + site.getDomain() + "/" + item.getProduct().getName() + "'><h2>" + item.getProduct().getTitle() + "</h2></a>");
                html.append("<h3>Color:</h3>");
                html.append(getOption("Color"));
                html.append("<br><h3>Size:</h3>");
                String size = getOption("Size");
                if ("Customized".equals(size)) {
                    html.append(getOption("Customized Size"));
                } else {
                    html.append(size);
                }
                
                return html.toString();
            }
            
            private String getOption(String optionName) {
                String value = "";
                List<UserOption> userOptions = item.getUserOptions();
                if (userOptions != null) {
                    for (UserOption userOption : userOptions) {
                        if (optionName.equals(userOption.getOptionName())) {
                            value = userOption.getValue();
                            break;
                        }
                    }
                }
                return value;
            }
        };
    }
	
	public static Command popUpImageQuery(final boolean multiSelect,final SelectedCallBack callBack) {
		return new CommandAdapter() {
			@Override
			public void execute() {
				final ComponentQuery componentQuery = new ComponentQuery("Image Query",Image.class);
				componentQuery.getQueryCondition().setAsc(false);
				componentQuery.setEnableMultiSelect(multiSelect);
				componentQuery.getQueryCondition().setOrderBy("createDate");
				HTMLPanel content = new HTMLPanel("<div></div>");
				content.setSize("850px", "500px");
				content.clear();
				content.add(componentQuery);
				Button button = new Button("Select");
				
				final PopWindow popWindow = new PopWindow("Query Image", content, true, true);
				button.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent e) {
						callBack.callBack(componentQuery.getSelected());
						popWindow.hide();
					}
				});
				popWindow.addButton(button);
				popWindow.center();
				
			}
		};
	}
	
	public static Command popUpQueryProduct(final boolean multiSelect,final SelectedCallBack callBack) {
		return new CommandAdapter() {
			@Override
			public void execute() {
				final ComponentQuery componentQuery = new ComponentQuery("Product Query",Product.class);
				componentQuery.getQueryCondition().setAsc(false);
				componentQuery.setEnableMultiSelect(multiSelect);
				componentQuery.getQueryCondition().setOrderBy("createDate");
				HTMLPanel content = new HTMLPanel("<div></div>");
				content.setSize("850px", "500px");
				content.clear();
				content.add(componentQuery);
				Button button = new Button("Select");
				
				final PopWindow popWindow = new PopWindow("Product Query", content, true, true);
				button.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent e) {
						callBack.callBack(componentQuery.getSelected());
						popWindow.hide();
					}
				});
				popWindow.addButton(button);
				popWindow.center();
				
			}
		};
	}

	public static Command queryProduct() {
		return new CommandAdapter() {
			@Override
			public void execute() {
				AdminWorkspace.contentPanel.body.clear();
				ComponentQuery componentQuery = new ComponentQuery("Product Query",Product.class);
				componentQuery.getQueryCondition().setAsc(false);
				componentQuery.getQueryCondition().setOrderBy("createDate");
				AdminWorkspace.contentPanel.body.add(componentQuery);
			}
		};
	}
	
	
	
	private static void loading(String title){
		if(null!=loading){
			loading.hide();
		}
		loading=PopWindow.createLoading(title);
		loading.setHTML("Processing...");
		loading.center();
	}
	
	private static void error(String title,String msg){
		unloading();
		if(null!=error){
			error.hide();
		}
		error=new PopWindow(title, new HTML(msg), true, true);
		error.center();
	}
	
	private static void unloading(){
		if(null!=loading){
			loading.hide();
		}
	}
	
	public static Command lock(final String title) {
		return new CommandAdapter() {
			@Override
			public void execute() {
				loading(title);
			}
			
		};
	}
	
	public static Command onError(final String title,final String msg) {
		return new CommandAdapter() {
			@Override
			public void execute() {
				error(title,msg);
			}
			
		};
	}
	
	public static Command release() {
		return new CommandAdapter() {
			@Override
			public void execute() {
				unloading();
			}
			
		};
	}

	public static Command siteManager() {
		return new CommandAdapter() {
			@Override
			public void execute() {
				AdminWorkspace.contentPanel.body.clear();
				SiteManager siteManager = new SiteManager();
				AdminWorkspace.contentPanel.body.add(siteManager);
			}
		};
	}
	
	public static Command topSelingManager() {
		return new CommandAdapter() {
			@Override
			public void execute() {
				final PopWindow popWindow = PopWindow.createLoading("Loading").lock();
				AdminWorkspace.contentPanel.body.clear();
				final TopSellingManager topSellingManager = new TopSellingManager();
				topSellingManager.setShowName(false);
				CommandFactory.lock("Process").execute();
				AdminWorkspace.ADMIN_SERVICE_ASYNC.getTopSelling(new AsyncCallbackAdapter<TabProduct>(){
					@Override
					public void onSuccess(TabProduct rs) {
						if(null==rs.getCreateDate()){
							rs.setCreateDate(new Date());
						}
						topSellingManager.setComponent(rs);
						popWindow.hide();
						RootPanel.get().remove(popWindow);
					}
				});
				AdminWorkspace.contentPanel.body.add(topSellingManager);
			}
		};
	}
	
	public static Command dashboardSellingManager() {
		return new CommandAdapter() {
			@Override
			public void execute() {
				final PopWindow popWindow = PopWindow.createLoading("Loading").lock();
				AdminWorkspace.contentPanel.body.clear();
				final DashboardSellingManager dashboardSellingManager = new DashboardSellingManager();
				AdminWorkspace.contentPanel.body.add(dashboardSellingManager);
				AdminWorkspace.ADMIN_SERVICE_ASYNC.getDefaulTabSelling(new AsyncCallbackAdapter<TabSelling>(){
					@Override
					public void onSuccess(TabSelling rs) {
						dashboardSellingManager.setComponent(rs);
						popWindow.hide();
						RootPanel.get().remove(popWindow);
					}
				});
				
			}
		};
	}

	public static Command createHTML() {
		return new CommandAdapter() {
			@Override
			public void execute() {
				final PopWindow popWindow = PopWindow.createLoading("Loading").lock();
				AdminWorkspace.contentPanel.body.clear();
				final HTMLCreation htmlCreation = new HTMLCreation();
				com.spshop.model.HTML html = new com.spshop.model.HTML();
				html.setCreateDate(new Date());
				htmlCreation.setComponent(html);
				AdminWorkspace.contentPanel.body.add(htmlCreation);
				popWindow.hide();
				RootPanel.get().remove(popWindow);
			}
		};
	}

	public static Command queryHTML() {
		return new CommandAdapter() {
			@Override
			public void execute() {
				AdminWorkspace.contentPanel.body.clear();
				ComponentQuery componentQuery = new ComponentQuery("HTML Query",com.spshop.model.HTML.class);
				componentQuery.getQueryCondition().setAsc(false);
				componentQuery.getQueryCondition().setOrderBy("createDate");
				AdminWorkspace.contentPanel.body.add(componentQuery);
			}
		};
	}

    public static Command queryUser() {
        return new CommandAdapter() {
            @Override
            public void execute() {
                AdminWorkspace.contentPanel.body.clear();
                ComponentQuery componentQuery = new ComponentQuery("User Query", User.class);
                componentQuery.getQueryCondition().setAsc(false);
                componentQuery.getQueryCondition().setOrderBy("createDate");
                AdminWorkspace.contentPanel.body.add(componentQuery);
            }
        };
    }

	public static Command createRelatedProduct() {
		return new CommandAdapter() {
			@Override
			public void execute() {
				final PopWindow popWindow = PopWindow.createLoading("Loading").lock();
				AdminWorkspace.contentPanel.body.clear();
				final TopSellingManager topSellingManager = new TopSellingManager();
				topSellingManager.setShowName(true);
				topSellingManager.setShowButton(true);
				TabProduct tp = new TabProduct();
				tp.setName("");
				tp.setCreateDate(new Date());
				topSellingManager.setComponent(tp);
				AdminWorkspace.contentPanel.body.add(topSellingManager);
				popWindow.hide();
				RootPanel.get().remove(popWindow);
			}
		};
	}

	public static Command queryRelatedProduct() {
		  return new CommandAdapter() {
	            @Override
	            public void execute() {
	                AdminWorkspace.contentPanel.body.clear();
	                ComponentQuery componentQuery = new ComponentQuery("Query Related Product", TabProduct.class);
	                componentQuery.getQueryCondition().setAsc(false);
	                componentQuery.getQueryCondition().setOrderBy("createDate");
	                AdminWorkspace.contentPanel.body.add(componentQuery);
	            }
	        };
	}
	
	
	public static Command popUpTabProductQuery(final boolean multiSelect,final SelectedCallBack callBack) {
		return new CommandAdapter() {
			@Override
			public void execute() {
				final ComponentQuery componentQuery = new ComponentQuery("Related Product Query",TabProduct.class);
				componentQuery.getQueryCondition().setAsc(false);
				componentQuery.setEnableMultiSelect(multiSelect);
				componentQuery.getQueryCondition().setOrderBy("createDate");
				HTMLPanel content = new HTMLPanel("<div></div>");
				content.setSize("900px", "500px");
				content.clear();
				content.add(componentQuery);
				Button button = new Button("Select");
				
				final PopWindow popWindow = new PopWindow("Related Product Query", content, true, true);
				button.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent e) {
						callBack.callBack(componentQuery.getSelected());
						popWindow.hide();
					}
				});
				popWindow.addButton(button);
				popWindow.center();
				
			}
		};
	}
	
	public static Command popUpHTMLQuery(final boolean multiSelect,final SelectedCallBack callBack) {
		return new CommandAdapter() {
			@Override
			public void execute() {
				final ComponentQuery componentQuery = new ComponentQuery("Related Product Query",com.spshop.model.HTML.class);
				componentQuery.getQueryCondition().setAsc(false);
				componentQuery.setEnableMultiSelect(multiSelect);
				componentQuery.getQueryCondition().setOrderBy("createDate");
				HTMLPanel content = new HTMLPanel("<div></div>");
				content.setSize("900px", "500px");
				content.clear();
				content.add(componentQuery);
				Button button = new Button("Select");
				
				final PopWindow popWindow = new PopWindow("Query HTML", content, true, true);
				button.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent e) {
						callBack.callBack(componentQuery.getSelected());
						popWindow.hide();
					}
				});
				popWindow.addButton(button);
				popWindow.center();
			}
		};
	}
	
	public static Command deliveryManager(){
		return new CommandAdapter() {
			@Override
			public void execute() {
				AdminWorkspace.contentPanel.body.clear();

				final DeliveryManager deliveryManager = new DeliveryManager(); 

				final PopWindow loading = PopWindow.createLoading("Waiting").lock();
				AdminWorkspace.ADMIN_SERVICE_ASYNC.getAllCountries(new AsyncCallbackAdapter<List<Country>>(){
					@Override
					public void onSuccess(List<Country> rs) {
						deliveryManager.setCountryList(rs);
						loading.hide();
						RootPanel.get().remove(loading);
					}
				});
				
				AdminWorkspace.contentPanel.body.add(deliveryManager);
			}
		};
	}
	
    public static Command manageOrder() {
        return new CommandAdapter() {
            @Override
            public void execute() {
                AdminWorkspace.contentPanel.body.clear();
                ComponentQuery componentQuery = new ComponentQuery("Order List", Order.class);
                componentQuery.getQueryCondition().setAsc(false);
                componentQuery.getQueryCondition().setOrderBy("createDate");
                AdminWorkspace.contentPanel.body.add(componentQuery);
            }
        };
    }
    
    public static Command showMessages(){
        return new CommandAdapter(){
            @Override
            public void execute() {
                AdminWorkspace.contentPanel.body.clear();
                ComponentQuery componentQuery = new ComponentQuery("Message List", Message.class);
                String hql = "From Message as m where m.replyBy = null order by m.replied asc";
                componentQuery.getQueryCondition().setAsc(true);
                componentQuery.getQueryCondition().setOrderBy("id");
                componentQuery.getQueryCondition().setHql(hql);
                AdminWorkspace.contentPanel.body.add(componentQuery);
            }  
        };
    }
    
    public static Command popUpReplyPanel(final boolean multiSelect, final Message message, final SelectedCallBack callBack) {
        return new CommandAdapter(){
            @Override
            public void execute() {
                List<Message> messages = retrieveMessageThread(message);
                PostMessage postMessage = new PostMessage();
                postMessage.setMessageList(messages);
                
                final PopWindow popWindow = new PopWindow("Reply Customers", postMessage, true, true);
                popWindow.center();
            }
            private List<Message> retrieveMessageThread(Message message) {
                List<Message> messages = null;
                if (message != null) {
                    messages = new ArrayList<Message>();
                    messages.add(message);
                    Message msg = message;
                    do {
                        if (msg.getReplyTo() != null) {
                            messages.add(msg.getReplyTo());
                            msg = msg.getReplyTo();
                        }
                    } while (msg != null && msg.getReplyTo() != null);
                    Collections.reverse(messages);
                }
                return messages;
            }
        };
    }
    
}
