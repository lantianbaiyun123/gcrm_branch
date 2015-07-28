package com.baidu.gcrm.ad.content.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.approval.service.IParticipantService;
import com.baidu.gcrm.ad.cancel.service.IAdContentCancelRecordsService;
import com.baidu.gcrm.ad.content.dao.IAdSolutionContentRepository;
import com.baidu.gcrm.ad.content.dao.IAdSolutionContentRepositoryCustom;
import com.baidu.gcrm.ad.content.model.AdContentBriefVO;
import com.baidu.gcrm.ad.content.model.AdContentCancelRecord;
import com.baidu.gcrm.ad.content.model.AdContentCancelRecord.CancelReason;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ApprovalStatus;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ContentType;
import com.baidu.gcrm.ad.content.model.ModifyStatus;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentListBean;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView;
import com.baidu.gcrm.ad.content.web.vo.AdcontentChangeVo;
import com.baidu.gcrm.ad.content.web.vo.DatePeriodState;
import com.baidu.gcrm.ad.material.service.IMaterialManageService;
import com.baidu.gcrm.ad.material.vo.MaterialApplyContentVO;
import com.baidu.gcrm.ad.material.vo.MaterialContentVO;
import com.baidu.gcrm.ad.material.vo.MaterialFileVO;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply.MaterialApplyState;
import com.baidu.gcrm.ad.model.AdvertiseMaterialMenu;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.model.AdvertiseSolutionApproveState;
import com.baidu.gcrm.ad.model.AdvertiseSolutionType;
import com.baidu.gcrm.ad.model.AdvertiseType;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.service.IAdModifyRecordsService;
import com.baidu.gcrm.ad.service.IAdvertiseMaterialApplyService;
import com.baidu.gcrm.ad.service.IAdvertiseMaterialService;
import com.baidu.gcrm.ad.service.IAdvertiseQuotationService;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.ad.web.utils.AdvertiseSolutionCondition;
import com.baidu.gcrm.bpm.vo.ApprovalTaskIds;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.CommonHelper;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.util.DoubleMath;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.data.bean.ADContent;
import com.baidu.gcrm.data.bean.MaterialAttachment;
import com.baidu.gcrm.data.model.ADContentStatistics;
import com.baidu.gcrm.data.service.IADContentDataStatisService;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.log.model.ModifyRecord;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.occupation1.service.IPositionDateRelationService;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.Publish.PublishStatus;
import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.model.PublishDate.PublishPeriodStatus;
import com.baidu.gcrm.publish.service.IPublishDateService;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.schedule1.model.Schedules;
import com.baidu.gcrm.schedule1.model.Schedules.ScheduleStatus;
import com.baidu.gcrm.schedule1.service.ISchedulesService;
import com.baidu.gcrm.stock.vo.AdContentBriefDTO;
import com.baidu.gcrm.stock.vo.AdContentBriefDTO.ADContentBriefStatus;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.google.common.collect.Multimap;

@Service
public class AdSolutionContentServiceImpl implements IAdSolutionContentService {

    @Autowired
    @Qualifier("billingModelServiceImpl")
    private AbstractValuelistCacheService<BillingModel> billingModelService;

    @Autowired
    IAdSolutionContentRepository contentRepository;
    
    @Autowired
    private IAdSolutionContentRepositoryCustom adContentRepositoryCustomDao;

    @Autowired
    private IAdvertiseSolutionService adSolutionService;

    @Autowired
    IAdContentCancelRecordsService adCancelRecordsService;

    @Autowired
    IPositionDateRelationService dateRelationService;

    @Autowired
    IPositionService positionService;

    @Autowired
    ISchedulesService scheduleService;

    @Autowired
    AdvertisingPlatformServiceImpl adPlatformService;

    @Autowired
    ISiteService siteService;

    @Autowired
    IAdModifyRecordsService adModifyRecordsService;

    @Autowired
    private IParticipantService partitipantService;

    @Autowired
    private I18nKVService i18nKVService;

    @Autowired
    IMaterialManageService materialManageService;

    @Autowired
    IParticipantService participantService;

    @Autowired
    IUserService userService;

    @Autowired
    IAdvertiseQuotationService adQuotationService;

    @Autowired
    IAdvertiseMaterialService adMaterialService;

    @Autowired
    IPublishService publishService;

    @Autowired
    IPublishDateService publishDateService;

    @Autowired
    IADContentDataStatisService contentDataService;

    @Autowired
    IAdvertiseMaterialApplyService materialApplyService;

    @Override
    public void saveContent(AdSolutionContentView contentView) {
        AdSolutionContent adSolutionContent = contentView.getAdSolutionContent();
        List<DatePeriod> periods = contentView.getPeriods();
        Set<Date> dates = DatePeriodHelper.getAllDates(periods);
        if (null == adSolutionContent) {
            return;
        }
        adSolutionContent.setTotalDays(Integer.valueOf(dates.size()));
        if (adSolutionContent.getId() == null) {
            adSolutionContent.setApprovalStatus(ApprovalStatus.saving);
            adSolutionContent.setContentType(ContentType.create);
            adSolutionContent.setModifyStatus(ModifyStatus.NOMODIFY);
        }
        // 记录广告内容修改记录
        adModifyRecordsService.saveAdContentModifyRecords(contentView);
        // TODO added by anhuan, why to set status modified
        adSolutionContent.setModifyStatus(ModifyStatus.MODIFYED);
        contentRepository.save(adSolutionContent);
        Long adSolutionContentId = adSolutionContent.getId();

        AdvertiseQuotation advertiseQuotation = contentView.getAdvertiseQuotation();
        // 有价格种类时才保存报价信息
        if (null != advertiseQuotation && null != advertiseQuotation.getPriceType()) {
            advertiseQuotation.setAdSolutionContentId(adSolutionContentId);
            adQuotationService.save(advertiseQuotation);
        }
        //保存物料信息
        materialManageService.saveMaterialApplyFromContent(adSolutionContent);
    }

