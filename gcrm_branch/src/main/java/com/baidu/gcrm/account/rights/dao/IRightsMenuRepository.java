package com.baidu.gcrm.account.rights.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.account.rights.model.RightsMenu;

public interface IRightsMenuRepository extends JpaRepository<RightsMenu, Long> {
	public List<RightsMenu> findByFuncTag(String funcTag);
}
