package com.love.family.pub.rbac.system.entity;

import java.util.Date;

import com.love.family.business.loginManage.entity.SysRole;

public class User extends GenericUser implements BaseEntityInterfc {
	private String idCard;

	private Long createdBy;
	
	private Date createdTime;
	
	private Long lastModifiedBy;
	
	private Date lastModifiedTime;
	
	private SysRole currentSelectRole;

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

	public SysRole getCurrentSelectRole() {
		return currentSelectRole;
	}

	public void setCurrentSelectRole(SysRole currentSelectRole) {
		this.currentSelectRole = currentSelectRole;
	}
	
	

}
