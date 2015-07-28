package com.baidu.gcrm.schedule1.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.approval.service.IParticipantService;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.service.IAdvertiseQuotationService;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.common.random.IRandomCheckCallback;
import com.baidu.gcrm.common.random.IRandomStringService;
import com.baidu.gcrm.common.random.RandomType;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.occupation1.service.IPositionDateService;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.service.impl.InfoDecorator;
import com.baidu.gcrm.schedule1.dao.ScheduleRepository;
import com.baidu.gcrm.schedule1.dao.ScheduleRepositoryCustom;
import com.baidu.gcrm.schedule1.model.ScheduleVO;
import com.baidu.gcrm.schedule1.model.Schedules;
import com.baidu.gcrm.schedule1.model.Schedules.ScheduleStatus;
import com.baidu.gcrm.schedule1.service.ISchedulesService;
import com.baidu.gcrm.schedule1.web.vo.ScheduleConditionVO;
import com.baidu.gcrm.schedule1.web.vo.ScheduleListVO;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Service
public class SchedulesServiceImpl implements ISchedulesService {

    @Autowired
    ScheduleRepository scheduleDao;

    @Autowired
    ScheduleRepositoryCustom scheduleCustomDao;

    @Autowired
    IPositionDateService positionDateService;

    @Autowired
    I18nKVService i18nKVService;

    @Resource(name = "billingModelServiceImpl")
    AbstractValuelistCacheService<BillingModel> billingModelService;

    @Autowired
    IParticipantService participantService;

    @Autowired
    IUserService userService;

    @Autowired
    IRandomStringService randomService;

    @Autowired
    IAdvertiseQuotationService quotationService;

    @Override
    public Schedules findByNumber(String number) {
        return scheduleDao.findByNumber(number);
    }

    @Override
    public Schedules createAndSaveSchedule(AdSolutionContent content) {
        return createAndSaveSchedule(content, ScheduleStatus.confirmed);
    }

    @Override
    public Schedules createAndSaveSchedule(AdSolutionContent content, ScheduleStatus status) {
        Schedules newSchedule = new Schedules();
        Long adContentId = content.getId();
        newSchedule.setAdContentId(adContentId);
        newSchedule.setNumber(genrateScheduleNumber());
        newSchedule.setPositionId(content.getPositionId());
        AdvertiseQuotation quotation = quotationService.findByAdSolutionContentId(adContentId);
        newSchedule.setBillingModelId(quotation == null ? null : quotation.getBillingModelId());
        newSchedule.setStatus(status);
        newSchedule.setCreateTime(new Date());
        newSchedule.setCreateOperator(RequestThreadLocal.getLoginUserId());
        newSchedule.setPeriodDescription(content.getPeriodDescription());
        switch (status) {
            case released:
                newSchedule.setReleaseTime(new Date());
                break;
            case locked:
                newSchedule.setLockTime(new Date());
            default:
                break;

        }

        return scheduleDao.save(newSchedule);
    }

