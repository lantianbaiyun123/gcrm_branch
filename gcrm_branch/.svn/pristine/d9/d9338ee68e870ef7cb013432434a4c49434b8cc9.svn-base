package com.baidu.gcrm.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baidu.gcrm.log.model.ModifyCheckIgnore;

public class BeanModifyUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<List> getModifyValues(Class<T> clazz, T newValue, T oldValue) {
		List<List> modifyList = new ArrayList<List>();
		if(newValue != null && oldValue !=null ){
			try {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
				    ModifyCheckIgnore check = field.getAnnotation(ModifyCheckIgnore.class);
					if(check == null || !check.skip()){
						if(isWrapClass(field.getType())){
							PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
									clazz);
							Method getMethod = pd.getReadMethod();
							Object o1 = getMethod.invoke(newValue);
							Object o2 = getMethod.invoke(oldValue);
							if(o1!=null){
								if (!o1.equals(o2)) {
									List value = new ArrayList();
									value.add(field.getName());
									value.add(o1);
									value.add(o2);
									modifyList.add(value);
								}
							}else if(o2!=null){
								List value = new ArrayList();
								value.add(field.getName());
								value.add(o1);
								value.add(o2);
								modifyList.add(value);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return modifyList;
	}
	
	
    public static <T> boolean isModified(Class<T> clazz, T newValue, T oldValue) {
        if(newValue != null && oldValue != null ){
            try {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    ModifyCheckIgnore check = field.getAnnotation(ModifyCheckIgnore.class);
                    if(check == null || !check.skip()){
                        if(isWrapClass(field.getType())){
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
                                    clazz);
                            Method getMethod = pd.getReadMethod();
                            Object o1 = getMethod.invoke(newValue);
                            Object o2 = getMethod.invoke(oldValue);
                            if(o1 != null){
                                if (!o1.equals(o2)) {
                                    return true;
                                }
                            }else if(o2 != null){
                                return true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
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

}
