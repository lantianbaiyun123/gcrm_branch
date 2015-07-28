package com.baidu.gcrm.common.code.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.i18n.model.BaseI18nModel;

@Entity
@Table(name = "g_code")
public class Code extends BaseI18nModel implements Serializable{
    
    private static final long serialVersionUID = -6544397834214902063L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "code_order")
    private Long codeOrder;
    
    @Column(name = "code_type")
    private String codeType;
    
    @Column(name = "code_value")
    private String codeValue;
    
    @Column(name = "code_enum")
    private String codeEnum;
    
    @Column(name = "code_desc")
    private String codeDesc;
    
    @Column(name = "code_parent")
    private String codeParent;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCodeType() {
        return codeType;
    }
    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
    public String getCodeValue() {
        return codeValue;
    }
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
    public Long getCodeOrder() {
        return codeOrder;
    }
    public void setCodeOrder(Long codeOrder) {
        this.codeOrder = codeOrder;
    }
    public String getCodeEnum() {
        return codeEnum;
    }
    public void setCodeEnum(String codeEnum) {
        this.codeEnum = codeEnum;
    }
    public String getCodeDesc() {
        return codeDesc;
    }
    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }
	public String getCodeParent() {
		return codeParent;
	}
	public void setCodeParent(String codeParent) {
		this.codeParent = codeParent;
	}
}