    private String genrateScheduleNumber() {
        try {
            return randomService.random(8, RandomType.random_schedule, new IRandomCheckCallback() {
                @Override
                public boolean exists(String randomStr) {
                    Schedules exists = scheduleDao.findByNumber(randomStr);
                    if (exists!=null) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        } catch (CRMBaseException e) {
            LoggerHelper.err(getClass(), "不能生成排期单号", e);
            throw new CRMRuntimeException(e.getMessage());
        }
    }

    @Override
    public void lockSchedule(Schedules schedule) {
        schedule.setStatus(ScheduleStatus.locked);
        schedule.setLockTime(new Date());
        scheduleDao.save(schedule);
    }

    @Override
    public void releaseSchedule(Schedules schedule) {
        schedule.setStatus(ScheduleStatus.released);
        schedule.setReleaseTime(new Date());
        scheduleDao.save(schedule);
    }

    @Override
    public void releaseScheduleByAdContentId(Long adContentId) {
        scheduleCustomDao.releaseScheduleByAdContentId(adContentId);
    }
    
    @Override
    public void saveOrUpdateSchedule(Schedules schedule) {
        scheduleDao.save(schedule);
    }

    @Override
    public void updateScheduleCompleted(String number) {
        scheduleDao.updateScheduleCompletedByNumber(number);
    }

    @Override
    public List<Schedules> findByPositionAndStatus(Position position, Collection<ScheduleStatus> status) {
        PositionType type = position.getType();
        Long id = position.getId();
        if (PositionType.position == type) {
            return scheduleDao.findByPositionIdAndStatusIn(id, status);
        } else {
            String indexStr = new StringBuilder().append(position.getIndexStr())
                                .append(id)
                                .append("-%").toString();
            return scheduleDao.findByIndexStrAndTypeAndStatus(indexStr, PositionType.position, status);
        }
    }

    @Override
    public List<Schedules> findByAdContentIdAndStatus(Long adContentId, ScheduleStatus status) {
        return scheduleDao.findByAdContentIdAndStatus(adContentId, status);
    }

    @Override
    public Schedules findCurrentScheduleByAdContent(AdSolutionContent content) {
        if (content == null) {
            return null;
        }
        List<Schedules> schedules = scheduleCustomDao.findLatestScheduleByAdContentId(content.getId());
        if (CollectionUtils.isNotEmpty(schedules)) {
            return schedules.get(0);
        }
        return null;
    }

    @Override
    public PageWrapper<ScheduleListVO> findByCondition(ScheduleConditionVO scheduleConditionVO) {
        String fromStr = scheduleConditionVO.getStartDate();
        String toStr = scheduleConditionVO.getEndDate();
        if (StringUtils.isNotBlank(fromStr) || StringUtils.isNotBlank(toStr)) {
            Date from = StringUtils.isBlank(fromStr) ? null : DateUtils.getString2Date(fromStr);
            Date to = StringUtils.isBlank(toStr) ? null : DateUtils.getString2Date(toStr);
            List<Long> positionDateIdList = positionDateService.findByDateFromTo(from, to);
            if (CollectionUtils.isEmpty(positionDateIdList)) {
                return new PageWrapper<ScheduleListVO>();
            }
            scheduleConditionVO.setPositionDateIds(positionDateIdList);
        }
        return scheduleCustomDao.findScheduleListByCondition(scheduleConditionVO);
    }

    @Override
    public boolean isSchedulesAllReleased(Long adContentId) {
        return CollectionUtils.isEmpty(scheduleDao.findByAdContentIdAndStatusNot(adContentId, ScheduleStatus.released));
    }

    @Override
    public ScheduleVO findScheduleAd(Long id, LocaleConstants locale) {
        ScheduleVO scheduleVO = scheduleCustomDao.findSchedule(id);
        if (scheduleVO == null) {
            return null;
        }

        String periodDescription = scheduleVO.getPeriodDescription();
        if (!StringUtils.isBlank(periodDescription)) {
            String periodDescriptionDisplay =
                    periodDescription.replaceAll("-", ".").replaceAll(",", "-").replaceAll(";", ",");
            if (periodDescriptionDisplay.endsWith(",")) {
                periodDescriptionDisplay = periodDescriptionDisplay.substring(0, periodDescriptionDisplay.length() - 1);
            }
            scheduleVO.setPeriodDescription(periodDescriptionDisplay);
        }

        Long billingModelId = scheduleVO.getBillingModelId();
        if (billingModelId != null && billingModelId.longValue() > 0) {
            BillingModel billingModelModel = billingModelService.getByIdAndLocale(billingModelId, locale);
            scheduleVO.setBillingModel(billingModelModel.getI18nName());
        }

        Long positionId = scheduleVO.getPositionId();
        if (positionId != null && positionId.longValue() > 0) {
            I18nKV positionI18nKV = i18nKVService.findByIdAndLocale(Position.class, positionId, locale);
            String i18nExtraValue = positionI18nKV.getExtraValue();
            if (!StringUtils.isBlank(i18nExtraValue)) {
                String[] i18nExtraValueArray = i18nExtraValue.split(InfoDecorator.SPLIT_FLAG);
                if (i18nExtraValueArray != null && i18nExtraValueArray.length > 4) {
                    scheduleVO.setAdPlatformName(i18nExtraValueArray[0]);
                    scheduleVO.setSiteName(i18nExtraValueArray[1]);
                    scheduleVO.setChannelName(i18nExtraValueArray[2]);
                    scheduleVO.setAreaName(i18nExtraValueArray[3]);
                    scheduleVO.setPositionName(i18nExtraValueArray[4]);
                }
            }
        }

        List<Participant> partList =
                participantService.findAllByKeyAndDescAndParticId(scheduleVO.getSiteId().toString(),
                        DescriptionType.site, ParticipantConstants.countryAgent.name());
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
            scheduleVO.setGuodai(sb.toString());

        }

        return scheduleVO;
    }

    @Override
    public List<Schedules> findByAdContentIdAndStatusNot(Long contentId, ScheduleStatus status) {
        return scheduleDao.findByAdContentIdAndStatusNot(contentId, status);
    }

    @Override
    public Schedules findCurrentSchedule(Long adContentId) {
        if (adContentId == null) {
            return null;
        }
        List<Schedules> schedules = scheduleCustomDao.findLatestScheduleByAdContentId(adContentId);
        if (CollectionUtils.isNotEmpty(schedules)) {
            return schedules.get(0);
        }
        return null;
    }
    public List<Schedules> findByAdContentId(Long adContentId){
        return scheduleDao.findByAdContentId(adContentId); 
    }
}
