package com.baidu.gcrm.ad.material.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.ad.model.AdvertiseMaterialMenu;

public interface IMaterialMenuRepository extends JpaRepository<AdvertiseMaterialMenu, Long> {

    public List<AdvertiseMaterialMenu> findByMaterialApplyIdOrderByIdAsc(Long materialApplyId);

    @Modifying
    @Query("delete from AdvertiseMaterialMenu where materialApplyId=?1 ")
    public void deleteByMaterialApplyId(Long materialApplyId);

}
