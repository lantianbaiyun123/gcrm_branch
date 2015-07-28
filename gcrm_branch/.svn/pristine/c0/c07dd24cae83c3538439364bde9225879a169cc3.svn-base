package com.baidu.gcrm.bpm.security;


public class GwfpAuthorizationOutHandler extends CommonAuthorizationOutHandler{
	@Override
	protected boolean isPasswordEncode() {
		return true;
	}
	
	@Override
	protected String getTokenTag() {
		return "AuthenticationToken";
	}

	@Override
	protected String getUserNameTag() {
		return "gwfpUserName";
	}

	@Override
	protected String getPasswordTag() {
		return "gwfpUserPassword";
	}

	@Override
	protected String getLoginUserTag() {
		return "currentLoginUserName";
	}
}