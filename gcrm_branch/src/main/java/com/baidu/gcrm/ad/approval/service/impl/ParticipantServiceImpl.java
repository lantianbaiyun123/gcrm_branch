package com.baidu.gcrm.ad.approval.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.approval.dao.IParticipantRepositoryCustom;
import com.baidu.gcrm.ad.approval.dao.ParticipantRepository;
import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.approval.service.IParticipantService;

@Service
public class ParticipantServiceImpl implements IParticipantService {

	@Autowired
	private ParticipantRepository participantDao;
	
	@Autowired
	private IParticipantRepositoryCustom participantCustomDao;
	
	@Override
	public Participant findParticipant(String username, String key, DescriptionType description) {
		return participantDao.findByUsernameAndKeyAndDescription(username, key, description);
	}
	
	@Override
	public Participant findParticipant(String username, String key, DescriptionType description, String partId) {
		return participantDao.findByUsernameAndKeyAndDescriptionAndParticipantId(username, key, description, partId);
	}
	
	@Override
	public List<String> getUsernamesByDescriptionAndInKeys(Collection<String> keys, DescriptionType description) {
		return participantCustomDao.getUsernamesByDescriptionAndInKeys(keys, description);
	}
	
	@Override
	public List<String> getUnamesByDescAndInKeysAndPartId(
			Collection<String> keys, DescriptionType description, String partId) {
		return participantCustomDao.getUnamesByDescAndInKeysAndPartId(keys, description, partId);
	}
	
	@Override
	public Participant findByKeyAndDescription(String key, DescriptionType description) {
		List<Participant> parts = participantDao.findByKeyAndDescription(key, description);
		if (CollectionUtils.isNotEmpty(parts)) {
			return parts.get(0);
		}
		return null;
	}
	
	@Override
	public Participant findByKeyAndDescAndParticId(String key, DescriptionType description, String particId) {
		List<Participant> parts = participantDao.findByKeyAndDescriptionAndParticipantId(key, description, particId);
		if (CollectionUtils.isNotEmpty(parts)) {
			return parts.get(0);
		}
		return null;
	}
	
	@Override
	public Map<String, String> getUsernameMapByDescriptionAndKeys(Collection<String> keys, DescriptionType description) {
		if (CollectionUtils.isEmpty(keys)) {
			return new HashMap<String, String>();
		}
		return participantCustomDao.getUsernameMapByDescriptionAndKeys(keys, description);
	}
	
	@Override
	public Map<String, String> getUsernameMapByDescAndKeysAndPartId(Collection<String> keys, DescriptionType description, String partId) {
		if (CollectionUtils.isEmpty(keys)) {
			return new HashMap<String, String>();
		}
		return participantCustomDao.getUsernameMapByDescAndKeysAndPartId(keys, description, partId);
	}

	@Override
	public List<String> getKeysByUsernameAndDescription(String username, DescriptionType desType) {
		return participantCustomDao.getKeysByUsernameAndDescription(username, desType);
	}

	@Override
	public Participant findByUnameAndPidAndDescAndKey(String username, String pid, DescriptionType desType, String key){
		return participantDao.findByParticipantIdAndUsernameAndDescriptionAndKey(pid, username, desType, key);
	}

    @Override
    public List<Participant> findAllByKeyAndDescAndParticId(String key, DescriptionType desc, String particId) {
        return participantDao.findByKeyAndDescriptionAndParticipantId(key, desc, particId);
    }
	
}
