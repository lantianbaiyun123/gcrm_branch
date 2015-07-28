package com.baidu.gcrm.resource.position.service.impl;

import com.baidu.gcrm.resource.position.model.Position;

public class PositionDecorator extends AreaDecorator{
    
    public PositionDecorator(){}
    
    public PositionDecorator(BaseI18nInfoDecoratorVO i18nInfoDecoratorVO) {
        this.i18nInfoDecoratorVO = i18nInfoDecoratorVO;
    }
    
    @Override
    public StringBuilder buildIndexStr(Position position) {
        Long areaId = position.getAreaId();
        StringBuilder areaStr = super.buildIndexStr(position);
        areaStr.append(areaId).append("-");
        return areaStr;
    }
    
    @Override
    public StringBuilder buildI18nEnExtraInfo() {
        StringBuilder str = super.buildI18nEnExtraInfo();
        str.append(InfoDecorator.SPLIT_FLAG).append(i18nInfoDecoratorVO.getAreaI18nVO().getEnName());
        return str;
    }

    @Override
    public StringBuilder buildI18nCnExtraInfo() {
        StringBuilder str = super.buildI18nCnExtraInfo();
        str.append(InfoDecorator.SPLIT_FLAG).append(i18nInfoDecoratorVO.getAreaI18nVO().getCnName());
        
        return str;
    }

}
