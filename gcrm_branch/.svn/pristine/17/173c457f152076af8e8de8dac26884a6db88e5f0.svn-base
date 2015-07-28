package com.baidu.gcrm.common.code.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.common.code.model.Code;

public interface ICodeRepository extends JpaRepository<Code, Long> {
    
    List<Code> findByCodeType(String codeType);
    
    @Modifying
    @Query("update Code set codeValue=?3 where codeType=?1 and codeValue=?2")
    int incrementCodeValue(String codeType, String oldCodeValue, String newCodeValue);
    
    @Modifying
    @Query("update Code set codeValue=?2 where codeType=?1 ")
    int resetCodeValue(String codeType, String codeValue);
    
}
