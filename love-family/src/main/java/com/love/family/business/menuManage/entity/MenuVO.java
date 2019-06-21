package com.love.family.business.menuManage.entity;

import java.io.Serializable;
import java.util.List;

public class MenuVO implements Serializable,Comparable<MenuVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8003350911663758098L;

	private Long id;

	private String code;

	private String icon;

	private String label;

	private Long parentId;

	private Long orderNum;

	private String isChannel;

	private List<MenuVO> demoMenus;
	
	private String functionName;
	
	private String functionUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getIsChannel() {
		return isChannel;
	}

	public void setIsChannel(String isChannel) {
		this.isChannel = isChannel;
	}

	public List<MenuVO> getDemoMenus() {
		return demoMenus;
	}

	public void setDemoMenus(List<MenuVO> demoMenus) {
		this.demoMenus = demoMenus;
	}
	
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	@Override
	public int compareTo(MenuVO o) {
		String other = o.getCode()==null?"":o.getCode();
		String self = this.getCode() ==null?"":this.getCode();
		if(self.compareTo(other)>0) {
			return 1;
		}else if(self.compareTo(other)<0) {
			return -1;
		}else {
			return 0;
		}
	}

}
