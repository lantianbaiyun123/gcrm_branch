package com.baidu.gcrm.customer.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.service.IContractService;
import com.baidu.gcrm.bpm.dao.IProcessNameI18nRepository;
import com.baidu.gcrm.bpm.model.ProcessNameI18n;
import com.baidu.gcrm.bpm.service.IAssignmentHandler;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.service.IBpmProcessStartService;
import com.baidu.gcrm.bpm.service.IGCrmTaskInfoService;
import com.baidu.gcrm.bpm.vo.CompleteActivityResponse;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.bpm.vo.RemindRequest;
import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.vo.StartProcessResponse;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.bpm.web.helper.BaseStartProcessBean;
import com.baidu.gcrm.bpm.web.helper.CompleteBaseActivityReq;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.contact.dao.IContactRepository;
import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.contact.service.IContactService;
import com.baidu.gcrm.customer.dao.CustomerApproveRecordRepository;
import com.baidu.gcrm.customer.dao.CustomerRepository;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.model.CustomerApprovalRecord;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.customer.vo.CustomerApplyInfo;
import com.baidu.gcrm.customer.web.helper.CustomerApproveState;
import com.baidu.gcrm.customer.web.helper.CustomerBean;
import com.baidu.gcrm.customer.web.helper.CustomerCondition;
import com.baidu.gcrm.customer.web.helper.CustomerDataState;
import com.baidu.gcrm.customer.web.helper.CustomerDetailView;
import com.baidu.gcrm.customer.web.helper.CustomerListBean;
import com.baidu.gcrm.customer.web.helper.CustomerState;
import com.baidu.gcrm.customer.web.helper.CustomerType;
import com.baidu.gcrm.customer.web.helper.CustomerView;
import com.baidu.gcrm.customer.web.vo.CustomerI18nView;
import com.baidu.gcrm.log.service.ModifyRecordConstant;
import com.baidu.gcrm.log.service.ModifyRecordService;
import com.baidu.gcrm.mail.impl.BaseMailContent;
import com.baidu.gcrm.materials.model.Attachment;
import com.baidu.gcrm.materials.service.MatericalsService;
import com.baidu.gcrm.opportunity.model.Opportunity;
import com.baidu.gcrm.opportunity.service.IOpportunityService;
import com.baidu.gcrm.qualification.model.CustomerResource;
import com.baidu.gcrm.qualification.model.Qualification;
import com.baidu.gcrm.qualification.service.IQualificationService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;
import com.baidu.gcrm.valuelistcache.model.Country;
import com.baidu.gcrm.valuelistcache.model.CurrencyType;
import com.baidu.gcrm.valuelistcache.model.Industry;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;
import com.baidu.gcrm.ws.cms.ICMSRequestFacade;
import com.baidu.gcrm.ws.mdm.IMDMRequestFacade;
import com.baidu.gwfp.ws.BPMClientExtendWebService;
import com.baidu.gwfp.ws.dto.ActivityData;
import com.baidu.gwfp.ws.exception.GWFPException;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerRepository customerDao;

    @Autowired
    private IOpportunityService opportunityService;
    
    @Autowired
    private IContactRepository contactRepository;

    @Autowired
    private IQualificationService qualificationService;

    @Autowired
    private MatericalsService matericalsService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private AbstractValuelistCacheService<AgentRegional> agentRegionalService;

    @Autowired
    private AbstractValuelistCacheService<Country> countryCacheServiceImpl;

    @Autowired
    private AbstractValuelistCacheService<Industry> industryServiceImpl;

    @Autowired
    private AbstractValuelistCacheService<CurrencyType> currencyTypeServiceImpl;

    private static final String messagePrefix = "customer.main.approval.";

    @Autowired
    private IBpmProcessService processService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ModifyRecordService modifyRecordService;

    @Autowired
    @Qualifier("baseProcessServiceImpl")
    IBpmProcessStartService baseProcessService;

    @Autowired
    IUserRightsService userRightService;

    @Autowired
    BPMClientExtendWebService bpmExtWebService;

    @Autowired
    IBpmProcessService bpmProcessService;

    @Autowired
    private CustomerApproveRecordRepository customerApproveRecordDao;

    @Autowired
    IGCrmTaskInfoService gcrmTaskInfoService;

    @Autowired
    private IMDMRequestFacade mdmRequestFacade;

    //
    @Autowired
    ICMSRequestFacade cmsRequestFacadeService;

    @Autowired
    ApplicationContext applicationContext;
    
    // 联系人
    @Autowired
    IContactService contactService;
    @Autowired
    IContractService contractService;

    @Resource(name = "taskExecutor")
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private IProcessNameI18nRepository processNameDao;
    // private static String MDM_SWITCH ="";

    @Value("#{appproperties['mdm.get.code.switch']}")
    private boolean mdmGetCodeSwitch;

    @Override
    public Customer findById(Long id) {
        return customerDao.findOne(id);
    }

    @Override
    public List<Customer> findByCountryAndLiscenseAndName(Integer countryId, String liscense, String companyName) {
        return customerDao.findByCountryAndLiscenseAndName(countryId, liscense, companyName);
    }

    @Override
    public List<Customer> findByCountryAndName(Integer countryId, String companyName) {
        return customerDao.findByCountryAndName(countryId, companyName);
    }

    @Override
    public List<Customer> findByCountryAndLiscense(Integer countryId, String liscense) {
        return customerDao.findByCountryAndLiscense(countryId, liscense);
    }

    @Override
    public List<Customer> findByCompanyName(String companyName) {
        return customerDao.findByCompanyName(companyName);
    }

    @Override
    public Customer findByCustomerNumber(Long customerNumber) {
        if (customerNumber == null) {
            return null;
        }

        return customerDao.findByCustomerNumber(customerNumber);
    }

    @Override
    public CustomerView findViewById(Long id, LocaleConstants locale) {
        Customer customer = customerDao.findOne(id);

        return customerToView(customer, locale);
    }

    @Override
    public CustomerDetailView findCustomerBaseInfoById(Long id, LocaleConstants locale, boolean isQueryView) {
        Customer customer = customerDao.findOne(id);
        if (customer == null) {
            return null;
        }
        CustomerDetailView resultView = new CustomerDetailView();
        formatView2DetailView(customerToView(customer, locale), resultView, locale, isQueryView);
        return resultView;
    }

    private void formatView2DetailView(CustomerView customerView, CustomerDetailView customerDetailView,
            LocaleConstants locale, boolean isQueryView) {
        customerDetailView.setCustomer(customerView.getCustomer());
        customerDetailView.setCountry(customerView.getCountry());
        customerDetailView.setBelongSales(customerView.getBelongSales());
        customerDetailView.setBelongManager(customerView.getBelongManager());
        customerDetailView.setCurrencyType(customerView.getCurrencyType());
        customerDetailView.setIndustry(customerView.getIndustry());
        customerDetailView.setAgentType(customerView.getAgentType());
        customerDetailView.setAgentRegional(customerView.getAgentRegional());
        customerDetailView.setAgentCountry(customerView.getAgentCountry());
        customerDetailView.setAgentCompany(customerView.getAgentCompany());
        if (isQueryView) {
            CustomerApplyInfo applyInfo = new CustomerApplyInfo();
            Customer tempCustomer = customerView.getCustomer();
            applyInfo.setCustomerId(tempCustomer.getId());
            applyInfo.setCustomerNumber(tempCustomer.getCustomerNumber());
            applyInfo.setApproveState(CustomerApproveState.valueOf(tempCustomer.getApprovalStatus()));
            applyInfo.setSubmitTime(tempCustomer.getCreateTime());
            applyInfo.setSubmitOperator(tempCustomer.getCreateOperatorName());
            customerDetailView.setCustomerApplyInfo(applyInfo);

            String taskInfo = tempCustomer.getTaskInfo();
            applyInfo.setTaskInfo(gcrmTaskInfoService.convertTaskInfo(RemindType.customer, taskInfo, locale));
        }
    }

    @Override
    public CustomerView customerToView(Customer customer, LocaleConstants locale) {
        CustomerView customerView = new CustomerView();
        if (customer == null) {
            return null;
        }

        User operator = userService.findByUcid(customer.getCreateOperator());
        if (operator != null)
            customer.setCreateOperatorName(operator.getRealname());

        customerView.setCustomer(customer);

        customerView.setCustomerType(customer.getCustomerType());

        customerView.setCompanySize(customer.getCompanySize());

        customerView
                .setCountry(countryCacheServiceImpl.getByIdAndLocale(String.valueOf(customer.getCountry()), locale));

        if (customer.getBelongSales() != null) {
            User sales = userService.findByUcid(customer.getBelongSales());
            customerView.setBelongSales(sales);

            if (customer.getBelongSales().equals(customer.getBelongManager())) {
                User manager = new User();
                manager.setId(sales.getId());
                manager.setUcid(sales.getUcid());
                manager.setRealname(sales.getRealname());
                customerView.setBelongManager(manager);
            } else {
                if (customer.getBelongManager() != null) {
                    User manager = userService.findByUcid(customer.getBelongManager());
                    customerView.setBelongManager(manager);
                }
            }
        }
        customerView.setApprovalStatus(CustomerApproveState.valueOf(customer.getApprovalStatus()));

        if (customer.getRegisterCapital() != null) {
            customerView.setCurrencyType(currencyTypeServiceImpl.getByIdAndLocale(customer.getCurrencyType(), locale));
        }
        if (CustomerType.offline.equals(customer.getCustomerType())) {
            customerView.setAgentType(customer.getAgentType());
            customerView.setAgentRegional(agentRegionalService.getByIdAndLocale(customer.getAgentRegional(), locale));
            Set<Country> agentCountrys = new HashSet<Country>();
            String agentCountry = customer.getAgentCountry();
            if (StringUtils.isNotBlank(agentCountry)) {
                String[] countryIds = agentCountry.split(",");
                for (String countryId : countryIds) {
                    agentCountrys.add(countryCacheServiceImpl.getByIdAndLocale(countryId, locale));
                }
            }
            customerView.setAgentCountry(agentCountrys);
        } else if (CustomerType.nondirect.equals(customer.getCustomerType()) && customer.getAgentCompany() != null) {
            Customer agentCompany = customerDao.findOne(customer.getAgentCompany());
            customerView.setAgentCompany(agentCompany);
            customerView.setIndustry(industryServiceImpl.getByIdAndLocale(customer.getIndustry(), locale));
        } else {
            customerView.setIndustry(industryServiceImpl.getByIdAndLocale(customer.getIndustry(), locale));
        }
        List<Contract> contracts = contractService.findByCustomerId(customer.getId());
        if(contracts!=null && contracts.size()>0){
        	customerView.setHasContract(true);
        }else{
        	customerView.setHasContract(false);
        }
        return customerView;
    }

    @Override
    public CustomerI18nView customerViewToI18nView(CustomerView customerView, LocaleConstants locale) {
        CustomerI18nView i18nView = new CustomerI18nView();
        if (customerView == null || customerView.getCustomer() == null) {
            return null;
        }

        i18nView.setAgentCompany(customerView.getAgentCompany());
        i18nView.setAgentCountry(customerView.getAgentCountry());
        i18nView.setAgentRegional(customerView.getAgentRegional());
        i18nView.setCountry(customerView.getCountry());
        i18nView.setCustomer(customerView.getCustomer());
        i18nView.setIndustry(customerView.getIndustry());
        i18nView.setCurrencyType(customerView.getCurrencyType());
        i18nView.setHasContract(customerView.isHasContract());

        User user = userService.findByUcid(customerView.getCustomer().getBelongSales());
        if (user != null) {
            i18nView.setBelongsSales(user.getRealname());
        }

        if (customerView.getAgentType() != null) {
            String agentType = customerView.getAgentType().toString();
            if (StringUtils.isNotBlank(agentType)) {
                agentType = "customer.agentType." + agentType;
                agentType = MessageHelper.getMessage(agentType, locale);
                i18nView.setAgentType(agentType);
            }
        }

        if (customerView.getApprovalStatus() != null) {
            String approvalStatus = customerView.getApprovalStatus().toString();
            if (StringUtils.isNotBlank(approvalStatus)) {
                approvalStatus = "customer.approvalStatus." + approvalStatus;
                approvalStatus = MessageHelper.getMessage(approvalStatus, locale);
                i18nView.setApprovalStatus(approvalStatus);
            }
        }

        if (customerView.getCompanySize() != null) {
            String companySize = customerView.getCompanySize().toString();
            if (StringUtils.isNotBlank(companySize)) {
                companySize = "customer.companySize." + companySize;
                companySize = MessageHelper.getMessage(companySize, locale);
                i18nView.setCompanySize(companySize);
            }
        }

        if (customerView.getCustomerType() != null) {
            String customerType = customerView.getCustomerType().toString();
            if (StringUtils.isNotBlank(customerType)) {
                customerType = "customer.type." + customerType;
                customerType = MessageHelper.getMessage(customerType, locale);
                i18nView.setCustomerType(customerType);
            }
        }

        return i18nView;
    }

    @Override
    public void save(Customer customer) {
        customer.setCompanyStatus(CustomerState.waiting_take_effect.ordinal());
        customer.setApprovalStatus(CustomerApproveState.saving.ordinal());

        // 如果代理国家不为空，需排序（修改记录显示问题）
        customer.setAgentCountry(agentCountrySort(customer.getAgentCountry()));

        eraseCustomer(customer);
        customerDao.save(customer);
    }

    @Override
    public void update(Customer customer) {
        Customer resultCustomer = null;
        Customer dbCustomer = customerDao.findOne(customer.getId());

        boolean isApprovaling = dbCustomer.getApprovalStatus().equals(CustomerApproveState.approving.ordinal()) ? true
                : false;

        if (isApprovaling) {
            // updateWhenApproving(customer);
            BeanCopier beanCopier = BeanCopier.create(Customer.class, Customer.class, false);
            Customer tempCustomer = new Customer();
            beanCopier.copy(dbCustomer, tempCustomer, null);
            tempCustomer.setBelongSales(customer.getBelongSales());
            tempCustomer.setBelongManager(customer.getBelongManager());
            tempCustomer.setAddress(customer.getAddress());
            tempCustomer.setDescription(customer.getDescription());
            tempCustomer.setRegisterCapital(customer.getRegisterCapital());
            tempCustomer.setCurrencyType(customer.getCurrencyType());

            tempCustomer.setUpdateTime(customer.getUpdateTime());
            tempCustomer.setUpdateOperator(customer.getUpdateOperator());
            resultCustomer = tempCustomer;
        } else {

            customer.setCustomerNumber(dbCustomer.getCustomerNumber());
            customer.setCreateTime(dbCustomer.getCreateTime());
            customer.setCreateOperator(dbCustomer.getCreateOperator());
            customer.setCompanyStatus(dbCustomer.getCompanyStatus());
            boolean isNeed2Reapprove = needToReapprove(customer, dbCustomer);
            if (isNeed2Reapprove) {
                customer.setApprovalStatus(CustomerApproveState.saving.ordinal());
                customer.setCompanyStatus(CustomerState.waiting_take_effect.ordinal());
            }
            resultCustomer = customer;
        }

        // 如果代理国家不为空，需排序（修改记录显示问题）
        resultCustomer.setAgentCountry(agentCountrySort(resultCustomer.getAgentCountry()));

        if (resultCustomer.getCreateTime() != null)
            modifyRecordService.saveModifyRecord(resultCustomer);

        customerDao.save(resultCustomer);
    }

    private boolean needToReapprove(Customer customer, Customer dbCustomer) {
        return CustomerApproveState.valueOf(dbCustomer.getApprovalStatus()).isApproved()
                && (!dbCustomer.getCompanyName().equals(customer.getCompanyName())
                        || !dbCustomer.getCountry().equals(customer.getCountry())
                        || !StringUtils.equals(dbCustomer.getBusinessType(), customer.getBusinessType())
                        || !dbCustomer.getCompanySize().equals(customer.getCompanySize())
                        || !StringUtils.equals(dbCustomer.getUrl(), customer.getUrl())
                        || (dbCustomer.getBusinessScope() != null && !StringUtils.trimToEmpty(
                                dbCustomer.getBusinessScope()).equals(customer.getBusinessScope()))
                        || (dbCustomer.getIndustry() != null && !dbCustomer.getIndustry()
                                .equals(customer.getIndustry()))
                        || dbCustomer.getRegisterTime().getTime() != customer.getRegisterTime().getTime()
                        || !dbCustomer.getCustomerType().equals(customer.getCustomerType())
                        || !StringUtils.equals(dbCustomer.getBusinessLicense(), customer.getBusinessLicense()) || !isAgentTheSame(
                            customer, dbCustomer));
    }

    private boolean isAgentTheSame(Customer customer, Customer dbCustomer) {
        return isTheSameObjcet(dbCustomer.getAgentType(), customer.getAgentType())
                && Arrays.equals(splitString(dbCustomer.getAgentCountry()), splitString(customer.getAgentCountry()))
                && isTheSameObjcet(dbCustomer.getAgentRegional(), customer.getAgentRegional())
                && isTheSameObjcet(dbCustomer.getAgentCompany(), customer.getAgentCompany());
    }

    private boolean isTheSameObjcet(Object i1, Object i2) {
        if (i1 == null && i2 == null) {
            return true;
        }

        if (i1 == null || i2 == null) {
            return false;
        }

        return i1.equals(i2);
    }

    private String[] splitString(String s) {
        if (StringUtils.isEmpty(s))
            return new String[] {};
        String[] strs = s.split(",");
        Arrays.sort(strs);
        return strs;
    }

    @Override
    public List<Customer> findByCustomerType(CustomerType customerType) {
        return customerDao.findByCustomerType(customerType);
    }

    @Override
    public List<Customer> findByCustomerTypeAndCompanyName(CustomerType customerType, String customerName) {
        return customerDao.findByCustomerTypeAndCompanyNameContaining(customerType, customerName);
    }

    private void eraseCustomer(Customer customer) {
        if (customer.getCustomerType() == null) {
            return;
        }
        CustomerType customerType = customer.getCustomerType();
        switch (customerType) {
        case offline:
            customer.setIndustry(null);
            customer.setAgentCompany(null);
            break;
        case direct:
            break;
        case union:
            customer.setAgentCompany(null);
            customer.setAgentCountry(null);
            customer.setAgentRegional(null);
            customer.setIndustry(null);
            break;
        case nondirect:
            customer.setAgentCountry(null);
            customer.setAgentRegional(null);
            break;
        }

    }

    public void saveContacts(Long customerNumber, List<ContactPerson> contacts) {
        List<ContactPerson> canSaveList = new ArrayList<ContactPerson>();
        for (ContactPerson contactPerson : contacts) {
            if (contactPerson.isNoNeedSave()) {
                continue;
            }
            contactPerson.setCustomerNumber(customerNumber);
            canSaveList.add(contactPerson);
        }
        contactRepository.save(canSaveList);
    }

    /**
     * 
     * 功能描述: 根据自定义筛选条件分页查询客户列表 findByCondition 创建人: chenchunhui01 创建时间:
     * 2014年5月4日 下午2:58:14
     * 
     * @param condition
     * @param locale
     * @return
     * @return Page<CustomerListBean>
     * @exception
     * @version
     */
    @Override
    public Page<CustomerListBean> findByCondition(CustomerCondition condition, LocaleConstants locale) {

        Page<CustomerListBean> page = customerDao.findByCondition(condition);
        processOtherInfo(page, locale);
        return page;
    }

    @Override
    public List<Customer> findByCompanyNameLikeAndBusinessTypeNotTop5(String companyName, Integer businessType) {
        PageRequest request = new PageRequest(0, 5);
        Collection<CustomerType> customerTypes = new ArrayList<CustomerType>();
        customerTypes.add(CustomerType.direct);
        customerTypes.add(CustomerType.offline);
        customerTypes.add(CustomerType.union);
        return customerDao.findByCompanyNameLikeAndCompanyStatusAndApprovalStatusAndCustomerTypeIn("%" + companyName
                + "%", CustomerState.have_taken_effect.ordinal(), CustomerApproveState.approved.ordinal(),
                customerTypes, request);
    }

    /**
     * 
     * 功能描述:封装客户其他信息 processOtherInfo 创建人: chenchunhui01 创建时间: 2014年5月4日
     * 下午3:00:04
     * 
     * @param page
     * @param locale
     * @return void
     * @exception
     * @version
     */
    private void processOtherInfo(Page<CustomerListBean> page, LocaleConstants locale) {
        List<CustomerListBean> dataList = page.getContent();
        if (dataList == null) {
            return;
        }
        Map<String, Country> countryMap = countryCacheServiceImpl.getAllMapByLocale(locale);
        Map<String, AgentRegional> agentRegionalMap = agentRegionalService.getAllMapByLocale(locale);
        for (CustomerListBean temCustomerListBean : dataList) {
            CustomerType customerType = CustomerType.valueOf(temCustomerListBean.getCustomerType());
            if (customerType != null) {
                temCustomerListBean.setCustomerTypeName(customerType.name());
            }

            CustomerApproveState customerApproveState = CustomerApproveState.valueOf(temCustomerListBean
                    .getApprovalStatus());
            if (customerApproveState != null) {
                temCustomerListBean.setApprovalStatusName(customerApproveState.name());
            }

            Integer countryId = temCustomerListBean.getCountry();
            if (countryId != null) {
                Country country = countryMap.get(countryId.toString());
                if (country != null) {
                    temCustomerListBean.setCountryName(country.getI18nName());
                }
            }

            if (temCustomerListBean.getAgentRegional() == null) {
                continue;
            }

            Integer agentRegionalId = temCustomerListBean.getAgentRegional();
            AgentRegional agentRegional = agentRegionalMap.get(agentRegionalId.toString());
            if (agentRegional != null) {
                temCustomerListBean.setAgentRegionalName(agentRegional.getI18nName());
            }
        }
    }

    /**
     * 作废客户
     */
    @Override
    public Customer discardCoustomerById(Long customerId, User user, LocaleConstants locale, String processDefineId)
            throws Exception {
        Customer customer = null;
        try {
        	// 调用CMS接口判断客户是否可以作废 
        	if (!cmsRequestFacadeService.invalidCustomer(customerId)) {
				return customer;
			}
        	
            customer = customerDao.findOne(customerId);
            if (customer == null) {
                throw new CRMBaseException("customerIderror");
            }
            // 撤销审批流，同时更客户状态为作废
            withdrawCustomerProcess(customer, user, locale, processDefineId);

            customer.setTaskInfo("");
            // 更改客户状态
            customer.setCompanyStatus(CustomerState.disabled.ordinal());
            customer.setApprovalStatus(CustomerApproveState.saving.ordinal());
            customer.setUpdateTime(new Date());
            customer.setUpdateOperator(user.getUcid());

            customerDao.save(customer);
            
            // 需要同步作废状态到CMS（无论是否同步到CMS过）
         	cmsRequestFacadeService.syncCustomer(customerId);
        } catch (Exception e) {
            throw e;
        }
        return customer;
    }

    @Override
    public Customer withdrawCoustomerApplyById(Long customerId, User user, LocaleConstants locale,
            String processDefineId) throws Exception {
        Customer customer = null;
        try {
            customer = customerDao.findOne(customerId);
            if (customer == null) {
                throw new CRMBaseException("customerIderror");
            }
            // 撤销审批流，同时更新客户单状态为作废
            withdrawCustomerProcess(customer, user, locale, processDefineId);

            // 设置作废操作TASKINFO信息
            customer.setTaskInfo(gcrmTaskInfoService.getTaskInfo(user.getRealname(), messagePrefix + "withdraw"));

            customer.setUpdateTime(new Date());
            customer.setUpdateOperator(user.getUcid());

            customerDao.save(customer);
        } catch (Exception e) {
            throw e;
        }
        return customer;
    }

    private void withdrawCustomerProcess(Customer customer, User user, LocaleConstants locale, String processDefineId)
            throws Exception {
        try {
            if (null == customer || StringUtils.isEmpty(String.valueOf(customer.getId()))) {
                return;
            }
            // 记录客户原本的状态
            CustomerApproveState oldState = CustomerApproveState.valueOf(customer.getApprovalStatus());
            Long customerId = customer.getId();

            // 调用工作流收回接口(只有在物料单的状态为审核中的时候才可以收回)
            if (oldState == CustomerApproveState.approving) {
                List<Object> objects = customerApproveRecordDao.findRecordByCustomerId(processDefineId, locale,
                        customerId);
                if (objects.size() > 0) {
                    Object[] row = (Object[]) objects.get(objects.size() - 1);
                    CustomerApprovalRecord record = (CustomerApprovalRecord) row[0];
                    String processId = record == null ? "" : record.getProcessId();

                    processService.withdrawAndTerminateProcess(processId, user.getUuapName(), StringUtils.EMPTY);
                }

                customer.setApprovalStatus(CustomerApproveState.saving.ordinal());

            }

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 恢复客户信息
     * 
     */

    @Override
    public Customer recoveryCustomerApplyById(Long customerId, User user) throws Exception {
        Customer customer = null;
        try {
            customer = customerDao.findOne(customerId);
            if (customer == null) {
                throw new CRMBaseException("customerIderror");
            }
            // 更改客户状态

            customer.setCompanyStatus(CustomerState.waiting_take_effect.ordinal());
            customer.setApprovalStatus(CustomerApproveState.saving.ordinal());
            customer.setTaskInfo("");
            customer.setUpdateTime(new Date());

            customerDao.save(customer);
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);
            throw e;
        }
        return customer;
    }

    @Override
    public org.springframework.data.domain.Page<Customer> findAll(Pageable page) {
        return customerDao.findAll(page);
    }

    @Override
    public Customer findCustomer(Long id) {
        return customerDao.findOne(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {

        customer.setCompanyStatus(CustomerState.waiting_take_effect.ordinal());
        customer.setApprovalStatus(CustomerApproveState.saving.ordinal());
        customer.setDataStatus(CustomerDataState.create);
        // 如果代理国家不为空，需排序（修改记录显示问题）
        customer.setAgentCountry(agentCountrySort(customer.getAgentCountry()));

        customer.setCreateTime(null);

        return customerDao.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer.getCustomerNumber() != null) {
            customer.setDataStatus(CustomerDataState.update);
        }

        // 如果代理国家不为空，需排序（修改记录显示问题）
        customer.setAgentCountry(agentCountrySort(customer.getAgentCountry()));

        customerDao.save(customer);
    }

    private String agentCountrySort(String soucre) {
        if (StringUtils.isBlank(soucre)) {
            return soucre;
        }
        String[] countryIds = soucre.split(",");
        Set<Long> countryIdSet = new TreeSet<Long>();
        for (String countryId : countryIds) {
            countryIdSet.add(Long.valueOf(countryId));
        }
        StringBuffer sb = new StringBuffer();
        for (Long countryId : countryIdSet) {
            sb.append(countryId);
            sb.append(",");
        }
        int size = sb.length();
        if (size > 0) {
            return sb.substring(0, size - 1);
        }
        return "";
    }

    @Override
    public List<Customer> findByAgentCompany(Long id) {
        return customerDao.findByAgentCompany(id);
    }

    @Override
    public List<Customer> findCustomerForAdcontent(String companyName) {
        PageRequest request = new PageRequest(0, 5);
        Collection<CustomerType> customerTypes = new ArrayList<CustomerType>();
        customerTypes.add(CustomerType.direct);
        customerTypes.add(CustomerType.nondirect);
        return customerDao.findByCompanyNameLikeAndCompanyStatusAndApprovalStatusAndCustomerTypeIn("%" + companyName
                + "%", CustomerState.have_taken_effect.ordinal(), CustomerApproveState.approved.ordinal(),
                customerTypes, request);
    }
    
	/*
	 * 模糊匹配 广告主名来提示所有有效的所有客户类型CustomerType
	 */
    @Override
    public List<Customer> findCustomerForNotice(String companyName){
    	PageRequest request = new PageRequest(0, 5);
        return customerDao.findByCompanyNameLikeAndCompanyStatusAndApprovalStatus("%" + companyName
                + "%", CustomerState.have_taken_effect.ordinal(), CustomerApproveState.approved.ordinal(), request);
	}

    // 启动客户审批
    // @Override
    public void submitProcess(CustomerBean customerBean, User operaterUser) throws CRMBaseException {

        saveOrUpdateCustomer(customerBean, operaterUser);

        Customer customer = customerBean.getCustomer();

        BaseStartProcessBean startBean = new BaseStartProcessBean();
        Long customerId = customer.getId();
        prepare(startBean, operaterUser, customer);
        StartProcessResponse respone = null;
        try {
          
            //构建 工作流个节点绑定的handler
            respone = baseProcessService.startProcess(startBean);

            CustomerApprovalRecord record = generateApprovalRecord(customerId, operaterUser, respone);
            // 更新申请单状态
            String taskInfo = gcrmTaskInfoService.getTaskInfo(respone.getActivities(), messagePrefix + "submit.task");
            changeStatus4CustomerApply(record, operaterUser, CustomerApproveState.approving, taskInfo);
            // 保持审批记录
            saveApprovalRecordVO(record, operaterUser);

        } catch (Exception e) {
            LoggerHelper.err(getClass(), e.getMessage(), e);

            throw new CRMBaseException("提交客户审批失败", e);
        }
    }

    private void changeStatus4CustomerApply(CustomerApprovalRecord record, User user, CustomerApproveState applyState,
            String taskInfo) throws Exception {
        try {
            Long customerId = record.getCustomerId();
            // 查询当前待审批客户
            Customer customer = customerDao.findOne(customerId);
            if (applyState != null) {
                customer.setApprovalStatus(applyState.ordinal());
                Long customerNumber = customer.getCustomerNumber();
                if (CustomerApproveState.approved.equals(applyState)) {
                    // 调用主数据 设置客户编码
                    if (customerNumber == null) {
                        if (mdmGetCodeSwitch) {
                            Long mdmResult = mdmRequestFacade.syncCustomer(customerId);
                            customerNumber = mdmResult;
                        } else {
                            customerNumber = 0L;
                            for (int i = 0; i < 11; i++) {
                                customerNumber = customerNumber + Math.round(Math.pow(10, i) * Math.random());
                            }
                        }

                        customer.setCustomerNumber(customerNumber);
                    } else {
                        taskExecutor.execute(new MDMSyncCustomerRunable(customerId));
                    }
                    customer.setCompanyStatus(CustomerState.have_taken_effect.ordinal());

                    taskInfo = "";

                    taskExecutor.execute(new CMSSyncCustomerRunable(customerId));
                }
                // 如果第一次提交，增加创建时间
                if (CustomerApproveState.approving.equals(applyState) && customer.getCreateTime() == null) {
                    customer.setCreateTime(new Date());
                    customer.setCreateOperator(user.getUcid());
                }
            }

            customer.setTaskInfo(taskInfo);

            customer.setUpdateOperator(user.getId());

            customer.setUpdateTime(new Date());

            customerDao.save(customer);
        } catch (Exception e) {
            LoggerHelper.err(getClass(), e.getMessage(), e);
            if (e instanceof CRMBaseException) {
                throw e;
            }
        }
    }
   
    public void  updateDirect(Customer customer){
    	customerDao.save(customer);
    }
    private class CMSSyncCustomerRunable implements Runnable {

        private Long customerId;

        public CMSSyncCustomerRunable(Long customerId) {
            this.customerId = customerId;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            cmsRequestFacadeService.syncCustomer(customerId);
        }
    }

    private class MDMSyncCustomerRunable implements Runnable {

        private Long customerId;

        public MDMSyncCustomerRunable(Long customerId) {
            this.customerId = customerId;
        }

        @Override
        public void run() {
            //
            try {
                mdmRequestFacade.syncCustomer(customerId);
            } catch (CRMBaseException e) {
                LoggerHelper.err(getClass(), e.getMessage(), e);
            }
        }
    }


    /**
     * 
     * 功能描述: prepare 创建人: chenchunhui01 创建时间: 2014年5月10日 上午11:32:41
     * 
     * @param startBean
     * @param operaterUser
     * @param customerId
     * @return void
     * @exception
     * @version
     */
    private void prepare(BaseStartProcessBean startBean, User operaterUser, Customer customer) {

        startBean.setStartUser(operaterUser.getUsername());

        String processDefId = GcrmConfig.getConfigValueByKey("customer.process.defineId");
        startBean.setPackageId(GcrmConfig.getConfigValueByKey("customer.package.id"));
        startBean.setProcessDefineId(processDefId);
        startBean.setProcessStartBeanName("CustomerProcessStartBean");
        ProcessNameI18n processNameI18n = processNameDao.findByProcessDefIdAndLocale(processDefId,
                LocaleConstants.en_US.name());
        if (processNameI18n != null) {
            startBean.setProcessName(processNameI18n.getProcessName());
        }
        Long customerId = customer.getId();
        startBean.putActivityData2Map("customerId", customerId);
        String roleValue;
        if (CustomerType.offline.equals(customer.getCustomerType())) {
            roleValue = "agent";
        } else {
            roleValue = "sale";
        }
        startBean.putActivityData2Map("type", roleValue);
        startBean.setForeignKey(customerId.toString());
        //为工作流个节点绑定获取操作人的handler
        startBean.putAssignmentHandler2Map(ParticipantConstants.startUser,applicationContext.getBean("processInstanceStarterHandler", IAssignmentHandler.class));
        startBean.putAssignmentHandler2Map(ParticipantConstants.direct_supervisor,applicationContext.getBean("directSupervisorAssignmentHandler", IAssignmentHandler.class));
        startBean.putAssignmentHandler2Map(ParticipantConstants.dept_manager,applicationContext.getBean("chainResponsibilityAssignmentHandler", IAssignmentHandler.class));
        startBean.putAssignmentHandler2Map(ParticipantConstants.global_ceo,applicationContext.getBean("chainResponsibilityAssignmentHandler", IAssignmentHandler.class));
    }

    private CustomerApprovalRecord generateApprovalRecord(Long customerId, User operaterUser,
            StartProcessResponse processResponse) {
        CustomerApprovalRecord record = new CustomerApprovalRecord();
        record.setCustomerId(customerId);
        record.setActDefId(processResponse.getActDefId());
        record.setActivityId(processResponse.getFirstActivityId());
        record.setProcessId(processResponse.getProcessId());
        return record;
    }

    /**
     * 点击审批按钮操作
     */
    @Override
    public void saveAndCompleteApproval(CustomerApprovalRecord approvalRecord, User operaterUser,
            LocaleConstants currentLocale) throws CRMBaseException {

        CustomerApproveState applyState = null;

        // 判断审批通过OR驳回
        Integer approvalStatus = approvalRecord.getApprovalStatus();
        boolean refused = isRefused(approvalStatus);
        String taskInfo = "";
        if (refused) {

            String operaterUserName = "";
            if (operaterUser != null) {
                operaterUserName = operaterUser.getRealname();
            } else {
                operaterUserName = approvalRecord.getCreateOperator().toString();
            }

            List<String> otherInfos = new ArrayList<String>();
            otherInfos.add(approvalRecord.getApprovalSuggestion());
            taskInfo = gcrmTaskInfoService.getTaskInfo(operaterUserName, messagePrefix + "refuse", otherInfos);
            applyState = CustomerApproveState.refused;

            String processId = approvalRecord.getProcessId();
            try {
                processService.terminateProcess(processId, operaterUserName, approvalRecord.getApprovalSuggestion());
                // return;
            } catch (GWFPException e) {
                LoggerHelper.err(getClass(), "打回流程失败，流程id：{}，任务id：{}，执行人：{}", processId,
                        approvalRecord.getActivityId(), operaterUserName);
                throw new CRMRuntimeException("activity.complete.error");
            }

        } else {
            CompleteBaseActivityReq req = generateCompleteActivityReq(approvalRecord, operaterUser);

        //    plusSignOperation(approvalRecord, req, operaterUser);
            try {
                CompleteActivityResponse response = baseProcessService.completeActivity(req);

                List<Activity> acts = response.getActivities();
                taskInfo = gcrmTaskInfoService.getTaskInfo(acts, "customer.main.approval.task");
                if (response.isProcessFinish()) {
                    applyState = CustomerApproveState.approved;
                }

            } catch (Exception e) {
                LoggerHelper.err(getClass(), "打回流程失败，流程id：{}，任务id：{}，执行人：{}", e);

                throw new CRMRuntimeException("activity.complete.error");
            }
        }
        try {
            // 更改申请单状态
            changeStatus4CustomerApply(approvalRecord, operaterUser, applyState, taskInfo);
        } catch (Exception e) {
            throw new CRMBaseException(e);
        }
        // 记录审批记录
        saveApprovalRecordVO(approvalRecord, operaterUser);
    }

    public boolean isHasAddplusRole(ParticipantConstants currentParticipantId,CustomerType customerType, User operaterUser) {

        try {

            if(currentParticipantId.equals(ParticipantConstants.dept_manager)){
                if(CustomerType.offline.equals(customerType)){
                    if(!isContainsSpeciallyRole(operaterUser,"GLOBAL_CEO")){
                        return true;
                    }
                }
            }
            if(currentParticipantId.equals(ParticipantConstants.direct_supervisor)){
                if(!CustomerType.offline.equals(customerType)){
                    if(!isContainsSpeciallyRole(operaterUser,"GLOBAL_CEO")){
                        return true;
                    }
                }else {
                   if(isContainsSpeciallyRole(operaterUser,"DEPT_MANAGER")){
                       return true;
                   }
                }
            }
        } catch (Exception e) {
            LoggerHelper.err(getClass(), e.getMessage(), e);
        }

        return false;
    }

    private boolean isContainsSpeciallyRole(User operaterUser,String speciallyRoleKey){
        
        List<RightsRole>rightsRoles = userRightService.findUserRolesByUcId(operaterUser.getUcid());
        
        for (RightsRole rightsRole : rightsRoles) {
            if (rightsRole.getRoleTag().equals(GcrmConfig.getConfigValueByKey(speciallyRoleKey)))
                return true;
        }
        return false;
    }
    
    
    /**
     * 判断是否拒绝退回
     */
    private boolean isRefused(Integer approvalStatus) {
        return approvalStatus == 0;
    }

    /**
     * 構建完结活动节点反馈信息
     * 
     */
    private CompleteBaseActivityReq generateCompleteActivityReq(CustomerApprovalRecord approvalRecord, User user) {
        CompleteBaseActivityReq req = new CompleteBaseActivityReq();
        req.setActivityId(approvalRecord.getActivityId());
        req.setProcessId(approvalRecord.getProcessId());
        req.setPerformer(user.getUuapName());
        req.setApproved(approvalRecord.getApprovalStatus());
        req.setReason(approvalRecord.getApprovalSuggestion());
        List<ActivityData> activityDataList = new ArrayList<ActivityData>();

        if (approvalRecord.isPlusSign()) {
            activityDataList.add(new ActivityData("isAdd", "1"));
            // 退出循环
           // activityDataList.add(new ActivityData("isRun", "0"));
        } else {
//            ActivityInfo activity = bpmProcessService.getActivityInfoByActivityId(approvalRecord.getActivityId());
//            ParticipantConstants currentParticipantId = activity.getParticipantId();
//            boolean hasAddSignRole = isHasAddplusRole(currentParticipantId, user);
//            if (hasAddSignRole) {
//                // 退出循环
//                activityDataList.add(new ActivityData("isRun", "0"));
//            } else {
//                activityDataList.add(new ActivityData("isRun", "1"));
//            }
            activityDataList.add(new ActivityData("isAdd", "0"));

        }

        req.setActivityDataList(activityDataList);

        return req;
    }

    /**
     * 根据客户ID查询审批记录
     */
    @Override
    public List<CustomerApprovalRecord> findRecordByCustomerId(Long customerId, String processDefineId,
            LocaleConstants currentLocale) {
        List<CustomerApprovalRecord> customerApprovalRecords = null;
        List<Object> tempResultList = customerApproveRecordDao.findRecordByCustomerId(processDefineId, currentLocale,
                customerId);
        customerApprovalRecords = foramtApproveRecord(tempResultList);
        return customerApprovalRecords;
    }

    /**
     * 格式化审批记录，封装activityName,operaterName 功能描述: foramtApproveRecord 创建人:
     * chenchunhui01 创建时间: 2014年4月30日 上午10:38:18
     * 
     * @param tempResultList
     * @return
     * @return List<MaterialApprovalRecord>
     * @exception
     * @version
     */
    private List<CustomerApprovalRecord> foramtApproveRecord(List<Object> tempResultList) {
        if (tempResultList == null) {
            return null;
        }
        List<CustomerApprovalRecord> customerApprovalRecords = new ArrayList<CustomerApprovalRecord>();
        Object[] tempObject = null;
        for (Object object : tempResultList) {
            tempObject = (Object[]) object;
            CustomerApprovalRecord record = (CustomerApprovalRecord) tempObject[0];
            String operateName = (String) tempObject[1];
            String taskName = (String) tempObject[2];
            record.setCreater(operateName);
            record.setTaskName(taskName);
            customerApprovalRecords.add(record);
        }
        return customerApprovalRecords;
    }

    /**
     * 
     * 功能描述: saveApprovalRecordVO 创建人: chenchunhui01 创建时间: 2014年5月9日 下午5:48:52
     * 
     * @param vo
     * @param operaterUser
     * @throws CRMBaseException
     * @return void
     * @exception
     * @version
     */
    public void saveApprovalRecordVO(CustomerApprovalRecord vo, User operaterUser) throws CRMBaseException {
        try {
            vo.setCreateOperator(operaterUser.getUcid());
            vo.setCreateTime(new Date());
            customerApproveRecordDao.save(vo);
        } catch (Exception e) {
            throw new CRMBaseException("创建客户审批记录异常", e);
        }
    }

    /**
     * 根据客户ID查询修改记录
     */
    @Override
    public List<Map<String, Object>> findChangeHistoryRecord(String customerId, LocaleConstants locale)
            throws CRMBaseException {
        try {
            Long id = Long.valueOf(customerId);

            Map<String, List<Long>> classAndIdMap = new HashMap<String, List<Long>>();
            List<Long> custoramerIds = new ArrayList<Long>();
            custoramerIds.add(id);
            classAndIdMap.put(Customer.class.getSimpleName(), custoramerIds);

            List<ContactPerson> contactPersonList = contactRepository.findByCustomerNumber(id);
            if (contactPersonList != null && contactPersonList.size() > 0) {
                List<Long> contactPersonListId = new ArrayList<Long>();
                for (ContactPerson cp : contactPersonList)
                    contactPersonListId.add(cp.getId());
                classAndIdMap.put(ContactPerson.class.getSimpleName(), contactPersonListId);
            }
            Qualification qualification = qualificationService.findByCustomerNumber(id);

            if (qualification != null) {
                List<Long> qualificationIds = new ArrayList<Long>();
                qualificationIds.add(qualification.getId());
                classAndIdMap.put(Qualification.class.getSimpleName(), qualificationIds);
            }

            Opportunity opportunity = opportunityService.findOpportunityByCustomerNumber(id);
            if (opportunity != null && opportunity.getId() != null) {
                List<Long> opportunityIds = new ArrayList<Long>();
                opportunityIds.add(opportunity.getId());
                classAndIdMap.put(Opportunity.class.getSimpleName(), opportunityIds);
            }

            List<Attachment> attachmentList = matericalsService.findByCustomerNumber(id);

            if (attachmentList != null && attachmentList.size() > 0) {
                List<Long> attachmentIds = new ArrayList<Long>();

                for (Attachment attachment : attachmentList) {
                    attachmentIds.add(attachment.getId());
                }
                classAndIdMap.put(Attachment.class.getSimpleName(), attachmentIds);
            }

            List<Map<String, Object>> historyRecordList = modifyRecordService.findModifyRecord(classAndIdMap, locale);

            convertMaterialHistoryRecord(historyRecordList, locale);
            return historyRecordList;
        } catch (Exception e) {
            throw new CRMBaseException("根据客户ID查询修改记录异常", e);
        }
    }

    /**
     * 
     * 功能描述:
     * 
     * 创建人: chenchunhui01 创建时间: 2014年5月8日 上午10:34:25
     * 
     * @param historyRecordList
     * @return void
     * @exception
     * @version
     */
    private void convertMaterialHistoryRecord(List<Map<String, Object>> historyRecordList, LocaleConstants locale) {

        for (Map<String, Object> historyRecord : historyRecordList) {
            processOperator(historyRecord);
            processOtherInfo(historyRecord, locale);
        }
    }
/**
 * 
 * 功能描述: 补充修改记录其他信息 目前字段尚未存在重复情况顾直接比较属性即可否则需按实体类型（表名）分类比较
 * processOtherInfo
 * @创建人:	 chenchunhui01
 * @创建时间: 	2014年7月15日 下午3:55:46     
 * @param record
 * @param locale   
 * @return void  
 * @exception   
 * @version
 */
    private void processOtherInfo(Map<String, Object> record, LocaleConstants locale) {
        Object filed = record.get(ModifyRecordConstant.TABLEFIELD_KEY);
        if (filed == null) {
            return;
        }
        String filedName = filed.toString();
        String newValueKey = "";
        if (record.get(ModifyRecordConstant.NEWVALUE_KEY) != null)
            newValueKey = record.get(ModifyRecordConstant.NEWVALUE_KEY).toString();
        String oldValueKey = "";
        if (record.get(ModifyRecordConstant.OLDVALUE_KEY) != null)
            oldValueKey = record.get(ModifyRecordConstant.OLDVALUE_KEY).toString();

        if ("customerType".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey))
                record.put(ModifyRecordConstant.NEWVALUE_KEY,
                        MessageHelper.getMessage("customer.type." + newValueKey, locale));
            if (StringUtils.isNotBlank(oldValueKey))
                record.put(ModifyRecordConstant.OLDVALUE_KEY,
                        MessageHelper.getMessage("customer.type." + oldValueKey, locale));
        } else if ("companySize".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey))
                record.put(ModifyRecordConstant.NEWVALUE_KEY,
                        MessageHelper.getMessage("customer.companySize." + newValueKey, locale));
            if (StringUtils.isNotBlank(oldValueKey))
                record.put(ModifyRecordConstant.OLDVALUE_KEY,
                        MessageHelper.getMessage("customer.companySize." + oldValueKey, locale));
        } else if ("country".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey))
                record.put(ModifyRecordConstant.NEWVALUE_KEY,
                        countryCacheServiceImpl.getByIdAndLocale(newValueKey, locale).getI18nName());
            if (StringUtils.isNotBlank(oldValueKey))
                record.put(ModifyRecordConstant.OLDVALUE_KEY,
                        countryCacheServiceImpl.getByIdAndLocale(oldValueKey, locale).getI18nName());
        } else if ("industry".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey))
                record.put(ModifyRecordConstant.NEWVALUE_KEY, industryServiceImpl.getByIdAndLocale(newValueKey, locale)
                        .getI18nName());
            if (StringUtils.isNotBlank(oldValueKey))
                record.put(ModifyRecordConstant.OLDVALUE_KEY, industryServiceImpl.getByIdAndLocale(oldValueKey, locale)
                        .getI18nName());
        } else if ("belongSales".equals(filedName) || "belongManager".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey)) {
                User user = userService.findByUcid(Long.valueOf(newValueKey));
                if (user != null)
                    record.put(ModifyRecordConstant.NEWVALUE_KEY, user.getRealname());
                else
                    record.put(ModifyRecordConstant.NEWVALUE_KEY, "");
            }
            if (StringUtils.isNotBlank(oldValueKey)) {
                User user = userService.findByUcid(Long.valueOf(oldValueKey));
                if (user != null)
                    record.put(ModifyRecordConstant.OLDVALUE_KEY, user.getRealname());
                else
                    record.put(ModifyRecordConstant.NEWVALUE_KEY, "");
            }
        } else if ("agentType".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey))
                record.put(ModifyRecordConstant.NEWVALUE_KEY,
                        MessageHelper.getMessage("customer.agentType." + newValueKey, locale));
            if (StringUtils.isNotBlank(oldValueKey))
                record.put(ModifyRecordConstant.OLDVALUE_KEY,
                        MessageHelper.getMessage("customer.agentType." + oldValueKey, locale));
        } else if ("agentRegional".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey))
                record.put(ModifyRecordConstant.NEWVALUE_KEY, agentRegionalService
                        .getByIdAndLocale(newValueKey, locale).getI18nName());
            if (StringUtils.isNotBlank(oldValueKey))
                record.put(ModifyRecordConstant.OLDVALUE_KEY, agentRegionalService
                        .getByIdAndLocale(oldValueKey, locale).getI18nName());
        } else if ("agentCountry".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey)) {
                String[] countryIds = newValueKey.split(",");
                StringBuffer countryName = new StringBuffer();
                for (String countryId : countryIds) {
                    countryName.append(countryCacheServiceImpl.getByIdAndLocale(countryId, locale).getI18nName());
                    countryName.append("、");
                }
                if (countryName.length() > 0) {
                    record.put(ModifyRecordConstant.NEWVALUE_KEY, countryName.subSequence(0, countryName.length() - 1));
                } else {
                    record.put(ModifyRecordConstant.NEWVALUE_KEY, "");
                }
            }
            if (StringUtils.isNotBlank(oldValueKey)) {
                String[] countryIds = oldValueKey.split(",");
                StringBuffer countryName = new StringBuffer();

                for (String countryId : countryIds) {
                    countryName.append(countryCacheServiceImpl.getByIdAndLocale(countryId, locale).getI18nName());
                    countryName.append("、");
                }
                if (countryName.length() > 0) {
                    record.put(ModifyRecordConstant.OLDVALUE_KEY, countryName.subSequence(0, countryName.length() - 1));
                } else {
                    record.put(ModifyRecordConstant.OLDVALUE_KEY, "");
                }
            }
        } else if ("businessType".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey)) {
                String[] businessKeys = newValueKey.split(",");
                StringBuffer businessTypeNames = new StringBuffer();
                for (String businessTypeKey : businessKeys) {
                    businessTypeNames.append(MessageHelper.getMessage("customer.businessType." + businessTypeKey,
                            locale));
                    businessTypeNames.append("、");
                }
                if (businessTypeNames.length() > 0) {
                    record.put(ModifyRecordConstant.NEWVALUE_KEY,
                            businessTypeNames.subSequence(0, businessTypeNames.length() - 1));
                } else {
                    record.put(ModifyRecordConstant.NEWVALUE_KEY, "");
                }
            }
            if (StringUtils.isNotBlank(oldValueKey)) {
                String[] businessKeys = oldValueKey.split(",");
                StringBuffer businessTypeNames = new StringBuffer();
                for (String businessTypeKey : businessKeys) {
                    businessTypeNames.append(MessageHelper.getMessage("customer.businessType." + businessTypeKey,
                            locale));
                    businessTypeNames.append("、");
                }
                if (businessTypeNames.length() > 0) {
                    record.put(ModifyRecordConstant.OLDVALUE_KEY,
                            businessTypeNames.subSequence(0, businessTypeNames.length() - 1));
                } else {
                    record.put(ModifyRecordConstant.OLDVALUE_KEY, "");
                }
            }

        } else if ("registerTime".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey)) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.ENGLISH);
                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    record.put(ModifyRecordConstant.NEWVALUE_KEY, sdf1.format(sdf.parse(newValueKey)));
                } catch (ParseException e) {
                    LoggerHelper.err(getClass(), e.getMessage(), e);
                }
            }
            if (StringUtils.isNotBlank(oldValueKey)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    record.put(ModifyRecordConstant.OLDVALUE_KEY, sdf1.format(sdf.parse(oldValueKey)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else if ("currencyType".equals(filedName)) {
            if (StringUtils.isNotBlank(newValueKey))
                record.put(ModifyRecordConstant.NEWVALUE_KEY,
                        currencyTypeServiceImpl.getByIdAndLocale(newValueKey, locale).getI18nName());
            if (StringUtils.isNotBlank(oldValueKey))
                record.put(ModifyRecordConstant.OLDVALUE_KEY,
                        currencyTypeServiceImpl.getByIdAndLocale(oldValueKey, locale).getI18nName());
        } else if("type".equals(filedName)){
            //资质文件类型格式化
            if (StringUtils.isNotBlank(newValueKey))
                record.put(ModifyRecordConstant.NEWVALUE_KEY,
                        MessageHelper.getMessage("customer.attachment.type." + newValueKey, locale));
            if (StringUtils.isNotBlank(oldValueKey))
                record.put(ModifyRecordConstant.OLDVALUE_KEY,
                        MessageHelper.getMessage("customer.attachment.type." + oldValueKey, locale));
        }
    }

    private void processOperator(Map<String, Object> record) {
        Object operator = record.get(ModifyRecordConstant.CREATEOPERATOR_KEY);
        if (operator != null) {
            User user = userService.findByUcid(((Number) operator).longValue());
            if (user != null) {
                record.put(ModifyRecordConstant.CREATEOPERATOR_KEY, user.getRealname());
            }

        }
    }

    public void remindersContentByMail(Long id, LocaleConstants currentLocale) {

        RemindRequest request = generateRemindRequest(RemindType.customer, id.toString());
        processService.remindByForeignKey(request);
    }

    private RemindRequest generateRemindRequest(RemindType type, String key) {
        int email = 0;
        RemindRequest request = new RemindRequest();
        request.setReminder(RequestThreadLocal.getLoginUuapName());
        request.setNotifyType(email);
        request.setType(type);
        request.setKey(key);
        return request;
    }

    /**
     * 格式化邮件接收人
     * 
     * @param pbcontent
     * @param adSoulutionId
     */
    public void generateAccount(BaseMailContent mailContent, Long customerId) {
        Set<String> sendTo = new HashSet<String>();
        HashMap<String, Object> account = customerDao.findExecutor(customerId);
        if (account != null) {

            mailContent.putValue4Template("operator", account.get("name").toString());
            sendTo.add(account.get("email").toString());
            mailContent.setSendTo(sendTo);
        }
    }

    @Override
    public List<Customer> findCustomerCreateBetween(Date startDate, Date endDate, String operateType) {
        return customerDao.findCustomerCreateBetween(startDate, endDate, operateType);
    }

    @Override
    public List<Customer> findCustomerApprovedBetween(Date startDate, Date endDate) {
        return customerDao.findCustomerApprovedBetween(startDate, endDate);
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public CustomerBean tempSaveCustomer(CustomerBean customerBean, User operatorUser) {
        saveOrUpdateCustomer(customerBean, operatorUser);
        return customerBean;
    }

    private void saveOrUpdateCustomer(CustomerBean customerBean, User operatorUser) {
        Customer customer = customerBean.getCustomer();
        try {
            if (customer.getId() == null) {
                customerBean = createCustomer(customerBean, operatorUser);
            } else {
                customerBean = updateCustomer(customerBean, operatorUser);
            }
        } catch (CRMBaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private CustomerBean createCustomer(CustomerBean customerBean, User operatorUser) throws CRMBaseException {
        Customer customer = customerBean.getCustomer();
        CustomerType customerType = customer.getCustomerType();
        try {
            generatePropertyForCreate(customer, operatorUser);
            customer = saveCustomer(customer);
            // 客户编码均需从MDM获取，顾在尚未从MDM获取客户编码时，关联表中客户编码均存储成客户ID.
            Long customerNumber = customer.getId();

            List<ContactPerson> contactPersonList = customerBean.getContacts();

            for (ContactPerson contactPerson : contactPersonList) {

                contactPerson.setCustomerNumber(customerNumber);

                generatePropertyForCreate(contactPerson, operatorUser);

                contactService.saveContact(contactPerson);
            }

            List<Attachment> attachments = customerBean.getAttachments();
            for (Attachment attachment : attachments) {
                attachment.setCustomerNumber(customerNumber);
                matericalsService.saveAttachment(attachment,false);
            }

            if (CustomerType.offline.equals(customerType)) {
                Qualification qualification = customerBean.getQualification();
                if (qualification != null) {
                    if (!isQualificationBlank(qualification)) {
                        generatePropertyForCreate(qualification, operatorUser);
                        qualification.setCustomerNumber(customerNumber);
                        qualificationService.saveQualification(qualification);
                    }
                }
            } else {
                Opportunity opportunity = customerBean.getOpportunity();
                if (opportunity != null) {
                    if (!isOpportunityBlank(opportunity)) {
                        generatePropertyForCreate(opportunity, operatorUser);
                        opportunity.setCustomerNumber(customerNumber);
                        opportunityService.saveOpportunity(opportunity);
                    }
                }
            }

        } catch (Exception e) {
            LoggerHelper.err(getClass(), "创建客户信息异常", e.getMessage());
            throw new CRMBaseException("创建客户信息异常", e);
        }

        return customerBean;

    }

    private CustomerBean updateCustomer(CustomerBean customerBean, User operatorUser) throws CRMBaseException {
        Customer customer = customerBean.getCustomer();
        CustomerType customerType = customer.getCustomerType();
        try {
            generatePropertyForUpdate(customer, operatorUser);

            update(customer);

            Long customerNumber = customer.getId();

            List<ContactPerson> contactPersonList = customerBean.getContacts();

            for (ContactPerson contactPerson : contactPersonList) {

                Long contactPersonId = contactPerson.getId();

                if (contactPersonId == null) {
                    generatePropertyForCreate(contactPerson, operatorUser);
                    contactPerson.setCustomerNumber(customerNumber);
                    contactService.saveContact(contactPerson);
                } else {
                    generatePropertyForCreateUpdate(contactPerson, operatorUser);
                    contactService.updateContact(contactPerson);
                }
            }

            List<Attachment> attachments = customerBean.getAttachments();
            if (attachments != null) {

                matericalsService.deleteAttachmentByCustomerId(customerNumber);

                for (Attachment attachment : attachments) {
                    attachment.setCustomerNumber(customerNumber);
                    matericalsService.saveAttachment(attachment,false);
                }
            }

            if (CustomerType.offline.equals(customerType)) {
                Qualification qualification = customerBean.getQualification();
                if (qualification != null) {
                    Long qualificationId = qualification.getId();
                    if (qualificationId == null) {
                        if (!isQualificationBlank(qualification)) {
                            generatePropertyForCreate(qualification, operatorUser);
                            qualification.setCustomerNumber(customerNumber);
                            qualificationService.saveQualification(qualification);
                        }
                    } else {
                        if (isQualificationBlank(qualification)) {

                            qualificationService.deleteByCustomerNumber(customerNumber);
                        } else {
                            generatePropertyForUpdate(qualification, operatorUser);
                            qualification.setCustomerNumber(customerNumber);

                            qualificationService.saveQualification(qualification);
                        }
                    }
                }
                opportunityService.deleteOpportunityByCustomerNumber(customerNumber);
            } else {

                Opportunity opportunity = customerBean.getOpportunity();
                if (opportunity != null) {

                    Long opportunityId = opportunity.getId();
                    if (opportunityId == null) {
                        if (!isOpportunityBlank(opportunity)) {
                            generatePropertyForCreate(opportunity, operatorUser);
                            opportunity.setCustomerNumber(customerNumber);
                            opportunityService.saveOpportunity(opportunity);
                        }
                    } else {

                        if (isOpportunityBlank(opportunity)) {
                            opportunityService.deleteOpportunity(opportunityId);
                            opportunityService.deletePlatformListByCustomerNumber(customerNumber);
                        } else {
                            generatePropertyForCreateUpdate(opportunity, operatorUser);
                            opportunity.setCustomerNumber(customerNumber);
                            opportunityService.saveOrUpdateOpportunity(opportunity);
                            opportunityService.updatePlatformList(opportunity.getId(), opportunity.getPlatformIds());
                        }
                    }
                }
                // 调用 删除 代理商资质方法
                qualificationService.deleteByCustomerNumber(customerNumber);

            }

        } catch (Exception e) {
            e.printStackTrace();
            LoggerHelper.err(getClass(), "客户更新出现异常", e);
            throw new CRMBaseException("客户更新出现异常", e);

        }
        return customerBean;
    }

    private void generatePropertyForCreate(BaseOperationModel baseModel, User operatorUser) {
        if (baseModel == null) {
            return;
        }
        Date current = new Date();
        Long loginId = operatorUser.getUcid();

        baseModel.setCreateOperator(loginId);
        baseModel.setUpdateOperator(loginId);
        baseModel.setCreateTime(current);
        baseModel.setUpdateTime(current);
    }

    private void generatePropertyForCreateUpdate(BaseOperationModel baseModel, User operatorUser) {
        Date current = new Date();
        Long loginId = operatorUser.getUcid();

        baseModel.setUpdateOperator(loginId);
        baseModel.setUpdateTime(current);
    }

    private void generatePropertyForUpdate(BaseOperationModel baseModel, User operatorUser) {
        if (baseModel == null) {
            return;
        }
        Date current = new Date();
        Long loginId = operatorUser.getUcid();

        baseModel.setUpdateOperator(loginId);
        baseModel.setUpdateTime(current);
    }

    /**
     * 
     * 功能描述: isQualificationBlank 创建人: chenchunhui01 创建时间: 2014年5月9日 上午11:58:06
     * 
     * @param qualification
     * @return
     * @return boolean
     * @exception
     * @version
     */
    private boolean isQualificationBlank(Qualification qualification) {
        boolean isQualificationEmpty = true;
        isQualificationEmpty = isQualificationEmpty && StringUtils.isEmpty(qualification.getParterTop1())
                && StringUtils.isEmpty(qualification.getParterTop2())
                && StringUtils.isEmpty(qualification.getParterTop3())
                && StringUtils.isEmpty(qualification.getPerformanceHighlights());

        List<CustomerResource> resources = qualification.getCustomerResources();

        if (resources == null || resources.size() == 0) {
            return isQualificationEmpty;
        }

        boolean isResourceEmpty = true;

        for (CustomerResource resource : resources) {
            isResourceEmpty = isResourceEmpty && StringUtils.isEmpty(resource.getAdvertisersCompany1())
                    && StringUtils.isEmpty(resource.getAdvertisersCompany2())
                    && StringUtils.isEmpty(resource.getAdvertisersCompany3())
                    && StringUtils.isEmpty(resource.getIndustry());
            if (isResourceEmpty == false) {
                break;
            }
        }

        return isQualificationEmpty && isResourceEmpty;
    }

    /**
     * 判斷業務机会是否为空（为空：新增时不创建，变更时删除） 功能描述: isOpportunityBlank 创建人: chenchunhui01
     * 创建时间: 2014年5月9日 上午11:19:00
     * 
     * @param opportunity
     * @return
     * @return boolean
     * @exception
     * @version
     */
    private boolean isOpportunityBlank(Opportunity opportunity) {

        if (opportunity == null) {
            return true;
        }

        if (opportunity.getBudget() != null) {
            return false;
        }
        if (!PatternUtil.isBlank(opportunity.getSpendingTime())) {
            return false;
        }
        if (opportunity.getPayment() != null) {
            return false;
        }
        if (!PatternUtil.isBlank(opportunity.getPaymentPeriod())) {
            return false;
        }
        if (!PatternUtil.isBlank(opportunity.getBillingModel())) {
            return false;
        }
        if (!PatternUtil.isBlank(opportunity.getBusinessType())) {
            return false;
        }
        if (!PatternUtil.isBlank(opportunity.getDescription())) {
            return false;
        }
        return true;

    }

    public void setCustomerDao(CustomerRepository customerDao) {
        this.customerDao = customerDao;
    }
}
