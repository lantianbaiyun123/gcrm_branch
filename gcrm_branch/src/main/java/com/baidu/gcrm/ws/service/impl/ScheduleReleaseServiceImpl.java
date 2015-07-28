package com.baidu.gcrm.ws.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.dao.IAdSolutionContentRepository;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.common.exception.CRMWebServiceRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.occupation.service.IPositionOccupationCommonService;
import com.baidu.gcrm.ws.service.IScheduleReleaseService;

@WebService(endpointInterface = "com.baidu.gcrm.ws.service.IScheduleReleaseService", targetNamespace = "http://gcrm.baidu.com")
@Service("ScheduleReleaseServiceImpl")
public class ScheduleReleaseServiceImpl implements IScheduleReleaseService {
	@Autowired
	IAdvertiseSolutionService adSolutionService;
	@Autowired
	IAdSolutionContentRepository contentDao;
	@Autowired
	IPositionOccupationCommonService occupationCommonService;
	
	@Override
	public void releaseSchedule(String adContentId, String scheduleNumber, String userName) {
		try {
			LoggerHelper.info(getClass(), "销售{}72小时内没有确认，释放排期单{}。", userName, scheduleNumber);
			// release schedule, fixed and rotation position are the same
			Long contentId = Long.valueOf(adContentId);
			occupationCommonService.releaseAfter72Hours(scheduleNumber, contentId);
		} catch(Exception e) {
			String msg = String.format("释放排期单%s失败，对应广告内容id：%s，失败原因：%s。", scheduleNumber, adContentId, e.getMessage());
			LoggerHelper.err(getClass(),msg);
			LoggerHelper.err(getClass(), e.getMessage(),e);
			throw new CRMWebServiceRuntimeException(e);
		}
	}

}
