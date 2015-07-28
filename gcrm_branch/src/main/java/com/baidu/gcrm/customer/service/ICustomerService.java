package com.baidu.gcrm.customer.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.model.CustomerApprovalRecord;
import com.baidu.gcrm.customer.web.helper.CustomerBean;
import com.baidu.gcrm.customer.web.helper.CustomerCondition;
import com.baidu.gcrm.customer.web.helper.CustomerDetailView;
import com.baidu.gcrm.customer.web.helper.CustomerListBean;
import com.baidu.gcrm.customer.web.helper.CustomerType;
import com.baidu.gcrm.customer.web.helper.CustomerView;
import com.baidu.gcrm.customer.web.vo.CustomerI18nView;
import com.baidu.gcrm.user.model.User;

public interface ICustomerService {
	void save(Customer customer);
	
	List<Customer> findByCustomerType(CustomerType customerType);
	/**
	 * 
	 * 功能描述:根据客户类型及客户名称匹配客户
	 * findByCustomerTypeAndCompanyName
	 * @创建人:	 chenchunhui01
	 * @创建时间: 	2014年5月28日 下午5:02:15     
	 * @param customerType
	 * @param customerName
	 * @return   
	 * @return List<Customer>  
	 * @exception   
	 * @version
	 */
	 public List<Customer> findByCustomerTypeAndCompanyName(CustomerType customerType,String customerName );
	List<Customer> findByCountryAndLiscenseAndName(Integer countryId,String liscense,String companyName);
	//根据国家ID和公司名称 查询客户列	表
    List<Customer> findByCountryAndName(Integer countryId, String companyName);
   //根據國家ID和運營許可證 查詢客戶列表
    List<Customer> findByCountryAndLiscense(Integer countryId, String liscense);
	
	List<Customer> findByCompanyName(String companyName);
    /**
     * 
     * 功能描述: 根据自定义筛选条件分页查询客户列表
     * findByCondition
     * 创建人:	 chenchunhui01
     * 创建时间: 	2014年5月4日 下午2:58:14     
     * @param condition
     * @param locale
     * @return   
     * @return Page<CustomerListBean>  
     * @exception   
     * @version
     */
	Page<CustomerListBean> findByCondition(CustomerCondition condition, LocaleConstants locale);

	CustomerView findViewById(Long id,LocaleConstants locale);
	 
	public CustomerDetailView findCustomerBaseInfoById(Long id,LocaleConstants locale,boolean isQueryView);
	
	 Customer findById(Long id);

	void update(Customer customer); 

//	void updateWhenApproving(Customer customer);

	void saveContacts(Long customerNumber,List<ContactPerson> contacts);
	
	Customer saveCustomer(Customer customer);
	
	void updateCustomer(Customer customer);
	
	org.springframework.data.domain.Page<Customer> findAll(Pageable page);
	
	Customer findCustomer(Long id);

	Customer findByCustomerNumber(Long customerNumber);
	
	List<Customer> findByCompanyNameLikeAndBusinessTypeNotTop5(String companyName, Integer businessType);
		
	List<Customer> findByAgentCompany(Long id);

	CustomerView customerToView(Customer customer, LocaleConstants locale);
	
	CustomerI18nView customerViewToI18nView(CustomerView customerView,
			LocaleConstants locale);

	List<Customer> findCustomerForAdcontent(String companyName);

	/**
	 * 模糊匹配 广告主名，来提示所有有效的所有客户类型，即 4个CustomerType （公告管理使用）
	 */
	List<Customer> findCustomerForNotice(String companyName);
   /**
    * 
    * 功能描述:
    * findRecordByCustomerId
    * 创建人:	 chenchunhui01
    * 创建时间: 	2014年5月9日 下午5:46:02     
    * @param customerId
    * @param processDefineId
    * @param currentLocale
    * @return
    * @throws CRMBaseException   
    * @return List<CustomerApprovalRecord>  
    * @exception   
    * @version
    */
   public  List<CustomerApprovalRecord> findRecordByCustomerId(Long customerId, String processDefineId,LocaleConstants currentLocale) throws CRMBaseException;
  
