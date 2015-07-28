package com.baidu.gcrm.log.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name = "g_modify_table_info")
public class ModifyTableInfo implements BaseOperationModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 表名称
	 */
	@Column(name = "table_name")
	private String tableName;
	/**
	 * 表字段
	 */
	@Column(name = "table_field")
	private String tableField;
	/**
	 * 字段显示名称
	 */
	@Column(name = "field_name")
	private String fieldName;
	/**
	 * 字段描述
	 */
	@Column(name = "desc")
	private String desc;
	
	@Column(name = "local")
	private String local;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "create_operator")
	private Long createOperator;
	@Column(name = "last_update_time")
	private Date updateTime;
	@Column(name = "last_update_operator")
	private Long updateOperator;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableField() {
		return tableField;
	}
	public void setTableField(String tableField) {
		this.tableField = tableField;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateOperator() {
		return createOperator;
	}
	public void setCreateOperator(Long createOperator) {
		this.createOperator = createOperator;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateOperator() {
		return updateOperator;
	}
	public void setUpdateOperator(Long updateOperator) {
		this.updateOperator = updateOperator;
	}
	
}
