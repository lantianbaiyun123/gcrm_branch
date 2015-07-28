package com.baidu.gcrm.ad.material.vo;

import java.util.List;

import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;

/**
 * 物料详情页VO
 * @author zhanglei35
 *
 */
public class MaterialDetailVO {

	public enum MaterialSaveType {
		/** 新增 */
		create,
		/** 变更 */
		change,
		/** 修改 */
		update,
		/** 恢复 */
		recovery;
		
		
		public static MaterialSaveType valueOf(Integer value){
			if(value == null){
				return null;
			}
			MaterialSaveType[] values = MaterialSaveType.values(); 
			for(MaterialSaveType status : values){
				if(status.ordinal() == value){
					return status;
				}
			}
			return null;
		}
	}
	
	/**
	 * 操作类型
	 */
	private Integer materialSaveType;

	/**
	 * 物料单所属的广告内容
	 */
	private MaterialContentVO materialContentVo;

	/**
	 * 物料单列表
	 */
	private List<MaterialApplyVO> materialApplyVoList;
	
	private AdvertiseMaterialApply materialApply;

	public MaterialContentVO getMaterialContentVo() {
		return materialContentVo;
	}

	public void setMaterialContentVo(MaterialContentVO materialContentVo) {
		this.materialContentVo = materialContentVo;
	}

	public List<MaterialApplyVO> getMaterialApplyVoList() {
		return materialApplyVoList;
	}

	public void setMaterialApplyVoList(List<MaterialApplyVO> materialApplyVoList) {
		this.materialApplyVoList = materialApplyVoList;
	}

	public Integer getMaterialSaveType() {
		return materialSaveType;
	}

	public void setMaterialSaveType(Integer materialSaveType) {
		this.materialSaveType = materialSaveType;
	}

    public AdvertiseMaterialApply getMaterialApply() {
        return materialApply;
    }

    public void setMaterialApply(AdvertiseMaterialApply materialApply) {
        this.materialApply = materialApply;
    }
	
	
}
