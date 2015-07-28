package com.baidu.gcrm.occupation1.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = false)
@Table(name = "g_position_date")
public class PositionDate implements Serializable {

    private static final long serialVersionUID = 8335808839020252628L;

    public enum PositionDateStatus {
        /* 禁用 */
        DISABLED,
        /* 启用 */
        ENABLED;
        
        public boolean isEnabled() {
            return this.equals(ENABLED);
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 位置id，对应唯一的位置
     */
    @Column(name = "position_id")
    private Long positionId;

    /**
     * 日期，格式yyyy-MM-dd
     */
    @Column(name = "date")
    private Date date;

    /**
     * 位置在当前日期是否可用，0表示不可用，1表示可用
     */
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private PositionDateStatus status;
    
    @Transient
    private AdSolutionContent adSolutionContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PositionDateStatus getStatus() {
        return status;
    }

    public void setStatus(PositionDateStatus status) {
        this.status = status;
    }

    public AdSolutionContent getAdSolutionContent() {
        return adSolutionContent;
    }

    public void setAdSolutionContent(AdSolutionContent adSolutionContent) {
        this.adSolutionContent = adSolutionContent;
    }

}
