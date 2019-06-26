package com.love.family.system.cache;

import java.io.Serializable;
import java.util.Map;

public interface SimpleCache<K extends Serializable, V extends Object> {
	void put(K key, V object);

	V get(K k);

	boolean remove(K key);

	void putAll(Map<K, V> objectMap);

	void clear();
}
