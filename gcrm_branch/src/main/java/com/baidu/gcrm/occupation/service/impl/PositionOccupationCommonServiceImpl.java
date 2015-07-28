package com.baidu.gcrm.occupation.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ApprovalStatus;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.vo.ProcessQueryBean;
import com.baidu.gcrm.bpm.vo.ProcessQueryConditionBean;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.mail.ScheduleCompleteContent;
import com.baidu.gcrm.occupation.dao.IADPositionDateRelationRepository;
import com.baidu.gcrm.occupation.dao.IPositionOccupationRepository;
import com.baidu.gcrm.occupation.dao.IPositionOccupationRepositoryCustom;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.occupation.helper.PositionOccupationHelper;
import com.baidu.gcrm.occupation.model.AdvertisePositionDateRelation;
import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.occupation.service.IPositionOccupationCommonService;
import com.baidu.gcrm.occupation1.service.IPositionDateRelationService;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.schedule.dao.IScheduleRepository;
import com.baidu.gcrm.schedule.model.Schedule;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;
import com.baidu.gcrm.schedule.service.IScheduleService;
import com.baidu.gcrm.user.model.User;

@Service
public class PositionOccupationCommonServiceImpl extends PositionOccupationHelper implements IPositionOccupationCommonService {

	@Autowired
	IPositionOccupationRepository positionOccupationDao;
	@Autowired
	IPositionDateRelationService relationService;
	@Autowired
	IAdSolutionContentService contentService;
	@Autowired
	IPositionOccupationRepositoryCustom occupationCustomDao;
	@Autowired
	IScheduleRepository scheduleDao;
	@Autowired
	IADPositionDateRelationRepository relationDao;
	@Autowired
	IScheduleService scheduleService;
	@Autowired
	IBpmProcessService bpmProcessService;
	@Autowired
	IPublishService publishService;

	@Override
	public PositionOccupation findByPositionIdAndDate(Long positionId, Date date) {
		return positionOccupationDao.findByPositionIdAndDate(positionId, date);
	}

	@Override
	public List<PositionOccupation> findOccupationByDateBetween(Long positionId, Date from, Date to) {
		return positionOccupationDao.findOccupationByDateBetween(positionId, from, to);
	}

