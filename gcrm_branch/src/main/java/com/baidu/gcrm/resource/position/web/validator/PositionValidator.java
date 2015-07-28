package com.baidu.gcrm.resource.position.web.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.service.impl.InfoDecorator;
import com.baidu.gcrm.resource.position.web.vo.PositionI18nVO;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;

public class PositionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Position.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target==null) {
			return;
		}
		
		Position position = (Position)target;
		//key=>name.lang,value=>id
		List<PositionVO> channelPositionInfoVO =  position.getPosotionData();
		if (CollectionUtils.isEmpty(channelPositionInfoVO)) {
		    return;
		}
		Long siteId = position.getSiteId();
        Long adPlatformId = position.getAdPlatformId();
		Map<String,String> existsNameMap = getExistsPositionMap(adPlatformId, siteId);
		if (existsNameMap.size() < 1) {
		    return;
		}
		
	    for (PositionVO temPositionInfoVO : channelPositionInfoVO) {
	        PositionI18nVO currPositionI18nVO = temPositionInfoVO.getI18nData();
	        if (currPositionI18nVO == null 
                || PatternUtil.isBlank(currPositionI18nVO.getEnName())
                || PatternUtil.isBlank(currPositionI18nVO.getCnName())) {
	            errors.reject("resource.position.name.null");
	            continue;
	        } else if (currPositionI18nVO.getEnName().indexOf(InfoDecorator.SPLIT_FLAG) != -1){
	            errors.reject("resource.position.name.error");
                continue;
	            
	        }
	        
	        String enKey = generateDetectKey(LocaleConstants.en_US, currPositionI18nVO.getEnName(), null);
	        String cnKey = generateDetectKey(LocaleConstants.zh_CN, currPositionI18nVO.getCnName(), null);
	        
	        peocessError(existsNameMap, enKey, cnKey, temPositionInfoVO, errors);
	        
	        //process area
	        List<PositionVO> areaVOList = temPositionInfoVO.getChildren();
	        if (CollectionUtils.isEmpty(areaVOList)) {
	            continue;
	        }
	        for (PositionVO temAreaPositionVO : areaVOList) {
	            PositionI18nVO areaI18nData = temAreaPositionVO.getI18nData();
	            if (areaI18nData == null) {
	                continue;
	            }
	            String enAreaKey = generateDetectKey(null, areaI18nData.getEnName(), enKey);
                String cnAreaKey = generateDetectKey(null, areaI18nData.getCnName(), cnKey);
                
                peocessError(existsNameMap, enAreaKey, cnAreaKey, temAreaPositionVO, errors);
                
                List<PositionVO> positionVOList = temAreaPositionVO.getChildren();
                if (CollectionUtils.isEmpty(positionVOList)) {
                    continue;
                }
                
                for (PositionVO positionPositionVO : positionVOList) {
                    PositionI18nVO positionI18nData = positionPositionVO.getI18nData();
                    if (positionI18nData == null) {
                        continue;
                    }
                    String enPositionKey = generateDetectKey(null, positionI18nData.getEnName(), enAreaKey);
                    String cnPositionKey = generateDetectKey(null, positionI18nData.getCnName(), cnAreaKey);
                    
                    peocessError(existsNameMap, enPositionKey, cnPositionKey, positionPositionVO, errors);
                }
	        }
	        
	    }
		
	}
	
	private void peocessError(Map<String,String> existsNameMap,
	        String enKey, String cnKey, PositionVO temPositionInfoVO, Errors errors) {
	    String idValue = getIdValue(temPositionInfoVO);
	    String existsId = existsNameMap.get(enKey);
        if (existsId != null && !existsId.equals(idValue)) {
            errors.reject("resource.position.name.duplicate", enKey);
        }
        
        String existsCnId = existsNameMap.get(cnKey);
        if (existsCnId != null && !existsCnId.equals(idValue)) {
            errors.reject("resource.position.name.duplicate", cnKey);
        }
	}
	
	private String getIdValue(PositionVO positionVO) {
	    String idValue = null;
        Long id = positionVO.getId();
        if (id != null) {
            idValue = id.toString();
        } else {
            idValue = "";
        }
        
        return idValue;
	}
	
	//key=>extra_value,value=>id
	public static Map<String,String> getExistsPositionMap(Long adPlatformId, Long siteId) {
	    
	    Map<String,String> existsPositionNameMap = new HashMap<String,String> ();
	    
        I18nKVService i18nService = ServiceBeanFactory.getI18nService();
        List<I18nKV> allI18nInfo = i18nService.findByAdPlatformIdAndSiteId(adPlatformId, siteId);
        if (CollectionUtils.isEmpty(allI18nInfo)) {
            return existsPositionNameMap;
        }
        
        for (I18nKV temI18nKV : allI18nInfo) {
            String extraValue = temI18nKV.getExtraValue();
            if (PatternUtil.isBlank(extraValue)) {
                continue;
            }
            String[] positionNameArray = extraValue.split(InfoDecorator.SPLIT_FLAG);
            if (positionNameArray == null || positionNameArray.length < 2) {
                continue;
            }
            StringBuilder nameValue = new StringBuilder();
            for (int i = 2;i < positionNameArray.length;i++) {
                if (nameValue.length() > 0) {
                    nameValue.append(InfoDecorator.SPLIT_FLAG);
                }
                nameValue.append(positionNameArray[i]);
            }
            String existsKey = generateDetectKey(temI18nKV.getLocale(), nameValue.toString(), null);
            
            String i18nKey = temI18nKV.getKey();
            existsPositionNameMap.put(existsKey, i18nKey.substring(i18nKey.lastIndexOf(".")+1));
        }
        
        return existsPositionNameMap;
	}
	
	public static String generateDetectKey(LocaleConstants locale,String nameStr, String prefixStr) {
	    if (PatternUtil.isBlank(prefixStr)) {
	        return new StringBuilder()
                    .append(locale.ordinal())
                    .append(InfoDecorator.SPLIT_FLAG)
                    .append(nameStr).toString();
                    
	    } else {
	        return new StringBuilder()
            .append(prefixStr)
            .append(InfoDecorator.SPLIT_FLAG)
            .append(nameStr).toString();
            
	    }
	    
	}
	

}
