package com.baidu.gcrm.common;

import java.io.InputStream;

import com.baidu.gcrm.attachment.model.AttachmentModel;

public interface IFileUploadService {
	
	public String upload(String fileName, byte[] contents) ;
	
	public String getUrl(String fileName);
	
	public InputStream download(String fileName);
	
	public void deleteFile(String fileName);
	
	public boolean uploadFile(AttachmentModel attachment);
}
