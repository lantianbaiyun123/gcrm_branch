package com.baidu.gcrm.ad.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.dao.ContractRepository;
import com.baidu.gcrm.ad.dao.ContractRepositoryCutom;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.service.IContractService;
import com.baidu.gcrm.ad.web.utils.ContractCondition;
import com.baidu.gcrm.ad.web.utils.ContractListBean;
import com.baidu.gcrm.ad.web.utils.ContractUrlUtilHelper;
import com.baidu.gcrm.common.exception.CRMBaseException;

@Service
public class ContractServiceImpl implements IContractService{
	
	@Autowired
	private ContractRepository contractDao;
	@Autowired
	private ContractRepositoryCutom contractRepositoryCutom;

	@Override
	public Contract findByNumber(String number) {
		if(StringUtils.isEmpty(number)){
			return null;
		}
		
		return contractDao.findOne(number);
	}
	
	@Override
	public List<Contract> findByNumberLikeAndCustomerIdTop5(String number,Long customerId){
				
		PageRequest request = new PageRequest(0, 5);
		return contractDao.findByNumberLikeAndCustomerIdAndState(number + "%", customerId, Contract.ContractState.VALID,
				request);
	}

    @Override
    public Contract findByAdSolutionId(Long adSolutionId) {
        return contractDao.findByAdSolutionId(adSolutionId);
    }

    @Override
    public void save(Contract contract) {
        contractDao.save(contract);
    }

    @Override
    public List<Contract> findByCustomerId(Long customerId) {
        return contractDao.findByCustomerId(customerId);
    }

	@Override
	public Long findValidContractAmountBetween(Date startDate, Date endDate) {
		return contractRepositoryCutom.findValidContractAmountBetween(startDate, endDate);
	}

    @Override
    public List<ContractListBean> findContractsByCondition(ContractCondition condition) throws CRMBaseException {
        List<ContractListBean> resultContractList = contractRepositoryCutom.findContractsByCondition(condition);
        for(ContractListBean contract:resultContractList){
            contract.setDetailUrl(ContractUrlUtilHelper.getContractDetailUrl(contract.getNumber()));
        }
        return  resultContractList;
    }
}
