package com.baidu.gcrm.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.SystemPropertyUtils;

import com.baidu.gcrm.common.exception.CRMRuntimeException;

public class GcrmConfig {
	private static Properties props = null;

	/**
	 * 解析property的placeholder工具
	 */
	private static PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(
			SystemPropertyUtils.PLACEHOLDER_PREFIX,
			SystemPropertyUtils.PLACEHOLDER_SUFFIX,
			SystemPropertyUtils.VALUE_SEPARATOR, false);

	private static Locale defaultLocale;

	private static HashSet<String> logOperationPermits;

	/**
	 * 根据配置的key
	 */
	public static String getConfigValueByKey(String configKey) {
		if (props == null) {

			return null;
		}
		return getProperty(configKey);
	}

	/**
	 * 根据给定的key返回对应的配置值
	 * 
	 */
	public static String getConfigValueByKey(String key, String defaultValue) {
		String value = getConfigValueByKey(key);

		if (StringUtils.isEmpty(value)) {
			value = defaultValue;
		}

		return value;
	}

	public static String getConfigValueByKey(String key, Properties properties) {
		if (props == null) {
			return null;
		}
		return getProperty(key, properties);
	}

	/**
	 * 根据给定的值返回字符串数组 默认是分号(;)
	 * 
	 * @param key
	 *            关键字
	 * 
	 */
	public static String[] getConfigValueArrayByKey(String key) {
		return getConfigValueArrayByKey(key, "\\;");
	}

	/**
	 * 根据给定的值返回字符串数组
	 * 
	 * @param key
	 *            关键字
	 * @param splitSymbol
	 *            拆分字符串的特殊符号
	 * 
	 */
	public static String[] getConfigValueArrayByKey(String key,
			String splitSymbol) {
		String value = getConfigValueByKey(key);
		String[] valueArray = null;

		if (value != null) {
			valueArray = value.split(splitSymbol);
		}

		return valueArray;
	}

	/**
	 * 根据给定的值返回字符串数组,并由iso8859转换成utf-8
	 * 
	 */
	public static String[] getConfigIsoValue2GbArrayByKey(String key,
			String splitSymbol) {
		String value = getConfigValueByKey(key);
		String[] valueArray = null;

		if (value != null) {
			valueArray = value.split(splitSymbol);
		}

		return valueArray;
	}

	/**
	 * 根据给定的值返回ArrayList,默认是分号(;)
	 * 
	 * @param key
	 *            关键字
	 * 
	 */
	public static List<String> getConfigValueListByKey(String key) {
		return getConfigValueListByKey(key, "\\;");
	}

	/**
	 * 根据给定的值返回ArrayList
	 * 
	 * @param key
	 *            关键字
	 * @param splitSymbol
	 *            拆分字符串的特殊符号
	 * 
	 */
	public static List<String> getConfigValueListByKey(String key,
			String splitSymbol) {
		List<String> valueList = new ArrayList<String>();
		String[] strings = getConfigValueArrayByKey(key, splitSymbol);
		if (strings != null) {
			Collections.addAll(valueList, strings);
		}
		return valueList;
	}

	/**
	 * 根据给定的值返回ArrayList,并将其中的iso转换成utf-8
	 * 
	 */
	public static List<String> getConfigIsoValue2GbListByKey(String key,
			String splitSymbol) {
		ArrayList<String> valueList = new ArrayList<String>();
		Collections.addAll(valueList,
				getConfigIsoValue2GbArrayByKey(key, splitSymbol));

		return valueList;
	}

	private static String getProperty(String key, Properties properties) {
		return helper.replacePlaceholders(props.getProperty(key), properties);
	}

	private static String getProperty(String key) {
		return helper.replacePlaceholders(props.getProperty(key), props);
	}
	
	/*---------------------------------------------------------------------------------------------------------*/
	public static Locale getDefaultLocale() {
		if (defaultLocale == null) {
			defaultLocale = Locale.SIMPLIFIED_CHINESE;
		}
		return defaultLocale;
	}

	/**
	 * 获取邮件发送人
	 * 
	 * @return DOCUMENT ME!
	 */
	public static String getMailFromUser() {
		return getConfigValueByKey("com.baidu.mailfrom");
	}

	/**
	 * webService是否允许的IP
	 * 
	 * @param methodName
	 *            String
	 * 
	 * @return boolean
	 */
	public static boolean isPermitOutputLog(String methodName) {
		if (logOperationPermits == null) {
			logOperationPermits = new HashSet<String>();
			logOperationPermits.addAll(getNotOutputNames());
		}

		return logOperationPermits.contains(methodName);
	}

	/**
	 * 获取输出log的方法名称列表
	 */
	public static List<String> getNotOutputNames() {
		return getConfigValueListByKey("com.baidu.log.not.output", ",");
	}

	public static Set<String> getAdminMailList() {
		String adminEmail = getConfigValueByKey("gcrm_admin");
		return transferStringArrayToSet(adminEmail == null ? null : adminEmail.split(","));
	}
	
	private static Set<String> transferStringArrayToSet(String... array) {
		if (array == null) {
			return new HashSet<String>();
		}
		return new HashSet<String>(Arrays.asList(array));
	}

	public static boolean isMockEnable() {
		return Boolean.valueOf(getConfigValueByKey("system.mock", "false"));
	}

	public void setProps(List<Properties> lists) {
		Properties Proper = new Properties();
		for (Properties properties : lists) {
			Proper.putAll(properties);
		}
		GcrmConfig.props = Proper;
	}

	public static int getSleepTime() {
		String time=getConfigValueByKey("system.sleep", "1500");
		return Integer.parseInt(time);
	}
	
	public static String getLocalTempDir() {
		String tempDir = getConfigValueByKey("file.temp.directory");
		if (!tempDir.endsWith("/")) {
			tempDir = tempDir + "/";
		}
		try {
			FileUtils.forceMkdir(new File(tempDir));
		} catch (IOException e) {
			throw new CRMRuntimeException(e);
		}
		return tempDir;
	}
	
	public static String getLocalBackupDir() {
		String backupDir = getConfigValueByKey("file.backup.directory");
		if (!backupDir.endsWith("/")) {
			backupDir = backupDir + "/";
		}
		try {
			FileUtils.forceMkdir(new File(backupDir));
		} catch (IOException e) {
			throw new CRMRuntimeException(e);
		}
		return backupDir;
	}
	
	public static String getLocalDir(String dirPropKey) {
		String dir = getConfigValueByKey(dirPropKey);
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		try {
			FileUtils.forceMkdir(new File(dir));
		} catch (IOException e) {
			throw new CRMRuntimeException(e);
		}
		return dir;
	}
}
