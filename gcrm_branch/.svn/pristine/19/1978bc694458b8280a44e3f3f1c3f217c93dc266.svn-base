/**
 * 
 */
package com.baidu.gcrm.notice.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baidu.gcrm.ad.web.utils.AutoSuggestBean;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.notice.model.Notice;
import com.baidu.gcrm.notice.model.Notice.ReceiverScope;
import com.baidu.gcrm.notice.model.NoticeReceivers;
import com.baidu.gcrm.notice.service.INoticeService;
import com.baidu.gcrm.notice.web.validator.NoticeValidator;
import com.baidu.gcrm.notice.web.vo.NoticeToReadVo;
import com.baidu.gcrm.notice.web.vo.NoticeVo;
import com.baidu.gcrm.notice.web.vo.ReceiveListVO;
import com.baidu.gcrm.notice.web.vo.SuggestedCustomerVO;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.user.web.vo.UserVO;
/**
 * @author shijiwen
 *
 */
@Controller
@RequestMapping("/notice")
public class NoticeAction extends ControllerHelper {
    private Logger logger = LoggerFactory.getLogger(NoticeAction.class);
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private INoticeService noticeService;
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/save")
    @ResponseBody
    public JsonBean<NoticeVo> saveOrUpdate(@RequestBody @Valid NoticeVo noticeVo, BindingResult result) {
        if(result.hasErrors()){
            return JsonBeanUtil.convertBeanToJsonBean(noticeVo, super.collectErrors(result), JsonBeanUtil.CODE_ERROR);
        }
        Notice notice = new Notice();
        notice.setId(noticeVo.getId());
        notice.setTitle(noticeVo.getTitle());
        notice.setContent(noticeVo.getContent());
        notice.setScope(noticeVo.getReceiveScope());
        if(notice.getScope()==ReceiverScope.external){
            if(noticeVo.getCustomers() != null){
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("customers", noticeVo.getCustomers());
                notice.setReceivers(JSONObject.toJSONString(m));
            }
        }
        else{
            if(noticeVo.getUsers() != null){
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("users", noticeVo.getUsers());
                notice.setReceivers(JSONObject.toJSONString(m));
            }
        }
        
        boolean send = noticeVo.getSend()==1;
        Map<String, String> errors = new HashMap<String, String>();
        Notice newNotice = null;
        try{
            newNotice = noticeService.saveOrUpdateNotice(notice, send);
        }
        catch(Exception e){
            logger.error("failed to saveOrUpdateNotice for "+JSONObject.toJSONString(noticeVo), e);
            errors.put("notice.operate.error", "notice.operate.error");
        }
        
        if(newNotice == null || !errors.isEmpty()){
            return JsonBeanUtil.convertBeanToJsonBean(null, errors);
        }
        
        return JsonBeanUtil.convertBeanToJsonBean(convertToNoticeVo(newNotice));
    }

    @RequestMapping("/send/{noticeId}")
    @ResponseBody
    public JsonBean<NoticeVo> send(@PathVariable Long noticeId) {
        Notice newNotice = null;
        try {
            newNotice = noticeService.send(noticeId);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<String, String>();
            logger.error("failed to send notice for: "+noticeId, e);
            if(e instanceof CRMBaseException){
                errors.put("notice.not.found.error", "notice.not.found.error"); 
            }
            else{
                errors.put("notice.operate.error", "notice.operate.error");
            }
            return JsonBeanUtil.convertBeanToJsonBean(null, errors);
        }
        return JsonBeanUtil.convertBeanToJsonBean(convertToNoticeVo(newNotice));

    }

    @RequestMapping("/managelist")
    @ResponseBody
    public JsonBean<Page<NoticeVo>> manageList(@RequestBody Page<Notice> noticePage) {
        Page<Notice> noticePageResult = noticeService.findManageNoticesByUser(noticePage);
        List<Notice> noticeList = noticePageResult.getContent();
        List<NoticeVo> noticeVoList = new ArrayList<NoticeVo>();
        if(!CollectionUtils.isEmpty(noticeList)){
            for(Notice notice : noticeList){
                 noticeVoList.add(convertToNoticeVo(notice));
            }
        }
        Page<NoticeVo> noticeVoPage = new Page<NoticeVo>();
        noticeVoPage.setPageNumber(noticePageResult.getPageNumber());
        noticeVoPage.setPageSize(noticePageResult.getPageSize());
        noticeVoPage.setContent(noticeVoList);
        noticeVoPage.setTotal(noticePage.getTotal());
        noticeVoPage.setTotalCount(noticePage.getTotal());
        noticeVoPage.setQueryAll(false);
        return JsonBeanUtil.convertBeanToJsonBean(noticeVoPage);
    }

    @RequestMapping("/detail/{noticeId}")
    @ResponseBody
    public JsonBean<NoticeVo> noticeDetail(@PathVariable Long noticeId) {
        Notice notice = noticeService.findNoticeById(noticeId);
        if(notice == null){
            logger.error("noticeDetail: no notice for id "+noticeId);
        }
        return JsonBeanUtil.convertBeanToJsonBean(convertToNoticeVo(notice));
    }
    
