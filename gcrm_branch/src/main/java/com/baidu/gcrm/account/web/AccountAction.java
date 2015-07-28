package com.baidu.gcrm.account.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.account.model.Account.AccountStatus;
import com.baidu.gcrm.account.model.Account.AccountType;
import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.account.vo.AccountVO;
import com.baidu.gcrm.account.vo.RegisterMailVO;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.customer.web.helper.AjaxResult;
import com.baidu.gcrm.customer.web.helper.CustomerType;
import com.baidu.gcrm.mail.MailHelper;

@Controller
@RequestMapping("/account")
public class AccountAction  extends ControllerHelper {
	
	@Value("#{appproperties['cas.defaultPwd']}")
	public String defaultPwd;
	
	/**
	 * 客户数据平台地址
	 */
	@Value("#{appproperties['app.customer.url']}")
	public String appCustUrl;
	
	private Logger logger = LoggerFactory.getLogger(AccountAction.class);

	@Autowired
	private IAccountService accountService;
	@Autowired
	private ICustomerService customerService;
	
	@RequestMapping("/view/{customerNumber}")
	public String view(@PathVariable("customerNumber")Long customerNumber, Model model){
        model.addAttribute("accounts", accountService.findByCustomerNumberAndAccountType(customerNumber, AccountType.OUTER));
        return "/widget/detailAccounts";
    }
	
	/**
	 * 编辑账号
	 * @param accountVO
	 * @return
	 */
	@RequestMapping("/edit/{accountId}")
	@ResponseBody
	public JsonBean<AccountVO> editAccount(@PathVariable("accountId") Long accountId){
		AccountVO resultVo = new AccountVO();
		if (null == accountId) {
			return JsonBeanUtil.convertBeanToJsonBean(null, "account id can not be empty!",JsonBeanUtil.CODE_ERROR_MESSAGE); 
		}
		Account account = accountService.findById(accountId);
		resultVo.setAccountId(account.getId());
		Customer customer = customerService.findByCustomerNumber(account.getCustomerNumber());
		resultVo.setCustomerName(customer.getCompanyName());
		resultVo.setCustomerNumber(customer.getCustomerNumber());
		resultVo.setEmail(account.getEmail());
		resultVo.setStatus(account.getStatus().toString());
		resultVo.setVerifyCode(account.getVerifyCode());
		resultVo.setUsername(account.getName());
		return JsonBeanUtil.convertBeanToJsonBean(resultVo);
	}
	
	/**
	 * 发送验证码
	 * @param accountVO
	 * @return
	 */
	@RequestMapping("/sendVerifyCode")
	@ResponseBody
	public JsonBean<AccountVO> sendVerifyCode(@RequestBody AccountVO accountVO){
		AccountVO resultVo = accountVO;
//		String verifyCode = accountVO.getVerifyCode();
		// 未发送验证码时
		if (null == accountVO.getAccountId() && null == accountVO.getVerifyCode()) {
			Account account = new Account();
			generatePropertyForCreate(account);
			account.setEmail(accountVO.getEmail());
			account.setCustomerNumber(accountVO.getCustomerNumber());
			resultVo = accountService.sendVerifyCode(account);
//			verifyCode = resultVo.getVerifyCode();
		}
		// 重新发送验证码
		else if(null != accountVO.getAccountId()){
			Account account = accountService.findById(accountVO.getAccountId());
			if (null != account) {
				generatePropertyForCreateUpdate(account);
				account.setEmail(accountVO.getEmail());
				resultVo = accountService.sendVerifyCode(account);
//				verifyCode = resultVo.getVerifyCode();
			}
		}else {
			return JsonBeanUtil.convertBeanToJsonBean(null, MessageHelper.getMessage("account.register.sendVerifyCode.error", currentLocale),JsonBeanUtil.CODE_ERROR_MESSAGE);
		}
		// 发送验证邮件
		RegisterMailVO regMailVO = new RegisterMailVO();
		regMailVO.setVerifyCode(resultVo.getVerifyCode());
		String operator = RequestThreadLocal.getLoginUser().getRealname();
		regMailVO.setOperator(operator);
		Set<String> sendTo = new HashSet<String>();
		sendTo.add(resultVo.getEmail());
		regMailVO.setSendTo(sendTo);
		MailHelper.sendRegisterMailVerifyMail(regMailVO, currentLocale);
		return JsonBeanUtil.convertBeanToJsonBean(resultVo);
	}
	
	/**
	 * 验证邮箱验证码
	 * @param accountVO
	 * @return
	 */
	@RequestMapping("/verifyEmail")
	@ResponseBody
	public JsonBean<AccountVO> verifyEmail(@RequestBody AccountVO accountVO){
		boolean result = false;
		result = accountService.verifyEmailCode(accountVO.getAccountId(), accountVO.getEmail(), accountVO.getVerifyCode());
		// 验证失败
		if (!result) {
			return JsonBeanUtil.convertBeanToJsonBean(null, MessageHelper.getMessage("account.register.verifyEmail.error", currentLocale),JsonBeanUtil.CODE_ERROR_MESSAGE);
		}
		// 验证成功，更新状态
		Account account = new Account();
		account.setId(accountVO.getAccountId());
		generatePropertyForUpdate(account);
		accountVO.setStatus(accountService.updateAfterVerfiy(account));
		return JsonBeanUtil.convertBeanToJsonBean(accountVO);
	}
	
