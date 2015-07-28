package com.baidu.gcrm.mail;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.baidu.gcrm.notice.model.Notice;
import com.baidu.gcrm.notice.model.Notice.ReceiverScope;
import com.baidu.gcrm.account.vo.RegisterMailVO;
import com.baidu.gcrm.ad.content.web.vo.AdcontentChangeVo;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.mail.MailTemplate;
import com.baidu.gcrm.common.velocity.VelocityUtil;
import com.baidu.gcrm.personalpage.web.vo.ChannelOperationVO;
import com.baidu.gcrm.publish.web.vo.PublishListVO;

public class MailHelper {

	public static void sendMail(IMailContent mailContent) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());

		mailTemplate.setTo(mailContent.getSendTo());
		mailTemplate.setCc(mailContent.getCc());

		String localeSuffix = getLocalePrefix(mailContent.getLocale());
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey(mailContent.getSubjectKey() + "." + localeSuffix));
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey(mailContent.getTemplateKey() + "." + localeSuffix),
				mailContent.getValueMap());
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}

	/**
	 * 发送排期单成功邮件
	 * 
	 * @param content
	 */
	public static void sendScheduleCompleteMail(ScheduleCompleteContent content) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());

		mailTemplate.setTo(content.getSendTo());

		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("schedule.complete.email.subject." + localeSuffix));
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("sendTo", content.getOperator());
		valueMap.put("adSubmitTime", content.getAdSubmitTime());
		valueMap.put("adContentNumber", content.getAdContentNumber());
		valueMap.put("occupationPeriods", content.getOccupationPeriods());
		Long adSolutionId = content.getAdSolutionId();
		if (adSolutionId != null) {
			valueMap.put("url", GcrmConfig.getConfigValueByKey("app.url").concat("views/main.jsp#/ad2?id=" ).concat(adSolutionId.toString()));
		}
		String insert = content.getInsertPeriods();
		if (insert != null && !"".equals(insert)) {
			valueMap.put("insertPeriods", insert);
		}
		valueMap.put("countryAgent", content.getCountryAgent());
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("schedule.complete.email.templateName." + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}

	public static void sendPositionDisableMail(PositionDisableContent content) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(content.getSendTo());

		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("position.disable.email.subject." + localeSuffix));
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("sendTo", content.getOperator());
		valueMap.put("operator", content.getOperator());
		valueMap.put("positionName", content.getPositionName());
		valueMap.put("adContentSubmitTime",
				DateUtils.getDate2String(DateUtils.YYYY_MM_DD_HH_MM_SS, content.getAdContentSubmitTime()));
		valueMap.put("adContentNumber", content.getAdContentNumber());
		valueMap.put("adContentURL", content.getAdContentURL());
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("position.disable.email.templateName." + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}

	public static void sendUpdateScheduleMail(ScheduleCompleteContent content, boolean needConfrim) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());

		mailTemplate.setTo(content.getSendTo());
		mailTemplate.setCc(content.getCc());
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("schedule.update.email.subject." + localeSuffix));

		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("sendTo", content.getOperator());
		valueMap.put("adSubmitTime", content.getAdSubmitTime());
		valueMap.put("adContentNumber", content.getAdContentNumber());
		valueMap.put("oldOccupationPeriods", content.getOldOccupationPeriods());
		valueMap.put("occupationPeriods", content.getOccupationPeriods());
		valueMap.put("scheduleUpdateTime", DateUtils.getCurrentFormatDate());
		valueMap.put("countryAgent", content.getCountryAgent());
		if (needConfrim) {
			valueMap.put("needConfrim", needConfrim);
		}
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("schedule.update.email.templateName." + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}

	private static String getLocalePrefix(LocaleConstants locale) {
		return locale.name().split("_")[0];
	}

	public static void sendScheduleReleasedMail(ScheduleCompleteContent content) {

		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());

		mailTemplate.setTo(content.getSendTo());
		mailTemplate.setCc(content.getCc());
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("schedule.release.email.subject." + localeSuffix));

		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("sendTo", content.getOperator());
		valueMap.put("adSubmitTime", content.getAdSubmitTime());
		valueMap.put("adContentNumber", content.getAdContentNumber());
		valueMap.put("scheduleUpdateTime", DateUtils.getCurrentFormatDate());
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("schedule.release.email.templateName." + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}
	public static void sendQuoteClashMail(QuoteCompleteContent content) {

		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(content.getSendTo());
		Set<String> to = new HashSet<String>();
		mailTemplate.setCc(to);
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("quote.clash.subuject." + localeSuffix));
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("sendTo", content.getSendTo());
		valueMap.put("operator", content.getOperator());
		valueMap.put("oldQuoteCreateDate", content.getOldQuoteCreateDate());
		valueMap.put("oldQuoteCode", content.getOldQuoteCode());
		valueMap.put("submitTer", content.getSubmitTer());
		valueMap.put("newQuoteCode", content.getNewQuoteCode());
		valueMap.put("type", content.getType());
		valueMap.put("oldEditValidDate", content.getOldEditValidDate());
		valueMap.put("addQuoteCode", content.getAddQuoteCode());
		valueMap.put("addValidDate", content.getAddValidDate());
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("quote.clash.email.templateName." + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}
	
	public static void sendOnlineApplyApprovedMail(OnlineApplyMailContent content) {

		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(content.getSendTo());
		mailTemplate.setCc(content.getCc());
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("onlineApply.approve.subject." + localeSuffix));
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("sendTo", content.getSendTo());
		valueMap.put("operator", content.getOperator());
		valueMap.put("customerName", content.getCustomerName());
		valueMap.put("contentNumber", content.getContentNumber());
		valueMap.put("contractNumber", content.getContractNumber());
		valueMap.put("adSolutionURL", content.getAdSolutionURL());
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("onlineApply.approve.subject.templateName." + localeSuffix), valueMap);
		
		//下边附中文邮件内容
		String zhBody=VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("onlineApply.approve.subject.templateName.zh"), valueMap);
		mailTemplate.setBody(body+zhBody);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}
	
	public static void sendOnlineApplyMail4ContractEffective(OnlineApplyMailContent content) {

		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(content.getSendTo());
		mailTemplate.setCc(content.getCc());
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("onlineApply.contractEffective.subject." + localeSuffix));
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("sendTo", content.getSendTo());
		valueMap.put("operator", content.getOperator());
		valueMap.put("adSolutionNumber", content.getAdSolutionNumber());
		valueMap.put("contractNumber", content.getContractNumber());
		valueMap.put("adSolutionURL", content.getAdSolutionURL());
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("onlineApply.contractEffective.subject.templateName." + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}
	public static void sendQuoteFinishMail(QuoteFinishContent content) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(content.getSendTo());
		mailTemplate.setInputStream(content.getInstream());
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "zh";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("quote.finish.email.subject." + localeSuffix));
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("sendTo", content.getSendTo());
		valueMap.put("operator", content.getOperator());
		valueMap.put("submitTer", content.getSubmitTer());
		valueMap.put("type", content.getPriceType());
		valueMap.put("platformName", content.getPlatFormName());
		valueMap.put("ValidDate", content.getValidStartTime());
		valueMap.put("siteName", content.getSiteName());
		valueMap.put("createTime", content.getSubmitTime());
		valueMap.put("url", content.getUrl());
		if (content.getPriceType() != null && content.getPriceType().length() != 0) {
			if (content.getPriceType().equals("广告单价")) {
				valueMap.put("cpc", content.getCpc());
				valueMap.put("cpt", content.getCpt());
				valueMap.put("cpa", content.getCpa());
				valueMap.put("cpm", content.getCpm());
				valueMap.put("cps1", content.getCps1());
			} else if (content.getPriceType().equals("分成")) {
				valueMap.put("RatioCustomer", content.getRatioCustomer());
				valueMap.put("RatioMine", content.getRatioMine());
				valueMap.put("RatioThird", content.getRatioThird());
			} else if (content.getPriceType().equals("返点")) {
				valueMap.put("RatioRebate", content.getRatioRebate());
			}

		}
		valueMap.put("PriceMessage", content.getPriceMessage());
		valueMap.put("PriceType", content.getPriceType());
		valueMap.put("quoUrl", content.getQuoteUrl());
		String body = VelocityUtil.merge(GcrmConfig.getConfigValueByKey("quote.finish.templateName." + localeSuffix),
				valueMap);
		mailTemplate.setBody(body);
		mailTemplate.setWordattachment(content.getwordName());
		ServiceBeanFactory.getMailService().sendMailByWord(mailTemplate);
	}
	
	public static void sendPublishContentMail(PublishContent content) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(content.getSendTo());
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("sendTo", content.getSendTo());
		valueMap.put("AdsubmitTime", content.getAdsubmitTime());
		valueMap.put("Description", content.getDescription());
		valueMap.put("Customer", content.getCustomer());
		valueMap.put("PeriodDescript", content.getPeriodDescription());
		valueMap.put("Operator", content.getOperator());
		valueMap.put("MaiType", content.getMaiType());
		if (content.getMaiType().equals("online")) {
			mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("publish.online.email.subject." + localeSuffix));
			valueMap.put("PmstartDate", content.getPmstartDate());
		}

		else if (content.getMaiType().equals("offline")) {
			mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("publish.offline.email.subject." + localeSuffix));

			valueMap.put("PmendDate", content.getPmendDate());
			valueMap.put("NextDate", content.getPdate());
		} else if (content.getMaiType().equals("terminate")) {
			mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("publish.terminate.email.subject." + localeSuffix));
			valueMap.put("PmterminateDate", content.getPmterminateDate());
		} else if (content.getMaiType().equals("reminders")) {
			mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("publish.reminders.email.subject." + localeSuffix));
			valueMap.put("materialUrl", content.getMaterialUrl());
		} else if (content.getMaiType().equals("materOngoing")) {
			mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("publish.material.email.subject." + localeSuffix));
			valueMap.put("adContenNumber", content.getAdContentNumber());
			valueMap.put("materialSubtime", content.getMaterialsSubtime());
			valueMap.put("materialUpdatetime", content.getMaterialsUpdatetime());
		}
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("publish.comeplete.email.templateName." + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}
	
	public static void sendeCollectContentMail(List<PublishListVO> voList, LocaleConstants currentLocale) {
	    if(CollectionUtils.isEmpty(voList)){
	        return;
	    }
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "zh";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("publish.collect.email.subject." + localeSuffix));
		mailTemplate.setTo(voList.get(0).getSendToers());
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("operator", voList.get(0).getOperator());
		valueMap.put("voList", voList);
		valueMap.put("PlatName", voList.get(0).getPlatName());
		valueMap.put("siteName", voList.get(0).getSiteName());
		valueMap.put("channelName", voList.get(0).getChannelName());
		valueMap.put("nowtime", DateUtils.getDate2String(DateUtils.YYYY_MM_DD, new Date()));
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("publish.CollectContent.email.templateName." + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}
	
	public static void sendeCollectMaterialMail(List<PublishListVO> voList, LocaleConstants currentLocale) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "zh";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("publish.collectmaterial.subject." + localeSuffix));
		mailTemplate.setTo(voList.get(0).getSendToers());

		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("operator", voList.get(0).getOperator());
		valueMap.put("voList", voList);
		valueMap.put("PlatName", voList.get(0).getPlatName());
		valueMap.put("siteName", voList.get(0).getSiteName());
		valueMap.put("channelName", voList.get(0).getChannelName());
		valueMap.put("nowtime", DateUtils.getDate2String(DateUtils.YYYY_MM_DD, new Date()));
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("publish.CollectMaterial.email.templateName." + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}
	/**
	 * 账号注册时发送邮箱验证码邮件
	 * 
	 * @param verifyCode
	 * @param currentLocale
	 */
	public static void sendRegisterMailVerifyMail(RegisterMailVO regMailVO, LocaleConstants locale) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(regMailVO.getSendTo());
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("account.registerMailVerify.email.subject."
				+ localeSuffix));

		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("verifyCode", regMailVO.getVerifyCode());
		valueMap.put("operator", regMailVO.getOperator());
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("account.registerMailVerify.email.templateName." + localeSuffix),
				valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}

	public static void sendSystemMail(String subject, String message) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(GcrmConfig.getAdminMailList());
		mailTemplate.setSubject(subject);
		mailTemplate.setBody(message);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}

	public static void sendPositionFullMail(List<ChannelOperationVO> voList) {
		MailTemplate mailTemplate = new MailTemplate();
		Set<String> to = new HashSet<String>();
		to.add(GcrmConfig.getConfigValueByKey("fully.baidu.mail"));
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(to);
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("position.full.email.subject.zh") + "("
				+ voList.get(0).getDate() + "--" + voList.get(voList.size() - 1).getDate() + ")");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("voList", voList);
		valueMap.put("beginDate", voList.get(0).getDate());
		valueMap.put("endDate", voList.get(voList.size() - 1).getDate());
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("position.Full.Situation.email.temlpateName.zh"), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}

	public static void sendRegisterSuccessMail(RegisterMailVO regMailVO, LocaleConstants locale) {
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(regMailVO.getSendTo());
		// String localeSuffix = getLocalePrefix(content.getLocale());
		String localeSuffix = "en";
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("account.registerSucc.email.subject." + localeSuffix));

		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("username", regMailVO.getUsername());
		valueMap.put("password", regMailVO.getPassword());
		valueMap.put("url", regMailVO.getUrl());
		valueMap.put("operator", regMailVO.getOperator());
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("account.registerSucc.email.templateName." + localeSuffix), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}
	
	public static void sendChangeAdcontentMail(AdcontentChangeVo vo){
		MailTemplate mailTemplate = new MailTemplate();
		mailTemplate.setFrom(GcrmConfig.getMailFromUser());
		mailTemplate.setTo(vo.getSendTo());
		mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("adcontent.change.email.subject.zh"));
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("oldAdcontent", vo.getOldadcontentNum());
		valueMap.put("newAdcontent", vo.getNewadcontentNum());
		valueMap.put("advertiser", vo.getAdvertiser());
		valueMap.put("oldonlineNum", vo.getOldonlineNum());
		valueMap.put("newonlineNum", vo.getNewonlineNum());
		valueMap.put("oldpositionName", vo.getOldPdname());
		valueMap.put("newpositionName", vo.getNewPdname());	
		valueMap.put("oldcontentVO", vo.getOldcontentVO());
		valueMap.put("newcontentVO", vo.getNewcontentVO());		
		valueMap.put("oldpdTime", vo.getOldpdTime());
		valueMap.put("newpdTime", vo.getNewpdTime());	
		valueMap.put("confirmTime",DateUtils.getDate2String(DateUtils.YYYY_MM_DD_HH_MM_SS,DateUtils.getString2Date(DateUtils.YYYY_MM_DD_HH_MM_SS,vo.getConfirmTime()))) ;
		valueMap.put("adOperator", vo.getAdOperator());
		valueMap.put("operator", vo.getOperator());
		String body = VelocityUtil.merge(
				GcrmConfig.getConfigValueByKey("adcontent.change.email.templateName.zh"), valueMap);
		mailTemplate.setBody(body);
		ServiceBeanFactory.getMailService().sendMail(mailTemplate);
	}

    public static void sendNotice(Notice notice, Set<String> mailToSet){
        if(CollectionUtils.isNotEmpty(mailToSet)){
            if(notice.getScope() == ReceiverScope.internal){//内部用户，一块发送
                MailTemplate mailTemplate = new MailTemplate();
                mailTemplate.setFrom(GcrmConfig.getMailFromUser());
                mailTemplate.setTo(mailToSet);
                mailTemplate.setSubject("[Baidu]" + notice.getTitle());
                mailTemplate.setBody(notice.getContent());
                ServiceBeanFactory.getMailService().sendMail(mailTemplate); 
            }
            else{
                for(String mail : mailToSet){//逐个发送，不暴漏其他收件人的邮箱，有空看看有没更好方案 TODO 
                    MailTemplate mailTemplate = new MailTemplate();
                    mailTemplate.setFrom(GcrmConfig.getMailFromUser());
                    Set<String> mailTmpSet = new HashSet<String>();
                    mailTmpSet.add(mail);
                    mailTemplate.setTo(mailTmpSet);
                    mailTemplate.setSubject("[Baidu]" + notice.getTitle());
                    mailTemplate.setBody(notice.getContent());
                    ServiceBeanFactory.getMailService().sendMail(mailTemplate);
                }
            }
        }
    }	
    
    public static void sendInfluencedAdSolutionMail(PositionStockContent content) {
        String localeSuffix = getLocalePrefix(content.getLocale());
        
        MailTemplate mailTemplate = new MailTemplate();
        mailTemplate.setFrom(GcrmConfig.getMailFromUser());
        mailTemplate.setTo(content.getSendTo());
        mailTemplate.setSubject(GcrmConfig.getConfigValueByKey("stock.influence.email.subject." + localeSuffix));
        
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("receiverName", content.getReceiverName());
        valueMap.put("timeRange", content.getTimeRange());
        valueMap.put("adSolutionOperator", content.getAdSolutionOperator());
        valueMap.put("adSolutionNumber", content.getAdSolutionNumber());
        valueMap.put("positionName", content.getPositionName());
        valueMap.put("influencedAdSolutionNumber", content.getInfluencedAdSolutionNumber());
        String body = VelocityUtil.merge(
                GcrmConfig.getConfigValueByKey("stock.influence.email.templateName." + localeSuffix), valueMap);
        mailTemplate.setBody(body);
        ServiceBeanFactory.getMailService().sendMail(mailTemplate); 
        
    }
}
