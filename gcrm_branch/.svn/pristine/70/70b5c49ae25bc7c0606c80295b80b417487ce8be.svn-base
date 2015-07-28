package com.baidu.gcrm.ad.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.web.utils.AdvertiseSolutionCondition;
import com.baidu.gcrm.ad.web.vo.AdvertiseSolutionListView;
import com.baidu.gcrm.common.page.Page;

public interface AdvertiseSolutionRepositoryCustom {
	/**
	* 功能描述：   广告方案列表查询
	* 创建人：yudajun    
	* 创建时间：2014-3-19 下午3:51:44   
	* 修改人：yudajun
	* 修改时间：2014-3-19 下午3:51:44   
	* 修改备注：   
	* 参数： @param adSolutionCondition
	* 参数： @return
	* @version
	 */
	public Page<AdvertiseSolutionListView> findAdSolutionPage(AdvertiseSolutionCondition adSolutionCondition);
	/**
	* 功能描述： 查询一段时间内的创建型且审批通过的方案数量  
	* 创建人：yudajun    
	* 创建时间：2014-5-17 下午2:17:05   
	* 修改人：yudajun
	* 修改时间：2014-5-17 下午2:17:05   
	* 修改备注：   
	* 参数： @param startDate
	* 参数： @param endDate
	* 参数： @return
	* @version
	 */
	public Long findSolutionCountCreatAndApproved(Date startDate,Date endDate);
	
	/**
	* 功能描述：   查询一段时间内的方案列表
	* 创建人：yudajun    
	* 创建时间：2014-5-20 下午2:47:53   
	* 修改人：yudajun
	* 修改时间：2014-5-20 下午2:47:53   
	* 修改备注：   
	* 参数： @param startDate
	* 参数： @param endDate
	* 参数： @param operateType
	* 参数： @return
	* @version
	 */
	public List<AdvertiseSolution> findSolutionCount(Date startDate,Date endDate,String operateType);
	
	public Map<Long, String> getIdNumberMap();
	
	public void moveToHistory(Long adSolutionId);
	
	List<AdvertiseSolution> findInfluencedAdSolution(Set<Long> positionDateIds, Long excludeAdSolutionId);
}
