package com.baidu.gcrm.ad.approval.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.ad.approval.record.model.ApprovalRecord;
import com.baidu.gcrm.ad.approval.record.service.IApprovalRecordService;
import com.baidu.gcrm.ad.approval.web.vo.AdApprovalContentView;
import com.baidu.gcrm.ad.approval.web.vo.AdApprovalInfoView;
import com.baidu.gcrm.ad.cancel.service.IAdContentCancelRecordsService;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ApprovalStatus;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ContentType;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentApplyService;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentApplyApprovalVo;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterialMenu;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.model.AdvertiseSolutionApproveState;
import com.baidu.gcrm.ad.model.AdvertiseSolutionType;
import com.baidu.gcrm.ad.model.AdvertiseType;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.service.IAdvertiseMaterialService;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.ad.service.IContractService;
import com.baidu.gcrm.ad.web.utils.ContractUrlUtilHelper;
import com.baidu.gcrm.attachment.model.AttachmentModel;
import com.baidu.gcrm.attachment.model.AttachmentModel.ModuleNameWithAtta;
import com.baidu.gcrm.attachment.service.IAttachmentModelService;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.service.IGCrmTaskInfoService;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.bpm.vo.ProcessInfo;
import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.vo.SimpleActivityInfo;
import com.baidu.gcrm.bpm.web.helper.ActivityInfo;
import com.baidu.gcrm.bpm.web.helper.ProcessStatus;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.IFileUploadService;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.datarole.IGCrmDataRoleService;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.customer.web.helper.CustomerView;
import com.baidu.gcrm.customer.web.vo.CustomerI18nView;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;
import com.baidu.gcrm.schedule1.model.Schedules;
import com.baidu.gcrm.schedule1.service.ISchedulesService;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;
import com.baidu.gwfp.ws.exception.GWFPException;

@Controller
@RequestMapping("adapprovalinfo")
public class AdApprovalInfoAction extends ControllerHelper {

    private static final Logger logger = LoggerFactory.getLogger(AdApprovalInfoAction.class);
    private static final String messagePrefix = "advertise.solution.approvale.";
    private static final String messagePrefix2 = "adcontent.onlineApply.approvale.";

    @Autowired
    IAdvertiseSolutionService solutionService;

    @Autowired
    IAdSolutionContentService contentService;

    @Autowired
    IContractService contractService;

    @Autowired
    ICustomerService customerService;

    @Autowired
    @Qualifier("billingModelServiceImpl")
    AbstractValuelistCacheService<BillingModel> billingModelService;

    @Autowired
    IApprovalRecordService approvalRecordService;

    @Autowired
    IAdvertiseMaterialService materialService;

    @Autowired
    IFileUploadService fileUploadService;

    @Autowired
    IBpmProcessService bpmProcessService;

    @Autowired
    ISchedulesService scheduleService;

    @Autowired
    IAdContentCancelRecordsService cancelRecordsService;

    @Autowired
    IPositionService positionService;

    @Autowired
    IUserService userService;

    @Autowired
    IGCrmTaskInfoService gcrmTaskInfoService;

    @Autowired
    IAdSolutionContentApplyService adSolutionContentApplyService;

    @Autowired
    IAttachmentModelService attachmentModelService;

    @Autowired
    IAdSolutionContentApplyService onlineApplyService;

    @Autowired
    IGCrmDataRoleService gcrmDataRoleService;

