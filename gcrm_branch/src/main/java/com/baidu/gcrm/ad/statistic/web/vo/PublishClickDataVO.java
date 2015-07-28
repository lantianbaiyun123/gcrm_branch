package com.baidu.gcrm.ad.statistic.web.vo;

import java.util.List;

public class PublishClickDataVO {
    
    private Long time;
    
    List<PublishClickStatDataVO> adStats;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<PublishClickStatDataVO> getAdStats() {
        return adStats;
    }

    public void setAdStats(List<PublishClickStatDataVO> adStats) {
        this.adStats = adStats;
    }
    
    
}
