package com.baidu.gcrm.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class PatternUtil {
	
	private static Pattern numberPattern = Pattern.compile("[0-9]+");
	private static Pattern emailPattern = Pattern.compile("^([\\w\\.-])+@(\\w)+\\.(\\w{2,3})$");
	private static Pattern imageAreaPattern = Pattern.compile("^([0-9])+\\*([0-9])+$");
	private static Pattern imageSizePattern = Pattern.compile("^([0-9])+k$");
	private static Pattern imageFilePattern = Pattern.compile("^.+\\.(jpg|jpeg|png|gif|bmp)$");
	
	public static boolean isBlank(String str){
		return StringUtils.isBlank(str);
	}
	
	public static boolean isNumber(CharSequence charSeq){
		return match(numberPattern,charSeq);
	}
	
	public static boolean isEmail(String email){
	    return null == email ? false : (email.indexOf("@") > 0 ? true : false) ;
	}
	
	public static boolean isImageAreaRequired(String descStr) {
	    return match(imageAreaPattern,descStr);
	}
	
	public static boolean isImageSize(String descStr) {
        return match(imageSizePattern,descStr);
    }
	
	public static boolean isImageFile(String fileName) {
        return match(imageFilePattern,fileName.toLowerCase());
    }
	
	private static boolean match(Pattern pattern,CharSequence charSeq){
		Matcher m = pattern.matcher(charSeq);
		return m.matches();
	}
}
