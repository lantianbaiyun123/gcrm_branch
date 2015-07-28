package com.baidu.gcrm.valuelist.utils;

import java.io.Serializable;

/**
 * 表的基本信息描述，用于控制界面显示
 * @author weichengke
 *
 */
public class ColumnMetaData implements Serializable{

	private static final long serialVersionUID = 773176578872149177L;
	private String columnName;
	private String columnType;
	private boolean nullAble;
	
	public ColumnMetaData() {
	}
	
	public ColumnMetaData(String columnName, String columnType, boolean nullAble) {
		this.columnName = columnName;
		this.columnType = columnType;
		this.nullAble = nullAble;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnType() {
		return columnType;
	}
	
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public boolean isNullAble() {
		return nullAble;
	}
	
	public void setNullAble(boolean nullAble) {
		this.nullAble = nullAble;
	}
	
	@Override
	public String toString(){
		return "column name is "+columnName+
				",column type is "+columnType+
				", null able is "+nullAble;
	}
}
