package com.expensetracker.caching;

import java.util.Set;

import org.ehcache.Cache;

import com.appslandia.common.caching.AppCache;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class EhAppCache<K, V> implements AppCache<K, V> {

	final Cache<K, V> cache;

	public EhAppCache(Cache<K, V> cache) {
		this.cache = cache;
	}

	@Override
	public V get(K key) {
		return this.cache.get(key);
	}

	@Override
	public void put(K key, V value) {
		this.cache.put(key, value);
	}

	@Override
	public boolean putIfAbsent(K key, V value) {
		return this.cache.putIfAbsent(key, value) == null;
	}

	@Override
	public boolean containsKey(K key) {
		return this.cache.containsKey(key);
	}

	@Override
	public boolean remove(K key) {
		if (this.cache.containsKey(key)) {
			this.cache.remove(key);
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(K key, V oldValue) {
		return this.cache.remove(key, oldValue);
	}

	@Override
	public void removeAll(Set<? extends K> keys) {
		this.cache.removeAll(keys);
	}

	@Override
	public boolean replace(K key, V value) {
		return this.cache.replace(key, value) != null;
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return this.cache.replace(key, oldValue, newValue);
	}

	@Override
	public void clear() {
		this.cache.clear();
	}
}
