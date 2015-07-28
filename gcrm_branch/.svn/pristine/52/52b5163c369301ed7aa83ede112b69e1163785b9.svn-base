package com.baidu.gcrm.common.log;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.baidu.gcrm.common.exception.CRMRuntimeException;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: www.baidu.com
 * </p>
 * 
 * @author fangjianping
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class LoggerHelper {
	/** DOCUMENT ME! */
	public static final String LOG_SPERATER = "\n---------------------------------\n";

	public static final String OPERATION_LOG_NAME = "operationLog";
	public static final String WSSERVICE_LOG_NAME = "wsserviceLog";
	public static final String FTP_LOG_NAME = "ftpLog";

	/**
	 * 打印起始时间
	 */
	public static final long TIME_LENGTH = 1000L;
	/**
	 * 字符串最大长度
	 */
	public static final int MAX_STR_LENGTH = 100;

	/**
	 * Creates a new LoggerHelper object.
	 */
	public LoggerHelper() {
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param o
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static String buildDebugMessage(Object o) {
		StringBuilder sb = new StringBuilder(LOG_SPERATER);
		sb.append(JSONObject.fromObject(o).toString());
		sb.append(LOG_SPERATER);

		return sb.toString();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param currentClass
	 * @param message
	 *            写入的日志消息
	 */
	public static void debug(Class currentClass, String message, Object... args) {
		Logger log = LoggerFactory.getLogger(currentClass);
		log.debug(message, args);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param currentClass
	 * @param message
	 *            写入的日志消息
	 * @param t
	 *            记录抛出的异常信息。
	 */
	public static void debug(Class currentClass, String message, Throwable t) {
		Logger log = LoggerFactory.getLogger(currentClass);
		log.debug(message, t);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param currentClass
	 * @param message
	 *            写入的日志消息
	 */
	public static void info(Class currentClass, String message, Object... args) {
		Logger log = LoggerFactory.getLogger(currentClass);
		log.info(message, args);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param currentClass
	 * @param message
	 *            写入的日志消息
	 * @param t
	 *            记录抛出的异常信息。
	 */
	public static void info(Class currentClass, String message, Throwable t) {
		Logger log = LoggerFactory.getLogger(currentClass);
		log.info(message, t);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param currentClass
	 * @param message
	 *            写入的日志消息
	 */
	public static void warn(Class currentClass, String message, Object... args) {
		Logger log = LoggerFactory.getLogger(currentClass);
		log.warn(message, args);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param currentClass
	 * @param message
	 *            写入的日志消息
	 * @param t
	 *            记录抛出的异常信息。
	 */
	public static void warn(Class currentClass, String message, Throwable t) {
		Logger log = LoggerFactory.getLogger(currentClass);
		log.warn(message, t);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param currentClass
	 * @param message
	 *            写入的日志消息
	 */
	public static void err(Class currentClass, String message, Object... args) {
		Logger log = LoggerFactory.getLogger(currentClass);
		log.error(message, args);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param currentClass
	 * @param message
	 *            写入的日志消息
	 * @param t
	 *            记录抛出的异常信息。
	 */
	public static void err(Class currentClass, String message, Throwable t) {
		Logger log = LoggerFactory.getLogger(currentClass);
		if (t instanceof CRMRuntimeException) {
			log.error(message + " 失败原因：" + t.getMessage());
		} else {
			log.error(message, t);
		}

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 * @param message
	 *            写入的日志消息
	 */
	public static void err(String name, String message, Object... args) {
		Logger log = LoggerFactory.getLogger(name);
		log.error(message + "\r\n", args);
	}
	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 * @param message
	 *            写入的日志消息
	 */
	public static void err(String name, String message, Throwable t) {

		Logger log = LoggerFactory.getLogger(name);
		if (t instanceof CRMRuntimeException) {
			log.error(message + " 失败原因：" + t.getMessage());
		} else {
			log.error(message, t);
		}

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 * @param message
	 *            写入的日志消息
	 */
	public static void warn(String name, String message, Object... args) {
		Logger log = LoggerFactory.getLogger(name);
		log.warn(message + "\r\n", args);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 * @param message
	 *            写入的日志消息
	 */
	public static void info(String name, String message, Object... args) {
		Logger log = LoggerFactory.getLogger(name);
		log.info(message + "\r\n", args);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 * @param message
	 *            写入的日志消息
	 */
	public static void debug(String name, String message, Object... args) {
		Logger log = LoggerFactory.getLogger(name);
		log.debug(message + "\r\n", args);
	}

	/**
	 * 记录操作日志
	 * 
	 * @param name
	 * @param message
	 *            写入的日志消息
	 */
	public static void logOperation(String message, Object... args) {
		Logger log = LoggerFactory.getLogger(OPERATION_LOG_NAME);
		if (SecurityContextHolder.getContext() != null) {
			if (SecurityContextHolder.getContext().getAuthentication() != null) {
				if (SecurityContextHolder.getContext().getAuthentication().getDetails() != null) {
					User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
					if (user != null) {
						log.info("操作人员" + user.getUsername() + "\r\n");
					}
				}
			}
		}

		log.info(message + "\r\n", args);
	}
	/**
	 * 记录ws日志
	 * 
	 * @param name
	 * @param message
	 *            写入的日志消息
	 */
	public static void logWsService(String message, Object... args) {
		Logger log = LoggerFactory.getLogger(WSSERVICE_LOG_NAME);
		log.info(message + "\r\n", args);
	}
	/**
	 * 记录ws日志
	 * 
	 * @param name
	 * @param message
	 *            写入的日志消息
	 */
	public static void logFtpError(String message, Object... args) {
		Logger log = LoggerFactory.getLogger(FTP_LOG_NAME);
		log.error(message, args);
	}
	/**
	 * 记录ws日志
	 * 
	 * @param name
	 * @param message
	 *            写入的日志消息
	 */
	public static void logFtpInfo(String message, Object... args) {
		Logger log = LoggerFactory.getLogger(FTP_LOG_NAME);
		log.info(message, args);
	}

	/**
	 * 记录性能日志
	 * 
	 * @param name
	 * @param message
	 *            写入的日志消息
	 */
	public static void logProfiling(long ms, String className, String methodName, String param, String result) {
		if (ms < TIME_LENGTH) {
			return;
		}

		if (param.length() > MAX_STR_LENGTH) {
			param = param.substring(0, MAX_STR_LENGTH);
		}
		if (result.length() > MAX_STR_LENGTH) {
			result = result.substring(0, MAX_STR_LENGTH);
		}

		Logger log = LoggerFactory.getLogger("ProfilingInterceptorLog");
		log.info("start[0] time[{}] tag[{}.{}] 参数: {} 结果: {}", new Object[]{ms, className, methodName, param, result});
	}
}
