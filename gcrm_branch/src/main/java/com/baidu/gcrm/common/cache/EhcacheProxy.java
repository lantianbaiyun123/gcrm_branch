package com.baidu.gcrm.common.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EhcacheProxy<T> implements ICacheProxy<T>{
	
	private Logger logger = LoggerFactory.getLogger(EhcacheProxy.class);
	
	@Autowired
    private Ehcache ehcacheClient;

	@SuppressWarnings("unchecked")
	@Override
	public T get(String key) {
		try{
			Element e = ehcacheClient.get(key) ;
			if( e==null){
				return null;
			}else{
				return (T)e.getObjectValue();
			}
		}catch(Exception e){
			logger.error( e.getLocalizedMessage() ,e ) ;
			return null; 
		}
	}

	@Override
	public Map<String, T> get(Set<String> keySet) {
		try{
			Map<String, T> result = new HashMap<String,T>() ;
			for( String key:keySet){
				T value = this.get( key ) ;
				if( value != null ){
					result.put( key,value) ;
				}
			}
			return result ;
		}catch(Exception e){
			logger.error( e.getLocalizedMessage() , e ) ;
			return null; 
		}
	}

	@Override
	public boolean put(String key, T value) {
		try{
			return put( key , value , 36000 ) ;
		}catch(Exception e){
			logger.error( e.getLocalizedMessage() , e) ;
			return false;
		}
		
	}

	@Override
	public boolean put(Map<String, T> valueMap) {
		try{
			return put( valueMap , 36000 ) ;
		}catch(Exception e){
			logger.error( e.getLocalizedMessage() , e ) ;
			return false; 
		}
		
	}

	@Override
	public boolean put(String key, T value, int timeout) {
		try{
			ehcacheClient.put(new Element(key, value));
			return true ;
		}catch(Exception e){
			logger.error( e.getLocalizedMessage(),e) ;
			return false ;
		}
	}

	@Override
	public boolean put(Map<String, T> valueMap, int timeout) {
		try{
			for( Map.Entry<String, T> entry : valueMap.entrySet()){
				if( !this.put( entry.getKey() , entry.getValue(),timeout ) ){
					return false ;
				}
			}
			return true ;
		}catch(Exception e){
			logger.error( e.getLocalizedMessage() ,e ) ;
			return false ;
		}
		
	}

	@Override
	public boolean remove(String key) {
		try{
			ehcacheClient.remove(key) ;
			return true ;
		}catch(Exception e){
			logger.error( e.getLocalizedMessage(),e) ;
			return false; 
		}
	}

	@Override
	public boolean remove(Set<String> keySet) {
		try{
			for( String key:keySet){
				if( !remove( key ) ){
					return false ;
				}
			}
			return true ;
		}catch(Exception e){
			logger.error( e.getLocalizedMessage() , e ) ;
			return false;
		}
	}

	public Ehcache getEhcacheClient() {
		return ehcacheClient;
	}

	public void setEhcacheClient(Ehcache ehcacheClient) {
		this.ehcacheClient = ehcacheClient;
	}
	
	
}
