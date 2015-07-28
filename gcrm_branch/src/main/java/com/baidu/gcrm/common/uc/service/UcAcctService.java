package com.baidu.gcrm.common.uc.service;

import com.baidu.gcrm.common.uc.RegUser;
import com.baidu.gcrm.common.uc.RegUserResponse;

public interface UcAcctService {
	/**
	 * 注册新的用户信息
	 * 
	 * @param u
	 *            用户信息，用户的Email需要调用setEmail1的方法
	 * @return
	 */
	public RegUserResponse regUser(RegUser u);
	
	public Integer getIdByName(String ucname);
}
