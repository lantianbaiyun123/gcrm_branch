package com.baidu.gcrm.common.cache;

import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

public class CacheTemplate<T> extends EhcacheProxy<T>{
	
	
	public T fetch(String key,T defaultValue,DataLoader<T> loader){
		Assert.notNull(key, "key must not be null");
		T value = get(key) ;
		if (value != null) {
			return value;
		}
		if(loader != null){
			value = loader.loadData();
		}
		if(value != null){
			put(key, value);
		}else{
			value = defaultValue;
		}
		
		return value;
	}
	
	public Map<String,T> fetch(Set<String> keySet,int timeout, DataLoader<Map<String,T>> loader){
		Assert.notNull(keySet, "key must not be null");
		Assert.notNull(loader, "Callback object must not be null");
		
		Map<String,T> result = get(keySet) ;
		if ( result != null) {
			return result;
		}
		
		result=loader.loadData();
		if(result != null){
			put(result, timeout);
		}
		
		return result;
	}
	



}
