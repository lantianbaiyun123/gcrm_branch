package com.baidu.gcrm.schedule.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.ad.approval.dao.ParticipantRepository;
import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.approval.record.service.IApprovalRecordService;
import com.baidu.gcrm.ad.approval.service.IParticipantService;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.service.IAdvertiseMaterialApplyService;
import com.baidu.gcrm.ad.service.IAdvertiseQuotationService;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.service.IBpmProcessStartService;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.common.random.IRandomCheckCallback;
import com.baidu.gcrm.common.random.IRandomStringService;
import com.baidu.gcrm.common.random.RandomType;
import com.baidu.gcrm.mail.ScheduleCompleteContent;
import com.baidu.gcrm.occupation.dao.IPositionOccupationRepository;
import com.baidu.gcrm.occupation.dao.IPositionOccupationRepositoryCustom;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.occupation.service.IADPositionDateRelationService;
import com.baidu.gcrm.occupation.service.IPositionOccupationCommonService;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.Publish.PublishStatus;
import com.baidu.gcrm.publish.model.Publish.PublishType;
import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.model.PublishDate.PublishPeriodStatus;
import com.baidu.gcrm.publish.service.IPublishDateService;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.schedule.dao.IScheduleRepository;
import com.baidu.gcrm.schedule.dao.IScheduleRepositoryCustom;
import com.baidu.gcrm.schedule.model.Schedule;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;
import com.baidu.gcrm.schedule.service.IScheduleService;
import com.baidu.gcrm.schedule.web.helper.ScheduleCondition;
import com.baidu.gcrm.schedule.web.vo.ScheduleListBean;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.BillingModelServiceImpl;

@Service
public class ScheduleServiceImpl implements IScheduleService {

    @Autowired
    private IScheduleRepository scheduleDao;
    @Autowired
    IAdSolutionContentService adSolutionContentService;
    @Autowired
    private BillingModelServiceImpl billingModelServiceImpl;
    @Autowired
    AdvertisingPlatformServiceImpl adPlatformService;
    @Autowired
    IPositionService positionService;
    @Autowired
    ISiteService siteService;
    @Autowired
    IScheduleRepositoryCustom scheduleCustomDao;
    @Autowired
    IPositionOccupationRepositoryCustom occupationCustomDao;

    @Autowired
    IPositionOccupationCommonService occupationService;

    @Autowired
    IADPositionDateRelationService relationService;

    @Autowired
    IAdvertiseQuotationService quotationService;

    @Autowired
    IApprovalRecordService approvalRecordService;

    @Autowired
    IAdvertiseMaterialApplyService materialApplyService;

    @Autowired
    IAccountService accountService;

    @Autowired
    IUserService userService;

    @Autowired
    IParticipantService participantService;

    @Autowired
    IPositionOccupationRepository occupationDao;

    @Autowired
    @Qualifier("biddingProcessServiceImpl")
    IBpmProcessStartService biddingProcessService;

    @Autowired
    IBpmProcessService processService;

    @Autowired
    IAdvertiseSolutionService solutionService;

    @Autowired
    IPublishService publishService;

    @Autowired
    IPublishDateService publishDateService;

    @Autowired
    IRandomStringService randomService;

    @Autowired
    IUserRightsService userRightsService;
    @Autowired
    private ParticipantRepository participantDao;

    @Override
    public Schedule findByNumber(String number) {
        return scheduleDao.findByNumber(number);
    }

    @Override
    public Schedule findByAdContentIdAndStatus(Long adContentId, ScheduleStatus status) {
        List<Schedule> schedules = scheduleDao.findByAdContentIdAndStatus(adContentId, status);
        if (CollectionUtils.isNotEmpty(schedules)) {
            return schedules.get(0);
        }
        return null;
    }

    @Override
    public void lockSchedule(Schedule schedule) {
        schedule.setStatus(ScheduleStatus.locked);
        schedule.setLockTime(new Date());
        scheduleDao.save(schedule);
    }

