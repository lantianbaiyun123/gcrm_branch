package com.baidu.gcrm.amp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.amp.dao.ICountryDao;
import com.baidu.gcrm.amp.model.Country;
import com.baidu.gcrm.amp.model.Country.CountryStatus;
import com.baidu.gcrm.amp.service.ICountryService;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.service.I18nKVService;

@Service
public class CountryServiceImpl implements ICountryService {
	@Autowired
	ICountryDao countryDao;
	@Autowired
	I18nKVService i18nService;

	@Override
	public List<Country> findAllCountry(CountryStatus status,LocaleConstants locale) {
		List<Country> currCountryList = countryDao.findByStatus(status);
		if(currCountryList != null){
			for(Country temCountry : currCountryList){
				i18nService.fillI18nInfo(temCountry, locale);
				
			}
		}
		return currCountryList;
	}
	

	@Override
	public void updateCountry(Country country) {
		Country existsCountry = countryDao.findOne(country.getId());
		existsCountry.setStatus(country.getStatus());
		existsCountry.setRemark(country.getRemark());
		existsCountry.setOptDate(new Date());
		existsCountry.setOptUserId(country.getOptUserId());
		countryDao.save(existsCountry);
		
	}
	
	
}
