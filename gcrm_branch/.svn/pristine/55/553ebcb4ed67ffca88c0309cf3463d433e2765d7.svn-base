package com.baidu.gcrm.ad.approval.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;

public interface ParticipantRepository extends JpaRepository<Participant, Long>{
	
	Participant findByUsernameAndKeyAndDescription(String username, String key, DescriptionType description);
	
	Participant findByUsernameAndKeyAndDescriptionAndParticipantId(String username, String key, DescriptionType description, String partId);
	
	Participant findByParticipantIdAndUsernameAndDescriptionAndKey(String pid, String username, DescriptionType description, String key);
	
	List<Participant> findByKeyAndDescription(String key, DescriptionType description);
	
	List<Participant> findByKeyAndDescriptionAndParticipantId(String key, DescriptionType description, String partId);
	
	List<Participant> findByUsername(String username);
	
	List<Participant> findByUsernameAndDescription(String username, DescriptionType desType);
	
	@Modifying
	@Query("Delete From Participant Where username = ?1")
	public void deleteByUsername(String username);
	
	@Modifying
	@Query("Delete From Participant Where username = ?1 and description = ?2 and key = ?3")
	public void deleteByUnameAndDescAndKey(String username, DescriptionType desType, String key);
	
	@Modifying
	@Query("Delete From Participant Where username = ?1 and participantId not in (?2) ")
	public void deleteByUnameAndNotExistPartIds(String username, Collection<String> partIds);
}
