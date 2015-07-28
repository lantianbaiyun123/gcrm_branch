package com.baidu.gcrm.quote.web.utils;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Comparator;
import java.util.Locale;

import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;

public class AdvertisingPlatformComparator implements Comparator<AdvertisingPlatform> {

	/**
	 * 排序比较
	 */
	@Override
	public int compare(AdvertisingPlatform o1, AdvertisingPlatform o2) {
		RuleBasedCollator collator = (RuleBasedCollator)Collator.getInstance(Locale.CHINA);
		if(o1 == null || o2 == null){
			return 0;
		}
		return collator.compare(o1.getI18nName(), o2.getI18nName());
	}

}
