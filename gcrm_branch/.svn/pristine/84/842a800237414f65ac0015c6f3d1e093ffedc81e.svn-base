package com.baidu.gcrm.data.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.util.MD5Generator;
import com.baidu.gcrm.data.bean.FileGenPair;
import com.thoughtworks.xstream.XStream;


/**
 * 数据文件生成器
 * 
 */
public abstract class FileGenerator implements FileGenerate {

	@Override
	public FileGenPair generateFile(List<?> records) {
		String localFileName = getLocalFileName();
		genericlyGenerateFile(localFileName, records);
		String remoteFilePath = getRemoteFilePath();
		return new FileGenPair(localFileName, remoteFilePath);
	}

	@Override
	public FileGenPair generateMD5File() {
		String localFileName = getLocalFileName();
		String localMD5FileName = getMD5FileName(localFileName);
		String remoteFilePath = getRemoteFilePath();
		String remoteMD5FilePath = getMD5FileName(remoteFilePath);
		try {
			File file = getLocalTargetFile(localFileName);
			String md5Value = MD5Generator.getFileMD5(file);
			File md5file = getLocalTargetFile(localMD5FileName);
			FileUtils.deleteQuietly(md5file);//删除文件
			FileUtils.writeStringToFile(md5file, md5Value, "UTF-8");
			return new FileGenPair(localMD5FileName, remoteMD5FilePath);
		} catch (Exception e) {
			String lf = "generateMD5File() failed from sourceFile[%s] to targetFile[%s]!";
			String message = String.format(lf, localMD5FileName, remoteMD5FilePath);
			LoggerHelper.err(getClass(), message, e);
			throw new CRMRuntimeException(e.getMessage(), e);
		}
	}


	/**
	 * 生成文件文本
	 * 
	 * @param fileName
	 * @param records
	 * @return
	 */
	protected void genericlyGenerateFile(String fileName, List<?> records) {
		File file = getLocalTargetFile(fileName);
		FileUtils.deleteQuietly(file);//删除文件
		Writer fw = null;
		try {
			fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			XStream xStream = new XStream();
			xStream.autodetectAnnotations(true);
			xStream.setMode(XStream.NO_REFERENCES);
			alias(xStream);
			xStream.toXML(records, fw);
			xStream=null;
			fw.close();
		} catch (Exception e) {
			String lf = "generateFile() failed for targetFile[%s],records size[%s]!";
			String message = String.format(lf, getLocalFileName(), records == null ? "null" : records.size() + "");
			LoggerHelper.err(getClass(), message, e);
			throw new CRMRuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(fw);
		}
	}
	
	private File getLocalTargetFile(String fileName) {
		return new File(GcrmConfig.getLocalDir("file.target.directory") + fileName);
	}

	/**
	 * 生成临时文件
	 * 
	 * @return
	 */
	protected String randomGenerateFileName() {
		long now = System.currentTimeMillis();
		return now + "_" + (now >> 4);
	}

	/**
	 * 获取远程存储文件路径（大部分情况是和{@link #getLocalFileName()}相同）
	 * 
	 * @return
	 */
	protected String getRemoteFilePath() {
		return getLocalFileName();
	}

	/**
	 * 获取某个文件对应的md5文件名
	 * 
	 * @return
	 */
	protected String getMD5FileName(String filePath) {
		return filePath + Constants.MD5_EXTENSION;
	}

	/**
	 * 获取本地文件的文件名
	 * 
	 * @return
	 */
	protected abstract String getLocalFileName();

	protected abstract void alias(XStream xstream);
}
