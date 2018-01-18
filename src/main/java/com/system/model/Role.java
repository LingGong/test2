package com.system.model;

import java.util.List;

import com.base.model.BaseModel;

public class Role extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleName;
	private List<Resource> list;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<Resource> getList() {
		return list;
	}
	public void setList(List<Resource> list) {
		this.list = list;
	}
}
