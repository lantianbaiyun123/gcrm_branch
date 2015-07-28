package com.baidu.gcrm.ad.content.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.resource.position.model.Position;

public class AdSolutionContentValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AdSolutionContentView.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target==null) {
			return;
		}
		AdSolutionContentView view = (AdSolutionContentView)target;
		AdSolutionContent adContent = view.getAdSolutionContent();
		
		if (adContent == null) {
//		    errors.reject("advertise.content.null");
		    return;
		}
		
//		if (PatternUtil.isBlank(adContent.getAdvertiser())) {
//		    errors.rejectValue("adSolutionContent.advertiser", "advertise.content.advertiser.null");
//		}
//		
//		if (PatternUtil.isBlank(adContent.getDescription())) {
//            errors.rejectValue("adSolutionContent.description", "advertise.content.description.null");
//        }
//		
//		if (adContent.getSiteId() == null) {
//            errors.rejectValue("adSolutionContent.siteId", "advertise.content.siteId.null");
//        }
//		
//		if (adContent.getProductId() == null) {
//		    errors.rejectValue("adSolutionContent.productId", "advertise.content.productId.null");
//        }
		Long positionId = adContent.getPositionId();
		if (positionId == null) {
//            errors.rejectValue("adSolutionContent.positionId", "advertise.content.positionId.null");
        } else {
            String materialTitle = adContent.getMaterialTitle();
            if (!PatternUtil.isBlank(materialTitle)) {
                Position currPosition = ServiceBeanFactory.getPositionService().findById(positionId);
                Integer textlinkLength = currPosition.getTextlinkLength();
                if (textlinkLength != null && textlinkLength.intValue() < materialTitle.length()) {
                    errors.reject("advertise.content.materialTitle.limit");
                }
            }
        }
		
		/*
		Integer materialEmbedCode = adContent.getMaterialEmbedCode();
        if (materialEmbedCode != null && 1 == materialEmbedCode.intValue() 
                && PatternUtil.isBlank(adContent.getMaterialEmbedCodeContent())) {
            errors.rejectValue("adSolutionContent.materialEmbedCodeContent", "advertise.content.materialEmbedCodeContent.null");
        }
        
		Integer materialType = adContent.getMaterialType();
		if (materialType == null) {
		    errors.reject("advertise.content.materialType.null");
		}else if (materialType.intValue() != PositionMaterialType.custom.ordinal()) {
		    if (PatternUtil.isBlank(adContent.getMaterialUrl())) {
                errors.rejectValue("adSolutionContent.materialUrl", "advertise.content.materialUrl.null");
            }
		    if (materialType.intValue() != PositionMaterialType.image.ordinal()) {
		        if (PatternUtil.isBlank(adContent.getMaterialTitle())) {
	                errors.rejectValue("adSolutionContent.materialTitle", "advertise.content.materialTitle.null");
	            }
		    }
		    
		    if (materialType.intValue() == PositionMaterialType.image.ordinal()
	            || materialType.intValue() == PositionMaterialType.image_and_textlink.ordinal()){
	            
		        List<AdvertiseMaterial> advertiseMaterials = view.getAdvertiseMaterials();
		        if (CollectionUtils.isEmpty(advertiseMaterials)) {
		            errors.reject("advertise.content.material.attachment.null");
		        }
		    }
        }
		
		*/
		
	}
	

}
