package com.love.family.business.menuManage.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_menu")
public class MenuEO implements Serializable,Comparable<MenuEO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8506075632068507529L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "icon")
	private String icon;

	@Column(name = "label")
	private String label;

	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "order_num")
	private Long orderNum;

	@Column(name = "is_channel")
	private String isChannel;
	
	@Transient
	private List<MenuEO> demoMenus;

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
	

	public List<MenuEO> getDemoMenus() {
		return demoMenus;
	}

	public void setDemoMenus(List<MenuEO> demoMenus) {
		this.demoMenus = demoMenus;
	}

	@Override
	public int compareTo(MenuEO o) {
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
