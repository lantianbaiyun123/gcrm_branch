package com.baidu.gcrm.amp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.baidu.gcrm.amp.model.Log;

public interface ILogService {
	
	void saveLog(Log log);
	
	Page<Log> findAll(Log log,Pageable page);

}
