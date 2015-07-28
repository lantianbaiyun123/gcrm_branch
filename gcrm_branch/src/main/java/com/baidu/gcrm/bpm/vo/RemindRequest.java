package com.baidu.gcrm.bpm.vo;

import java.util.List;

public class RemindRequest {
    /** 当前催办人 */
    private String reminder; 

    /** 催办原因 */
    private String reason; 

    /** 消息的发送方式 0：邮件 1：短信 2：全部 3都不通知*/
    private Integer notifyType; 
    
    /**被催办的人*/
    private List<String> toBeReminded;
    
    private RemindType type;
    
    private String key;

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}

	public List<String> getToBeReminded() {
		return toBeReminded;
	}

	public void setToBeReminded(List<String> toBeReminded) {
		this.toBeReminded = toBeReminded;
	}

	public RemindType getType() {
		return type;
	}

	public void setType(RemindType type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
    
}
