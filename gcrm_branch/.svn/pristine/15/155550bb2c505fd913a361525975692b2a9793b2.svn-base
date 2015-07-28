package com.baidu.gcrm.common.random.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.code.model.Code;
import com.baidu.gcrm.common.code.service.ICodeService;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.random.IRandomCheckCallback;
import com.baidu.gcrm.common.random.IRandomStringService;
import com.baidu.gcrm.common.random.RandomType;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.valuelist.dao.IValuelistCacheDao;
import com.baidu.gcrm.valuelist.service.IValuelistWithCacheService;
import com.baidu.gcrm.valuelist.utils.TableMetaData;
import com.baidu.gcrm.valuelistcache.service.impl.CodeCacheServiceImpl;

@Service("randomStringServiceImpl")
public class RandomStringServiceImpl implements IRandomStringService{
    
    Logger logger = LoggerFactory.getLogger(RandomStringServiceImpl.class);
    
    @Resource(name="taskExecutor")
    ThreadPoolTaskExecutor taskExecutor;
    
    @Autowired
    CodeCacheServiceImpl codeCacheService;
    
    @Autowired
    ICodeService codeService;
    
    @Autowired
    IValuelistWithCacheService valuelistWithCacheService;
    
    @Resource(name = "codeTable")
    TableMetaData codeTableMetaData;
    
    @Autowired
    IValuelistCacheDao valuelistCacheDao;
    
    @Override
    public String random(int length, RandomType randomType,
            IRandomCheckCallback checkCallback) throws CRMBaseException{
        DecimalFormat format = null;
        String prefixStr = null;
        int prefixLength = 0;
        int randomLength = length;
        if (randomType != null) {
            int postfixLength = Integer.parseInt(GcrmConfig.getConfigValueByKey("random.postfix.length"));
            randomLength = postfixLength;
            
            if (length > postfixLength) {
                prefixLength = length - postfixLength;
                int prefixIndex = 0;
                StringBuilder formatStr = new StringBuilder();
                while (prefixIndex < prefixLength) {
                    formatStr.append("0");
                    prefixIndex++;
                }
                format = new DecimalFormat(formatStr.toString());
                Code currCode = getCodeByRandomType(randomType, false);
                Integer prefixValue = Integer.valueOf(currCode.getCodeValue());
                prefixStr = checkPrefixValue(randomType, prefixValue,
                        prefixLength, format);
            }
            
        }
        
        
        String temRandomStr =  random(randomLength, prefixStr);
        if (checkCallback == null) {
            return temRandomStr;
        }
        if (!checkCallback.exists(temRandomStr)) {
            return temRandomStr;
        } else {
            int retryCount = Integer.parseInt(GcrmConfig.getConfigValueByKey("random.retry.limit"));
            temRandomStr = retryRandom(retryCount, randomLength, prefixStr, checkCallback);
            if (temRandomStr != null) {
                return temRandomStr;
            }
            //too many conflict,update prefix
            if (randomType != null && format != null) {
                Integer prefixValue = incrementRandomPrefixValue(randomType);
                prefixStr = checkPrefixValue(randomType, prefixValue,
                        prefixLength, format);
            }
            //send conflict notify email
            sendMail("[info]随机串模块冲突提醒",
                    new StringBuilder()
                        .append("系统启动自恢复，对【随机模块】随机串前缀进行更新。<br>")
                        .append("请检查是否需要修改随机串前缀增长周期或增加随机串长度 <br>")
                        .append("随机模块类型：")
                        .append(randomType).toString());
            //try again
            System.err.println("============update======"+prefixStr);
            int conflictedRetryCount = Integer.parseInt(GcrmConfig.getConfigValueByKey("random.conflict.retry.limit"));
            temRandomStr = retryRandom(conflictedRetryCount, randomLength, prefixStr, checkCallback);
            if (temRandomStr != null) {
                return temRandomStr;
            }
            //conflict heavy,send warn email
            sendMail("[warn]随机串模块冲突严重报警",
                    new StringBuilder()
                        .append("系统随机模块对串前缀进行更新后依然出现严重冲突。<br>")
                        .append("请修改随机串前缀增长周期或增加随机串长度 <br>")
                        .append("随机模块类型：")
                        .append(randomType).toString());
            throw new CRMBaseException("generate random failed .");
        }
    }
    
    @Override
    public String random(int length, IRandomCheckCallback checkCallback)
            throws CRMBaseException {
        return random(length, null, checkCallback);
    }
    
    private String retryRandom(int retryLimit, int length,
            String randomPrefix, IRandomCheckCallback callback) 
                    throws CRMBaseException {
        int i = 0;
        while ( i < retryLimit) {
            String temRandomStr = random(length, randomPrefix);
            if (!callback.exists(temRandomStr)) {
                return temRandomStr;
            }
            i++;
        }
        return null;
    }
    
