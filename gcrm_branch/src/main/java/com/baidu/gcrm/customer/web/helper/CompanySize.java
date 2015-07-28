package com.baidu.gcrm.customer.web.helper;

public enum CompanySize {
	less_50,
	less_100,
	less_200,
	less_500,
	less_1000,
	more_1000;
	
	public int getValue() {
        return this.ordinal();
    }
	
	public static CompanySize valueOf(Integer value){
		if(value == null){
			return null;
		}
		CompanySize[] values = CompanySize.values(); 
		for(CompanySize companySize : values){
			if(companySize.ordinal() == value){
				return companySize;
			}
		}
		return null;
	}
}
