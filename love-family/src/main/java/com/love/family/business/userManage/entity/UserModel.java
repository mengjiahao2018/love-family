package com.love.family.business.userManage.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.love.family.business.roleManage.entity.RoleInfo;

@Entity
@Table(name = "sys_user")
public class UserModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5899805478754620321L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="login_name")
	private String loginName;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="status")
	private String status;
	
	@Column(name="last_modify_password")
	private String lastModifyPassword;
	
	@ManyToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	@JsonIgnore
    @JoinTable(name = "sys_user_role",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
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

	public String getLastModifyPassword() {
		return lastModifyPassword;
	}

	public void setLastModifyPassword(String lastModifyPassword) {
		this.lastModifyPassword = lastModifyPassword;
	}
}