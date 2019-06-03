package com.love.family.entity.supports;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class AbstractEntity<ID extends Serializable> implements Entity<ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8892783570862445175L;

	protected ID id;

	public void setId(ID id) {
		this.id = id;
	}

	public boolean isDeletable() {
		return true;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this,ToStringStyle.DEFAULT_STYLE).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(null == obj) {
			return false;
		}
		if(this == obj) {
			return  true;
		}
		if(!getClass().equals(obj.getClass())) {
			return false;
		}
		AbstractEntity<?> that = (AbstractEntity<?>)obj;
		return null == this.getId()?false:this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}
	
	
	
	
}
