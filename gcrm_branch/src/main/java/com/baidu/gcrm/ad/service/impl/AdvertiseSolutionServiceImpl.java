package com.baidu.gcrm.ad.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.approval.record.model.ApprovalRecord;
import com.baidu.gcrm.ad.approval.record.service.IApprovalRecordService;
import com.baidu.gcrm.ad.cancel.service.IAdContentCancelRecordsService;
import com.baidu.gcrm.ad.content.dao.IAdSolutionContentRepositoryCustom;
import com.baidu.gcrm.ad.content.model.AdContentCancelRecord;
import com.baidu.gcrm.ad.content.model.AdContentCancelRecord.CancelReason;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ApprovalStatus;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ContentType;
import com.baidu.gcrm.ad.content.model.ModifyStatus;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.dao.AdvertiseMaterialRepository;
import com.baidu.gcrm.ad.dao.AdvertiseQuotationRepository;
import com.baidu.gcrm.ad.dao.AdvertiseSolutionRepository;
import com.baidu.gcrm.ad.dao.AdvertiseSolutionRepositoryCustom;
import com.baidu.gcrm.ad.dao.ContractRepository;
import com.baidu.gcrm.ad.decorators.PrepareStartAdProcessDecorator;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.model.AdvertiseSolutionApproveState;
import com.baidu.gcrm.ad.model.AdvertiseSolutionContractStatus;
import com.baidu.gcrm.ad.model.AdvertiseSolutionContractType;
import com.baidu.gcrm.ad.model.AdvertiseSolutionType;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.model.Contract.ContractState;
import com.baidu.gcrm.ad.service.IAdModifyRecordsService;
import com.baidu.gcrm.ad.service.IAdvertiseQuotationService;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.ad.web.utils.AdvertiseSolutionCondition;
import com.baidu.gcrm.ad.web.utils.AdvertiseSolutionUtils;
import com.baidu.gcrm.ad.web.vo.AdvertiseMultipleVO;
import com.baidu.gcrm.ad.web.vo.AdvertiseSolutionListView;
import com.baidu.gcrm.ad.web.vo.AdvertiseSolutionView;
import com.baidu.gcrm.bpm.dao.IProcessNameI18nRepository;
import com.baidu.gcrm.bpm.model.ProcessNameI18n;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.service.IBpmProcessStartService;
import com.baidu.gcrm.bpm.service.IGCrmTaskInfoService;
import com.baidu.gcrm.bpm.vo.RemindRequest;
import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.vo.StartProcessResponse;
import com.baidu.gcrm.bpm.web.helper.AdPlanProcessStartBean;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.common.random.IRandomCheckCallback;
import com.baidu.gcrm.common.random.IRandomStringService;
import com.baidu.gcrm.common.random.RandomType;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.mail.PositionStockContent;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.occupation1.model.PositionDate;
import com.baidu.gcrm.occupation1.service.IPositionDateRelationService;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.Publish.PublishStatus;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.schedule1.model.Schedules;
import com.baidu.gcrm.schedule1.model.Schedules.ScheduleStatus;
import com.baidu.gcrm.schedule1.service.ISchedulesService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.CurrencyType;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;
import com.baidu.gcrm.ws.cms.ICMSRequestFacade;

@Service
public class AdvertiseSolutionServiceImpl implements IAdvertiseSolutionService {

    @Autowired
    private AdvertiseSolutionRepository adSolutionDao;

    @Autowired
    private AbstractValuelistCacheService<CurrencyType> currencyTypeServiceImpl;

    @Autowired
    private IProcessNameI18nRepository processNameDao;

    @Autowired
    @Qualifier("adPlanProcessServiceImpl")
    private IBpmProcessStartService adPlanProcessService;

    @Autowired
    IAdSolutionContentService contentService;

    @Autowired
    private IPositionDateRelationService relationService;

    private List<PrepareStartAdProcessDecorator> prepareDecorators;

    @Autowired
    AdvertiseSolutionRepositoryCustom adSolutionCustomDao;

    @Autowired
    IAdSolutionContentRepositoryCustom adSolutionContentRepositoryCustomDao;

    @Autowired
    IApprovalRecordService approvalRecordService;

    @Autowired
    IAdModifyRecordsService adModifyRecordsService;

    @Autowired
    IUserService userService;

    @Autowired
    IBpmProcessService bpmProcessService;

    @Autowired
    ISchedulesService scheduleService;

    @Autowired
    private AdvertiseMaterialRepository adMaterialRepository;

    @Autowired
    IAdContentCancelRecordsService cancelRecordsService;

    @Autowired
    IAdvertiseQuotationService advertiseQuotationService;

    @Autowired
    private AdvertiseQuotationRepository quotationDao;

    @Autowired
    ICMSRequestFacade cMSRequestFacade;

    @Autowired
    IPublishService publishService;

    @Autowired
    IRandomStringService randomService;

    @Autowired
    private ContractRepository contractDao;

    @Autowired
    IGCrmTaskInfoService gcrmTaskInfoService;

    @Resource(name = "taskExecutor")
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    I18nKVService i18nService;

