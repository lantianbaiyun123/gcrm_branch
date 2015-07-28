package com.baidu.gcrm.attachment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baidu.gcrm.attachment.model.AttachmentModel;
import com.baidu.gcrm.attachment.model.AttachmentModel.ModuleNameWithAtta;

@Repository
public interface IAttachmentModelDao{
	List<AttachmentModel> findByRecordAndModule(Long recoredId,ModuleNameWithAtta moduleName);
	/**
	@Modifying
	@Query("DELETE  from Attachment a where  a.customerNumber= :customerNumber")
	void deleteByCustomerNumber(@Param("customerNumber")Long customerNumber);
	**/
	AttachmentModel findByUrl(String url);
	
	public void save(AttachmentModel attachmentModel);
	public int deleteByRecordAndModule(Long recoredId,ModuleNameWithAtta moduleName);
}