    private NoticeVo convertToNoticeVo(Notice notice){
        NoticeVo noticeVo = new NoticeVo();
        if(notice == null){
            return noticeVo;
        }
        noticeVo.setContent(notice.getContent());
        noticeVo.setCreateOperator(notice.getCreateOperator());
        noticeVo.setCreateOperatorName(notice.getCreateOperatorName());
        noticeVo.setCreateTime(notice.getCreateTime());
        noticeVo.setSentTime(notice.getSentTime());
        noticeVo.setId(notice.getId());
        noticeVo.setReceiveScope(notice.getScope());
        noticeVo.setTitle(notice.getTitle());
        noticeVo.setStatus(notice.getStatus());
        Long currentUserUcid = RequestThreadLocal.getLoginUser().getUcid();
        noticeVo.setIsOwner(currentUserUcid.equals(notice.getCreateOperator()));
        String receivers = notice.getReceivers();
        if (!StringUtils.isEmpty(receivers)) { // 有指定的某些发送目标人，可能是内部用户 也可能是外部用户，根据users:{} or customers:{}来判断
            JSONObject jo = JSONObject.parseObject(receivers);
            if (jo != null && jo.containsKey("users")) {// 指定了少数内部用户
                List<UserVO> users = JSONObject.parseArray(jo.getString("users"), UserVO.class);
                noticeVo.setUsers(users);
            }
            else if (jo != null && jo.containsKey("customers")) {// 指定了少数外部用户
                List<SuggestedCustomerVO> customers = JSONObject.parseArray(jo.getString("customers"), SuggestedCustomerVO.class);
                noticeVo.setCustomers(customers);
            }
        }
        return noticeVo;
    }

    @RequestMapping("/receivelist")
    @ResponseBody
    public JsonBean<List<ReceiveListVO>> receiveList() {
        User user = RequestThreadLocal.getLoginUser();
        List<NoticeReceivers> noticeReceivers = noticeService.findNoticeListByReceiverUcid(user.getUcid());
        List<ReceiveListVO> voList = new ArrayList<ReceiveListVO>();
        if (!CollectionUtils.isEmpty(noticeReceivers)) {
            for (NoticeReceivers nr : noticeReceivers) {
                ReceiveListVO vo = new ReceiveListVO();
                vo.setId(nr.getNoticeId());
                vo.setTitle(nr.getNoticeTitle());
                voList.add(vo);
            }
        }

        return JsonBeanUtil.convertBeanToJsonBean(voList);
    }

    @RequestMapping("/read/{noticeId}")
    @ResponseBody
    public JsonBean<NoticeToReadVo> readNotice(@PathVariable Long noticeId) {
        NoticeToReadVo noticeToReadVo = new NoticeToReadVo();
        Notice notice = noticeService.findNoticeById(noticeId);
        User user = RequestThreadLocal.getLoginUser();
        if(notice == null || user == null){
            return JsonBeanUtil.convertBeanToJsonBean(noticeToReadVo);
        }
        noticeToReadVo.setContent(notice.getContent());
        noticeToReadVo.setTitle(notice.getTitle());
        noticeToReadVo.setNoticeId(noticeId);
        try {
            noticeService.readByReceiverUcid(notice, user.getUcid());
        } catch (Exception e) {
            logger.error("failed to read notice:"+noticeId, e);
            return JsonBeanUtil.convertBeanToJsonBean(null, "notice.read.error");
        }
        return JsonBeanUtil.convertBeanToJsonBean(noticeToReadVo);
    }

    @RequestMapping("/customersSuggest")
    @ResponseBody
    public JsonBean<List<AutoSuggestBean<Long>>> customersSuggest(@RequestParam("query") String tag) {
        List<AutoSuggestBean<Long>> suggests = new ArrayList<AutoSuggestBean<Long>>();
        if (StringUtils.isBlank(tag)) {
            return JsonBeanUtil.convertBeanToJsonBean(suggests);
        }

        List<Customer> customers = customerService.findCustomerForNotice(tag);
        for (Customer customer : customers) {
            String companyName = customer.getCompanyName();
            suggests.add(new AutoSuggestBean<Long>(companyName, customer.getCustomerNumber()));
        }

        return JsonBeanUtil.convertBeanToJsonBean(suggests);
    }
    
    @RequestMapping("/suggestUser")
    @ResponseBody
    public JsonBean<List<UserVO>> usersSuggest(@RequestParam("query") String tag) {
        List<UserVO> suggestUserVO = new ArrayList<UserVO>();
        if (StringUtils.isBlank(tag)) {
            return JsonBeanUtil.convertBeanToJsonBean(suggestUserVO);
        }
        List<User> users = userService.findByName(tag);
        
        for (User user : users) {
            UserVO userVO = new UserVO();
            String realName = user.getRealname();
            String userName = user.getUsername();
            if (StringUtils.containsIgnoreCase(realName, tag) || StringUtils.containsIgnoreCase(userName, tag)) {
                userVO.setEmail(user.getEmail());
                userVO.setRealName(realName);
                userVO.setUcid(user.getUcid());
                userVO.setUsername(userName);
                suggestUserVO.add(userVO);
            }
        }
        return JsonBeanUtil.convertBeanToJsonBean(suggestUserVO);
    }

    @InitBinder("noticeVo")
    protected void initBinder(ServletRequestDataBinder binder) throws Exception {
        binder.setValidator(new NoticeValidator());
    }
}
