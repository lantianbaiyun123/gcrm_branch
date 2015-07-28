package com.baidu.gcrm.attachment.service;

import java.util.List;

import com.baidu.gcrm.attachment.model.AttachmentModel;
import com.baidu.gcrm.attachment.model.AttachmentModel.ModuleNameWithAtta;

public interface IAttachmentModelService {
	public void save(AttachmentModel attachmentModel);
	public List<AttachmentModel> findByRecordAndModule(Long recoredId,ModuleNameWithAtta moduleName);
	public int deleteByRecordAndModule(Long recoredId,ModuleNameWithAtta moduleName);
	
}
