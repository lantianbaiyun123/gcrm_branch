package com.baidu.gcrm.notice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.account.model.Account.AccountStatus;
import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.auth.service.IUserDataRightService;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.notice.dao.INoticeReceiversRepository;
import com.baidu.gcrm.notice.dao.INoticeRepository;
import com.baidu.gcrm.notice.dao.INoticeRepositoryCustom;
import com.baidu.gcrm.notice.model.Notice;
import com.baidu.gcrm.notice.model.Notice.NoticeStatus;
import com.baidu.gcrm.notice.model.Notice.ReceiverScope;
import com.baidu.gcrm.notice.model.NoticeReceivers;
import com.baidu.gcrm.notice.service.INoticeService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;

/**
 * @author shijiwen
 *
 */
@Service
public class NoticeServiceImpl implements INoticeService {

    private Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);
    @Autowired
    private INoticeRepository noticeDao;
    @Autowired
    private INoticeRepositoryCustom noticeRepositoryCustom;
    @Autowired
    private INoticeReceiversRepository noticeReceiversDao;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserDataRightService userDataRightService;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    private final static Long noticePeriod = 30*24*3600L;
    
    @Override
    public List<NoticeReceivers> findNoticeListByReceiverUcid(Long ucid) {
        if(ucid == null){
            logger.error("no ucid for findNoticeListByReceiverUcid");
            return new ArrayList<NoticeReceivers>();
        }
        return noticeReceiversDao.findNoticeIdsByReceiverId(ucid);
    }

    @Override
    public Notice saveOrUpdateNotice(Notice notice, boolean send) throws CRMBaseException {
        Notice newNotice;
        if (notice.getId() == null || !noticeDao.exists(notice.getId())) {
            notice.setStatus(NoticeStatus.draft);
            notice.setCreateTime(new Date());
            User user = RequestThreadLocal.getLoginUser();
            notice.setCreateOperator(user.getUcid());
            notice.setCreateOperatorName(user.getRealname());
            newNotice = noticeDao.save(notice);
        } else {
            newNotice = noticeDao.findOne(notice.getId());
            newNotice.setContent(notice.getContent());
            newNotice.setTitle(notice.getTitle());
            newNotice.setReceivers(notice.getReceivers());
            newNotice.setScope(notice.getScope());
            newNotice = noticeDao.saveAndFlush(newNotice);
        }

        if (send) {
            newNotice = send(newNotice.getId());
        }
        return newNotice;
    }

    @Override
    public Notice send(Long noticeId) throws CRMBaseException {
        logger.info("start send notice:"+noticeId);
        final Notice notice = noticeDao.findOne(noticeId);
        final Set<String> mailToSet = new HashSet<String>();
        List<Long> idList = new ArrayList<Long>();
        if (notice != null) {
            String receivers = notice.getReceivers();
            if (!StringUtils.isEmpty(receivers)) { // 有指定的某些发送目标人，可能是内部用户 也可能是外部用户，根据users:{} or customers:{}来判断
                JSONObject jo = JSONObject.parseObject(receivers);
                if (jo != null && jo.containsKey("users")) {// 指定了若干内部用户
                    JSONArray jsonArray = jo.getJSONArray("users");
                    if (jsonArray != null && jsonArray.size() > 0) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jo1 = jsonArray.getJSONObject(i);
                            Long ucid = jo1.getLong("ucid");
                            String mail = jo1.getString("email");
                            mailToSet.add(mail);
                            idList.add(ucid);
                        }
                    }
                }
                if (jo != null && jo.containsKey("customers")) {// 指定了若干外部广告主
                    JSONArray jsonArray = jo.getJSONArray("customers");
                    if (jsonArray != null && jsonArray.size() > 0) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jo1 = jsonArray.getJSONObject(i);
                            Long customerNumber = jo1.getLong("data");
                            List<Account> customerAccountList = accountService.findByCustomerNumberAndAccountStatus(customerNumber, AccountStatus.ENABLE);
                            for (Account a : customerAccountList) {
                                mailToSet.add(a.getEmail());
                                idList.add(a.getUcid());
                            }
                        }
                    }
                }
            }
            
            if(StringUtils.isEmpty(receivers) || idList.isEmpty()){// receivers字符串非空但没有效值 。没指定少数接收者，要根据发送范围取出全部内外部用户
                ReceiverScope scope = notice.getScope();
                if (scope == ReceiverScope.external) {//取出所有外部广告主，根据customerNumber非空 并且 状态为有效查询account表
                    List<Account> customerAccountList = accountService.findByCustomerNumberNotNullAndStatus(AccountStatus.ENABLE);
                    for (Account a : customerAccountList) {
                        mailToSet.add(a.getEmail());
                        idList.add(a.getUcid());
                    }
                } 
                if(scope == ReceiverScope.internal){//取出所有内部用户，只判断enble
                    List<User> allUsers = userService.findAllByStatus(AccountStatus.ENABLE.ordinal());
                    for (User u : allUsers) {
                        mailToSet.add(u.getEmail());
                        idList.add(u.getUcid());
                    }
                } 
            }

        } else {//noticeId无效
            logger.error("no notice exists for Id:"+noticeId);
            throw new CRMBaseException("no notice exists for Id:"+noticeId);
        }

        //把所有接收者--noticeId 映射关系存入noticeReceivers表
        List<NoticeReceivers> noticeReceivers = new ArrayList<NoticeReceivers>();
        for (Long a : idList) {
            NoticeReceivers nr = new NoticeReceivers();
            nr.setNoticeId(noticeId);
            nr.setNoticeTitle(notice.getTitle());
            nr.setReceived(false);
            nr.setUcid(a);
            noticeReceivers.add(nr);
        }
        noticeReceiversDao.save(noticeReceivers);

        //发送邮件
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    logger.info("starting send email:"+notice.getTitle()+" to:"+mailToSet);
                    MailHelper.sendNotice(notice, mailToSet);
                }
                catch(Exception e){
                    logger.error("taskExecutor system mail failed!",e);
                }
                
            }

        });

        //如果前面的都执行成功，则更新原始公告表：改状态 记录时间
        notice.setSentTime(new Date());
        notice.setStatus(NoticeStatus.sent);
        Notice newNotice = noticeDao.saveAndFlush(notice);
        return newNotice;
    }

    @Override
    public void readByReceiverUcid(Notice notice, Long ucid) {
        if(notice != null && notice.getId() != null && ucid != null){
            noticeReceiversDao.readByReceiverId(notice.getId(), ucid);
        }
        else{
            logger.error("failed to read notice because no notice or ucid");
        }
    }

    @Override
    public Page<Notice> findManageNoticesByUser(Page<Notice> noticePage) {
        User user = RequestThreadLocal.getLoginUser();
        noticePage.setResultClass(Notice.class);
        List<User> userSubList = userDataRightService.getSubUserListByUcId(user.getUcid());//取得user对应的所有下属
        Set<Long> ucidList = new HashSet<Long>();
        ucidList.add(user.getUcid());
        if (!CollectionUtils.isEmpty(userSubList)) {
            for (User u : userSubList) {
                ucidList.add(u.getUcid());
            }
        }
        return noticeRepositoryCustom.findByCreatorIdList(noticePage, ucidList);
    }

    @Override
    public Notice findNoticeById(Long noticeId) {
        return noticeDao.findOne(noticeId);
    }
    
    @Override
    public void clearTimeoutTimer(){
        List<Notice> noticeList = noticeDao.findByStatus(NoticeStatus.sent);
        List<Long> idTimeoutList = new ArrayList<Long>();
        if(CollectionUtils.isEmpty(noticeList)){
            for(Notice n : noticeList){
                Date sent = n.getSentTime();
                Date now = new Date();
                if(sent != null && sent.getTime() < now.getTime() - noticePeriod*1000){
                    idTimeoutList.add(n.getId());
                }
            }
        }
        
        if(idTimeoutList.size() > 0){
            noticeDao.updateBatchStatus(idTimeoutList, NoticeStatus.invalid);
        }
    }

    public void setNoticeDao(INoticeRepository noticeDao) {
        this.noticeDao = noticeDao;
    }

    public void setNoticeRepositoryCustom(INoticeRepositoryCustom noticeRepositoryCustom) {
        this.noticeRepositoryCustom = noticeRepositoryCustom;
    }

    public void setNoticeReceiversDao(INoticeReceiversRepository noticeReceiversDao) {
        this.noticeReceiversDao = noticeReceiversDao;
    }
}
