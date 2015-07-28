package com.baidu.gcrm.resource.adplatform.web;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.ad.content.web.helper.BaseI18nModelComparator;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.auth.service.IUserDataRightService;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform.AdPlatformBusinessType;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform.AdPlatformStatus;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.adplatform.service.IAdPlatformService;
import com.baidu.gcrm.resource.adplatform.service.IAdPlatformSiteRelationService;
import com.baidu.gcrm.resource.adplatform.web.validator.AdPlatformValidator;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;

@Controller
@RequestMapping("/adPlatform")
public class AdPlatformAction extends ControllerHelper {

    @Autowired
    IAdPlatformService adPlatformService;

    @Autowired
    AdvertisingPlatformServiceImpl adPlatformCacheService;

    @Autowired
    ISiteService siteService;

    @Autowired
    I18nKVService i18nKVService;

    @Autowired
    IUserDataRightService userDataRightService;

    @Autowired
    private IAdPlatformSiteRelationService platformSiteService;

    @RequestMapping("/queryAdPlatform")
    @ResponseBody
    public JsonBean<List<AdvertisingPlatform>> queryAdPlatform(
            @RequestParam(value = "businessType", defaultValue = "0") Integer businessType) {
        AdPlatformBusinessType filterBusinessType = null;
        if (businessType == null) {
            filterBusinessType = AdPlatformBusinessType.liquidate;
        } else {
            filterBusinessType = AdPlatformBusinessType.valueOf(businessType);
        }
        List<AdvertisingPlatform> adPlatformList =
                userDataRightService.getPlatformListByUcId(this.getUserId(), currentLocale);
        if (CollectionUtils.isEmpty(adPlatformList)) {
            return JsonBeanUtil.convertBeanToJsonBean(null);
        }
        List<AdvertisingPlatform> realAdplatformList = new LinkedList<AdvertisingPlatform>();
        for (AdvertisingPlatform dbAdvertisingPlatform : adPlatformList) {
            Integer status = dbAdvertisingPlatform.getStatus();
            if (status == null || AdPlatformStatus.enable.ordinal() != status.intValue()) {
                continue;
            }
            Integer dbBusinessType = dbAdvertisingPlatform.getBusinessType();
            if (dbBusinessType != null && dbBusinessType.intValue() == filterBusinessType.ordinal()) {
                realAdplatformList.add(dbAdvertisingPlatform);
            }
        }
        Collections.sort(realAdplatformList, new BaseI18nModelComparator());
        return JsonBeanUtil.convertBeanToJsonBean(realAdplatformList);
    }

    @RequestMapping("/querySiteByAdPlatformId")
    @ResponseBody
    public JsonBean<List<Site>> querySiteByProductId(@RequestParam("adPlatformId") Long adPlatformId) {
        List<Site> siteList =
                userDataRightService.getSiteListByUcIdAndPlatformId(this.getUserId(), adPlatformId, currentLocale);
        Collections.sort(siteList, new BaseI18nModelComparator());
        return JsonBeanUtil.convertBeanToJsonBean(siteList);
    }

    @RequestMapping("/query")
    @ResponseBody
    public JsonBean<List<AdPlatform>> query() {
        List<AdPlatform> adPlatformList = adPlatformService.findAll(currentLocale, true);
        return JsonBeanUtil.convertBeanToJsonBean(adPlatformList);
    }

    @RequestMapping("/{platformId}/sites")
    @ResponseBody
    public JsonBean<List<AdPlatformSiteRelation>> findAccessSites4Platform(@PathVariable Long platformId) {

        return JsonBeanUtil.convertBeanToJsonBean(adPlatformService.findAccessSites4Platform(platformId,
                getCurrentLocale()));
    }

    @RequestMapping("/preSave")
    @ResponseBody
    public JsonBean<AdPlatform> preSave(AdPlatform adPlatform) {
        AdPlatform currAdPlatform = adPlatformService.findById(adPlatform.getId(), currentLocale);
        return JsonBeanUtil.convertBeanToJsonBean(currAdPlatform);
    }

    @RequestMapping("/save")
    @ResponseBody
    public JsonBean<AdPlatform> save(@RequestBody AdPlatform adPlatform, BindingResult bindingResult) {
        ValidationUtils.invokeValidator(new AdPlatformValidator(), adPlatform, bindingResult);
        if (bindingResult.hasErrors()) {
            return JsonBeanUtil.convertBeanToJsonBean(adPlatform, super.collectErrors(bindingResult));
        }
        Long id = adPlatform.getId();
        if (id == null) {
            generatePropertyForCreate(adPlatform);
        } else {
            generatePropertyForUpdate(adPlatform);
        }
        adPlatformService.saveAdPlatformAndRelation(adPlatform);
        return JsonBeanUtil.convertBeanToJsonBean(adPlatform);

    }

    @RequestMapping("/queryUsedCount/{id}")
    @ResponseBody
    public JsonBean<Long> queryUsedCount(@PathVariable("id") Long id) {
        Long count = adPlatformService.findUsedAmountById(id);
        return JsonBeanUtil.convertBeanToJsonBean(count);
    }

    @RequestMapping("/disable/{id}")
    @ResponseBody
    public JsonBean<AdPlatform> disable(@PathVariable("id") Long id) {
        AdPlatform dbAdPlatform = adPlatformService.findById(id);
        if (AdPlatformStatus.disable == dbAdPlatform.getStatus()) {
            return JsonBeanUtil.convertBeanToJsonBean(null);
        }
        dbAdPlatform.setStatus(AdPlatformStatus.disable);
        generatePropertyForUpdate(dbAdPlatform);
        adPlatformService.closeAdPlatform(dbAdPlatform);
        return JsonBeanUtil.convertBeanToJsonBean(dbAdPlatform);

    }

    @RequestMapping("/enable/{id}")
    @ResponseBody
    public JsonBean<AdPlatform> enable(@PathVariable("id") Long id) {
        AdPlatform dbAdPlatform = adPlatformService.findById(id);
        if (AdPlatformStatus.enable == dbAdPlatform.getStatus()) {
            return JsonBeanUtil.convertBeanToJsonBean(null);
        }
        dbAdPlatform.setStatus(AdPlatformStatus.enable);
        generatePropertyForUpdate(dbAdPlatform);
        adPlatformService.openAdPlatform(dbAdPlatform);
        return JsonBeanUtil.convertBeanToJsonBean(dbAdPlatform);

    }

}
