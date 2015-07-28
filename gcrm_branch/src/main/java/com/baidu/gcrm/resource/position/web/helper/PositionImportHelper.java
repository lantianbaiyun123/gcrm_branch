package com.baidu.gcrm.resource.position.web.helper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionMaterialType;
import com.baidu.gcrm.resource.position.model.Position.RotationOrder;
import com.baidu.gcrm.resource.position.model.Position.RotationType;
import com.baidu.gcrm.resource.position.service.impl.InfoDecorator;
import com.baidu.gcrm.resource.position.web.validator.PositionValidator;
import com.baidu.gcrm.resource.position.web.vo.ErrorCellVO;
import com.baidu.gcrm.resource.position.web.vo.PositionI18nVO;
import com.baidu.gcrm.resource.position.web.vo.PositionImportColumnVO;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;

public class PositionImportHelper {

    public static void convertToPositionBean(Position savePosition, Map<String, PositionVO> allImportDataMap,
            Map<String, String> dbPositionNameMap) {
        if (allImportDataMap.size() < 1) {
            return;
        }
        List<PositionVO> channelImportData = new LinkedList<PositionVO>();
        Map<String, String> channelDetectMap = new HashMap<String, String>();
        Map<String, String> areaDetectMap = new HashMap<String, String>();
        Map<String, List<PositionVO>> subAreaDataMap = new HashMap<String, List<PositionVO>>();
        Map<String, List<PositionVO>> subPositionDataMap = new HashMap<String, List<PositionVO>>();

        for (Map.Entry<String, PositionVO> positionVOEntry : allImportDataMap.entrySet()) {
            PositionVO temPositionVO = positionVOEntry.getValue();
            String channelEnName = temPositionVO.getChannelEnName();
            String channelCnName = temPositionVO.getChannelCnName();
            String areaEnName = temPositionVO.getAreaEnName();
            String areaCnName = temPositionVO.getAreaCnName();
            String channelEnKey = PositionValidator.generateDetectKey(LocaleConstants.en_US, channelEnName, null);
            if (channelDetectMap.get(channelEnKey) == null) {
                channelDetectMap.put(channelEnKey, "");
                channelImportData.add(buildPositionI18nVO(dbPositionNameMap.get(channelEnKey), channelEnName,
                        channelCnName));

            }
            String areaEnKey = PositionValidator.generateDetectKey(null, areaEnName, channelEnKey);

            if (areaDetectMap.get(areaEnKey) == null) {
                areaDetectMap.put(areaEnKey, "");
                List<PositionVO> areaDataList = subAreaDataMap.get(channelEnKey);
                if (areaDataList == null) {
                    areaDataList = new LinkedList<PositionVO>();
                    subAreaDataMap.put(channelEnKey, areaDataList);
                }
                areaDataList.add(buildPositionI18nVO(dbPositionNameMap.get(areaEnKey), areaEnName, areaCnName));
            }

            List<PositionVO> positionDataList = subPositionDataMap.get(areaEnKey);
            if (positionDataList == null) {
                positionDataList = new LinkedList<PositionVO>();
                subPositionDataMap.put(areaEnKey, positionDataList);
            }
            positionDataList.add(temPositionVO);
        }

        // process position obj
        processTreeStructure(channelImportData, subAreaDataMap, subPositionDataMap);
        savePosition.setPosotionData(channelImportData);

    }

    private static void processTreeStructure(List<PositionVO> channelImportData,
            Map<String, List<PositionVO>> subAreaDataMap, Map<String, List<PositionVO>> subPositionDataMap) {

        if (channelImportData.size() < 1) {
            return;
        }
        for (PositionVO temChannelPositionVO : channelImportData) {
            PositionI18nVO channelPositionI18nVO = temChannelPositionVO.getI18nData();
            String channelEnKey =
                    PositionValidator.generateDetectKey(LocaleConstants.en_US, channelPositionI18nVO.getEnName(), null);
            List<PositionVO> subAreaList = subAreaDataMap.get(channelEnKey);
            if (!CollectionUtils.isEmpty(subAreaList)) {
                temChannelPositionVO.setChildren(subAreaList);
                for (PositionVO temAreaPositionVO : subAreaList) {
                    PositionI18nVO areaPositionI18nVO = temAreaPositionVO.getI18nData();
                    String areaEnKey =
                            PositionValidator.generateDetectKey(null, areaPositionI18nVO.getEnName(), channelEnKey);
                    List<PositionVO> subPositionList = subPositionDataMap.get(areaEnKey);
                    if (!CollectionUtils.isEmpty(subPositionList)) {
                        temAreaPositionVO.setChildren(subPositionList);
                    }
                }
            }
        }
    }

