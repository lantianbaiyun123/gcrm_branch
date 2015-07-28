package com.baidu.gcrm.personalpage.service.impl;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.occupation.dao.IPositionOccupationRepositoryCustom;
import com.baidu.gcrm.personalpage.service.IOperationMailService;
import com.baidu.gcrm.personalpage.web.vo.ChannelOperationVO;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gcrm.ws.cms.impl.CMSRequestFacade;
@Service

public class OperationMailServiceImpl implements IOperationMailService {
    private static Logger logger = LoggerFactory.getLogger(OperationMailServiceImpl.class);
	@Autowired
	AdvertisingPlatformServiceImpl adPlatformService;
	@Autowired
	private ISiteService siteService;
    @Autowired
    private IPositionService positionService;  
	@Autowired
	IPositionOccupationRepositoryCustom positionOccupationDao;
	@Autowired
    I18nKVService i18nService;
	@Override
	public void StatisticsPositonByChannel() {		
		DecimalFormat df2 = new DecimalFormat("#.##");
		List<ChannelOperationVO> voList=positionOccupationDao.getPositionFull();
		List<ChannelOperationVO> vo2List=new ArrayList<ChannelOperationVO>();
		if (CollectionUtils.isNotEmpty(voList)){			
			for(ChannelOperationVO voBean:voList){
				try{
				generatePlatSiteNames(voBean,LocaleConstants.zh_CN);
				}catch (Exception e) {
					logger.error("siteId or platId not found,siteId:{},platId:{}",voBean.getSiteId(),voBean.getPlatformId());
					continue;
				}
				List<Position> positionList=positionService.findByIndexStrLikeAndTypeAndStatus(voBean.getChannelId(), voBean.getIndexStr(),PositionType.position,PositionStatus.enable);
				if(CollectionUtils.isEmpty(positionList)){
		    		continue;
		    	}
		    	List<Long> positionIdList = new ArrayList<Long>();
		    	for(Position position : positionList){
		    		positionIdList.add(position.getId());
		    	}
		    	Date currentDate = DateUtils.getString2Date(voBean.getDate());		    	
				Long salesAmount=Long.valueOf(voBean.getSalesAmount());
				Long soldAmount = positionService.findPositionOperation(positionIdList, currentDate);
				voBean.setSoldAmount(soldAmount.toString());
		        if(salesAmount.intValue() == 0){
		        	voBean.setRatio("0");
		        }else{
		        	Double ratio = soldAmount * 100.00/salesAmount;
		        	voBean.setRatio(df2.format(ratio));
		        }
		        vo2List.add(voBean);
			}			
		}
		MailHelper.sendPositionFullMail(vo2List);	
	}
	
	public void generatePlatSiteNames(ChannelOperationVO voBean,LocaleConstants currentLocale)
	{   
		 AdvertisingPlatform advertisingPlatform= adPlatformService.getByIdAndLocale(voBean.getPlatformId(),currentLocale);
		 if(advertisingPlatform!=null) {
			 voBean.setPlatName(advertisingPlatform.getI18nName());
		 }
		 if(voBean.getSiteId()!=null){
		 Site temSite=siteService.findSiteAndI18nById(voBean.getSiteId(),currentLocale);
		 if(temSite!=null) {
			 voBean.setSiteName(temSite.getI18nName());
		 }
		 }
		 String channelName=i18nService.getI18Info(Position.class, voBean.getChannelId(), currentLocale);
		  
		 if(channelName!=null){
			 voBean.setChannelName(channelName);
		 }
	}


}
