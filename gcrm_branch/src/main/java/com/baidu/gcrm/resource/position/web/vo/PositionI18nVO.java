package com.baidu.gcrm.resource.position.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.position.service.impl.InfoDecorator;
import com.baidu.gcrm.resource.position.service.impl.PositionInfoDecorator;

public class PositionI18nVO {
    
    public PositionI18nVO(){}
    
    public PositionI18nVO(String enName, String cnName) {
        this.enName = enName;
        this.cnName = cnName;
    }
    
    private String cnName;
    
    private String enName;
    
    private String cnExtraName;
    
    private String enExtraName;

    public String getCnName() {
        if (cnName != null && cnName.indexOf(InfoDecorator.SPLIT_FLAG) != -1) {
            return cnName.replace(InfoDecorator.SPLIT_FLAG, "_");
        }
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        if (enName != null && enName.indexOf(InfoDecorator.SPLIT_FLAG) != -1) {
            return enName.replace(InfoDecorator.SPLIT_FLAG, "_");
        }
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
    
    public List<LocaleVO> peocessLocaleVO(PositionInfoDecorator decorator) {
        List<LocaleVO> voList = new ArrayList<LocaleVO> ();
        LocaleVO enLocaleVO = new LocaleVO();
        enLocaleVO.setLocale(LocaleConstants.en_US);
        enLocaleVO.setValue(getEnName());
        enLocaleVO.setExtraValue(decorator.buildI18nEnExtraInfo().append(InfoDecorator.SPLIT_FLAG).append(getEnName()).toString());
        
        LocaleVO cnLocaleVO = new LocaleVO();
        cnLocaleVO.setLocale(LocaleConstants.zh_CN);
        cnLocaleVO.setValue(getCnName());
        cnLocaleVO.setExtraValue(decorator.buildI18nCnExtraInfo().append(InfoDecorator.SPLIT_FLAG).append(getCnName()).toString());
        
        voList.add(enLocaleVO);
        voList.add(cnLocaleVO);
        return voList;
    }

    public String getCnExtraName() {
        return cnExtraName;
    }

    public void setCnExtraName(String cnExtraName) {
        this.cnExtraName = cnExtraName;
    }

    public String getEnExtraName() {
        return enExtraName;
    }

    public void setEnExtraName(String enExtraName) {
        this.enExtraName = enExtraName;
    }
    
}
