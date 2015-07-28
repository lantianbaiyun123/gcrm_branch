/**
 * 
 */
package com.baidu.gcrm.notice.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.baidu.gcrm.notice.dao.INoticeReceiversRepositoryCustom;

/**
 * @author shijiwen
 *
 */
@Repository
public class INoticeReceiversRepositoryCustomImpl implements INoticeReceiversRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see INoticeReceiversRepositoryCustom#moveTimeoutAndReadToHistory(Long)
	 */
	@Override
	public void moveTimeoutAndReadToHistory() {
		// TODO Auto-generated method stub
		
	}
	
	
}
