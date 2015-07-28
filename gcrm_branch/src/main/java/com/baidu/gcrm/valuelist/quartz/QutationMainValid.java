package com.baidu.gcrm.valuelist.quartz;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.quote.service.QuotationMainService;

public class QutationMainValid {
	private static Logger log = LoggerFactory.getLogger(QutationMainValid.class);
	@Autowired
	private QuotationMainService quotationMainService;
	
	@PostConstruct
	public void doIt(){
		log.debug("标杆价定时任务开始于："+new Date());
		quotationMainService.validQuotation(null, null);
		log.debug("标杆价定时任务结束于："+new Date());
	}
}
