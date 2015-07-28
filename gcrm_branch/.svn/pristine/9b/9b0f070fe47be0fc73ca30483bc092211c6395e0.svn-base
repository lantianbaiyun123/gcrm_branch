package com.baidu.gcrm.common.code.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.code.dao.ICodeRepository;
import com.baidu.gcrm.common.code.model.Code;
import com.baidu.gcrm.common.code.service.ICodeService;

@Service
public class CodeServiceImpl implements ICodeService{
    
    @Autowired
    ICodeRepository codeRepository;

    @Override
    public void save(Code code) {
        codeRepository.save(code);
    }

    @Override
    public List<Code> findByCodeType(String codeType) {
        return codeRepository.findByCodeType(codeType);
    }

    @Override
    public int incrementCodeValue(String codeType, String oldCodeValue,
            String newCodeValue) {
        return codeRepository.incrementCodeValue(codeType, oldCodeValue, newCodeValue);
    }

    @Override
    public int resetCodeValue(String codeType, String codeValue) {
        return codeRepository.resetCodeValue(codeType, codeValue);
    }
    

}
