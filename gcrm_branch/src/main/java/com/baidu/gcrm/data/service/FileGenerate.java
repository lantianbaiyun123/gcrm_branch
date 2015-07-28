package com.baidu.gcrm.data.service;

import java.util.List;

import com.baidu.gcrm.data.bean.FileGenPair;

/**
 * 为了生成文件而提供的服务
 * 
 */
public interface FileGenerate {

	/**
	 * 产生数据文件
	 * 
	 * @param records 
	 * @return 返回当前文件名和远程存储的文件名（可能带有相对路径）的一个映射
	 * @throws Exception 任何错误都会抛出
	 */
	FileGenPair generateFile(List<?> records);

	/**
	 * 产生md5文件
	 * 
	 * @return 返回当前文件名和远程存储的文件名（可能带有相对路径）的一个映射
	 * @throws Exception 任何错误都会抛出
	 */
	FileGenPair generateMD5File();

}
