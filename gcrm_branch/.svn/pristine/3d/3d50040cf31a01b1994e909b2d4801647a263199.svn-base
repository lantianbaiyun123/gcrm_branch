package com.baidu.gcrm.ws.cms;

import java.util.List;

import com.baidu.gcrm.ad.model.Contract;

/**
 * 请求CMS接口facade
 *
 */
public interface ICMSRequestFacade {
    
    /**
     * 同步客户
     * @param customerId 客户
     */
    void syncCustomer(Long customerId);
    
    /**
     * 
     * @param adSolutionId 广告方案id
     * @param oldContractNum 合同变更时，传入历史合同号
     */
    void createContract(Long adSolutionId, String oldContractNum);
    
    /**
     * 创建框架协议下的单笔合同
     * @param contractNum 框架协议合同号
     * @param adSolutionId 广告方案ID
     */
    void createSingleContract(String contractNum, Long adSolutionId);
    
    /**
     * 创建PO
     * @param contractNumber 合同编号
     * @param adContentId 内容id
     * @param ucid 创建人ucid
     * @param oldPONumber PO变更时，传入旧PO编号
     * @return po编号
     */
    String createPO(String contractNumber, Long adContentId, Long ucid,String oldPONumber);
    
    /**
     * 撤消广告方案
     * @param adSolutionId 方案id
     * @return 是否撤消
     */
    boolean cancelAdSolution(Long adSolutionId);
    
    /**
     * 即时返回指定客户关联到的合同对象，供提前上线申请选择
     * @param customerId 客户id
     * @return 符合条件的contract对象
     */
    List<Contract> getImmeContractsByCustomerId(Long customerId);
    
    /**
     * 是否可以作废客户
     * @param customerId
     * @return
     */
    public boolean invalidCustomer(Long customerId); 

}
