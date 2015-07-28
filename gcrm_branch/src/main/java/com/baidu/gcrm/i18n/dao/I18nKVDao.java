package com.baidu.gcrm.i18n.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.model.I18nKV;

public interface I18nKVDao extends JpaRepository<I18nKV, Long> {
    
	I18nKV findByKeyAndLocale(String key,LocaleConstants locale);
	
	@Query("from I18nKV i where i.key like ?1 and i.locale=?2 ")
	List<I18nKV> findByKeyNameLikeAndLocale(String keyName, LocaleConstants locale);
	
	List<I18nKV> findByKey(String key);
	
	@Modifying
	@Query("delete from I18nKV where key=?1")
	void deleteByKey(String key);
	
	@Modifying
    @Query("delete from I18nKV where key=?1 and locale=?2")
    void deleteByKeyAndLocale(String key, LocaleConstants locale);
	
}
