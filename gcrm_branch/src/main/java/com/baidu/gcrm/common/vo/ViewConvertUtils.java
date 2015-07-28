package com.baidu.gcrm.common.vo;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.i18n.MessageHelper;

public class ViewConvertUtils {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map viewConvert(Object value,List<Class> processedClass){
		Map convertedValue = new HashMap();
		Class clazz = value.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			try {
				PropertyDescriptor pd=null;
				try {
					pd = new PropertyDescriptor(field.getName(),
							clazz);
				} catch (Exception e) {
					continue;
				}
				Method getMethod = pd.getReadMethod();
				Object o = getMethod.invoke(value);
				Class fieldClazz = field.getType();
				if(isWrapClass(fieldClazz)){
					ViewConvert convert = field.getAnnotation(ViewConvert.class);
					convertedValue.put(field.getName(), o);
					if(convert != null ){
						String displayName = convert.displayName();
						Object convertedO =  null;
						if(isEnum(fieldClazz)){
							convertedO = MessageHelper.getMessage(convert.i18nKey()+"."+o,
									 RequestThreadLocal.getLocale());
						}else{
							ValueConvertHandler convertInstance = convert.customeClass().newInstance();
							convertedO = ((ValueConvertHandler)convertInstance).convert(o);
						}
						convertedValue.put(displayName,convertedO);
					}
				}else{
					if(processedClass == null){
						processedClass = new ArrayList<Class>();
					}
					//判断类之间相互引用的环
					if(processedClass.contains(fieldClazz)){
						continue;
					}else{
						processedClass.add(fieldClazz);	
						convertedValue.put(field.getName(), viewConvert(o,processedClass));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return convertedValue; 
	}
	
	public static Map viewConvert(Object value){
		return viewConvert(value,new ArrayList<Class>());
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isWrapClass(Class clz) { 
	    try { 
	       return (clz.equals(String.class)
	    		   ||clz.equals(Date.class)
	    		   ||clz.equals(java.sql.Date.class)
	    		   ||clz.isEnum() || (clz.getSuperclass() != null && clz.getSuperclass().isEnum())
	    		   ||((Class) clz.getField("TYPE").get(null)).isPrimitive());
	    } catch (Exception e) { 
	        return false; 
	    } 
	}
	
	public static boolean isEnum(Class clz){
		try {
			return clz.isEnum() || (clz.getSuperclass() != null && clz.getSuperclass().isEnum());
		} catch (Exception e) {
			return false;
		}
	}

}
