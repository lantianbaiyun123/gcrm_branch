package com.baidu.gcrm.ad.content.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.baidu.gcrm.ad.content.dao.IAdSolutionContentApplyRepository;
import com.baidu.gcrm.ad.content.model.AdContentApplyApprovalRecord;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply.ApprovalStatus;
import com.baidu.gcrm.common.LocaleConstants;

@Repository
public class AdSolutionContentApplyRepoImpl implements
		IAdSolutionContentApplyRepository {

    @PersistenceContext
	private EntityManager entityManager;
	@Override
	public void saveAdContentApply(AdSolutionContentApply solutionContentApply) {
		if(solutionContentApply.getId()==null){
		entityManager.persist(solutionContentApply);
		}else{
			entityManager.merge(solutionContentApply);
		}
	}
	
	public AdSolutionContentApply findAdContentApply(Long id) {
		return entityManager.find(AdSolutionContentApply.class, id);
	}
	public void saveAdContentApplyApproval(AdContentApplyApprovalRecord adContentApplyApprovalRecord) {
		entityManager.persist(adContentApplyApprovalRecord);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findAdContentApplyApprovalRecords(String processDefId ,LocaleConstants local ,Long onlineApplyId) {
		String JQL="SELECT R,U.realname,C.activityName FROM AdContentApplyApprovalRecord R,User U,ActivityNameI18n C WHERE R.createOperator = U.ucid AND  R.actDefId = C.activityId AND C.processDefId =?1 AND   C.locale = ?2 AND R.adContentApplyId =?3 order by R.createTime";
		List records=entityManager.createQuery(JQL).setParameter(1, processDefId).setParameter(2, local).setParameter(3, onlineApplyId).getResultList();
        return records;
	}

	@SuppressWarnings("rawtypes")
	public AdSolutionContentApply findAdContentApplyByConId(Long contentId) {
		String JQL="SELECT R FROM AdSolutionContentApply R WHERE R.adSolutionContentId=?1";
		List records= entityManager.createQuery(JQL).setParameter(1, contentId).getResultList();
		if(records!=null&&records.size()>0){
			return (AdSolutionContentApply)records.get(0);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<AdSolutionContentApply> findByContractNumber(String contractNumber) {
		String JQL="SELECT R FROM AdSolutionContentApply R WHERE (R.approvalStatus=?1 or R.approvalStatus=?2) and R.contractNumber=?3";
		List records= entityManager.createQuery(JQL).setParameter(1, ApprovalStatus.approving).setParameter(2, ApprovalStatus.approved).setParameter(3, contractNumber).getResultList();
		return records;
	}
	
}
