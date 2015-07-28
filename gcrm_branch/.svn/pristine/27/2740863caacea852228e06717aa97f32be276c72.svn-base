package com.baidu.gcrm.common.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class SampleBatchRepository<T> implements BatchRepository<T>{
	
	protected int batchSize = 100;

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void batchInsert(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			em.persist(list.get(i));
			if (i % batchSize == 0) {
				em.flush();
				em.clear();
			}
		}
		
	}
	
	@Override
	public void batchUpdate(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			em.merge(list.get(i));
			if (i % batchSize == 0) {
				em.flush();
				em.clear();
			}
		}
	}
	
	
	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}


	


}