    @Override
    public void save(AdvertiseSolution adSolution) {
        try {
            if (adSolution != null && adSolution.getId() != null) {
                adModifyRecordsService.saveAdSolutionModifyRecords(adSolution);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adSolutionDao.save(adSolution);
    }

    @Override
    public AdvertiseSolution findById(Long id) {
        return adSolutionDao.findOne(id);
    }

    @Override
    public AdvertiseSolution findByNumber(String number) {
        return adSolutionDao.findByNumber(number);
    }

    @Override
    public AdvertiseSolutionView advrtiseSolutionToView(AdvertiseSolution advertiseSolution, LocaleConstants locale) {
        if (advertiseSolution == null) {
            return null;
        }

        AdvertiseSolutionView advertiseSolutionView = new AdvertiseSolutionView();
        advertiseSolutionView.setAdvertiseSolution(advertiseSolution);

        AdvertiseSolutionApproveState adsApprovalStatus = advertiseSolution.getApprovalStatus();
        if (adsApprovalStatus != null) {
            String adsApprovalStatusStr =
                    MessageHelper.getMessage("advertise.solution.approvale.status." + adsApprovalStatus.toString(),
                            locale);
            advertiseSolutionView.setAdvertiseSolutionApproveState(adsApprovalStatusStr);
        }

        AdvertiseSolutionType adsType = advertiseSolution.getType();
        if (adsType != null) {
            String adsTypeStr = MessageHelper.getMessage("advertise.solution.type." + adsType.toString(), locale);
            advertiseSolutionView.setAdvertiseSolutionType(adsTypeStr);
        }

        advertiseSolutionView.setCurrencyType(currencyTypeServiceImpl.getByIdAndLocale(
                advertiseSolution.getCurrencyType(), locale));

        return advertiseSolutionView;
    }

    @Override
    public void saveAndSubmitAdvertise(AdvertiseSolution adSolution, String username) throws Exception {
        adSolution.setApprovalStatus(AdvertiseSolutionApproveState.approving);
        save(adSolution);
        Long adSolutionId = adSolution.getId();
        List<AdSolutionContent> contents = contentService.findByAdSolutionId(adSolutionId);
        processContents(contents, adSolution);
        StartProcessResponse processResponse = submitProcess(adSolution, contents, username);
        // add approval record
        ApprovalRecord record = generateApprovalRecord(adSolution, processResponse);
        approvalRecordService.saveApprovalRecord(record);
    }

    private void processContents(List<AdSolutionContent> contents, AdvertiseSolution adSolution) {
        List<AdSolutionContent> approveRequiredContents = new ArrayList<AdSolutionContent>();
        List<AdSolutionContent> cancelRequiredContents = new ArrayList<AdSolutionContent>();
        for (AdSolutionContent content : contents) {
            if (contentService.isNewContent(content)) {
                approveRequiredContents.add(content);
            } else if (contentService.isModifiedContent(content)) {
                approveRequiredContents.add(content);
                processModifiedContent(cancelRequiredContents, content);
            } else { // is unchanged content
                processUnmodifiedContent(cancelRequiredContents, content);
                content.setApprovalStatus(ApprovalStatus.approved);
            }
        }
        
        updateAdContentAndBuildRelationship(approveRequiredContents);
        
        // 作废旧方案
        if (AdvertiseSolutionType.create == adSolution.getType() || CollectionUtils.isEmpty(cancelRequiredContents)) {
            return;
        }
        cancelContentsAndSolutionIfNecessary(contents.size(), adSolution.getOldSolutionId(), cancelRequiredContents);
    }

    private void processModifiedContent(List<AdSolutionContent> cancelRequiredContents, AdSolutionContent content) {
        Long oldContentId = content.getOldContentId();
        AdSolutionContent oldContent = contentService.findOne(oldContentId);
        Publish publish = publishService.findByAdContentId(oldContentId);
        if (publish == null || PublishStatus.unpublish.equals(publish.getStatus())) { // before publish
            // cancel old content
            cancelRequiredContents.add(oldContent);
            // remove old relationship
            relationService.removeRelationshipsAndReleaseStock(oldContentId);
            // release schedule and publish
            if (publish != null) {
                publishService.releasePublish(publish);
            }
            scheduleService.releaseScheduleByAdContentId(oldContentId);
        } else { // after publish
            Long oldPositionId = oldContent.getPositionId();
            if (!oldPositionId.equals(content.getPositionId())) { // not the same position, do nothing
                return;
            }
            
            Set<Date> overlappingDates = DatePeriodHelper.getOverlappingDates(oldContent.getPeriodDescription(),
                    content.getPeriodDescription());
            if (CollectionUtils.isEmpty(overlappingDates)) { // no overlapping dates, do nothing
                return;
            }
            relationService.removeRelationsAndReleaseStocksByDateIn(oldContentId, overlappingDates);
        }
    }

    private void processUnmodifiedContent(List<AdSolutionContent> cancelRequiredContents, AdSolutionContent content) {
        Long oldContentId = content.getOldContentId();
        Publish publish = publishService.findByAdContentIdandStatus(oldContentId, PublishStatus.unpublish);
        AdSolutionContent oldContent = contentService.findOne(oldContentId);
        if (publish != null && !ApprovalStatus.cancel.equals(oldContent.getApprovalStatus())) { // before publish
            // cancel old content
            cancelRequiredContents.add(oldContent);
        } 
        // replace old content id in relationship with new content id
        relationService.replaceAdContentIdInRelationship(oldContentId, content.getId());
    }
    
    private void cancelContentsAndSolutionIfNecessary(int contentsCount, Long  oldSolutionId,
            List<AdSolutionContent> cancelRequiredContents) {
        contentService.cancelContents(cancelRequiredContents);
        cancelRecordsService.saveCancelRecords(cancelRequiredContents, CancelReason.adChange);
        if (cancelRequiredContents.size() == contentsCount) { // all content in old solution is cancelled
            AdvertiseSolution oldSolution = adSolutionDao.findOne(oldSolutionId);
            oldSolution.setApprovalStatus(AdvertiseSolutionApproveState.cancel); // old作废
            oldSolution.setUpdateTime(new Date());
            adSolutionDao.save(oldSolution);
        }
    }

    private ApprovalRecord generateApprovalRecord(AdvertiseSolution adSolution, StartProcessResponse processResponse) {
        ApprovalRecord record = new ApprovalRecord();
        record.setAdSolutionId(adSolution.getId());
        record.setTaskId(processResponse.getFirstActivityId());
        record.setActivityId(processResponse.getActDefId());
        record.setCreateOperator(adSolution.getCreateOperator());
        record.setCreateTime(processResponse.getProcessStartTime());
        record.setProcessId(processResponse.getProcessId());
        return record;
    }

    private void updateAdContentAndBuildRelationship(List<AdSolutionContent> contents) {
        for (AdSolutionContent adSolutionContent : contents) {
            if (ApprovalStatus.saving.equals(adSolutionContent.getApprovalStatus())
                    || ApprovalStatus.refused.equals(adSolutionContent.getApprovalStatus())) {
                String periodDescription = adSolutionContent.getPeriodDescription();
                Long contentId = adSolutionContent.getId();
                Set<Date> dates = DatePeriodHelper.getAllDates(periodDescription);
                relationService.removeRelationshipsAndReleaseStock(contentId);
                relationService.buildRelationshipAndOccypyStock(adSolutionContent, dates);
                adSolutionContent.setApprovalStatus(ApprovalStatus.approving);
                adSolutionContent.setSubmitTime(new Date());
            }
        }
        
        contentService.save(contents);
    }

    private StartProcessResponse submitProcess(AdvertiseSolution advertiseSolution, List<AdSolutionContent> contents,
            String username) throws Exception {
        AdPlanProcessStartBean startBean = new AdPlanProcessStartBean();
        startBean.setStartUser(username);
        prepare(startBean, advertiseSolution.getId(), contents);
        StartProcessResponse respone = adPlanProcessService.startProcess(startBean);
        String foreignkey = startBean.getForeignKey();
        Long contentId = null;
        if (foreignkey != null && foreignkey.indexOf("_") > -1) {
            contentId = Long.parseLong(foreignkey.substring(foreignkey.indexOf("_") + 1));
        }
        saveAdSolutionTaskInfo(respone, advertiseSolution, contentId);
        return respone;
    }

    private void saveAdSolutionTaskInfo(StartProcessResponse respone, AdvertiseSolution advertiseSolution,
            Long contentId) {
        String info = gcrmTaskInfoService.getTaskInfo(respone.getActivities(), "advertise.solution.approval.task");
        if (StringUtils.isNotBlank(info)) {
            Long ucid = RequestThreadLocal.getLoginUserId();
            if (contentId != null) {
                AdSolutionContent content = contentService.findOne(contentId);
                content.setTaskInfo(info);
                content.setUpdateOperator(ucid);
                content.setUpdateTime(new Date());
                contentService.save(content);
                advertiseSolution.setTaskInfo("");
                advertiseSolution.setUpdateOperator(ucid);
                advertiseSolution.setUpdateTime(new Date());
                adSolutionDao.save(advertiseSolution);
            } else {
                advertiseSolution.setTaskInfo(info);
                advertiseSolution.setUpdateOperator(ucid);
                advertiseSolution.setUpdateTime(new Date());
                adSolutionDao.save(advertiseSolution);
            }
        }
    }

    private void prepare(AdPlanProcessStartBean startBean, Long adSolutionId, List<AdSolutionContent> contents) {
        String processDefId = GcrmConfig.getConfigValueByKey("ad.process.defineId");
        startBean.setPackageId(GcrmConfig.getConfigValueByKey("ad.package.id"));
        startBean.setProcessDefineId(processDefId);
        ProcessNameI18n processNameI18n =
                processNameDao.findByProcessDefIdAndLocale(processDefId, LocaleConstants.en_US.name());
        if (processNameI18n != null) {
            startBean.setProcessName(processNameI18n.getProcessName());
        }
        startBean.setAdPlanId(adSolutionId);

        for (PrepareStartAdProcessDecorator decorator : prepareDecorators) {
            decorator.prepare(startBean, adSolutionId, contents);
        }
    }

    @Override
    public void completeAdApproveAndCreateSchedule(AdvertiseSolution solution, String username) throws CRMBaseException {
        Long adSolutionId = solution.getId();
        List<AdSolutionContent> contents = contentService.findByAdSolutionId(adSolutionId);
        List<AdSolutionContent> toUpdateContents = new ArrayList<AdSolutionContent>();
        List<AdSolutionContent> toCancelContents = new ArrayList<AdSolutionContent>();
        List<AdSolutionContent> toMoveContents = new ArrayList<AdSolutionContent>();
        Date currentDate = DateUtils.getCurrentDateOfZero();
        String adSolutionNumber = solution.getNumber();
        for (AdSolutionContent content : contents) {
            AdSolutionContent oldContent = null;
            if (content.getOldContentId() != null) {
                oldContent = contentService.findOne(content.getOldContentId());
            }
            if (contentService.isNewContent(content) || contentService.isModifiedContent(content)) {
                ScheduleStatus status = processContentsAfterApproved(content, oldContent, currentDate, toMoveContents);
                scheduleService.createAndSaveSchedule(content, status);
                toUpdateContents.add(content);
            } else {
                if (oldContent == null) {
                    continue;
                }
                AdvertiseMultipleVO vo = new AdvertiseMultipleVO();
                vo.setOldSolutionId(oldContent.getAdSolutionId());
                vo.setNewSolutionId(content.getAdSolutionId());
                vo.setOldContentId(content.getOldContentId());
                vo.setNewContentId(content.getId());
                vo.setOldContentNumber(oldContent.getNumber());
                vo.setNewContentNumber(content.getNumber());
                vo.setNewSolutionNumber(adSolutionNumber);
                adSolutionContentRepositoryCustomDao.updateDataAfterSolutionChangedApproved(vo);
                if (!ApprovalStatus.cancel.equals(oldContent.getApprovalStatus())) {
                    toCancelContents.add(oldContent);
                }
            }
        }
        contentService.save(toUpdateContents);
        if (CollectionUtils.isNotEmpty(toCancelContents)) {
            contentService.cancelContents(toCancelContents);
            cancelRecordsService.saveCancelRecords(toCancelContents, CancelReason.adChange);
        }
        moveOldContentsToNewSolution(adSolutionId, toMoveContents, adSolutionNumber);
        // update ad status
        solution.setApprovalStatus(AdvertiseSolutionApproveState.approved);
        save(solution);
    }

    private void moveOldContentsToNewSolution(Long adSolutionId, List<AdSolutionContent> toMoveContents,
            String adSolutionNumber) {
        if (CollectionUtils.isEmpty(toMoveContents)) {
            return;
        }
        Set<String> numberSuffixSet = contentService.findNumberByAdSolutionId(adSolutionId);
        LoggerHelper.info(getClass(), "move contents to new solution {}", adSolutionId);
        List<Long> adContentIds = new ArrayList<Long>();
        for (AdSolutionContent content : toMoveContents) {
            content.setAdSolutionId(adSolutionId);
            String newGenNumber = AdvertiseSolutionUtils.generateContentNumber(adSolutionNumber, numberSuffixSet);
            content.setNumber(newGenNumber);
            numberSuffixSet.add(newGenNumber);
            adContentIds.add(content.getId());
        }
        contentService.save(toMoveContents); // save period description update and solution info update together
        publishService.updateAdSolutionNumberByAdContentIdIn(adContentIds, adSolutionNumber);
    }

    @Override
    public void completeSingleContentApproveAndCreateSchedule(Long adContentId, String username) {
        AdSolutionContent content = contentService.findOne(adContentId);
        AdSolutionContent oldContent = null;
        if (content.getOldContentId() != null) {
            oldContent = contentService.findOne(content.getOldContentId());
        }
        // old content has already published before changed
        Date currentDate = DateUtils.getCurrentDateOfZero();
        ArrayList<AdSolutionContent> toUpdateOldContents = new ArrayList<AdSolutionContent>();
        ScheduleStatus status = processContentsAfterApproved(content, oldContent, currentDate,
                toUpdateOldContents);
        contentService.saveAndUpdateAdContentStatus(content);
        contentService.save(toUpdateOldContents);
        // create schedule
        scheduleService.createAndSaveSchedule(content, status);
    }

    private ScheduleStatus processContentsAfterApproved(AdSolutionContent content, AdSolutionContent oldContent,
            Date currentDate, List<AdSolutionContent> toUpdateOldContents) {
        ScheduleStatus status = ScheduleStatus.confirmed;
        content.setApprovalStatus(ApprovalStatus.approved);
        content.setApprovalDate(currentDate);
        content.setUpdateOperator(RequestThreadLocal.getLoginUserId());
        content.setUpdateTime(currentDate);
        if (oldContent != null && !ApprovalStatus.cancel.equals(oldContent.getApprovalStatus())) {
            processOldContent(oldContent);
            toUpdateOldContents.add(oldContent);
            List<DatePeriod> oldPeriods = DatePeriodHelper.getDatePeriods(content.getPeriodDescription());
            List<Date> newDates = DatePeriodHelper.getDatesEqualOrAfter(oldPeriods, currentDate);
            String newPeriodStr = DatePeriodHelper.getDatePeriodStr(newDates);
            content.setPeriodDescription(newPeriodStr);
            content.setTotalDays(newDates.size());
            status = StringUtils.isEmpty(newPeriodStr) ? ScheduleStatus.released : ScheduleStatus.confirmed;
        } else {
            if (isStartDateOverdue(currentDate, content.getPeriodDescription())) {
                status = ScheduleStatus.released;
                relationService.removeRelationsAndReleaseStocksFromDate(content.getId(), currentDate);
            }
        }
        return status;
    }

    private boolean isStartDateOverdue(Date currentDate, String period) {
        int dateLength = 10;
        String startDate = period.substring(0, dateLength);
        return currentDate.after(DateUtils.getString2Date(startDate));
    }

    private void processOldContent(AdSolutionContent oldContent) {
        Date currentDate = DateUtils.getCurrentDateOfZero();
        List<DatePeriod> oldPeriods = DatePeriodHelper.getDatePeriods(oldContent.getPeriodDescription());
        List<DatePeriod> newPeriods = DatePeriodHelper.getDatePeriodStringEqualOrBefore(oldPeriods, currentDate);
        String newPeriodStr = DatePeriodHelper.getDatePeriodString(newPeriods);
        oldContent.setPeriodDescription(newPeriodStr);
        oldContent.setTotalDays(DatePeriodHelper.getTotalDays(newPeriods));
        // contentService.save(oldContent); save later in upper method
        Long oldContentId = oldContent.getId();
        Schedules schedule = scheduleService.findCurrentSchedule(oldContentId);
        schedule.setPeriodDescription(newPeriodStr);
        int completed = 1;
        schedule.setCompleted(completed);
        scheduleService.saveOrUpdateSchedule(schedule);
        publishService.unpublishBeforeDeadline(oldContentId);
        relationService.removeRelationsAndReleaseStocksFromDate(oldContentId, new Date());
    }

    @Autowired
    @Qualifier("prepareDecorators")
    public void setRegisterValidators(ArrayList<PrepareStartAdProcessDecorator> prepareDecorators) {
        this.prepareDecorators = prepareDecorators;
    }

    /**
     * 广告方案列表查询
     */
    @Override
    public Page<AdvertiseSolutionListView> findAdSolutionPage(AdvertiseSolutionCondition adSolutionCondition) {
        // 如果选择了按日期查询，先找到广告方案的id列表
        if (StringUtils.isNotBlank(adSolutionCondition.getStartDate())
                || StringUtils.isNotBlank(adSolutionCondition.getEndDate())) {
            // Date to = StringUtils.isBlank(adSolutionCondition.getEndDate()) ? null : DateUtils
            // .getString2Date(adSolutionCondition.getEndDate());
            // 以下内容应PM要求，修改为只查广告内容的时间
            // 查询方案状态为审核完成之前：根据销售录入的初次的期望开始投放时间，查询方案id列表
            List<Long> beforeIds =
                    adSolutionContentRepositoryCustomDao.findSolutionIdListBeforeApproving(
                            adSolutionCondition.getStartDate(), adSolutionCondition.getEndDate());
            // 放置条件方案id列表
            adSolutionCondition.setSolutionIdList(beforeIds);
        }

        return adSolutionCustomDao.findAdSolutionPage(adSolutionCondition);
    }

    @Override
    public void submitContentUpdateProcess(AdSolutionContent content, AdvertiseSolution solution, String username) throws Exception {
        Long adSolutionId = content.getAdSolutionId();
        AdPlanProcessStartBean startBean = new AdPlanProcessStartBean();
        startBean.setStartUser(username);
        startBean.setForeignKey(adSolutionId.toString().concat(Constants.FOREIGN_KEY_SPLIT)
                .concat(content.getId().toString()));
        List<AdSolutionContent> contents = new ArrayList<AdSolutionContent>();
        contents.add(content);
        prepare(startBean, adSolutionId, contents);
        content.setSubmitTime(new Date());

        StartProcessResponse response = adPlanProcessService.startProcess(startBean);

        LoggerHelper.info(getClass(), "修改广告内容后，重新发起广告方案审批流程，广告内容id：{}，流程id：{}", content.getId(),
                response.getProcessId());
        saveAdSolutionTaskInfo(response, solution, content.getId());
        // add approval record
        ApprovalRecord record = generateApprovalRecord(solution, response);
        record.setAdContentId(content.getId());
        approvalRecordService.saveApprovalRecord(record);
    }

    public boolean checkChangeSolution(Long solutionId) {
        Contract contract = contractDao.findByAdSolutionId(solutionId);
        if (contract != null && StringUtils.isNotBlank(contract.getNumber()) && contract.getState() != null
                && ContractState.VALID.ordinal() == contract.getState().ordinal()) {
            List<AdSolutionContent> contentList = contentService.findByAdSolutionId(solutionId);
            for (AdSolutionContent content : contentList) {
                if (StringUtils.isNotBlank(content.getPeriodDescription())) {
                    Set<Date> dates = DatePeriodHelper.getAllDates(content.getPeriodDescription());
                    Date max = Collections.max(dates);
                    if (!max.before(DateUtils.getCurrentDateOfZero())) {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 变更广告方案
     */
    public AdvertiseSolution changeAdSolution(AdvertiseSolution oldSolution) throws CRMBaseException {
        // old
        Long solutionId = oldSolution.getId();
        // new 初始化为old
        AdvertiseSolution newSolution = new AdvertiseSolution();

        AdvertiseSolutionUtils.copySolutionProperties(newSolution, oldSolution);

        // 生成新的方案编号
        String number = randomService.random(8, RandomType.random_ad_solution, new IRandomCheckCallback() {
            @Override
            public boolean exists(String randomStr) {
                AdvertiseSolution exists = adSolutionDao.findByNumber(randomStr);
                if (exists != null) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        // oldSolution.setApprovalStatus(AdvertiseSolutionApproveState.cancel);// old作废

        newSolution.setNumber(number);// 新的方案编号
        newSolution.setApprovalStatus(AdvertiseSolutionApproveState.saving);// 待提交
        newSolution.setType(AdvertiseSolutionType.update);// 变更
        newSolution.setOldSolutionId(oldSolution.getId());// 记录旧方案id
        newSolution.setContractNumber(null);// 去掉合同，等待下一次创建合同时重新生成合同
        newSolution.setId(null);// 复制重新生成
        newSolution.setUpdateTime(new Date());
        newSolution.setCreateOperator(oldSolution.getUpdateOperator());
        newSolution.setCreateTime(new Date());

        AdvertiseSolution newAdSolution = adSolutionDao.save(newSolution);// 生成新方案
        oldSolution.setApprovalStatus(AdvertiseSolutionApproveState.cancel);
        adSolutionDao.save(oldSolution);// 作废原方案

        // 内容列表
        List<AdSolutionContent> oldContentList = contentService.findByAdSolutionId(solutionId);

        // List<AdSolutionContent> operOldContentList = new ArrayList<AdSolutionContent>();

        List<AdvertiseMaterial> advertiseMaterialsNew = new ArrayList<AdvertiseMaterial>();

        for (int i = 0; i < oldContentList.size(); i++) {
            AdSolutionContent content = oldContentList.get(i);
            // 查询原广告内容对应的报价信息
            AdvertiseQuotation quotation = advertiseQuotationService.findByAdSolutionContentId(content.getId());
            // 根据旧内容上线前或上线后决定是否作废旧记录
            AdSolutionContent newcontent = new AdSolutionContent();

            AdvertiseSolutionUtils.copySolutionContentProperties(newcontent, content);

            // 生成新记录
            newcontent.setApprovalStatus(ApprovalStatus.saving);// 待提交
            newcontent.setContentType(ContentType.update);
            newcontent.setOldContentId(content.getId());// 记录旧内容的id
            newcontent.setId(null);// 复制重新生成
            newcontent.setUpdateTime(new Date());
            newcontent.setCreateOperator(oldSolution.getUpdateOperator());
            newcontent.setCreateTime(new Date());
            newcontent.setContentType(ContentType.update);
            newcontent.setUpdateOperator(oldSolution.getUpdateOperator());
            DecimalFormat formater = new DecimalFormat("00");
            String currGenerateNumberStr = formater.format(i + 1);
            newcontent.setNumber(number + "-" + currGenerateNumberStr);// 新生成广告内容编号
            newcontent.setAdSolutionId(newAdSolution.getId());
            newcontent.setModifyStatus(ModifyStatus.NOMODIFY);
            contentService.save(newcontent);// 先保存新内容

            List<AdvertiseMaterial> oldSingle = adMaterialRepository.findByAdSolutionContentId(content.getId());
            for (AdvertiseMaterial me : oldSingle) {
                AdvertiseMaterial newMe = new AdvertiseMaterial();
                newMe = me.clone();
                newMe.setId(null);
                newMe.setMaterialApplyId(null);
                newMe.setUpdateTime(new Date());
                newMe.setCreateOperator(oldSolution.getUpdateOperator());
                newMe.setCreateTime(new Date());
                newMe.setUpdateOperator(oldSolution.getUpdateOperator());
                newMe.setAdSolutionContentId(newcontent.getId());
                advertiseMaterialsNew.add(newMe);
            }
            // 保存新的广告内容对应的报价信息
            if (quotation != null) {
                AdvertiseQuotation quotationNew = new AdvertiseQuotation();
                quotationNew = quotation.clone();
                quotationNew.setUpdateTime(new Date());
                quotationNew.setCreateOperator(oldSolution.getUpdateOperator());
                quotationNew.setCreateTime(new Date());
                quotationNew.setUpdateOperator(oldSolution.getUpdateOperator());
                quotationNew.setId(null);
                quotationNew.setAdSolutionContentId(newcontent.getId());
                advertiseQuotationService.save(quotationNew);
            }
            // cancelRecordsService.saveCancel(content, CancelReason.adChange);//记录作废记录
        }
        adMaterialRepository.save(advertiseMaterialsNew);// 物料
        // contentService.save(operOldContentList);// 执行作废原内容

        return newAdSolution;
    }

    @Override
    public String updateOperator(Long id, Long ucid, String name) {
        String opertor = null;

        AdvertiseSolution adSolution = adSolutionDao.findOne(id);
        User account = userService.findByUcid(ucid);
        if (account == null || !account.getRealname().equals(name)) {
            return opertor;
        } else {

            adSolution.setOperator(ucid);
            adSolutionDao.save(adSolution);
            return account.getRealname();
        }

    }

    @Override
    public String findOperator(Long id) {
        String operatorName = adSolutionDao.findOperatorNameById(id);
        if (operatorName.isEmpty()) {
            operatorName = "找不到执行人";
        }
        return operatorName;
    }

    @Override
    public String findContractNumber(Long id) {

        return adSolutionDao.findContractNumberById(id);
    }

    @Override
    public boolean checkContractTime(Date begin, Date end, Long adsolutionid) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date perStrat = null;
        Date perEnd = null;

        boolean check = true;
        List<AdSolutionContent> contentList = contentService.findByAdSolutionId(adsolutionid);
        for (AdSolutionContent content : contentList) {
            String contenPer = content.getPeriodDescription().replaceAll(";", ",");
            String[] perDescription = contenPer.split(",");
            try {
                perStrat = format.parse(perDescription[0]);
                perEnd = format.parse(perDescription[perDescription.length - 1]);
            } catch (ParseException e) {

                e.printStackTrace();
            }

            if (begin != null && perStrat.before(begin)) {
                check = false;
                break;
            } else if (end != null && perEnd.after(end)) {
                check = false;
                break;
            }
        }

        return check;
    }

    @Override
    public void remind(Long adSolutionId) {
        AdvertiseSolution solution = adSolutionDao.findOne(adSolutionId);
        if (solution == null) {
            throw new CRMRuntimeException();
        }
        List<AdSolutionContent> contents = contentService.findByAdSolutionId(adSolutionId);
        AdvertiseSolutionApproveState solutionApprovalStatus = solution.getApprovalStatus();
        switch (solutionApprovalStatus) {
            case approved:
                remindApproved(adSolutionId, contents);
                break;
            case approving:
                remindApproving(adSolutionId, contents);
                break;
            default:
                throw new CRMRuntimeException("remind.forbidden");
        }
    }

    private void remindApproved(Long adSolutionId, List<AdSolutionContent> contents) {
        for (AdSolutionContent content : contents) {
            if (ApprovalStatus.approved.equals(content.getApprovalStatus())) {
                RemindRequest request = generateRemindRequest(RemindType.bidding, content.getId().toString());
                bpmProcessService.remindByForeignKey(request);
            }
        }
    }

    private void remindApproving(Long adSolutionId, List<AdSolutionContent> contents) {
        for (AdSolutionContent content : contents) {
            RemindRequest request;
            String contentIdStr = content.getId().toString();
            if (ApprovalStatus.approved.equals(content.getApprovalStatus())) {
                request = generateRemindRequest(RemindType.bidding, contentIdStr);
                bpmProcessService.remindByForeignKey(request);
            } else if (ApprovalStatus.approving.equals(content.getApprovalStatus())) {
                String key =
                        (content.getOldContentId() == null) ? adSolutionId.toString() : adSolutionId.toString()
                                .concat(Constants.FOREIGN_KEY_SPLIT).concat(contentIdStr);
                request = generateRemindRequest(RemindType.advertise, key);
                bpmProcessService.remindByForeignKey(request);
            }
        }
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

    @Override
    public void updateAdSolutionEnum(Long id) {
        AdvertiseSolution solution = adSolutionDao.findOne(id);
        solution.setContractType(AdvertiseSolutionContractType.single);
        solution.setContractStatus(AdvertiseSolutionContractStatus.display);
        solution.setTaskInfo(gcrmTaskInfoService.getTaskInfo("", "advertise.solution.cms.task"));
        adSolutionDao.save(solution);
    }

    @Override
    public void updateAdSolutionContract(Long id, String contractType) {
        AdvertiseSolution solution = adSolutionDao.findOne(id);
        if (contractType.equals("protocol")) {
            solution.setContractType(AdvertiseSolutionContractType.protocol);
        } else {
            solution.setContractType(AdvertiseSolutionContractType.frame);
        }
        solution.setContractStatus(AdvertiseSolutionContractStatus.display);
        solution.setTaskInfo(gcrmTaskInfoService.getTaskInfo("", "advertise.solution.cms.task"));
        adSolutionDao.save(solution);
    }

    @Override
    public int updateContractNumberById(String contractNumber, Long id) {
        return adSolutionDao.updateContractNumberById(contractNumber, id);
    }

    @Override
    public void updateAdSolutionContentContract(Long id) {
        AdvertiseSolution solution = adSolutionDao.findOne(id);
        solution.setContractType(null);
        solution.setContractStatus(AdvertiseSolutionContractStatus.Revoked);
        adSolutionDao.save(solution);

    }

    @Override
    public void updateAdSolutionContractNum(Long id, String contractNum) {
        AdvertiseSolution solution = adSolutionDao.findOne(id);
        solution.setContractNumber(contractNum);
        adSolutionDao.save(solution);

    }

    @Override
    public void updateAdSolutionStatus(Long id) {
        // 查询广告方案下所有已确认的广告内容
        List<AdSolutionContent> contents =
                contentService.findByAdSolutionIdAndApprovalStatus(id, ApprovalStatus.approved);
        if (CollectionUtils.isEmpty(contents)) {
            return;
        }
    }

    /**
     * 取消变更
     */
    @Override
    public Long cancelChangeAdSolution(Long id) {
        AdvertiseSolution newSolution = adSolutionDao.findOne(id);
        if (newSolution.getOldSolutionId() != null) {
            // 内容列表
            List<AdSolutionContent> newContentList = contentService.findByAdSolutionId(id);
            List<AdvertiseMaterial> advertiseMaterialsNew = new ArrayList<AdvertiseMaterial>();
            List<AdvertiseQuotation> quotationNew = new ArrayList<AdvertiseQuotation>();

            if (CollectionUtils.isNotEmpty(newContentList)) {
                for (AdSolutionContent content : newContentList) {
                    List<AdvertiseMaterial> newSingle = adMaterialRepository.findByAdSolutionContentId(content.getId());
                    advertiseMaterialsNew.addAll(newSingle);

                    // 查询广告内容对应的报价信息
                    AdvertiseQuotation quotation = advertiseQuotationService.findByAdSolutionContentId(content.getId());
                    quotationNew.add(quotation);
                }
            }

            // AdvertiseSolution oldSolution = adSolutionDao.findOne(newSolution.getOldSolutionId());
            // List<AdSolutionContent> oldContentList =
            // contentService.findByAdSolutionIdAndApprovalStatus(newSolution.getOldSolutionId(),ApprovalStatus.cancel);

            // List<AdSolutionContent> oldBackContentList = new ArrayList<AdSolutionContent>();

            // oldSolution.setApprovalStatus(AdvertiseSolutionApproveState.confirmed);// old作废
            // oldSolution.setUpdateTime(new Date());

            // List<AdContentCancelRecord> allRecord =
            // cancelRecordsService.findByAdSolutionId(newSolution.getOldSolutionId());
            // Map<Long,AdContentCancelRecord> rdMap = new HashMap<Long, AdContentCancelRecord>();
            // List<AdContentCancelRecord> cancelRecordList = new ArrayList<AdContentCancelRecord>();
            // for(AdContentCancelRecord rd : allRecord){
            // if(CancelReason.adChange.equals(rd.getCancelReason())){
            // rdMap.put(rd.getAdSolutionContentId(), rd);
            // cancelRecordList.add(rd);
            // }
            // }

            // for(AdSolutionContent content : oldContentList){
            // if(rdMap.containsKey(content.getId())){
            // content.setApprovalStatus(ApprovalStatus.confirmed);
            // content.setUpdateTime(new Date());
            // oldBackContentList.add(content);
            // }
            // }

            // adContentCancelRecordRepository.delete(cancelRecordList);

            // adSolutionDao.save(oldSolution);// 启动旧方案
            // contentService.save(oldBackContentList);//启动旧内容

            adSolutionDao.delete(newSolution);// 删除新方案
            contentService.deleteContents(newContentList);// 删除新内容
            adMaterialRepository.delete(advertiseMaterialsNew);// 删除新物料
            quotationDao.delete(quotationNew);// 删除新的报价信息
            return newSolution.getOldSolutionId();
        }

        return id;
    }

    @Override
    public String findAdSolutionMessage(Long id, LocaleConstants localecontent) {
        AdvertiseSolution ad = findById(id);
        String[] str = localecontent.toString().split("_");
        Locale locale = Locale.ENGLISH;
        if (str != null) {
            locale = new Locale(str[0], str[1]);
        }
        String taskInfoMessage = convertTaskInfo(ad.getTaskInfo(), locale);
        String task = taskInfoMessage.replace("已提交至商务人员", "合同已被受理，请联系商务人员");
        return task;
    }

    private String convertTaskInfo(String taskInfo, Locale locale) {
        if (taskInfo != null) {
            int index = taskInfo.indexOf(":");
            if (index > 0) {
                String key = taskInfo.substring(0, index);
                String[] values = taskInfo.substring(index + 1).split(",");
                return MessageHelper.getMessage(key, values, locale);
            }
        }
        return "";
    }

    @Override
    public void updateAdSoulutionAndScheduleStatus(Contract contract) {
        if (!ContractState.VALID.equals(contract.getState())) {
            LoggerHelper.info(getClass(), "合同[{}]不是生效状态，不能更新广告方案合同号和排期单状态。", contract.getNumber());
        }
        if (contract.getAdvertiseSolutionId() != null && StringUtils.isNotBlank(contract.getNumber())) {
            AdvertiseSolution ad = findById(contract.getAdvertiseSolutionId());
            if (ad != null) {
                ad.setContractNumber(contract.getNumber());
                ad.setContractStatus(AdvertiseSolutionContractStatus.blank);
                updateStatusToEffective(ad);
                adSolutionDao.save(ad);
                List<AdSolutionContent> contents =
                        contentService.findByAdSolutionIdAndApprovalStatus(ad.getId(), ApprovalStatus.effective);
                if (CollectionUtils.isEmpty(contents)) {
                    LoggerHelper.info(getClass(), "没有有效的广告内容");
                    return;
                }
                for (AdSolutionContent content : contents) {
                    lockScheduleAndCreatePublishIfSatisfied(content);
                }

            } else {
                LoggerHelper.err(getClass(), "找不到广告方案id，广告方案id是[{}]合同编号是{}。", contract.getAdvertiseSolutionId(),
                        contract.getNumber());
            }
        }
    }

    @Override
    public void createPo(String contractNumber, Long adsolutionid, Long ucId) {
        List<AdSolutionContent> contentList =
                contentService.findByAdSolutionIdAndApprovalStatus(adsolutionid, ApprovalStatus.approved);
        if (contentList != null) {
            for (AdSolutionContent content : contentList) {
                managePoNum(content, contractNumber, ucId);
            }
            // 表示批量创建po成功
            if (StringUtils.isNotBlank(contentList.get(0).getPoNum())) {
                AdvertiseSolution solution = adSolutionDao.findOne(adsolutionid);
                solution.setApprovalStatus(AdvertiseSolutionApproveState.effective);
                adSolutionDao.save(solution);
            }
        }
    }

    @Override
    public Long findSolutionCountCreatAndApproved(Date startDate, Date endDate) {
        return adSolutionCustomDao.findSolutionCountCreatAndApproved(startDate, endDate);
    }

    @Override
    public List<AdvertiseSolution> findSolutionCount(Date startDate, Date endDate, String operateType) {
        return adSolutionCustomDao.findSolutionCount(startDate, endDate, operateType);
    }

    /**
     * 变更作废记录
     * 
     * @param newcontent
     * @param oldcontent
     */
    void changeCancelRecords(AdSolutionContent newcontent, AdSolutionContent oldcontent) {
        List<AdContentCancelRecord> cancelList =
                cancelRecordsService.findByAdContentId(oldcontent.getId());
        if (CollectionUtils.isNotEmpty(cancelList)) {
            for (AdContentCancelRecord cancel : cancelList) {
                cancel.setAdSolutionContentId(newcontent.getId());
                cancel.setAdSolutionId(newcontent.getAdSolutionId());
                cancel.setAdNumber(newcontent.getNumber());
                cancelRecordsService.updateCancelRecord(cancel);
            }
        }

    }

    void managePoNum(AdSolutionContent content, String contractNumber, Long ucId) {
        String po = StringUtils.EMPTY;
        String oldPo = null;
        // 判断当前内容是否关联旧的广告内容
        if (ContentType.update.equals(content.getContentType()) && content.getOldContentId() != null) {
            AdSolutionContent oldContent = contentService.findOne(content.getOldContentId());
            // 如果旧内容有PO号
            if (oldContent.getPoNum() != null) {
                // cms变更po接口
                oldPo = oldContent.getPoNum();
            }
        }
        Long contentId = content.getId();
        po = cMSRequestFacade.createPO(contractNumber, contentId, ucId, oldPo);
        if (StringUtils.isNotBlank(po)) {
            content.setPoNum(po);
            content.setApprovalStatus(ApprovalStatus.effective);
            contentService.save(content);
            lockScheduleAndCreatePublishIfSatisfied(content);
        }
        if (StringUtils.isNotBlank(oldPo)) {
            publishService.autoPublishAfterChangePO(content);
        }
    }

    private void lockScheduleAndCreatePublishIfSatisfied(AdSolutionContent content) {
        boolean isSatisfied = publishService.tryLockScheduleAndCreatePublish(content);
        if (isSatisfied) {
            LoggerHelper.info(getClass(), "广告内容在合同或PO生效时，锁定排期单并生成上线单");
        } else {
            LoggerHelper.info(getClass(), "广告内容在合同或PO生效时，物料还未审核通过，广告内容编号：{}", content.getNumber());
        }
    }

    @Override
    public String changePoNum(String contractNumber, Long id, Long ucId) {
        AdSolutionContent content = contentService.findOne(id);
        managePoNum(content, contractNumber, ucId);
        contentService.saveAndUpdateAdContentStatus(content);
        return content.getPoNum();
    }

    @Override
    public String getOldcontractNumber(Long id) {
        String contractNumber = null;
        AdvertiseSolution solution = adSolutionDao.findOne(id);
        if (solution.getType().equals(AdvertiseSolutionType.update) && solution.getOldSolutionId() != null) {
            AdvertiseSolution oldAdSolution = adSolutionDao.findOne(solution.getOldSolutionId());
            if (oldAdSolution.getContractNumber() != null && oldAdSolution.getContractType() != null) {
                contractNumber = oldAdSolution.getContractNumber();
            }
        }
        return contractNumber;
    }

    @Override
    public void eraseContractNumber(String contractNumber) {
        List<AdvertiseSolution> adList = adSolutionDao.findAdSolutionByContractNumber(contractNumber);
        if (CollectionUtils.isNotEmpty(adList)) {
            for (AdvertiseSolution ad : adList) {
                if (isEraseAble(ad)) {
                    ad.setContractNumber(null);
                    adSolutionDao.save(ad);
                }
            }

        }
    }

    private boolean isEraseAble(AdvertiseSolution ad) {
        boolean eraseAble = false;
        if (ad.getType() == AdvertiseSolutionType.create) {
            List<AdSolutionContent> contents = contentService.findByAdSolutionId(ad.getId());
            if (CollectionUtils.isNotEmpty(contents)) {
                for (AdSolutionContent content : contents) {
                    if (StringUtils.isNotEmpty(content.getPoNum())) {
                        return eraseAble;
                    }
                }
            }
            eraseAble = true;
        }
        return eraseAble;
    }

    @Override
    public void moveToHistory(Long adSolutionId) {
        contentService.moveToHistory(adSolutionId);
        adSolutionCustomDao.moveToHistory(adSolutionId);
        adSolutionDao.delete(adSolutionId);
    }

    @Override
    public boolean canDelete(AdvertiseSolution adSolution) {
        // check type
        if (adSolution == null || AdvertiseSolutionType.create != adSolution.getType()) {
            return false;
        }
        Long adSolutionId = adSolution.getId();
        // check has valid ad content
        boolean hasValidAdContent = contentService.hasValidAdContent(adSolutionId);
        if (hasValidAdContent) {
            return false;
        }
        return true;
    }

    @Override
    public void notifyInfluencedAdSolution(Set<PositionDate> positionDates, 
            AdSolutionContent adContent, AdvertiseSolution adSolution) {
        if (CollectionUtils.isEmpty(positionDates)
                || adContent == null) {
            return;
        }
        List<Date> allDates = new LinkedList<Date>();
        Set<Long> positionDateIds = new HashSet<Long>();
        for (PositionDate currPositionDate : positionDates) {
            positionDateIds.add(currPositionDate.getId());
            allDates.add(currPositionDate.getDate());
        }
        
        AdvertiseSolution currAdvertiseSolution = adSolution;
        if (currAdvertiseSolution == null) {
            currAdvertiseSolution = adSolutionDao.findOne(adContent.getAdSolutionId());
            if (currAdvertiseSolution == null) {
                return;
            }
        }
        List<AdvertiseSolution> influencedAdSolutions = adSolutionCustomDao.findInfluencedAdSolution(positionDateIds,
                currAdvertiseSolution.getId());
        if (CollectionUtils.isEmpty(influencedAdSolutions)) {
            return;
        }
        Set<Long> userIds = new HashSet<Long>();
        userIds.add(currAdvertiseSolution.getOperator());
        for (AdvertiseSolution temAdvertiseSolution : influencedAdSolutions) {
            userIds.add(temAdvertiseSolution.getOperator());
        }
        Map<Long,User> users = userService.findByUcidIn(userIds);
        if (users.size() < 1) {
            return;
        }
        
        LocaleConstants localeConstant = LocaleConstants.en_US;
        String dateStr = DatePeriodHelper.getDatePeriodStr(allDates, "~", ";"); 
        User currAdSolutionUser = users.get(currAdvertiseSolution.getOperator());
        String positionNames = "";
        I18nKV i18nKV = i18nService.findByIdAndLocale(Position.class, adContent.getPositionId(), localeConstant);
        if (i18nKV != null) {
            positionNames = i18nKV.getExtraValue();
        }
            
        for (AdvertiseSolution otherAdvertiseSolution : influencedAdSolutions) {
            Long influencedAdSolutionOperator = otherAdvertiseSolution.getOperator();
            User influencedUser = users.get(influencedAdSolutionOperator);
            if (influencedUser == null) {
                continue;
            }
            final PositionStockContent mailContent = new PositionStockContent();
            Set<String> to = new HashSet<String>();
            to.add(influencedUser.getEmail());
            mailContent.setSendTo(to);
            mailContent.setReceiverName(influencedUser.getRealname());
            mailContent.setTimeRange(dateStr);
            mailContent.setAdSolutionOperator(currAdSolutionUser.getRealname());
            mailContent.setAdSolutionNumber(currAdvertiseSolution.getNumber());
            mailContent.setInfluencedAdSolutionNumber(otherAdvertiseSolution.getNumber());
            mailContent.setPositionName(positionNames.toString());
            mailContent.setLocale(localeConstant);
            
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    MailHelper.sendInfluencedAdSolutionMail(mailContent);
                }
                
            });
        }
    }
    
    public List<AdvertiseSolution> findByApprovalStatusIn(List<AdvertiseSolutionApproveState> list){
        return adSolutionDao.findByApprovalStatusIn(list);
    }

    @Override
    public void updateStatusToEffective(AdvertiseSolution solution) {
        solution.setApprovalStatus(AdvertiseSolutionApproveState.effective);
        List<AdSolutionContent> contents =
                contentService.findByAdSolutionIdAndApprovalStatus(solution.getId(), ApprovalStatus.approved);
        if (CollectionUtils.isNotEmpty(contents)) {
            for (AdSolutionContent content : contents) {
                content.setApprovalStatus(ApprovalStatus.effective);
            }
            contentService.save(contents);
        }
        adSolutionDao.save(solution);

    }

    @Override
    public void updateStatusToEffective(Long solutionId) {
        updateStatusToEffective(adSolutionDao.findOne(solutionId));

    }
}