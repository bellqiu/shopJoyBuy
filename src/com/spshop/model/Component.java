package com.spshop.model;

import java.io.Serializable;
import java.util.Date;

public  abstract class Component implements Serializable,Cloneable{
	public static final String EXPIRED="Y";
	public static final String NOT_EXPIRED="N";
	/**
	 *
	 */
	private static final long serialVersionUID = 5832328929094344696L;
	private long id;
	private Site site;
	private String name;
	private Date createDate;
	private Date updateDate;
	private boolean expired;
	private String strExpired;
	private int componentStatus;
	
	public Component() {
	}
	
	public Component(Component component) {
		this(component,true);
	}
	
	public Component(Component component,boolean withSite) {
		setId(component.getId());
		if(withSite){
			if(null!=component.getSite()){
				setSite(component.getSite().clone());
			}
		}
		setName(component.getName());
		setCreateDate(component.getCreateDate());
		setUpdateDate(component.getUpdateDate());
		setComponentStatus(getComponentStatus());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Site getSite() {
		return site;
	}
	
	public void setStrExpired(String e){
		if(EXPIRED.equals(e)){
			strExpired = e;
		}else{
			strExpired = NOT_EXPIRED; 
		}
	}
	
	public String getStrExpired(){
		if(strExpired == EXPIRED){
			return EXPIRED;
		}else{
			return NOT_EXPIRED;
		}
	}
	
	public boolean isExpired() {
		return strExpired == EXPIRED;
	}

	public abstract Component clone();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (id != other.id)
			return false;
		
		if(expired!=other.expired){
			return false;
		}
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public int getComponentStatus() {
		return componentStatus;
	}

	public void setComponentStatus(int componentStatus) {
		this.componentStatus = componentStatus;
	}


}
