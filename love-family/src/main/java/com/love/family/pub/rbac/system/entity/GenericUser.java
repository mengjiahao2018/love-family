package com.love.family.pub.rbac.system.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.love.family.business.loginManage.entity.GenericRole;
import com.love.family.entity.supports.AbstractEntity;

@MappedSuperclass
public class GenericUser extends AbstractEntity<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8987827623651970940L;

	private String userName;

	private String loginName;

	private String password;

	private String status;
	
	private List<GenericRole> roles;

	@Id
	@GeneratedValue(generator = "seq_user")
	public Long getId() {
		return id;
	}

	public void setId() {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<GenericRole> getRoles() {
		return roles;
	}

	public void setRoles(List<GenericRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getName());
		builder.append("hashCode:").append(this.hashCode());
		builder.append("id:").append(id);
		builder.append("userName:").append(userName);
		builder.append("loginName:").append(loginName);
		builder.append("password:").append(password);
		builder.append("status:").append(status);
		builder.append("roles:").append(roles);
		return builder.toString();
	}

}