package com.baidu.gcrm.i18n.helper;

import java.text.Collator;
import java.util.Comparator;

import com.baidu.gcrm.i18n.model.BaseI18nModel;

public class BaseI18nModelComparator implements Comparator<BaseI18nModel> {

    public enum ComparatorWay {
        code, name;
    }

    private ComparatorWay compWay = ComparatorWay.name;

    private Comparator<Object> currComparator = Collator.getInstance(java.util.Locale.CHINA);

    public BaseI18nModelComparator(ComparatorWay compWay) {
        this.compWay = compWay;
    }

    @Override
    public int compare(BaseI18nModel o1, BaseI18nModel o2) {

        if (ComparatorWay.code.equals(compWay)) {
            Long o1Value = o1.getId();
            Long o2Value = o2.getId();

            if ((o1Value == null && o2Value == null) || o1Value == o2Value) {
                return 0;
            } else if ((o1Value == null && o2Value != null) || o1Value < o2Value) {
                return -1;
            } else if (o1Value != null && o2Value == null || o1Value > o2Value) {
                return 1;
            } else {
                return 1;
            }
        } else {
            String o1Value = o1.getI18nName();
            String o2Value = o2.getI18nName();

            if (o1Value == null && o2Value == null) {
                return 0;
            } else if (o1Value == null && o2Value != null) {
                return -1;
            } else if (o1Value != null && o2Value == null) {
                return 1;
            } else {
                return currComparator.compare(o1Value, o2Value);
            }
        }
    }
}
