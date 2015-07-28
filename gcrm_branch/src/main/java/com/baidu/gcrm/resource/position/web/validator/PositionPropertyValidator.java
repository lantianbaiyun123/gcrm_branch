package com.baidu.gcrm.resource.position.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionMaterialType;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.model.Position.RotationType;

public class PositionPropertyValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Position.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target==null) {
			return;
		}
		Position position = (Position)target;
		RotationType rotationType = position.getRotationType();
		if (rotationType == null) {
		    errors.reject("resource.position.rotationTyp.null");
		}
		PositionMaterialType materialType = position.getMaterialType();
		if (materialType == null) {
            errors.reject("resource.position.materialType.null");
        }
		
		if (PositionType.position != position.getType()) {
		    return;
		}
		Integer salesAmount = position.getSalesAmount();
		if (salesAmount == null) {
		    errors.reject("resource.position.salesAmount.null");
		} else if (RotationType.no == rotationType && salesAmount.intValue() > 1) {
		    errors.reject("resource.position.salesAmount.limit");
		}
	}
	

}
