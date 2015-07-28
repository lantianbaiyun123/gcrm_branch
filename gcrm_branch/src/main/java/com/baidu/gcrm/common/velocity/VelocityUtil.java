package com.baidu.gcrm.common.velocity;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.ServiceBeanFactory;


/**
 * 通用的velocity工具，可以直接注入到需要它的地方
 * 
 */
public class VelocityUtil {

	private static final String CHARSET = "UTF-8";
	private static final String BASEFOLDER = GcrmConfig.getConfigValueByKey("velocity.basefolder");

	/**
	 * 合并模板和数据
	 * 
	 * @param templateName 不能为null
	 * @param valueMap 不能为null
	 * @return
	 */
	public static String merge(String templateName, Map<String, Object> valueMap) {
		VelocityEngine velocityEngine = ServiceBeanFactory.getVelocityEngine();
		String filepath = BASEFOLDER + "/" + templateName;
		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, filepath, CHARSET, valueMap);
	}

	/**
	 * 合并模板和数据
	 * 
	 * @param templateName 不能为null
	 * @param valueMap 不能为null
	 * @return
	 */
	public static String merge(String templateName, String key, Object value) {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(key, value);
		return merge(templateName, valueMap);
	}
}
