package com.baidu.gcrm.resource.position.service.impl;

import com.baidu.gcrm.resource.position.model.Position;

public class AreaDecorator extends ChannelDecorator{
    
    public AreaDecorator(){}
    
    public AreaDecorator(BaseI18nInfoDecoratorVO i18nInfoDecoratorVO) {
        this.i18nInfoDecoratorVO = i18nInfoDecoratorVO;
    }

    @Override
    public StringBuilder buildIndexStr(Position position) {
        StringBuilder channelStr = super.buildIndexStr(position);
        Long channelId = position.getChannelId();
        channelStr.append(channelId).append("-");
        return channelStr;
    }
    
    @Override
    public StringBuilder buildI18nEnExtraInfo() {
        StringBuilder str = super.buildI18nEnExtraInfo();
        str.append(SPLIT_FLAG).append(i18nInfoDecoratorVO.getChannelI18nVO().getEnName());
        return str;
    }

    @Override
    public StringBuilder buildI18nCnExtraInfo() {
        StringBuilder str = super.buildI18nCnExtraInfo();
        str.append(SPLIT_FLAG).append(i18nInfoDecoratorVO.getChannelI18nVO().getCnName());
        return str;
    }

}
