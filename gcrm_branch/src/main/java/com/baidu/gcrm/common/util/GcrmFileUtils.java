package com.baidu.gcrm.common.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.StringUtils;

import au.com.bytecode.opencsv.CSVReader;

import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.log.LoggerHelper;

public class GcrmFileUtils {
	
	private final static char SEPRATOR = '\t';
	
	public static File[] getNotMd5Files(File directory) {
		IOFileFilter filter = new SuffixFileFilter(Constants.MD5_EXTENSION);
		FileFilter notFileFilter = new NotFileFilter(filter);
		return directory.listFiles(notFileFilter);
	}
	
	public static void backupFile(File file) throws CRMBaseException {
		File destDir = getDestFileDir();
		try {
			FileUtils.copyFileToDirectory(file, destDir);
		} catch (Exception e) {
			String s = String.format("Error occurs while coping file %s to directory %s", file.getPath(),
					destDir.getPath());
			LoggerHelper.err(GcrmFileUtils.class, s);
			throw new CRMBaseException(e, s);
		}
	}
	
	private static File getDestFileDir() {
		String folderName = DateUtils.getCurrentFormatDate(DateUtils.YYYY_MM);
		String filePath = GcrmConfig.getLocalBackupDir() + folderName;
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	
	public static void deleteFiles(List<File> files) {
        for (File file : files) {
            deleteFile(file);
        }
    }

    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.delete()) {
            LoggerHelper.info(GcrmFileUtils.class, "Delete file: {}", file.getPath());
        } else {
            LoggerHelper.err(GcrmFileUtils.class, "Fail to delete file {}", file.getPath());
        }
    }

	
	public static List<String[]> analyzeFile(File file, int columnCount) throws CRMBaseException {
		List<String[]> datas = new ArrayList<String[]>();
		CSVReader reader = null;
		Reader fr = null;
		try {
			fr = new FileReader(file);
			reader = new CSVReader(fr, SEPRATOR);
			LoggerHelper.info(GcrmFileUtils.class, "Begin to analyze file {}", file.getPath());
			String[] data;
			int line_number = 0;
			while ((data = reader.readNext()) != null) {
				line_number++;
				checkDataNumberByLine(line_number, data, columnCount);
				datas.add(data);
			}
		} catch (IOException e) {
			LoggerHelper.err(GcrmFileUtils.class, "Error occours reading File {}, reason: {}.", file.getPath(), e.getMessage());
			throw new CRMBaseException("IO error occours reading File " + file.getPath() + ", reason: " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(fr);
		}
		return datas;
	}

	private static void checkDataNumberByLine(int line_number, String[] data, int count) throws CRMBaseException {
		if (data.length != count) {
			String s = String.format("Row [%s] has [%s] columns, current row data is %s", line_number, data.length,
					Arrays.toString(data));
			LoggerHelper.err(GcrmFileUtils.class, s);
			throw new CRMBaseException(s);
		} else {
			for (int i = 0; i < data.length; i++) {
				String d = StringUtils.trimToEmpty(data[i]);
				if (StringUtils.EMPTY.equals(d)) {
					LoggerHelper.err(GcrmFileUtils.class, "Row [{}], column [{}] data is empty", line_number, i + 1);
					throw new CRMBaseException("Row [" + line_number + "], column [" + (i + 1) + "] data is empty");
				}
			}
		}
	}
	
	public static void checkFileIntegrity(File file, File Md5File) throws CRMBaseException {
		if (!file.exists()) {
			LoggerHelper.err(GcrmFileUtils.class, "Missing file {}", file.getPath());
			throw new CRMBaseException("Missing file " + file.getPath());
		}
		if (!Md5File.exists()) {
			LoggerHelper.err(GcrmFileUtils.class, "Missing md5 file {}", Md5File.getPath());
			throw new CRMBaseException("Missing md5 file " + Md5File.getPath());
		}
		String md5 = MD5Generator.getFileMD5(file);
		String referenceMD5 = MD5Generator.readMD5FromFile(Md5File).trim();
		LoggerHelper.info(GcrmFileUtils.class, "Calculating file {} md5 value, value: {}", file.getPath(), md5);
		LoggerHelper.info(GcrmFileUtils.class, "Reading md5 from file {}, value: {}", Md5File.getPath(), referenceMD5);
		if (StringUtils.isEmpty(md5) || StringUtils.isEmpty(referenceMD5)) {
			LoggerHelper.err(GcrmFileUtils.class, "Error occurs while getting MD5 value");
			throw new CRMBaseException("Error occurs while getting MD5 value");
		}
		if (!md5.equals(referenceMD5)) {
			String s = String.format("同步到本地的文件[%s]MD5值(%s)与远程文件的MD5值(%s)不一致", file.getName(), md5, referenceMD5);
			LoggerHelper.err(GcrmFileUtils.class, s);
			throw new CRMBaseException(s);
		}
	}
}
