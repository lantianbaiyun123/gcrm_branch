package com.baidu.gcrm.ad.approval.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;

public interface IParticipantService {
	@Deprecated
	Participant findParticipant(String username, String key, DescriptionType description);
	
	Participant findParticipant(String username, String key, DescriptionType description, String partId);
	
	List<String> getUsernamesByDescriptionAndInKeys(Collection<String> keys, DescriptionType description);
	
	List<String> getUnamesByDescAndInKeysAndPartId(Collection<String> keys, DescriptionType description, String partId);
	
	Map<String, String> getUsernameMapByDescriptionAndKeys(Collection<String> keys, DescriptionType description);
	
	public Map<String, String> getUsernameMapByDescAndKeysAndPartId(Collection<String> keys, DescriptionType description, String partId);
	
	Participant findByKeyAndDescription(String key, DescriptionType description);
	
	Participant findByKeyAndDescAndParticId(String key, DescriptionType description, String particId);
	
	List<Participant> findAllByKeyAndDescAndParticId(String key, DescriptionType desc, String particId);
	
	/**
	 * 根据用户名和描述获取key
	 * @param username 可以为null
	 * @param desType
	 * @return
	 */
	List<String> getKeysByUsernameAndDescription(String username, DescriptionType desType);
	
	Participant findByUnameAndPidAndDescAndKey(String username, String pid, DescriptionType desType, String key);
	
}