    @RequestMapping("view")
    @ResponseBody
    public JsonBean<AdApprovalInfoView> view(@RequestParam("id") String id,
            @RequestParam("activityId") String activityId, HttpServletRequest request) {

        String actDefId = null;
        if (activityId == null || !activityId.contains("_")) {
            String activityIdMessage = MessageHelper.getMessage(messagePrefix + "activity.id.error", currentLocale);
            return JsonBeanUtil.convertBeanToJsonBean(null, activityIdMessage);
        }

        actDefId = activityId.split("_")[1];
        AdApprovalInfoView infoView = new AdApprovalInfoView();
        Long adSolutionId = null;
        Long adContentId = null;
        int index = id.indexOf("_");
        if (index > -1) {
            adSolutionId = Long.parseLong(id.substring(0, index));
            adContentId = Long.parseLong(id.substring(index + 1));
        } else {
            adSolutionId = Long.parseLong(id);
        }
        try {
            ActivityInfo activity = bpmProcessService.getActivityInfoByActivityId(activityId);
            if (!id.equals(activity.getForeignKey())) {
                return JsonBeanUtil.convertBeanToJsonBean(null,
                        MessageHelper.getMessage("operation.not.permit", currentLocale));
            }
            if (activity.getPerformer().indexOf(getUuapUserName()) < 0) {
                return JsonBeanUtil.convertBeanToJsonBean(null, "activity.view.unauthorized");
            }
            if (!activity.isRunning()) {
                return JsonBeanUtil.convertBeanToJsonBean(null, "activity.complete.already",
                        JsonBeanUtil.CODE_ERROR_IGNORE);
            } else {
                infoView.setTaskClose(false);
                infoView.setRole(activity.getParticipantId().name());
            }

            setAdvertiseSolution(infoView, adSolutionId, false);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
        }
        if (adContentId == null) {
            infoView.setApprovalContentViews(getAdvertiseContentBySolution(adSolutionId, actDefId, infoView.getRole(),
                    request));
        } else {
            AdSolutionContentView contentView =
                    contentService.findByAdSolutionContentId(adContentId, getCurrentLocale(), request.getContextPath());
            infoView.setApprovalContentViews(Arrays.asList(getAdvertiseContent(contentView, infoView.getRole(), request)));
        }
        return JsonBeanUtil.convertBeanToJsonBean(infoView);
    }

