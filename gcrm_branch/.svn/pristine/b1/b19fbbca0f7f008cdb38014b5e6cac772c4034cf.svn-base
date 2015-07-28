package com.baidu.gcrm.customer.web.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.contact.web.validator.ContactPersonValidator;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.web.helper.CustomerBean;
import com.baidu.gcrm.opportunity.model.Opportunity;
import com.baidu.gcrm.qualification.model.CustomerResource;
import com.baidu.gcrm.qualification.model.Qualification;

/**
 * 项目名称：gcrm 类名称：CustomerAddValidator 类描述： 客户信息新增验证 创建人：chenchunhui01
 * 创建时间：2014年4月15日 上午9:58:07 修改人：chenchunhui01 修改时间：2014年4月15日 上午9:58:07 修改备注：
 * 
 * @version
 * 
 */
public class CustomerAddValidator extends CustomerBaseValidator implements Validator {
    private final static int CONTACT_LIMIT = 10;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CustomerBean.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerBean customerBean = (CustomerBean) target;

        ValidationUtils.invokeValidator(new CustomerValidator(), customerBean.getCustomer(), errors);
        // 验证基础信息不通过，就不在继续验证重复性
        if (errors.hasErrors()) {
            return;
        }

        Customer customer = customerBean.getCustomer();
        // 驗證名稱和運營許可證 add by cch
        validatorNameAndLicense(customer, errors);
        //验证代理区域与代理国家对应关系
        validatorAgentTypeAndAgentCountry(customer, errors);
        List<ContactPerson> contacts = customerBean.getContacts();
        if (contacts != null && contacts.size() > CONTACT_LIMIT) {
            errors.rejectValue("", "contact.size.invalid");
        }

        if (CollectionUtils.isNotEmpty(contacts)) {
            int i = 0;
            for (ContactPerson temContactPerson : contacts) {
                ContactPersonValidator.validateSingleContactPerson(errors, new StringBuilder("contacts[").append(i)
                        .append("].").toString(), temContactPerson, true);
                i++;
            }
        }

        Opportunity opportunity = customerBean.getOpportunity();
        if (opportunity != null) {
            Double budget = opportunity.getBudget();
            if (budget != null && budget.doubleValue()< 0) {
                errors.rejectValue("opportunity.budget", "opportunity.budget.number");
            }
        }
        // weichengke add
        Qualification qualification = customerBean.getQualification();

        if (qualification != null) {

            if (StringUtils.isNotBlank(qualification.getParterTop1())&&qualification.getParterTop1().length() > 128) {
                errors.rejectValue("qualification.parterTop1", "qualification.parterTop.length.toolong");
            }
            if (StringUtils.isNotBlank(qualification.getParterTop2())&&qualification.getParterTop2().length() > 128) {
                errors.rejectValue("qualification.parterTop2", "qualification.parterTop.length.toolong");
            }
            if (StringUtils.isNotBlank(qualification.getParterTop3())&&qualification.getParterTop3().length() > 128) {
                errors.rejectValue("qualification.parterTop3", "qualification.parterTop.length.toolong");
            }
            if (StringUtils.isNotBlank(qualification.getPerformanceHighlights())&&qualification.getPerformanceHighlights().length() > 512) {
                errors.rejectValue("qualification.performanceHighlights",
                        "qualification.performanceHighlights.length.toolong");
            }

            List<CustomerResource> resources = qualification.getCustomerResources();
            if(resources ==null || resources.size() ==0){
                return;
            }
            
            int index = 0;
            for (CustomerResource resource : qualification.getCustomerResources()) {
                if (StringUtils.isNotBlank(resource.getAdvertisersCompany1())&&resource.getAdvertisersCompany1().length() > 128) {
                    errors.rejectValue("qualification.customerResources[" + index + "].advertisersCompany1",
                            "costomerResource.advertisersCompany.length.toolong");
                }
                if (StringUtils.isNotBlank(resource.getAdvertisersCompany2())&&resource.getAdvertisersCompany2().length() > 128) {
                    errors.rejectValue("qualification.customerResources[" + index + "].advertisersCompany2",
                            "costomerResource.advertisersCompany.length.toolong");
                }
                if (StringUtils.isNotBlank(resource.getAdvertisersCompany3())&&resource.getAdvertisersCompany3().length() > 128) {
                    errors.rejectValue("qualification.customerResources[" + index + "].advertisersCompany3",
                            "costomerResource.advertisersCompany.length.toolong");
                }
                if (StringUtils.isNotBlank(resource.getIndustry())&&resource.getIndustry().length() > 128) {
                    errors.rejectValue("qualification.customerResources[" + index + "].industry",
                            "costomerResource.industry.length.toolong");
                }
                index++;
            }
        }
    }
}
