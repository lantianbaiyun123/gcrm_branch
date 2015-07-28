package com.baidu.gcrm.quote.web.vo;

import java.util.List;

public class QuotationMainConflictVO {
	private QuotationMainVO oldQuotationMainVO;
    private List<QuotationMainVO> newQuotationMainVOList;
    
	public QuotationMainVO getOldQuotationMainVO() {
		return oldQuotationMainVO;
	}
	public void setOldQuotationMainVO(QuotationMainVO oldQuotationMainVO) {
		this.oldQuotationMainVO = oldQuotationMainVO;
	}
	public List<QuotationMainVO> getNewQuotationMainVOList() {
		return newQuotationMainVOList;
	}
	public void setNewQuotationMainVOList(
			List<QuotationMainVO> newQuotationMainVOList) {
		this.newQuotationMainVOList = newQuotationMainVOList;
	}
}
