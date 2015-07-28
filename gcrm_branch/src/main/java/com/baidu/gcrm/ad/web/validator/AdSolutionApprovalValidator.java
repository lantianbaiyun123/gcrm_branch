package com.baidu.gcrm.ad.web.validator;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.ModifyStatus;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ContentType;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.resource.position.model.Position;

public class AdSolutionApprovalValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(AdvertiseSolution.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null) {
            return;
        }
        AdvertiseSolution advertiseSolution = (AdvertiseSolution) target;
        Long adSolutionId = advertiseSolution.getId();
        List<AdSolutionContent> adContents = ServiceBeanFactory.getAdSolutionContentService().findByAdSolutionId(
                adSolutionId);
        if (CollectionUtils.isEmpty(adContents)) {
            errors.reject("advertise.content.null");
            return;
        }

        validateContract(errors, adSolutionId, adContents);
        validatePosition(errors, adContents);
        validateIfHasChanges(errors, adContents);
    }

    private void validateIfHasChanges(Errors errors, List<AdSolutionContent> adContents) {
        for (AdSolutionContent content : adContents) {
            if (isNewContent(content) || isModifiedContent(content)) {
                return;
            }
        }
        errors.reject("advertise.solution.notchange.error");
    }
    
    private boolean isModifiedContent(AdSolutionContent content) {
        return ContentType.update.equals(content.getContentType()) && ModifyStatus.MODIFYED.equals(content
                .getModifyStatus());
    }

    private boolean isNewContent(AdSolutionContent content) {
        return !ContentType.update.equals(content.getContentType());
    }

    private void validatePosition(Errors errors, List<AdSolutionContent> adContents) {
        Map<Long, Position> disabledPositions = ServiceBeanFactory.getPositionService().findDisabledPositions();
        for (AdSolutionContent content : adContents) {
            Long positionId = content.getPositionId();
            if (disabledPositions.get(positionId) != null) {
                String[] args = new String[]{disabledPositions.get(positionId).getName()};
                errors.reject("advertise.content.position.disabled", args, null);
            }
        }
    }

    private void validateContract(Errors errors, Long adSolutionId, List<AdSolutionContent> adContents) {
        Contract contract = ServiceBeanFactory.getContractService().findByAdSolutionId(adSolutionId);
        if (contract == null) {
            return;
        }
        Date contractEndDate = contract.getEndDate();
        Date contractStartDate = contract.getBeginDate();
        String contractErrorCode = "occupation.compare.contract.invalid";
        for (AdSolutionContent temAdSolutionContent : adContents) {
            List<DatePeriod> periods = DatePeriodHelper.getDatePeriods(temAdSolutionContent.getPeriodDescription());
            Object[] errorArgs = new Object[]{temAdSolutionContent.getId()};
            if (!CollectionUtils.isEmpty(periods)) {
                for (DatePeriod temDatePeriod : periods) {
                    Date temFrom = temDatePeriod.getFrom();
                    Date temTo = temDatePeriod.getTo();

                    if ((contractStartDate != null && temFrom.before(contractStartDate))
                            || (contractEndDate != null && temFrom.after(contractEndDate))) {
                        errors.reject(contractErrorCode, errorArgs, null);
                        break;
                    } else if ((contractStartDate != null && temTo.before(contractStartDate))
                            || (contractEndDate != null && temTo.after(contractEndDate))) {
                        errors.reject(contractErrorCode, errorArgs, null);
                        break;
                    }
                }
            }
        }
    }
}
