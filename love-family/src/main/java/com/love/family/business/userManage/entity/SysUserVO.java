package com.love.family.business.userManage.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.love.family.business.roleManage.entity.RoleInfo;

public class SysUserVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -235125017150457950L;

	private Long id;
	
	private String loginName;
	
	private String userName;
	
	private String password;
	
	private String status;
	
	@JsonIgnore
	private List<RoleInfo> roles = new ArrayList<RoleInfo>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<RoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleInfo> roles) {
		this.roles = roles;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}