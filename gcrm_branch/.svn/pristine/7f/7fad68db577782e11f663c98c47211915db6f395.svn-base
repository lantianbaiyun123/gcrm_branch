package com.baidu.gcrm.attachment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.attachment.dao.IAttachmentModelDao;
import com.baidu.gcrm.attachment.model.AttachmentModel;
import com.baidu.gcrm.attachment.model.AttachmentModel.ModuleNameWithAtta;
import com.baidu.gcrm.attachment.service.IAttachmentModelService;

@Service
public class AttachmentModelServiceImpl implements IAttachmentModelService {
    @Autowired
	private IAttachmentModelDao attachmentModelDao;
	@Override
	public void save(AttachmentModel attachmentModel) {
		// TODO Auto-generated method stub
		attachmentModelDao.save(attachmentModel);
	}
	@Override
	public List<AttachmentModel> findByRecordAndModule(Long recoredId,
			ModuleNameWithAtta moduleName) {
		// TODO Auto-generated method stub
		return attachmentModelDao.findByRecordAndModule(recoredId, moduleName);
	}
	public int deleteByRecordAndModule(Long recoredId,
			ModuleNameWithAtta moduleName) {
	   return attachmentModelDao.deleteByRecordAndModule(recoredId, moduleName);
	
	}
	

}