    @Override
    public void saveContentNew(AdSolutionContentView contentView, Long oldContentId, String username) {
        AdSolutionContent adSolutionContent = contentView.getAdSolutionContent();
        ApprovalStatus approvalStatus = adSolutionContent.getApprovalStatus();
        AdvertiseQuotation advertiseQuotation = contentView.getAdvertiseQuotation();
        List<DatePeriod> periods = contentView.getPeriods();
        Set<Date> dates = DatePeriodHelper.getAllDates(periods);
        adSolutionContent.setTotalDays(dates.size());
        AdvertiseSolution adSolution = adSolutionService.findById(adSolutionContent.getAdSolutionId());
        boolean releaseScheduleRequired = false;
        // 是否是在审核通过后修改内容
        boolean isUpdatingContent = approvalStatus.equals(ApprovalStatus.approved)
                || approvalStatus.equals(ApprovalStatus.effective);
        if (isUpdatingContent) {
            adSolutionContent.setOldContentId(oldContentId);
            adSolutionContent.setApprovalStatus(approvalStatus);
            adSolutionContent.setContentType(ContentType.update);
            // 清空广告内容id
            adSolutionContent.setId(null);
            // 清空广告内容对应PO
            adSolutionContent.setPoNum(null);
            contentRepository.save(adSolutionContent);
            // 清空报价信息
            advertiseQuotation.setId(null);
            // 保存报价信息
            advertiseQuotation.setAdSolutionContentId(adSolutionContent.getId());
            adQuotationService.save(advertiseQuotation);
            try {
                Publish publish = publishService.findByAdContentId(oldContentId);
                AdSolutionContent oldContent = findOne(oldContentId);
                if (publish == null || PublishStatus.unpublish.equals(publish.getStatus())) { // before publish
                    // cancel old content
                    oldContent.setApprovalStatus(ApprovalStatus.cancel);
                    oldContent.setUpdateTime(new Date());
                    oldContent.setUpdateOperator(RequestThreadLocal.getLoginUserId());
                    contentRepository.save(oldContent);
                    AdContentCancelRecord cancelRecord = adCancelRecordsService.saveCancel(oldContent, CancelReason.change);
                    contentView.setCancelRecord(cancelRecord);
                    // remove old relationship
                    dateRelationService.removeRelationshipsAndReleaseStock(oldContentId);
                    // release schedule and publish
                    if (publish != null) {
                        publishService.releasePublish(publish);
                    }
                    releaseScheduleRequired = true;
                } else { // after publish
                    Long oldPositionId = oldContent.getPositionId();
                    if (oldPositionId.equals(adSolutionContent.getPositionId())) {
                        // not the same position, do nothing
                        Set<Date> overlappingDates = DatePeriodHelper.getOverlappingDates(
                                oldContent.getPeriodDescription(), adSolutionContent.getPeriodDescription());
                        if (CollectionUtils.isNotEmpty(overlappingDates)) {
                            // no overlapping dates, do nothing
                            dateRelationService.removeRelationsAndReleaseStocksByDateIn(oldContentId, overlappingDates);
                        }
                    }
                }
            } catch (Exception e) {
                LoggerHelper.err(getClass(), "在旧内容变更/修改后，更新新旧内容状态错误", e);
                throw new CRMRuntimeException(e);
            }
            
            // 回显
            // 修改广告方案
            adSolution.setType(AdvertiseSolutionType.update);
        }
        dateRelationService.buildRelationByAdContent(adSolutionContent);
        
        // 是否直接发起竞价排期
        boolean approveNotRequired = false;
        List<ModifyRecord> modifyRecords = new ArrayList<ModifyRecord>();
        if (adSolutionContent.getOldContentId() != null) {
            AdSolutionContentView oldContentView = findById(adSolutionContent.getOldContentId());
            // 记录广告内容修改记录
            // boolean reordModif = false;
            // 修改记录
            modifyRecords = adModifyRecordsService.saveAdContentModifyRecords(contentView, oldContentView);
            if (isUpdatingContent) { // 如果是内容修改后撤销或打回，必须审批
                approveNotRequired = isDirectSchedule(modifyRecords, approvalStatus,
                        adSolutionContent.getOldContentId(), contentView);
            }
        }
        if (releaseScheduleRequired) {
            scheduleService.releaseScheduleByAdContentId(oldContentId);
        }

        // 保存广告方案
        adSolutionService.save(adSolution);

        // 在变更广告内容，保存修改记录时,fix不能记录ModifiedDataId的问题
        if (CollectionUtils.isNotEmpty(modifyRecords)) {
            for (ModifyRecord record : modifyRecords) {
                if (record.getModifiedDataId() == null) {
                    if (AdSolutionContent.class.getSimpleName().equals(record.getTableName())) {
                        record.setModifiedDataId(adSolutionContent.getId());
                    } else if (AdvertiseQuotation.class.getSimpleName().equals(record.getTableName())) {
                        record.setModifiedDataId(advertiseQuotation.getId());
                    }
                }
            }
        }
        
        adSolutionContent.setModifyStatus(ModifyStatus.MODIFYED);
        contentRepository.save(adSolutionContent);
        //保存物料信息
        materialManageService.saveMaterialApplyFromContent(adSolutionContent);
        // 设置广告内容类型为变更
        contentView.setType(AdvertiseSolutionType.update);
        // 如果是发起广告方案审批, 修改广告方案状态和类型并保存
        if (!approveNotRequired) {
            contentView.setApprovalStatus(AdvertiseSolutionApproveState.approving);
        }

        // 发起修改后的广告内容的流程
        startNewContentProcess(adSolutionContent, adSolution, username, approveNotRequired);
    }

