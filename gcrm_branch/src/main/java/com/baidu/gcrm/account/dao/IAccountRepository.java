package com.baidu.gcrm.account.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.account.model.Account.AccountStatus;
import com.baidu.gcrm.account.model.Account.AccountType;

public interface IAccountRepository extends JpaRepository<Account, Long> {

	@Modifying
	@Query("update Account a set a.status = ?1 where a.ucid = ?2")
	void updateAccountStatus(AccountStatus status, Long ucid);
	
	Account findByUcid(Long ucid);
	
	List<Account> findByCustomerNumber(Long customerNumber);
	
	List<Account> findByCustomerNumberAndType(Long customerNumber, AccountType accountType);
	
	List<Account> findByCustomerNumberAndStatus(Long customerNumber, AccountStatus status);
	
	List<Account> findByTypeAndStatus(AccountType accountType, AccountStatus status);
	
	List<Account> findByNameLikeAndTypeAndStatus(String name,AccountType type,AccountStatus status,Pageable page);
	
	Account findByName(String name);
	
	Account findByIdAndEmailAndVerifyCode(Long accountId, String email, String code);
	
	@Query("Select type From Account where ucid = ?1")
	public AccountType findTypeByUcId(Long ucid);
	
	@Query("From Account where customerNumber is not null and status = ?1")
    public List<Account> findByCustomerIsNotNullAndStatus(AccountStatus status);
	
}
