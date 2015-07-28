package com.baidu.gcrm.ad.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.model.AdvertiseSolutionApproveState;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.web.utils.AdvertiseSolutionCondition;
import com.baidu.gcrm.ad.web.vo.AdvertiseSolutionListView;
import com.baidu.gcrm.ad.web.vo.AdvertiseSolutionView;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.occupation1.model.PositionDate;

public interface IAdvertiseSolutionService {

	void save(AdvertiseSolution adSolution);
	
	void moveToHistory(Long adSolutionId);

	AdvertiseSolution findById(Long id);
	
	boolean canDelete(AdvertiseSolution adSolution);

	AdvertiseSolutionView advrtiseSolutionToView(AdvertiseSolution advertiseSolutionView, LocaleConstants local);

	AdvertiseSolution findByNumber(String number);
	
	void saveAndSubmitAdvertise(AdvertiseSolution adSolution, String username) throws Exception;
	
	/**
	 * <p>完成广告方案审批，创建国代竞价排期任务</p>
	 * <li>广告方案审核状态更新为<code>AdvertiseSolutionApproveState.approved</code></li>
	 * <li>广告方案对应广告内容状态更新为<code>ApprovalStatus.unconfirmed</code></li>
	 * <li>根据广告内容所选站点，为站点国代创建竞价排期待办任务</li>
	 * @param solution 广告方案
	 * @param username 广告方案创建人
	 */
	void completeAdApproveAndCreateSchedule(AdvertiseSolution solution, String username) throws CRMBaseException;
	
	/**
	 * <p>完成广告方案中变更的广告内容审批</p>
	 * <li>广告方案审核状态更新为<code>AdvertiseSolutionApproveState.approved</code></li>
	 * <li>广告方案对应广告内容状态更新为<code>ApprovalStatus.approved</code></li>
	 * @param adContentId 广告内容id
	 * @param username 广告方案创建人
	 */
	void completeSingleContentApproveAndCreateSchedule(Long adContentId, String username);
	
	/**
	* 功能描述：   广告方案列表查询
	* 创建人：yudajun    
	* 创建时间：2014-3-19 下午3:48:57   
	* 修改人：yudajun
	* 修改时间：2014-3-19 下午3:48:57   
	* 修改备注：   
	* 参数： @param adSolutionCondition
	* 参数： @return
	* @version
	 */
	public Page<AdvertiseSolutionListView> findAdSolutionPage(AdvertiseSolutionCondition adSolutionCondition);



	void submitContentUpdateProcess(AdSolutionContent content, AdvertiseSolution solution, String username)
			throws Exception;
	/**
	* 功能描述：   变更广告方案
	* 创建人：yudajun    
	* 创建时间：2014-3-25 上午10:03:25   
	* 修改人：yudajun
	* 修改时间：2014-3-25 上午10:03:25   
	* 修改备注：   
	* 参数： @param oldSolution
	* 参数： @return
	* @version
	 */
	public AdvertiseSolution changeAdSolution(AdvertiseSolution oldSolution) throws CRMBaseException;
	/**
	* 功能描述：   检查是否可以变更方案，每个广告内容的最大的投放日期都必须小于今天,true 可以变更  false 不允许变更
	* 创建人：yudajun    
	* 创建时间：2014-5-11 上午11:09:02   
	* 修改人：yudajun
	* 修改时间：2014-5-11 上午11:09:02   
	* 修改备注：   
	* 参数： @param solutionId
	* 参数： @return
	* @version
	 */
	public boolean checkChangeSolution(Long solutionId);
	/**
	* 功能描述：   取消变更
	* 创建人：yudajun    
	* 创建时间：2014-4-21 下午6:14:11   
	* 修改人：yudajun
	* 修改时间：2014-4-21 下午6:14:11   
	* 修改备注：   
	* 参数： @param id
	* 参数： @return
	* @version
	 */
	public Long cancelChangeAdSolution(Long id);
	String findOperator(Long id);
	String updateOperator(Long id,Long ucid,String operator);
	String findContractNumber(Long id);
	boolean checkContractTime(Date begin,Date end,Long adsolutionid);
	
	void remind(Long solutionId);
	/**
	 * <p>创建单笔合同后，更新广告方案合同状态和类型</p>
	 * <li>提示信息“已提交至商务人员审批”</li>
	 * @param id 广告方案
	 */
	void updateAdSolutionEnum(Long id);
	/**
	 * <p>创建框架/协议合同后，更新广告方案合同类型</p>
	 * <li>提示信息“已提交至商务人员审批”</li>
	 * @param id 广告方案
	 */
	void updateAdSolutionContract(Long id,String constractType);	
	int updateContractNumberById(String contractNumber, Long id);
	/**
	 * <p>撤销合同</p>
	 * @param 
	 */
	void updateAdSolutionContentContract(Long id);
	/**
	 * <p>将本地合同编号写入广告方案</p>
	 * <li>PO写入广告内容</li>
	 * @param 
	 */
	void updateAdSolutionContractNum(Long id, String contractNum);
	/**
	 * <p>修改广告方案/广告内容/排期单对应的审批状态</p>
	 * <li></li>
	 * @param 
	 */
	void updateAdSolutionStatus(Long id);
	
	/**
	 * <p>修改广告方案/广告内容/排期单对应的审批状态</p>
	 * @param 
	 */
	String findAdSolutionMessage(Long id,LocaleConstants currentLocale);

	/**
	 * 合同生效后，将合同编号回写广告方案<br>
	 * <li>对应排期状态为锁定</li>	 	  
	 * @param contents
	 * @param adSolutionId
	 */
    void updateAdSoulutionAndScheduleStatus(Contract contract);
    /**
	 * 为广告方案下面的广告内容创建PO<br>
	 * <li>对应排期状态为锁定</li>	 	  
	 * @param contents
	 * @param adSolutionId
	 */
    void createPo(String contractNumber,Long adsolutionid, Long ucId);
    
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
	* 创建时间：2014-5-20 下午2:47:01   
	* 修改人：yudajun
	* 修改时间：2014-5-20 下午2:47:01   
	* 修改备注：   
	* 参数： @param startDate
	* 参数： @param endDate
	* 参数： @param operateType
	* 参数： @return
	* @version
	 */
	public List<AdvertiseSolution> findSolutionCount(Date startDate,Date endDate,String operateType);
	/**
	 * 变更po
	 * author:luge
	 * @param contractNumber
	 * @param id
	 * @param ucId
	 * @return
	 */
	public  String changePoNum(String contractNumber,Long id, Long ucId);
	/**
	 * 变更合同
	 * author:luge
	 * @param id
	 * @return
	 */
	public  String getOldcontractNumber(Long id);
	/**
	 * 抹掉合同编号
	 * author:luge
	 * @param contractNumber
	 */
	public void eraseContractNumber(String contractNumber);
	
	void notifyInfluencedAdSolution(Set<PositionDate> positionDates, 
	        AdSolutionContent adSolutionContents, AdvertiseSolution advertiseSolution);
	
	public List<AdvertiseSolution> findByApprovalStatusIn(List<AdvertiseSolutionApproveState> list);
	
	public void updateStatusToEffective(AdvertiseSolution solution);
	
	public void updateStatusToEffective(Long solutionId);
}


