package com.baidu.gcrm.personalpage.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.ad.material.vo.MaterialApplyContentVO;
import com.baidu.gcrm.ad.web.vo.AdvertiseSolutionListView;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.customer.web.helper.CustomerListBean;
import com.baidu.gcrm.customer.web.vo.CustomerI18nView;
import com.baidu.gcrm.personalpage.model.OperateReport;
import com.baidu.gcrm.personalpage.service.IOperationMailService;
import com.baidu.gcrm.personalpage.service.IPersonalPageService;
import com.baidu.gcrm.personalpage.web.vo.AdSolutionOperationVO;
import com.baidu.gcrm.personalpage.web.vo.ChannelOperationVO;
import com.baidu.gcrm.personalpage.web.vo.OperateReportVO;
import com.baidu.gcrm.personalpage.web.vo.PositionOperationVO;
import com.baidu.gcrm.personalpage.web.vo.RoleORModuleVO;
import com.baidu.gcrm.personalpage.web.vo.ModuleType;
import com.baidu.gcrm.quote.web.vo.QuotationMainVO;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;

@Controller
@RequestMapping("/personal")
public class PersonalPageAction extends ControllerHelper {
	@Autowired
	private IPersonalPageService personalPageService;
	
    @Autowired
    private IUserRightsService userRightsService;
    @Autowired
    IOperationMailService operationMailService;
	@RequestMapping("/findModule")
	@ResponseBody
	public JsonBean<List<RoleORModuleVO>> findModule(){
		String sales = GcrmConfig.getConfigValueByKey("personal.notDispaly.task.user.role");
		String operation = GcrmConfig.getConfigValueByKey("personal.dispaly.moduleCount.user.role");
		
		List<RightsRole> roleList = userRightsService.findUserRolesByUcId(RequestThreadLocal.getLoginUserId());
		boolean showTask = false;
		boolean showModule = false;
		
		for(RightsRole role : roleList){
			if(!sales.contains("-." + role.getRoleTag() + "-.")){
				showTask = true;
			}
			
			if(operation.contains("-." + role.getRoleTag() + "-.")){
				showModule = true;
			}
		}
		
		List<RoleORModuleVO> res = new ArrayList<RoleORModuleVO>();
		for(ModuleType type : ModuleType.values()){
			if(ModuleType.unprocess.ordinal() == type.ordinal()){//
				RoleORModuleVO vo = getModule(type,showTask);
				res.add(vo);
				continue;
			}
			
			if(ModuleType.moduleCount.ordinal() == type.ordinal()){//
				RoleORModuleVO vo = getModule(type,showModule);
				res.add(vo);
				continue;
			}
			
			if(ModuleType.operation.ordinal() == type.ordinal() ||
					ModuleType.submit.ordinal() == type.ordinal()){
				RoleORModuleVO vo = getModule(type,true);
				res.add(vo);
				continue;
			}
		}
		return JsonBeanUtil.convertBeanToJsonBean(res);
	}
	
