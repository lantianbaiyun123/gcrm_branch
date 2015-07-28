package com.baidu.gcrm.resource.position.service.impl;

import com.baidu.gcrm.resource.position.model.Position;

public class ChannelDecorator implements PositionInfoDecorator {
    
    protected BaseI18nInfoDecoratorVO i18nInfoDecoratorVO;
    
    public ChannelDecorator(){}
    
    public ChannelDecorator(BaseI18nInfoDecoratorVO i18nInfoDecoratorVO) {
        this.i18nInfoDecoratorVO = i18nInfoDecoratorVO;
    }
    
    @Override
    public StringBuilder buildIndexStr(Position position) {
        Long adPlatformId = position.getAdPlatformId();
        Long siteId = position.getSiteId();
        
        StringBuilder str = new StringBuilder();
        str.append(adPlatformId).append("-").append(siteId).append("-");
        
        return str;
    }
    
    @Override
    public StringBuilder buildI18nEnExtraInfo() {
        StringBuilder str = new StringBuilder();
        str.append(i18nInfoDecoratorVO.getAdPlatformI18nVO().getEnName())
            .append(InfoDecorator.SPLIT_FLAG)
            .append(i18nInfoDecoratorVO.getSiteI18nVO().getEnName());
        
        return str;
    }

    @Override
    public StringBuilder buildI18nCnExtraInfo() {
        StringBuilder str = new StringBuilder();
        str.append(i18nInfoDecoratorVO.getAdPlatformI18nVO().getCnName())
            .append(InfoDecorator.SPLIT_FLAG)
            .append(i18nInfoDecoratorVO.getSiteI18nVO().getCnName());
        
        return str;
    }

    @Override
    public BaseI18nInfoDecoratorVO getI18nInfoDecoratorVO() {
        return i18nInfoDecoratorVO;
    }

    @Override
    public void setI18nInfoDecoratorVO(
            BaseI18nInfoDecoratorVO i18nInfoDecoratorVO) {
        this.i18nInfoDecoratorVO = i18nInfoDecoratorVO;
        
    }

}
