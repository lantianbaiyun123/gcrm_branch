package com.baidu.gcrm.mail;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.quote.approval.record.model.QtApprovalRecord;

public class QuoteFinishContent {
	/**	收件人 */
	private Set<String> sendTo;	
	/**	抄送 */
	private Set<String> cc;
	/**业务类型*/	
	private String type;
	/**投放平台*/
	private String platFormName;
	/**价格种类*/
	private String priceType;
	/**生效时间*/
	private String validStartTime;
	/**失效时间*/
	private String validEndTime;
	/**定价说明*/
	private String priceMessage;
	/**投放站点*/
	private String siteName;
	/**标杆价*/
	private String quotePrice;
	/**标杆价详情*/
	private String submitTime;
	private List<Map> url;
	private String quoteUrl;
	private LocaleConstants locale;
	private String submitTer;
	private String cpc;
	private String cpm;
	private String cpa;
	private String cpt;
	private String cps1;
	private String putonType;
	private String cps3;
	private String ratioCustomer;
	private String ratioMine;
	private String ratioThird;
	private String ratioRebate;
	private String operator;
	public String getRatioCustomer() {
		return ratioCustomer;
	}
	public void setRatioCustomer(String ratioCustomer) {
		this.ratioCustomer = ratioCustomer;
	}
	public String getRatioMine() {
		return ratioMine;
	}
	public void setRatioMine(String ratioMine) {
		this.ratioMine = ratioMine;
	}
	public String getRatioThird() {
		return ratioThird;
	}
	public void setRatioThird(String ratioThird) {
		this.ratioThird = ratioThird;
	}
	private String recordList;
	private String wordName;
	private	ByteArrayInputStream instream;
	public String getCpc() {
		return cpc;
	}
	public void setCpc(String cpc) {
		this.cpc = cpc;
	}
	public String getCpm() {
		return cpm;
	}
	public void setCpm(String cpm) {
		this.cpm = cpm;
	}
	public String getCpa() {
		return cpa;
	}
	public void setCpa(String cpa) {
		this.cpa = cpa;
	}
	public String getCpt() {
		return cpt;
	}
	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

	public Set<String> getSendTo() {
		return sendTo;
	}
	public void setSendTo(Set<String> sendTo) {
		this.sendTo = sendTo;
	}
	public Set<String> getCc() {
		return cc;
	}
	public void setCc(Set<String> cc) {
		this.cc = cc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPlatFormName() {
		return platFormName;
	}
	public void setPlatFormName(String platFormName) {
		this.platFormName = platFormName;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public String getValidStartTime() {
		return validStartTime;
	}
	public void setValidStartTime(String validStartTime) {
		this.validStartTime = validStartTime;
	}
	public String getPriceMessage() {
		return priceMessage;
	}
	public void setPriceMessage(String priceMessage) {
		this.priceMessage = priceMessage;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getQuotePrice() {
		return quotePrice;
	}
	public void setQuotePrice(String quotePrice) {
		this.quotePrice = quotePrice;
	}
	public String getQuoteUrl() {
		return quoteUrl;
	}
	public void setQuoteUrl(String quoteUrl) {
		this.quoteUrl = quoteUrl;
	}
	/**
	 * @return the url
	 */
	public List<Map> getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(List<Map> url) {
		this.url = url;
	}
	/**
	 * @return the locale
	 */
	public LocaleConstants getLocale() {
		return locale;
	}
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(LocaleConstants locale) {
		this.locale = locale;
	}
	/**
	 * @return the submitTer
	 */
	public String getSubmitTer() {
		return submitTer;
	}
	/**
	 * @param submitTer the submitTer to set
	 */
	public void setSubmitTer(String submitTer) {
		this.submitTer = submitTer;
	}
	/**
	 * @return the submitTime
	 */
	public String getSubmitTime() {
		return submitTime;
	}
	/**
	 * @param submitTime the submitTime to set
	 */
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	/**
	 * @return the recordList
	 */
	public String getRecordList() {
		return recordList;
	}
	/**
	 * @param recordList the recordList to set
	 */
	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}
	/**
	 * @return the wordUrl
	 */
	public String getwordName() {
		return wordName;
	}
	/**
	 * @param wordUrl the wordUrl to set
	 */
	public void setwordName(String wordName) {
		this.wordName = wordName;
	}
	/**
	 * @return the validEndTime
	 */
	public String getValidEndTime() {
		return validEndTime;
	}
	/**
	 * @param validEndTime the validEndTime to set
	 */
	public void setValidEndTime(String validEndTime) {
		this.validEndTime = validEndTime;
	}
	/**
	 * @return the instream
	 */
	public ByteArrayInputStream getInstream() {
		return instream;
	}
	/**
	 * @param instream the instream to set
	 */
	public void setInstream(ByteArrayInputStream instream) {
		this.instream = instream;
	}

	public String getCps1() {
		return cps1;
	}
	public void setCps1(String cps1) {
		this.cps1 = cps1;
	}
	public String getputonType() {
		return putonType;
	}
	public void setputonType(String putonType) {
		this.putonType = putonType;
	}
	public String getCps3() {
		return cps3;
	}
	public void setCps3(String cps3) {
		this.cps3 = cps3;
	}
	/**
	 * @return the ratioRebate
	 */
	public String getRatioRebate() {
		return ratioRebate;
	}
	/**
	 * @param ratioRebate the ratioRebate to set
	 */
	public void setRatioRebate(String ratioRebate) {
		this.ratioRebate = ratioRebate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
