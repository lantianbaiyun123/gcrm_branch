package com.baidu.gcrm.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.bpm.dao.IActivityNameI18nRepository;
import com.baidu.gcrm.bpm.model.ActivityNameI18n;
import com.baidu.gcrm.bpm.service.IGCrmTaskInfoService;
import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.vo.TaskInfoModel;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gson.JsonParseException;
import com.baidu.gson.JsonParser;
import com.baidu.tianlu.common.utils.JsonUtils;

@Service
public class GCrmTaskInfoServiceImpl implements IGCrmTaskInfoService {

    @Autowired
    IUserService userService;

    @Autowired
    IActivityNameI18nRepository activityNameI18nRepository;

    @Override
    public String getTaskInfo(List<Activity> acts, String msgCode) {
        if (acts == null || acts.size() == 0) {
            return "";
        }
        TaskInfoModel taskInfoModel = new TaskInfoModel();
        taskInfoModel.setMainKey(msgCode);
        taskInfoModel.addValue2Map(0, DateUtils.getCurrentFormatDate(DateUtils.YYYY_MM_DD_HH_MM));
        StringBuffer sb = new StringBuffer();
       
        Map<String, String> activityMap = new HashMap<String, String>();
      try{
        
        for (Activity ac : acts) {
            String key = ac.getActDefId();
            String performer = ac.getPerformer();
            if(StringUtils.isBlank(performer)){
                continue;
            }
            performer = performer.replaceAll("\\]\\[", ",").replaceAll("[\\[|\\]]", "");
            if (activityMap.containsKey(key)) {         
                activityMap.put(key, activityMap.get(key) + "," +performer);
            } else {
                activityMap.put(key,performer);
            }
        }
        if(activityMap.size() ==0){
            return "";
        }
        for (String role : activityMap.keySet()) {
            sb.append(role + ":");
            if (activityMap.get(role) != null) {
                String performer = activityMap.get(role);
                if(StringUtils.isBlank(performer)){
                    continue;
                }
                
                String[] users = performer.split(",");
                for (String uuapName : users) {
                    User user = userService.findUserByUuapName(uuapName);
                    if (user != null && StringUtils.isNotBlank(user.getRealname())) {
                        performer = performer.replace(uuapName, user.getRealname());
                    }
                }
                sb.append(performer);
            }
            sb.append(";");
        }

        taskInfoModel.addValue2Map(1, sb.substring(0, sb.length() - 1));
      }catch(Exception e){
          e.printStackTrace();
      }
        return JsonUtils.objToJson(taskInfoModel);

    }

    @Override
    public String getTaskInfo(String userName, String msgCode) {

        TaskInfoModel taskInfoModel = new TaskInfoModel();
        taskInfoModel.setMainKey(msgCode);
        taskInfoModel.addValue2Map(0, DateUtils.getCurrentFormatDate(DateUtils.YYYY_MM_DD_HH_MM));
        if(StringUtils.isNotBlank(userName))
        taskInfoModel.addValue2Map(1, userName);
        return JsonUtils.objToJson(taskInfoModel);
    }

    @Override
    public String getTaskInfo(String userName, String msgCode, List<String> otherInfos) {
        TaskInfoModel taskInfoModel = new TaskInfoModel();
        taskInfoModel.setMainKey(msgCode);
        taskInfoModel.addValue2Map(0, DateUtils.getCurrentFormatDate(DateUtils.YYYY_MM_DD_HH_MM));
        taskInfoModel.addValue2Map(1, userName);
        for (int i = 0; i < otherInfos.size(); i++) {
            taskInfoModel.addValue2Map(i + 2, otherInfos.get(i));
        }
        return JsonUtils.objToJson(taskInfoModel);
    }

