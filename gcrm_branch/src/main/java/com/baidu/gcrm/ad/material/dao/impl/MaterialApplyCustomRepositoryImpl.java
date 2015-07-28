package com.baidu.gcrm.ad.material.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.baidu.gcrm.ad.material.dao.MaterialApplyCustomRepository;

@Repository
public class MaterialApplyCustomRepositoryImpl implements MaterialApplyCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, String> getIdNumberMap() {
		Map<Long, String> results = new HashMap<Long, String>();
		String sql = "select id, number from AdvertiseMaterialApply";
		Query query = entityManager.createQuery(sql.toString());
		List<Object[]> objects = query.getResultList();
		for (Object[] object : objects) {
			results.put((Long) object[0], (String) object[1]);
		}
		return results;
	}

}
