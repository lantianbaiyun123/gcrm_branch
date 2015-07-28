package com.baidu.gcrm.materials.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.materials.model.Attachment;

@Repository
public interface AttachmentDao extends JpaRepository<Attachment, Long>{
	List<Attachment> findByCustomerNumber(Long customerNumber);
	@Modifying
	@Query("DELETE  from Attachment a where  a.customerNumber= :customerNumber")
	void deleteByCustomerNumber(@Param("customerNumber")Long customerNumber);
	
	Attachment findByUrl(String url);
}
