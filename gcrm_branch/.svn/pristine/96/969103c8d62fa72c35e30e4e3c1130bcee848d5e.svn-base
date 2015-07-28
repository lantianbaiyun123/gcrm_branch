package com.baidu.gcrm.valuelist.quartz;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.personalpage.service.IOperationMailService;

public class PositionMailJob {

	private static Logger log = LoggerFactory.getLogger(PositionMailJob.class);
	 @Autowired
	 IOperationMailService operationMailService;
	 public void sendPositonFullMail(){
			log.debug("满载率统计任务开始于："+new Date());
			operationMailService.StatisticsPositonByChannel();
			log.debug("满载率统计任务结束于："+new Date());
	 }
}
