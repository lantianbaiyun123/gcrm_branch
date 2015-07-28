package com.baidu.gcrm.common.uc.service;

import com.baidu.gcrm.common.uc.UserSecureResponse;

public interface UserSecureService {
	public UserSecureResponse bindEmailFromOuterReg(Long ucid, String email, String key);
}
