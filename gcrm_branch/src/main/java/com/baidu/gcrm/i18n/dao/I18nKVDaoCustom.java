package com.baidu.gcrm.i18n.dao;

import java.util.List;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.position.model.Position.PositionType;

public interface I18nKVDaoCustom {
    
    List<I18nKV> findExistsValue(String keyPrefix, Long id, List<LocaleVO> values);
    
    List<I18nKV> findByAdPlatformIdAndSiteId(Long adPlatformId, Long siteId);
    
    List<I18nKV> findByIndexStr(Long excludePositionId, String indexStr);
    
    void updateSubPositionExtraName(String indexStr,PositionType type, LocaleConstants locale);
}
