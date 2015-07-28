package com.baidu.gcrm.schedule1.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.DateEditor;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.schedule.service.IScheduleTimerService;
import com.baidu.gcrm.schedule1.model.ScheduleVO;
import com.baidu.gcrm.schedule1.model.Schedules;
import com.baidu.gcrm.schedule1.service.ISchedulesService;
import com.baidu.gcrm.schedule1.web.vo.ScheduleConditionVO;
import com.baidu.gcrm.schedule1.web.vo.ScheduleListVO;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;

@Controller
@RequestMapping("/schedule")
public class ScheduleAction extends ControllerHelper {
	Logger logger = LoggerFactory.getLogger(ScheduleAction.class);

	@Autowired
	ISchedulesService scheduleService;

	@Autowired
	AdvertisingPlatformServiceImpl adPlatformService;// 平台
	
	@Autowired
	IScheduleTimerService scheduleTimerService;

	@RequestMapping("/findSchedule/{id}")
	@ResponseBody
	public JsonBean<ScheduleVO> findSchedule(@PathVariable("id") Long id) {
	    ScheduleVO scheduleVO = scheduleService.findScheduleAd(id, getCurrentLocale());
		if (scheduleVO == null) {
			return JsonBeanUtil.convertBeanToJsonBean(null, "schedule.not.found");
		}
		return JsonBeanUtil.convertBeanToJsonBean(scheduleVO);
	}

	/**
	 * 排期单INDEX页面跳转，初始化查询条件
	 * 
	 * @param scheduleCondition
	 * @return
	 */
	@RequestMapping("/index")
	@ResponseBody
	public JsonBean<PageWrapper<ScheduleListVO>> index(HttpServletRequest request, ScheduleConditionVO scheduleCondition) {
		request.setAttribute("adPlatformList", adPlatformService.getAllByLocale(currentLocale));
		request.setAttribute("queryType", ScheduleConditionVO.QueryType.values());
		request.setAttribute("scheduleStatus", Schedules.ScheduleStatus.values());
		PageWrapper<ScheduleListVO> page = qureyForPage(scheduleCondition);
		return JsonBeanUtil.convertPageBeanToJsonBean(page);
	}

	/**
	 * 查询排期单列表
	 * 
	 * @param scheduleCondition
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public JsonBean<PageWrapper<ScheduleListVO>> qurey(@RequestBody ScheduleConditionVO scheduleCondition) {
		return JsonBeanUtil.convertPageBeanToJsonBean(qureyForPage(scheduleCondition));
	}
	
	private PageWrapper<ScheduleListVO> qureyForPage(ScheduleConditionVO scheduleConditionVO) {
	    scheduleConditionVO.setResultClass(ScheduleListVO.class);
		PageWrapper<ScheduleListVO> page = scheduleService.findByCondition(scheduleConditionVO);
		scheduleConditionVO.setPositionDateIds(null);
		return page;
	}

	@InitBinder("queryScheduleDate")
	private void submitCustomer(ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DateEditor(DateEditor.DEFAULT_DATEFORMAT));
	}
	
	@RequestMapping("/autoRelease")
	@ResponseBody
	public JsonBean<String> autoRelease() {
		scheduleTimerService.autoReleaseScheduleTimer();
		return JsonBeanUtil.convertBeanToJsonBean("success");
	}
	
	@RequestMapping("/remindAdvertiseOperator")
	@ResponseBody
	public JsonBean<String> remindAdvertiseOperator() {
		scheduleTimerService.remindAdvertiseOperatorTimer();
		return JsonBeanUtil.convertBeanToJsonBean("success");
	}

}
