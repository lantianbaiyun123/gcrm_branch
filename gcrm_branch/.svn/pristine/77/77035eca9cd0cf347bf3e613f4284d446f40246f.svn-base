package com.baidu.gcrm.ad.approval.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
@Repository
public class ParticipantRepositoryCustomImpl implements IParticipantRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUsernamesByDescriptionAndInKeys(Collection<String> keys, DescriptionType description) {
		Query query = entityManager.createQuery("select distinct username from Participant where key in(:keys) and description=:description");
		query.setParameter("keys", keys);
		query.setParameter("description", description);
		return (List<String>)query.getResultList();
	}
	
	@Override
	public List<String> getUnamesByDescAndInKeysAndPartId(Collection<String> keys, DescriptionType description, String partId) {
		Query query = entityManager.createQuery("select distinct username from Participant where key in(:keys) and description=:description and participantId = :partId");
		query.setParameter("keys", keys);
		query.setParameter("description", description);
		query.setParameter("partId", partId);
		return (List<String>)query.getResultList();
	}

	@Override
	public Map<String, String> getUsernameMapByDescriptionAndKeys(Collection<String> keys, DescriptionType description) {
		Query query = entityManager.createQuery("select distinct key, username from Participant where key in(:keys) and description=:description");
		query.setParameter("keys", keys);
		query.setParameter("description", description);
		Map<String, String> results = new HashMap<String, String>();
		List<Object[]> objects = query.getResultList();
		for (Object[] object : objects) {
			results.put((String) object[0], (String)object[1]);
		}
		return results;
	}
	
	@Override
	public Map<String, String> getUsernameMapByDescAndKeysAndPartId(Collection<String> keys, DescriptionType description, String partId) {
		Query query = entityManager.createQuery("select distinct key, username from Participant where key in(:keys) and description=:description and participantId = :partId");
		query.setParameter("keys", keys);
		query.setParameter("description", description);
		query.setParameter("partId", partId);
		Map<String, String> results = new HashMap<String, String>();
		List<Object[]> objects = query.getResultList();
		for (Object[] object : objects) {
			results.put((String) object[0], (String)object[1]);
		}
		return results;
	}

	@Override
	public List<String> getKeysByUsernameAndDescription(String username, DescriptionType desType) {
		String sql = "";
		if (StringUtils.isNotEmpty(username)) {
			sql = "select distinct key from Participant where username=:username and description=:description";
		}else {
			sql = "select distinct key from Participant where description=:description";
		}
		Query query = entityManager.createQuery(sql);
		if (StringUtils.isNotEmpty(username)) {
			query.setParameter("username", username);
		}
		query.setParameter("description", desType);
		return (List<String>)query.getResultList();
	}
	
	@Override
	public List<String> getKeysByUnameAndDescAndPartId(String username, DescriptionType desType, String partId) {
		String sql = "select distinct key from Participant where username=:username and description=:description and participantId=:partId";
		Query query = entityManager.createQuery(sql);
		query.setParameter("username", username);
		query.setParameter("description", desType);
		query.setParameter("partId", partId);
		return (List<String>)query.getResultList();
	}
}
