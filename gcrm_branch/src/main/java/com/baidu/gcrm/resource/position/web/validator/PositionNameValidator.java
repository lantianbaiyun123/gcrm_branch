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
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.position.service.impl.InfoDecorator;
import com.baidu.gcrm.resource.position.web.vo.PositionI18nVO;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;

public class PositionNameValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(PositionVO.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target==null) {
			return;
		}
		PositionVO positionVO = (PositionVO)target;
		PositionI18nVO currPositionI18nVO = positionVO.getI18nData();
		if (currPositionI18nVO == null 
                || PatternUtil.isBlank(currPositionI18nVO.getEnName())
                || PatternUtil.isBlank(currPositionI18nVO.getCnName())) {
            errors.reject("resource.position.name.null");
            return;
        } else if (currPositionI18nVO.getEnName().indexOf(InfoDecorator.SPLIT_FLAG) != -1){
            errors.reject("resource.position.name.error");
            return;
        }
		Long positionId = positionVO.getId();
		IPositionService positionService = ServiceBeanFactory.getPositionService();
		Position currPosition = positionService.findById(positionId);
		I18nKVService i18nService = ServiceBeanFactory.getI18nService();
        List<I18nKV> allI18nInfo = i18nService.findByIndexStr(positionId, currPosition.getIndexStr());
        if (CollectionUtils.isEmpty(allI18nInfo)) {
            return;
        }
        
        Map<String,String> existsNameMap = getExistsName(allI18nInfo);
        String enKey = getDetectKey(currPositionI18nVO.getEnName(), LocaleConstants.en_US);
        String cnKey = getDetectKey(currPositionI18nVO.getCnName(), LocaleConstants.zh_CN);
        if (existsNameMap.get(enKey) != null || existsNameMap.get(cnKey) != null ) {
            errors.reject("resource.position.name.duplicate", "resource.position.name.duplicate");
        }
		
		
	}
	
	private Map<String,String> getExistsName(List<I18nKV> allI18nInfo) {
	    Map<String,String> positionMap = new HashMap<String,String> ();
	    for (I18nKV temI18nKV : allI18nInfo) {
	        String key = getDetectKey(temI18nKV.getValue(), temI18nKV.getLocale());
	        positionMap.put(key, "");
	    }
	    return positionMap;
	}
	private String getDetectKey(String name, LocaleConstants locale) {
	    return new StringBuilder().append(locale.ordinal())
                .append(InfoDecorator.SPLIT_FLAG)
                .append(name).toString();
	}
	

}
