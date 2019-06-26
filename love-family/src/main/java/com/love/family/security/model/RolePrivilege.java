package com.love.family.security.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RolePrivilege implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3053356092145018091L;

	private Long roleId;

	private Map<String, String> menuMap;

	private List<String> resourceList;

	public RolePrivilege(Long roleId) {
		this.roleId = roleId;
	}

	public Map<String, String> getMenuMap() {
		return menuMap;
	}

	public void setMenuMap(Map<String, String> menuMap) {
		this.menuMap = menuMap;
	}

	public List<String> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}

	public Long getRoleId() {
		return roleId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getName());
		builder.append("hashCode:").append(this.hashCode());
		builder.append("roleId:").append(roleId);
		builder.append("menuMap:").append(menuMap);
		builder.append("resourceList:").append(resourceList);
		return builder.toString();	}
	
	

}
