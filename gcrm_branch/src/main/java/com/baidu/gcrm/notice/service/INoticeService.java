package com.baidu.gcrm.notice.service;

import java.util.List;

import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.notice.model.Notice;
import com.baidu.gcrm.notice.model.NoticeReceivers;

public interface INoticeService {

	/*
	 * 根据当前系统使用者来获得需要展示的公告，首先判断是内部用户 还是外部用户，然后去总表查询（缓存），再结合特定部分目标收听者表（缓存），再结合已读表
	 * AccountType accountType =  ServiceBeanFactory.getAccountService().findAccountTypeByUcId(ucid);
	 */
	public List<NoticeReceivers>  findNoticeListByReceiverUcid(Long ucid);
	
	/*
	 * 创建一个新的公告，仅仅生成ID和存储，不发布，所以不更新缓存
	 * 检查角色和权限 roles = userRightsService.findUserRolesByUcId();
	{ParticipantConstants.cash_leader.name().equals(role.getRoleTag()))}
		在service调用sent,avoid transaction
	}
	 */
	
	/*
	 * 仅仅更新下公告，没点发送，此时需要保存到DB，如果变更了收件人列表
	 */
	public Notice saveOrUpdateNotice(Notice notice, boolean send) throws CRMBaseException;
	
	/*
	 * 根据公告ID来完成发送动作：在数据库中修改状态，并且根据指定收件人列表来发送邮件，根据是否指定部分人群来放一个缓存键值对 或两个缓存键值对
	 */
	public Notice send(Long noticeId) throws CRMBaseException;
	
	/*
	 * 某用户已读某条公告消息，做标记，以后不再给此用户发送，存入用户已读表，不更新缓存
	 */
	public void readByReceiverUcid(Notice notice, Long ucid) throws CRMBaseException;
	
	/*
	 * 获得当前内部人员可看到的所有公告，包括自己发布的，和自己的所有下属发布的，要不要判断是否内部人员？好像没必要,page
	 * IUserDataRightService.getSubUserListByUcId
	 */
	public Page<Notice> findManageNoticesByUser(Page<Notice> noticePage);
	
	/*
	 * 根据公告ID来查看某条公告的详情
	 * 
	 */
	public Notice findNoticeById(Long noticeId);
	
	/*
     * 定期清理一些过期公告
     */
    public void clearTimeoutTimer();
}
