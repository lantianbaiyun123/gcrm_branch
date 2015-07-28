package com.baidu.gcrm.amp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.amp.dao.ILogDao;
import com.baidu.gcrm.amp.model.Log;
import com.baidu.gcrm.amp.service.ILogService;

@Service
public class LogServiceImpl implements ILogService{
	
	@Autowired
	ILogDao logDao;

	@Override
	public void saveLog(Log log) {
		logDao.save(log);
	}

	@Override
	public Page<Log> findAll(Log offer, Pageable pageable) {
		return logDao.findAll(pageable);
	}
	
	
}
