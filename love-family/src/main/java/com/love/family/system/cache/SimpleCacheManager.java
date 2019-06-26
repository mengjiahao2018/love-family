package com.love.family.system.cache;

import java.io.Serializable;

public interface SimpleCacheManager {

	<K extends Serializable, V extends Object> SimpleCache<K,V> getCache(String cacheName);
}
