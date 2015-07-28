package com.baidu.gcrm.ws.cms.dto;

import java.io.Serializable;

/**
 * 位置信息DTO
 * 
 */
public class PositionDTO implements Serializable{

    private static final long serialVersionUID = 9107187501662868985L;
    
    private Long id;//主键id
    private Long productId;//产品id
    private Long siteId;//站点id
    private Long parentId;//父id
    private Integer type;//类型，0频道，1区域，2位置
    private Integer status;//状态，0未启用，1启用

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
