package com.baidu.gcrm.bpm.vo;

import java.util.HashMap;
import java.util.Map;

public class TaskInfoModel {
    private String mainKey;

    private Map<Integer, String> valueMap;

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    public Map<Integer, String> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<Integer, String> valueMap) {
        this.valueMap = valueMap;
    }

    public void addValue2Map(Integer key, String vaule) {
        if (this.valueMap == null) {
            valueMap = new HashMap<Integer, String>();
        }
        valueMap.put(key, vaule);
    }
}
