package com.baidu.gcrm.common.auth;


public class ThreadLocalInfo {
	private static final ThreadLocal<Long> uuid = new ThreadLocal<Long>();
	
	public static Long getThreadUuid() {
		return uuid.get();
	}

	public static void setThreadUuid(Long uid) {
		uuid.set(uid);
	}
}
