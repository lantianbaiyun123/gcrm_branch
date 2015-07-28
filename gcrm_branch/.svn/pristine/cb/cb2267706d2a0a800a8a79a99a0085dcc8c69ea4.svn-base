package com.baidu.gcrm.common.json;

import java.util.Map;

public class JsonBean <T> {
    private String message = "OK";
    private T data;
    private Map<String, String> errors;
    private Object errorList;
    private int code = JsonBeanUtil.CODE_SUCCESS;
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
    public Object getErrorList() {
        return errorList;
    }
    public void setErrorList(Object errorList) {
        this.errorList = errorList;
    }
    
}
