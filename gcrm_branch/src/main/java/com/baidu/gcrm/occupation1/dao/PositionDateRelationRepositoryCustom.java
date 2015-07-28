package com.baidu.gcrm.occupation1.dao;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.google.common.collect.Multimap;

public interface PositionDateRelationRepositoryCustom {
    Map<Long, Long> getCountGroupByPositionOccId(Collection<Long> positionOccIds);
    
    /**
     * 返回map，key是positionOccId，value是与该positionOccId关联的adContentId的count
     */
    Map<Long, Long> getCountGroupByAdContentIdInAndDateFrom(Collection<Long> adContentIds, Date fromDate);
    
    /**
     * 获取指定广告内容对应的投放日期
     * @param adContentIds
     * @param fromDate
     * @return 返回map，key是广告内容id，value是广告内容对应的投放日期
     */
    Multimap<Long, String> getContentIdDateMap(Collection<Long> adContentIds, Date fromDate);
    
    int replaceAdContentId(Long oldContentId, Long replaceContentId);
}
