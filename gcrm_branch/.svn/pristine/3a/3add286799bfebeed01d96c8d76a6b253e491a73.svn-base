package com.baidu.gcrm.bpm.service;

import java.util.List;

import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.common.LocaleConstants;

public interface IGCrmTaskInfoService {
    public String getTaskInfo(List<Activity> acts, String msgCode) ;
    public String getTaskInfo(String userName,String msgCode);
    public String getTaskInfo(String userName,String msgCode,List<String> otherInfos);
    public String convertTaskInfo(RemindType remindType,String taskInfo, LocaleConstants locale) ;
    public String removeDoneNodeInfo(String taskInfo,String actDef);
}
