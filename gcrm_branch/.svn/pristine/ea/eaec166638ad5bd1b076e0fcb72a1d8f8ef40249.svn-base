package com.baidu.gcrm.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.attachment.model.AttachmentModel;
import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.DownloadObject;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

/**
 * 云存储
 * 
 * 
 */
@Service
public class CloudStorageServiceImpl implements IFileUploadService{

	@Value("#{appproperties['cloud.storage.ak']}")
	private String AK;
	@Value("#{appproperties['cloud.storage.sk']}")
    private String SK;
	@Value("#{appproperties['cloud.storage.host']}")
    private String HOST;
	@Value("#{appproperties['cloud.storage.bucket']}")
    private String BUCKET;
    
	private BaiduBCS BAIDU_BCS = null;
	

	/**
	 * 上传文件到云端
	 * @param fileName
	 * @param contents
	 * @throws IOException
	 */
	@Override
	public String upload(String fileName, byte[] contents){
		if(BAIDU_BCS == null ){
			BAIDU_BCS = new BaiduBCS(new BCSCredentials(AK, SK),HOST);
		}
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentEncoding("utf-8");
		objectMetadata.setContentLength(contents.length);
		
		PutObjectRequest request = new PutObjectRequest(BUCKET, fileName, new ByteArrayInputStream(contents), objectMetadata);
		BAIDU_BCS.putObject(request);
		return getUrl(fileName);
	}

	 public boolean uploadFile(AttachmentModel attachment) {
	        String tempUrl = "";
	        String url = "";
	        tempUrl = "/" + UUID.randomUUID().toString();

	        try {
	            url = upload(tempUrl, attachment.getBytes());
	        } catch (Exception e) {
	            return false;
	        }

	        attachment.setTempUrl(tempUrl);
	        attachment.setUrl(url);
	        return true;
	    }
	/**
	 * 获取fileName的地址
	 * @param fileName
	 * @return
	 */
	@Override
	public String getUrl(String fileName) {
		if(BAIDU_BCS == null ){
			BAIDU_BCS = new BaiduBCS(new BCSCredentials(AK, SK),HOST);
		}
		GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(HttpMethodName.GET, BUCKET, fileName);
		return BAIDU_BCS.generateUrl(generateUrlRequest);
	}

	/**
	 * 从云端下载文件
	 * @param fileName
	 * @return
	 */
	@Override
	public InputStream download(String fileName) {
		if(BAIDU_BCS == null ){
			BAIDU_BCS = new BaiduBCS(new BCSCredentials(AK, SK),HOST);
		}
		GetObjectRequest request = new GetObjectRequest(BUCKET, fileName);
		BaiduBCSResponse<DownloadObject> response = BAIDU_BCS.getObject(request);
		DownloadObject downloadObject =  response.getResult();
		InputStream result = null;
		if(downloadObject != null){
			result = downloadObject.getContent();
		}
		return result;
	}
	
	/**
	 * 删除文件
	 * @param fileName
	 */
	@Override
	public void deleteFile(String fileName) {
		if(BAIDU_BCS == null ){
			BAIDU_BCS = new BaiduBCS(new BCSCredentials(AK, SK),HOST);
		}
		BAIDU_BCS.deleteObject(BUCKET, fileName);
	}
}
