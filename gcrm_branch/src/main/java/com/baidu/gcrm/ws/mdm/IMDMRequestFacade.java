package com.baidu.gcrm.ws.mdm;

import com.baidu.gcrm.common.exception.CRMBaseException;

/**
 * GCRM向主数据同步数据请求门面
 *
 *     
 * 项目名称：gcrm    
 * 类名称：IMDMServiceFacade    
 * 类描述：   主要功能是：封装数据等调用webservice接口 
 * 创建人：chenchunhui01    
 * 创建时间：2014年4月29日 上午10:55:20    
 * 修改人：chenchunhui01    
 * 修改时间：2014年4月29日 上午10:55:20    
 * 修改备注：    
 * @version     
 *
 */
public interface IMDMRequestFacade {
    /**
     * 同步客户
     * @param customerId 客户
     * @throws CRMBaseException 
     */
 public   Long syncCustomer(Long customerId) throws CRMBaseException;
}
