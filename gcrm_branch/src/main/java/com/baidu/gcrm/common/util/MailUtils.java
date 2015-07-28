package com.baidu.gcrm.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


public class MailUtils {

	/**
	 * 获取邮件列表
	 */
	public static List<String> getEmailListFromStr(String email, String... splitStrs) {
		String splitStr = "[;,]";

		if ((splitStrs.length == 1) && StringUtils.isNotEmpty(splitStrs[0])) {
			splitStr = splitStrs[0];
		}

		List<String> emailList = new ArrayList<String>();

		if (StringUtils.isNotEmpty(email)) {
			String[] tos = email.split(splitStr);

			for (String oneMail : tos) {
				if (StringUtils.isEmpty(oneMail)) {
					continue;
				}
				oneMail = StringUtils.trimToEmpty(oneMail);
				if (validateEmail(oneMail)) {
					emailList.add(oneMail);
				} 
			}
		}

		return emailList;
	}

	/**
	 * 验证邮件地址是否合法
	 * 
	 * @param email 待验证邮件地址
	 */
	public static boolean validateEmail(String email) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]{2,3}");
		Matcher m = p.matcher(email);

		return m.matches();
	}
}
