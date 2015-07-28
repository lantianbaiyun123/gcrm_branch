package com.baidu.gcrm.ad.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.baidu.gcrm.ad.dao.AdvertiseMaterialRepositoryCustom;
import com.baidu.gcrm.ad.material.vo.MaterialFileVO;

@Repository
public class AdvertiseMaterialRepositoryCustomImpl implements AdvertiseMaterialRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unused")
	@Override
	public void moveToHistory(List<Long> adContentIds) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into g_advertise_material_history (")
			.append("id,advertise_solution_content_id,file_url,upload_file_name,")
			.append("download_file_name,create_time,create_operator,last_update_time,")
			.append("last_update_operator,material_apply_id,material_file_type) ")
			.append(" select id,advertise_solution_content_id,file_url,upload_file_name,")
			.append("download_file_name,create_time,create_operator,last_update_time,")
			.append("last_update_operator,material_apply_id,material_file_type ")
			.append(" from g_advertise_material where advertise_solution_content_id in (");
		int temParamIndex = 0;
		for(Long adContentId : adContentIds) {
			if (temParamIndex > 0) {
				sql.append(",");
			}
			sql.append("?");
			temParamIndex++;
		}
		sql.append(")");
		
		Query query = entityManager.createNativeQuery(sql.toString());
		int currParamIdex = 1;
		for(Long adContentId : adContentIds) {
			query.setParameter(currParamIdex, adContentId);
			currParamIdex++;
		}
		
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialFileVO> findMaterialFilesByAdContentIdIn(Collection<Long> adContentIds) {
		String sql = "select id, advertise_solution_content_id, file_url, material_file_type, download_file_name, upload_file_name" +
				" from g_advertise_material where advertise_solution_content_id in(:adContentIds)";
		Query query = entityManager.createNativeQuery(sql, MaterialFileVO.class);
		query.setParameter("adContentIds", adContentIds);
		return query.getResultList();
	}
}
