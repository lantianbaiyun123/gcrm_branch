package com.baidu.gcrm.ad.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterial.MaterialFileType;

public interface AdvertiseMaterialRepository extends JpaRepository<AdvertiseMaterial, Long> {
    
	@Query("From AdvertiseMaterial where adSolutionContentId = ?1 and (materialApplyId is null or "
			+ "materialApplyId = (select id from AdvertiseMaterialApply where adSolutionContentId = ?1 "
			+ "and applyState ='confirm'))")
    public List<AdvertiseMaterial> findByAdSolutionContentId(Long adContentId);
    
    @Query("From AdvertiseMaterial where materialApplyId = ?")
    public List<AdvertiseMaterial> findByMaterialApplyId(Long applyId);
    
    @Query("From AdvertiseMaterial where materialApplyId = ?1 and materialFileType = ?2")
    public List<AdvertiseMaterial> findByMaterialApplyIdAndFileType(Long applyId, MaterialFileType fileType);
    
    @Modifying
    @Query("delete from AdvertiseMaterial where adSolutionContentId = ?1")
    public void deleteByAdSolutionContentId(Long contentId);
    
    @Modifying
    @Query("delete from AdvertiseMaterial where adSolutionContentId = ?1 and materialFileType = ?2")
    public void deleteByAdContentIdAndFileType(Long contentId, MaterialFileType fileType);
    
    @Modifying
    @Query("delete from AdvertiseMaterial where adSolutionContentId = ?1 and materialApplyId =  ?2 ")
    public void deleteByContentIdAndApplyId(Long contentId, Long applyId);
    
    @Modifying
    @Query("delete from AdvertiseMaterial where materialApplyId =  ?1 ")
    public void deleteByMaterialApplyId(Long applyId);
    
    @Modifying
    @Query("delete from AdvertiseMaterial where adSolutionContentId in (?1)")
    void deleteByContentIdIn(List<Long> contentIds);
    
    /**
     * 查找广告内容中的物料关联的相关文件，非物料单中的文件
     * @param contentId
     * @return
     */
    @Query("From AdvertiseMaterial where adSolutionContentId = ?1 and materialApplyId is null")
    public List<AdvertiseMaterial> findNoApplyFileByContentId(Long contentId);
    
    /**
     * 根据附件类型，查找广告内容中上传的物料相关附件，非物料单中的文件
     * @param contentId
     * @param fileType
     * @return
     */
    @Query("From AdvertiseMaterial where adSolutionContentId = ?1 and materialFileType = ?2 and materialApplyId is null")
    public List<AdvertiseMaterial> findNoApplyFileByConIdAndFileType(Long contentId, MaterialFileType fileType);
    

}