    @Override
    public Map<String, Object> findScheduleAd(Long id, LocaleConstants locale) {
        String modelname = "";

        Map<String, Object> schedule = scheduleCustomDao.findSchedule(id);
        if (schedule == null) {
            return null;
        }

        Long siteId = Long.valueOf(schedule.get("siteId").toString());
        Long channelId = Long.valueOf(schedule.get("channelId").toString());
        Long areaId = Long.valueOf(schedule.get("areaId").toString());
        Long positionId = Long.valueOf(schedule.get("positionId").toString());

        if (schedule.get("periodDescription") == null
                || StringUtils.isBlank(schedule.get("periodDescription").toString())) {
            schedule.put("periodDescription", null);
        } else {
            String pdtime =
                    schedule.get("periodDescription").toString().replaceAll("-", ".").replaceAll(",", "-")
                            .replaceAll(";", ",");

            char c = pdtime.charAt(pdtime.length() - 1);
            if (c == ',') {
                schedule.put("periodDescription", pdtime.substring(0, pdtime.length() - 1));
            } else {
                schedule.put("periodDescription", pdtime);
            }
        }
        if (schedule.get("insertPeriodDescription") == null
                || StringUtils.isBlank(schedule.get("insertPeriodDescription").toString())) {
            schedule.put("insertPeriodDescription", null);
        } else {
            String idtime = schedule.get("insertPeriodDescription").toString().replaceAll(";", ",");
            schedule.put("insertPeriodDescription", idtime);
        }

        if (schedule.get("billingModel") != null && StringUtils.isNotBlank(schedule.get("billingModel").toString())) {

            BillingModel billingModel = billingModelServiceImpl.getById(schedule.get("billingModel").toString());
            if (billingModel == null) {
                modelname = "不存在";
            } else {
                modelname = billingModel.getName();
            }
            schedule.put("billingModel", modelname);
        }

        AdvertisingPlatform advertisingPlatform =
                adPlatformService.getByIdAndLocale(schedule.get("adPlatformId").toString(), locale);
        if (advertisingPlatform != null) {
            schedule.put("adPlatformName", advertisingPlatform.getI18nName());
        }

        Site temSite = siteService.findSiteAndI18nById(siteId, locale);
        if (temSite != null) {
            schedule.put("siteId", temSite.getI18nName());
        }

        List<Long> positionIds = new ArrayList<Long>();
        positionIds.add(channelId);
        positionIds.add(areaId);
        positionIds.add(positionId);

        List<PositionVO> positionVOs = positionService.findI18nByPositionIds(positionIds, locale);
        for (PositionVO temPositionVO : positionVOs) {
            String i18nName = temPositionVO.getI18nName();
            long temPositionId = temPositionVO.getId().longValue();
            if (temPositionId == channelId.longValue()) {
                schedule.put("channelName", i18nName);
            } else if (temPositionId == areaId.longValue()) {
                schedule.put("areaName", i18nName);
            } else if (temPositionId == positionId.longValue()) {
                schedule.put("positionName", i18nName);
            }

        }

        List<Participant> partList =
                participantDao.findByKeyAndDescriptionAndParticipantId(siteId.toString(), DescriptionType.site,
                        ParticipantConstants.countryAgent.name());
        if (CollectionUtils.isNotEmpty(partList)) {
            StringBuffer sb = new StringBuffer();
            for (Participant pa : partList) {
                User user = userService.findByUsername(pa.getUsername());
                if (user != null) {
                    sb.append(user.getRealname() + " ");
                } else {
                    sb.append(pa.getUsername() + " ");
                }
            }
            schedule.put("guodai", sb.toString());

        }

        return schedule;
    }

