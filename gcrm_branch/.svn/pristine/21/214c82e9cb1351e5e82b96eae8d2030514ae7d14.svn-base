package com.baidu.gcrm.quote.web.utils;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.random.IRandomStringService;

public class QuotationCodeUtil {
	/**
	* 功能描述：   获取标杆价编号
	* 创建人：yudajun    
	* 创建时间：2014-4-3 下午6:56:26   
	* 修改人：yudajun
	* 修改时间：2014-4-3 下午6:56:26   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
    public static String getQuotationCode() throws CRMBaseException{
    	int len = 3;//默认值为3位
    	String codeLenth = GcrmConfig.getConfigValueByKey("gcrm.QuotationCode.length");
    	if(StringUtils.isNotBlank(codeLenth)){//可以从配置文件获取设置的长度
    		len = Integer.valueOf(codeLenth);
    	}
    	StringBuilder code = new StringBuilder();
    	IRandomStringService randomStringService = ServiceBeanFactory.getRandomStringService();
    	
		code.append("PP-")
				.append(DateUtils.getCurrentFormatDate(DateUtils.YYYYMMDD))
				.append("-")
				.append(randomStringService.random(len, null));
    	
    	return code.toString();
    }
}
