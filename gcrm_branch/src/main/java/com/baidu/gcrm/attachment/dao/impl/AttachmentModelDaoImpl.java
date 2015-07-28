package com.baidu.gcrm.attachment.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.baidu.gcrm.attachment.dao.IAttachmentModelDao;
import com.baidu.gcrm.attachment.model.AttachmentModel;
import com.baidu.gcrm.attachment.model.AttachmentModel.ModuleNameWithAtta;

@Repository
public class AttachmentModelDaoImpl implements IAttachmentModelDao {

	@PersistenceContext
	private EntityManager entityManager;
	@SuppressWarnings("unchecked")
	@Override
	public List<AttachmentModel> findByRecordAndModule(Long recoredId,
			ModuleNameWithAtta moduleName) {
		// TODO Auto-generated method stub
		String qlString="select a from AttachmentModel a where a.transactionRecordId=?1 and a.moduleName=?2";
		return entityManager.createQuery(qlString).setParameter(1, recoredId).setParameter(2, moduleName).getResultList();
	}

	public int deleteByRecordAndModule(Long recoredId,
			ModuleNameWithAtta moduleName) {
	   String delStr="delete from AttachmentModel a where a.transactionRecordId=?1 and a.moduleName=?2";
	   return entityManager.createQuery(delStr).setParameter(1, recoredId).setParameter(2, moduleName).executeUpdate();
	
	}
	
	@Override
	public AttachmentModel findByUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(AttachmentModel attachmentModel) {
		// TODO Auto-generated method stub
		entityManager.persist(attachmentModel);
	}

}
