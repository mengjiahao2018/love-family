package com.love.family.security.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.love.family.business.roleManage.entity.RoleInfo;

public class User extends GenericUser implements BaseEntityInterfc {
	private String idCard;

	private Long createdBy;
	
	private Date createdTime;
	
	private Long lastModifiedBy;
	
	private Date lastModifiedTime;
	
	@JsonIgnore
	private RoleInfo currentSelectRole;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8333192639305602645L;

	@Override
	public String[] needPersistFields() {
		return new String[] { "createdBy", "createdTime", "lastModifiedBy", "lastModifiedTime" };
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public RoleInfo getCurrentSelectRole() {
		return currentSelectRole;
	}

	public void setCurrentSelectRole(RoleInfo currentSelectRole) {
		this.currentSelectRole = currentSelectRole;
	}
	
	

}
