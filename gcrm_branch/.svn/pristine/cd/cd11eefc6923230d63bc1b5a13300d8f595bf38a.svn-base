package com.baidu.gcrm.publish.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.publish.dao.IForcePublishProofRepository;
import com.baidu.gcrm.publish.model.ForcePublishProof;
import com.baidu.gcrm.publish.service.IForcePublishProofService;

@Service
public class IForcePublishProofServiceImpl implements IForcePublishProofService {
	@Autowired
    IForcePublishProofRepository forcePublishProofDao;
	
	@Override
	public ForcePublishProof findById(Long id) {
		return forcePublishProofDao.findOne(id);
	}

}