    /**
     * 根据条件分页查询排期单列表
     */
    public PageWrapper<ScheduleListBean> findByCondition(ScheduleCondition scheduleCondition) {
        StringBuilder tfIds = new StringBuilder();

        // 如果选择了按日期查询
        if (StringUtils.isNotBlank(scheduleCondition.getStartDate())
                || StringUtils.isNotBlank(scheduleCondition.getEndDate())) {

            Date from =
                    StringUtils.isBlank(scheduleCondition.getStartDate()) ? null : DateUtils
                            .getString2Date(scheduleCondition.getStartDate());

            Date to =
                    StringUtils.isBlank(scheduleCondition.getEndDate()) ? null : DateUtils
                            .getString2Date(scheduleCondition.getEndDate());

            // 根据时间段获取投放列表
            List<PositionOccupation> tfList = occupationCustomDao.findByDateFromTo(from, to);
            for (PositionOccupation occ : tfList) {
                tfIds.append(occ.getId()).append(",");
            }

            if (tfIds.length() > 0) {
                tfIds = new StringBuilder(tfIds.substring(0, tfIds.length() - 1));
                // 将排期单ID加入查询条件
//                scheduleCondition.setCurOccupyIds(tfIds.toString());
            } else {
                return new PageWrapper<ScheduleListBean>();
            }
        }
        PageWrapper<ScheduleListBean> page = scheduleCustomDao.findScheduleListByCondition(scheduleCondition);

        return page;
    }

    private void convertOccupationId(Collection<Date> allDates, Map<String, Long> occupationIdMap,
            List<Long> allOccupationIds) {
        for (Date temdDate : allDates) {
            String dateKey = DateUtils.getDate2String(DateUtils.YYYY_MM_DD, temdDate);
            Long temOccupationId = occupationIdMap.get(dateKey);
            if (temOccupationId != null) {
                allOccupationIds.add(temOccupationId);
            }
        }
    }


    public Date intersectionDate(Collection<Date> curInsertDates, Collection<Date> allDates) {
        for (Date date : allDates) {
            if (curInsertDates.contains(date)) {
                return date;
            }
        }
        return null;
    }


    private String convertListToString(List<Long> occupyIds) {
        Collections.sort(occupyIds);
        StringBuilder sb = new StringBuilder();
        for (Long id : occupyIds) {
            sb.append(id).append(",");
        }
        return StringUtils.removeEnd(sb.toString(), ",");
    }

    public ScheduleCompleteContent processMailContent(Object[] insert, Map<String, User> operatorMap) {
        if (insert != null && insert.length >= 9) {
            Long operatorId = (Long) insert[3];
            Long siteId = (Long) insert[4];
            Long productId = (Long) insert[5];
            Date createTime = (Date) insert[6];
            String number = (String) insert[7];
            String occupyPeriod = (String) insert[8];
            occupyPeriod = occupyPeriod.replace(",", "~");
            ScheduleCompleteContent mailContent = new ScheduleCompleteContent();
            Set<String> to = new HashSet<String>();
            User operator = this.getOperator(operatorId, operatorMap);
            if (operator != null) {
                to.add(operator.getEmail());
                LoggerHelper.info(getClass(), "收件人：{}", operatorId + "-" + operator.getEmail());
                mailContent.setOperator(operator.getRealname());
            }
            mailContent.setSendTo(to);

            Set<String> cc = new HashSet<String>();
            operator = this.getPm(productId, operatorMap);
            if (operator != null) {
                cc.add(operator.getEmail());
                LoggerHelper.info(getClass(), "抄送pm：{}", operator.getEmail());
            }
            operator = this.getCountryAgent(siteId, operatorMap);
            if (operator != null) {
                cc.add(operator.getEmail());
                mailContent.setCountryAgent(operator.getRealname());
                LoggerHelper.info(getClass(), "抄送国代：{}", operator.getEmail());
            }
            String realizationExecutive = getRealizationExecutive();
            cc.add(realizationExecutive);
            LoggerHelper.info(getClass(), "抄送变现主管：{}", realizationExecutive);
            mailContent.setCc(cc);
            mailContent.setAdSubmitTime(DateUtils.getDate2String(createTime));
            mailContent.setAdContentNumber(number);
            mailContent.setOldOccupationPeriods(occupyPeriod);
            mailContent.setLocale(RequestThreadLocal.getLocale());
            return mailContent;
        }
        return null;
    }

