package com.love.family.business.roleFunctionManage.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.love.family.system.dao.AbstractEntity;

@Entity
@Table(name = "sys_role_function")
public class RoleFunction extends AbstractEntity<Long> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9010495138376054806L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "function_id")
	private Long functionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

}