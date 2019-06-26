package com.love.family.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserInfoImpl implements UserInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 256808602035007549L;

	private Long id;
	private String loginName;
	private String password;

	private List<String> resources = new ArrayList<String>();

	private Map<String, Set<String>> orgAuthority = new HashMap<String, Set<String>>();

	private Map<String, String> menus = new HashMap<String, String>();
	
	private GenericUser user;

	public UserInfoImpl() {

	}

	public UserInfoImpl(GenericUser user) {
		this.id = user.getId();
		this.loginName = user.getLoginName();
		this.password = user.getPassword();
		this.user = user;
	}

	@Override
	public GenericUser getSimpleUser() {
		return user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
		return getLoginName();
	}

}
