package com.baidu.gcrm.log.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.log.model.ModifyTableInfo;

@Repository
public interface ModifyTableInfoRepository extends JpaRepository<ModifyTableInfo, Long>{

	/**
	* 功能描述：   根据tablename、filed、local查询修改表信息
	* 创建人：yudajun    
	* 创建时间：2014-4-24 下午1:56:54   
	* 修改人：yudajun
	* 修改时间：2014-4-24 下午1:56:54   
	* 修改备注：   
	* 参数： @param tableName
	* 参数： @param tableField
	* 参数： @param local
	* 参数： @return
	* @version
	 */
	public List<ModifyTableInfo> findByTableNameAndTableFieldAndLocal(String tableName,String tableField,String local);
}
