package com.love.family.business.functionManage.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_function")
public class GenericFunction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3673856111198829475L;

	public static final String MENU_TYPE = "MENU";
	
	public static final String URL_TYPE = "URL";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="code")
	protected String code;

	@Column(name="name")
	protected String name;
	
	@Column(name="status")
	protected String status;
	
	@Column(name="type")
	protected String type;

	@Column(name="url")
	protected String url;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getName());
		builder.append("hashCode:").append(this.hashCode());
		builder.append("name:").append(name);
		builder.append("url:").append(url);
		return builder.toString();
	}

}
