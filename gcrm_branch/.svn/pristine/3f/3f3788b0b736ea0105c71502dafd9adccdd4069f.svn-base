package com.baidu.gcrm.occupation1.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.occupation.model.AdvertisePositionDateRelation;
import com.google.common.collect.Multimap;

public interface IPositionDateRelationService {
    
    /**
     * 
     * @param adContentId
     * @param positionId
     * @param dates
     * @return 全部被占用完的positionDate
     * 
     * @see IAdvertiseSolutionService#notifyInfluencedAdSolution
     */
    void buildRelationshipAndOccypyStock(AdSolutionContent content, Set<Date> dates);
	
    void buildRelationByAdContentId(Long adContentId);
    
    void buildRelationByAdContent(AdSolutionContent content);
	
	void removeRelationshipsAndReleaseStock(Long adContentId);
	
	void removeRelationshipsAndReleaseStock(List<Long> adContentIds);
	
	void removeRelationshipsAndReleaseStock(List<AdvertisePositionDateRelation> relations, Long adContentId);

	void removeRelationsAndReleaseStocksFromDate(Long adContentId, Date from);
	
	void removeRelationsAndReleaseStocksByDateIn(Long adContentId, Collection<Date> dates);
	
	int replaceAdContentIdInRelationship(Long oldContentId, Long replaceContentId);
	
	Map<Long, Long> getCountGroupByPositionDateId(Collection<Long> positionDateIds);
	
	/**
     * 获取指定广告内容对应的投放日期
     * @param adContentIds
     * @param fromDate
     * @return 返回map，key是广告内容id，value是广告内容对应的投放日期
     */
	Multimap<Long, String> getContentIdDateMap(Collection<Long> adContentIds, Date fromDate);
	
	List<Date> getDatesByContentIdAndDateBetween(Long adContentId, Date fromDate, Date toDate);
	
    List<AdvertisePositionDateRelation> findByAdContentIdAndDatesIn(Long adContentId, Collection<Date> dates);
}
