package com.spshop.admin.client.businessui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.PopWindow;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.model.Image;
import com.spshop.model.enums.ImageSizeType;
public class ImageCreation extends Composite{

	private static ImageCreationUiBinder uiBinder = GWT
			.create(ImageCreationUiBinder.class);
	
	private Image image;
	
	@UiField
	TextBox imageName;
	
	@UiField
	ListBox imageType;
	
	@UiField
	FileUpload imageFile;
	@UiField FormPanel formPanel;
	@UiField TabLayoutPanel tab;
	@UiField Hidden id;
	@UiField Button submit;
	@UiField FlowPanel small;
	@UiField VerticalPanel links;

	interface ImageCreationUiBinder extends UiBinder<TabLayoutPanel, ImageCreation> {
	}
	
	public ImageCreation(Image image) {
		initWidget(uiBinder.createAndBindUi(this));
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		for(ImageSizeType imageSizeType : ImageSizeType.values()){
			imageType.addItem(imageSizeType.getTitle(), imageSizeType.getValue());
		}
		setImage(image);
	}
	
	@UiHandler("formPanel")
	void onFormPanelSubmitComplete(SubmitCompleteEvent event) {
		CommandFactory.lock("Create Image").execute();
		final ImageCreation self = this;
		String rs = event.getResults();
		String noWrapRs = rs.substring(rs.indexOf('>')+1,rs.lastIndexOf('<'));
		if(null!=noWrapRs&&noWrapRs.matches("\\d+")){
			final long id = Long.valueOf(noWrapRs);
			AdminWorkspace.ADMIN_SERVICE_ASYNC.getImageById(id, new AsyncCallbackAdapter<Image>(){
				@Override
				public void onSuccess(Image rs) {
					self.setImage(rs);
					CommandFactory.release().execute();
				}
			});
		}else{
			PopWindow window = new PopWindow();
			window.setText("Error");
			window.setContent(new HTML(rs));
			window.setGlassEnabled(true);
			window.center();
		}
	}
	public void setImage(Image image) {
		this.image = image;
		imageName.setValue(image.getName());
		for(int i=0 ; i<imageType.getItemCount(); i++){
			String value = imageType.getValue(i);
			if(value.equals(image.getSizeType().getValue())){
				imageType.setSelectedIndex(i);
			}
		}
		id.setValue(""+image.getId());
		if(image.getId()>0){
			submit.setText("Update");
			small.clear();
			small.add(new com.google.gwt.user.client.ui.Image(image.getSmallUrl()));
			links.clear();
			Anchor large = new Anchor(AdminWorkspace.getSilteUrl()+image.getLargerUrl());
			Anchor logo = new Anchor(AdminWorkspace.getSilteUrl()+image.getLogoUrl());
			Anchor thumbnail = new Anchor(AdminWorkspace.getSilteUrl()+image.getThumbnailUrl());
			Anchor small = new Anchor(AdminWorkspace.getSilteUrl()+image.getSmallUrl());
			Anchor icon = new Anchor(AdminWorkspace.getSilteUrl()+image.getIconUrl());
			
			Anchor original = new Anchor(AdminWorkspace.getSilteUrl()+image.getNoChangeUrl());
			large.addClickHandler(new LinksClick(image.getLargerUrl()));
			logo.addClickHandler(new LinksClick(image.getLogoUrl()));
			thumbnail.addClickHandler(new LinksClick(image.getThumbnailUrl()));
			icon.addClickHandler(new LinksClick(image.getIconUrl()));
			original.addClickHandler(new LinksClick(image.getNoChangeUrl()));
			small.addClickHandler(new LinksClick(image.getSmallUrl()));
			links.add(large);
			links.add(logo);
			links.add(thumbnail);
			links.add(small);
			links.add(icon);
			links.add(original);
			links.setVisible(true);
			imageType.setEnabled(true);
		}else{
			submit.setText("Create");
			links.clear();
			links.setVisible(false);
		}
		
	}
	
	class LinksClick implements ClickHandler{
		private String imageURL;
		private int[] size;
		private boolean useCustomSize = true;
		public LinksClick(String imageURL,int[] size) {
			this.imageURL = imageURL;
			this.size = size;
		}
		public LinksClick(String imageURL) {
			this.imageURL = imageURL;
			useCustomSize = false;
		}
		@Override
		public void onClick(ClickEvent e) {
			PopWindow popWindow = new PopWindow();
			popWindow.setText("Image");
			SimplePanel cnt = new SimplePanel(new com.google.gwt.user.client.ui.Image(imageURL));
			new com.google.gwt.user.client.ui.Image(image.getNoChangeUrl());
			if(useCustomSize){
				cnt.setSize(size[0]+"px", size[1]+"px");
			}
			popWindow.setContent(cnt);
			popWindow.setAnimationEnabled(true);
			popWindow.center();
		}
		
	}
	
	public Image getImage() {
		return image;
	}
	
	@UiHandler("submit")
	void onSubmitClick(ClickEvent event) {
		if(null==imageName||"".equals(imageName)){
			Window.alert("Image name cannot be null !");
			return;
		}
		formPanel.submit();
	}
}