    private String random(int length, String randomPrefix) throws CRMBaseException{
        
        if (randomPrefix == null) {
            return RandomStringUtils.randomNumeric(length);
        } else {
            String postfixStr = RandomStringUtils.randomNumeric(length);
            return new StringBuilder().append(randomPrefix).append(postfixStr).toString();
        }
    }
    
    private void sendMail(final String subject, final String message) {
        taskExecutor.execute(new Runnable(){
            @Override
            public void run() {
                try {
                    MailHelper.sendSystemMail(subject, message);
                } catch (Exception e) {
                    logger.error("RandomStringGenerateServiceImpl send maul failed!",e);
                }
            }
        });
    }
    
    private Integer incrementRandomPrefixValue(RandomType randomType) throws CRMBaseException{
        if (randomType == null) {
            return null;
        }
        String codeType = randomType.name();
        Code currCode = getCodeByRandomType(randomType, true);
        String oldCodeValue = currCode.getCodeValue();
        int currCount = Integer.parseInt(currCode.getCodeValue()) + 1;
        String currCodeValue = String.valueOf(currCount);
        int changeCount = codeService.incrementCodeValue(codeType, oldCodeValue, currCodeValue);
        if (changeCount < 1) {
            oldCodeValue = String.valueOf(currCount);
            currCodeValue = String.valueOf(currCount+1);
            changeCount = codeService.incrementCodeValue(codeType, oldCodeValue, currCodeValue);
        }
        //TODO 需替换成刷新缓存集群
        Map<String,String> valueMap = new HashMap<String,String> ();
        valueMap.put("id", currCode.getId().toString());
        valueMap.put("code_type", codeType);
        valueMap.put("code_value", currCodeValue);
        valueMap.put("code_order", "1");
        valuelistCacheDao.add("g_code", valueMap);
        
        return Integer.valueOf(currCodeValue);
    }
    
    private String checkPrefixValue(RandomType randomType, Integer prefixValue,
            int prefixLength, DecimalFormat format) {
        String currFormatPrefixStr = format.format(prefixValue);
        if (currFormatPrefixStr != null && currFormatPrefixStr.length() > prefixLength) {
            String resetValue = "0";
            codeService.resetCodeValue(randomType.name(), resetValue);
            currFormatPrefixStr = format.format(resetValue);
            sendMail("[info]随机串模块前缀用尽重置提醒",
                    new StringBuilder()
                        .append("系统启动自动处理，随机串前缀已用尽，进行重置。<br>")
                        .append("请检查是否需要修改随机串前缀增长周期或增加随机串长度 <br>")
                        .append("随机模块类型：")
                        .append(randomType).toString());
            
        }
        
        return currFormatPrefixStr;
    }
    
    private Code getCodeByRandomType(RandomType randomType, boolean loadPrefixFromDB) throws CRMBaseException{
        String codeType = randomType.name();
        List<Code> codeList = null;
        if (!loadPrefixFromDB) {
            codeList = codeCacheService.getAllByCodeType(codeType);
        }
                
        Code code = null;
        if (CollectionUtils.isEmpty(codeList) || loadPrefixFromDB) {
            //check db 
            System.out.println("query db");
            codeList = codeService.findByCodeType(codeType);
            //init db
            if (CollectionUtils.isEmpty(codeList)) {
                String codeValueStr = "1";
                Code currCode = generateCodeBean(codeType, codeValueStr);
                try {
                    codeService.save(currCode);
                } catch (Exception e) {
                    logger.warn("concurrency cause init codeType code failed, often do not occur . ",e);
                }
                code = currCode;
            } else {
                code = codeList.get(0);
            }
            
            //TODO 需替换成刷新缓存集群
            Map<String,String> valueMap = new HashMap<String,String> ();
            valueMap.put("id", code.getId().toString());
            valueMap.put("code_type", codeType);
            valueMap.put("code_order", "1");
            valueMap.put("code_value", code.getCodeValue());
            valuelistCacheDao.add("g_code", valueMap);
        } else {
            code = codeList.get(0);
        }
        if (code == null) {
            throw new CRMBaseException("RandomType not found.");
        }
        
        return code;
    }
    
    private Code generateCodeBean(String codeType, String codeValue) {
        Code newCode = new Code();
        newCode.setCodeType(codeType);
        newCode.setCodeValue(codeValue);
        newCode.setCodeDesc("init RandomType");
        newCode.setCodeEnum(null);
        newCode.setCodeOrder(Long.valueOf(1));
        return newCode;
    }

    @Override
    public void incrementAllRandomPrefixValue() {
        RandomType[] randomTypes = RandomType.values();
        for (RandomType temRandomType : randomTypes) {
            try {
                incrementRandomPrefixValue(temRandomType);
            } catch (CRMBaseException e) {
                logger.error("task incrementAllRandomPrefixValue  failed!",e);
                sendMail("[warn]随机串模块前缀值刷新任务报警",
                        new StringBuilder()
                            .append("刷新随机模块对串前缀出现错误。<br>")
                            .append("随机模块类型：")
                            .append(temRandomType.name()).toString());
            }
        }
        
    }

    


}
