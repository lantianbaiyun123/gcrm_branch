package com.baidu.gcrm.valuelist.quartz;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.valuelist.service.IValuelistWithCacheService;
import com.baidu.gcrm.valuelist.utils.ITableMetaDataManger;
import com.baidu.gcrm.valuelist.utils.TableMetaData;

/**
 * 定时刷新值列表缓存数据
 * @author weichengke
 *
 */

public class CacheRefreshJob {
	private static Logger log = LoggerFactory.getLogger(CacheRefreshJob.class);
	@Autowired
	private ITableMetaDataManger talbeManager;
	@Autowired
	private IValuelistWithCacheService dao;
	
	@PostConstruct
	public void doIt() {
		log.debug("缓存刷新开始于"+new Date());
		for(TableMetaData tableMetaData : talbeManager.getTableMetas()){
			talbeManager.loadTableMetaData();
			dao.refreshCache(tableMetaData);
		}
		log.debug("缓存刷新结束于"+new Date());
	}
}
