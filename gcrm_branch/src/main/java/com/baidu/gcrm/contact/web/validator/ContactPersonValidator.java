package com.baidu.gcrm.contact.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.contact.model.ContactPerson.DecisionMakerStatus;
import com.baidu.gcrm.contact.model.ContactPerson.LegalPersonStatus;

public class ContactPersonValidator implements Validator{
	
	 
	@Override
	public boolean supports(Class<?> clazz) {
		return ContactPerson.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ContactPerson contactPerson = (ContactPerson)target;
		validateSingleContactPerson(errors, "", contactPerson, true);
	}
	
	
	
	public static void validateSingleContactPerson(Errors errors, String fieldPrefix,
			ContactPerson contactPerson, boolean isRejectValue){
		if(PatternUtil.isBlank(contactPerson.getName())){
			boolean hasOtherProperty = false;
			if(!PatternUtil.isBlank(contactPerson.getPositionName())){
				hasOtherProperty = true;
			}else if(!PatternUtil.isBlank(contactPerson.getSuperiorPosition())){
				hasOtherProperty = true;
			}else if(!PatternUtil.isBlank(contactPerson.getDepartment())){
				hasOtherProperty = true;
			}else if(!PatternUtil.isBlank(contactPerson.getTelephone())){
				hasOtherProperty = true;
			}else if(!PatternUtil.isBlank(contactPerson.getMobile())){
				hasOtherProperty = true;
			}else if(!PatternUtil.isBlank(contactPerson.getEmail())){
				hasOtherProperty = true;
			}else if(contactPerson.getIsLegalPerson() == LegalPersonStatus.ENABLE){
				hasOtherProperty = true;
			}else if(contactPerson.getIsDecisionMaker() == DecisionMakerStatus.ENABLE){
				hasOtherProperty = true;
			}
			
			if(hasOtherProperty && isRejectValue){
				errors.rejectValue(new StringBuilder(fieldPrefix).append("name").toString(), "contact.name.empty");
			}else{
				contactPerson.setNoNeedSave(true);
			}
			
		}
		
		if (!isRejectValue){
			return;
		}
		
		validatePhoneNumber(errors,new StringBuilder(fieldPrefix).append("telephone").toString(),contactPerson.getTelephone());
		
		String emailStr = contactPerson.getEmail();
		if(!PatternUtil.isBlank(emailStr) && !PatternUtil.isEmail(emailStr)){
			errors.rejectValue(new StringBuilder(fieldPrefix).append("email").toString(), "contact.email.invalid");
		}
	}
	
	private static void validatePhoneNumber(Errors errors,String field,String phoneNum){
		if(!PatternUtil.isBlank(phoneNum)){
//			String[] zoneNumberArr = phoneNum.split("-");
//			if(zoneNumberArr == null || zoneNumberArr.length < 2){
//				errors.rejectValue(field, "contact.phone.invalid");
//				return;
//			}
//			if(!PatternUtil.isNumber(zoneNumberArr[0])){
//				errors.rejectValue(field, "contact.zonecode.invalid");
//			}
//			if(!PatternUtil.isNumber(phoneNum)){
//				errors.rejectValue(field, "contact.num.invalid");
//			}
		}
	}
	

}