    @RequestMapping("/viewOnlineApplyApproval")
    @ResponseBody
    public JsonBean<AdSolutionContentApplyApprovalVo> viewOnlineApplyApproval(
            @RequestParam("onlineApplyId") String onlineApplyId, @RequestParam("activityId") String activityId,
            HttpServletRequest request) {

        if (activityId == null || !activityId.contains("_")) {
            String activityIdMessage = MessageHelper.getMessage(messagePrefix2 + "activity.id.error", currentLocale);
            return JsonBeanUtil.convertBeanToJsonBean(null, activityIdMessage);
        }

        AdSolutionContentApplyApprovalVo adSolutionContentApplyApprovalVo = new AdSolutionContentApplyApprovalVo();
        Long applyId = Long.parseLong(onlineApplyId);
        AdSolutionContentApply adSolutionContentApply = adSolutionContentApplyService.findAdContentApply(applyId);
        String participantRole = null;
        try {
            ActivityInfo activity = bpmProcessService.getActivityInfoByActivityId(activityId);
            if (!onlineApplyId.equals(activity.getForeignKey())) {
                return JsonBeanUtil.convertBeanToJsonBean(null,
                        MessageHelper.getMessage("operation.not.permit", currentLocale));
            }
            if (activity.getPerformer().indexOf(getUuapUserName()) < 0) {
                return JsonBeanUtil.convertBeanToJsonBean(null, "activity.view.unauthorized");
            }
            if (!activity.isRunning()) {
                return JsonBeanUtil.convertBeanToJsonBean(null, "activity.complete.already",
                        JsonBeanUtil.CODE_ERROR_IGNORE);
            } else {
                participantRole = activity.getParticipantId().name();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
        }
        setAdContentApplyInfoToVo(adSolutionContentApplyApprovalVo, adSolutionContentApply);

        AdSolutionContentView contentView =
                contentService.findByAdSolutionContentId(adSolutionContentApply.getAdSolutionContentId(),
                        getCurrentLocale(), request.getContextPath());
        adSolutionContentApplyApprovalVo.setApprovalContentViews(Arrays.asList(getAdvertiseContent(contentView,
                participantRole, request)));

        return JsonBeanUtil.convertBeanToJsonBean(adSolutionContentApplyApprovalVo);
    }

    private void setAdContentApplyInfoToVo(AdSolutionContentApplyApprovalVo applyVo, AdSolutionContentApply apply) {
        List<AttachmentModel> attachments =
                attachmentModelService.findByRecordAndModule(apply.getId(), ModuleNameWithAtta.adcontentonlineapply);
        AdvertiseSolution solution = solutionService.findById(apply.getAdSolutionId());
        Contract contract = getContractInfo4OnlineRequest(apply);
        Customer customer = customerService.findByCustomerNumber(solution.getCustomerNumber());
        CustomerView customerView = customerService.customerToView(customer, currentLocale);
        CustomerI18nView customerI18nView = customerService.customerViewToI18nView(customerView, currentLocale);
        applyVo.setAdSolutionContentApply(apply);
        applyVo.setAttachments(attachments);
        applyVo.setContract(contract);
        applyVo.setCustomerI18nView(customerI18nView);
    }

    private Contract getContractInfo4OnlineRequest(AdSolutionContentApply apply) {
        Contract contract = new Contract();
        contract.setNumber(apply.getContractNumber());
        contract.setCategory(apply.getContractType());
        try {
            ProcessInfo pinfo = bpmProcessService.getCmsProcessInfoByForeignKey(contract.getNumber());
            if (pinfo != null) {
                if (pinfo.getStatus() == ProcessStatus.RUN) {
                    List<SimpleActivityInfo> acts = pinfo.getActs();
                    if (acts != null && acts.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        int i = 0;
                        for (SimpleActivityInfo act : acts) {
                            if (i > 0) {
                                sb.append(",");
                            }
                            sb.append(act.getActivityName() + " (审批人:" + act.getPerformer() + ")");
                            i++;
                        }
                        contract.setContractInfo(sb.toString());
                    }
                } else if (pinfo.getStatus() == ProcessStatus.COMPLETE) {
                    contract.setContractInfo(MessageHelper
                            .getMessage("contract.state.process.completed", currentLocale));
                } else if (pinfo.getStatus() == ProcessStatus.TERMINAL) {
                    contract.setContractInfo(MessageHelper.getMessage("contract.state.process.terminated",
                            currentLocale));
                } else if (pinfo.getStatus() == ProcessStatus.SUSPEND) {
                    contract.setContractInfo(MessageHelper
                            .getMessage("contract.state.process.suspended", currentLocale));
                }
            }
            String contractDate = apply.getContractDate();
            if (StringUtils.isNotEmpty(contractDate)) {
                contract.setBeginDate(DateUtils.getString2Date(DateUtils.YYYY_MM_DD, contractDate.split(",", 2)[0]));
                contract.setEndDate(DateUtils.getString2Date(DateUtils.YYYY_MM_DD, contractDate.split(",", 2)[1]));
            }
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "转换提前上线申请合同字段信息发生异常:" + e.getMessage(), e);
        }
        return contract;
    }

    @RequestMapping("/schedule")
    @ResponseBody
    public JsonBean<AdApprovalInfoView> schedule(@RequestParam("id") Long id, HttpServletRequest request) {
        AdApprovalInfoView infoView = new AdApprovalInfoView();
        AdSolutionContentView contentView =
                contentService.findByAdSolutionContentId(id, getCurrentLocale(), request.getContextPath());
        if (contentView == null || contentView.getAdSolutionContent() == null) {

        } else {
            try {
                AdSolutionContent content = contentView.getAdSolutionContent();
                // if(!ApprovalStatus.approved.equals(content.getApprovalStatus())
                // &&!ApprovalStatus.cancel.equals(content.getApprovalStatus())){
                // return JsonBeanUtil.convertBeanToErrorJsonBean(null, "schedule.content.status.error");
                // }
                if (!ApprovalStatus.cancel.equals(content.getApprovalStatus())
                        && !contentService.checkContentPostionState(content.getPositionId())) {
                    try {
                        bpmProcessService.terminateProcess(request.getParameter("processId"), this.getUserName(),
                                "position disable");
                    } catch (GWFPException e) {
                        logger.error(e.getMessage(), e);
                    }
                    return JsonBeanUtil.convertBeanToErrorJsonBean(null, "advertise.content.position.invalid");
                }
                setAdvertiseSolution(infoView, contentView.getAdSolutionContent().getAdSolutionId(), true);
                infoView.setCreatOperator(userService.getAccountByUcId(infoView.getAdSolution().getUpdateOperator()));
            } catch (CRMBaseException e) {
                return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
            }
            infoView.setApprovalContentViews(Arrays.asList(getAdvertiseContent(contentView, null, request)));
        }
        return JsonBeanUtil.convertBeanToJsonBean(infoView);
    }

