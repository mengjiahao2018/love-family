package com.love.family.security.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserInfo extends UserDetails {

	public Long getId();

	public String getLoginName();

	public String getPassword();

	public Map<String, Set<String>> getOrgAuthority();

	public Map<String, String> getMenus();

	public List<String> getResources();
	
	GenericUser getSimpleUser(); 

}
