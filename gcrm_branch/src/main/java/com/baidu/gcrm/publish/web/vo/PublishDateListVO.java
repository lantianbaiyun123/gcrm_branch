package com.baidu.gcrm.publish.web.vo;

import java.util.List;
import java.util.Map;

import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterialMenu;
import com.baidu.gcrm.publish.model.ForcePublishProof.ProofType;

public class PublishDateListVO {

	private List<PublishDateVO> publishDate;

	// 物料类
	private String materialNumber;// 物料单号
	private String materialTitle;// 物料title
	private Integer materialType;// 位置类型
	private Integer materialFileType;// 物料文件类型
	private String materialUrl;// 物料url
    private String materialEmbedCodeContent;
    private String monitorUrl;
	private Long adContentId;
	
	/**
	 * 物料图片文件列表
	 */
	private List<AdvertiseMaterial> materialList;
	
	private boolean isMaterialEmpty;
	
	private boolean isMaterialOld = false;
	
	private Integer materialEmptyType;//0 空 1 不全 2完整
	
	private Map<ProofType,List<PublishProofVO>> proof;

    /**
     * 图片类型物料下拉菜单列表
     */
    private List<AdvertiseMaterialMenu> materialMenuList;

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public String getMaterialTitle() {
		return materialTitle;
	}

	public void setMaterialTitle(String materialTitle) {
		this.materialTitle = materialTitle;
	}

	public String getMaterialUrl() {
		return materialUrl;
	}

	public void setMaterialUrl(String materialUrl) {
		this.materialUrl = materialUrl;
	}
	
	public List<AdvertiseMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<AdvertiseMaterial> materialList) {
        this.materialList = materialList;
    }

    public List<PublishDateVO> getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(List<PublishDateVO> publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getMaterialFileType() {
		return materialFileType;
	}

	public void setMaterialFileType(Integer materialFileType) {
		this.materialFileType = materialFileType;
	}

	public Long getAdContentId() {
		return adContentId;
	}

	public void setAdContentId(Long adContentId) {
		this.adContentId = adContentId;
	}

	public Map<ProofType, List<PublishProofVO>> getProof() {
		return proof;
	}

	public void setProof(Map<ProofType, List<PublishProofVO>> proof) {
		this.proof = proof;
	}

	public boolean isMaterialEmpty() {
		return isMaterialEmpty;
	}

	public void setMaterialEmpty(boolean isMaterialEmpty) {
		this.isMaterialEmpty = isMaterialEmpty;
	}

	public boolean isMaterialOld() {
		return isMaterialOld;
	}

	public void setMaterialOld(boolean isMaterialOld) {
		this.isMaterialOld = isMaterialOld;
	}
	
	public Integer getMaterialEmptyType() {
		return materialEmptyType;
	}

	public void setMaterialEmptyType(Integer materialEmptyType) {
		this.materialEmptyType = materialEmptyType;
	}

    public Integer getMaterialType() {
        return materialType;
    }

    public String getMaterialEmbedCodeContent() {
        return materialEmbedCodeContent;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMaterialType(Integer materialType) {
        this.materialType = materialType;
    }

    public void setMaterialEmbedCodeContent(String materialEmbedCodeContent) {
        this.materialEmbedCodeContent = materialEmbedCodeContent;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public List<AdvertiseMaterialMenu> getMaterialMenuList() {
        return materialMenuList;
    }

    public void setMaterialMenuList(List<AdvertiseMaterialMenu> materialMenuList) {
        this.materialMenuList = materialMenuList;
    }
}