    @RequestMapping("viewAdSolution")
    @ResponseBody
    public JsonBean<AdApprovalInfoView> viewAdSolution(@RequestParam("id") Long id, HttpServletRequest request) {
        AdApprovalInfoView infoView = new AdApprovalInfoView();
        try {
            setAdvertiseSolution(infoView, id, true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
        }

        infoView.setApprovalContentViews(getAdvertiseContentBySolution(id, null, "", request));
        setApprovalTaskInfo(infoView);
        setScheduleAndCheckIfCanUpdate(infoView);
        setCancelRecord(infoView);
        if (AdvertiseSolutionApproveState.approving.equals(infoView.getState()) && !infoView.isCanWithdraw()) {
            infoView.setCanWithdraw(true);
            for (AdApprovalContentView contentView : infoView.getApprovalContentViews()) {
                if (contentView.isCanWithdraw()) {
                    infoView.setCanWithdraw(false);
                    break;
                }
            }
        } else if (AdvertiseSolutionApproveState.approved.equals(infoView.getState())||AdvertiseSolutionApproveState.effective.equals(infoView.getState())) {
            infoView.setCanUpdate(false);
            for (AdApprovalContentView contentView : infoView.getApprovalContentViews()) {
                if (contentView.isCanUpdate()) {
                    infoView.setCanUpdate(true);
                    break;
                }
            }
            for (AdApprovalContentView contentView : infoView.getApprovalContentViews()) {
                if (contentView.isCanCreatePO()) {
                    infoView.setCanCreatePO(false);
                    break;
                }
            }
        }

        // 处理0元测试单的情形，控制某些按钮是否显示
        if (AdvertiseType.zerotest == infoView.getAdSolution().getAdvertiseType()) {
            infoView.setCanUpdate(false);
            infoView.setCanCreatePO(false);
            for (AdApprovalContentView contentView : infoView.getApprovalContentViews()) {
                if (ApprovalStatus.approved.equals(contentView.getAdSolutionContent().getApprovalStatus())||ApprovalStatus.effective.equals(contentView.getAdSolutionContent().getApprovalStatus())) {
                    contentView.setCanUpdate(false);
                }
                contentView.setCanCreatePO(false);
            }
        }
        setStatus4OnlineApply(infoView);// 设置flag，是否可以提前上线申请和已申请的状态
        return JsonBeanUtil.convertBeanToJsonBean(infoView);
    }

    private void setScheduleAndCheckIfCanUpdate(AdApprovalInfoView infoView) {
        if (infoView != null && infoView.getApprovalContentViews() != null) {
            List<AdApprovalContentView> contentV = infoView.getApprovalContentViews();
            for (AdApprovalContentView content : contentV) {
                AdSolutionContent adContent = content.getAdSolutionContent();
                if (adContent != null) {
                    Schedules sch = scheduleService.findCurrentScheduleByAdContent(adContent);
                    if (sch != null) {
                        List<DatePeriod> periods = DatePeriodHelper.getDatePeriods(sch.getPeriodDescription());
                        int totalDays = DatePeriodHelper.getTotalDays(periods);
                        content.setSchedule(sch);
                        content.setScheduleTotalDays(totalDays);
                    }
                    if (ApprovalStatus.approved.equals(adContent.getApprovalStatus())
                            || ApprovalStatus.effective.equals(adContent.getApprovalStatus())) {
                        // 是否允许变更
                        if (StringUtils.isNotEmpty(adContent.getPeriodDescription())) {
                            Set<Date> dates = DatePeriodHelper.getAllDates(adContent.getPeriodDescription());
                            Date max = Collections.max(dates);
                            if (max.before(DateUtils.getCurrentDate())
                                    || CollectionUtils.isNotEmpty(contentService.findByOldContentIdAndContentType(
                                            adContent.getId(), ContentType.update))) {
                                content.setCanUpdate(false);
                            }
                        }
                    }
                }
            }
        }
    }

    private PositionVO convertPostion(Position position) {
        if (position == null) {
            return null;
        }
        PositionVO postionVo = new PositionVO();
        postionVo.setId(position.getId());
        postionVo.setI18nName(position.getI18nName());
        if (position.getMaterialType() != null) {
            postionVo.setMaterialType(position.getMaterialType().ordinal());
        }
        postionVo.setAreaRequired(position.getAreaRequired());
        postionVo.setSize(position.getSize());
        postionVo.setPv(position.getPv());
        postionVo.setClick(position.getClick());
        if (position.getRotationType() != null) {
            postionVo.setRotationType(position.getRotationType().ordinal());
        }
        postionVo.setSalesAmount(position.getSalesAmount());
        postionVo.setTextlinkLength(position.getTextlinkLength());
        String guideFileName = position.getGuideFileName();
        if (PatternUtil.isBlank(guideFileName)) {
            postionVo.setHasGuideFile(false);
        } else {
            postionVo.setHasGuideFile(true);
        }
        return postionVo;
    }

    private void setCancelRecord(AdApprovalInfoView infoView) {
        if (infoView != null && infoView.getAdSolution() != null) {
            infoView.setCancelRecord(cancelRecordsService.findByAdSolutionId(infoView.getAdSolution().getId()));
        }
    }

    private void setApprovalTaskInfo(AdApprovalInfoView infoView) {
        // 转换详情页任务到达信息
        AdvertiseSolution ad = infoView.getAdSolution();
        if (ad != null && ad.getTaskInfo() != null) {
            String taskInfo = ad.getTaskInfo();
            ad.setTaskInfo(gcrmTaskInfoService.convertTaskInfo(RemindType.advertise, taskInfo, currentLocale));
        }
        List<AdApprovalContentView> contents = infoView.getApprovalContentViews();
        for (AdApprovalContentView contentv : contents) {
            AdSolutionContent content = contentv.getAdSolutionContent();
            if (content != null && content.getTaskInfo() != null) {
                String taskInfo = content.getTaskInfo();
                content.setTaskInfo(gcrmTaskInfoService.convertTaskInfo(RemindType.advertise, taskInfo, currentLocale));
            }
        }
    }

    // private String convertTaskInfo(String taskInfo,Locale locale){
    // if(taskInfo!=null){
    // int index = taskInfo.indexOf(":");
    // if(index>0){
    // String key = taskInfo.substring(0,index);
    // String[] values = taskInfo.substring(index+1).split(",");
    // return MessageHelper.getMessage(key, values, locale);
    // }
    // }
    // return "";
    // }

    private void setAdvertiseSolution(AdApprovalInfoView infoView, Long adSolutionId, boolean checkAuth)
            throws CRMBaseException {
        AdvertiseSolution solution = solutionService.findById(adSolutionId);
        if (solution == null) {
            throw new CRMBaseException(MessageHelper.getMessage("advertise.solution.is.not.exist", currentLocale));
        } else {
            List<Long> userIdList = gcrmDataRoleService.findFeasiblityUserIdList(getUserId());
            // userIdList为空时表示该用户具有查询所有数据的最高权限
            if (checkAuth && CollectionUtils.isNotEmpty(userIdList)
                    && !userIdList.contains(solution.getCreateOperator())
                    && !userIdList.contains(solution.getOperator())) {
                throw new CRMBaseException(MessageHelper.getMessage("adSolution.view.unauthorized", currentLocale));
            }
            if (solution.getType() != null) {
                infoView.setType(solution.getType());
                if (AdvertiseSolutionType.update.equals(solution.getType()) && solution.getOldSolutionId() != null) {
                    AdvertiseSolution oldSolution = solutionService.findById(solution.getOldSolutionId());
                    infoView.setOldSolutionNumber(oldSolution.getNumber());
                    if (oldSolution.getContractType() != null) {
                        infoView.setOldSolutionContratType(oldSolution.getContractType().name());
                    }
                }
                String typeCode = "advertise.solution.type." + solution.getType().toString();
                infoView.setAdSolutionType(MessageHelper.getMessage(typeCode, currentLocale));
                if (AdvertiseSolutionType.create.equals(solution.getType())
                        && AdvertiseSolutionApproveState.approving.equals(solution.getApprovalStatus())) {
                    infoView.setCanWithdraw(true);
                }
            }
        }
        // Account account = accountService.findById(solution.getOperator());
        Contract contract = contractService.findByNumber(solution.getContractNumber());
        Customer customer = customerService.findByCustomerNumber(solution.getCustomerNumber());
        CustomerView customerView = customerService.customerToView(customer, currentLocale);
        CustomerI18nView customerI18nView = customerService.customerViewToI18nView(customerView, currentLocale);
        infoView.setAccount(userService.getAccountByUcId(solution.getOperator())); // 设置方案执行人
        infoView.setAdSolution(solution);
        if (contract != null) {
            infoView.setContract(contract);
            infoView.setContractUrl(getContractUrl(solution.getContractNumber()));
        }
        infoView.setCustomerI18nView(customerI18nView);
        infoView.setState(solution.getApprovalStatus());
        // infoView.setCreatOperator(accountService.findByUcid(solution.getCreateOperator())); // 设置方案提交人
        Long loginUcId = RequestThreadLocal.getLoginUserId();
        if ((null != solution.getCreateOperator() && solution.getCreateOperator().equals(loginUcId))
                || (null != infoView.getAccount() && infoView.getAccount().getUcid().equals(loginUcId))) {
            infoView.setIsOwner(true);
        } else {
            infoView.setIsOwner(false);
        }
        // set can detele status
        infoView.setCanDelete(solutionService.canDelete(solution));
    }

    private List<AdApprovalContentView> getAdvertiseContentBySolution(Long id, String actDefId, String role,
            HttpServletRequest request) {
        List<AdSolutionContentView> contentViews = null;
        String contextPath = request.getContextPath();
        if (actDefId == null || "".equals(actDefId)) {
            contentViews = contentService.findAdSolutionContentView(id, currentLocale, contextPath);
        } else {
            contentViews =
                    contentService.findAdSolutionContentView4Approval(id, getUserName(), actDefId, currentLocale,
                            contextPath);
        }
        List<AdApprovalContentView> approvalContentViews = new ArrayList<AdApprovalContentView>();
        if (contentViews != null) {
            for (AdSolutionContentView contentView : contentViews) {
                approvalContentViews.add(getAdvertiseContent(contentView, role, request));
            }
        }
        return approvalContentViews;
    }

    private AdApprovalContentView getAdvertiseContent(AdSolutionContentView contentView, String role,
            HttpServletRequest request) {
        AdApprovalContentView adApprovalContentView = new AdApprovalContentView();
        AdSolutionContent content = contentView.getAdSolutionContent();
        if (content == null) {
            return null;
        }

        adApprovalContentView.setAdSolutionContent(content);
        if (ContentType.update.equals(content.getContentType()) && content.getOldContentId() != null) {
            AdSolutionContent oldContent = contentService.findOne(content.getOldContentId());
            if (oldContent != null) {
                adApprovalContentView.setOldContentNumber(oldContent.getNumber());

                /*
                 * 变更后的方案是否可以变更不用参考旧内容的投放时间信息 Set<Date> dates =
                 * DatePeriodHelper.getAllDates(oldContent.getPeriodDescription()); Date max = Collections.max(dates);
                 * if(max.before(DateUtils.getCurrentDate())){ adApprovalContentView.setCanUpdate(false); }
                 */
                if (ApprovalStatus.approved.equals(content.getApprovalStatus())
                        && StringUtils.isEmpty(content.getPoNum()) && StringUtils.isNotEmpty(oldContent.getPoNum())) {
                    adApprovalContentView.setCanCreatePO(true);
                }
            }
        }
        contentService.updateMaterialApplyToContent(content);
        List<AdvertiseMaterial> advertiseMaterials = contentService.getAdvertiseMaterial4Content(content);
        if (CollectionUtils.isNotEmpty(advertiseMaterials)) {
            // 将云端的地址转换为服务端地址返回
            for (AdvertiseMaterial material : advertiseMaterials) {
                material.setDownloadUrl(getDownlaodURL(request.getRequestURL().toString(), material.getId()));
            }
        }
        
        List<AdvertiseMaterialMenu> advertiseMaterialMenus = contentService.getAdvertiseMaterialMenu4Content(content);
        if (CollectionUtils.isNotEmpty(advertiseMaterialMenus)) {
            // 将云端的地址转换为服务端地址返回
            for (AdvertiseMaterialMenu materialMenu : advertiseMaterialMenus) {
                materialMenu.setDownloadUrl(getMaterialMenuDownloadURL(
                        request.getRequestURL().toString(), materialMenu.getId()));
            }
        }
        
        if (role == null || !role.equals(ParticipantConstants.pm.name())) {
            AdvertiseQuotation quotation = contentView.getAdvertiseQuotation();
            if (quotation != null && quotation.getBillingModelId() != null) {
                BillingModel billingModel =
                        billingModelService.getByIdAndLocale(quotation.getBillingModelId(), currentLocale);
                if (billingModel != null) {
                    quotation.setBillingModelName(billingModel.getI18nName());
                }
            }
            adApprovalContentView.setAdvertiseQuotation(quotation);
        }
        // if(role==null || role.equals(ParticipantConstants.cash_leader.name())
        // ||role.equals(ParticipantConstants.global_cfo.name())
        // ||role.equals(ParticipantConstants.law_manager.name())
        // ||role.equals(ParticipantConstants.starter_superior.name())){
        // List<AdApprovalInserInfoView> insertInfoViews = getInsertedInfos(content);
        // adApprovalContentView.setInfoViews(insertInfoViews);
        // }
        List<ApprovalRecord> records = approvalRecordService.findByAdSolutionIdAndAdContentId(
                content.getAdSolutionId(), content.getId());
        if (CollectionUtils.isNotEmpty(records) && ApprovalStatus.approving.equals(content.getApprovalStatus())) {
            adApprovalContentView.setCanWithdraw(true);
        }

        if (null == contentView.getPosition()) {
            Position position = positionService.findById(content.getPositionId());
            adApprovalContentView.setPosition(convertPostion(position));
        } else {
            adApprovalContentView.setPosition(contentView.getPosition());
        }
        adApprovalContentView.setAdPlatformList(contentView.getAdPlatformList());
        adApprovalContentView.setSiteList(contentView.getSiteList());
        adApprovalContentView.setChannelVOList(contentView.getChannelVOList());
        adApprovalContentView.setAreaVOList(contentView.getAreaVOList());
        adApprovalContentView.setPositionVOList(contentView.getPositionVOList());

        return adApprovalContentView;
    }

    /**
     * 设置每个广告内容是否可以申请提前上线及上线申请的状态
     * 
     * @param infoView
     */
    private void setStatus4OnlineApply(AdApprovalInfoView infoView) {
        if (infoView == null || AdvertiseType.zerotest == infoView.getAdSolution().getAdvertiseType()) {
            return;
        }
        List<AdApprovalContentView> contentviews = infoView.getApprovalContentViews();
        AdSolutionContent content = null;
        if (contentviews != null) {
            for (AdApprovalContentView view : contentviews) {
                content = view.getAdSolutionContent();
                if (content != null && content.getApprovalStatus() == ApprovalStatus.approved) {
                    AdSolutionContentApply apply = onlineApplyService.findAdContentApplyByConId(content.getId());
                    List<DatePeriod> periods = DatePeriodHelper.getDatePeriods(content.getPeriodDescription());
                    if (periods != null && periods.size() > 0) {
                        if (apply != null
                                || (infoView.getContract() == null && DateUtils.compareDate(new Date(), periods.get(0)
                                        .getFrom(), DateUtils.YYYY_MM_DD) < 0)) {
                            view.setOnlineApplyAllowed(Boolean.TRUE);
                            if (apply != null) {
                                view.setOnlineApplyApprovalStatus(apply.getApprovalStatus());
                                view.setOnlineApplyId(apply.getId());
                            }
                        }
                    }

                }
            }

        }
    }

    @RequestMapping("downloadAdvertiseMaterial")
    public void downloadAdvertiseMaterial(HttpServletResponse response,
            @RequestParam("advertiseMaterialId") Long advertiseMaterialId) {

        AdvertiseMaterial material = materialService.findById(advertiseMaterialId);
        String fileName = material.getDownloadFileName();
        if (!PatternUtil.isBlank(fileName) && fileName.contains(".")) {
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            response.setContentType("image/" + fileType);
        }

        try {
            FileCopyUtils.copy(fileUploadService.download(material.getUploadFileName()), response.getOutputStream());
        } catch (Exception e) {
            logger.error("=====download advertise material fail====" + e.getMessage());
        }
    }

    @RequestMapping("/withdrawAD")
    @ResponseBody
    public JsonBean<String> withdrawAD(@RequestParam("id") Long adSolutionId) {
        try {
            approvalRecordService.withdrawAD(adSolutionId);
            return JsonBeanUtil.convertBeanToJsonBean("success");
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);
            return JsonBeanUtil.convertBeanToJsonBean("error", e.getMessage());
        }
    }

