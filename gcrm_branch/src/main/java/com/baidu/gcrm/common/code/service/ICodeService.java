package com.baidu.gcrm.common.code.service;

import java.util.List;

import com.baidu.gcrm.common.code.model.Code;

public interface ICodeService {
    
    void save(Code code);
    
    List<Code> findByCodeType(String codeType);
    
    int incrementCodeValue(String codeType, String oldCodeValue, String newCodeValue);
    
    int resetCodeValue(String codeType, String codeValue);

}
