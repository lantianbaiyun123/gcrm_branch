package com.baidu.gcrm.log.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.common.model.BaseOperationModel;


@Entity
@Table(name = "g_modify_record")
public class ModifyRecord implements BaseOperationModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum OperateType{
		insert,
		modify,
		delete,
	}
	
	
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 表名称
	 */
	@Column(name = "table_name")
	private String tableName;
	/**
	 * 修改数据ID
	 */
	@Column(name = "modified_data_id")
	private Long modifiedDataId;
	/**
	 * 修改字段
	 */
	@Column(name = "modify_field")
	private String modifyField;
	/**
	 * 操作类型
	 */
	@Column(name = "operate_type")
	@Enumerated(EnumType.STRING)
	private OperateType operateType;
	/**
	 * 原来值
	 */
	@Column(name = "old_value")
	private String oldValue;
	/**
	 * 修改后的值
	 */
	@Column(name = "new_value")
	private String newValue;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "create_operator")
	private Long createOperator;
	
	
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
	public Long getModifiedDataId() {
		return modifiedDataId;
	}
	public void setModifiedDataId(Long modifiedDataId) {
		this.modifiedDataId = modifiedDataId;
	}
	public String getModifyField() {
		return modifyField;
	}
	public void setModifyField(String modifyField) {
		this.modifyField = modifyField;
	}
	public OperateType getOperateType() {
		return operateType;
	}
	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
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
	
	
	@Override
	public Date getUpdateTime() {
		return null;
	}
	@Override
	public void setUpdateTime(Date updateTime) {
		
	}
	@Override
	public Long getUpdateOperator() {
		return null;
	}
	@Override
	public void setUpdateOperator(Long updateOperator) {
		
	}
	
}
