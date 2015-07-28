package com.baidu.gcrm.quote.material.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.baidu.gcrm.quote.material.model.QuotationMaterial;

public interface QuotationMaterialRepository extends
		JpaRepository<QuotationMaterial, Long> {

    public List<QuotationMaterial> findByQuotationMainId(Long quotationMainId);
    
}
