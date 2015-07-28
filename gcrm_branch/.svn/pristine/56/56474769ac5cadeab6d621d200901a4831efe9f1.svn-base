package com.baidu.gcrm.ws.cms;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.baidu.gcrm.ws.cms.dto.ContractDTO;
import com.baidu.gcrm.ws.cms.dto.ContractExecuteInfoDTO;
import com.baidu.gcrm.ws.cms.dto.I18nDTO;
import com.baidu.gcrm.ws.cms.dto.PositionDTO;
import com.baidu.gcrm.ws.cms.dto.QuotationDTO;
import com.baidu.gcrm.ws.cms.dto.SiteDTO;
import com.baidu.gcrm.ws.cms.dto.UCUserDTO;

/**
 * CMS通知GCRM接口
 *
 */
@WebService(targetNamespace="http://ws.api.gcrm.baidu.com/")
public interface ICMSServiceFacade {
    
    
    /**
     * 查询所有国家
     * @return
     */
    List<Long> queryAllCountry();
    
    /**
     * 查询所有国家
     * @return
     */
    List<Long> queryAllAgentRegional();
    
    /**
     * 查询所有货币类型
     * @return
     */
    List<Long> queryAllCurrencyType();
    
    /**
     * 查询所有计费方式
     * @return
     */
    List<Long> queryAllBillingModel();
    
    /**
     * 查询所有计费方式
     * @return
     */
    List<Long> queryAllProduct();
    
    /**
     * 查询所有站点信息
     * @return
     */
    List<SiteDTO> queryAllSite();
    
    /**
     * 查询位置信息
     * @param siteId
     * @return
     */
    List<PositionDTO> queryAllPosition();
    
    /**
     * 查询国际化信息
     * @return
     */
    List<I18nDTO> queryAllI18n();
    
    /**
     * 查询UC人员信息
     * @param ucId
     * @return
     */
    UCUserDTO queryUserById(Long ucId);
    
    /**
     * 同步合同到GCRM
     * @param contractDTO
     */
    void syncContract(ContractDTO contractDTO);
    
    /**
     * 是否允许撤消广告方案接口
     * @param isAllowWithdraw 是否允许
     * @param adSolutionId 广告方案ID
     */
    void updateAdSolutionWithdrawState(boolean isAllowWithdraw, Long adSolutionId);
    
    /**
     * CMS退回广告方案接口
     * @param adSolutionId 广告方案ID
     * @param reason 原因
     * @param operator 操作人
     * @param operateDate 操作时间
     */
    void withdrawAdSolution(Long adSolutionId, String reason,
        Long operator, Date operateDate);
    
    /**
     * 查询价格政策信息
     * @return
     */
    List<QuotationDTO> queryAllQuotation();
    
    /**
     * 查询合同执行情况
     * @param adContentId 广告内容id
     * @return
     */
    List<ContractExecuteInfoDTO> queryContractExecuteInfo(Long adContentId);
    
}