	private RoleORModuleVO getModule(ModuleType type,boolean display){
		RoleORModuleVO vo = new RoleORModuleVO();
		vo.setDisplay(display);
		vo.setModuleType(type);
		return vo;
	}
	
	
	/**
	* 功能描述：   查询个人提交标杆价
	* 创建人：yudajun    
	* 创建时间：2014-5-15 下午4:59:53   
	* 修改人：yudajun
	* 修改时间：2014-5-15 下午4:59:53   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findQuotation")
	@ResponseBody
	public JsonBean<List<QuotationMainVO>> findQuotation() {
		return JsonBeanUtil.convertBeanToJsonBean(personalPageService.findPersonalQuotation(currentLocale));
	}

	/**
	* 功能描述：  查询近一个月提交的广告方案
	* 创建人：yudajun    
	* 创建时间：2014-5-15 下午5:00:49   
	* 修改人：yudajun
	* 修改时间：2014-5-15 下午5:00:49   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findAdSolution")
	@ResponseBody
	public JsonBean<List<AdvertiseSolutionListView>> findAdSolution() {
		return JsonBeanUtil.convertBeanToJsonBean(personalPageService.findAdSolution(currentLocale));
	}
	/**
	* 功能描述： 查询近一个月来提交的物料单
	* 创建人：yudajun    
	* 创建时间：2014-5-16 下午8:26:29   
	* 修改人：yudajun
	* 修改时间：2014-5-16 下午8:26:29   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findMaterialApplyContent")
	@ResponseBody
	public JsonBean<List<MaterialApplyContentVO>> findMaterialApplyContent(){
		return JsonBeanUtil.convertBeanToJsonBean(personalPageService.findMaterialApplyContent(currentLocale));
	}
	/**
	* 功能描述：   查询最近一个月的客户信息
	* 创建人：yudajun    
	* 创建时间：2014-5-19 下午8:13:50   
	* 修改人：yudajun
	* 修改时间：2014-5-19 下午8:13:50   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findCustomer")
	@ResponseBody
	public JsonBean<List<CustomerListBean>> findCustomer(){
		return JsonBeanUtil.convertBeanToJsonBean(personalPageService.findCustomer(currentLocale));
	}
	@RequestMapping("/findCustomerOperation")
	@ResponseBody
	public JsonBean<AdSolutionOperationVO> findCustomerOperation(){
		return JsonBeanUtil.convertBeanToJsonBean(personalPageService.findCustomerOperation());
	}
	
	/**
	* 功能描述：   方案及合同运营情况
	* 创建人：yudajun    
	* 创建时间：2014-5-17 下午3:43:27   
	* 修改人：yudajun
	* 修改时间：2014-5-17 下午3:43:27   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findSolutionOperation")
	@ResponseBody
	public JsonBean<AdSolutionOperationVO> findSolutionOperation(){
		return JsonBeanUtil.convertBeanToJsonBean(personalPageService.findSolutionOperation());
	}
	
	@RequestMapping("/findPlatformByCurrUser")
	@ResponseBody
	public JsonBean<List<AdvertisingPlatform>> findPlatformByCurrUser(){
		return JsonBeanUtil.convertBeanToJsonBean(personalPageService.findPlatformByCurrUser(currentLocale));
	}
	/**
	* 功能描述：   根据平台id查询站点的投放情况
	* 创建人：yudajun    
	* 创建时间：2014-5-17 下午6:10:41   
	* 修改人：yudajun
	* 修改时间：2014-5-17 下午6:10:41   
	* 修改备注：   
	* 参数： @param platformId
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findSiteOperation/{platformId}")
	@ResponseBody
	public JsonBean<List<PositionOperationVO>> findSiteOperation(@PathVariable("platformId") Long platformId){
		return JsonBeanUtil.convertBeanToJsonBean(personalPageService.findSiteOperationByPlatformId(platformId, currentLocale));
	}

	/**
	* 功能描述：   模块统计
	* 创建人：yudajun    
	* 创建时间：2014-5-15 下午5:00:58   
	* 修改人：yudajun
	* 修改时间：2014-5-15 下午5:00:58   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findModuleCount")
	@ResponseBody
	public JsonBean<OperateReportVO> findModuleCount() {
		return JsonBeanUtil.convertBeanToJsonBean(personalPageService.findOperateReport());
	}
	
	@RequestMapping("/analysisOperateReport")
	@ResponseBody
	public Object analysisOperateReport() {
		personalPageService.analysisOperateReport();
		return JsonBeanUtil.convertBeanToJsonBean(null);
	}
	@RequestMapping("/findVolist")
	@ResponseBody
	public JsonBean<String> findVolist() {
		try{
			operationMailService.StatisticsPositonByChannel();
		}catch (Exception e) {
			return JsonBeanUtil.convertBeanToJsonBean(null);
		}
		return JsonBeanUtil.convertBeanToJsonBean("ok");
	}
}
