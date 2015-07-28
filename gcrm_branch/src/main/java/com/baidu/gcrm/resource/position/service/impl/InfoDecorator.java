package com.baidu.gcrm.resource.position.service.impl;

public interface InfoDecorator {
    
    String SPLIT_FLAG = ">";
    
    int PISITION_NAME_LEVEL = 5;
    
    StringBuilder buildI18nEnExtraInfo();
    
    StringBuilder buildI18nCnExtraInfo();

}
