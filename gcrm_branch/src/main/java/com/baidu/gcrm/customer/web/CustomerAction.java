package com.baidu.gcrm.customer.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.ad.content.web.helper.BaseI18nModelComparator;
import com.baidu.gcrm.ad.service.IContractService;
import com.baidu.gcrm.ad.web.utils.ContractCondition;
import com.baidu.gcrm.ad.web.utils.ContractListBean;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.bpm.web.helper.ActivityInfo;
import com.baidu.gcrm.common.AutoSuggestBean;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.IFileUploadService;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.datarole.IGCrmDataRoleService;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.common.util.HttpHeaderUtil;
import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.contact.service.IContactService;
import com.baidu.gcrm.contact.web.validator.ContactPersonValidator;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.model.CustomerApprovalRecord;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.customer.web.helper.AgentCustomerSuggestBean;
import com.baidu.gcrm.customer.web.helper.AgentType;
import com.baidu.gcrm.customer.web.helper.BusinessType;
import com.baidu.gcrm.customer.web.helper.CompanySize;
import com.baidu.gcrm.customer.web.helper.CustomerApproveState;
import com.baidu.gcrm.customer.web.helper.CustomerBean;
import com.baidu.gcrm.customer.web.helper.CustomerCondition;
import com.baidu.gcrm.customer.web.helper.CustomerDetailView;
import com.baidu.gcrm.customer.web.helper.CustomerListBean;
import com.baidu.gcrm.customer.web.helper.CustomerState;
import com.baidu.gcrm.customer.web.helper.CustomerType;
import com.baidu.gcrm.customer.web.validator.CustomerAddValidator;
import com.baidu.gcrm.customer.web.validator.CustomerSaveOrUpdateValidator;
import com.baidu.gcrm.customer.web.validator.CustomerTemporarySaveValidator;
import com.baidu.gcrm.i18n.helper.BaseI18nModelComparator.ComparatorWay;
import com.baidu.gcrm.log.service.ModifyRecordService;
import com.baidu.gcrm.materials.model.Attachment;
import com.baidu.gcrm.materials.model.AttachmentType;
import com.baidu.gcrm.materials.service.MatericalsService;
import com.baidu.gcrm.opportunity.model.Opportunity;
import com.baidu.gcrm.opportunity.service.IOpportunityService;
import com.baidu.gcrm.opportunity.web.validator.OpportunityValidator;
import com.baidu.gcrm.opportunity.web.vo.OpportunityView;
import com.baidu.gcrm.qualification.model.CustomerResource;
import com.baidu.gcrm.qualification.model.Qualification;
import com.baidu.gcrm.qualification.service.IQualificationService;
import com.baidu.gcrm.qualification.web.QualificationValidator;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;
import com.baidu.gcrm.valuelistcache.model.Country;
import com.baidu.gcrm.valuelistcache.model.CurrencyType;
import com.baidu.gcrm.valuelistcache.model.Industry;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.BillingModelServiceImpl;
import com.baidu.gcrm.ws.cms.ICMSRequestFacade;
import com.baidu.gcrm.ws.mdm.IMDMRequestFacade;

@Controller
@RequestMapping("/customer")
public class CustomerAction extends ControllerHelper {
    /**
     * 客戶流程定义】
     */
    private String processDefineId = GcrmConfig.getConfigValueByKey("customer.process.defineId");

    private static final String messagePrefix = "customer.approval.";

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AbstractValuelistCacheService<AgentRegional> agentRegionalService;

    @Autowired
    private AbstractValuelistCacheService<Country> countryCacheServiceImpl;

    @Autowired
    private AbstractValuelistCacheService<Industry> industryServiceImpl;

    @Autowired
    private AbstractValuelistCacheService<CurrencyType> currencyTypeServiceImpl;

    @Autowired
    private IOpportunityService opportunityService;

    @Autowired
    private AdvertisingPlatformServiceImpl advertisingPlatformServiceImpl;

    @Autowired
    private BillingModelServiceImpl billingModelServiceImpl;

    // 联系人
    @Autowired
    IContactService contactService;

    @Autowired
    private IQualificationService qualificationService;

    @Autowired
    private MatericalsService matericalsService;

    @Autowired
    // private IAccountService accountService;
    private IUserService userService;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private ModifyRecordService modifyRecordService;

    @Autowired
    private IBpmProcessService bpmProcessService;

    @Autowired
    private IUserRightsService userRightsService;

    // 合同
    @Autowired
    private IContractService contractServie;

    @Autowired
    IGCrmDataRoleService gcrmDataRoleService;

    @Autowired
    ICMSRequestFacade cmsRequestFacade;

    @Autowired
    IMDMRequestFacade mdmRequestFacade;

    /**
     * 
     * 功能描述: 界面初始化下拉列表 initView
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月12日 下午2:08:47
     * @return
     * @return JsonBean<Map<String,Object>>
     * @exception
     * @version
     */
    @RequestMapping("/init")
    @ResponseBody
    public JsonBean<Map<String, Object>> initView() {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 所属行业
        List<Industry> industrys = industryServiceImpl.getAllByLocale(currentLocale);

        Collections.sort(industrys, new com.baidu.gcrm.i18n.helper.BaseI18nModelComparator(ComparatorWay.code));

        resultMap.put("industrys", industrys);
        // 币种类型
        List<CurrencyType> currencyTypes = currencyTypeServiceImpl.getAllByLocale(currentLocale);
        resultMap.put("currencyTypes", currencyTypes);
        // 客户类型
        resultMap.put("customerTypes", CustomerType.values());
        // 公司规模
        resultMap.put("companySizes", CompanySize.values());
        // 代理商类型
        resultMap.put("agentTypes", AgentType.values());
        // 資質文件类型
        resultMap.put("attachmentTypes", AttachmentType.values());
        // 业务类型
        resultMap.put("businessType", BusinessType.values());

        List<AgentRegional> agentRegionals = agentRegionalService.getAllByLocale(currentLocale);
        Collections.sort(agentRegionals, new com.baidu.gcrm.i18n.helper.BaseI18nModelComparator(ComparatorWay.code));
        resultMap.put("agentRegionals", agentRegionals);

        return JsonBeanUtil.convertBeanToJsonBean(resultMap);
    }

