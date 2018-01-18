package com.system.model;

import com.base.model.BaseModel;

public class Resource extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String resourceUrl;
	private Resource parentResource;
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	
	public Resource getParentResource() {
		return parentResource;
	}
	public void setParentResource(Resource parentResource) {
		this.parentResource = parentResource;
	}

}
