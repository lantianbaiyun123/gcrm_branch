package com.baidu.gcrm.ad.approval.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;

public interface IParticipantRepositoryCustom {
	List<String> getUsernamesByDescriptionAndInKeys(Collection<String> keys, DescriptionType description);
	
	List<String> getUnamesByDescAndInKeysAndPartId(Collection<String> keys, DescriptionType description, String partId);

	Map<String, String> getUsernameMapByDescriptionAndKeys(Collection<String> keys, DescriptionType description);
	
	public Map<String, String> getUsernameMapByDescAndKeysAndPartId(Collection<String> keys, DescriptionType description, String partId);
	
	List<String> getKeysByUsernameAndDescription(String username, DescriptionType desType);
	
	List<String> getKeysByUnameAndDescAndPartId(String username, DescriptionType desType, String partId);
}