    /**
     * 发起修改后的广告内容的流程
     * 
     * @param content 广告内容
     * @param modifyRecords 广告内容修改记录
     * @param oldApprovalStatus 旧的广告内容状态
     * @param username 发起人
     */
    private void startNewContentProcess(AdSolutionContent content, AdvertiseSolution solution, String username,
            boolean approveNotRequired) {
        // 广告内容销售确认状态, 需要判断是否能够直接发起竞价排期流程
        try {
            // 直接生成排期单
            if (approveNotRequired) {
                dateRelationService.removeRelationsAndReleaseStocksFromDate(content.getOldContentId(),
                        DateUtils.getCurrentDateOfZero());
                scheduleService.createAndSaveSchedule(content);
                content.setApprovalStatus(ApprovalStatus.approved);
                updateMaterialApplyState(content);
                content.setApprovalDate(new Date());
                content.setTaskInfo(StringUtils.EMPTY);
                saveAndUpdateAdContentStatus(content);
            } else {
                // 发起广告方案审批流程
                content.setApprovalStatus(ApprovalStatus.approving);
                solution.setApprovalStatus(AdvertiseSolutionApproveState.approving);
                contentRepository.save(content);
                adSolutionService.save(solution);
                adSolutionService.submitContentUpdateProcess(content, solution, username);
            }
            
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "发起修改后的广告内容的流程失败，内容id：{}，错误原因：{}", content.getId(), e.getMessage());
        }
    }

    private void updateMaterialApplyState(AdSolutionContent content) {
        List<AdvertiseMaterialApply> applys =
                materialApplyService.findMaterialApplyByContentIdAndState(content.getId(),
                        MaterialApplyState.create);
        if (CollectionUtils.isNotEmpty(applys)) {
            for (AdvertiseMaterialApply apply : applys) {
                apply.setApplyState(MaterialApplyState.confirm);
                materialApplyService.save(apply);
            }
        }
    }

    /**
     * 判断是否跳过方案审批，直接发起竞价排期流程，需满足条件：
     * 只修改了投放时间字段（可以有总价字段）
     * 
     * @param modifyRecords
     * @return
     */
    private boolean isDirectSchedule(List<ModifyRecord> modifyRecords, ApprovalStatus oldStatus, Long oldContentId,
            AdSolutionContentView adContentView) {
        // 非确认状态，或者有未释放的排期单的广告内容返回false
        if (CollectionUtils.isNotEmpty(scheduleService.findByAdContentIdAndStatusNot(oldContentId,
                        ScheduleStatus.released))) {
            return false;
        }
        if (CollectionUtils.isEmpty(modifyRecords)) {
            return false;
        }
        List<String> keyList = new ArrayList<String>();
        // 投放时间key
        String periodDesc = "periodDescription";
        // 预算（修改投放时间的时候可能会影响预算、总投放量、日投放量）
        String budget = "budget";
        String totalAmount = "totalAmount";
        String dailyAmount = "dailyAmount";
        keyList.add(periodDesc);
        keyList.add(budget);
        keyList.add(totalAmount);
        keyList.add(dailyAmount);
        // 广告内容和刊例价表
        List<String> classNameList = new ArrayList<String>();
        classNameList.add(AdSolutionContent.class.getSimpleName());
        classNameList.add(AdvertiseQuotation.class.getSimpleName());
        // 判断是否仅仅修改了投放时间
        Map<String, String[]> dataPeriodMap = CommonHelper.isKeysOnlyModified(modifyRecords, keyList, classNameList);
        // 只修改了总价，则返回false
        if (null == dataPeriodMap) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delContent(Long id) {
        AdSolutionContent content = contentRepository.findOne(id);
        ApprovalStatus approvalStatus = content.getApprovalStatus();
        if (approvalStatus != null && approvalStatus != ApprovalStatus.saving
                && approvalStatus != ApprovalStatus.refused) {
            return false;
        }
        // 记录广告内容修改记录
        adModifyRecordsService.removeAdContentModifyRecords(content);
        adQuotationService.deleteByAdSolutionContentId(id);
        adMaterialService.deleteByAdSolutionContentId(id);
        contentRepository.delete(id);
        // 删除内容相关物料
        deleteMaterial(id);
        return true;
    }

    private void deleteMaterial(Long id) {
        List<AdvertiseMaterialApply> applys = materialApplyService.findByAdSolutionContentId(id);
        if (CollectionUtils.isNotEmpty(applys)) {
            for (AdvertiseMaterialApply apply : applys) {
                materialManageService.delete(apply);
            }
        }
    }

    @Override
    public AdSolutionContentView findById(Long id) {
        AdSolutionContentView contentView = new AdSolutionContentView();
        AdSolutionContent adContent = contentRepository.findOne(id);
        contentView.setAdSolutionContent(adContent);
        contentView.setAdvertiseQuotation(adQuotationService.findByAdSolutionContentId(id));
        contentView.setDatePeriodState(getDatePeriodStates(adContent));
        return contentView;
    }

    @Override
    public List<AdSolutionContentView> findByAdSolutionId(Long adSolutionId, LocaleConstants locale, String contextPath) {
        List<AdSolutionContentView> viewList = new ArrayList<AdSolutionContentView>();

        List<AdSolutionContent> adSolutionContentList = contentRepository.findByAdSolutionId(adSolutionId);
        if (adSolutionContentList != null) {
            for (AdSolutionContent adSolutionContent : adSolutionContentList) {
                viewList.add(processAdSolutionContent(adSolutionContent, locale, contextPath));
            }
        }

        return viewList;
    }

    @Override
    public List<AdSolutionContentView> findAdSolutionContentView(Long adSolutionId, LocaleConstants locale,
            String contextPath) {
        List<AdSolutionContentView> viewList = new ArrayList<AdSolutionContentView>();
        List<AdSolutionContent> adSolutionContentList = contentRepository.findByAdSolutionId(adSolutionId);
        if (adSolutionContentList != null) {
            for (AdSolutionContent adSolutionContent : adSolutionContentList) {
                if (!adSolutionContent.getApprovalStatus().equals(ApprovalStatus.cancel)) {
                    // 广告内容查看VO数据
                    AdSolutionContentView adContentView =
                            processAdSolutionContentView(adSolutionContent, locale, contextPath);
                    // 补全广告内容修改时VO的数据
                    AdSolutionContentView adContentEditView =
                            processAdSolutionContent(adSolutionContent, locale, contextPath);
                    adContentView.setAdvertiseQuotation(adContentEditView.getAdvertiseQuotation());
                    adContentView.setAdvertiseCodeMaterial(adContentEditView.getAdvertiseCodeMaterial());
                    adContentView.setEmptyMaterial(adContentEditView.isEmptyMaterial());
                    adContentView.setAdPlatformList(adContentEditView.getAdPlatformList());
                    adContentView.setSiteList(adContentEditView.getSiteList());
                    adContentView.setChannelVOList(adContentEditView.getChannelVOList());
                    adContentView.setAreaVOList(adContentEditView.getAreaVOList());
                    adContentView.setPositionVOList(adContentEditView.getPositionVOList());
                    adContentView.setPosition(adContentEditView.getPosition());
                    viewList.add(adContentView);
                }
            }
        }
        return viewList;
    }

    private AdSolutionContentView processAdSolutionContentView(AdSolutionContent adSolutionContent,
            LocaleConstants locale, String contextPath) {
        AdvertisingPlatform adPlatform =
                (null == adSolutionContent.getProductId()) ? null : adPlatformService.getByIdAndLocale(
                        adSolutionContent.getProductId(), locale);
        String productName = (null == adPlatform) ? "" : adPlatform.getI18nName();
        adSolutionContent.setProductName(productName);

        adSolutionContent.setSiteName(i18nKVService.getI18Info(Site.class, adSolutionContent.getSiteId(), locale));
        adSolutionContent.setChannelName(i18nKVService.getI18Info(Position.class, adSolutionContent.getChannelId(),
                locale));
        adSolutionContent.setAreaName(i18nKVService.getI18Info(Position.class, adSolutionContent.getAreaId(), locale));
        adSolutionContent.setPositionName(i18nKVService.getI18Info(Position.class, adSolutionContent.getPositionId(),
                locale));
        AdSolutionContentView contentView = new AdSolutionContentView();
        contentView.setAdSolutionContent(adSolutionContent);
        contentView.setDatePeriodState(getDatePeriodStates(adSolutionContent));
        Long adSolutionContentId = adSolutionContent.getId();
        contentView.setAdvertiseQuotation(adQuotationService.findByAdSolutionContentId(adSolutionContentId));
        updateMaterialApplyToContent(adSolutionContent);
        setDownLoadUrlToMaterial(adSolutionContentId, contentView, contextPath);
        return contentView;
    }

    @Override
    public List<AdSolutionContentView> findAdSolutionContentView4Approval(Long adSolutionId, String username,
            String actDefId, LocaleConstants locale, String contextPath) {
        List<AdSolutionContentView> viewList = new ArrayList<AdSolutionContentView>();

        List<AdSolutionContent> adSolutionContentList = contentRepository.findByAdSolutionId(adSolutionId);
        if (adSolutionContentList != null) {
            for (AdSolutionContent adSolutionContent : adSolutionContentList) {
                if (adSolutionContent.getApprovalStatus().equals(ApprovalStatus.approving)
                        && (!ContentType.update.equals(adSolutionContent.getContentType()) || (ContentType.update
                                .equals(adSolutionContent.getContentType()) && ModifyStatus.MODIFYED
                                .equals(adSolutionContent.getModifyStatus())))) {
                    if (actDefId != null) {
                        Participant partitipant = null;
                        String platformId = adSolutionContent.getProductId().toString();
                        if (actDefId.equals(ApprovalTaskIds.act2.name())
                                || actDefId.startsWith(ApprovalTaskIds.act2.name())) {
                            partitipant =
                                    partitipantService.findParticipant(username, platformId, DescriptionType.platform,
                                            ParticipantConstants.pm_leader.toString());
                            if (partitipant == null) {
                                continue;
                            }
                        } else if (actDefId.equals(ApprovalTaskIds.act3.name())
                                || actDefId.startsWith(ApprovalTaskIds.act3.name())) {
                            partitipant =
                                    partitipantService.findParticipant(username, platformId, DescriptionType.platform,
                                            ParticipantConstants.cash_leader.toString());
                            if (partitipant == null) {
                                continue;
                            }
                        } else if (actDefId.equals(ApprovalTaskIds.act5.name())
                                || actDefId.startsWith(ApprovalTaskIds.act5.name())) {
                            partitipant =
                                    partitipantService.findParticipant(username, platformId, DescriptionType.platform,
                                            ParticipantConstants.dept_manager.toString());
                            if (partitipant == null) {
                                continue;
                            }
                        }
                    }
                    viewList.add(processAdSolutionContentView(adSolutionContent, locale, contextPath));
                }
            }
        }
        return viewList;
    }

    @Override
    public AdSolutionContentView findByAdSolutionContentId(Long contentId, LocaleConstants locale, String contextPath) {
        if (null == contentId) {
            return new AdSolutionContentView();
        }
        AdSolutionContent adSolutionContent = contentRepository.findOne(contentId);
        return processAdSolutionContent(adSolutionContent, locale, contextPath);
    }

    private AdSolutionContentView processAdSolutionContent(AdSolutionContent adSolutionContent, LocaleConstants locale,
            String contextPath) {
        AdSolutionContentView contentView = new AdSolutionContentView();
        contentView.setDatePeriodState(getDatePeriodStates(adSolutionContent));
        if (null == adSolutionContent || null == adSolutionContent.getId()) {
            return contentView;
        }
        Long adSolutionContentId = adSolutionContent.getId();
        contentView.setAdSolutionContent(adSolutionContent);
        AdvertiseQuotation quotation = adQuotationService.findByAdSolutionContentId(adSolutionContentId);
        BillingModel billingModel =
                (null == quotation || null == quotation.getBillingModelId()) ? null : billingModelService
                        .getByIdAndLocale(quotation.getBillingModelId(), locale);
        if (null != billingModel) {
            quotation.setBillingModelName(billingModel.getI18nName());
        }
        contentView.setAdvertiseQuotation(quotation);
        updateMaterialApplyToContent(adSolutionContent);
        setDownLoadUrlToMaterial(adSolutionContentId, contentView, contextPath);

        if (locale != null) {
            processPositionInfo(contentView, locale);
        }

        return contentView;
    }

    public void updateMaterialApplyToContent(AdSolutionContent content) {
        if (content == null || CollectionUtils.isNotEmpty(content.getMaterialApplyList())) {
            return;
        } else {
            List<AdvertiseMaterialApply> applys = null;
            ApprovalStatus status = content.getApprovalStatus();
            if (ApprovalStatus.saving.equals(status) || ApprovalStatus.approving.equals(status)
                    || ApprovalStatus.refused.equals(status)) {
                applys =
                        materialApplyService.findMaterialApplyByContentIdAndState(content.getId(),
                                MaterialApplyState.create);
            } else {
                applys =
                        materialApplyService.findMaterialApplyByContentIdAndState(content.getId(),
                                MaterialApplyState.confirm);
            }
            if (CollectionUtils.isNotEmpty(applys)) {
                for (AdvertiseMaterialApply apply : applys) {
                    apply.setMaterialList(adMaterialService.findByMaterialApplyId(apply.getId()));
                    apply.setMaterialMenuList(materialManageService.findMaterialMenusByApplyId(apply.getId()));
                }
                content.setMaterialApplyList(applys);
            }
        }
    }
    
    public boolean isContentMaterialFull(AdSolutionContent content) {
        if (CollectionUtils.isEmpty(content.getMaterialApplyList())) {
            content.setMaterialApplyList(materialApplyService.findByAdSolutionContentIdAndApplyStateNot(content.getId(),MaterialApplyState.cancel));
        }
        return CollectionUtils.isNotEmpty(content.getMaterialApplyList());
    }

    private void setDownLoadUrlToMaterial(Long adSolutionContentId, AdSolutionContentView contentView,
            String contextPath) {
        if (null == adSolutionContentId) {
            return;
        }
        List<AdvertiseMaterial> materials = getAdvertiseMaterial4Content(contentView.getAdSolutionContent());
        List<AdvertiseMaterialMenu> materialMenus =
                getAdvertiseMaterialMenu4Content(contentView.getAdSolutionContent());
        if (CollectionUtils.isNotEmpty(materials)) {
            for (AdvertiseMaterial temAdvertiseMaterial : materials) {

                if (contextPath != null) {
                    StringBuilder requestUrlStr = new StringBuilder();
                    requestUrlStr.append(contextPath).append("/material/downloadMaterialFile?materialFileId=")
                            .append(temAdvertiseMaterial.getId().toString());
                    temAdvertiseMaterial.setDownloadUrl(requestUrlStr.toString());
                }
            }

        }
        if (CollectionUtils.isNotEmpty(materialMenus)) {
            for (AdvertiseMaterialMenu temMenu : materialMenus) {

                if (contextPath != null) {
                    StringBuilder requestUrlStr = new StringBuilder();
                    requestUrlStr.append(contextPath)
                            .append("/material/downloadMaterialMenuFile/false?materialMenuFileId=")
                            .append(temMenu.getId().toString());
                    temMenu.setDownloadUrl(requestUrlStr.toString());
                }
            }

        }

    }

    private void processPositionInfo(AdSolutionContentView contentView, LocaleConstants locale) {
        AdSolutionContent adSolutionContent = contentView.getAdSolutionContent();
        if (null == adSolutionContent) {
            return;
        }
        Long productId = adSolutionContent.getProductId();
        Long siteId = adSolutionContent.getSiteId();

        Long channelId = adSolutionContent.getChannelId();
        Long areaId = adSolutionContent.getAreaId();

        List<AdvertisingPlatform> adPlatformList = adPlatformService.getAllByLocale(locale);
        List<Site> siteList = siteService.findSiteByAdPlatform(productId, locale);
        contentView.setAdPlatformList(adPlatformList);
        contentView.setSiteList(siteList);
        contentView.setChannelVOList(positionService.findChannelBySiteIdAndStatus(productId, siteId, locale, null));
        contentView.setAreaVOList(positionService.findByParentIdAndStatus(channelId, locale, null));
        contentView.setPositionVOList(positionService.findByParentIdAndStatus(areaId, locale, null));
        contentView.setPosition(positionService.findPositionVOById(adSolutionContent.getPositionId()));

        processOtherI18nName(adSolutionContent, adPlatformList, siteList, locale);
    }

    private void processOtherI18nName(AdSolutionContent adSolutionContent, List<AdvertisingPlatform> adPlatformList,
            List<Site> siteList, LocaleConstants locale) {

        Long productId = (null == adSolutionContent.getProductId()) ? -1L : adSolutionContent.getProductId();
        Long siteId = (null == adSolutionContent.getSiteId()) ? -1L : adSolutionContent.getSiteId();
        Long channelId = (null == adSolutionContent.getChannelId()) ? -1L : adSolutionContent.getChannelId();
        Long areaId = (null == adSolutionContent.getAreaId()) ? -1L : adSolutionContent.getAreaId();
        Long positionId = (null == adSolutionContent.getPositionId()) ? -1L : adSolutionContent.getPositionId();

        if (CollectionUtils.isNotEmpty(adPlatformList) && !productId.equals(-1L)) {
            for (AdvertisingPlatform temAdvertisingPlatform : adPlatformList) {
                long temPlatformId = temAdvertisingPlatform.getId().longValue();
                if (productId.longValue() == temPlatformId) {
                    adSolutionContent.setProductName(temAdvertisingPlatform.getI18nName());
                    break;
                }
            }
        }

        if (CollectionUtils.isNotEmpty(siteList) && !siteId.equals(-1L)) {
            for (Site temSite : siteList) {
                long temSiteId = temSite.getId().longValue();
                if (temSiteId == siteId.longValue()) {
                    adSolutionContent.setSiteName(temSite.getI18nName());
                    break;
                }
            }
        }
        List<Long> positionIds = new ArrayList<Long>();
        positionIds.add(channelId);
        positionIds.add(areaId);
        positionIds.add(positionId);

        List<PositionVO> positionVOs = positionService.findI18nByPositionIds(positionIds, locale);
        if (null != positionVOs) {
            for (PositionVO temPositionVO : positionVOs) {
                if (null == temPositionVO) {
                    continue;
                }
                String i18nName = temPositionVO.getI18nName();
                long temPositionId = temPositionVO.getId().longValue();
                if (temPositionId == channelId.longValue()) {
                    adSolutionContent.setChannelName(i18nName);
                } else if (temPositionId == areaId.longValue()) {
                    adSolutionContent.setAreaName(i18nName);
                } else if (temPositionId == positionId.longValue()) {
                    adSolutionContent.setPositionName(i18nName);
                }
            }
        }
    }

    @Override
    public List<AdSolutionContent> findByAdSolutionId(Long adSolutionId) {
        return contentRepository.findByAdSolutionId(adSolutionId);
    }

    @Override
    public void save(List<AdSolutionContent> contents) {
        contentRepository.save(contents);
    }

    @Override
    public void save(AdSolutionContent content) {
        contentRepository.save(content);
    }
    
    @Override
    public AdSolutionContent findOne(Long id) {
        return contentRepository.findOne(id);
    }

    @Override
    public Set<String> findNumberByAdSolutionId(Long adSolutionId) {
        Set<String> numberSuffixSet = new HashSet<String>();
        List<String> numbers = contentRepository.findNumberByAdSolutionId(adSolutionId);
        if (CollectionUtils.isEmpty(numbers)) {
            return numberSuffixSet;
        }

        for (String number : numbers) {
            String numberSuffix = number.substring(number.indexOf("-") + 1);
            if (!numberSuffixSet.contains(numberSuffix)) {
                numberSuffixSet.add(numberSuffix);
            } else {
                LoggerHelper.err(getClass(), "方案id：{}有重复的number：{}。", adSolutionId, number);
            }
        }
        return numberSuffixSet;
    }

    @Override
    public List<AdSolutionContent> findByAdContentIds(Collection<Long> adContentIds) {
        return contentRepository.findByIdIn(adContentIds);
    }

    @Override
    public List<Object[]> findOperatorByIdIn(Collection<Long> adContentIds) {
        return contentRepository.findOperatorByIdIn(adContentIds);
    }

    /**
     * 根据广告方案id，查询广告内容
     */
    @Override
    public List<AdSolutionContentListBean> findListByAdSolutionId(Long adSolutionId, LocaleConstants locale) {
        List<AdSolutionContentListBean> viewList = new ArrayList<AdSolutionContentListBean>();

        List<AdSolutionContent> adSolutionContentList = contentRepository.findByAdSolutionId(adSolutionId);
        if (adSolutionContentList != null) {
            for (AdSolutionContent adSolutionContent : adSolutionContentList) {
                processAdSolutionContentI18nName(adSolutionContent, locale);

                // 处理一下位置信息
                StringBuilder positionName = new StringBuilder();
                positionName.append(adSolutionContent.getProductName()).append("-")
                        .append(adSolutionContent.getSiteName()).append("-").append(adSolutionContent.getChannelName())
                        .append("-").append(adSolutionContent.getAreaName()).append("-")
                        .append(adSolutionContent.getPositionName());

                adSolutionContent.setPositionName(positionName.toString());

                AdSolutionContentListBean bean = new AdSolutionContentListBean();
                bean.setAdSolutionContent(adSolutionContent);
                List<Schedules> scheduleList =
                        scheduleService.findByAdContentIdAndStatusNot(adSolutionContent.getId(),
                                ScheduleStatus.released);// 广告内容id查询 非释放状态的排期单
                if (scheduleList != null && scheduleList.size() > 0) {
                    Schedules schedule = scheduleList.get(0);
                    bean.setSchedulePeriod(schedule.getPeriodDescription());// 排期单投放时段
                }
                bean.setPoNumber(adSolutionContent.getPoNum());// po编号
                viewList.add(bean);
            }
        }
        return viewList;
    }

    /**
     * 功能描述： 处理国际化其他字段 创建人：yudajun 创建时间：2014-3-19 下午3:57:29 修改人：yudajun 修改时间：2014-3-19 下午3:57:29 修改备注： 参数： @param
     * adSolutionContent 参数： @param locale
     * 
     * @version
     */
    private void processAdSolutionContentI18nName(AdSolutionContent adSolutionContent, LocaleConstants locale) {
        Long productId = adSolutionContent.getProductId();
        List<AdvertisingPlatform> adPlatformList = adPlatformService.getAllByLocale(locale);
        List<Site> siteList = siteService.findSiteByAdPlatform(productId, locale);
        processOtherI18nName(adSolutionContent, adPlatformList, siteList, locale);
    }

    @Override
    public List<AdSolutionContent> findByAdSolutionIdAndApprovalStatus(Long adSolutionId, ApprovalStatus status) {
        return contentRepository.findByAdSolutionIdAndApprovalStatus(adSolutionId, status);
    }

    @Override
    public void cancelContents(List<AdSolutionContent> contents) {
        if (CollectionUtils.isEmpty(contents)) {
            return;
        }
        for (AdSolutionContent content : contents) {
            content.setApprovalStatus(ApprovalStatus.cancel);
            content.setUpdateTime(new Date());
            content.setUpdateOperator(RequestThreadLocal.getLoginUserId());
        }
        contentRepository.save(contents);
    }

    @Override
    public void saveAndUpdateAdContentStatus(AdSolutionContent content) {
        contentRepository.save(content);
        List<AdSolutionContent> contents = contentRepository.findByAdSolutionId(content.getAdSolutionId());
        ApprovalStatus status = content.getApprovalStatus();
        // 找出广告方案的广告内容的最小状态
        for (AdSolutionContent adContent : contents) {
            if (adContent.getId() != content.getId()) {
                if (adContent.getApprovalStatus().ordinal() < status.ordinal()) {
                    status = adContent.getApprovalStatus();
                }
            }
        }
        // 仅当前广告内容的状态为作废状态，或者等于最小状态时，才需要更新广告方案状态为最小状态
        if (ApprovalStatus.cancel.equals(content.getApprovalStatus()) || status.equals(content.getApprovalStatus())) {
            AdvertiseSolution solution = adSolutionService.findById(content.getAdSolutionId());
            solution.setApprovalStatus(AdvertiseSolutionApproveState.valueOf(status.ordinal()));
            adSolutionService.save(solution);
        }
    }

    @Override
    public Long countByContentId(Long id, Long adSolutionId) {
        return contentRepository.countByContentId(id, adSolutionId);
    }

    @Override
    public Contract findContractByContentId(Long adContentId) {
        return contentRepository.findContractByContentId(adContentId);
    }

    @Override
    public Contract findWriteBackContractByContentId(Long adContentId) {
        return contentRepository.findWriteBackContractByContentId(adContentId);
    }

    @Override
    public Long findAdContentAmountByAdPlatformId(Long adPlatformId) {
        // find submit content amount
        Long submitAmount =
                contentRepository.findSubmitAmountByAdPlatformId(adPlatformId, ApprovalStatus.approving,
                        ApprovalStatus.approved,ApprovalStatus.effective);

        return submitAmount.longValue();

    }

    public boolean checkContentPostionState(Long positionId) {
        Position position = positionService.findById(positionId);
        if (position != null && PositionStatus.enable.equals(position.getStatus())) {
            return true;
        }
        return false;
    }

    @Override
    public List<AdSolutionContent> findByPositionIdIn(Collection<Long> positionIds) {
        return contentRepository.findByPositionIdIn(positionIds);
    }

    @Override
    public void cancelContents(List<AdSolutionContent> contents, CancelReason reason) {
        if (CollectionUtils.isEmpty(contents)) {
            return;
        }
        for (AdSolutionContent content : contents) {
            ApprovalStatus approvalStatus = content.getApprovalStatus();
            if (ApprovalStatus.cancel.equals(approvalStatus) || ApprovalStatus.refused.equals(approvalStatus)
                    || ApprovalStatus.saving.equals(approvalStatus)) {
                continue;
            }
            content.setApprovalStatus(ApprovalStatus.cancel);
            content.setUpdateOperator(RequestThreadLocal.getLoginUserId());
            content.setUpdateTime(new Date());
            saveAndUpdateAdContentStatus(content);
        }
        adCancelRecordsService.saveCancelRecords(contents, reason);
    }

    @Override
    public List<MaterialApplyContentVO> findMaterialApplyContent(AdvertiseSolutionCondition adSolutionCondition) {
        return adContentRepositoryCustomDao.findMaterialApplyContent(adSolutionCondition);
    }

    @Override
    public void getChangeContent(Long adContentId) {
        Set<String> sendTo = new HashSet<String>();
        AdcontentChangeVo vo = adContentRepositoryCustomDao.getChangeContent(adContentId);
        if (vo.getNewpositionId() != null) {
            vo.setNewpositionName(i18nKVService.getI18Info(Position.class, vo.getNewpositionId(), LocaleConstants.zh_CN));
        }
        if (vo.getOldpositionId() != null) {
            vo.setOldpositionName(i18nKVService.getI18Info(Position.class, vo.getOldpositionId(), LocaleConstants.zh_CN));

        }
        generatePdtime(vo);
        generatePlatSiteNames(vo);
        MaterialContentVO contentVO = materialManageService.findAdContVoMaterByContId(adContentId);
        if (contentVO != null) {
            vo.setNewcontentVO(contentVO);
        }
        MaterialContentVO oldcontentVO = materialManageService.findAdContVoMaterByContId(vo.getOldcontentId());
        if (contentVO != null) {
            vo.setOldcontentVO(oldcontentVO);
        }
        if (vo.getProductId() != null) {
            Participant part =
                    participantService.findByKeyAndDescAndParticId(vo.getProductId().toString(),
                            DescriptionType.platform, ParticipantConstants.pm_leader.toString());
            if (part != null) {
                User use = userService.findByUsername(part.getUsername());
                if (use != null) {
                    sendTo.add(use.getEmail());
                    vo.setSendTo(sendTo);
                    vo.setOperator(use.getRealname());
                }
            }
        }
        if (vo.getAdsolutionId() != null) {
            User aduse = userService.findByUcid(adSolutionService.findById(vo.getAdsolutionId()).getOperator());
            if (aduse != null) {
                vo.setAdOperator(aduse.getRealname());
            }
        }

        if ((vo.getOldonlineNum() != null && vo.getNewonlineNum() != null)
                && vo.getOldonlineNum().equals(vo.getNewonlineNum())) {
            vo.setNewonlineNum("无变化");
        }

        if ((vo.getOldPdname() != null && vo.getNewPdname() != null) && vo.getOldPdname().equals(vo.getNewPdname())) {
            vo.setNewPdname("无变化");
        }

        if ((vo.getOldcontentVO() != null && vo.getNewcontentVO() != null)
                && vo.getOldcontentVO().getMaterialUrl().equals(vo.getNewcontentVO().getMaterialUrl())) {
            vo.setNewcontentVO(null);
        }

        if ((vo.getOldpdTime() != null && vo.getNewpdTime() != null) && vo.getOldpdTime().equals(vo.getNewpdTime())) {
            vo.setNewpdTime("无变化");
        }
        MailHelper.sendChangeAdcontentMail(vo);
    }

    void generatePdtime(AdcontentChangeVo vo) {
        if (vo.getNewpdTime() != null) {
            String newTime = vo.getNewpdTime().replaceAll("-", ".").replaceAll(",", "-").replaceAll(";", ",");
            char c = newTime.charAt(newTime.length() - 1);
            if (c == ',') {
                vo.setNewpdTime(newTime.substring(0, newTime.length() - 1));
            } else {
                vo.setNewpdTime(newTime);
            }
        }
        if (vo.getOldpdTime() != null) {
            String oldTime = vo.getOldpdTime().replaceAll("-", ".").replaceAll(",", "-").replaceAll(";", ",");
            char c = oldTime.charAt(oldTime.length() - 1);
            if (c == ',') {
                vo.setOldpdTime(oldTime.substring(0, oldTime.length() - 1));
            } else {
                vo.setOldpdTime(oldTime);
            }
        }

    }

    public void updateContentCustomer(Long adSolutionId, Customer customer) {
        List<AdSolutionContent> contents = this.contentRepository.findByAdSolutionId(adSolutionId);
        for (AdSolutionContent content : contents) {
            content.setAdvertiser(customer.getCompanyName());
            content.setAdvertiserId(customer.getCustomerNumber());
        }
    }

    /**
     * 国际化投放平台和站点
     * 
     * @param channelId
     * @param areaId
     * @param positionId
     * @param currentLocale
     * @return
     */
    public void generatePlatSiteNames(AdcontentChangeVo vo) {
        AdvertisingPlatform advertisingPlatform =
                adPlatformService.getByIdAndLocale(vo.getProductId(), LocaleConstants.zh_CN);
        AdvertisingPlatform oldAdvertisingPlatform =
                adPlatformService.getByIdAndLocale(vo.getOldProductId(), LocaleConstants.zh_CN);

        StringBuffer newsb = new StringBuffer();
        StringBuffer oldsb = new StringBuffer();
        if (advertisingPlatform != null) {
            newsb.append(advertisingPlatform.getI18nName() + "—");
        }
        if (oldAdvertisingPlatform != null) {
            oldsb.append(oldAdvertisingPlatform.getI18nName() + "—");
        }

        Site temSite = siteService.findSiteAndI18nById(vo.getSiteId(), LocaleConstants.zh_CN);
        Site oldTemSite = siteService.findSiteAndI18nById(vo.getOldSiteId(), LocaleConstants.zh_CN);
        if (temSite != null) {
            newsb.append(temSite.getI18nName() + "—");
        }
        if (oldTemSite != null) {
            oldsb.append(oldTemSite.getI18nName() + "—");
        }
        List<Long> positionIds = new ArrayList<Long>();
        positionIds.add(vo.getChannelId());
        positionIds.add(vo.getAreaId());
        positionIds.add(vo.getNewpositionId());

        List<PositionVO> positionVOs = positionService.findI18nByPositionIds(positionIds, LocaleConstants.zh_CN);
        for (PositionVO temPositionVO : positionVOs) {
            String i18nName = temPositionVO.getI18nName();
            long temPositionId = temPositionVO.getId().longValue();
            if (temPositionId == vo.getChannelId().longValue()) {
                newsb.append(i18nName + "—");
            } else if (temPositionId == vo.getAreaId().longValue()) {
                newsb.append(i18nName + "—");
            } else if (temPositionId == vo.getNewpositionId().longValue()) {
                newsb.append(i18nName);
            }
        }
        List<Long> oldPositionIds = new ArrayList<Long>();
        oldPositionIds.add(vo.getChannelId());
        oldPositionIds.add(vo.getAreaId());
        oldPositionIds.add(vo.getNewpositionId());

        List<PositionVO> oldPositionVOs = positionService.findI18nByPositionIds(oldPositionIds, LocaleConstants.zh_CN);
        for (PositionVO temPositionVO : oldPositionVOs) {
            String i18nName = temPositionVO.getI18nName();
            long temPositionId = temPositionVO.getId().longValue();
            if (temPositionId == vo.getChannelId().longValue()) {
                oldsb.append(i18nName + "—");
            } else if (temPositionId == vo.getAreaId().longValue()) {
                oldsb.append(i18nName + "—");
            } else if (temPositionId == vo.getNewpositionId().longValue()) {
                oldsb.append(i18nName);
            }
        }
        vo.setNewPdname(newsb.toString());
        vo.setOldPdname(newsb.toString());
    }

    @Override
    public Map<Long, AdSolutionContent> findAdContentMap(List<Long> adContentIds) {
        Map<Long, AdSolutionContent> contentMap = new HashMap<Long, AdSolutionContent>();
        if (CollectionUtils.isEmpty(adContentIds)) {
            return contentMap;
        }
        List<AdSolutionContent> contents = findByAdContentIds(adContentIds);
        for (AdSolutionContent content : contents) {
            contentMap.put(content.getId(), content);
        }
        return contentMap;
    }

    @Override
    public Map<Long, User> findOperatorsByAdContentIdIn(Collection<Long> adContentIds) {
        Map<Long, User> contentUserMap = new HashMap<Long, User>();
        if (CollectionUtils.isEmpty(adContentIds)) {
            return contentUserMap;
        }

        Map<Long, Long> contentUserIdMap = new HashMap<Long, Long>();
        Set<Long> ucIds = new HashSet<Long>();
        for (Object[] objects : findOperatorByIdIn(adContentIds)) {
            ucIds.add((Long) objects[1]);
            contentUserIdMap.put((Long) objects[0], (Long) objects[1]);
        }
        if (CollectionUtils.isEmpty(ucIds)) {
            return contentUserMap;
        }

        Map<Long, User> userMap = userService.findByUcidIn(ucIds);

        for (Long adContentId : adContentIds) {
            Long ucId = contentUserIdMap.get(adContentId);
            User user = userMap.get(ucId);
            contentUserMap.put(adContentId, user);
        }
        return contentUserMap;
    }

    @Override
    public void moveToHistory(Long adSolutionId) {
        List<AdSolutionContent> contents = contentRepository.findByAdSolutionId(adSolutionId);
        if (CollectionUtils.isEmpty(contents)) {
            return;
        }
        List<Long> adContentIds = new LinkedList<Long>();
        for (AdSolutionContent temAdSolutionContent : contents) {
            adContentIds.add(temAdSolutionContent.getId());
            deleteMaterial(temAdSolutionContent.getId());
        }

        // move
        adContentRepositoryCustomDao.moveToHistory(adSolutionId);
        adQuotationService.moveToHistory(adContentIds);
        adMaterialService.moveToHistory(adContentIds);

        // delete
        contentRepository.deleteByAdSolutionId(adSolutionId);

        adQuotationService.deleteByAdSolutionContentIdIn(adContentIds);
        adMaterialService.deleteByContentIdIn(adContentIds);

    }

    @Override
    public boolean hasValidAdContent(Long adSolutionId) {
        Long count = contentRepository.findValidAdContent(adSolutionId, ApprovalStatus.saving, ApprovalStatus.refused);
        if (count != null && count.longValue() > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<ADContent> findPublishValidADContents() {
    	Map<Long, Publish> publishMap = getValidPublishMap();
        List<Long> adContentIds = new ArrayList<Long>(publishMap.keySet());
        if (CollectionUtils.isEmpty(adContentIds)) {
            return new ArrayList<ADContent>();
        }

        Map<Long, ADContentStatistics> statisticsMap = getContentStaticticsMap(adContentIds);
        Set<Long> contentIdsWithStatistics = statisticsMap.keySet();
        if (CollectionUtils.isEmpty(contentIdsWithStatistics)) {
            return new ArrayList<ADContent>();
        }

        Multimap<Long, MaterialFileVO> materialFiles =
                adMaterialService.getMaterialFilesByAdContentIdIn(contentIdsWithStatistics);
        List<ADContent> contents = adContentRepositoryCustomDao.findAllADContentsByIdIn(contentIdsWithStatistics);
        Map<Long, String> timeZoneOffsetMap = siteService.findTimeZoneOffsetMap();
        for (ADContent adContent : contents) {
            Long contentId = adContent.getId();
            // get value from statistics
            ADContentStatistics contentStatistics = statisticsMap.get(contentId);
            if (contentStatistics == null) {
                LoggerHelper.err(getClass(), "广告内容的统计数据不存在，内容id：{}", contentId);
                continue;
            }
            if (adContent.getBillingModeId() == null) {
                adContent.setStatus(ADContent.DISABLE);
                //adContent.setOccupyPeriod(null); TODO fix me
                LoggerHelper.err(getClass(), "广告内容{}计费方式为空。", contentId);
                continue;
            }
            adContent.setStatus(ADContent.ENABLE);
            adContent.setTimeZoneOffset(timeZoneOffsetMap.get(adContent.getSiteId()));
            // fill daily/total/accumulated amount if necessary
            fillAmountData(adContent, contentStatistics);
            fillMaterialData(adContent, materialFiles);
            fillPeriod(adContent, publishMap);
          //因特殊字符较多,对嵌入代码内容进行编码处理,减少服务器间数据传输问题
            processCodeContent(adContent);
        }
        return contents;
    }
    
    private void processCodeContent(ADContent content) {
        if (StringUtils.isNotBlank(content.getMaterialCodeContent())) {
            try {
                content.setMaterialCodeContent(URLEncoder.encode(content.getMaterialCodeContent(), "UTF-8").replaceAll(
                        "\\+", " "));
            } catch (UnsupportedEncodingException e) {
                LoggerHelper.err(getClass(), "嵌入代码内容URLEncoder.encode()转码错误", e);
            }
        }
    }

    private Map<Long, ADContentStatistics> getContentStaticticsMap(List<Long> adContentIds) {
        Map<Long, ADContentStatistics> statisticsMap = new HashMap<Long, ADContentStatistics>();
        List<ADContentStatistics> statistics = contentDataService.findByAdContentIdIn(adContentIds);
        if (CollectionUtils.isEmpty(statistics)) {
            return statisticsMap;
        }
        for (ADContentStatistics adContentStatistics : statistics) {
            statisticsMap.put(adContentStatistics.getAdContentId(), adContentStatistics);
        }
        return statisticsMap;
    }

    private void fillAmountData(ADContent adContent, ADContentStatistics contentStatistics) {
        if (adContent.getBillingModeId().longValue() == 1) {
            // Set CPC total amount & daily amount
            long maxAmount = -1L;
            adContent.setAccumulatedAmount(contentStatistics.getClicks());
            if (adContent.getBudget() != null && adContent.getBudget() > 0) {
                Double customerQuoto = adContent.getCustomerQuote();
                if (customerQuoto != null && customerQuoto > 0) {
                    maxAmount = new Double(DoubleMath.divToFloor(adContent.getBudget(), customerQuoto)).longValue();
                }
            } else if (adContent.getDailyAmount() != null) {// 0元测试单进入或其他情形
                maxAmount =
                        adContent.getDailyAmount()
                                * DatePeriodHelper.getTotalDays(DatePeriodHelper.getDatePeriods(adContent.getPeriod()));
            }
            if (maxAmount != -1 && maxAmount <= adContent.getAccumulatedAmount().longValue()) {
                adContent.setStatus(ADContent.DISABLE);
            }
            adContent.setTotalAmount(maxAmount);

        } else if (adContent.getBillingModeId().longValue() != 4) {
            // CPT\CPA\CPS have no total amount and daily amount, that is no limit
            adContent.setTotalAmount(-1L);
            adContent.setDailyAmount(-1L);
            adContent.setAccumulatedAmount(contentStatistics.getAccumulatedDays().longValue());
        } else {
            // CPM convert to impressions per person, daily/total amount means impressions per thousand persons
            adContent.setAccumulatedAmount(contentStatistics.getImpressions());
            adContent.setDailyAmount(adContent.getDailyAmount() * 1000l);
            adContent.setTotalAmount(adContent.getTotalAmount() * 1000l);
            if(adContent.getTotalAmount()<=adContent.getAccumulatedAmount()){
                adContent.setStatus(ADContent.DISABLE);
            }
        }
    }

    private void fillMaterialData(ADContent adContent, Multimap<Long, MaterialFileVO> materialFiles) {
        List<MaterialFileVO> files = (List<MaterialFileVO>) materialFiles.get(adContent.getId());
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        List<MaterialAttachment> materialAttachments = new ArrayList<MaterialAttachment>();
        for (MaterialFileVO materialFileVO : files) {
            String suffix = FilenameUtils.getExtension(materialFileVO.getFilename());
            String filename = FilenameUtils.getName(materialFileVO.getUploadFileName());
            MaterialAttachment attachment =
                    new MaterialAttachment(materialFileVO.getFileUrl(),
                            filename.concat(FilenameUtils.EXTENSION_SEPARATOR + suffix));
            materialAttachments.add(attachment);
        }
        adContent.setMaterialAttachments(materialAttachments);
    }

    // FIXME
    private void fillPeriod(ADContent adContent, Map<Long, Publish> publishMap) {
        /*String occupyPeriod = adContent.getOccupyPeriod();
        Set<Long> occupyIds = new HashSet<Long>();
        if (StringUtils.isNotBlank(occupyPeriod)) {
            for (String occIdStr : occupyPeriod.split(Constants.OCCUPY_ID_SPLIT)) {
                occupyIds.add(Long.valueOf(occIdStr));
            }
        }
        List<Date> dates = positionDateService.findDateById(occupyIds);
        adContent.setOccupyPeriod(null);
        if (CollectionUtils.isEmpty(dates)) {
            adContent.setPeriod(StringUtils.EMPTY);
            adContent.setStatus(ADContent.DISABLE);
            return;
        }
        adContent.setPeriod(DatePeriodHelper.getDatePeriodStr(dates));
        
        // if current date has already bean finished, content must be disable on today.
        Date currentDate = DateUtils.getCurrentDateOfZero();
        if (dates.contains(currentDate) && isCurrentDateFinished(currentDate, adContent, publishMap)) {
            adContent.setStatus(ADContent.DISABLE);
        }*/
    }
    
    private boolean isCurrentDateFinished(Date currentDate, ADContent adContent, Map<Long, Publish> publishMap) {
        Publish publish = publishMap.get(adContent.getId());
        return publishService.isPublishInterval(publish.getNumber());
    }

    public AdvertiseType findContentAdvertiseType(AdSolutionContent content) {
        if (content == null) {
            return null;
        }
        return adSolutionService.findById(content.getAdSolutionId()).getAdvertiseType();
    }
    
    private Map<Long, Publish> getValidPublishMap() {
        Map<Long, Publish> publishMap = new HashMap<Long, Publish>();
        List<Publish> validPublish = publishService.findByStatus(PublishStatus.unpublish);
        validPublish.addAll(publishService.findByStatus(PublishStatus.publishing));
        for (Publish publish : validPublish) {
            publishMap.put(publish.getAdContentId(), publish);
        }
        return publishMap;
    }


    public boolean findAutoPublishStatus(Long adContentId) {
        boolean isAuto = false;
        if (adContentId == null) {
            return isAuto;
        }
        Long productId = findOne(adContentId).getProductId();
        if (productId == Constants.PLATFORM_HAO123_ID
                && Boolean.valueOf(GcrmConfig.getConfigValueByKey("hao123.autoPublished"))) {
            isAuto = true;
        }
        return isAuto;
    }

    public List<DatePeriodState> getDatePeriodStates(AdSolutionContent content) {
        List<DatePeriodState> periodStates = new ArrayList<DatePeriodState>();
        if (content != null && (ApprovalStatus.approved.equals(content.getApprovalStatus()) || ApprovalStatus.effective.equals(content.getApprovalStatus()))) {
            List<PublishDate> pdates = publishDateService.findByAdContentId(content.getId());
            if (CollectionUtils.isNotEmpty(pdates)) {
                Collections.sort(pdates);
                DatePeriodState dpstate = null;
                for (PublishDate pdate : pdates) {
                    dpstate = new DatePeriodState();
                    if (PublishPeriodStatus.ongoing == pdate.getStatus()) {
                        dpstate.setStartChangeable(false);

                    } else if (PublishPeriodStatus.end == pdate.getStatus()) {
                        dpstate.setStartChangeable(false);
                        dpstate.setEndChangeable(false);
                    } else if (pdate.getPlanEnd().compareTo(DateUtils.getCurrentDateOfZero()) < 0) {
                        dpstate.setStartChangeable(false);
                        dpstate.setEndChangeable(false);
                    }
                    periodStates.add(dpstate);
                }

            }
        }
        return periodStates;
    }

    @Override
    public List<AdContentBriefDTO> findContentsHoldingPosition(Long positionId) {
        List<AdContentBriefDTO> briefDTOs = new ArrayList<AdContentBriefDTO>();
        List<AdContentBriefVO> briefVOs = adContentRepositoryCustomDao.findContentBriefsHodingPosition(positionId);
        if (CollectionUtils.isEmpty(briefVOs)) {
            return briefDTOs;
        }
        Set<Long> adContentIds = new HashSet<Long>();
        for (AdContentBriefVO briefVO : briefVOs) {
            adContentIds.add(briefVO.getId());
        }
        Multimap<Long, String> contentIdDateMap = dateRelationService.getContentIdDateMap(adContentIds,
                DateUtils.getCurrentDateOfZero());
        Map<Long, User> usersMap = userService.findAllUsersMap();
        Map<String, BillingModel> billingModelMap = billingModelService.getAllMapByLocale(LocaleConstants.en_US);
        for (AdContentBriefVO briefVO : briefVOs) {
            Long adContentId = briefVO.getId();
            List<String> dates = (List<String>) contentIdDateMap.get(adContentId);
            if (CollectionUtils.isEmpty(dates)) {
                continue;
            }
            Collections.sort(dates);
            AdContentBriefDTO briefDTO = new AdContentBriefDTO();
            briefDTO.setAdvertiser(briefVO.getAdvertiser());
            briefDTO.setBillingModel(billingModelMap.get(briefVO.getBillingModelId().toString()).getName());
            User user = usersMap.get(briefVO.getOperator());
            briefDTO.setOperator(user == null ? StringUtils.EMPTY : user.getRealname());
            briefDTO.setStatus(ApprovalStatus.approving.equals(briefVO.getApprovalStatus()) ? ADContentBriefStatus.reserved
                    : ADContentBriefStatus.confirmed);
            briefDTO.setDates(dates);
            briefDTO.setAdSolutionId(briefVO.getAdSolutionId());
            briefDTO.setAdContentId(adContentId);
            briefDTO.setNumber(briefVO.getNumber());
            briefDTOs.add(briefDTO);
        }
        Collections.sort(briefDTOs);
        return briefDTOs;
    }

    public List<AdSolutionContent> findByOldContentIdAndContentType(Long oldContentId, ContentType type) {
        return contentRepository.findByOldContentIdAndContentType(oldContentId, type);
    }

    public List<AdvertiseMaterial> getAdvertiseMaterial4Content(AdSolutionContent content) {

        if (content == null) {
            return null;
        }
        List<AdvertiseMaterial> advertiseMaterials = new ArrayList<AdvertiseMaterial>();
        if (CollectionUtils.isNotEmpty(content.getMaterialApplyList())) {
            for (AdvertiseMaterialApply apply : content.getMaterialApplyList()) {
                advertiseMaterials.addAll(apply.getMaterialList());
            }
        }
        return advertiseMaterials;
    }

    public List<AdvertiseMaterialMenu> getAdvertiseMaterialMenu4Content(AdSolutionContent content) {

        if (content == null) {
            return null;
        }
        List<AdvertiseMaterialMenu> advertiseMaterialMenus = new ArrayList<AdvertiseMaterialMenu>();
        if (CollectionUtils.isNotEmpty(content.getMaterialApplyList())) {
            for (AdvertiseMaterialApply apply : content.getMaterialApplyList()) {
                advertiseMaterialMenus.addAll(apply.getMaterialMenuList());
            }
        }
        return advertiseMaterialMenus;
    }
    
    public List<AdSolutionContent> findAllWithoutMaterialApply(){
        return contentRepository.findAllWithoutMaterialApply();
    }

    public void stopAdSolutionContent(AdSolutionContent content) {
        content.setApprovalStatus(ApprovalStatus.cancel);
        contentRepository.save(content);
        adCancelRecordsService.saveCancel(content, CancelReason.end);
        // 释放排期单
        Schedules schedule = scheduleService.findCurrentSchedule(content.getId());
        scheduleService.releaseSchedule(schedule);
        //释放库存
        dateRelationService.removeRelationshipsAndReleaseStock(content.getId());
        saveAndUpdateAdContentStatus(content);
    }
    
    //TODO,此接口只调一次，可在R2Sprint4上线后删除
    public void initOnlineAdAndSolutionStatus() {
        List<AdSolutionContent> contents =
                contentRepository.findByApprovalStatusIn(Arrays.asList(ApprovalStatus.approved));
        List<AdvertiseSolution> solutions =
                adSolutionService.findByApprovalStatusIn(Arrays.asList(AdvertiseSolutionApproveState.approved));
        Map<Long, AdvertiseSolution> temp = new HashMap<Long, AdvertiseSolution>();
        processAdSolution(solutions, temp);
        processAdContent(contents, temp);

    }

    private void processAdContent(List<AdSolutionContent> contents, Map<Long, AdvertiseSolution> temp) {
        if (CollectionUtils.isNotEmpty(contents)) {
            for (AdSolutionContent content : contents) {
                if (CollectionUtils.isEmpty(scheduleService.findByAdContentId(content.getId()))) {
                    Set<Date> dates = DatePeriodHelper.getAllDates(content.getPeriodDescription());
                    if (CollectionUtils.isNotEmpty(dates)) {
                        Date max = Collections.max(dates);
                        if (max.before(DateUtils.getCurrentDateOfZero())) {
                            scheduleService.createAndSaveSchedule(content, ScheduleStatus.released);
                        } else {
                            scheduleService.createAndSaveSchedule(content, ScheduleStatus.confirmed);
                        }
                    }
                }
                if (StringUtils.isNotBlank(content.getPoNum())) {
                    content.setApprovalStatus(ApprovalStatus.effective);
                } else {
                    AdvertiseSolution solution = temp.get(content.getAdSolutionId());
                    if (solution == null) {
                        solution = adSolutionService.findById(content.getAdSolutionId());
                    }
                    if (StringUtils.isNotBlank(solution.getContractNumber()) && solution.getContractType() != null) {
                        content.setApprovalStatus(ApprovalStatus.effective);
                    } else {
                        content.setApprovalStatus(ApprovalStatus.approved);
                    }
                }

            }
            contentRepository.save(contents);
        }
    }

    private void processAdSolution(List<AdvertiseSolution> solutions, Map<Long, AdvertiseSolution> temp) {
        if (CollectionUtils.isNotEmpty(solutions)) {
            List<AdSolutionContent> tempCons = null;
            for (AdvertiseSolution solution : solutions) {
                if (StringUtils.isNotBlank(solution.getContractNumber()) && solution.getContractType() != null) {
                    solution.setApprovalStatus(AdvertiseSolutionApproveState.effective);
                } else {
                    tempCons = contentRepository.findByAdSolutionId(solution.getId());
                    if (CollectionUtils.isNotEmpty(tempCons)) {
                        solution.setApprovalStatus(AdvertiseSolutionApproveState.effective);
                        for (AdSolutionContent content : tempCons) {
                            if (StringUtils.isBlank(content.getPoNum())) {
                                solution.setApprovalStatus(AdvertiseSolutionApproveState.approved);
                                break;
                            }
                        }

                    } else {
                        solution.setApprovalStatus(AdvertiseSolutionApproveState.approved);
                    }

                }
                adSolutionService.save(solution);
                temp.put(solution.getId(), solution);
            }

        }
    }

    @Override
    public void deleteContents(List<AdSolutionContent> contents) {
        contentRepository.delete(contents);
    }
    
    @Override
    public boolean isModifiedContent(AdSolutionContent content) {
        return ContentType.update.equals(content.getContentType())
                && ModifyStatus.MODIFYED.equals(content.getModifyStatus());
    }

    @Override
    public boolean isNewContent(AdSolutionContent content) {
        return !ContentType.update.equals(content.getContentType());
    }
}
