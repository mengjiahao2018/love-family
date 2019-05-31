package com.love.family.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.love.family.business.loginManage.entity.SysUser;

public class UserInfoImpl implements UserInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 256808602035007549L;

	private Long id;
	private String userCode;
	private String password;

	private List<String> resources = new ArrayList<String>();

	private Map<String, Set<String>> orgAuthority = new HashMap<String, Set<String>>();

	private Map<String, String> menus = new HashMap<String, String>();

	private SysUser sysUser;

	public UserInfoImpl() {

	}

	public UserInfoImpl(SysUser sysUser) {
		this.id = sysUser.getId();
		this.userCode = sysUser.getUsername();
		this.password = sysUser.getPassword();
		this.sysUser = sysUser;
	}

	public SysUser getSimpleUser() {
		return sysUser;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_DEFAULT");
		grantedAuthorities.add(simpleGrantedAuthority);
		return grantedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Map<String, Set<String>> getOrgAuthority() {
		if (orgAuthority == null) {
			orgAuthority = new HashMap<String, Set<String>>();
		}
		return orgAuthority;
	}

	@Override
	public Map<String, String> getMenus() {
		if (menus == null) {
			menus = new HashMap<String, String>();
		}
		return menus;
	}

	@Override
	public List<String> getResources() {
		if (resources == null) {
			resources = new ArrayList<String>();
		}
		return resources;
	}

	@Override
	public String getUsername() {
		return getUserCode();
	}

}
