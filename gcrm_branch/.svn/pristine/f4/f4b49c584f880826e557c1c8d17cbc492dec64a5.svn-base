package com.baidu.gcrm.valuelist.quartz;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.personalpage.service.IPersonalPageService;

public class OperateReportJob {
	private static Logger log = LoggerFactory.getLogger(OperateReportJob.class);
	
	@Autowired
	private IPersonalPageService personalPageService;
	
	@PostConstruct
	public void doIt(){
		log.debug("分析操作统计定时任务开始于："+new Date());
		personalPageService.analysisOperateReport();
		log.debug("分析操作统计定时任务结束于："+new Date());
	}
}
