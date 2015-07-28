package com.baidu.gcrm.valuelist.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;





import com.baidu.gcrm.valuelist.dao.IValuelistDao;

@Component
public class TableMetaDataManager implements ITableMetaDataManger {
	
    @Autowired
	private List<TableMetaData> tableMetas;
	
	@Autowired
	private IValuelistDao valuelistDao;
	
	public TableMetaDataManager(){
	    tableMetas = new ArrayList<TableMetaData>();
	}
	
	@Override
	public TableMetaData getTableMetaData(String tableName) {
		if(tableName==null||tableMetas==null)
			return null;
		for(TableMetaData table : tableMetas){
			if(table.getTableName().equals(tableName)){
				return table;
			}
		}
		return null;
	}
	
	@Override
    public List<String> getTableNames() {
	    List<String> talbeNames = new LinkedList<String>();
        for(TableMetaData table : tableMetas){
            talbeNames.add(table.getTableName());
        }
        return talbeNames;
    }
	
	@Override
	public void loadTableMetaData() {
		for(TableMetaData table : tableMetas){
			//为表信息加载列信息，其他信息在配置文件中已经完成
			List<ColumnMetaData> columns = valuelistDao.getTableInfo(table.getTableName());
			table.setColumns(columns);
		}
	}

	@Override
	public String getTableCacheKey(String tableName) {
		TableMetaData table = getTableMetaData(tableName);
		if(table!=null){
			return table.getTableCacheKey();
		}else{
			return "";
		}
	}

	@Override
	public String getRowKey(String tableName, Map<String, String> values) {
		TableMetaData table = getTableMetaData(tableName);
		if(table!=null){
			return table.getRowKey(values);
		}else{
			return "";
		}
	}

	public IValuelistDao getValuelistDao() {
		return valuelistDao;
	}

	public void setValuelistDao(IValuelistDao valuelistDao) {
		this.valuelistDao = valuelistDao;
	}

    public List<TableMetaData> getTableMetas() {
        return tableMetas;
    }

    public void setTableMetas(List<TableMetaData> tableMetas) {
        this.tableMetas = tableMetas;
    }
    
}