    @Override
    public String convertTaskInfo(RemindType remindType, String taskInfo, LocaleConstants locale) {

        // 如果taskInfo 为空直接返回“”;
        String resultStr = "";
        if (!isGoodJson(taskInfo)) {
            return resultStr;
        }
        try {
            TaskInfoModel taskInfoModel = JsonUtils.jsonToObj(taskInfo, TaskInfoModel.class);

            String mainKeyStr = taskInfoModel.getMainKey();

            Map<Integer, String> valueMap = taskInfoModel.getValueMap();
            List<String> tempArray = new ArrayList<String>();

            Integer size = valueMap.keySet().size();
            if (size > 0) {
                tempArray.add(valueMap.get(0));
            }
            if (size > 1) {
                StringBuffer resultSb = new StringBuffer();

                for (int i = 1; i < size; i++) {
                    resultSb.delete(0, resultSb.length());
                    
                    String value = valueMap.get(i);
                    String[] taskArray = value.split(";");
                   
                    int taskArrayNum = taskArray.length;
                    for (int j = 0; j < taskArrayNum; j++) {
                        String keyValue = taskArray[j];
                      
                        if (keyValue.indexOf(":") > 0) {
                            String[] keyValueArray = keyValue.split(":");
                            String key = keyValueArray[0];
                           
                            ActivityNameI18n activeityName = activityNameI18nRepository
                                    .findByProcessDefIdAndLocaleAndActivityId( GcrmConfig.getConfigValueByKey(remindType.getProcessDefineId()), locale, key);
                            
                            if (activeityName != null)
                                resultSb.append(activeityName.getActivityName());
                            else
                                resultSb.append(MessageHelper.getMessage(key, locale));
                            resultSb.append(" ：");
                            if(keyValueArray.length>1)
                                resultSb.append(keyValueArray[1]);
                        } else {
                            resultSb.append(MessageHelper.getMessage(keyValue, null, locale));
                        }
                        resultSb.append("  ；");
                    }
                    tempArray.add(resultSb.substring(0, resultSb.length() - 1));
                }
           
            }
            resultStr = MessageHelper.getMessage(mainKeyStr, tempArray.toArray(new String[tempArray.size()]),
                    locale);

        } catch (Exception e) {
            LoggerHelper.err(getClass(), e.getMessage(), e);
        }
        return resultStr;
    }

    private boolean isGoodJson(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	} 
    /**
     * 
     * 功能描述:并行节点审批时 移除已完结节点的提示信息
     * @ 因需求需要 目前删除逻辑如下：如果该节点值包含一个人，测将该节点移除否则值将操作人移除
     * removeDoneNodeInfo
     * @创建人:	 chenchunhui01
     * @创建时间: 	2014年7月14日 下午1:52:49     
     * @param taskInfo
     * @param actDef
     * @return   
     * @return String  
     * @exception   
     * @version
     */
    public String removeDoneNodeInfo(String taskInfo,String actDef){
        
        String resultStr = "";
        if (!isGoodJson(taskInfo)) {
            return resultStr;
        }
        try {
            TaskInfoModel taskInfoModel = JsonUtils.jsonToObj(taskInfo, TaskInfoModel.class);

            Map<Integer, String>valueMap = taskInfoModel.getValueMap();
            if(!valueMap.containsKey(1)){
                return resultStr;
            }
            //key为1的数据为taskinfo中存储工作流节点与操作人对应关系的数据。
            String tempTaskInfo = valueMap.get(1);
            
            String[] tempActInfoArray = tempTaskInfo.split(";");

            StringBuffer tempResultStr = new StringBuffer();
            String operateName = RequestThreadLocal.getLoginUser().getRealname();         
            for(String tempAct :tempActInfoArray){
                if(tempAct.indexOf(actDef)>-1){
                    String[] keyValueArray = tempAct.split(":");
                    if(keyValueArray.length>1){
                    
                        String[] nameArray =keyValueArray[1].split(",");
                        //如果名字只有一个则将整个节点移除
                        if(nameArray.length ==1){
                            continue;
                        }
                        tempResultStr.append(actDef).append(":");
                        for(String name :nameArray){
                            if(operateName.equals(name)){
                                continue;
                            }
                            tempResultStr.append(name).append(",");
                            
                        }
                        //将最后一个逗号替换为分号
                        tempResultStr.replace(tempResultStr.length()-1, tempResultStr.length(), ";");
                    }
                } else 
                    tempResultStr.append(tempAct).append(";");
            }
            if(tempResultStr.length()==0)
                return "";
            valueMap.put(1,tempResultStr.substring(0, tempResultStr.length()-1));

            return JsonUtils.objToJson(taskInfoModel);

        }catch(Exception e){
            LoggerHelper.err(getClass(), e.getMessage(), e);
        }
        return "";
    }
    
}
