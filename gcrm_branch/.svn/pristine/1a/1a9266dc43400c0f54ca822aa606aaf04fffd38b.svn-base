package com.baidu.gcrm.quote.material.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.quote.material.dao.QuotationMaterialRepository;
import com.baidu.gcrm.quote.material.model.QuotationMaterial;
import com.baidu.gcrm.quote.material.service.QuotationMaterialService;

@Service
public class QuotationMaterialServiceImpl implements QuotationMaterialService {

	@Autowired
	private QuotationMaterialRepository quotationMaterialRepository;
	
	@Override
	public QuotationMaterial findById(Long id) {
		return quotationMaterialRepository.findOne(id);
	}

	@Override
	public void deleteQuotationMaterial(Long id) {
		quotationMaterialRepository.delete(id);
	}

}
