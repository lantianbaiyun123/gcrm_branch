package com.baidu.gcrm.common.uc.service;

import com.baidu.gcrm.common.uc.UpdUserResponse;

public interface LoginService {
	
	/**
	 * 修改用户是否可以登录的状态
	 * @param ucid
	 * @param isAllow 0,not allowed;1,allowed
	 * @return
	 */
	public UpdUserResponse setAllow(long ucid, int isAllow);
	
	public UpdUserResponse chgPwdByUcid(long ucid, String newPwd);
}
