package com.baidu.gcrm.resource.position.web.vo;

import com.baidu.gcrm.common.GcrmConfig;

public final class PositionImportColumnVO {
    
    private static int channelCnNameIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.channel.cnname"));
    private static int channelEnNameIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.channel.enname"));
    private static int areaCnNameIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.area.cnname"));
    private static int areaEnNameIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.area.enname"));
    private static int positionCnNameIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.cnname"));
    private static int positionEnNameIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.enname"));
    private static int rotationTypeIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.rotationtype"));
    private static int rotationOrderIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.rotationorder"));
    private static int materialTypeIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.rotation.materialtype"));
    private static int imageAreaIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.image.area"));
    private static int imageSizeIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.image.size"));
    private static int textlinkLengthIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.textlink.length"));
    private static int salesAmountIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.salesamount"));
    private static int pvIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.pv"));
    private static int clickIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.click"));
    private static int cptBenchmarkPriceIndex = Integer.parseInt(GcrmConfig.getConfigValueByKey("column.position.cpt.benchmarkprice"));
    
    public static int getChannelCnNameIndex() {
        return channelCnNameIndex;
    }
    public static int getChannelEnNameIndex() {
        return channelEnNameIndex;
    }
    public static int getAreaCnNameIndex() {
        return areaCnNameIndex;
    }
    public static int getAreaEnNameIndex() {
        return areaEnNameIndex;
    }
    public static int getPositionCnNameIndex() {
        return positionCnNameIndex;
    }
    public static int getPositionEnNameIndex() {
        return positionEnNameIndex;
    }
    public static int getRotationTypeIndex() {
        return rotationTypeIndex;
    }
    public static int getMaterialTypeIndex() {
        return materialTypeIndex;
    }
    public static int getImageAreaIndex() {
        return imageAreaIndex;
    }
    public static int getImageSizeIndex() {
        return imageSizeIndex;
    }
    public static int getTextlinkLengthIndex() {
        return textlinkLengthIndex;
    }
    public static int getSalesAmountIndex() {
        return salesAmountIndex;
    }
    public static int getPvIndex() {
        return pvIndex;
    }
    public static int getClickIndex() {
        return clickIndex;
    }
    public static int getCptBenchmarkPriceIndex() {
        return cptBenchmarkPriceIndex;
    }
    public static int getRotationOrderIndex() {
        return rotationOrderIndex;
    }
}
