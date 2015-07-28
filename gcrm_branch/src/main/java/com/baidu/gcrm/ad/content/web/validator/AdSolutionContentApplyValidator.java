package com.baidu.gcrm.ad.content.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.ad.model.Contract.ContractState;



public class AdSolutionContentApplyValidator implements Validator {
    private static final int  APPLY_REASON_MAX_LENGTH=5000;
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AdSolutionContentApply.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AdSolutionContentApply contentApply = (AdSolutionContentApply) target;
		if(ContractState.VALID.toString().equals(contentApply.getContractState())){
		  errors.rejectValue("adSolutionContentApply.contractNumber","onlineApply.field.contractNumber.invalid");	
		}
		String applyReason=contentApply.getApplyReason();
		if (applyReason != null) {
			if (applyReason.trim().length() > APPLY_REASON_MAX_LENGTH) {
				errors.rejectValue("adSolutionContentApply.applyReason","onlineApply.field.applyreason.toolong");
			} else {
				contentApply.setApplyReason(applyReason.trim());
			}
		}
	}


}
