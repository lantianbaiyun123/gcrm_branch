package com.baidu.gcrm.personalpage.service;

import java.util.List;
import java.util.Map;

import com.baidu.gcrm.ad.material.vo.MaterialApplyContentVO;
import com.baidu.gcrm.ad.web.vo.AdvertiseSolutionListView;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.customer.web.helper.CustomerListBean;
import com.baidu.gcrm.customer.web.vo.CustomerI18nView;
import com.baidu.gcrm.personalpage.model.OperateReport;
import com.baidu.gcrm.personalpage.web.vo.AdSolutionOperationVO;
import com.baidu.gcrm.personalpage.web.vo.OperateReportVO;
import com.baidu.gcrm.personalpage.web.vo.PositionOperationVO;
import com.baidu.gcrm.quote.web.vo.QuotationMainVO;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;

public interface IPersonalPageService {
	/**
	* 功能描述：   查询个人一个月以来提交的标杆价
	* 创建人：yudajun    
	* 创建时间：2014-5-16 上午9:39:54   
	* 修改人：yudajun
	* 修改时间：2014-5-16 上午9:39:54   
	* 修改备注：   
	* 参数： @param currentLocale
	* 参数： @return
	* @version
	 */
	public List<QuotationMainVO> findPersonalQuotation(LocaleConstants currentLocale);
	
	/**
	* 功能描述：   查询近一个月来提交的广告方案
	* 创建人：yudajun    
	* 创建时间：2014-5-16 下午5:43:10   
	* 修改人：yudajun
	* 修改时间：2014-5-16 下午5:43:10   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	public List<AdvertiseSolutionListView> findAdSolution(LocaleConstants currentLocale);
	/**
	* 功能描述：   查询近一个月来提交的物料单
	* 创建人：yudajun    
	* 创建时间：2014-5-16 下午8:24:06   
	* 修改人：yudajun
	* 修改时间：2014-5-16 下午8:24:06   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	public List<MaterialApplyContentVO> findMaterialApplyContent(LocaleConstants currentLocale);
	
	/**
	* 功能描述：   方案及合同运营情况
	* 创建人：yudajun    
	* 创建时间：2014-5-17 下午3:41:59   
	* 修改人：yudajun
	* 修改时间：2014-5-17 下午3:41:59   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	public AdSolutionOperationVO findSolutionOperation();
	/**
	* 功能描述：   客户运营情况
	* 创建人：yudajun    
	* 创建时间：2014-5-20 上午10:31:51   
	* 修改人：yudajun
	* 修改时间：2014-5-20 上午10:31:51   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	public AdSolutionOperationVO findCustomerOperation();
	/**
	* 功能描述：   查询当前登录人拥有的平台
	* 创建人：yudajun    
	* 创建时间：2014-5-17 下午6:05:46   
	* 修改人：yudajun
	* 修改时间：2014-5-17 下午6:05:46   
	* 修改备注：   
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public List<AdvertisingPlatform> findPlatformByCurrUser(LocaleConstants locale);
	/**
	* 功能描述：   根据平台id查询站点的投放情况
	* 创建人：yudajun    
	* 创建时间：2014-5-17 下午6:10:33   
	* 修改人：yudajun
	* 修改时间：2014-5-17 下午6:10:33   
	* 修改备注：   
	* 参数： @param platformId
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public List<PositionOperationVO> findSiteOperationByPlatformId(Long platformId,LocaleConstants locale);
	/**
	* 功能描述：   查询操作统计
	* 创建人：yudajun    
	* 创建时间：2014-5-19 下午4:08:21   
	* 修改人：yudajun
	* 修改时间：2014-5-19 下午4:08:21   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	public OperateReportVO findOperateReport();
	/**
	* 功能描述：   定时任务统计操作数据
	* 创建人：yudajun    
	* 创建时间：2014-5-19 下午4:35:53   
	* 修改人：yudajun
	* 修改时间：2014-5-19 下午4:35:53   
	* 修改备注：   
	* 参数： 
	* @version
	 */
	public void analysisOperateReport();
	/**
	* 功能描述：   查询最近一个月的客户信息
	* 创建人：yudajun    
	* 创建时间：2014-5-19 下午6:09:10   
	* 修改人：yudajun
	* 修改时间：2014-5-19 下午6:09:10   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	public List<CustomerListBean> findCustomer(LocaleConstants locale);
}
