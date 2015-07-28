package com.baidu.gcrm.occupation.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.occupation.model.AdvertisePositionDateRelation;

public interface IADPositionDateRelationService {
	int buildRelationshipAndOccypyStock(Long adContentId, Long positionId, Set<Date> dates);
	
	int removeRelationships(Long adContentId);

	int removeRelationships(List<Long> adContentIds);
	
	List<AdvertisePositionDateRelation> findByOccupations(Collection<Long> occupationIds);
	
	List<AdvertisePositionDateRelation> findByAdContentIdAndOccIds(Long adContentId, Collection<Long> occupationIds);
	
	int deleteByAdContentIdAndPositionOccId(Long adContentId, List<Long> positionOccIds);
	
	void save(List<AdvertisePositionDateRelation> entitys);
	
	Long findCountsByAdContentIdAndPositionOccId(Long adContentId, List<Long> positionOccIds);
	
    List<Long> findAdContentIdByOccupation(Collection<Long> occupationIds);
    
    List<AdvertisePositionDateRelation> findRelationsWithScheduleByOccupations(Collection<Long> occupationIds);
    
    int buildRelationByAdContentId(Long adContentId);
    
    int buildRelationByAdContent(AdSolutionContent content);
    
    Map<Long, Long> getCountGroupByPositionDateId(Collection<Long> positionDateIds);
}
