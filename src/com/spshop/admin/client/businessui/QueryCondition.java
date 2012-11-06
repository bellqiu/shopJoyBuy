package com.spshop.admin.client.businessui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.spshop.model.query.QueryCriteria;
@SuppressWarnings("rawtypes")
public class QueryCondition extends Composite {
	
	private ComponentQuery  componentQuery;
	private Class type;

	private static QueryConditionUiBinder uiBinder = GWT
			.create(QueryConditionUiBinder.class);
	
	@UiField
	CaptionPanel caption;
	
	@UiField TextBox nameBox;
	@UiField DateBox start;
	@UiField DateBox end;
	@UiField Button search;
	@UiField FlowPanel conditionField;
	private String orderBy;
	private boolean asc = false;
	private String hql;
	private List<Object> params;
	@UiField 
	ConditionStyle style;
	
	private Map<String,Widget> fields = new HashMap<String,Widget>();
	
	interface ConditionStyle extends CssResource {
		String conditonLabel();
	}
	
	interface QueryConditionUiBinder extends UiBinder<Widget, QueryCondition> {
	}

	public QueryCondition() {
		initWidget(uiBinder.createAndBindUi(this));
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy/MM/dd");
	    start.setFormat(new DateBox.DefaultFormat(dateFormat));
	    end.setFormat(new DateBox.DefaultFormat(dateFormat));
	}

	public void setComponentQuery(ComponentQuery componentQuery) {
		this.componentQuery = componentQuery;
	}

	public ComponentQuery getComponentQuery() {
		return componentQuery;
	}
	
	public void setCaption(String caption){
		this.caption.setCaptionText(caption);
	}
	
	@UiHandler("search")
	void onSearchClick(ClickEvent event) {
		QueryCriteria criteria = new QueryCriteria();
		criteria.setKey(nameBox.getValue());
		criteria.setType(type.getName());
		criteria.setOrderBy(orderBy);
		criteria.setAsc(asc);
		criteria.setStart(start.getValue());
		criteria.setEnd(end.getValue());
		criteria.setStartIndex(1);
		for (String key : fields.keySet()) {
			Object value = fields.get(key);
			if(value instanceof HasValue){
				criteria.addProperty(key, value);
			}else{
				ListBox val = (ListBox)value;
				criteria.addProperty(key, val.getValue(val.getSelectedIndex()));
			}
		}
		componentQuery.setQueryCriteria(criteria);
		componentQuery.search();
	}

	public void setType(Class type) {
		this.type = type;
	}

	public Class getType() {
		return type;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

	public void addField(String name, String label, Widget field) {
		if(field instanceof IsWidget && (field instanceof HasValue || field instanceof ListBox)){
			fields.put(name, field);
			InlineLabel inlineLabel = new InlineLabel(label + ":");
			inlineLabel.addStyleName(style.conditonLabel());
			conditionField.insert(inlineLabel, conditionField.getWidgetIndex(search));
			conditionField.insert((Widget)field,conditionField.getWidgetIndex(search));
		}
	}

	public Map<String,Widget> getFields() {
		return fields;
	}

}
