package com.baidu.gcrm.valuelist.quartz;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.publish.model.PublishMailType;
import com.baidu.gcrm.publish.service.IPublishMailService;

public class PublishSendMail {
	private static Logger log = LoggerFactory.getLogger(PublishSendMail.class);
	@Autowired
	IPublishMailService publishMailService;
	
	
	public void sendOnlineCollectionMail(){
		log.debug("上线提醒定时任务开始于："+new Date());
		publishMailService.findPulishMail(null, PublishMailType.onlineCollection);
		log.debug("上线提醒定时任务结束于："+new Date());
	}
	
	public void sendMaterCollectionMail(){
		log.debug("物料提醒定时任务开始于："+new Date());
		publishMailService.findPulishMail(null, PublishMailType.materCollection);
		log.debug("物料提醒定时任务结束于："+new Date());
	}
}
