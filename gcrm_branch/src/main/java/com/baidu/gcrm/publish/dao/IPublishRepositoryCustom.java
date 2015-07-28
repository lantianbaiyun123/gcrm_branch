package com.baidu.gcrm.publish.dao;

import java.util.HashMap;
import java.util.List;

import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.model.PublishMailType;
import com.baidu.gcrm.publish.web.utils.PublishCondition;
import com.baidu.gcrm.publish.web.utils.PublishDoneCondition;
import com.baidu.gcrm.publish.web.vo.MaterialVo;
import com.baidu.gcrm.publish.web.vo.PublishDoneListVO;
import com.baidu.gcrm.publish.web.vo.PublishListVO;

public interface IPublishRepositoryCustom {

	/**
	* 功能描述：   条件查询上线申请单
	* 创建人：yudajun    
	* 创建时间：2014-4-30 上午11:28:40   
	* 修改人：yudajun
	* 修改时间：2014-4-30 上午11:28:40   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @return
	* @version
	 */
	public List<Publish> findPublishByCondition(PublishCondition condition);
	/**
	* 功能描述：   上下线申请单列表
	* 创建人：yudajun    
	* 创建时间：2014-4-30 上午11:37:36   
	* 修改人：yudajun
	* 修改时间：2014-4-30 上午11:37:36   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @return
	* @version
	 */
	public Page<PublishListVO> findPublishListVOByCondition(PublishCondition condition);
	
	/**
	* 功能描述：  根据申请单编号查 上下线申请单子表
	* 创建人：yudajun    
	* 创建时间：2014-5-3 上午10:59:34   
	* 修改人：yudajun
	* 修改时间：2014-5-3 上午10:59:34   
	* 修改备注：   
	* 参数： @param publishName
	* 参数： @return
	* @version
	 */
	public List<PublishDate> findPublishDateByPublishNumber(String publishNumber);
	/**
	* 功能描述： 查询已处理列表  
	* 创建人：yudajun    
	* 创建时间：2014-5-3 上午11:19:18   
	* 修改人：yudajun
	* 修改时间：2014-5-3 上午11:19:18   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @return
	* @version
	 */
	public Page<PublishDoneListVO> findPublishDoneList(PublishDoneCondition condition);
	/**
	 * 功能描述：通过方案查询执行人姓名和邮件地址
	 * 创建人：luge
	 * 参数： @param adsoulutionId
	 * */
	HashMap<String, Object> findExecutor(Long id);
	
	List<PublishListVO> findOnlinePublishByPlanStart(PublishMailType type);

	List<MaterialVo> findMaterialByAdcontentId(String number);
    List<PublishDate> findPublishNoStartDateByPublishNumber(String publishNumber);

	
}
