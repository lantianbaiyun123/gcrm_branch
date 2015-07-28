package com.baidu.gcrm.common;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONObject;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.json.JsonKVBean;
import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.user.model.User;

public class ControllerHelper{
    
    protected LocaleConstants currentLocale = LocaleConstants.en_US;
    
	protected void generatePropertyForCreate(BaseOperationModel baseModel){
		if(baseModel == null) {
			return;
		}
		Date current = new Date();
		Long loginId = this.getUserId();
		
		baseModel.setCreateOperator(loginId);
		baseModel.setUpdateOperator(loginId);
		baseModel.setCreateTime(current);
		baseModel.setUpdateTime(current);
	}
	
	protected void generatePropertyForUpdate(BaseOperationModel baseModel){
		if(baseModel == null) {
			return;
		}
		Date current = new Date();
		Long loginId = this.getUserId();
		
		baseModel.setUpdateOperator(loginId);
		baseModel.setUpdateTime(current);
	}
	
	protected void generatePropertysForCreate(List<? extends BaseOperationModel> baseModelList){
		if(baseModelList == null || baseModelList.size() < 1){
			return;
		}
		
		for(BaseOperationModel temBaseOperationModel : baseModelList){
			generatePropertyForCreate(temBaseOperationModel);
		}
	}
	
	protected void generatePropertyForCreateUpdate(BaseOperationModel baseModel){
		Date current = new Date();
		Long loginId = this.getUserId();
		
		baseModel.setUpdateOperator(loginId);
		baseModel.setUpdateTime(current);
	}
	
	protected void generatePropertyForCreateUpdate(List<? extends BaseOperationModel> baseModelList){
        if(baseModelList == null || baseModelList.size() < 1){
            return;
        }
        
        for(BaseOperationModel temBaseOperationModel : baseModelList){
            generatePropertyForCreateUpdate(temBaseOperationModel);
        }
    }
	

	protected void addError(BindingResult result, Model model) {
		if(result.hasErrors()){
			Map<String, String> errors = collectErrors(result);
			model.addAttribute("errors", errors);
			model.addAttribute("errorsJson", JSONObject.toJSON(errors).toString());
		}
	}

	protected Map<String, String> collectErrors(BindingResult result) {
	    Map<String,String> errorsMap = new HashMap<String,String>();
	    
	    for (ObjectError objectError : result.getGlobalErrors()) {
	        String defaultMessage = objectError.getDefaultMessage();
	        if (!PatternUtil.isBlank(defaultMessage)) {
	            errorsMap.put(objectError.getCode(), defaultMessage);
	        }else {
	            errorsMap.put(objectError.getCode(), objectError.getCode());
	        }
	        
	    }
	    
	    for(FieldError fieldError : result.getFieldErrors()){
	    	String[] codes = fieldError.getCodes();
	    	if(fieldError.getCode().equals("typeMismatch")){
	    	    errorsMap.put(fieldError.getField(),codes[0]);
	    	} else {
	    	    errorsMap.put(fieldError.getField(),fieldError.getCode());
	    	}
	    }
	    return errorsMap;
    }
	
	protected LinkedList<JsonKVBean> collectErrorList(BindingResult result) {
	    LinkedList<JsonKVBean> errorList = new LinkedList<JsonKVBean> ();
        for (ObjectError objectError : result.getGlobalErrors()) {
            String errorMessage = objectError.getDefaultMessage();
            String errorCode = objectError.getCode();
            Object[] args = objectError.getArguments();
            if (PatternUtil.isBlank(errorMessage)) {
                errorMessage = errorCode;
            }
            errorList.add(new JsonKVBean(errorCode, errorMessage, args));
        }
        
        for(FieldError fieldError : result.getFieldErrors()){
            String[] codes = fieldError.getCodes();
            String errorField = fieldError.getField();
            String errorCode = fieldError.getCode();
            String errorMessage = errorCode;
            Object[] args = fieldError.getArguments();
            if(errorCode.equals("typeMismatch")){
                errorMessage = codes[0];
            }
            
            errorList.add(new JsonKVBean(errorField, errorMessage, args));
        }
        return errorList;
    }
	
	protected JsonBean collectMergeErrors(BindingResult result, int errorCode) {
		JsonBean jsonBean = new JsonBean();
		jsonBean.setCode(errorCode);
		if (errorCode == JsonBeanUtil.CODE_ERROR_DISPLAY_DIRECTLY) {
			jsonBean.setErrorList(collectErrorList(result));
		}else {
			jsonBean.setErrors(collectErrors(result));
		}
		return jsonBean;
	}
	
	protected User getLogin() {
		return null;
	}
	
	private User getUser(){ 
	    return RequestThreadLocal.getLoginUser();
	}

	protected String getUserName() {
 		return RequestThreadLocal.getLoginUsername();
	}
	protected String getUuapUserName() {
        return RequestThreadLocal.getLoginUser().getUuapName();
    }
	protected Long getUserId() {
 		return RequestThreadLocal.getLoginUserId();
	}
	protected String getChineseName() {
		String realName = this.getUser() == null ? null : this.getUser().getRealname();
		return realName;
	}

    public LocaleConstants getCurrentLocale() {
        return currentLocale;
    }
    
    @ModelAttribute
    public void initLocaleConstants(Model model,Locale currJavaLocale) {
        LocaleConstants temLocaleConstants = LocaleConstants.getLocaleConstantsByName(currJavaLocale.toString());
        if(temLocaleConstants != null){
            this.currentLocale = temLocaleConstants;
        }
    }
    
}
