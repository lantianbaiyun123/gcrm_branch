package com.baidu.gcrm.valuelistcache.model;


import com.baidu.gcrm.i18n.model.BaseI18nModel;

public class BillingModel extends BaseI18nModel{
	public enum BillingModelType{
		NORMAL,
		CPS;
		
		public static BillingModelType valueOf(Integer value){
			if(value == null){
				return null;
			}
			BillingModelType[] values = BillingModelType.values(); 
			for(BillingModelType status : values){
				if(status.ordinal() == value){
					return status;
				}
			}
			return null;
		}
	}
	
	public static final Long CPM_ID = 4L;
	
	public static final Long CPT_ID = 5L;
	
	private Long id;
	private String name;
	private Integer type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
