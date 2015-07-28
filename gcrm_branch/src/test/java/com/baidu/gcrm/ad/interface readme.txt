========报价投放量测试接口 begin=========
1）获取计费方式接口
${baseURL}/adsolution/content/queryBillModels
2）获取刊例价、分成比例
${baseURL}/adsolution/content/queryQuotationPattern
	说明:
	    参数Quotation
	    获取刊例价需要设置
	    advertisingPlatformId：投放平台ID
	    siteId：站点ID
	    billingModelId：计费方式ID
	  获取分成需要设置  投放平台ID与站点ID
3）获取广告内容的报价投放量
${baseURL}/adsolution/content/queryAdContentQuotation/{contentId}
	contentId：广告内容ID
4）保存报价投放量
${baseURL}/adsolution/content/saveAdsolutionContent
   报价投放量作为广告内容一部分，需要和其他信息一起存储，建议先测试报价投放量的后台校验，存储等其他部分信息完成在测试
========报价投放量测试接口  end=========
