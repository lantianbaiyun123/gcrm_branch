package com.baidu.gcrm.amp.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.baidu.gcrm.amp.model.Offer;

public class OfferSpecs {
	
	 public static Specification<Offer> byCustomer(final String customerName){
		 return new Specification<Offer>() {
			 public Predicate toPredicate(Root<Offer> root, CriteriaQuery<?> query,
				 CriteriaBuilder builder) {
				 if(customerName == null || "".equals(customerName.trim())){
					 return null;
				 }
				 Path<String> customerName = root.get("customer").get("name");
				 return builder.equal(customerName,customerName);
				 
			 }
		 };
	  }
	 
	 

}
