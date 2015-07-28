package com.baidu.gcrm.ad.statistic.web.vo;

import java.util.Date;
import java.util.List;

public class PublishClickListVO extends BasePageVO{
    
    private String adContentNumber;
    
    private Date from;
    
    private Date to;
    
    private List<PublishClickStatDataVO> adClickDatas;

    public List<PublishClickStatDataVO> getAdClickDatas() {
        return adClickDatas;
    }

    public void setAdClickDatas(List<PublishClickStatDataVO> adClickDatas) {
        this.adClickDatas = adClickDatas;
    }

    public String getAdContentNumber() {
        return adContentNumber;
    }

    public void setAdContentNumber(String adContentNumber) {
        this.adContentNumber = adContentNumber;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
    
}
