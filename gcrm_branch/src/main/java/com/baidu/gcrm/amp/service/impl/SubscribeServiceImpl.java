package com.baidu.gcrm.amp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.amp.dao.ISubscribeDao;
import com.baidu.gcrm.amp.model.Subscribe;
import com.baidu.gcrm.amp.service.ISubscribeService;

@Service
public class SubscribeServiceImpl implements ISubscribeService {
	
	@Autowired
	ISubscribeDao subscribeDao;

	@Override
	public void saveSubscribe(Subscribe subscribe) {
		subscribeDao.save(subscribe);
		
	}

	@Override
	public void delSubscribe(Subscribe subscribe) {
		subscribeDao.updateSubscribeStatus(Subscribe.SubscribeStatus.DISABLE.name(), subscribe.getId());
		
	}

	@Override
	public Page<Subscribe> findAll(Subscribe subscribe, Pageable pageable) {
		return null;
	}
	
}
