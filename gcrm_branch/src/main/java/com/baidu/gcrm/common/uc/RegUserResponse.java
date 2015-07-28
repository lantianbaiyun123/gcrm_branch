package com.baidu.gcrm.common.uc;

public class RegUserResponse {
    
    private Long result;
    
    private Long ucid;

    private String msg;

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public Long getUcid() {
        return ucid;
    }

    public void setUcid(Long ucid) {
        this.ucid = ucid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

	@Override
	public String toString() {
		return "RegUserResponse [result=" + result + ", ucid=" + ucid
				+ ", msg=" + msg + "]";
	}
}