    private String getRealizationExecutive() {
        List<User> users =
                userRightsService.findUserByRoleTag(GcrmConfig.getConfigValueByKey("uc.role.realization.excutive"));
        if (CollectionUtils.isEmpty(users)) {
            return StringUtils.EMPTY;
        }

        return users.get(0).getEmail();
    }

    private void generatePublish(AdSolutionContent content, Schedule schedule, Position position, String applyNumber)
            throws CRMBaseException {
        AdvertiseSolution solution = solutionService.findById(content.getAdSolutionId());
        String scheduleNumber = schedule.getNumber();
        Publish publishInDb = publishService.findByScheduleNumber(scheduleNumber);
        if (publishInDb != null) {
            updatePublishFromReleaseToNormal(content, scheduleNumber, publishInDb);
            return;
        }
        Publish publish = new Publish();
        publish.setAdContentNumber(content.getNumber());
        publish.setAdContentId(content.getId());
        publish.setAdSolutionNumber(solution.getNumber());
        publish.setCreateOperator(RequestThreadLocal.getLoginUserId());
        publish.setCreateTime(DateUtils.getCurrentDate());
        publish.setMaterialNumber(applyNumber);
        publish.setPositionId(position.getId());
        publish.setNumber(publishService.generateNumber());
        publish.setPositionNumber(position.getPositionNumber());
        publish.setScheduleNumber(scheduleNumber);
        publish.setStatus(PublishStatus.unpublish);
        publish.setCustomerNumber(solution.getCustomerNumber().toString());
        publish.setOnlineNumber(publish.getNumber());
        // 确认排期单时，没有生成合同，需要强制上线；当合同创建成功或变更成功时，修改上线类型为“正常上线”
        publish.setType(PublishType.force);

        String periodDescription = schedule.getPeriodDescription();
        List<DatePeriod> periods = DatePeriodHelper.getDatePeriods(periodDescription);
        List<PublishDate> publishDates = new ArrayList<PublishDate>();
        if (content.getOldContentId() != null) {
            publishDates = getPublishDatesIfPublishAlready(content, periods, publish);
        }
        if (CollectionUtils.isEmpty(publishDates)) {
            publishDates = publishDateService.generatePublishDatesAndSave(periods, publish.getNumber());
        }
        publishService.save(publish);

        LoggerHelper.info(getClass(), "广告内容：{}，生成上线申请单：{}", content.getId(), publish);
        LoggerHelper.info(getClass(), "生成上线申请单对应上线时间段：{}", new ArrayList<PublishDate>(publishDates));
    }

    private void updatePublishFromReleaseToNormal(AdSolutionContent content, String scheduleNumber, Publish publishInDb) {
        PublishStatus status = PublishStatus.unpublish;
        List<PublishDate> endDates =
                publishDateService.findByPublishNumberAndStatus(publishInDb.getNumber(), PublishPeriodStatus.end);
        if (CollectionUtils.isNotEmpty(endDates)) {
            List<PublishDate> notStartDates =
                    publishDateService.findByPublishNumberAndStatus(publishInDb.getNumber(),
                            PublishPeriodStatus.not_start);
            if (CollectionUtils.isEmpty(notStartDates)) {
                status = PublishStatus.publish_finish;
            } else {
                status = PublishStatus.publishing;
            }
        }
        publishInDb.setStatus(status);
        publishInDb.setUpdateTime(new Date());
        publishInDb.setUpdateOperator(RequestThreadLocal.getLoginUserId());
        publishService.save(publishInDb);
        LoggerHelper.info(getClass(), "广告内容id：{}，排期单编号：{}，对应上线单已存在， 更新上线单到有效状态，上线单：{}。", content.getId(),
                scheduleNumber, publishInDb);
    }

