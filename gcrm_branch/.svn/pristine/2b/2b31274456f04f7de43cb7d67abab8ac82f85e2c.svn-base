package com.baidu.gcrm.ad.material.web.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.material.vo.MaterialApplyDetailVO;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionMaterialType;

/**
 * 物料表单验证类
 * @author zhanglei35
 *
 */
public class MaterialValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(MaterialApplyDetailVO.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target==null) {
			return;
		}
		MaterialApplyDetailVO applyDetailVO = (MaterialApplyDetailVO) target;
		AdvertiseMaterialApply materialApply = applyDetailVO.getMaterialApply();
		List<AdvertiseMaterial> imgList = applyDetailVO.getMaterialApply().getMaterialList();
		
		// 物料URL不能为空
		if ((materialApply.getMaterialFileType() == null || materialApply.getMaterialFileType() != 2)
                && PatternUtil.isBlank(materialApply.getMaterialUrl())) {
            errors.rejectValue("materialApply.materialUrl", "adSolution.content.material.materialUrl.null");
        }
		AdSolutionContent adContent = ServiceBeanFactory.getAdSolutionContentService().findOne(materialApply.getAdSolutionContentId());
		Position position = ServiceBeanFactory.getPositionService().findById(adContent.getPositionId());
		// 根据资源位的物料类型，检验图片、文字链是否为空
		if (position.getMaterialType() == PositionMaterialType.image) {
		    if(materialApply.getMaterialFileType()!=null && materialApply.getMaterialFileType()==2){
		        if (PatternUtil.isBlank(materialApply.getMaterialEmbedCodeContent())) {
		            errors.rejectValue("materialApply.materialEmbedCodeContent", "adSolution.content.material.materialEmbedCodeContent.null");
		        } 
		    }else{
			checkImage(imgList, errors);
		    }
		    
		}else if (position.getMaterialType() == PositionMaterialType.textlink) {
			checkTextlink(materialApply, position, errors);
		}else if (position.getMaterialType() == PositionMaterialType.image_and_textlink) {
		    if(materialApply.getMaterialFileType()!=null && materialApply.getMaterialFileType()==2){
                if (PatternUtil.isBlank(materialApply.getMaterialEmbedCodeContent())) {
                    errors.rejectValue("materialApply.materialEmbedCodeContent", "adSolution.content.material.materialEmbedCodeContent.null");
                } 
            }else{
			checkImage(imgList, errors);
		    }
			checkTextlink(materialApply, position, errors);
		}
	}
	
	private void checkImage(List<AdvertiseMaterial> imgList, Errors errors){
		if (imgList.size() < 1 || (null != imgList.get(0) && PatternUtil.isBlank(imgList.get(0).getFileUrl()))) {
			errors.rejectValue("", "adSolution.content.material.image.null");
		}
	}
	
	private void checkTextlink(AdvertiseMaterialApply materialApply, Position position, Errors errors){
		if (PatternUtil.isBlank(materialApply.getMaterialTitle()) ) {
			errors.rejectValue("materialApply.materialTitle", "adSolution.content.material.title.null");
			return;
		}
//		if (null != position && null != materialApply.getMaterialTitle() && position.getTextlinkLength() != null) {
//			if (position.getTextlinkLength() < materialApply.getMaterialTitle().length()) {
//				errors.rejectValue("materialApply.materialTitle", "adSolution.content.material.textlinktoolong");
//			}
//		}
	}
}
