package com.baidu.gcrm.common.util;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.common.GcrmConfig;

public class GcrmHelper {
    public static File getLocalTempFile(String fileName) {
        return new File(GcrmConfig.getLocalTempDir() + fileName);
    }

    public static File getLocalBackupDir(String fileName) {
        return new File(GcrmConfig.getLocalBackupDir() + fileName);
    }

    /**
     * 如果positionNumber不足6位，则在前面补0以满足6位要求
     * 
     * @param positionNumber
     * @return
     */
    public static String getPositionNumber(String positionNumber) {

        if (StringUtils.isNotBlank(positionNumber) && positionNumber.length() < 6) {
            String temp = "";
            for (int i = 0; i < 6 - positionNumber.length(); i++) {
                temp += "0";
            }
            positionNumber = temp + positionNumber;
        }
        return positionNumber;
    }
}
