package com.baidu.gcrm.amp.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.amp.model.Country;
import com.baidu.gcrm.amp.model.Country.CountryStatus;

public interface ICountryDao extends JpaRepository<Country, Long> {
	
	List<Country> findByStatus(CountryStatus status);
	
	
}