   /**
    * 审批操作
    * @author chenchunhui01
    * @param CustomerApprovalRecord
    * @return
    * @throws CRMBaseException 
    */
   public void saveAndCompleteApproval(CustomerApprovalRecord approvalRecord, User operaterUser, LocaleConstants currentLocale) throws CRMBaseException;
   
   /**
   * 
   * 功能描述:
   * findChangeHistoryRecord
   * 创建人:	 chenchunhui01
   * 创建时间: 	2014年5月9日 下午5:46:10     
   * @param materialApplyId
   * @param locale
   * @return
   * @throws CRMBaseException   
   * @return List<Map<String,Object>>  
   * @exception   
   * @version
   */
   public List<Map<String, Object>> findChangeHistoryRecord(String customerId, LocaleConstants locale) throws CRMBaseException;
    /**
     * 
     * 功能描述:客户提交操作
     * submitProcess
     * @创建人:	 chenchunhui01
     * @创建时间: 	2014年6月25日 下午1:38:37     
     * @param customerBean
     * @param operaterUser
     * @throws CRMBaseException   
     * @return void  
     * @exception   
     * @version
     */
   public void  submitProcess(CustomerBean customerBean, User operaterUser )  throws CRMBaseException;

   public Customer discardCoustomerById(Long customerId, User user, LocaleConstants locale, String processDefineId)
        throws Exception;

   public Customer withdrawCoustomerApplyById(Long customerId, User user, LocaleConstants locale, String processDefineId)
        throws Exception;
/**
 * 
 * 功能描述:恢复客户申请操作
 * recoveryCustomerApplyById
 * @创建人:	 chenchunhui01
 * @创建时间: 	2014年5月13日 下午5:57:37     
 * @param customerId
 * @param user
 * @return
 * @throws Exception   
 * @return Customer  
 * @exception   
 * @version
 */
   public Customer recoveryCustomerApplyById(Long customerId, User user)
        throws Exception;
   
   /**
    * 催办邮件
    * @param id
    * @param currentLocale
    */

   public void remindersContentByMail(Long id,LocaleConstants currentLocale);
   /**
    * 
    * 功能描述:
    * isHasAddplusRole
    * @创建人:	 chenchunhui01
    * @创建时间: 	2014年5月19日 上午11:02:55     
    * @param approvalRecord
    * @return   
    * @return boolean  
    * @exception   
    * @version
    */
   public boolean isHasAddplusRole( ParticipantConstants currentParticipantId,CustomerType customerType,User operaterUser);
   
   /**
    * 功能描述：   查询一段时间内创建的客户信息
    * 创建人：yudajun    
    * 创建时间：2014-5-19 下午5:42:44   
    * 修改人：yudajun
    * 修改时间：2014-5-19 下午5:42:44   
    * 修改备注：   
    * 参数： @param startDate
    * 参数： @param endDate
    * 参数： @return
    * @version
     */
    public List<Customer> findCustomerCreateBetween(Date startDate,Date endDate,String operateType);
    
    /**
    * 功能描述：   查询某一段时间内审批通过的新增类型（修改变更不算）的客户
    * 创建人：yudajun    
    * 创建时间：2014-5-20 上午10:36:56   
    * 修改人：yudajun
    * 修改时间：2014-5-20 上午10:36:56   
    * 修改备注：   
    * 参数： @param startDate
    * 参数： @param endDate
    * 参数： @return
    * @version
     */
    public List<Customer> findCustomerApprovedBetween(Date startDate,Date endDate);
    /**
     * 
     * 功能描述:客戶暫存
     * tempSaveCustomer
     * @创建人:	 chenchunhui01
     * @创建时间: 	2014年6月10日 下午5:33:00     
     * @param customerBean
     * @param operatorUser
     * @return   
     * @return CustomerBean  
     * @exception   
     * @version
     */
    public CustomerBean tempSaveCustomer(CustomerBean customerBean, User operatorUser);
    public void  updateDirect(Customer customer);
}
