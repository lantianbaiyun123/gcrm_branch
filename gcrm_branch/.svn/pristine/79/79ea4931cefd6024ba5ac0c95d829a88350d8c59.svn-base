package com.baidu.gcrm.publish.service;

import java.util.List;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.model.PublishMailType;
import com.baidu.gcrm.publish.web.vo.PublishListVO;

public interface IPublishMailService {
	/**
	 * 终止投放邮件
	 * @param publish
	 * @param currentLocale
	 */
	void terminateContentByMail(Publish publish);	
	/**
	 * 催办邮件
	 * @param id
	 * @param currentLocale
	 */

	void remindersContetnByMail(Long id,LocaleConstants currentLocale);
	/**
	 * 操作上下线邮件
	 * @param date
	 * @param currentLocale
	 */
	void ongoingOrEndByMail(PublishDate date,Publish publish);

	/**
	 * 
	 * 功能说明
     * 每天晚上0：05分，查看系统里，当天的上线任务；按平台、站点、频道汇总；
     * 数据范围：
     * 当天为计划上线时间；
     * 截止到当天时间，还未完成上线，且计划下线时间在当天之后的单据
     * 物料已审核通过
     * 广告内容状态“已确认”
	 * @param currentLocale
	 * @return
	 */
	List<PublishListVO> findPulishMail(LocaleConstants currentLocale,PublishMailType type);	
	void materialOngoing(Publish publish);
}
