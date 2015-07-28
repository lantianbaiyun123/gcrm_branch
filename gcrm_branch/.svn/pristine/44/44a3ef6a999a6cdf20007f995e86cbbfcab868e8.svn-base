package com.baidu.gcrm.publish.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.publish.model.ForcePublishProof;

public interface IForcePublishProofRepository extends JpaRepository<ForcePublishProof, Long> {
	@Query("select q from ForcePublishProof q where q.publishNumber = ?1 order by recordId desc")
	List<ForcePublishProof> findByPublishNumber(String publishNumber);
}
