package com.baidu.gcrm.ad.content.web.helper;

import java.text.Collator;
import java.util.Comparator;

import com.baidu.gcrm.i18n.model.BaseI18nModel;

public class BaseI18nModelComparator implements Comparator<BaseI18nModel>{
    
    Comparator<Object> currComparator = Collator.getInstance(java.util.Locale.CHINA);

    @Override
    public int compare(BaseI18nModel o1, BaseI18nModel o2) {
        String o1Name = o1.getI18nName();
        String o2Name = o2.getI18nName();
        if (o1Name == null && o2Name == null) {
            return 0;
        } else if (o1Name == null && o2Name != null) {
            return -1;
        } else if (o1Name != null && o2Name == null) {
            return 1;
        } else {
            return currComparator.compare(o1Name, o2Name);
        }
    }

}
