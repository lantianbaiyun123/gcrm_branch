package com.baidu.gcrm.resource.site.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;

@Controller
@RequestMapping("/site")
public class SiteAction extends ControllerHelper{
    
    @Autowired
    ISiteService siteService;
    
    @RequestMapping("/queryByAdPlatform/{adPlatformId}")
    @ResponseBody
    public List<Site> queryByAdPlatform(@PathVariable("adPlatformId") Long adPlatformId) {
        List<Site> siteList = siteService.findSiteByAdPlatform(adPlatformId, currentLocale);
        return siteList;
    }
}
