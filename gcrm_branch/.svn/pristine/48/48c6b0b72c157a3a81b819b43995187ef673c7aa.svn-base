package com.baidu.gcrm.account.service;

import java.util.List;
import java.util.Map;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.account.model.Account.AccountStatus;
import com.baidu.gcrm.account.model.Account.AccountType;
import com.baidu.gcrm.account.vo.AccountVO;
import com.baidu.gcrm.common.exception.CRMBaseException;

public interface IAccountService {
	
	/**
	 * 注册账号到UC，并保存本地库
	 * @param account
	 * @return
	 * @throws CRMBaseException
	 */
	void regAccount(Account account) throws CRMBaseException;
	
	/**
	 * 绑定UC安全中心的邮箱，为了以后的找回密码、修改密码
	 * @param account
	 */
	void bindUCMail(Account account) throws CRMBaseException;
	
	/**
	 * 生成邮箱验证码，并保存账号的邮箱和验证码的关系
	 * @param accountVO
	 * @return
	 */
	public AccountVO sendVerifyCode(Account account);
	
	/**
	 * 校验验证码是否正确
	 * @param customerNum
	 * @param email
	 * @param code
	 * @return
	 */
	public boolean verifyEmailCode(Long accountId, String email, String code);
	
	/**
	 * 验证码校验成功后更新状态
	 * @param account
	 * @return
	 */
	public String updateAfterVerfiy(Account account);
	
	/**
	 * 更新账号状态（启用/禁用）
	 * @param status
	 * @param account
	 * @throws CRMBaseException
	 */
	public void updateAccountStatus(AccountStatus status, Account account) throws CRMBaseException;
	
	/**
	 * 删除账号
	 * @param account
	 */
	public void deleteAccount(Long accountId) throws CRMBaseException;
	
	void save(Account account);
	
	Account findByUcid(Long ucid);
	
	/**
	 * 根据UCID查找账号类型
	 * @param ucid
	 * @return
	 */
	public AccountType findAccountTypeByUcId(Long ucid);
	
	Account findById(Long id);
	
	Long findUcIdByUcName(String ucname);
	
	/**
	 * 根据客户编号和账号类型查询账号
	 * @param customerNumber
	 * @param accountType 为null时，账号类型不做限制
	 * @return
	 */
	List<AccountVO> findByCustomerNumberAndAccountType(Long customerNumber, AccountType accountType);
	
	void changeAccountPwd(Long ucId, String newPwd) throws CRMBaseException;

	List<Account> findByNameLike(String name);
	
	Account findByName(String name);

	Map<Long, String> getIdNameMap();
	
	List<Account> findAll();
	
	/**
	 * 根据客户编号和账号状态查询账号---公告管理使用 查询 广告主下属的所有账号
	 */
	List<Account> findByCustomerNumberAndAccountStatus(Long customerNumber, AccountStatus status);
	/**
     * 根据状态查询所有广告主账号---公告管理使用 查询所有广告主的所有有效账号
     */
    List<Account> findByCustomerNumberNotNullAndStatus(AccountStatus status);
}
