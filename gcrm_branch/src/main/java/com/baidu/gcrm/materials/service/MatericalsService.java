package com.baidu.gcrm.materials.service;

import java.io.IOException;
import java.util.List;

import com.baidu.gcrm.materials.model.Attachment;

public interface MatericalsService {
	
	/**
	 * 保存db信息
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
    Attachment saveAttachment(Attachment attachment,boolean isNeedRecordHistory) throws  Exception;
	
		
	/**
	 * 删除db信息，同时删除服务器文件
	 * @return
	 */
	void deleteAttachment(Long id);
	
	/**
	 * 上传文件到服务器
	 * @return
	 * @throws IOException 
	 */
	boolean uploadFile(Attachment attachment); 
	
	/**
	 * 删除服务器的文件
	 * @return
	 */
	void deleteFile(String url);
	@Deprecated
	List<Attachment> findByCustomerNumber(Long customerNumber);
	
	
	
	void deleteAttachmentByCustomerId(Long customerId);

	Attachment findById(Long id);

  //  boolean saveAttachments(List<Attachment> attachments, Long customerId);
}
