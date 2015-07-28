package com.baidu.gcrm.quote.approval.record.service;

import java.util.Map;

import com.baidu.gcrm.mail.QuoteFinishContent;


public interface IQtProcessEndUpWordService {
	
	byte[] poiWordTableReplace(String sourceFile,QuoteFinishContent content,StringBuffer priceType) throws Exception;

    
}
