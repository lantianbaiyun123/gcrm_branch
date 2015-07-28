package com.baidu.gcrm.publish.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.customer.web.helper.CustomerCondition;
import com.baidu.gcrm.customer.web.helper.CustomerListBean;
import com.baidu.gcrm.publish.web.utils.PublishCondition;
import com.baidu.gcrm.publish.web.utils.PublishDoneCondition;
import com.baidu.gcrm.publish.web.vo.ConditionVO;
import com.baidu.gcrm.publish.web.vo.PublishDateListVO;
import com.baidu.gcrm.publish.web.vo.PublishDoneListVO;
import com.baidu.gcrm.publish.web.vo.PublishListVO;
import com.baidu.gcrm.publish.web.vo.PublishOwnerListVO;

public interface IPublishListService {
	/**
	* 功能描述：    根据申请单编号查 上下线申请单子表
	* 创建人：yudajun    
	* 创建时间：2014-5-4 下午3:14:08   
	* 修改人：yudajun
	* 修改时间：2014-5-4 下午3:14:08   
	* 修改备注：   
	* 参数： @param publishNumber
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public PublishDateListVO findPublishDetailByPublishNumber(String publishNumber, Date operateDate);
	/**
	* 功能描述：  上下线申请单列表 
	* 创建人：yudajun    
	* 创建时间：2014-5-3 上午11:06:56   
	* 修改人：yudajun
	* 修改时间：2014-5-3 上午11:06:56   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public Page<PublishListVO> findPublishList(PublishCondition condition,LocaleConstants locale);
	/**
	* 功能描述：查询已处理   
	* 创建人：yudajun    
	* 创建时间：2014-5-3 上午11:15:11   
	* 修改人：yudajun
	* 修改时间：2014-5-3 上午11:15:11   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public Page<PublishDoneListVO> findPublishDoneList(PublishDoneCondition condition,LocaleConstants locale);

	/**
	* 功能描述：   筛选器
	* 创建人：yudajun    
	* 创建时间：2014-5-4 下午4:25:07   
	* 修改人：yudajun
	* 修改时间：2014-5-4 下午4:25:07   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public Map<String,ConditionVO> findConditionResult(PublishCondition condition, LocaleConstants locale);
	

	
	/**
	* 功能描述： 根据平台id查询频道操作人  
	* 创建人：yudajun    
	* 创建时间：2014-5-7 下午5:43:39   
	* 修改人：yudajun
	* 修改时间：2014-5-7 下午5:43:39   
	* 修改备注：   
	* 参数： @param platformId
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public List<PublishOwnerListVO> findPublishOwnerListByPlatformId(Long platformId,LocaleConstants locale);
	/**
	* 功能描述：   删除人员配置
	* 创建人：yudajun    
	* 创建时间：2014-5-7 下午7:51:01   
	* 修改人：yudajun
	* 修改时间：2014-5-7 下午7:51:01   
	* 修改备注：   
	* 参数： @param id
	* 参数： @param ucid
	* @version
	 */
	public void deletePublishOwner(Long id,Long ucid);
	/**
	* 功能描述：   添加人员配置
	* 创建人：yudajun    
	* 创建时间：2014-5-7 下午7:51:17   
	* 修改人：yudajun
	* 修改时间：2014-5-7 下午7:51:17   
	* 修改备注：   
	* 参数： @param id
	* 参数： @param ucid
	* 参数： @return
	* @version
	 */
	public Long addPublishOwner(Long id,Long ucid,Long positionId);
	/**
	* 功能描述：   判断有没有重复添加
	* 创建人：yudajun    
	* 创建时间：2014-5-8 上午11:22:07   
	* 修改人：yudajun
	* 修改时间：2014-5-8 上午11:22:07   
	* 修改备注：   
	* 参数： @param id
	* 参数： @param ucid
	* 参数： @return
	* @version
	 */
	public boolean checkAddUcidRe(Long id, Long ucid);
	
	PublishDateListVO findPublishDatesByPublishAndMaterialNumber(String publishNumber, String materialNumber);
}
