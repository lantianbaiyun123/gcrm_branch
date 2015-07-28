package com.baidu.gcrm.ad;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.alibaba.fastjson.JSONObject;
import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.TestUtils;
import com.baidu.gcrm.ad.content.web.AdSolutionContentAction;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonListBean;
import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.valuelistcache.model.BillingModel;

@Ignore
public class AdSolutionContentActionTest extends BaseTestContext {
	
	@Autowired
	AdSolutionContentAction adSolutionContentAction;
	
	@Before
	public void init() throws Exception {
		TestUtils.initDatabase(dataSource, "datas/g_quotation.xml");
	}
	
	@Rollback
	public void testSaveContent(){
		AdvertiseQuotation testQuotation = getPatternData();
		AdSolutionContentView adSolutionContentView = new AdSolutionContentView();
		adSolutionContentView.setAdvertiseQuotation(testQuotation);
		BindingResult bindingResult = new BeanPropertyBindingResult(adSolutionContentView, "quotation");
		JsonBean<AdSolutionContentView> json = adSolutionContentAction.saveAdsolutionContent(adSolutionContentView , bindingResult,  new MockHttpServletRequest());
		System.out.println("=====json:"+JSONObject.toJSONString(json));
		Assert.isNull(bindingResult);
		Assert.notEmpty(bindingResult.getModel());
	}
	
	public void testGetBillModels(){
		JsonBean<JsonListBean<BillingModel>> models = adSolutionContentAction.queryBillModels();
		System.out.println("=====json:"+JSONObject.toJSONString(models));
	}
	
	@Test
	public void testQueryQuotationPattern(){
		Quotation quotation = new Quotation();
		quotation.setAdvertisingPlatformId(2l);
		quotation.setSiteId(6l);
		JsonBean<JsonListBean<Quotation>> json = adSolutionContentAction.queryQuotationPattern(quotation);
		System.out.println("=====json:"+JSONObject.toJSONString(json));
		quotation.setBillingModelId(1l);
		json = adSolutionContentAction.queryQuotationPattern(quotation);
		System.out.println("=====json:"+JSONObject.toJSONString(json));
	}
	
	private AdvertiseQuotation getPatternData(){
		AdvertiseQuotation testQuotation = new AdvertiseQuotation();
		testQuotation.setAdSolutionContentId(3l);
		testQuotation.setPriceType(PriceType.ratio);
		testQuotation.setBillingModelId(1l);
		testQuotation.setProductRatioMine(40d);
		testQuotation.setProductRatioCustomer(50d);
		testQuotation.setProductRatioCustomer(10d);
		testQuotation.setRatioMine(60d);
		testQuotation.setRatioCustomer(30d);
		testQuotation.setRatioThird(10d);
		//testQuotation.setRatioCondition("aaaaaaaaaaaaaaaa");
		testQuotation.setRatioConditionDesc("dddddddddddd");
		testQuotation.setReachEstimate(true);
		return testQuotation;
	}
	
	@SuppressWarnings("unused")
    private AdvertiseQuotation getCPAData(){
		AdvertiseQuotation testQuotation = new AdvertiseQuotation();
		testQuotation.setAdSolutionContentId(3l);
		testQuotation.setPriceType(PriceType.unit);
		testQuotation.setBillingModelId(1l);
		testQuotation.setCurrencyType(1);
		testQuotation.setPublishPrice(5.1);
		testQuotation.setCustomerQuote(1234.33);
		testQuotation.setTrafficAmount(200000l);
		testQuotation.setClickAmount(123456l);
		testQuotation.setDiscount(0.9);
		testQuotation.setBudget(123456789.11);
		testQuotation.setTotalPrice(300023.332);
		testQuotation.setProductRatioMine(0.4);
		testQuotation.setProductRatioCustomer(0.6);
		testQuotation.setRatioMine(0.6);
		testQuotation.setRatioCustomer(0.3);
		testQuotation.setRatioThird(0.1);
		//testQuotation.setRatioCondition("aaaaaaaaaaaaaaaa");
		testQuotation.setRatioConditionDesc("dddddddddddd");
		testQuotation.setReachEstimate(true);
		testQuotation.setDailyAmount(123456l);
		testQuotation.setTotalAmount(8888888l);
		return testQuotation;
	}
}
