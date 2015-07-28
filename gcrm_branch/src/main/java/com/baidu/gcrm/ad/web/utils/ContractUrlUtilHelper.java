package com.baidu.gcrm.ad.web.utils;

import com.baidu.gcrm.common.GcrmConfig;

public class ContractUrlUtilHelper {
    
    public static String getContractHomeUrl() {
        return new StringBuilder().append(GcrmConfig.getConfigValueByKey("cms.redirect.url"))
                    .append(GcrmConfig.getConfigValueByKey("cms.home.url")).toString();
                                    
    }
    
    public static String getContractDetailUrl(String contractNum) {
        return new StringBuilder().append(GcrmConfig.getConfigValueByKey("cms.redirect.url"))
                    .append(GcrmConfig.getConfigValueByKey("cms.contract.url"))
                    .append(contractNum).toString();
    }

}