	/**
	 * 保存账号
	 * @param accountVO
	 * @return
	 */
	@RequestMapping("/saveAccount")
	@ResponseBody
	public JsonBean<AccountVO> saveAccount(@RequestBody AccountVO accountVO){
		Account account = new Account();
		try {
			Long customerNumber = accountVO.getCustomerNumber();
			Customer customer = customerService.findByCustomerNumber(customerNumber);
			if (customer == null) {
				throw new CRMBaseException(-2l);
			}
			generatePropertyForCreate(account);
			account.setId(accountVO.getAccountId());
			account.setCustomerNumber(customerNumber);
			account.setName(accountVO.getUsername());
			account.setEmail(accountVO.getEmail());
			account.setStatus(AccountStatus.ENABLE);
			setAccountType(account, customer.getCustomerType().ordinal());
			accountService.regAccount(account);
		} catch (CRMBaseException e) {
			logger.error(MessageHelper.getMessage("account.register.error." + e.getErrorNo(), currentLocale), e);
			return JsonBeanUtil.convertBeanToJsonBean(null, MessageHelper.getMessage("account.register.error." + e.getErrorNo(), currentLocale),JsonBeanUtil.CODE_ERROR_MESSAGE);
		}
		// 绑定UC用户的安全中心邮箱
		try {
			accountService.bindUCMail(account);
		} catch (CRMBaseException e) {
			logger.error(e.getMessage(), e);
			logger.error(MessageHelper.getMessage("account.register.usersecureinfo.error." + e.getErrorNo(), currentLocale));
		}
		
		// 发送注册成功邮件
		RegisterMailVO regMailVO = new RegisterMailVO();
		regMailVO.setUsername(account.getName());
		regMailVO.setPassword(defaultPwd);
		String operator = RequestThreadLocal.getLoginUser().getRealname();
		regMailVO.setOperator(operator);
		Set<String> sendTo = new HashSet<String>();
		sendTo.add(account.getEmail());
		regMailVO.setSendTo(sendTo);
		regMailVO.setUrl(appCustUrl);
		MailHelper.sendRegisterSuccessMail(regMailVO, currentLocale);
		return JsonBeanUtil.convertBeanToJsonBean(accountVO);
	}
	
	/**
	 * 帐户列表
	 * @param customerNumber
	 * @return
	 */
	@RequestMapping("/query/{customerNumber}")
	@ResponseBody
	public JsonBean<List<AccountVO>> query(@PathVariable("customerNumber") Long customerNumber){
		List<AccountVO> accountVOList = accountService.findByCustomerNumberAndAccountType(customerNumber, null);
		return JsonBeanUtil.convertBeanToJsonBean(accountVOList);
	}
	
	/**
	 * 更改账号状态
	 * @param ucid
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateState")
	@ResponseBody
	public JsonBean updateState(@RequestParam("accountId")Long accountId, @RequestParam("status")String status){
	    try {
	    	Account account = accountService.findById(accountId);
	    	AccountStatus accountStatus = null;
	    	generatePropertyForUpdate(account);
	    	if (status.equals(AccountStatus.ENABLE.toString())) {
	    		accountStatus = AccountStatus.DISABLE;
			}else {
				accountStatus = AccountStatus.ENABLE;
			}
	    	accountService.updateAccountStatus(accountStatus, account);
	    } catch (CRMBaseException e) {
	    	logger.error(e.getMessage(), e);
	    	return JsonBeanUtil.convertBeanToJsonBean(null, MessageHelper.getMessage("account.register.error." + e.getErrorNo(), currentLocale),JsonBeanUtil.CODE_ERROR_MESSAGE);
	    }
	    return JsonBeanUtil.convertBeanToJsonBean(null);
	}
	
	/**
	 * 删除账号
	 * @param ucid
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{accountId}")
	@ResponseBody
	public JsonBean deleteAccount(@PathVariable("accountId")Long accountId){
	    try {
	    	accountService.deleteAccount(accountId);
	    } catch (CRMBaseException e) {
	    	logger.error(e.getMessage(), e);
	    	return JsonBeanUtil.convertBeanToJsonBean(null, MessageHelper.getMessage("account.register.error." + e.getErrorNo(), currentLocale),JsonBeanUtil.CODE_ERROR_MESSAGE);
	    }
	    return JsonBeanUtil.convertBeanToJsonBean(null);
	}
	
	/**
	 * 设置账号类型
	 * @param account
	 * @param customerType
	 */
	private void setAccountType(Account account, Integer customerType) {
		if (customerType != null && customerType == CustomerType.offline.getValue()) {
			account.setType(AccountType.AGENT);
		} else {
			account.setType(AccountType.OUTER);
		}
	}
	
	@RequestMapping("/register")
	@ResponseBody
	@Deprecated
	public AjaxResult register(Account account, Model model) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			Long customerNumber = account.getCustomerNumber();
			Customer customer = customerService.findByCustomerNumber(customerNumber);
			if (customer == null) {
				throw new CRMBaseException(-2l);
			}
			// TODO 这里的注释要去掉
			/*generatePropertyForCreate(account);
			setAccountType(account, customer.getBusinessType());
			accountService.regAccount(account);*/
			ajaxResult.setRetBean(account.getName());
			ajaxResult.setSuccess(true);
		} catch (CRMBaseException e) {
			Map<String,String> errors = new HashMap<String, String>();
			errors.put("name", "account.register.error." + e.getErrorNo());
			ajaxResult.setErrors(errors);
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}
}
