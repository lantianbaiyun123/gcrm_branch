package com.baidu.gcrm.common.random;

import com.baidu.gcrm.common.exception.CRMBaseException;

public interface IRandomStringService {
    
    String random(int length, RandomType randomTyp, IRandomCheckCallback checkCallback) throws CRMBaseException;
    
    String random(int length, IRandomCheckCallback checkCallback) throws CRMBaseException;
    
    void incrementAllRandomPrefixValue();
    
}
