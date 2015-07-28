package com.baidu.gcrm.amp.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.amp.dao.IOfferDao;
import com.baidu.gcrm.amp.dao.OfferSpecs;
import com.baidu.gcrm.amp.model.Offer;
import com.baidu.gcrm.amp.service.IOfferService;
import com.baidu.gcrm.customer.model.Customer;

@Service
public class OfferServiceImpl implements IOfferService {
	
	@Autowired
	IOfferDao offerDao;

	@Override
	public void saveOffer(Offer offer) {
		offerDao.save(offer);
		offerDao.updateOfferBusinessId(generateOfferBusinessId(offer), offer.getId());
	}

	@Override
	public Page<Offer> findAll(final Offer offer,Pageable page) {
		Customer customer = offer.getCustomer();
		String customerName = customer != null ? customer.getCompanyName() : null;
		
		return offerDao.findAll(Specifications.where(OfferSpecs.byCustomer(customerName)),
				page);
	}

	@Override
	public void delOffer(Offer offer) {
		offerDao.updateOfferStatus(Offer.OfferStatus.DISABLE.name(), offer.getId());
		
	}
	
	private String generateOfferBusinessId(Offer offer){
		return new StringBuilder()
					.append(offer.getCustomer().getId())
					.append(offer.getType())
					.append(offer.getId()).toString();
	}
	
}
