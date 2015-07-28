package com.baidu.gcrm.valuelistcache.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.code.model.Code;
import com.baidu.gcrm.valuelist.dao.IValuelistCacheDao;
import com.baidu.gcrm.valuelistcache.model.I18N;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Service("codeCacheServiceImpl")
public class CodeCacheServiceImpl extends AbstractValuelistCacheService<Code> {

	@Autowired
	private IValuelistCacheDao cacheDao;
	/**
	 * 赋值
	 */
	@Override
	protected Code mapToEntity(Map<String, String> map) {
		if(map == null){
			return new Code();
		}
		Code code = new Code();
		code.setId(Long.valueOf(map.get("id")));
		code.setCodeOrder(Long.valueOf(map.get("code_order")));
		code.setCodeType(map.get("code_type"));
		code.setCodeParent(map.get("code_parent"));
		
		if(StringUtils.isNotBlank(map.get("code_value"))){
			code.setCodeValue(map.get("code_value"));
		}
		
		if(StringUtils.isNotBlank(map.get("code_enum"))){
			code.setCodeEnum(map.get("code_enum"));
		}
		return code;
	}
    /**
     * 初始化表名
     */
	@Override
	protected void init() {
		this.tableName="g_code";
	}
	/**
	 * 获取所有
	 */
	public List<Code> getAllByLocale(LocaleConstants locale){
		List<Code> codeList = super.getAllByLocale(locale);
		if(CollectionUtils.isNotEmpty(codeList)){
			for(Code code : codeList){
				if(StringUtils.isNotBlank(code.getCodeType()) &&
						StringUtils.isNotBlank(code.getCodeValue()) &&
						locale != null &&
						StringUtils.isNotBlank(locale.name())){
					code.setI18nName(getI18N(code.getCodeType(),code.getCodeValue(),locale.name()).getKeyValue());
				}
			}
		}
		return codeList;
	}
	
	public Map<String,Code> getCodeValueMap(String codeType,String locale){
		Map<String,Code> map = new HashMap<String, Code>();
		List<Code> codeList = this.getAllByCodeTypeAndLoacle(codeType,locale);

		if(CollectionUtils.isNotEmpty(codeList)){
			for(Code code : codeList){
				map.put(code.getCodeValue(), code);
			}
		}
		return map;
	}
	
	public Map<String,Code> getCodeEnumMap(String codeType,String locale){
		Map<String,Code> map = new HashMap<String, Code>();
		List<Code> codeList = this.getAllByCodeTypeAndLoacle(codeType,locale);

		if(CollectionUtils.isNotEmpty(codeList)){
			for(Code code : codeList){
				map.put(code.getCodeEnum(), code);
			}
		}
		return map;
	}
	
	public List<Code> getAllByCodeTypeAndLoacle(String codeType,String locale){
		List<Code> results = new ArrayList<Code>();
		if(StringUtils.isBlank(codeType)){
			return results;
		}
		List<Map<String,String>> maps ;
        maps = cacheDao.findAll(tableName);
		
		for(Map<String,String> map : maps){
			if(StringUtils.isNotBlank(map.get("code_type")) && codeType.equals(map.get("code_type"))){
				Code code = mapToEntity(map);
				if(StringUtils.isNotBlank(code.getCodeType()) &&
						StringUtils.isNotBlank(code.getCodeValue()) &&
						StringUtils.isNotBlank(locale)){
					code.setI18nName(getI18N(code.getCodeType(),code.getCodeValue(),locale).getKeyValue());
				}
				results.add(code);
			}
		}
		return results;
	}
	
	public List<Code> getAllByCodeTypeAndLoacleAndCodeParent(String codeType,String locale,String codeParent){
		List<Code> results = new ArrayList<Code>();
		if(StringUtils.isBlank(codeType)){
			return results;
		}
		List<Map<String,String>> maps ;
        maps = cacheDao.findAll(tableName);
		
		for(Map<String,String> map : maps){
			if(StringUtils.isNotBlank(map.get("code_type")) && codeType.equals(map.get("code_type"))){
				Code code = mapToEntity(map);
				
				if(StringUtils.isNotBlank(code.getCodeType()) &&
						StringUtils.isNotBlank(code.getCodeValue()) &&
						StringUtils.isNotBlank(locale)){
					code.setI18nName(getI18N(code.getCodeType(),code.getCodeValue(),locale).getKeyValue());
				}
				
				if(StringUtils.isBlank(codeParent)){
					if(StringUtils.isBlank(code.getCodeParent())){
						results.add(code);
					}
				}else{
					if(codeParent.equals(code.getCodeParent())){
						results.add(code);
					}
				}
			}
		}
		return results;
	}
	
