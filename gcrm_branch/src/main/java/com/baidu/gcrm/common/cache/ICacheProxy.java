package com.baidu.gcrm.common.cache;

import java.util.Map;
import java.util.Set;

public interface ICacheProxy<T> {
	
	public T get(String key);
	public Map<String,T> get(Set<String> keySet);
	
	public boolean put(String key, T value);
	public boolean put(Map<String,T> valueMap);
	public boolean put(String key,T value,int timeout);
	public boolean put(Map<String,T> valueMap,int timeout);
	
	public boolean remove(String key );
	public boolean remove(Set<String> keySet);

}
