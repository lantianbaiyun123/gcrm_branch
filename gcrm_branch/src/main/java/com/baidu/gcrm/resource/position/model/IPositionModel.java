package com.baidu.gcrm.resource.position.model;

import com.baidu.gcrm.resource.position.model.Position.PositionMaterialType;
import com.baidu.gcrm.resource.position.model.Position.RotationOrder;
import com.baidu.gcrm.resource.position.model.Position.RotationType;

public interface IPositionModel {
    
    RotationType getRotationType();
    
    RotationOrder getRotationOrder();
    
    PositionMaterialType getMaterialType();
    
    String getAreaRequired();
    
    String getSize();
    
    Integer getTextlinkLength();
    
    Integer getSalesAmount();
    
    Long getPv();
    
    Long getClick();
    
    Double getCptBenchmarkPrice();

}
