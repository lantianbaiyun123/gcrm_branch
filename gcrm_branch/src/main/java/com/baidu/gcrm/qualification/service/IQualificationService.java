package com.baidu.gcrm.qualification.service;

import com.baidu.gcrm.qualification.model.Qualification;

public interface IQualificationService {

	
	public void saveQualification(Qualification qualification);
	@Deprecated
	public int updateCustomerNumber(Long newCustomerNumber, Long oldCustomerNumber);
	@Deprecated
	public Qualification findByCustomerNumber(Long customerNumber);
	@Deprecated
	public void deleteByCustomerNumber(Long customerNumber);
}