package com.baidu.gcrm.customer.dao;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.web.helper.CustomerCondition;
import com.baidu.gcrm.customer.web.helper.CustomerListBean;


public interface CustomerRepositoryCustom {
	void updateCustomerNumber(Long oldId, Long newId);
	
	Page<CustomerListBean> findByCondition(CustomerCondition condition);
	
	/**
	 * 
	 * 功能描述:
	 * findExecutor
	 * @创建人:	 chenchunhui01
	 * @创建时间: 	2014年5月14日 上午11:55:57     
	 * @param id
	 * @return   
	 * @return HashMap<String,Object>  
	 * @exception   
	 * @version
	 */
   public  HashMap<String, Object> findExecutor(Long id);
   
   /**
   * 功能描述：   查询一段时间内创建的客户信息
   * 创建人：yudajun    
   * 创建时间：2014-5-19 下午5:42:44   
   * 修改人：yudajun
   * 修改时间：2014-5-19 下午5:42:44   
   * 修改备注：   
   * 参数： @param startDate
   * 参数： @param endDate
   * 参数： @return
   * @version
    */
   public List<Customer> findCustomerCreateBetween(Date startDate,Date endDate,String operateType);
   
   /**
    * 功能描述：   查询某一段时间内审批通过的新增类型（修改变更不算）的客户
    * 创建人：yudajun    
    * 创建时间：2014-5-20 上午10:36:56   
    * 修改人：yudajun
    * 修改时间：2014-5-20 上午10:36:56   
    * 修改备注：   
    * 参数： @param startDate
    * 参数： @param endDate
    * 参数： @return
    * @version
     */
    public List<Customer> findCustomerApprovedBetween(Date startDate,Date endDate);
    
    public Map<Long, Long> getIdNumberMap();
}
