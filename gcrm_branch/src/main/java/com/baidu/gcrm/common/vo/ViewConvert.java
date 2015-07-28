package com.baidu.gcrm.common.vo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewConvert {
	
	String convertType() default ConvertConstant.CONVERT_TYPE_ENUM;
	
	String displayName() ;
	
	Class<? extends ValueConvertHandler> customeClass() default ValueConvertHandler.class;
	
	String i18nKey() default ""; 
}