    private static PositionVO buildPositionI18nVO(String id, String enName, String cnName) {
        PositionVO temPositionVO = new PositionVO();
        PositionI18nVO i18nVOData = new PositionI18nVO(enName, cnName);
        temPositionVO.setI18nData(i18nVOData);
        if (id != null) {
            temPositionVO.setId(Long.valueOf(id));
        }

        return temPositionVO;
    }

    private static void buildError(Map<String, List<ErrorCellVO>> errorMap, String code, int row, int column,
            ErrorCellVO otherErrorVO) {
        ErrorCellVO errorVO = new ErrorCellVO(row, column);
        errorVO.setOtherCell(otherErrorVO);
        processErrorMap(errorMap, code, errorVO);
    }

    private static Cell processCellNull(Row row, int rowNum, int columnIndex, String code,
            Map<String, List<ErrorCellVO>> errorMap) {
        Cell cell = getCell(row, columnIndex);
        if (cell == null || PatternUtil.isBlank(cell.getStringCellValue())) {
            buildError(errorMap, code, rowNum, columnIndex + 1, null);
        }
        return cell;
    }

    private static Cell getCell(Row row, int columnIndex) {
        Cell currCell = row.getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
        if (currCell != null) {
            currCell.setCellType(Cell.CELL_TYPE_STRING);
        }
        return currCell;
    }

    public static void processRow(Row row, Map<String, List<ErrorCellVO>> errorMap, Map<String, PositionVO> allDataMap,
            Map<String, String> allFileDataCheckMap, Map<String, String> dbPositionNameMap) {

        PositionVO newPositionVO = new PositionVO();

        int rowNum = row.getRowNum() + 1;
        int channelCnIndex = PositionImportColumnVO.getChannelCnNameIndex();
        int channelEnIndex = PositionImportColumnVO.getChannelEnNameIndex();
        Cell channelCnName = processCellNull(row, rowNum, channelCnIndex, "resource.position.import.name", errorMap);
        Cell channelEnName = processCellNull(row, rowNum, channelEnIndex, "resource.position.import.name", errorMap);

        int areaCnIndex = PositionImportColumnVO.getAreaCnNameIndex();
        int areaEnIndex = PositionImportColumnVO.getAreaEnNameIndex();
        Cell areaCnName = processCellNull(row, rowNum, areaCnIndex, "resource.position.import.name", errorMap);
        Cell areaEnName = processCellNull(row, rowNum, areaEnIndex, "resource.position.import.name", errorMap);

        int positionCnIndex = PositionImportColumnVO.getPositionCnNameIndex();
        int positionEnIndex = PositionImportColumnVO.getPositionEnNameIndex();
        Cell positionCnName = processCellNull(row, rowNum, positionCnIndex, "resource.position.import.name", errorMap);
        Cell positionEnName = processCellNull(row, rowNum, positionEnIndex, "resource.position.import.name", errorMap);

        processOtherProperty(row, errorMap, newPositionVO, rowNum);
        if (errorMap.size() > 0) {
            return;
        }

        String channelEnNameStr = channelEnName.getStringCellValue();
        String areaEnNameStr = areaEnName.getStringCellValue();
        String positionEnNameStr = positionEnName.getStringCellValue();
        String enKey =
                new StringBuilder().append(LocaleConstants.en_US.ordinal()).append(InfoDecorator.SPLIT_FLAG)
                        .append(channelEnNameStr).append(InfoDecorator.SPLIT_FLAG).append(areaEnNameStr)
                        .append(InfoDecorator.SPLIT_FLAG).append(positionEnNameStr).toString();
        checkPositionName(allFileDataCheckMap, dbPositionNameMap, enKey, rowNum, (positionEnIndex + 1), errorMap);

        String channelCnNameStr = channelCnName.getStringCellValue();
        String areaCnNameStr = areaCnName.getStringCellValue();
        String positionCnNameStr = positionCnName.getStringCellValue();
        String cnKey =
                new StringBuilder().append(LocaleConstants.zh_CN.ordinal()).append(InfoDecorator.SPLIT_FLAG)
                        .append(channelCnNameStr).append(InfoDecorator.SPLIT_FLAG).append(areaCnNameStr)
                        .append(InfoDecorator.SPLIT_FLAG).append(positionCnNameStr).toString();
        checkPositionName(allFileDataCheckMap, dbPositionNameMap, cnKey, rowNum, (positionCnIndex + 1), errorMap);

        if (errorMap.size() > 0) {
            return;
        }

        newPositionVO.setChannelEnName(channelEnNameStr);
        newPositionVO.setChannelCnName(channelCnName.getStringCellValue());
        newPositionVO.setAreaEnName(areaEnNameStr);
        newPositionVO.setAreaCnName(areaCnName.getStringCellValue());
        PositionI18nVO i18nNameVO =
                new PositionI18nVO(positionEnName.getStringCellValue(), positionCnName.getStringCellValue());
        newPositionVO.setI18nData(i18nNameVO);

        allDataMap.put(enKey, newPositionVO);
    }

