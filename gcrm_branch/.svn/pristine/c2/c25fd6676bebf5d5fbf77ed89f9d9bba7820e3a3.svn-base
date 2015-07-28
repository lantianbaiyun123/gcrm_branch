package com.baidu.gcrm.ws.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * gcrm为bpm提供的触发事件接口，功能：释放排期单。具体如下：
 * <li>将排期单的状态修改为“released”</li>
 * <li>将广告内容与排期单中的投放时间id关联关系去掉</li>
 * <li>去掉投放时间中历史排期单中的释放编号</li>
 * @author anhuan
 *
 */
@WebService(targetNamespace = "http://gcrm.baidu.com")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IScheduleReleaseService {
	void releaseSchedule(String adContentId, String scheduleNumber, String sales);
}
