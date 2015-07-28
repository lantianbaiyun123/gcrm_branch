package com.baidu.gcrm.ad.dao;

import java.util.Collection;
import java.util.List;

import com.baidu.gcrm.ad.material.vo.MaterialFileVO;

public interface AdvertiseMaterialRepositoryCustom {
	
	void moveToHistory(List<Long> adContentIds);
	
	List<MaterialFileVO> findMaterialFilesByAdContentIdIn(Collection<Long> adContentIds);
}
