package com.love.family.system.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import com.love.family.system.cache.SimpleCache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class SimpleEhcache implements SimpleCache<Serializable, Object> {
	
	private Ehcache ehcache;

	public SimpleEhcache(Ehcache ehcache) {
		if(ehcache==null) {
			throw new RuntimeException("传入的ehcache实例为空！");
		}
		this.ehcache=ehcache;
	}

	@Override
	public void put(Serializable key, Object object) {
		this.ehcache.put(new Element(key, object));
	}

	@Override
	public Object get(Serializable key) {
		Element e = this.ehcache.get(key);
		if (e != null)
			return e.getObjectValue();
		else
			return null;
	}

	@Override
	public boolean remove(Serializable key) {
		return this.ehcache.remove(key);
	}

	@Override
	public void putAll(Map<Serializable, Object> objectMap) {
		ArrayList<Element> elements = new ArrayList<Element>();
		for(Serializable key : objectMap.keySet()) {
			elements.add(new Element(key, objectMap.get(key)));
		}
		this.ehcache.putAll(elements);
	}

	@Override
	public void clear() {
		this.ehcache.removeAll();
	}

}
