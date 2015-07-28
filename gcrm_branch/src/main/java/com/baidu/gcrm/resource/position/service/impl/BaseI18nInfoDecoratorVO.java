package com.baidu.gcrm.resource.position.service.impl;

import com.baidu.gcrm.resource.position.web.vo.PositionI18nVO;

public class BaseI18nInfoDecoratorVO {
    
    private PositionI18nVO adPlatformI18nVO;
    
    private PositionI18nVO siteI18nVO;
    
    private PositionI18nVO channelI18nVO;
    
    private PositionI18nVO areaI18nVO;
    
    private PositionI18nVO positionI18nVO;

    public PositionI18nVO getPositionI18nVO() {
        return positionI18nVO;
    }

    public void setPositionI18nVO(PositionI18nVO positionI18nVO) {
        this.positionI18nVO = positionI18nVO;
    }

    public PositionI18nVO getChannelI18nVO() {
        return channelI18nVO;
    }


    public void setChannelI18nVO(PositionI18nVO channelI18nVO) {
        this.channelI18nVO = channelI18nVO;
    }


    public PositionI18nVO getAreaI18nVO() {
        return areaI18nVO;
    }


    public void setAreaI18nVO(PositionI18nVO areaI18nVO) {
        this.areaI18nVO = areaI18nVO;
    }


    public PositionI18nVO getAdPlatformI18nVO() {
        return adPlatformI18nVO;
    }


    public void setAdPlatformI18nVO(PositionI18nVO adPlatformI18nVO) {
        this.adPlatformI18nVO = adPlatformI18nVO;
    }


    public PositionI18nVO getSiteI18nVO() {
        return siteI18nVO;
    }


    public void setSiteI18nVO(PositionI18nVO siteI18nVO) {
        this.siteI18nVO = siteI18nVO;
    }

}
