package com.baidu.gcrm.schedule.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply.ApprovalStatus;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentApplyService;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.material.service.IMaterialManageService;
import com.baidu.gcrm.ad.material.vo.MaterialContentVO;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.model.Contract.ContractState;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.mail.IMailService;
import com.baidu.gcrm.common.mail.MailTemplate;
import com.baidu.gcrm.common.velocity.VelocityUtil;
import com.baidu.gcrm.occupation.service.IPositionOccupationCommonService;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.schedule.dao.IScheduleRepository;
import com.baidu.gcrm.schedule.model.Schedule;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;
import com.baidu.gcrm.schedule.service.IScheduleTimerService;
import com.baidu.gcrm.user.model.User;

@Service
public class ScheduleTimerServiceImpl implements IScheduleTimerService {
	@Autowired
	IPositionOccupationCommonService positionOccService;
	@Autowired
	IMaterialManageService materialService;
	@Autowired
	IScheduleRepository scheduleDao;
	@Autowired
	IAdSolutionContentService contentService;
	@Autowired
	IMailService mailService;
	@Autowired
	IAdSolutionContentApplyService contentApplyService;
	@Autowired
	IPublishService publishService;
	
	@Override
	public void remindAdvertiseOperatorTimer() {
		LoggerHelper.info(getClass(), "开始上线前通知执行人。");
		List<Schedule> schedules = getConfirmedSchedulesBeginningInDays(3);
		List<Long> adContentIds = getAdContentIds(schedules);
		Map<Long, AdSolutionContent> contentMap = getAdContentMap(adContentIds);
		Map<Long, User> contentOperatorMap = contentService.findOperatorsByAdContentIdIn(adContentIds);
		for (Schedule schedule : schedules) {
			try {
				AdSolutionContent content = contentMap.get(schedule.getAdContentId());
				if (content == null || StringUtils.isEmpty(content.getPeriodDescription())) {
					LoggerHelper.err(getClass(), "排期单没有对应广告内容或投放时间为空，排期单编号：{}", schedule.getNumber());
					continue;
				}
				User receiver = contentOperatorMap.get(content.getId());
				if (receiver == null) {
					LoggerHelper.err(getClass(), "排期单对应广告内容没有执行人，排期单编号：{}，内容编号：{}", schedule.getNumber(),
							content.getNumber());
					continue;
				}
				sendMailIfSatisfied(content, receiver);
			} catch (Exception e) {
				LoggerHelper.err(getClass(), "发送上线预警失败，排期单编号：{}，内容id：{}，错误原因：{}", schedule.getNumber(),
						schedule.getAdContentId(), e.getMessage());
			}
		}
		LoggerHelper.info(getClass(), "完成上线前通知执行人。");
	}

	@Override
	public void autoReleaseScheduleTimer() {
		LoggerHelper.info(getClass(), "开始自动释放排期单定时任务。");
		
		List<Schedule> schedules = getConfirmedSchedulesBeginningInDays(0);
		if (CollectionUtils.isEmpty(schedules)) {
			LoggerHelper.info(getClass(), "没有今天开始投放的排期单。");
		}
		
		Map<Long, AdSolutionContent> contentMap = getAdContentMap(getAdContentIds(schedules));
		List<Schedule> satisfiedSchedules = new ArrayList<Schedule>();
		for (Schedule schedule : schedules) {
			AdSolutionContent content = contentMap.get(schedule.getAdContentId());
			if (content == null || !isMaterialPassed(content)
					|| (!isContractPassed(content) && !isPrePublishApplyPassed(content))) {
				satisfiedSchedules.add(schedule);
			}
		}
		
		int successCount = positionOccService.releaseConfirmedSchedules(satisfiedSchedules);
		
		LoggerHelper.info(getClass(), "完成自动释放任务，需要释放{}个，成功释放{}个", satisfiedSchedules.size(), successCount);
	}
	
	private List<Schedule> getConfirmedSchedulesBeginningInDays(int offsetDays) {
		String today = DateUtils.getCurrentFormatDate(DateUtils.YYYY_MM_DD);
		String dateTo = DateUtils.getDate2String(DateUtils.YYYY_MM_DD, DateUtils.getNDayFromDate(new Date(), offsetDays + 1));
		return scheduleDao.findSchedulesBeginningInDays(ScheduleStatus.confirmed, today, dateTo);
	}
	