    private List<PublishDate> getPublishDatesIfPublishAlready(AdSolutionContent content, List<DatePeriod> periods,
            Publish newPublish) {
        String newPublishNumber = newPublish.getNumber();
        List<PublishDate> publishDates = new ArrayList<PublishDate>();
        Long oldContentId = content.getOldContentId();
        Long positionId = content.getPositionId();
        Publish publish = publishService.findByAdContentId(oldContentId);
        while (publish == null) {
            content = adSolutionContentService.findOne(oldContentId);
            if (content == null) {
                return publishDates;
            }
            oldContentId = content.getOldContentId();
            if (oldContentId == null) {
                return publishDates;
            }
            publish = publishService.findByAdContentId(oldContentId);
        }
        if (!publish.getPositionId().equals(positionId)) {
            return publishDates;
        }
        newPublish.setOnlineNumber(publish.getOnlineNumber());
        newPublish.setProperty(Integer.valueOf(Constants.OLD_PUBLISH_NUMBER));
        if (PublishStatus.unpublish.equals(publish.getStatus())) {
            return publishDates;
        }

        Date approvalDate = content.getApprovalDate();
        if (approvalDate == null) {
            approvalDate = DateUtils.getCurrentDate();
            LoggerHelper.err(getClass(), "广告内容id：{}的审核通过时间为空，设置为当前时间：{}。", content.getId(), approvalDate);
        }
        publishDates =
                publishDateService.combinePublishDatesAndSave(periods, approvalDate, newPublishNumber,
                        publish.getNumber());
        updatePublishStatus(newPublish, publishDates);
        return publishDates;
    }

    private void updatePublishStatus(Publish newPublish, List<PublishDate> publishDates) {
        for (PublishDate publishDate : publishDates) {
            if (!PublishPeriodStatus.not_start.equals(publishDate.getStatus())) {
                newPublish.setStatus(PublishStatus.publishing);
                break;
            }
        }
    }

    public Schedule generateNewSchedule(AdSolutionContent content, String occupyPeriod) throws CRMBaseException {
        Schedule newSchedule = new Schedule();
        Long adContentId = content.getId();
        newSchedule.setAdContentId(adContentId);
        newSchedule.setNumber(genrateScheduleNumber());
        newSchedule.setPositionId(content.getPositionId());
        AdvertiseQuotation quotation = quotationService.findByAdSolutionContentId(adContentId);
        newSchedule.setBillingModelId(quotation == null ? null : quotation.getBillingModelId());
        newSchedule.setAdvertisers(content.getAdvertiser());
        newSchedule.setStatus(ScheduleStatus.confirmed);
        newSchedule.setCreateTime(new Date());
        newSchedule.setCreateOperator(RequestThreadLocal.getLoginUserId());
        newSchedule.setConfirmTime(new Date());
        newSchedule.setConfirmOperator(RequestThreadLocal.getLoginUserId());
        newSchedule.setPeriodDescription(content.getPeriodDescription());
        newSchedule.setOccupyPeriod(occupyPeriod);
        return scheduleDao.save(newSchedule);
    }

