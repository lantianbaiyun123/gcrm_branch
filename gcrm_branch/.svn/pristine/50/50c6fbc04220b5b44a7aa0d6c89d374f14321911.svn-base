package com.baidu.gcrm.ad.content.model;

import com.baidu.gcrm.ad.content.model.AdSolutionContent.ApprovalStatus;

public enum ModifyStatus  {
	
	/** 未修改 */
	NOMODIFY,
	/** 修改 */
	MODIFYED;
	
	
	
	
	public static ModifyStatus valueOf(Integer value){
		if(value == null){
			return null;
		}
		ModifyStatus[] values = ModifyStatus.values(); 
		for(ModifyStatus status : values){
			if(status.ordinal() == value){
				return status;
			}
		}
		return null;
	}
}