	@Override
	public List<PositionOccupation> findOccupationByIdIn(Collection<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return new ArrayList<PositionOccupation>();
		}
		return positionOccupationDao.findByIdIn(ids);
	}
	
	@Override
	public List<Date> findDateById(Collection<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return new ArrayList<Date>();
		}
		return positionOccupationDao.findDateByIdIn(ids);
	}

	@Override
	public List<Long> findIdsByPositionIdAndDateFrom(Long positionId, Date date) {
		return positionOccupationDao.findIdsByPositionIdAndDateFrom(positionId, date);
	}

	@Override
	public void releaseAfter72Hours(String scheduleNumber, Long adContentId) {
		AdSolutionContent content = contentService.findOne(adContentId);
		content.setApprovalStatus(ApprovalStatus.approved);
		content.setUpdateOperator(RequestThreadLocal.getLoginUserId());
		content.setUpdateTime(new Date());
		contentService.saveAndUpdateAdContentStatus(content);
		releaseOccupationDuringProcess(scheduleNumber, adContentId);
		sendReleaseMail(adContentId);
	}

	@Override
	public void releaseOccupationDuringProcess(String releasedScheduleNumber, Long adContentId) {
		Schedule schedule = scheduleDao.findByNumber(releasedScheduleNumber);
		
		if (schedule == null) {
			completeBiddingProcess(adContentId, releasedScheduleNumber);
			LoggerHelper.err(getClass(), "72小时释放排期单{}，排期单不存在", releasedScheduleNumber);
			return;
		}
		if (!schedule.getAdContentId().equals(adContentId)) {
			throw new CRMRuntimeException("schedule.content.not.related");
		}
		
		boolean needToReleasePublish = publishService.findByScheduleNumber(releasedScheduleNumber) != null;
		
		// clear all relation whatever status before
		int deleteCount = relationDao.deleteByAdContentId(adContentId);
		LoggerHelper.info(getClass(), "释放排期单[{}]，广告内容id[{}]，删除关联的relation[{}]个", releasedScheduleNumber, adContentId,
				deleteCount);
		List<PositionOccupation> occupations = new ArrayList<PositionOccupation>();
		// 预定状态72小时未确认的情况，需要重新给国代创建排期任务，恢复relation对应关系到广告内容原始投放状态
		if (ScheduleStatus.reserved.equals(schedule.getStatus())) {
			AdSolutionContent content = contentService.findOne(adContentId);
			if (!ApprovalStatus.cancel.equals(content.getApprovalStatus())) {
				// rebuild relation according to ad. content
				relationService.buildRelationByAdContent(content);
				LoggerHelper.info(getClass(), "释放排期单[{}]，广告内容id[{}]", releasedScheduleNumber, adContentId);
			}
			/*List<Long> insertedOccupyIds = getInsertedOccupyIds(schedule.getInsertPeriod());
			if (CollectionUtils.isNotEmpty(insertedOccupyIds)) {
				occupations = positionOccupationDao.findByIdIn(insertedOccupyIds);
			}*/
			List<Long> occupyIds = getInsertedOccupyIds(schedule.getOccupyPeriod());
			if (CollectionUtils.isNotEmpty(occupyIds)) {
				occupations = positionOccupationDao.findByIdInAndHistorySchedulesNotEmpty(occupyIds);
			}
		} else {
			occupations = occupationCustomDao.findByCurScheduleNumberFazzyLike(releasedScheduleNumber);
		}
		
		updateScheduleToRelease(schedule);
		
		release(releasedScheduleNumber, occupations);
		
		if (needToReleasePublish) {
			publishService.terminateAllAfterRelease(releasedScheduleNumber);
		}
		
		completeBiddingProcess(adContentId, releasedScheduleNumber);
		
	}

	private void release(String releasedScheduleNumber, List<PositionOccupation> occupations) {
		if (CollectionUtils.isEmpty(occupations)) {
			LoggerHelper.info(getClass(), "释放排期单：{}，没有投放时间受到影响。", releasedScheduleNumber);
			return;
		}

		// updateHistorySchedules(releasedScheduleNumber, occupations);
		Set<String> historyScheduleSet = getHistorySchedules(occupations);
		removeReservedAndReleasedSchedules(historyScheduleSet);

		// conditions of update schedule
		List<String> conditions = new ArrayList<String>();

		// 记录恢复的历史排期单对应的投放日期和投放id
		Map<String, List<Date>> restoredDates = new HashMap<String, List<Date>>();
		Map<String, List<Long>> restoredOccupyIds = new HashMap<String, List<Long>>();
		LoggerHelper.info(getClass(), "释放排期单：{}，受影响的投放时间有{}个", releasedScheduleNumber, occupations.size());
		for (PositionOccupation occupation : occupations) {
			conditions.add(occupation.getCurScheduleNumber());
			// 上一个处于确认或锁定状态的排期单
			String historySchedules = occupation.getHistorySchedules();
			LoggerHelper.info(getClass(), "投放时间id：{}，追溯前当前排期单：[{}]，历史排期单：[{}]，售卖天数：{}", occupation.getId(),
					occupation.getCurScheduleNumber(), historySchedules, occupation.getSoldAmount());
			// 如果投放时间小于等于今天，只释放当前排期单不追回历史排期单
			String lastSchedule = occupation.getDate().after(DateUtils.getCurrentDate()) ? getLastScheduleFromHistory(historySchedules, historyScheduleSet) : StringUtils.EMPTY;
			String currentSchedule = getCurrentSchedulesAfterRelease(occupation.getCurScheduleNumber(),
					releasedScheduleNumber, lastSchedule, occupation.getTotalAmount());
			int soldAmount = getSoldAmount(currentSchedule);
			String historySchedulesAfterRelease;
			// 释放后和释放前的当前排期单相同，说明是1从预定状态变为释放2释放前当前投放时间已被其他排期单占满。此时无需从历史排期单追溯。
			if (currentSchedule.equals(occupation.getCurScheduleNumber())) {
				historySchedulesAfterRelease = historySchedules;
			} else {
				historySchedulesAfterRelease = getHistorySchedulesAfterRelease(historySchedules, lastSchedule);
			}
			occupation.setCurScheduleNumber(currentSchedule);
			occupation.setSoldAmount(soldAmount);
			occupation.setHistorySchedules(historySchedulesAfterRelease);
			LoggerHelper.info(getClass(), "投放时间id：{}，追溯后当前排期单：[{}]，历史排期单：[{}]，售卖天数：{}", occupation.getId(),
					currentSchedule, historySchedulesAfterRelease, occupation.getSoldAmount());
			if (StringUtils.isNotEmpty(lastSchedule)) {
				List<Date> dates = restoredDates.get(lastSchedule);
				List<Long> occupyIds = restoredOccupyIds.get(lastSchedule);
				if (CollectionUtils.isEmpty(dates)) {
					dates = new ArrayList<Date>();
					occupyIds = new ArrayList<Long>();
				}
				dates.add(occupation.getDate());
				occupyIds.add(occupation.getId());
				restoredDates.put(lastSchedule, dates);
				restoredOccupyIds.put(lastSchedule, occupyIds);
			}
		}
		
		occupationCustomDao.batchUpdateAfterRelease(occupations, conditions);
		if (CollectionUtils.isNotEmpty(restoredOccupyIds.keySet())) {
			Collection<Schedule> schedules = scheduleDao.findByNumberIn(restoredOccupyIds.keySet());
			batchUpdateLast(restoredDates, restoredOccupyIds, schedules);
		}
	}

	private void completeBiddingProcess(Long adContentId, String scheduleNumber) {
		try {
			ProcessQueryConditionBean queryBean = new ProcessQueryConditionBean();
			queryBean.setForeignKey(adContentId.toString());
			queryBean.setPackageId(GcrmConfig.getConfigValueByKey("ad.package.id"));
			queryBean.setProcessDefineId(GcrmConfig.getConfigValueByKey("bidding.process.defineId"));
			List<Integer> processState = new ArrayList<Integer>();
			processState.add(0);
			queryBean.setProcessState(processState);
			List<ProcessQueryBean> processes = bpmProcessService.queryProcess(queryBean);
			if (CollectionUtils.isEmpty(processes)) {
				return;
			}
			for (ProcessQueryBean process : processes) {
				String username = RequestThreadLocal.getLoginUsername();
				if (StringUtils.isEmpty(username)) {
					username = "gcrm_system";
				}
				String processId = process.getProcessId();
				try {
					bpmProcessService.terminateProcess(processId, username, "释放排期单后，终止流程。");
					LoggerHelper.info(getClass(), "释放排期单[{}]后，终止流程，流程id：{}", scheduleNumber, processId);
				} catch(Exception e) {
					LoggerHelper.err(getClass(), "释放排期单[{}]后，终止流程失败，流程id：{}，失败原因：{}", scheduleNumber, processId,
							e.getMessage());
				}
			}
		} catch(Exception e) {
			LoggerHelper.err(getClass(), "释放排期单[{}]后，终止流程失败，失败原因：{}", scheduleNumber, e.getMessage());
		}
	}

	private void sendReleaseMail(Long adContentId){
		List<Object[]> contentDatas = scheduleDao.findScheduleMailContentByAdContentId(adContentId);
		if(contentDatas != null && contentDatas.size()>0){
			Map<String, User> operatorMap = new HashMap<String, User>();
			ScheduleCompleteContent mailContent = scheduleService.processMailContent(
					contentDatas.get(0), operatorMap);
			MailHelper.sendScheduleReleasedMail(mailContent);
		}
	}
	
	private List<Long> getInsertedOccupyIds(String insertPeriod) {
		List<Long> occupyIds = new ArrayList<Long>();
		if (StringUtils.isBlank(insertPeriod)) {
			return occupyIds;
		}
		String[] insertPeriods = insertPeriod.split(Constants.OCCUPY_ID_SPLIT);
		if (ArrayUtils.isEmpty(insertPeriods)) {
			return occupyIds;
		}
		for (String idStr : insertPeriods) {
			occupyIds.add(Long.valueOf(idStr));
		}
		return occupyIds;
	}

	/**
	 * 更新从历史中恢复的排期单和对应广告内容的投放时间和插单时间，同时更新relation
	 * 
	 * @param restoredDates
	 * @param restoredOccupyIds
	 * @param schedules
	 */
	private void batchUpdateLast(Map<String, List<Date>> restoredDates, Map<String, List<Long>> restoredOccupyIds,
			Collection<Schedule> schedules) {
		List<AdvertisePositionDateRelation> newRelations = new ArrayList<AdvertisePositionDateRelation>();
		Map<Long, String> occupyPeriods = new HashMap<Long, String>();
		// update schedule
		for (Schedule schedule : schedules) {
			String number = schedule.getNumber();
			Long adContentId = schedule.getAdContentId();
			List<Date> dates = restoredDates.get(number);
			// 更新上下线申请单
			publishService.updatePublishBySchedule(number, new ArrayList<Date>(dates));
			dates.addAll(DatePeriodHelper.getAllDates(schedule.getPeriodDescription()));
			String oldPeriodStr = schedule.getPeriodDescription();
			schedule.setPeriodDescription(DatePeriodHelper.getDatePeriodStr(dates));
			List<Long> occupyIds = restoredOccupyIds.get(number);
			for (Long occupyId : occupyIds) {
				AdvertisePositionDateRelation relation = new AdvertisePositionDateRelation();
				relation.setAdContentId(adContentId);
				relation.setPositionOccId(occupyId);
				newRelations.add(relation);
			}
			String occupyPeriod = schedule.getOccupyPeriod();
			if (StringUtils.isNotBlank(occupyPeriod)) {
				String[] occupyArr = occupyPeriod.split(Constants.OCCUPY_ID_SPLIT);
				occupyIds.addAll(convertArrayToList(occupyArr));
			}
			Collections.sort(occupyIds);
			schedule.setOccupyPeriod(StringUtils.join(occupyIds, Constants.OCCUPY_ID_SPLIT));
			occupyPeriods.put(adContentId, schedule.getPeriodDescription());
			LoggerHelper.info(getClass(), "追溯历史排期单{}，恢复后投放时间由[{}]变为[{}]", number, oldPeriodStr,
					schedule.getPeriodDescription());
		}

		// update content
		List<AdSolutionContent> contents = contentService.findByAdContentIds(occupyPeriods.keySet());
		for (AdSolutionContent content : contents) {
			String periodDescription = occupyPeriods.get(content.getId());
			content.setPeriodDescription(periodDescription);
			content.setTotalDays(DatePeriodHelper.getTotalDays(DatePeriodHelper.getDatePeriods(periodDescription)));
			content.setUpdateOperator(RequestThreadLocal.getLoginUserId());
			content.setUpdateTime(DateUtils.getCurrentDate());
		}

		scheduleDao.save(schedules);
		contentService.save(contents);
		// add new relations
		relationDao.save(newRelations);
	}

	private List<Long> convertArrayToList(String[] occupyArr) {
		List<Long> occupyIds = new ArrayList<Long>();
		for (String occupyStr : occupyArr) {
			occupyIds.add(Long.valueOf(occupyStr));
		}
		return occupyIds;
	}

	private void updateScheduleToRelease(Schedule schedule) {
		schedule.setStatus(ScheduleStatus.released);
		schedule.setReleaseTime(new Date());
		scheduleDao.save(schedule);
	}

	private void removeReservedAndReleasedSchedules(Set<String> historySchedules) {
		if (CollectionUtils.isEmpty(historySchedules)) {
			return;
		}
		Collection<Schedule> schedules = scheduleDao.findByNumberIn(historySchedules);
		for (Schedule schedule : schedules) {
			ScheduleStatus status = schedule.getStatus();
			if (status.isConfirmed() || status.isLocked()) {
				continue;
			}
			historySchedules.remove(schedule.getNumber());
		}
	}
	
	@Override
	public void updateScheduleFromConfirmedToReserved(String scheduleNumber) {
		List<PositionOccupation> occupationsByCur = occupationCustomDao.findByCurScheduleNumberFazzyLike(scheduleNumber);
		String split = Constants.SCHEDULE_NUMBER_SPLIT;
		for (PositionOccupation occupation : occupationsByCur) {
			String curScheduleNumber = occupation.getCurScheduleNumber();
			curScheduleNumber = curScheduleNumber.replace(split + scheduleNumber + split, split);
			if (split.equals(curScheduleNumber)) {
				curScheduleNumber = StringUtils.EMPTY;
			}
			occupation.setCurScheduleNumber(curScheduleNumber);
			occupation.setSoldAmount(getSoldAmount(curScheduleNumber));
		}
		List<PositionOccupation> occupationsByHistory = occupationCustomDao.findByHistoryScheduleNumberFazzyLike(scheduleNumber);
		for (PositionOccupation occupation : occupationsByHistory) {
			String historySchedules = occupation.getHistorySchedules();
			historySchedules = historySchedules.replace(split + scheduleNumber + split, split);
			if (split.equals(historySchedules)) {
				historySchedules = StringUtils.EMPTY;
			}
			occupation.setHistorySchedules(historySchedules);
		}
		positionOccupationDao.save(occupationsByCur);
		positionOccupationDao.save(occupationsByHistory);
		
		publishService.terminateAllAfterRelease(scheduleNumber);
		LoggerHelper.info(getClass(), "排期单{}状态由确认变为预定，终止投放，上线单变为释放状态。", scheduleNumber);
	}

    @Override
    public Integer findMaxUsedCountByPositionId(Long positionId,
            Collection<ScheduleStatus> scheduleStatus) {
        return occupationCustomDao.findMaxUsedCountByPositionId(positionId, scheduleStatus);
    }

    @Override
    public void updateOccupationTotalAmount(Position position) {
        occupationCustomDao.updateOccupationTotalAmount(position);
    }
    
    @Override
    public void releaseOccupationByDates(String scheduleNumber, List<Date> dates, Long positionId, Long adContentId) {
    	List<Long> occIds = new ArrayList<Long>();
    	List<PositionOccupation> occupations = positionOccupationDao.findOccupationByDateIn(positionId, dates);
    	Schedule schedule = scheduleDao.findByNumber(scheduleNumber);
    	String split = Constants.OCCUPY_ID_SPLIT;
		String occupyPeriod = split.concat(StringUtils.trimToEmpty(schedule.getOccupyPeriod())).concat(split);
//    	String insertPeriod = split.concat(StringUtils.trimToEmpty(schedule.getInsertPeriod())).concat(split);
    	LoggerHelper.info(getClass(), "释放排期{}日期前，投放时间：{}", scheduleNumber, occupyPeriod);
    	for (PositionOccupation occ : occupations) {
    		Long occId = occ.getId();
			occIds.add(occId);
			occupyPeriod = occupyPeriod.replace(split.concat(occId.toString()).concat(split), split);
//			insertPeriod = insertPeriod.replace(split.concat(occId.toString()).concat(split), split);
		}
    	schedule.setOccupyPeriod(StringUtils.removeEnd(StringUtils.removeStart(occupyPeriod, split), split));
//    	schedule.setInsertPeriod(StringUtils.removeEnd(StringUtils.removeStart(insertPeriod, split), split));
    	scheduleDao.save(schedule);
    	LoggerHelper.info(getClass(), "释放排期{}日期后，投放时间：{}", scheduleNumber, schedule.getOccupyPeriod());
    	release(scheduleNumber, occupations);
    	relationDao.deleteByAdContentIdAndPositionOccId(adContentId, occIds);
    }
    
    @Override
    public void releaseOccupationAfterPublishTerminate(String releasedScheduleNumber, Long adContentId) {
    	Schedule schedule = scheduleDao.findByNumber(releasedScheduleNumber);
		if (!schedule.getAdContentId().equals(adContentId)) {
			throw new CRMRuntimeException("schedule.content.not.related");
		}
		
		// clear all relation whatever status before
		int deleteCount = relationDao.deleteByAdContentId(adContentId);
		LoggerHelper.info(getClass(), "释放排期单[{}]，广告内容id[{}]，删除关联的relation[{}]个", releasedScheduleNumber, adContentId,
				deleteCount);
		List<PositionOccupation> occupations = new ArrayList<PositionOccupation>();
		// 预定状态72小时未确认的情况，需要重新给国代创建排期任务，恢复relation对应关系到广告内容原始投放状态
		if (ScheduleStatus.reserved.equals(schedule.getStatus())) {
			LoggerHelper.err(getClass(), "全部终止投放后释放排期单，但排期单状态是预定，而不是确认或锁定，排期单编号：{}", releasedScheduleNumber);
		} else {
			occupations = occupationCustomDao.findByCurScheduleNumberFazzyLikeFrom(releasedScheduleNumber, DateUtils.getCurrentDateOfZero());
		}
		
		updateScheduleToRelease(schedule);
		
		release(releasedScheduleNumber, occupations);
    	LoggerHelper.info(getClass(), "上线单终止投放后，释放对应排期单成功，编号{}", releasedScheduleNumber);
    }
    
    @Override
    public List<Long> releasePositionOccupation(List<Long> positionIds, List<Long> adContentIds) {
    	List<Long> releasedAdContentIds = new ArrayList<Long>();
    	if (CollectionUtils.isEmpty(positionIds)) {
    		return releasedAdContentIds;
    	}
    	List<String> confirmedAndLockedNumbers = new ArrayList<String>();
    	List<Schedule> reservedSchedules = new ArrayList<Schedule>();
    	getReservedAndConfirmedAndLockedSchedules(positionIds, confirmedAndLockedNumbers, reservedSchedules, releasedAdContentIds);
    	
    	terminateAllAfterRelease(confirmedAndLockedNumbers);
    	completeScheduleProcess(reservedSchedules);
    	// update schedule of positionIds to release status
    	int releaseScheduleCounts = scheduleDao.updateScheduleStatusByPositionIdIn(positionIds, ScheduleStatus.released);
    	// delete relationships in adContentIds
    	int deleteRelationCounts = relationDao.deleteByAdContentIds(adContentIds);
    	// clear position occupation
    	int cleaerOccCounts = positionOccupationDao.clearScheduleNumberByPositionId(positionIds);
		LoggerHelper.info(getClass(), "禁用位置：{}后，释放排期单{}个，删除关联关系{}个，清空投放时间{}个", positionIds, releaseScheduleCounts,
				deleteRelationCounts, cleaerOccCounts);
		return releasedAdContentIds;
    }

	private void getReservedAndConfirmedAndLockedSchedules(List<Long> positionIds, List<String> confirmedAndLockedNumbers,
			List<Schedule> reservedSchedules, List<Long> adContentIds) {
		List<ScheduleStatus> statusList = new ArrayList<ScheduleStatus>(3);
    	statusList.add(ScheduleStatus.reserved);
    	statusList.add(ScheduleStatus.confirmed);
    	statusList.add(ScheduleStatus.locked);
    	List<Schedule> schedules = scheduleDao.findByPositionIdsAndStatus(positionIds, statusList);
    	for (Schedule schedule : schedules) {
			ScheduleStatus status = schedule.getStatus();
			String number = schedule.getNumber();
			if (schedule.getCompleted() != null && schedule.getCompleted() == 1) {
				continue;
			}
			if (ScheduleStatus.reserved.equals(status)) {
				reservedSchedules.add(schedule);
			} else if (ScheduleStatus.confirmed.equals(status)) {
				confirmedAndLockedNumbers.add(number);
			} else if (ScheduleStatus.locked.equals(status)) {
				confirmedAndLockedNumbers.add(number);
			}
			adContentIds.add(schedule.getAdContentId());
		}
	}

	private void completeScheduleProcess(List<Schedule> reservedSchedules) {
		if (CollectionUtils.isEmpty(reservedSchedules)) {
			return;
		}
		for (Schedule schedule : reservedSchedules) {
			completeBiddingProcess(schedule.getAdContentId(), schedule.getNumber());
		}
		LoggerHelper.info(getClass(), "禁用位置后，终止已经排期的竞价排期流程{}个", reservedSchedules.size());
	}

	private void terminateAllAfterRelease(List<String> confirmedScheduleNumbers) {
		if (CollectionUtils.isEmpty(confirmedScheduleNumbers)) {
			return;
		}
		for (String number : confirmedScheduleNumbers) {
			publishService.terminateAllAfterRelease(number);
		}
	}
	
	@Override
	public int releaseConfirmedSchedules(List<Schedule> schedules) {
		int count = 0;
		for (Schedule schedule : schedules) {
			Long adContentId = schedule.getAdContentId();
			String number = schedule.getNumber();
			try {
				int deleteCount = relationDao.deleteByAdContentId(adContentId);
				LoggerHelper.info(getClass(), "释放排期单[{}]，广告内容id[{}]，删除关联的relation[{}]个", number, adContentId,
						deleteCount);
				List<PositionOccupation> occupations = occupationCustomDao.findByCurScheduleNumberFazzyLikeFrom(number, DateUtils.getCurrentDateOfZero());
				
				updateScheduleToRelease(schedule);
				
				release(number, occupations);
				LoggerHelper.info(getClass(), "释放确认排期单成功，编号{}", number);
				count++;
				
			} catch (Exception e) {
				LoggerHelper.info(getClass(), "释放确认排期单失败，编号{}，错误原因：{}", number, e.getMessage());
			}
		}
		return count;
	}
}