    @RequestMapping("/initQueryView")
    @ResponseBody
    public JsonBean<Map<String, Object>> initQueryView() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 客户类型
        resultMap.put("customerTypes", CustomerType.values());

        resultMap.put("approveState", CustomerApproveState.values());

        List<AgentRegional> agentRegionals = agentRegionalService.getAllByLocale(currentLocale);
        Collections.sort(agentRegionals, new com.baidu.gcrm.i18n.helper.BaseI18nModelComparator(ComparatorWay.code));

        resultMap.put("agentRegionals", agentRegionals);

        return JsonBeanUtil.convertBeanToJsonBean(resultMap);
    }

    @RequestMapping("/getAgentRegional/{id}")
    @ResponseBody
    public JsonBean<AgentRegional> getAgentRegionalById(@PathVariable("id")
    Long id) {
        if (id == null) {
            return JsonBeanUtil.convertBeanToErrorJsonBean(null, "agentRegionalIdIsNull");
        }
        AgentRegional agentRegional = agentRegionalService.getByIdAndLocale(id, currentLocale);

        return JsonBeanUtil.convertBeanToJsonBean(agentRegional);
    }

    @RequestMapping("/initAdvertisingPlatform")
    @ResponseBody
    public JsonBean<List<AdvertisingPlatform>> initAdvertisingPlatformByBusinessType(@RequestParam("businessType")
    String businessType) {

        if (businessType == null) {
            return JsonBeanUtil.convertBeanToErrorJsonBean(null, "businessTypeNotNull");

        }
        List<AdvertisingPlatform> resultAdvertisingPlatformList = new ArrayList<AdvertisingPlatform>();
        List<AdvertisingPlatform> advertisingPlatformList = advertisingPlatformServiceImpl
                .getAllByLocale(currentLocale);

        for (AdvertisingPlatform ap : advertisingPlatformList) {
            if (ap.getBusinessType() == null || ap.getStatus() == 0) {
                continue;
            }
            Integer businessTypeNum = ap.getBusinessType();
            if (businessType.equals(BusinessType.valueOf(businessTypeNum).toString())) {
                resultAdvertisingPlatformList.add(ap);
            }
        }
        Collections.sort(resultAdvertisingPlatformList, new BaseI18nModelComparator());

        return JsonBeanUtil.convertBeanToJsonBean(resultAdvertisingPlatformList);

    }

    /**
     * 
     * 功能描述: countrySuggest/国家
     * 
     * @创建人: chenchunhui01
     * 
     * @创建时间: 2014年5月12日 下午12:08:30
     * 
     * @param tag
     * @return
     * @return JsonBean<List<AutoSuggestBean>>
     * @exception
     * @version
     */
    @RequestMapping("/countrySuggest")
    @ResponseBody
    public JsonBean<List<AutoSuggestBean>> countrySuggest(@RequestParam("query")
    String tag) {

        List<AutoSuggestBean> suggests = new ArrayList<AutoSuggestBean>();
        if (StringUtils.isBlank(tag)) {
            return JsonBeanUtil.convertBeanToJsonBean(suggests);
        }

        List<Country> countries = countryCacheServiceImpl.getAllByLocale(currentLocale);
        for (Country country : countries) {

            if (StringUtils.containsIgnoreCase(country.getI18nName(), tag)) {

                if (country.getId().equals(999L)) {
                    continue;
                }

                suggests.add(new AutoSuggestBean(country.getId().toString(), country.getI18nName(), country
                        .getI18nName()));
            }
        }

        return JsonBeanUtil.convertBeanToJsonBean(suggests);
    }

    /**
     * 
     * 功能描述: 代理公司 agentCompanySuggest
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月12日 下午12:13:09
     * @param tag
     * @return
     * @return JsonBean<List<AutoSuggestBean>>
     * @exception
     * @version
     */
    @RequestMapping("/agentCompanySuggest")
    @ResponseBody
    public JsonBean<List<AgentCustomerSuggestBean>> agentCompanySuggest(@RequestParam("query")
    String tag) {
        List<AgentCustomerSuggestBean> suggests = new ArrayList<AgentCustomerSuggestBean>();
        if (StringUtils.isBlank(tag)) {
            return JsonBeanUtil.convertBeanToJsonBean(suggests);
        }

        List<Customer> customers = customerService.findByCustomerTypeAndCompanyName(CustomerType.offline, tag);
        for (Customer customer : customers) {
            String companyName = customer.getCompanyName();
            if (StringUtils.containsIgnoreCase(companyName, tag)) {
                suggests.add(new AgentCustomerSuggestBean(customer.getId().toString(), companyName, companyName,
                        customer.getCompanyStatus()));
            }
        }
        return JsonBeanUtil.convertBeanToJsonBean(suggests);
    }

    /**
     * 
     * 功能描述: belongSalesSuggest/所属销售
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月12日 下午12:11:19
     * @param tag
     * @return
     * @return List<AutoSuggestBean>
     * @exception
     * @version
     */
    @RequestMapping("/belongSalesSuggest")
    @ResponseBody
    public JsonBean<List<AutoSuggestBean>> belongSalesSuggest(@RequestParam("query")
    String tag) {
        List<AutoSuggestBean> suggests = new ArrayList<AutoSuggestBean>();
        if (StringUtils.isBlank(tag)) {
            return JsonBeanUtil.convertBeanToJsonBean(suggests);
        }
        List<User> users = userService.findByName(tag);
        for (User user : users) {
            String realName = user.getRealname();
            String userName = user.getUsername();
            if (StringUtils.containsIgnoreCase(realName, tag) || StringUtils.containsIgnoreCase(userName, tag)) {
                suggests.add(new AutoSuggestBean(user.getUcid().toString(), userName, realName));
            }
        }
        return JsonBeanUtil.convertBeanToJsonBean(suggests);
    }

    /**
     * 
     * 功能描述:获取销售上级 salerLeader
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月20日 下午3:56:33
     * @param ucId
     * @return
     * @return JsonBean<List<AutoSuggestBean>>
     * @exception
     * @version
     */
    @RequestMapping("/belongSalesLeader")
    @ResponseBody
    public JsonBean<AutoSuggestBean> salerLeader(@RequestParam("salerId")
    Long ucId) {
        List<AutoSuggestBean> suggests = new ArrayList<AutoSuggestBean>();
        if (ucId == null) {
            return JsonBeanUtil.convertBeanToJsonBean(null);
        }

        List<User> users = userRightsService.findDirectLeaderByUcId(ucId);
        for (User user : users) {
            String realName = user.getRealname();
            String userName = user.getUsername();
            suggests.add(new AutoSuggestBean(user.getUcid().toString(), userName, realName));
        }
        if (suggests.size() > 0)
            return JsonBeanUtil.convertBeanToJsonBean(suggests.get(0));
        else
            return JsonBeanUtil.convertBeanToJsonBean(null);
    }

    private boolean isTypeChangeAllowed(Customer customer) {
        if (customer.getCustomerType() == null) {
            return true;
        }
        Integer type = customer.getCustomerType().ordinal();
        if (type == null || type != CustomerType.offline.getValue()) {
            return true;
        }
        if (CollectionUtils.isNotEmpty(customerService.findByAgentCompany(customer.getId()))) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 功能描述: 暂存客户基本信息 tempSaveCustomer 创建人: chenchunhui01 创建时间: 2014年5月6日
     * 下午6:49:26
     * 
     * @param customerBean
     * @param result
     * @return
     * @return JsonBean<String>
     * @exception
     * @version
     */
    @RequestMapping("/tempSaveCustomer")
    @ResponseBody
    public JsonBean<CustomerBean> tempSaveCustomer(@RequestBody
    CustomerBean customerBean, BindingResult result) {
        // 调用验证器
        if (customerBean == null) {
            return JsonBeanUtil.convertBeanToJsonBean(customerBean);

        }
        if (!isValidatorPass(new CustomerTemporarySaveValidator(), customerBean, result)) {
            return JsonBeanUtil.convertBeanToJsonBean(null, super.collectErrors(result));
        }
        try {
            Customer customer = customerBean.getCustomer();

            customer.setApprovalStatus(CustomerApproveState.saving.ordinal());
            customer.setCompanyStatus(CustomerState.waiting_take_effect.ordinal());
            customerService.tempSaveCustomer(customerBean, getCurrentUser());
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "客户暂存出现异常", e);
            return JsonBeanUtil.convertBeanToErrorJsonBean(customerBean, e.getMessage());
        }
        return JsonBeanUtil.convertBeanToJsonBean(customerBean);
    }

    @RequestMapping("/submit")
    @ResponseBody
    public JsonBean<CustomerBean> submit(@RequestBody
    CustomerBean customerBean, BindingResult result) {
        // 调用验证器(基本信息新增验证）
        if (!isValidatorPass(new CustomerAddValidator(), customerBean, result)) {
            return JsonBeanUtil.convertBeanToJsonBean(null, super.collectErrors(result));
        }

        try {
            Customer customer = customerBean.getCustomer();
            customer.setCompanyStatus(CustomerState.waiting_take_effect.ordinal());
            customerService.submitProcess(customerBean, getCurrentUser());
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "客户提交出现异常", e);
            String message = MessageHelper.getMessage("activity.complete.error", this.currentLocale);
            return JsonBeanUtil.convertBeanToJsonBean(null, message);
        }

        return JsonBeanUtil.convertBeanToJsonBean(customerBean);
    }

    private boolean isValidatorPass(Validator validator, Object traget, BindingResult result) {
        // 调用验证器(基本信息新增验证）
        ValidationUtils.invokeValidator(validator, traget, result);

        if (result.hasErrors()) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 功能描述: 审批操作，点击审批按钮操作 approve
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月14日 上午10:44:12
     * @param customerApprovalRecord
     * @return
     * @return JsonBean<String>
     * @exception
     * @version
     */
    @RequestMapping("/approve")
    @ResponseBody
    public JsonBean<String> approve(@RequestBody
    CustomerApprovalRecord customerApprovalRecord) {
        try {
            User currentOperator = getCurrentUser();
            customerService.saveAndCompleteApproval(customerApprovalRecord, currentOperator, currentLocale);
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "客户审批出现异常", e);
            return JsonBeanUtil.convertBeanToErrorJsonBean("error", e.getMessage());
        }
        return JsonBeanUtil.convertBeanToJsonBean("Success");
    }

    /**
     * 
     * 功能描述: 作废操作 recoveryCoustomer
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月13日 下午5:19:55
     * @param customerId
     * @param request
     * @return
     * @return JsonBean<Customer>
     * @exception
     * @version
     */
    @RequestMapping("/discardCustomer/{id}")
    @ResponseBody
    public JsonBean<Customer> discardCustomer(@PathVariable("id")
    Long customerId, HttpServletRequest request) {
        Customer customer = null;
        if (StringUtils.isEmpty(String.valueOf(customerId))) {
            return JsonBeanUtil.convertBeanToJsonBean(customer);
        }
        try {
            User user = getCurrentUser();
            customer = customerService.discardCoustomerById(customerId, user, currentLocale, processDefineId);
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);
            return JsonBeanUtil.convertBeanToErrorJsonBean(customer, e.getMessage());
        }
        if (null == customer) {
			return JsonBeanUtil.convertBeanToJsonBean(null, MessageHelper.getMessage("customer.list.cannot.discard", currentLocale), JsonBeanUtil.CODE_MESSAGE);
		}
        return JsonBeanUtil.convertBeanToJsonBean(customer);
    }

    /**
     * 
     * 功能描述:撤销客户申请操作 withdrawCustomer
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月14日 上午10:47:00
     * @param customerId
     * @param request
     * @return
     * @return JsonBean<Customer>
     * @exception
     * @version
     */
    @RequestMapping("/withdrawCustomer/{id}")
    @ResponseBody
    public JsonBean<Customer> withdrawCustomer(@PathVariable("id")
    Long customerId, HttpServletRequest request) {
        Customer customer = null;
        if (StringUtils.isEmpty(String.valueOf(customerId))) {
            return JsonBeanUtil.convertBeanToJsonBean(customer);
        }
        try {
            User user = getCurrentUser();
            customer = customerService.withdrawCoustomerApplyById(customerId, user, currentLocale, processDefineId);
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);

            return JsonBeanUtil.convertBeanToErrorJsonBean(customer, e.getMessage());
        }
        return JsonBeanUtil.convertBeanToJsonBean(customer);
    }

    /**
     * 
     * 功能描述: 点击恢复按钮，完成恢复操作 recoveryCustomer
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月14日 上午10:47:26
     * @param customerId
     * @param result
     * @return
     * @return JsonBean<Customer>
     * @exception
     * @version
     */
    @RequestMapping("/recoveryCustomer")
    @ResponseBody
    public JsonBean<Customer> recoveryCustomer(@RequestBody
    CustomerBean customerBean, BindingResult result) {

        if (customerBean == null) {
            JsonBeanUtil.convertBeanToJsonBean(null);
        }

        Customer customer = customerBean.getCustomer();

        Long customerId = customer.getId();
        if (customerId == null) {
            return JsonBeanUtil.convertBeanToJsonBean(customer);
        }
        customer = customerService.findById(customerId);

        if (isValidatorPass(new CustomerAddValidator(), customerBean, result)) {
            return JsonBeanUtil.convertBeanToJsonBean(null, super.collectErrors(result));
        }

        try {
            User user = getCurrentUser();
            customer = customerService.recoveryCustomerApplyById(customerId, user);
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);

            return JsonBeanUtil.convertBeanToErrorJsonBean(customer, e.getMessage());
        }
        return JsonBeanUtil.convertBeanToJsonBean(customer);
    }

    @RequestMapping("/reminders/{id}")
    @ResponseBody
    public JsonBean<String> remindersCustomer(@PathVariable("id")
    Long customerId, HttpServletRequest request) {
        customerService.remindersContentByMail(customerId, currentLocale);
        return JsonBeanUtil.convertBeanToJsonBean("success");

    }

    /**
     * 功能描述: 分页查询客户列表 queryCustomerList4Page 创建人: chenchunhui01 创建时间: 2014年5月4日
     * 下午2:56:19
     * 
     * @param condition
     * @return
     * @return JsonBean<Page<CustomerListBean>>
     * @exception
     * @version
     */
    @RequestMapping("/query")
    @ResponseBody
    public JsonBean<Page<CustomerListBean>> queryCustomerList4Page(@RequestBody
    CustomerCondition condition) {
        List<Long> feasibityUserIds = gcrmDataRoleService.findFeasiblityUserIdList(getUserId());
        condition.setOperatorIdList(feasibityUserIds);
        if (CollectionUtils.isNotEmpty(condition.getOperatorIdList()) && condition.getCreateOperator() == null)
            condition.setCreateOperator(getUserId());
        Page<CustomerListBean> resultPage = customerService.findByCondition(condition, currentLocale);
        return JsonBeanUtil.convertBeanToJsonBean(resultPage);
    }

    /**
     * 
     * 功能描述: 查看客户详细信息 showCustomerDetailInfo 创建人: chenchunhui01 创建时间: 2014年5月4日
     * 下午5:25:56
     * 
     * @param id
     * @return
     * @return JsonBean<CustomerView>
     * @exception
     * @version
     */
    @RequestMapping("/showCustomerDetailInfo/{id}")
    @ResponseBody
    public JsonBean<CustomerDetailView> showCustomerDetailInfo(@PathVariable("id")
    Long id, HttpServletRequest request) {
        CustomerDetailView customerView = null;
        try {
            // 基本信息
            customerView = queryCustomerViewById(id, true, request.getContextPath());

            if (customerView == null) {
                return JsonBeanUtil.convertBeanToErrorJsonBean(null, "customerNotFindById");
            }

        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);

            return JsonBeanUtil.convertBeanToErrorJsonBean(null, e.getMessage());
        }

        return JsonBeanUtil.convertBeanToJsonBean(customerView);
    }

    @RequestMapping("/approveview")
    @ResponseBody
    public JsonBean<CustomerDetailView> showApproveInfoview(@RequestBody
    CustomerApprovalRecord customerApprovalRecord, HttpServletRequest request) {

        String activityId = customerApprovalRecord.getActivityId();
        if (activityId == null || !activityId.contains("_")) {
            String activityIdMessage = MessageHelper.getMessage(messagePrefix + "activity.id.error", currentLocale);
            return JsonBeanUtil.convertBeanToJsonBean(null, activityIdMessage);
        }

        CustomerDetailView infoView = null;
        try {

            ActivityInfo activity = bpmProcessService.getActivityInfoByActivityId(activityId);
            ParticipantConstants currentParticipantId = activity.getParticipantId();
            String performer = activity.getPerformer();

            if (StringUtils.isBlank(performer)) {
                String activityIdMessage = MessageHelper.getMessage(messagePrefix + "activity.nouser", currentLocale);
                return JsonBeanUtil.convertBeanToJsonBean(null, activityIdMessage);
            }
            if (performer.indexOf(getUuapUserName()) < 0) {
                String activityIdMessage = MessageHelper.getMessage(messagePrefix + "activity.user.norole",
                        currentLocale);
                return JsonBeanUtil.convertBeanToJsonBean(null, activityIdMessage);
            }
            Long customerId = customerApprovalRecord.getCustomerId();
            if (customerId != null && !customerId.toString().equals(activity.getForeignKey())) {
                return JsonBeanUtil.convertBeanToJsonBean(null,
                        MessageHelper.getMessage("operation.not.permit", currentLocale));
            }
            if (!activity.isRunning()) {
                return JsonBeanUtil.convertBeanToJsonBean(null, "activity.complete.already",
                        JsonBeanUtil.CODE_ERROR_IGNORE);
            }
            infoView = queryCustomerViewById(customerId, false, request.getContextPath());
            // 详细界面 是否有加签权限
            CustomerType customerType = infoView.getCustomer().getCustomerType();
            boolean isHasAddPlusRole = customerService.isHasAddplusRole(currentParticipantId, customerType,
                    getCurrentUser());
            infoView.setAddPlusOperate(isHasAddPlusRole);

        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);

            return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
        }

        return JsonBeanUtil.convertBeanToJsonBean(infoView);
    }

    private CustomerDetailView queryCustomerViewById(Long customerId, boolean isQueryView, String contextPath) {
        CustomerDetailView customerView = customerService.findCustomerBaseInfoById(customerId, currentLocale,
                isQueryView);
        if (customerView != null) {
            Customer currCustomer = customerView.getCustomer();

            if (isQueryView) {
                boolean isTypeChange = isTypeChangeAllowed(currCustomer);
                customerView.setTypeChangeAllowed(isTypeChange);
            }

            CustomerType customerType = currCustomer.getCustomerType();

            Long customerNumber = currCustomer.getId();

            List<ContactPerson> contacts = contactService.findContactsByCustomerNumber(customerNumber);
            customerView.setContacts(contacts);

            if (customerType != null && CustomerType.offline.equals(customerType)) {
                Qualification qualification = qualificationService.findByCustomerNumber(customerNumber);

                customerView.setQualification(qualification);
            } else {

                OpportunityView opportunityView = opportunityService.getOpportunityViewByCustomerNumber(customerNumber,
                        currentLocale);

                // if(opportunityView ==null) {
                // opportunityView = new OpportunityView();
                // }

                customerView.setOpportunityView(opportunityView);

            }
            List<Attachment> attachments = matericalsService.findByCustomerNumber(customerNumber);

            for (Attachment tempAttachment : attachments) {
                tempAttachment.setDownLoadUrl(contextPath, "/customer/download");
            }
            customerView.setAttachments(attachments);
        }
        return customerView;
    }

    /**
     * 功能描述： 查看审批记录 创建人：cch 创建时间：2014-4-22 19:38:47 修改人：cch 修改时间：2014-4-22
     * 19:38:47 修改备注： 参数： @param materialApplyId 参数：
     * 
     * @return
     * @version
     */
    @RequestMapping("/findApproveRecord")
    @ResponseBody
    public Object findApproveRecord(@RequestParam("customerId")
    String customerId) {
        List<CustomerApprovalRecord> recordList = new ArrayList<CustomerApprovalRecord>();

        try {
            if (StringUtils.isNotBlank(customerId)) {

                recordList = customerService.findRecordByCustomerId(Long.valueOf(customerId), processDefineId,
                        currentLocale);
            }
        } catch (NumberFormatException e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);

        } catch (CRMBaseException e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);

        }

        return JsonBeanUtil.convertBeanToJsonBean(recordList);
    }

    /**
     * 功能描述： 查看申请单修改记录 创建人：cch 创建时间：2014-4-22 19:38:47 修改人：cch 修改时间：2014-4-22
     * 19:38:47 修改备注： 参数：
     * 
     * @param materialApplyId
     *            参数： @return
     * 
     * @version
     */
    @RequestMapping("/findModifyRecord")
    @ResponseBody
    public Object findChangeHistoryRecord(@RequestParam("customerId")
    String customerId) {
        List<Map<String, Object>> recordList = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotBlank(customerId)) {
            try {
                recordList = customerService.findChangeHistoryRecord(customerId, currentLocale);
            } catch (CRMBaseException e) {

                return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
            }
        }
        return JsonBeanUtil.convertBeanToJsonBean(recordList);
    }

    @RequestMapping("/updateCustomer")
    @ResponseBody
    public JsonBean<CustomerBean> updateCustomer(@RequestBody
    CustomerBean customerBean, BindingResult result) {

        ValidationUtils.invokeValidator(new CustomerSaveOrUpdateValidator(), customerBean, result);

        if (result.hasErrors()) {
            return JsonBeanUtil.convertBeanToJsonBean(customerBean, super.collectErrors(result),
                    JsonBeanUtil.CODE_ERROR);
        }
        Customer customer = customerBean.getCustomer();
        Long customerId = customer.getId();
        // 更新操作 客户 ID 不能为空
        if (customerId == null) {
            return JsonBeanUtil.convertBeanToErrorJsonBean(null, "customerIdNotNull");
        }
        generatePropertyForUpdate(customer);
        customerService.update(customer);

        return JsonBeanUtil.convertBeanToJsonBean(customerBean);
    }

    /**
     * 
     * 功能描述: 更新业务机会 updateOpportunity 创建人: chenchunhui01 创建时间: 2014年5月9日
     * 上午11:12:28
     * 
     * @param opportunityBean
     * @param result
     * @return
     * @return JsonBean<Opportunity>
     * @exception
     * @version
     */
    @RequestMapping("/updateOpportunity")
    @ResponseBody
    public JsonBean<Opportunity> updateOpportunity(@RequestBody
    Opportunity opportunityBean, BindingResult result) {

        ValidationUtils.invokeValidator(new OpportunityValidator(), opportunityBean, result);

        if (result.hasErrors()) {
            return JsonBeanUtil.convertBeanToJsonBean(opportunityBean, super.collectErrors(result),
                    JsonBeanUtil.CODE_ERROR);
        }

        Long opportunityId = opportunityBean.getId();
        Long customerNumber = opportunityBean.getCustomerNumber();
        // 更新操作 业务机会 ID 不能为空
        if (opportunityId == null) {
            // return JsonBeanUtil.convertBeanToErrorJsonBean(null,
            // "opportunityIdNotNull");
        }
        if (customerNumber == null) {
            return JsonBeanUtil.convertBeanToErrorJsonBean(null, "customerNumberNotNull");
        }
        if (isOpportunityBlank(opportunityBean)) {
            opportunityService.deleteOpportunity(opportunityId);
            opportunityService.deletePlatformListByCustomerNumber(customerNumber);
        } else {
            generatePropertyForCreateUpdate(opportunityBean);
            opportunityService.saveOrUpdateOpportunity(opportunityBean);
            opportunityService.updatePlatformList(opportunityBean.getId(), opportunityBean.getPlatformIds());
        }
        updateCustomerRelated(customerNumber);

        return JsonBeanUtil.convertBeanToJsonBean(opportunityBean);
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

    /**
     * 
     * 功能描述: updateQualification 更新代理商资质
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月23日 上午11:05:50
     * @param qualificationBean
     * @param result
     * @return
     * @return JsonBean<Qualification>
     * @exception
     * @version
     */
    @RequestMapping("/updateQualification")
    @ResponseBody
    public JsonBean<Qualification> updateQualification(@RequestBody
    Qualification qualificationBean, BindingResult result) {

        ValidationUtils.invokeValidator(new QualificationValidator(), qualificationBean, result);

        if (result.hasErrors()) {
            return JsonBeanUtil.convertBeanToJsonBean(qualificationBean, super.collectErrors(result),
                    JsonBeanUtil.CODE_ERROR);
        }

        Long qualificationId = qualificationBean.getId();
        Long customerNumber = qualificationBean.getCustomerNumber();
        // 更新操作 代理商资质 ID 不能为空
        if (qualificationId == null) {
            // return JsonBeanUtil.convertBeanToErrorJsonBean(null,
            // "qualificationIdNotNulll");
        }

        if (customerNumber == null) {
            return JsonBeanUtil.convertBeanToErrorJsonBean(null, "customerNumberNotNulll");
        }

        if (isQualificationBlank(qualificationBean)) {
            qualificationService.deleteByCustomerNumber(customerNumber);
        } else {
            generatePropertyForUpdate(qualificationBean);
            qualificationService.saveQualification(qualificationBean);
        }
        updateCustomerRelated(customerNumber);

        return JsonBeanUtil.convertBeanToJsonBean(qualificationBean);

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
     * 
     * 功能描述: 更新联系人 updateContactPerson 创建人: chenchunhui01 创建时间: 2014年5月9日
     * 下午12:04:05
     * 
     * @param contactPerson
     * @param result
     * @return JsonBean<ContactPerson>
     * @exception
     * @version
     */
    @RequestMapping("/saveOrUpdateContactPerson")
    @ResponseBody
    public JsonBean<ContactPerson> saveOrUpdateContactPerson(@RequestBody
    ContactPerson contactPersonBean, BindingResult result) {
        try {
            ValidationUtils.invokeValidator(new ContactPersonValidator(), contactPersonBean, result);

            if (result.hasErrors()) {
                return JsonBeanUtil.convertBeanToJsonBean(contactPersonBean, super.collectErrors(result),
                        JsonBeanUtil.CODE_ERROR);
            }

            Long customerNumber = contactPersonBean.getCustomerNumber();

            Long contactPersonId = contactPersonBean.getId();

            if (customerNumber == null) {
                return JsonBeanUtil.convertBeanToErrorJsonBean(null, "customerNumberNotNulll");
            }
            if (contactPersonId == null) {
                generatePropertyForCreate(contactPersonBean);
                contactService.saveContact(contactPersonBean);
            } else {
                generatePropertyForCreateUpdate(contactPersonBean);
                contactService.updateContact(contactPersonBean);
            }
            updateCustomerRelated(customerNumber);
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);
            return JsonBeanUtil.convertBeanToErrorJsonBean(null, "saveOrUpdateContactPersonException");
        }
        return JsonBeanUtil.convertBeanToJsonBean(contactPersonBean);
    }

    /**
     * 功能描述: delete联系人 updateContactPerson 创建人: chenchunhui01 创建时间: 2014年5月9日
     * 下午12:04:05
     * 
     * @param contactPerson
     * @param result
     * @return JsonBean<ContactPerson>
     * @exception
     * @version
     */
    @RequestMapping("/deleteContactPerson")
    @ResponseBody
    public JsonBean<String> deleteContactPerson(@RequestBody
    ContactPerson contactPersonBean, BindingResult result) {
        try {

            Long customerNumber = contactPersonBean.getCustomerNumber();

            Long contactPersonId = contactPersonBean.getId();

            if (contactPersonId == null) {
                return JsonBeanUtil.convertBeanToErrorJsonBean(null, "contactPersonIdNotNulll");
            }

            if (customerNumber == null) {
                return JsonBeanUtil.convertBeanToErrorJsonBean(null, "customerNumberNotNulll");
            }

            contactService.delContact(contactPersonId);

            updateCustomerRelated(customerNumber);

        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), "删除联系人出现异常", e);

            return JsonBeanUtil.convertBeanToErrorJsonBean(null, "deleteContactPersonException");
        }
        return JsonBeanUtil.convertBeanToJsonBean("Success");
    }

    /**
     * 
     * 功能描述: saveOrUpdateAttachment 创建人: chenchunhui01 创建时间: 2014年5月9日 下午3:21:59
     * 
     * @param attachment
     * @param result
     * @return
     * @return JsonBean<Attachment>
     * @exception
     * @version
     */
    @RequestMapping("/saveOrUpdateAttachment")
    @ResponseBody
    public JsonBean<Attachment> saveOrUpdateAttachment(@RequestBody
    Attachment attachment, BindingResult result) {
        try {
            Long customerNumber = attachment.getCustomerNumber();

            if (customerNumber == null) {
                return JsonBeanUtil.convertBeanToErrorJsonBean(null, "customerNumberNotNulll");
            }

            attachment = matericalsService.saveAttachment(attachment, true);

            updateCusotmer2Saving(customerNumber);
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), "保存或更资质文件出现异常", e);

            return JsonBeanUtil.convertBeanToErrorJsonBean(null, "saveOrUpdateAttachmentException");
        }
        return JsonBeanUtil.convertBeanToJsonBean(attachment);
    }

    /**
     * 
     * 功能描述: deleteAttachment 创建人: chenchunhui01 创建时间: 2014年5月9日 下午3:17:52
     * 
     * @param attachment
     * @param result
     * @return
     * @return JsonBean<String>
     * @exception
     * @version
     */
    @RequestMapping("/deleteAttachment")
    @ResponseBody
    public JsonBean<String> deleteAttachment(@RequestBody
    Attachment attachment, BindingResult result) {
        try {

            Long customerNumber = attachment.getCustomerNumber();

            Long attachmentId = attachment.getId();

            if (attachmentId == null) {
                return JsonBeanUtil.convertBeanToErrorJsonBean(null, "attachmentIdNotNulll");
            }

            if (customerNumber == null) {
                return JsonBeanUtil.convertBeanToErrorJsonBean(null, "customerNumberNotNulll");
            }

            matericalsService.deleteAttachment(attachmentId);
            if (customerNumber != null && customerNumber > -1)
                updateCusotmer2Saving(customerNumber);

        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), "删除资质文件出现异常", e);

            return JsonBeanUtil.convertBeanToErrorJsonBean(null, "deleteAttachmentException");
        }
        return JsonBeanUtil.convertBeanToJsonBean("Success");
    }

    /**
     * 
     * 功能描述: updateCustomerRelated 创建人: chenchunhui01 创建时间: 2014年5月9日 上午11:59:12
     * 
     * @param customer
     * @return void
     * @exception
     * @version
     */
    private void updateCustomerRelated(Long customerId) {
        Customer customer = customerService.findById(customerId);

        generatePropertyForUpdate(customer);
        customerService.updateCustomer(customer);
    }

    private void updateCusotmer2Saving(Long customerId) {
        Customer customer = customerService.findById(customerId);
        customer.setApprovalStatus(CustomerApproveState.saving.ordinal());
        customer.setCompanyStatus(CustomerState.waiting_take_effect.ordinal());
        generatePropertyForUpdate(customer);
        customerService.updateCustomer(customer);
    }

    /**
     * 
     * 功能描述:增加代理公司 addAgent
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月13日 上午10:49:48
     * @param customer
     * @param result
     * @return
     * @return JsonBean<Customer>
     * @exception
     * @version
     */
    @RequestMapping("/addAgent")
    @ResponseBody
    public JsonBean<Customer> addAgent(@RequestBody
    Customer customer, BindingResult result) {
        String companyName = StringUtils.trim(customer.getCompanyName());
        if (StringUtils.isEmpty(companyName)) {
            result.rejectValue("agentCompany", "customer.agent.name.required");
            return JsonBeanUtil.convertBeanToJsonBean(customer, super.collectErrors(result), JsonBeanUtil.CODE_ERROR);
        }
        try {
            customer.setCompanyName(companyName);
            customer.setCustomerType(CustomerType.offline);
            customer.setCompanySize(CompanySize.less_50);

            Long ucId = getUserId();
            customer.setBelongSales(ucId);

            List<User> users = userRightsService.findDirectLeaderByUcId(ucId);
            if (users != null && users.size() > 0) {
                customer.setBelongManager(users.get(0).getUcid());
            }

            generatePropertyForCreate(customer);
            customer.setCreateTime(null);
            customerService.save(customer);
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), "新增代理出现异常", e);

            return JsonBeanUtil.convertBeanToErrorJsonBean(customer, e.getMessage());
        }
        return JsonBeanUtil.convertBeanToJsonBean(customer);
    }

    /**
     * 
     * 功能描述: 销售转移 updateSales
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月13日 上午10:53:59
     * @param id
     * @param salesId
     * @return
     * @return JsonBean<String>
     * @exception
     * @version
     */
    @RequestMapping("/updateSales/{id}/{salesId}")
    @ResponseBody
    public JsonBean<String> updateSales(@PathVariable("id")
    Long id, @PathVariable("salesId")
    Long salesId) {
        try {
            Customer customer = customerService.findById(id);
            generatePropertyForUpdate(customer);
            customer.setBelongSales(salesId);
            customerService.update(customer);
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), "销售转移出现异常", e);
            return JsonBeanUtil.convertBeanToErrorJsonBean("error", e.getMessage());

        }
        return JsonBeanUtil.convertBeanToJsonBean("Success");
    }

    /**
     * 文件上传
     * 
     * @return
     */
    @RequestMapping("/doUploadFile")
    @ResponseBody
    public void doUploadFile(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {

        PrintWriter writer = null;
        response.setContentType("text/html; charset=UTF-8");

        Iterator<String> it = multipartRequest.getFileNames();
        MultipartFile mpf = null;
        while (it.hasNext()) {
            String fileName = it.next();
            mpf = multipartRequest.getFile(fileName);
            // log.debug("===" + fileName+"upload to local sever");
        }

        // 设置默认值，防止属性绑定失败
        Attachment attachment = new Attachment();
        attachment.setFieldName(mpf.getName());
        attachment.setName(mpf.getOriginalFilename());
        attachment.setCustomerNumber(-1L);
        attachment.setId(-1L);
        attachment.setTempUrl("");
        attachment.setUrl("");
        try {
            writer = response.getWriter();
            attachment.setBytes(mpf.getBytes());
        } catch (IOException e) {
            attachment.setMessage("failed");
            writer.write(JSON.toJSONString(JsonBeanUtil.convertBeanToErrorJsonBean(attachment, e.getMessage())));
            return;
        }

        if (!StringUtils.isEmpty(mpf.getOriginalFilename())) {
            if (mpf.getOriginalFilename().endsWith(".exe")) {
                attachment.setMessage("materials.extension.error");// 设置I18N消息code
            } else {
                if (matericalsService.uploadFile(attachment)) {
                    attachment.setMessage("success");
                } else {
                    attachment.setMessage("failed");
                }
            }
        }
        attachment.setBytes(null);

        writer.write(JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(attachment)));
    }

    @RequestMapping(value = "/download/{id}")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        Attachment attachment = matericalsService.findById(id);
        try {
            String downloadFileName = attachment.getName();

            HttpHeaderUtil.setDownloadHeader(downloadFileName, request, response, false);

            FileCopyUtils.copy(fileUploadService.download(attachment.getTempUrl()), response.getOutputStream());
        } catch (IOException e) {
            // log.error("=====下载文件出错"+e.getMessage());
        }
    }

    /**
     * 
     * 功能描述:查询合同接口 queryContract
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月21日 下午1:57:16
     * @param condition
     * @return
     * @return JsonBean<List<ContractListBean>>
     * @exception
     * @version
     */
    @RequestMapping("/queryContract")
    @ResponseBody
    public JsonBean<List<ContractListBean>> queryContract(@RequestBody
    ContractCondition condition) {

        Map<String, String> errors = new HashMap<String, String>();
        List<ContractListBean> contracts = null;

        try {
            contracts = contractServie.findContractsByCondition(condition);
        } catch (Exception e) {
            errors.put("customer.contract.error", "customer.contract.error");
            return JsonBeanUtil.convertBeanToJsonBean(contracts, errors);
        }

        return JsonBeanUtil.convertBeanToJsonBean(contracts);
    }

    @RequestMapping("/syncCustomer2Cms")
    @ResponseBody
    public JsonBean<String> sync(@RequestParam("id")
    Long customerId) {
        cmsRequestFacade.syncCustomer(customerId);
        return JsonBeanUtil.convertBeanToJsonBean("success");
    }

    @RequestMapping("/syncCustomer2Mdm")
    @ResponseBody
    public JsonBean<String> syncCustomer2Mdm(@RequestParam("id")
    Long customerId) {

        Long result = null;
        try {
            result = mdmRequestFacade.syncCustomer(customerId);

            if (result > 1) {
                Customer customer = customerService.findById(customerId);
                if (customer.getCustomerNumber() == null) {
                    customer.setCustomerNumber(result);
                    customer.setApprovalStatus(CustomerApproveState.approved.ordinal());
                    customer.setCompanyStatus(CustomerState.have_taken_effect.ordinal());
                    generatePropertyForUpdate(customer);
                    customerService.updateCustomer(customer);
                }
            }
        } catch (CRMBaseException e) {
            e.printStackTrace();
        }
        return JsonBeanUtil.convertBeanToJsonBean("success");

    }

    /**
     * 
     * 功能描述:获取当前用户 getCurrentUser 创建人: chenchunhui01 创建时间: 2014年5月7日 下午3:35:23
     * 
     * @return
     * @return User
     * @exception
     * @version
     */
    private User getCurrentUser() {
        User user = new User();
        user.setUcid(getUserId());
        user.setUsername(getUserName());
        user.setUuapName(getUuapUserName());
        user.setRealname(getChineseName());
        user.setRole("Saler");
        return user;
    }

    @ExceptionHandler(Exception.class)
    public String customerExceptionHandler(Exception exception) {

        LoggerHelper.err(this.getClass(), "客户出现异常", exception);

        return "exception";
    }
}