    private String genrateScheduleNumber() throws CRMBaseException {
        return randomService.random(8, RandomType.random_schedule, new IRandomCheckCallback() {
            @Override
            public boolean exists(String randomStr) {
                Schedule exists = scheduleDao.findByNumber(randomStr);
                if (exists != null) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public String listHashSumToString(List<Long> list) {
        Integer hashCode = 0;
        for (Long l : list) {
            hashCode += l.hashCode();
        }
        return hashCode.toString();
    }

    public User getOperator(Long ucid, Map<String, User> operatorMap) {
        String key = "operator" + ucid;
        User user = operatorMap.get(key);
        if (user == null) {
            user = userService.findByUcid(ucid);
            operatorMap.put(key, user);
        }
        return user;
    }

    public User getPm(Long id, Map<String, User> operatorMap) {
        String key = DescriptionType.platform.name() + id;
        User user = operatorMap.get(key);
        if (user == null) {
            Participant partic =
                    participantService.findByKeyAndDescAndParticId(id.toString(), DescriptionType.platform,
                            ParticipantConstants.pm_leader.toString());
            if (partic != null) {
                user = userService.findByUsername(partic.getUsername());
                operatorMap.put(key, user);
            }
        }
        return user;
    }

    public User getCountryAgent(Long id, Map<String, User> operatorMap) {
        String key = DescriptionType.site.name() + id;
        User user = operatorMap.get(key);
        if (user == null) {
            Participant partic =
                    participantService.findByKeyAndDescAndParticId(id.toString(), DescriptionType.site,
                            ParticipantConstants.countryAgent.toString());
            if (partic != null) {
                user = userService.findByUsername(partic.getUsername());
                operatorMap.put(key, user);
            }
        }
        return user;
    }

    public Schedule findCurrentScheduleByAdContentId(Long adContentId) {
        List<Schedule> schedules = scheduleDao.findByAdContentIdAndStatusNot(adContentId, ScheduleStatus.released);
        if (schedules != null && schedules.size() > 0) {
            if (schedules.size() > 1) {
                throw new CRMRuntimeException("more than one schedules not released, contentId: " + adContentId);
            }
            return schedules.get(0);
        }
        return null;
    }

    public List<Schedule> findRelaseSchedulesByAdContentId(Long adContentId) {
        return scheduleDao.findByContentIdAndStatusAndOrder(adContentId, ScheduleStatus.released);
    }

    @Override
    public List<Schedule> findByPositionAndStatus(Position position, Collection<ScheduleStatus> status) {
        PositionType type = position.getType();
        Long id = position.getId();
        if (PositionType.position == type) {
            return scheduleDao.findByPositionIdAndStatus(id, status);
        } else {
            String indexStr = new StringBuilder().append(position.getIndexStr()).append(id).append("-%").toString();
            return scheduleDao.findByIndexStrAndTypeAndStatus(indexStr, PositionType.position, status);
        }

    }

    @Override
    public boolean isScheduleLocked(String scheduleNumber) {
        Schedule schedule = scheduleDao.findByNumber(scheduleNumber);
        if (schedule == null) {
            LoggerHelper.err(getClass(), "排期单编号{}对应排期单不存在。", scheduleNumber);
        }
        return ScheduleStatus.locked.equals(schedule.getStatus());
    }

    @Override
    public void updateScheduleCompleted(String scheduleNumber) {
        scheduleDao.updateScheduleCompleted(scheduleNumber);
    }

    @Override
    public List<Schedule> findByAdContentIdAndStatusNot(Long adContentId, ScheduleStatus status) {
        return scheduleDao.findByAdContentIdAndStatusNot(adContentId, status);
    }

    public String getOccupyPeriods(Long positionId, List<Date> dates) {
        Map<String, Long> allOccDateIdMap =
                occupationCustomDao.getDateOccIdMapFrom(positionId, DateUtils.getYesterdayDate());
        List<Long> allOccupationIds = new ArrayList<Long>();
        convertOccupationId(dates, allOccDateIdMap, allOccupationIds);
        return convertListToString(allOccupationIds);

    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleDao.save(schedule);
    }
    /**
     * TODO
     * 释放排期单的统一接口，后续会增加释放库存等相关资源
     */
    public Schedule releaseSchedule(Schedule schedule) {
        if (schedule != null) {
            schedule.setStatus(ScheduleStatus.released);
            schedule.setReleaseTime(new Date());
        }
        return schedule;
    }
}