    @RequestMapping("/withdrawSingleContent")
    @ResponseBody
    public JsonBean<String> withdrawSingleContent(@RequestParam("id") Long contentId) {
        try {
            approvalRecordService.withdrawSingleContent(contentId);
            return JsonBeanUtil.convertBeanToJsonBean("success");
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);
            return JsonBeanUtil.convertBeanToJsonBean("error", e.getMessage());
        }
    }

    private String getDownlaodURL(String requestURL, Long id) {
        int urlLength = requestURL.indexOf("/adapprovalinfo");
        if (urlLength >= 0) {
            StringBuffer sb = new StringBuffer(requestURL.substring(0, urlLength));
            sb.append("/material/downloadMaterialFile?materialFileId=");
            sb.append(id);
            return sb.toString();
        }
        return "";
    }
    
    private String getMaterialMenuDownloadURL(String requestURL, Long id) {
        int urlLength = requestURL.indexOf("/adapprovalinfo");
        if (urlLength >= 0) {
            StringBuffer sb = new StringBuffer(requestURL.substring(0, urlLength));
            sb.append("/material/downloadMaterialMenuFile/false?materialMenuFileId=");
            sb.append(id);
            return sb.toString();
        }
        return "";
    }

    /**
     * 功能描述： 方案变更 创建人：yudajun 创建时间：2014-3-25 上午10:54:49 修改人：yudajun 修改时间：2014-3-25 上午10:54:49 修改备注： 参数： @return
     * 
     * @version
     */
    @RequestMapping("/changeAdSolution/{id}")
    @ResponseBody
    public JsonBean<AdApprovalInfoView> changeAdSolution(@PathVariable("id") Long solutionId, HttpServletRequest request) {
        if (!solutionService.checkChangeSolution(solutionId)) {
            String message = MessageHelper.getMessage("adSolution.change.notAllow", this.currentLocale);
            return JsonBeanUtil.convertBeanToJsonBean(null, message, JsonBeanUtil.CODE_MESSAGE);
        }

        AdvertiseSolution oldSolution = solutionService.findById(solutionId);
        oldSolution.setUpdateOperator(this.getUserId());
        AdvertiseSolution newSolution;
        try {
            newSolution = solutionService.changeAdSolution(oldSolution);
        } catch (CRMBaseException e) {
            String message = MessageHelper.getMessage("adSolution.change.failed", this.currentLocale);
            return JsonBeanUtil.convertBeanToJsonBean(null, message, JsonBeanUtil.CODE_MESSAGE);
        }
        return viewAdSolution(newSolution.getId(), request);
    }

    @RequestMapping("/cancelChangeAdSolution/{id}")
    @ResponseBody
    public JsonBean<Long> cancelChangeAdSolution(@PathVariable("id") Long solutionId) {
        return JsonBeanUtil.convertBeanToJsonBean(solutionService.cancelChangeAdSolution(solutionId));
    }

    private String getContractUrl(String number) {
        return ContractUrlUtilHelper.getContractDetailUrl(number);
    }
}