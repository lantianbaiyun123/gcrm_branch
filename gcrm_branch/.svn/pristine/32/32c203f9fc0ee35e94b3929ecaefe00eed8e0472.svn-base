package com.baidu.gcrm.ad.cancel.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.ad.content.model.AdContentCancelRecord;

public interface IAdContentCancelRecordRepository extends JpaRepository<AdContentCancelRecord, Long> {

public List<AdContentCancelRecord> findByAdSolutionContentId(Long adSolutionContentId);

public List<AdContentCancelRecord> findByAdSolutionContentIdIn(List<Long> adSolutionContentIds);

public List<AdContentCancelRecord> findByAdSolutionId(Long adSolutionId);

}
