package com.baidu.gcrm.data.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.util.GcrmFileUtils;
import com.baidu.gcrm.common.util.GcrmHelper;
import com.baidu.gcrm.data.bean.ADContent;
import com.baidu.gcrm.data.bean.PositionBean;
import com.baidu.gcrm.data.service.FileGenerate;
import com.baidu.gcrm.data.service.IADContentDataSampleService;
import com.baidu.gcrm.data.service.IFileReadRecordService;
import com.baidu.gcrm.data.service.IFileTimerService;
import com.baidu.gcrm.data.service.IPositionDataSampleService;
import com.baidu.gcrm.resource.position.service.IPositionService;

@Service("fileTimerServiceImpl")
@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
public class FileTimerServiceImpl implements IFileTimerService {
	
	@Autowired
	IAdSolutionContentService contentService;
	
	@Autowired
	IPositionService positionService;
	
	@Autowired
	IFileReadRecordService fileRecordService;
	
	@Autowired
	IADContentDataSampleService contentSampleService;
	
	@Autowired
	IPositionDataSampleService positionSampleService;
	
	@Autowired
	@Qualifier("ADContentGenerator")
	FileGenerate contentGenerator;
	
	@Autowired
	@Qualifier("PositionGenerator")
	FileGenerate positionGenerator;
	
	private static final String FILE_TYPE = "reportads.txt";

	@Override
	public void generateADContentFileEveryFiveMinutes() {
		List<ADContent> contents = contentService.findPublishValidADContents();
		contentGenerator.generateFile(contents);
		contentGenerator.generateMD5File();
	}

	@Override
	public void generatePositionFileEveryFiveMinutes() {
		List<PositionBean> positions = positionService.getAllPositions();
		positionGenerator.generateFile(positions);
		positionGenerator.generateMD5File();
	}

	@Override
	public void readAndBackupFileTimer() {
		File directory = new File(GcrmConfig.getLocalTempDir());
		File[] files = GcrmFileUtils.getNotMd5Files(directory);
		if (files == null || files.length == 0) {
			return;
		}
		for (File file : files) {
			String filename = StringUtils.trimToEmpty(file.getName());
			File md5File = GcrmHelper.getLocalTempFile(filename + Constants.MD5_EXTENSION);
			boolean hasRead = fileRecordService.checkIfHasRead(filename);
			try {
				if (hasRead || file.length()==0L) {
					GcrmFileUtils.backupFile(file);
					deleteReadFiles(file, md5File);
					continue;
				}
				GcrmFileUtils.checkFileIntegrity(file, md5File);
				// read from file and save sample
				if (isContentFile(filename)) {
					contentSampleService.readFromFileAndSave(file);
				} else {
					positionSampleService.readFromFileAndSave(file);
				}
				GcrmFileUtils.backupFile(file);
                deleteReadFiles(file, md5File);
            } catch (CRMRuntimeException e) {
                LoggerHelper.err(getClass(), "读取ecom文件{}更新统计数据时出错，此文件数据回滚", file.getName(), e);
            } catch (Exception e1) {
                LoggerHelper.err(getClass(), "操作ecom文件{}时发生异常,跳过继续处理下个文件", file.getName(), e1);
            }
        }
	}

	private void deleteReadFiles(File file, File md5File) {
		List<File> readFiles = new ArrayList<File>();
		readFiles.add(file);
		readFiles.add(md5File);
		GcrmFileUtils.deleteFiles(readFiles);
	}
	
	private boolean isContentFile(String filename) {
		return filename.contains(FILE_TYPE);
	}
}