	private List<Long> getAdContentIds(List<Schedule> schedules) {
		List<Long> adContentIds = new ArrayList<Long>();
		for (Schedule schedule : schedules) {
			adContentIds.add(schedule.getAdContentId());
		}
		return adContentIds;
	}
	
	private Map<Long, AdSolutionContent> getAdContentMap(List<Long> adContentIds) {
		return contentService.findAdContentMap(adContentIds);
	}
	
	/**
	 * 物料是否审核通过
	 */
	private boolean isMaterialPassed(AdSolutionContent content) {
		MaterialContentVO materialContentVO = materialService.findAdContVoMaterByCont(content);
		if (materialContentVO == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 是否有有效的合同或PO
	 */
	private boolean isContractPassed(AdSolutionContent content) {
		// 广告内容必须有PO号或广告方案有有效的合同
		if (StringUtils.isBlank(content.getPoNum())) {
			Contract contract = contentService.findWriteBackContractByContentId(content.getId());
			if (contract == null || !ContractState.VALID.equals(contract.getState())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否通过提前上线申请流程
	 */
	private boolean isPrePublishApplyPassed(AdSolutionContent content) {
		AdSolutionContentApply contentApply = contentApplyService.findAdContentApplyByConId(content.getId());
		return contentApply != null && ApprovalStatus.approved.equals(contentApply.getApprovalStatus());
	}
	
	public void sendMailIfSatisfied(AdSolutionContent content, User receiver) {
		boolean isMaterValid = isMaterialPassed(content);
		boolean isContractValid = isContractPassed(content);
		boolean isPrepublishValid = isPrePublishApplyPassed(content);
		if (!isMaterValid && (!isContractValid && !isPrepublishValid)) {
			// 没有关联已生效的合同或PO（提前上线也未通过）并且没有审核通过的物料
			sendPublishAlertMail(content, receiver, "publish.alert.both.email.templateName.");
			return;
		} else if (!isMaterValid) {
			// 没有审核通过的物料
			sendPublishAlertMail(content, receiver, "publish.alert.material.email.templateName.");
			return;
		} else if (!isContractValid && !isPrepublishValid) {
			// 没有关联已生效的合同或PO（提前上线也未通过）
			sendPublishAlertMail(content, receiver, "publish.alert.contract.email.templateName.");
			return;
		} else {
			// 有关联已生效的合同或PO（提前上线也未通过）并且有审核通过的物料
			LoggerHelper.info(getClass(), "排期单应该是锁定状态，内容编号：{}，但是现在是确认状态，更新为锁定。", content.getNumber());
			publishService.tryLockScheduleAndCreatePublish(content);
		}
	}
	
	private void sendPublishAlertMail(AdSolutionContent content, User receiver, String templateName) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		Set<String> tos = new HashSet<String>();
		tos.add(receiver.getEmail());
		mailTemplate.setTo(tos);

		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("publish.alert.email.subject." + localeSuffix));
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("sendTo", receiver.getRealname());
		valueMap.put("number", content.getNumber());
		String startDate = getStartDate(content);
		valueMap.put("date", startDate);
		valueMap.put("url", GcrmConfig.getConfigValueByKey("app.url").concat("views/main.jsp#/ad2?id=" ).concat(content.getAdSolutionId().toString()));
		String body = VelocityUtil.merge(GcrmConfig.getConfigValueByKey(templateName + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		mailService.sendMail(mailTemplate);
		LoggerHelper.info(getClass(), "发送上线预警邮件，收件人：{}，内容编号：{}，开始上线时间：{}。", receiver.getRealname(),
				content.getNumber(), startDate);
	}


	private String getStartDate(AdSolutionContent content) {
		String periodDescription = content.getPeriodDescription();
		if (StringUtils.isBlank(periodDescription)) {
			return StringUtils.EMPTY;
		}
		return periodDescription.substring(0, 10);
	}
}
