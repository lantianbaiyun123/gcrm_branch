package com.baidu.gcrm.bpm.web.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.bpm.service.IAssignmentHandler;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;

public class BaseStartProcessBean extends StartProcessBean {
    
    private String processStartBeanName;

    /**
     * 启动流程实例时，是否绑定activity
     */
    private String bindActivityId;

    /**
     * 流程流转参数
     */
    private Map<String, Object> activityDataMap;

    
    private Map<ParticipantConstants,IAssignmentHandler> assignmentHandlerMap;
    
    private Map<String,Object> customParams;
    
  
    public Map<String, Object> getActivityDataMap() {
        return activityDataMap;
    }

    public void setActivityDataMap(Map<String, Object> activityDataMap) {
        this.activityDataMap = activityDataMap;
    }

    public void putActivityData2Map(String key,Object value){
        if(this.activityDataMap ==null)
            activityDataMap = new HashMap<String, Object>();
        activityDataMap.put(key, value);
    }
    
    public boolean isBindActivity() {
        return StringUtils.isBlank(bindActivityId) ? false : true;
    }

    public String getBindActivityId() {
        return bindActivityId;
    }

    public void setBindActivityId(String bindActivityId) {
        this.bindActivityId = bindActivityId;
    }

    public String getProcessStartBeanName() {
        return processStartBeanName;
    }

    public void setProcessStartBeanName(String processStartBeanName) {
        this.processStartBeanName = processStartBeanName;
    }
    public Map<ParticipantConstants, IAssignmentHandler> getAssignmentHandler() {
        return assignmentHandlerMap;
    }

    public void putAssignmentHandler2Map(ParticipantConstants key, IAssignmentHandler handler) {
        if(this.assignmentHandlerMap ==null){
            assignmentHandlerMap = new HashMap<ParticipantConstants, IAssignmentHandler>();
        }
        assignmentHandlerMap.put(key, handler);
    }

    public IAssignmentHandler getAssignmentHandlerByParicipantId(String participantId){
        if(this.assignmentHandlerMap!=null){
            ParticipantConstants participantConstants = ParticipantConstants.valueOf(participantId);
            if(assignmentHandlerMap.containsKey(participantConstants)){
                return assignmentHandlerMap.get(participantConstants);
            }
        }
        return null;
    }
    
    
    public Map<String, Object> getCustomParams() {
        return customParams;
    }

    public void setCustomParams(Map<String, Object> customParams) {
        this.customParams = customParams;
    }

    @Override
    public void validate() {

    }

    @Override
    public String toString() {
        StringBuffer infoStr;
        String resultStr = "";
        if (activityDataMap != null) {
            infoStr = new StringBuffer();
            Set<String> keys = activityDataMap.keySet();
            for (String key : keys) {
                infoStr.append(key).append("=").append(activityDataMap.get(key));
                infoStr.append(",");
            }
            infoStr.deleteCharAt(infoStr.length() - 1);
            resultStr = processStartBeanName + "[" + infoStr.toString() + "]";
        }
        return resultStr;
    }
}
