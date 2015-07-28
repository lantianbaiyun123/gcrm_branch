package com.baidu.gcrm.account.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.dao.IAccountRepository;
import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.account.model.Account.AccountStatus;
import com.baidu.gcrm.account.model.Account.AccountType;
import com.baidu.gcrm.account.vo.AccountVO;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.uc.RegUser;
import com.baidu.gcrm.common.uc.RegUserResponse;
import com.baidu.gcrm.common.uc.UpdUserResponse;
import com.baidu.gcrm.common.uc.UserSecureResponse;
import com.baidu.gcrm.common.uc.service.LoginService;
import com.baidu.gcrm.common.uc.service.UcAcctService;
import com.baidu.gcrm.common.uc.service.UserSecureService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;

@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private IAccountRepository accountDao;
	// UC Hessian 账号相关接口
	@Autowired
	private UcAcctService ucAcctService;
	// UC Hessian 登陆相关接口
	@Autowired
	private LoginService loginService;
	// UC安全中心 Hessian 相关接口
	@Autowired
	private UserSecureService userSecureService;
	// UC挂接时的appId
	@Value("#{appproperties['cas.appid']}")
	private int appId;
	// UC注册时默认密码
	@Value("#{appproperties['cas.defaultPwd']}")
	private String defaultPwd;
	// UC返回的错误代码
	@Value("#{appproperties['cas.errno']}")
	private String errno;
	// UC安全中心绑定邮箱的密钥
	@Value("#{appproperties['uc.usersecureinfo.key']}")
	private String secureKey;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public AccountVO sendVerifyCode(Account account) {
		AccountVO accountVO = new AccountVO();
		String verifyCode = genVerifyCode();
		account.setVerifyCode(verifyCode);
		account.setStatus(AccountStatus.UNVERIFY);
		// 保存账号邮箱与验证码的关系
		save(account);
		accountVO.setEmail(account.getEmail());
		accountVO.setAccountId(account.getId());
		accountVO.setVerifyCode(verifyCode);
		accountVO.setStatus(account.getStatus().toString());
		return accountVO;
	}
	
	@Override
	public boolean verifyEmailCode(Long accountId, String email, String code) {
		Account account = accountDao.findByIdAndEmailAndVerifyCode(accountId, email, code);
		return null == account ? false : true;
	}
	
	@Override
	public String updateAfterVerfiy(Account account) {
		account = findById(account.getId());
		account.setStatus(AccountStatus.VERIFIED);
		save(account);
		return account.getStatus().toString();
	}
	
	private String genVerifyCode(){
		Random random = new Random();
		StringBuilder codeBuilder = new StringBuilder();
		for(int i = 0; i < 6; ++i){
			codeBuilder.append(random.nextInt(10));
		}
		return codeBuilder.toString();
	}
	
	@Override
	public void regAccount(Account account) throws CRMBaseException{
		RegUser user = generateRegUser(account);
		RegUserResponse regUserResponse = ucAcctService.regUser(user);
		Long result = regUserResponse.getResult();
		if (!responseSuccess(result.longValue())) {
			throw new CRMBaseException(getErrorCode(result));
		}
		account.setUcid(regUserResponse.getUcid());
		save(account);
	}
	
	@Override
	public void bindUCMail(Account account) throws CRMBaseException{
		// 绑定UC安全中心邮箱（为了以后的密码修改、找回密码）
		UserSecureResponse userSecureResponse = userSecureService.bindEmailFromOuterReg(account.getUcid(), account.getEmail(), secureKey);
		Long errno = userSecureResponse.getStatus();
		if (!responseSuccess(errno)) {
			throw new CRMBaseException(getErrorCode(errno));
		}
	}

	private RegUser generateRegUser(Account account) {
		RegUser user = new RegUser();
		user.setUcname(account.getName());
		user.setAppid(appId);
		user.setUtype(account.getType().name());
		System.out.println("[AccountServiceImpl]    account type :   "+account.getType().toString());
		System.out.println("[AccountServiceImpl]    set password :   "+account.getPwd());
		// 外部用户使用默认密码，内部用户自己设置密码
		if (account.getType() != AccountType.INNER) {
			user.setUcpwd(defaultPwd);
		}else {
			user.setUcpwd(account.getPwd());
		}
		user.setEmail(account.getEmail());
		user.setAllowlogin(account.getStatus().ordinal());
		return user;
	}

	private Long getErrorCode(Long err) {
		if (errno.contains(err.toString())) {
			return err;
		}
		return -1l;
	}
	
	private boolean responseSuccess(Long errCode) {
		return errCode == 0l;
	}

	@Override
	public void save(Account account) {
		accountDao.save(account);
		accountDao.flush();
	}

	@Override
	public Account findByUcid(Long ucid) {
		return accountDao.findByUcid(ucid);
	}
	
	@Override
	public AccountType findAccountTypeByUcId(Long ucid) {
		return accountDao.findTypeByUcId(ucid);
	}
	
	@Override
	public List<AccountVO> findByCustomerNumberAndAccountType(Long customerNumber, AccountType accountType) {
		List<AccountVO> accountVOList = new ArrayList<AccountVO>();
		List<Account> accountList = null;
		if (null != accountType) {
			accountList = accountDao.findByCustomerNumberAndType(customerNumber, accountType);
		}else {
			accountList = accountDao.findByCustomerNumber(customerNumber);
		}
		for (Account account : accountList) {
			AccountVO accountVO = new AccountVO();
			User creatorUser = userService.findByUcid(account.getCreateOperator());
			String creator = "";
			if (null != creatorUser) {
				creator = creatorUser.getRealname();
			}
			accountVO.setCreator(creator);
			accountVO.setCreateDate(account.getCreateTime());
			accountVO.setAccountId(account.getId());
			accountVO.setEmail(account.getEmail());
			accountVO.setUsername(account.getName());
			accountVO.setStatus(account.getStatus().toString());
			accountVOList.add(accountVO);
		}
		return accountVOList;
	}
	
	@Override
	public List<Account> findByCustomerNumberAndAccountStatus(Long customerNumber, AccountStatus status){
	    if (null != status) {
            return accountDao.findByCustomerNumberAndStatus(customerNumber, status);
        }else {
            return accountDao.findByCustomerNumber(customerNumber);
        }
	}

	@Override
	public void updateAccountStatus(AccountStatus status, Account account) throws CRMBaseException {
		// 只有禁用和启用状态可以更改
		if (!status.equals(AccountStatus.ENABLE) && !status.equals(AccountStatus.DISABLE)) {
			return;
		}
		UpdUserResponse updUserResponse = loginService.setAllow(account.getUcid(), status.ordinal());
		Long result = updUserResponse.getErr_no();
		if (!responseSuccess(result.longValue())) {
			throw new CRMBaseException(getErrorCode(result));
		}
		account.setStatus(status);
		accountDao.save(account);
	}
	
	@Override
	public void deleteAccount(Long accountId) throws CRMBaseException {
		Account account = accountDao.findOne(accountId);
		UpdUserResponse updUserResponse = loginService.setAllow(account.getUcid(), AccountStatus.DISABLE.ordinal());
		Long result = updUserResponse.getErr_no();
		if (!responseSuccess(result.longValue())) {
			throw new CRMBaseException(getErrorCode(result));
		}
		accountDao.delete(account);
	}
	
	@Override
	public void changeAccountPwd(Long ucId, String newPwd)
			throws CRMBaseException {
		UpdUserResponse updUserResponse = loginService.chgPwdByUcid(ucId, newPwd);
		Long result = updUserResponse.getErr_no();
		if (!responseSuccess(result.longValue())) {
			throw new CRMBaseException(getErrorCode(result));
		}
	}
	
	@Override
	public List<Account> findByNameLike(String name) {
		PageRequest request = new PageRequest(0, 5);
		return accountDao.findByNameLikeAndTypeAndStatus("%" + name + "%",
				AccountType.INNER,AccountStatus.ENABLE,request);
	}
	
	@Override
	public Account findByName(String name) {
		return accountDao.findByName(name);
	}
	
	@Override
	public Long findUcIdByUcName(String ucname) {
		if (null == ucAcctService.getIdByName(ucname)) {
			return null;
		}
		return Long.parseLong(ucAcctService.getIdByName(ucname).toString());
	}

	@Override
	public Account findById(Long id) {
		return accountDao.findOne(id);
	}

	@Override
	public Map<Long, String> getIdNameMap() {
		Map<Long, String> accountMap = new HashMap<Long, String>();
		List<Account> accounts = accountDao.findAll();
		for (Account account : accounts) {
			accountMap.put(account.getUcid(), account.getName());
		}
		return accountMap;
	}

	@Override
	public List<Account> findAll() {
		return accountDao.findAll();
	}
	
	@Override
	public List<Account> findByCustomerNumberNotNullAndStatus(AccountStatus status){
	    return accountDao.findByCustomerIsNotNullAndStatus(status);
	}
	
    public void setAccountDao(IAccountRepository accountDao) {
        this.accountDao = accountDao;
    }
}