    private static void processErrorMap(Map<String, List<ErrorCellVO>> errorMap, String code, ErrorCellVO errorVO) {

        List<ErrorCellVO> existsErrors = errorMap.get(code);
        if (existsErrors == null) {
            existsErrors = new LinkedList<ErrorCellVO>();
            errorMap.put(code, existsErrors);
        }

        existsErrors.add(errorVO);

    }

    private static void processOtherProperty(Row row, Map<String, List<ErrorCellVO>> errorMap,
            PositionVO newPositionVO, int rowNum) {

        int rotationTypeIndex = PositionImportColumnVO.getRotationTypeIndex();
        Cell rotationType = getCell(row, rotationTypeIndex);
        RotationType currRotationType = null;
        if (rotationType != null) {
            String rotationTypeStr = rotationType.getStringCellValue();
            currRotationType = RotationType.cnValueOf(rotationTypeStr);
            if (currRotationType == null) {
                buildError(errorMap, "resource.position.import.rotationType", rowNum, rotationTypeIndex + 1, null);
            } else {
                newPositionVO.setRotationType(currRotationType.ordinal());
            }
        }

        int rotationOrderIndex = PositionImportColumnVO.getRotationOrderIndex();
        Cell rotationOrder = getCell(row, rotationOrderIndex);
        RotationOrder currRotationOrder = null;
        if (rotationOrder != null && RotationType.yes == currRotationType) {
            String rotationOrderStr = rotationOrder.getStringCellValue();
            try {
                currRotationOrder = RotationOrder.cnValueOf(rotationOrderStr);
                if (currRotationOrder == null) {
                    buildError(errorMap, "resource.position.import.rotationOrder", rowNum, rotationOrderIndex + 1, null);
                }
            } catch (Exception e) {
                buildError(errorMap, "resource.position.import.rotationOrder", rowNum, rotationOrderIndex + 1, null);
            }
            newPositionVO.setRotationOrder(currRotationOrder);
        }

        int salesAmountIndex = PositionImportColumnVO.getSalesAmountIndex();
        Cell salesAmount = getCell(row, salesAmountIndex);
        if (salesAmount != null) {
            try {
                Integer currSalesAmount = Integer.valueOf(salesAmount.getStringCellValue());
                if (RotationType.no == currRotationType && currSalesAmount.intValue() > 1) {
                    buildError(errorMap, "resource.position.import.salesAmount", rowNum, salesAmountIndex + 1, null);
                }
                newPositionVO.setSalesAmount(currSalesAmount);
            } catch (NumberFormatException e) {
                buildError(errorMap, "resource.position.import.salesAmount", rowNum, salesAmountIndex + 1, null);
            }
        }

        int materialTypeIndex = PositionImportColumnVO.getMaterialTypeIndex();
        Cell materialTypeCell = getCell(row, materialTypeIndex);
        if (materialTypeCell != null) {
            String materialTypeStr = materialTypeCell.getStringCellValue();
            PositionMaterialType materialType = PositionMaterialType.cnValueOf(materialTypeStr);
            if (materialType == null) {
                buildError(errorMap, "resource.position.import.materialType", rowNum, materialTypeIndex + 1, null);
            } else {
                newPositionVO.setMaterialType(materialType.ordinal());
            }
        }

        int imageAreaIndex = PositionImportColumnVO.getImageAreaIndex();
        Cell imageArea = getCell(row, imageAreaIndex);
        if (imageArea != null) {
            newPositionVO.setAreaRequired(imageArea.getStringCellValue());
            // buildError(errorMap, "resource.position.import.image.dimension", rowNum, imageAreaIndex+1, null);
        }

        int imageSizeIndex = PositionImportColumnVO.getImageSizeIndex();
        Cell imageSize = getCell(row, imageSizeIndex);
        if (imageSize != null) {
            newPositionVO.setSize(imageSize.getStringCellValue());
            // buildError(errorMap, "resource.position.import.image.size", rowNum, imageSizeIndex+1, null);
        }

        int textlinkLengthIndex = PositionImportColumnVO.getTextlinkLengthIndex();
        Cell textlinkLength = getCell(row, textlinkLengthIndex);
        if (textlinkLength != null) {
            try {
                newPositionVO.setTextlinkLength(Integer.valueOf(textlinkLength.getStringCellValue()));
            } catch (NumberFormatException e) {
                buildError(errorMap, "resource.position.import.textlink.length", rowNum, textlinkLengthIndex + 1, null);
            }
        }

        int pvIndex = PositionImportColumnVO.getPvIndex();
        Cell pv = getCell(row, pvIndex);
        if (pv != null) {
            try {
                newPositionVO.setPv(Long.valueOf(pv.getStringCellValue()));
            } catch (NumberFormatException e) {
                buildError(errorMap, "resource.position.import.throughput", rowNum, pvIndex + 1, null);
            }
        }
        int clickIndex = PositionImportColumnVO.getClickIndex();
        Cell click = getCell(row, clickIndex);
        if (click != null) {
            try {
                newPositionVO.setClick(Long.valueOf(click.getStringCellValue()));
            } catch (NumberFormatException e) {
                buildError(errorMap, "resource.position.import.throughput", rowNum, clickIndex + 1, null);
            }
        }

        int cptBenchmarkPriceIndex = PositionImportColumnVO.getCptBenchmarkPriceIndex();
        Cell cptBenchmarkPrice = getCell(row, cptBenchmarkPriceIndex);
        if (cptBenchmarkPrice != null) {
            try {
                newPositionVO.setCptBenchmarkPrice(Double.valueOf(cptBenchmarkPrice.getStringCellValue()));
            } catch (NumberFormatException e) {
                buildError(errorMap, "resource.position.import.cpt.benchmarkPrice", rowNum, cptBenchmarkPriceIndex + 1,
                        null);
            }
        }
    }

    private static void checkPositionName(Map<String, String> fileNameMap, Map<String, String> dbNameMap, String key,
            int rowNum, int columnNum, Map<String, List<ErrorCellVO>> errorMap) {
        String existsPosition = fileNameMap.get(key);
        if (existsPosition == null && dbNameMap.get(key) == null) {
            fileNameMap.put(key, new StringBuilder().append(rowNum).append("-").append(columnNum).toString());
        } else {
            ErrorCellVO otherErrorVO = null;
            if (existsPosition != null) {
                String[] existsPositionIndex = existsPosition.split("-");
                otherErrorVO =
                        new ErrorCellVO(Integer.parseInt(existsPositionIndex[0]),
                                Integer.parseInt(existsPositionIndex[1]));
            }

            buildError(errorMap, "resource.position.import.name.duplicate", rowNum, columnNum, otherErrorVO);
        }
    }

}
