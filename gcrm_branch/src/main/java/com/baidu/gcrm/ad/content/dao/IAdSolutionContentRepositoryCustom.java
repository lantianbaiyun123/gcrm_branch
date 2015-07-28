package com.baidu.gcrm.ad.content.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.ad.content.model.AdContentBriefVO;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.web.helper.AdSolutionContentCondition;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView4Material;
import com.baidu.gcrm.ad.content.web.vo.AdcontentChangeVo;
import com.baidu.gcrm.ad.material.vo.MaterialApplyContentVO;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;
import com.baidu.gcrm.ad.web.utils.AdvertiseSolutionCondition;
import com.baidu.gcrm.ad.web.vo.AdvertiseMultipleVO;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.data.bean.ADContent;

public interface IAdSolutionContentRepositoryCustom {

	/**
	 * 查询方案状态为审核完成之前：根据销售录入的初次的期望开始投放时间，查询方案id列表
	 * @param from
	 * @param to
	 * @return
	 */
	List<Long> findSolutionIdListBeforeApproving(String from,String to);
	
	/**
	 * 当方案状态为待确认或已确认，则根据竞价完成后的最终排期时间的开始时间，查询方案id列表
	 * @param from
	 * @param to
	 * @return
	 */
	List<Long> findSolutionIdListafterApproving(String from,String to);
	/**
     * 根据查询条件，查询广告内容列表
     * @param from
     * @param to
     * @return
     */
	Page<AdSolutionContentView4Material> finSolutionContentByConditon(AdSolutionContentCondition condition);
	
	/**
	 * 根据广告内容ID更新其物料信息
	 * @param applys
	 * @param contentId
	 */
	public void updateContentMaterialById(AdvertiseMaterialApply apply, Long contentId, Date updateDate, Long updateUcId);
	
	/**
	 * 根据广告方案ID找到有物料信息的广告内容
	 * @param solutionId
	 * @return
	 */
	public List<AdSolutionContent> findHasMaterialContentBySolutionId(Long solutionId);
	/**
	* 功能描述：   首页查询物料申请记录
	* 创建人：yudajun    
	* 创建时间：2014-5-16 下午8:18:26   
	* 修改人：yudajun
	* 修改时间：2014-5-16 下午8:18:26   
	* 修改备注：   
	* 参数： @param adSolutionCondition
	* 参数： @return
	* @version
	 */
	public List<MaterialApplyContentVO> findMaterialApplyContent(AdvertiseSolutionCondition adSolutionCondition);
	
	public Map<Long, String> getIdNumberMap();
	public AdcontentChangeVo getChangeContent(Long adContentId);
	
	/**
	* 功能描述：   变更的方案在审批通过后进行一系列的数据更新操作
	* 创建人：yudajun    
	* 创建时间：2014-6-17 上午11:38:42   
	* 修改人：yudajun
	* 修改时间：2014-6-17 上午11:38:42   
	* 修改备注：   
	* @version
	 */
	public void updateDataAfterSolutionChangedApproved(AdvertiseMultipleVO vo);
	
	void moveToHistory(Long adSolutionId);
	
	List<ADContent> findAllADContentsByIdIn(Collection<Long> adContentIds);
	
    /**
     * 获取占用指定位置的审批中的和审批通过但未完成上线的内容列表
     * 
     * @param positionId 指定位置的id
     * @return 符合条件的精简内容列表
     */
	List<AdContentBriefVO> findContentBriefsHodingPosition(Long positionId);
}
