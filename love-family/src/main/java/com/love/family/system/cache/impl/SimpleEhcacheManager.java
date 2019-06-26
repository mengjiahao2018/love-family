package com.love.family.system.cache.impl;

import java.util.HashMap;
import java.util.Map;

import com.love.family.system.cache.SimpleCacheManager;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;


public class SimpleEhcacheManager implements SimpleCacheManager {

	private CacheManager cacheManager;
	
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	public Map<String,SimpleEhcache> cacheMap = new HashMap<String,SimpleEhcache>();

	@Override
	public SimpleEhcache getCache(String cacheName) {
		SimpleEhcache simpleEhcache = cacheMap.get(cacheName);
		if(simpleEhcache==null) {
			Ehcache ehcache = cacheManager.getEhcache(cacheName);
			simpleEhcache = new SimpleEhcache(ehcache);
			cacheMap.put(cacheName, simpleEhcache);
		}
		return simpleEhcache;
	}

}
