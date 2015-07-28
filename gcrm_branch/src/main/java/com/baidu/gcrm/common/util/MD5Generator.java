package com.baidu.gcrm.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import com.baidu.gcrm.common.log.LoggerHelper;


public class MD5Generator {

	private final static char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f'};

	/**
	 * 计算指定文件的MD5值
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String getFileMD5(File file) {
		FileInputStream in = null;
		FileChannel ch = null;
		MessageDigest messageDigest = null;
		try {
			in = new FileInputStream(file);
			ch = in.getChannel();
			MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(byteBuffer);
		} catch (Exception e) {
			LoggerHelper.err(MD5Generator.class, e.getMessage());
			return null;
		} finally {
			try {
				in.close();
				ch.close();
			} catch (IOException e) {
				LoggerHelper.err(MD5Generator.class, e.getMessage());
				return null;
			}
		}
		return toHexString(messageDigest.digest());
	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_CHAR[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_CHAR[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 从指定的MD5文件中读MD5值
	 * 
	 * @param Md5File
	 * @return
	 * @throws IOException
	 */
	public static String readMD5FromFile(File Md5File) {
		try {
			String s = FileUtils.readFileToString(Md5File);
			int index = s.indexOf(" ");
			if (index < 0) {
				return s;
			} else {
				return s.substring(0, index);
			}
		} catch (IOException e) {
			LoggerHelper.err(MD5Generator.class, e.getMessage());
			return "";
		}
	}

	/**
	 * 获取指定文件夹中的md5文件中的md5值
	 * 
	 * @param directory
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> getDirMD5(File directory) {
		Collection<File> md5Files = getMD5Files(directory);
		Map<String, String> md5s = new HashMap<String, String>();
		for (File file : md5Files) {
			String md5 = MD5Generator.readMD5FromFile(file);
			md5s.put(file.getName(), md5);
		}
		return md5s;
	}

	/**
	 * 获取指定目录下的MD5文件列表
	 * 
	 * @param directory
	 * @return
	 */
	private static Collection<File> getMD5Files(File directory) {
		IOFileFilter suffixFileFilter = FileFilterUtils.suffixFileFilter("md5");
		IOFileFilter fileFilter = FileFilterUtils.makeFileOnly(suffixFileFilter);
		Collection<File> files = FileUtils.listFiles(directory, fileFilter, null);
		return files;
	}
}
