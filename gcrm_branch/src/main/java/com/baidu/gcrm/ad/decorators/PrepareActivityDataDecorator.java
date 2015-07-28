package com.baidu.gcrm.ad.decorators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ContentType;
import com.baidu.gcrm.ad.content.model.ModifyStatus;
import com.baidu.gcrm.ad.service.IAdvertiseQuotationService;
import com.baidu.gcrm.ad.web.vo.QuotationRecordVO;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.bpm.web.helper.AdPlanProcessStartBean;
import com.baidu.gcrm.bpm.web.helper.StartProcessBean;
import com.baidu.gcrm.common.auth.RequestThreadLocal;

@Component("PrepareActivityDataDecorator")
public class PrepareActivityDataDecorator implements PrepareStartAdProcessDecorator {
	@Autowired
	private IAdvertiseQuotationService quotationService;
	@Autowired
	private IUserRightsService userRightsService;
	
	private static final String TRUE = "1";
	private static final String FALSE = "0";

	@Override
	public void prepare(AdPlanProcessStartBean startBean, Long adSolutionId, List<AdSolutionContent> contents) {
		setDefaultValue(startBean);
		
		List<QuotationRecordVO> vos = new ArrayList<QuotationRecordVO>();
		for (AdSolutionContent content : contents) {
			if (!isContentNewOrChanged(content)) {
				continue;
			}
			QuotationRecordVO vo = quotationService.findQuotationRecordByContent(content.getId());
			vos.add(vo);
		}
		
		for (QuotationRecordVO vo : vos) {
			if (!vo.isRecord()) {
				startBean.setIsWithoutRecord(TRUE);
			}
			
			if (vo.isLessProductRatio()) {
				startBean.setIsActualRatioLessThanMine(TRUE);
			}
			
			Double discount = vo.getDiscount();
			if (discount == null) {
				continue;
			}
			if (Double.compare(discount, 0.5d) < 0) {
                startBean.setIsLessThanFiftyPercent(TRUE);
                startBean.setIsLessThanEightyPercent(TRUE);
            } else if (Double.compare(discount, 0.8d) < 0) {
                startBean.setIsLessThanEightyPercent(TRUE);
            }
		}
		
		startBean.setNeedSuperiorApproval(isCashLeader() ? TRUE : FALSE);
	}
	
	private void setDefaultValue(AdPlanProcessStartBean startBean) {
		startBean.setIsLessThanFiftyPercent(FALSE);
		startBean.setIsLessThanEightyPercent(FALSE);
		startBean.setIsActualRatioLessThanMine(FALSE);
		startBean.setIsWithoutRecord(FALSE);
		startBean.setIsInsertOrder(FALSE);
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

    @Override
    public void prepare(StartProcessBean startBean, List<AdSolutionContent> contents) {
    	return;
    }

}
