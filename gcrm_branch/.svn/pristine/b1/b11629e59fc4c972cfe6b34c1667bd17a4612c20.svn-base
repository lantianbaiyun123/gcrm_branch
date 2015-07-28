package com.baidu.gcrm.ad.service;

import java.util.Date;
import java.util.List;

import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.web.utils.ContractCondition;
import com.baidu.gcrm.ad.web.utils.ContractListBean;
import com.baidu.gcrm.common.exception.CRMBaseException;

public interface IContractService {

	Contract findByNumber(String number);
	
	List<Contract> findByCustomerId(Long customerId);

	List<Contract> findByNumberLikeAndCustomerIdTop5(String tag, Long customerId);
	
	Contract findByAdSolutionId(Long adSolutionId);
	
	void save(Contract contract);

	/**
	* 功能描述：   查询一段时间内的有效的合同数量
	* 创建人：yudajun    
	* 创建时间：2014-5-19 下午4:51:39   
	* 修改人：yudajun
	* 修改时间：2014-5-19 下午4:51:39   
	* 修改备注：   
	* 参数： @param startDate
	* 参数： @param endDate
	* 参数： @return
	* @version
	 */
	public Long findValidContractAmountBetween(Date startDate,Date endDate);
	
	/**
     * 
     * 功能描述:根据查询条件查找合同列表
     * findContractsByCondition
     * @创建人:     chenchunhui01
     * @创建时间:   2014年5月21日 上午10:44:51     
     * @param condition
     * @return   
     * @return List<Contract>  
     * @exception   
     * @version
     */
    public List<ContractListBean> findContractsByCondition(ContractCondition condition) throws CRMBaseException;
	
}
