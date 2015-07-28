package com.baidu.gcrm;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.common.uc.RegUser;
import com.baidu.gcrm.common.uc.RegUserResponse;
import com.baidu.gcrm.common.uc.UpdUserResponse;
import com.baidu.gcrm.common.uc.service.LoginService;
import com.baidu.gcrm.common.uc.service.UcAcctService;

@Ignore
public class HessianTest extends BaseTestContext{
	@Autowired
	private UcAcctService ucAcctService;
	
	@Autowired
	private LoginService ucAcctExtService;
	
	@Test
	@Ignore
	public void regUser(){
		RegUser user = new RegUser();
		user.setUcname("anhuantest");
		user.setUcpwd("Crm888");
		user.setAppid(240);
		user.setUtype("INNER");
		
		RegUserResponse res = ucAcctService.regUser(user);
		
		System.out.println(res);
		Assert.assertNotNull(res);
		Assert.assertEquals(res.getUcid(), Long.valueOf(0));
	}
	
	@Test
	public void updAcct(){
		/*UpdUser user = new UpdUser();
		user.setUcid(6255995l);
		user.setAllowlogin(0);
		user.setUnitid(66);
		user.setRealname("anhuan");
		user.setEmail("anhuan@baidu.com");
		user.setPosition("39604");
		user.setAppid(240);
		user.setPhone("123");*/
		
		UpdUserResponse res = ucAcctExtService.setAllow(6255995l, 1);
		
		System.out.println(res);
		System.out.println(res.getMsg());
	}
	
	
}
