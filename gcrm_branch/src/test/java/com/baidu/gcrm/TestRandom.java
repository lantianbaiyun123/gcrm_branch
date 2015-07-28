package com.baidu.gcrm;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.random.IRandomCheckCallback;
import com.baidu.gcrm.common.random.IRandomStringService;
import com.baidu.gcrm.common.random.RandomType;

@Ignore
public class TestRandom extends BaseTestContext{
    
    @Autowired
    IRandomStringService randomStringService;
    
    @Test
    public  void testRandom() {
        Map<String,String> detectMap = new HashMap<String,String> ();
        
        try {
            int i = 0;
            while (i < 30) {
                String str = randomStringService.random(6, RandomType.random_publish, null);
                String extstsStr = detectMap.get(str);
                if (extstsStr == null) {
                    detectMap.put(str, "");
                    System.err.println(str);
                } else {
                    System.err.println("=========random conflicted=============");
                    Assert.assertNull(extstsStr);
                }
                
                i++;
            }
            i = 0;
            while (i < 30) {
                String str = randomStringService.random(6, RandomType.random_publish, new IRandomCheckCallback(){

                    @Override
                    public boolean exists(String randomStr) {
                        return true;
                    }
                    
                });
                String extstsStr = detectMap.get(str);
                if (extstsStr == null) {
                    detectMap.put(str, "");
                    System.err.println(str);
                } else {
                    System.err.println("=========random conflicted=============");
                    Assert.assertNull(extstsStr);
                }
                
                i++;
            }
            
        } catch (CRMBaseException e) {
            e.printStackTrace();
        }
        
    }
}
