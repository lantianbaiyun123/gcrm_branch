package com.baidu.gcrm.schedule.web;

import java.util.Date;
import java.util.Map;

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
import com.baidu.gcrm.schedule.model.Schedule;
import com.baidu.gcrm.schedule.service.IScheduleService;
import com.baidu.gcrm.schedule.service.IScheduleTimerService;
import com.baidu.gcrm.schedule.web.helper.ScheduleCondition;
import com.baidu.gcrm.schedule.web.vo.ScheduleListBean;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;

@Controller
@RequestMapping("/schedule1")
public class Schedule1Action extends ControllerHelper {
	Logger logger = LoggerFactory.getLogger(Schedule1Action.class);

	@Autowired
	IScheduleService scheduleService;

	@Autowired
	AdvertisingPlatformServiceImpl adPlatformService;// 平台
	
	@Autowired
	IScheduleTimerService scheduleTimerService;

	@RequestMapping("/findSchedule/{id}")
	@ResponseBody
	public JsonBean<Map<String, Object>> findSchedule(@PathVariable("id") Long id) {
		Map<String, Object> schedulemap = scheduleService.findScheduleAd(id, getCurrentLocale());
		if (schedulemap == null) {
			return JsonBeanUtil.convertBeanToJsonBean(null, "schedule.not.found");
		}
		return JsonBeanUtil.convertBeanToJsonBean(schedulemap);
	}

	/**
	 * 排期单INDEX页面跳转，初始化查询条件
	 * 
	 * @param scheduleCondition
	 * @return
	 */
	@RequestMapping("/index")
	@ResponseBody
	public JsonBean<PageWrapper<ScheduleListBean>> index(HttpServletRequest request, ScheduleCondition scheduleCondition) {
		request.setAttribute("adPlatformList", adPlatformService.getAllByLocale(currentLocale));
		request.setAttribute("queryType", ScheduleCondition.QueryType.values());
		request.setAttribute("scheduleStatus", Schedule.ScheduleStatus.values());
		PageWrapper<ScheduleListBean> page = qureyForPage(scheduleCondition);
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
	public JsonBean<PageWrapper<ScheduleListBean>> qurey(@RequestBody ScheduleCondition scheduleCondition) {
		PageWrapper<ScheduleListBean> page = qureyForPage(scheduleCondition);
		return JsonBeanUtil.convertPageBeanToJsonBean(page);
	}
	/**
	 * 公用查询排期单列表
	 * 
	 * @param scheduleCondition
	 * @return
	 */
	private PageWrapper<ScheduleListBean> qureyForPage(ScheduleCondition scheduleCondition) {
		scheduleCondition.setResultClass(ScheduleListBean.class);
		return scheduleService.findByCondition(scheduleCondition);
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
