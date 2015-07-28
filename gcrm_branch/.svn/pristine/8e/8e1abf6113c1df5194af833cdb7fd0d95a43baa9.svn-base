package com.baidu.gcrm.ad.decorators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.approval.service.IParticipantService;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ContentType;
import com.baidu.gcrm.ad.content.model.ModifyStatus;
import com.baidu.gcrm.ad.dao.AdvertiseMaterialRepository;
import com.baidu.gcrm.ad.material.service.IMaterialManageService;
import com.baidu.gcrm.ad.material.vo.MaterialContentVO;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterial.MaterialFileType;
import com.baidu.gcrm.ad.service.IAdvertiseQuotationService;
import com.baidu.gcrm.ad.web.vo.QuotationRecordVO;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.bpm.web.helper.AdPlanProcessStartBean;
import com.baidu.gcrm.bpm.web.helper.ParticipantBean;
import com.baidu.gcrm.bpm.web.helper.StartProcessBean;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;

@Component("PrepareParticipantsDecorator")
public class PrepareParticipantsDecorator implements PrepareStartAdProcessDecorator {
	@Autowired
	private IParticipantService participantService;
	
	@Autowired
	IMaterialManageService materialManageService;
	
	@Autowired
	private AdvertiseMaterialRepository adMaterialRepository;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserRightsService userRightsService;
	
	@Autowired
	private IAdvertiseQuotationService quotationService;
	
	@Override
	public void prepare(AdPlanProcessStartBean startBean, Long adSolutionId, List<AdSolutionContent> contents) {
		Set<String> platformIds = new HashSet<String>();
		Set<String> pmPlatformIds = new HashSet<String>();
		Set<String> depPlatformIds = new HashSet<String>();
		for (AdSolutionContent content : contents) {
			if (isContentNewOrChanged(content)) {
				platformIds.add(content.getProductId().toString());
				if(materialManageService.isMaterFullByPosMaterType(content)){
					pmPlatformIds.add(content.getProductId().toString());
				}
				if(needDeptAprrovel(content)){
					depPlatformIds.add(content.getProductId().toString());
				}
			}
		}
		List<ParticipantBean> participants = new ArrayList<ParticipantBean>();
		//设置PM
		ParticipantBean participant = new ParticipantBean();
		participant.setParticipantId(ParticipantConstants.pm.name());
		List<String> usernames = null;
		if(pmPlatformIds.size()>0){
			usernames = participantService.getUnamesByDescAndInKeysAndPartId(pmPlatformIds, DescriptionType.platform, ParticipantConstants.pm_leader.toString());
			if(usernames!=null && usernames.size()>0){
				usernames = userService.findUuapNameByName(usernames);
			}
		}else{
			usernames = new ArrayList<String>();
		}
		participant.setUsernames(usernames);
		participants.add(participant);
		//设置变现主管
		ParticipantBean participantCash = new ParticipantBean();
		participantCash.setParticipantId(ParticipantConstants.cash_leader.name());
		List<String> cashs = null;
		if(platformIds.size()>0){
			cashs = participantService.getUnamesByDescAndInKeysAndPartId(platformIds, DescriptionType.platform, ParticipantConstants.cash_leader.toString());
			if(cashs!=null && cashs.size()>0){
				cashs = userService.findUuapNameByName(cashs);
			}
		}
		if(cashs == null){
			cashs = new ArrayList<String>();
		}
		participantCash.setUsernames(cashs);
		participants.add(participantCash);
		// 设置变现主管上级
		ParticipantBean participantSuperior = new ParticipantBean();
		participantSuperior.setParticipantId(ParticipantConstants.starter_superior.name());
		List<String> superiors = new ArrayList<String>();
		if(isCashLeader()){
			List<User> users = userRightsService.findDirectLeaderByUcId(RequestThreadLocal.getLoginUserId());
			if(users != null && users.size()>0){
				superiors = new ArrayList<String>();
				superiors.add(users.get(0).getUuapName());
			}
		}
		participantSuperior.setUsernames(superiors);
		participants.add(participantSuperior);
		//设置部门总监
		ParticipantBean participantDept = new ParticipantBean();
		participantDept.setParticipantId(ParticipantConstants.country_leader.name());
		List<String> depts = null;
		if(depPlatformIds.size()>0){
			depts = participantService.getUnamesByDescAndInKeysAndPartId(depPlatformIds, DescriptionType.platform, ParticipantConstants.dept_manager.toString());
			if(depts!=null && depts.size()>0){
				depts = userService.findUuapNameByName(depts);
			}
		}else{
			depts = new ArrayList<String>();
		}
		participantDept.setUsernames(depts);
		participants.add(participantDept);
		startBean.setParticipants(participants);
	}
	
	private boolean isContentNewOrChanged(AdSolutionContent content) {
		ContentType contentType = content.getContentType();
		return ContentType.create.equals(contentType)
				|| (ContentType.update.equals(contentType) && ModifyStatus.MODIFYED.equals(content.getModifyStatus()));
	}
	
	private boolean isCashLeader(){
		List<RightsRole> roles = userRightsService.findUserRolesByUcId(
				RequestThreadLocal.getLoginUserId());
		if(roles != null && roles.size()>0){
			for(RightsRole role:roles){
				if(ParticipantConstants.cash_leader.name().equals(role.getRoleTag())){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean needDeptAprrovel(AdSolutionContent content){
		QuotationRecordVO vo = quotationService.findQuotationRecordByContent(content.getId());
		if (vo.isLessProductRatio() || 
				(vo.getDiscount() != null && Double.compare(vo.getDiscount(), 0.8d) < 0)) {
			return true;
		}
		return false;
	}
	
	//@Override
    public void prepare(StartProcessBean startBean, List<AdSolutionContent> contents) {
        Set<String> platformIds = new HashSet<String>();
        Set<String> siteIds = new HashSet<String>();

        for (AdSolutionContent content : contents) {
            platformIds.add(content.getProductId().toString());
            siteIds.add(content.getSiteId().toString());
        }
        List<ParticipantBean> participants = new ArrayList<ParticipantBean>();
        ParticipantBean participant4Pm = new ParticipantBean();
        participant4Pm.setParticipantId(ParticipantConstants.pm.name());
        List<String> usernames4Pm = null;
        if(platformIds.size()>0){
            usernames4Pm = participantService.getUnamesByDescAndInKeysAndPartId(platformIds, DescriptionType.platform, ParticipantConstants.pm_leader.toString());
            if(usernames4Pm!=null && usernames4Pm.size()>0){
                usernames4Pm = userService.findUuapNameByName(usernames4Pm);
            }
        }else{
            usernames4Pm = new ArrayList<String>();
        }
        participant4Pm.setUsernames(usernames4Pm);
        participants.add(participant4Pm);
        
        ParticipantBean participantAgent = new ParticipantBean();
        participantAgent.setParticipantId(ParticipantConstants.countryAgent.name());
        List<String> usernames4Sits = null;
        if(siteIds.size()>0){
            usernames4Sits = participantService.getUnamesByDescAndInKeysAndPartId(siteIds, DescriptionType.site, ParticipantConstants.countryAgent.toString());
            if(usernames4Sits!=null && usernames4Sits.size()>0){
                usernames4Sits = userService.findUuapNameByName(usernames4Sits);
            }
        }else{
            usernames4Sits = new ArrayList<String>();
        }
        participantAgent.setUsernames(usernames4Sits);
        participants.add(participantAgent);
        
        startBean.setParticipants(participants);
    }
}
