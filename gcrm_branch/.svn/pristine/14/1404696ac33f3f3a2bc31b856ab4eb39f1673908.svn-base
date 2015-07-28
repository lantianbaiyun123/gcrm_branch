package com.baidu.gcrm.ad.material.vo;

import java.util.Date;
import java.util.List;

import com.baidu.gcrm.ad.content.model.AdSolutionContent.ApprovalStatus;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;
import com.baidu.gcrm.ad.model.AdvertiseMaterialMenu;

public class MaterialApproveInfoView {
    // 为前台处理更方便 顾从新定义属性信息
    /**
     * 物料申请单基本信息
     */
    // private MaterialApplyVO materialApplyVO;
    /**
     * 物料申请单中 物料详细信息
     */
    // private MaterialApplyDetailVO materialApplyDetailVO;
    /**
     * 物料申请单中，所对应的广告内容信息
     */
    // private MaterialContentVO materialContentVO;
    /**
     * 物料單ID
     */
    private String materialApplyId;

    /**
     * 物料单号
     */
    private String materialApplyNumber;

    /**
     * 提交人
     */
    private String submitPerson;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 广告主
     */
    private String advertiser;

    /**
     * 广告内容编号
     */

    private String advertiseContentNumber;

    /**
     * 方案执行人
     */
    private String operatorName;

    /**
     * 内容描述
     */
    private String description;

    /**
     * 内容状态
     */
    private ApprovalStatus approvalStatus;

    /**
     * 投放时间
     */
    private String periodDescription;

    /***
     * 投放位置描述
     */
    private String positionName;

    /**
     * 位置类型
     */
    private Integer  materialType;


    /**
     * 图片尺寸
     */
    private String areaRequired;

    /**
     * 图片大小
     */
    private String size;

    /**
     * 文字链长度
     */
    private Integer textlinkLength;

    /**
     * Title
     */
    private String materialTitle;

    /**
     * URL
     */
    private String materialUrl;
    
    private Integer materialFileType;
    
    private String monitorUrl;
    
    private String materialEmbedCodeContent;
    


    /**
     * 主物料图片附件
     */
    private List<AdvertiseMaterial> materialList;
    
    /**
     * 当前操作节点所对应的角色
     */
    private String role;

    /**
     * 当前任务是否完结
     */
    private boolean taskClose;
    
    /**
     * 图片类型物料下拉菜单列表
     */
    private List<AdvertiseMaterialMenu> materialMenuList;
    
    /**
     * 物料投放时间
     */
    private String materialPeriodDescription;
    
    public String getMaterialApplyId() {
        return materialApplyId;
    }

    public String getMaterialApplyNumber() {
        return materialApplyNumber;
    }

    public String getSubmitPerson() {
        return submitPerson;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public String getAdvertiseContentNumber() {
        return advertiseContentNumber;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getDescription() {
        return description;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public String getPeriodDescription() {
        return periodDescription;
    }

    public String getPositionName() {
        return positionName;
    }

    public Integer getMaterialType() {
        return materialType;
    }

    public String getAreaRequired() {
        return areaRequired;
    }

    public String getSize() {
        return size;
    }

    public Integer getTextlinkLength() {
        return textlinkLength;
    }

    public String getMaterialTitle() {
        return materialTitle;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public List<AdvertiseMaterial> getMaterialList() {
        return materialList;
    }

    /**
     * 
     * 功能描述: setMaterialApplyVO 创建人: chenchunhui01 创建时间: 2014年4月25日 下午4:17:23
     * 
     * @param materialApplyVO
     * @return void
     * @exception
     * @version
     */
    public void setMaterialApplyVO(MaterialApplyVO materialApplyVO) {
        if (materialApplyVO != null) {
            this.materialApplyNumber = materialApplyVO.getNumber();
            this.submitPerson = materialApplyVO.getCreateOperatorName();
            if(materialApplyVO.getCreateTime()  !=null)
                this.submitTime = materialApplyVO.getCreateTime();
        }
    }

    /**
     * 
     * 功能描述: setMaterialApplyDetailVO 创建人: chenchunhui01 创建时间: 2014年4月25日
     * 下午4:17:28
     * 
     * @param materialApplyDetailVO
     * @return void
     * @exception
     * @version
     */
    public void setMaterialApplyDetailVO(MaterialApplyDetailVO materialApplyDetailVO) {
        if (materialApplyDetailVO != null) {
            AdvertiseMaterialApply tempAdm = materialApplyDetailVO.getMaterialApply();
            this.materialList = materialApplyDetailVO.getMaterialApply().getMaterialList();
            this.materialApplyId = null == tempAdm.getId() ? "" : tempAdm.getId().toString();
            this.materialTitle = tempAdm.getMaterialTitle();
            this.materialUrl = tempAdm.getMaterialUrl();
            this.materialEmbedCodeContent = tempAdm.getMaterialEmbedCodeContent();
            this.materialFileType = tempAdm.getMaterialFileType();
            this.monitorUrl = tempAdm.getMonitorUrl();
            this.materialMenuList = tempAdm.getMaterialMenuList();
            this.materialPeriodDescription = tempAdm.getPeriodDescription();
        }
    }

    /**
     * 
     * 功能描述: setMaterialContentVO 创建人: chenchunhui01 创建时间: 2014年4月25日 下午4:17:32
     * 
     * @param materialContentVO
     * @return void
     * @exception
     * @version
     */
    public void setMaterialContentVO(MaterialContentVO materialContentVO) {
        if(materialContentVO==null){
            return;
        }
        this.advertiseContentNumber = materialContentVO.getNumber();
        this.advertiser = materialContentVO.getAdvertiser();
        this.operatorName = materialContentVO.getOperatorName();
        this.description = materialContentVO.getDescription();
        this.approvalStatus = materialContentVO.getApprovalStatus();
        this.periodDescription = materialContentVO.getPeriodDescription();
        this.positionName = materialContentVO.getPositionName();
        this.materialType = materialContentVO.getMaterialType();
        this.areaRequired = materialContentVO.getAreaRequired();
        this.size = materialContentVO.getSize();
        this.textlinkLength = materialContentVO.getTextlinkLength();
        //this.imageList = materialContentVO.getImageList();
        //this.codeFile = materialContentVO.getCodeFile();
    }

    public boolean isTaskClose() {
        return taskClose;
    }

    public void setTaskClose(boolean taskClose) {
        this.taskClose = taskClose;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getMaterialFileType() {
        return materialFileType;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public String getMaterialEmbedCodeContent() {
        return materialEmbedCodeContent;
    }

    public void setMaterialFileType(Integer materialFileType) {
        this.materialFileType = materialFileType;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public void setMaterialEmbedCodeContent(String materialEmbedCodeContent) {
        this.materialEmbedCodeContent = materialEmbedCodeContent;
    }

    public List<AdvertiseMaterialMenu> getMaterialMenuList() {
        return materialMenuList;
    }

    public String getMaterialPeriodDescription() {
        return materialPeriodDescription;
    }
}