	/**
	* 功能描述：   通过代码类型、序号、locale类型获取相应的I18n
	* 创建人：yudajun    
	* 创建时间：2014-4-21 下午4:01:08   
	* 修改人：yudajun
	* 修改时间：2014-4-21 下午4:01:08   
	* 修改备注：   
	* 参数： @param codeType
	* 参数： @param codeNum
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public I18N getI18N(String codeType,String codeValue,String locale){
		I18N i18n = new I18N();
		if(StringUtils.isBlank(codeType) || codeValue == null){
			return i18n;
		}
		
		Map<String,String> params = new HashMap<String,String>();
		
		params.put("key_name",codeType+"."+codeValue);
		params.put("locale",locale);
		Map<String,String> values = cacheDao.get(i18nTableName, params);
		
		if(values==null)
			return i18n;
		
		i18n.setId(new Long(values.get("id")));
		i18n.setKeyName(values.get("key_name"));
		i18n.setKeyValue(values.get("key_value"));
		i18n.setLocale(values.get("locale"));
		
		return i18n;
	}
	/**
	* 功能描述：   代码类型获取code列表
	* 创建人：yudajun    
	* 创建时间：2014-4-21 下午4:02:07   
	* 修改人：yudajun
	* 修改时间：2014-4-21 下午4:02:07   
	* 修改备注：   
	* 参数： @param codeType
	* 参数： @return
	* @version
	 */
	public List<Code> getAllByCodeType(String codeType){
		List<Code> results = new ArrayList<Code>();
		List<Map<String,String>> maps = cacheDao.findAll(tableName);
		if (CollectionUtils.isEmpty(maps)) {
		    return results;
		}
		for(Map<String,String> map : maps){
		    if (codeType.equals(map.get("code_type"))) {
		        results.add(mapToEntity(map));
		    }
			
		}
		return results;
	}
    /**
    * 功能描述：   获取codeType对应的I18n列表
    * 创建人：yudajun    
    * 创建时间：2014-4-21 下午4:07:01   
    * 修改人：yudajun
    * 修改时间：2014-4-21 下午4:07:01   
    * 修改备注：   
    * 参数： @param codeType
    * 参数： @param locale
    * 参数： @return
    * @version
     */
	public List<I18N> getAllI18nByCodeType(String codeType,String locale){
		List<I18N> results = new ArrayList<I18N>();
		List<Code> codeList = getAllByCodeType(codeType);
		if(CollectionUtils.isNotEmpty(codeList)){
			for(Code code : codeList){
				I18N i18n = getI18N(codeType,code.getCodeValue(),locale);
				results.add(i18n);
			}
		}
		return results;
	}
	/**
	* 功能描述：   通过codeType、codeValue获取code
	* 创建人：yudajun    
	* 创建时间：2014-4-21 下午4:14:16   
	* 修改人：yudajun
	* 修改时间：2014-4-21 下午4:14:16   
	* 修改备注：   
	* 参数： @param codeType
	* 参数： @param codeValue
	* 参数： @return
	* @version
	 */
	public Code getCodeByTypeAndValueAndLocale(String codeType,String codeValue,String locale){
		if(StringUtils.isBlank(codeType) || StringUtils.isBlank(codeValue)){
			return null;
		}
		Code result = null;
		List<Code> codeList = getAllByCodeType(codeType);
		if(CollectionUtils.isNotEmpty(codeList)){
			for(Code code : codeList){
				if(codeValue.equals(code.getCodeValue())){
				    if (locale != null) {
				        code.setI18nName(getI18N(codeType,code.getCodeValue(),locale).getKeyValue());
				    }
					result = code;
				}
			}
		}
		return result;
	}
	
	/**
	* 功能描述：   通过codeType、codeValue、locale获取I18N
	* 创建人：yudajun    
	* 创建时间：2014-4-21 下午4:17:33   
	* 修改人：yudajun
	* 修改时间：2014-4-21 下午4:17:33   
	* 修改备注：   
	* 参数： @param codeType
	* 参数： @param codeValue
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public I18N getI18NByTypeAndValue(String codeType,String codeValue,String locale){
		Code code = getCodeByTypeAndValueAndLocale(codeType,codeValue,locale);
		I18N i18n = new I18N();
		if(code != null){
			getI18N(code.getCodeType(),code.getCodeValue(),locale);
		}
		return i18n;
	}
}
