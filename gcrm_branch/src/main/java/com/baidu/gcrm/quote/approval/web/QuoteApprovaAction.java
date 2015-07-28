package com.baidu.gcrm.quote.approval.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.ad.cancel.service.IAdContentCancelRecordsService;
import com.baidu.gcrm.ad.service.IAdvertiseMaterialApplyService;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.web.helper.ActivityInfo;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.quote.approval.record.service.IQtApprovalRecordService;
import com.baidu.gcrm.quote.approval.web.vo.QuoteApprovalInfoView;
import com.baidu.gcrm.quote.service.QuotationMainService;
import com.baidu.gcrm.quote.web.vo.QuotationMainView;
import com.baidu.gcrm.resource.position.service.IPositionService;

@Controller
@RequestMapping("qouteapprovalinfo")
public class QuoteApprovaAction extends ControllerHelper {

	private static final Logger logger = LoggerFactory.getLogger(QuoteApprovaAction.class);
	private static final String messagePrefix = "advertise.solution.approvale.";
	@Autowired
	IBpmProcessService bpmProcessService;
	@Autowired
	IAdContentCancelRecordsService cancelRecordsService;
	@Autowired
	IPositionService positionService;
	@Autowired
	IAdvertiseMaterialApplyService materialApplyService;
	@Autowired
	QuotationMainService quotationMainService;
	IQtApprovalRecordService qtApprovalRecordService;
	@RequestMapping("/view/{id}/{activityId}")
	@ResponseBody
	public JsonBean<QuoteApprovalInfoView> view(@PathVariable("id") Long id,
			@PathVariable("activityId") String activityId, HttpServletRequest request) {

		if (activityId == null || !activityId.contains("_")) {
			String activityIdMessage = MessageHelper.getMessage(messagePrefix + "activity.id.error", currentLocale);
			return JsonBeanUtil.convertBeanToJsonBean(null, activityIdMessage);
		}

		QuoteApprovalInfoView infoView = new QuoteApprovalInfoView();
		try {
			ActivityInfo activity = bpmProcessService.getActivityInfoByActivityId(activityId);
			if(id != null && !id.toString().equals(activity.getForeignKey())){
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
			setQuotaionMain(infoView, id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
		}
		QuotationMainView mainView = quotationMainService.findQuotationVOById(id, currentLocale, request);
		infoView.setQuoteMainView(mainView);
		return JsonBeanUtil.convertBeanToJsonBean(infoView);
	}

	private void setQuotaionMain(QuoteApprovalInfoView infoView, Long id) throws CRMBaseException {

		infoView.setAccount(null);

		infoView.setCustomerI18nView(null);
	}
	@RequestMapping("/down/{id}")
	@ResponseBody
	public JsonBean<String> down(@PathVariable("id") Long id) {
		try {
			qtApprovalRecordService.withDrawApproval(id);
		}

		catch (Exception e) {
			return JsonBeanUtil.convertBeanToJsonBean("error");
		}
		return JsonBeanUtil.convertBeanToJsonBean("OK");
	}

}