package com.baidu.gcrm.customer.web.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.contact.web.validator.ContactPersonValidator;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.web.helper.CustomerBean;
import com.baidu.gcrm.customer.web.helper.CustomerState;
import com.baidu.gcrm.opportunity.model.Opportunity;
import com.baidu.gcrm.qualification.model.Qualification;
import com.baidu.gcrm.qualification.web.QualificationValidator;

public class CustomerSubmitValidator extends CustomerBaseValidator implements Validator {
    private final static int CONTACT_LIMIT = 10;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CustomerBean.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerBean customerBean = (CustomerBean) target;

        Customer customer = ServiceBeanFactory.getCustomerService().findById(customerBean.getCustomer().getId());
        customerBean.setCustomer(customer);

        ValidationUtils.invokeValidator(new CustomerValidator(), customer, errors);
        ValidationUtils.invokeValidator(new CustomerTypeChangeValidator(), customer, errors);
        if (customer.getCompanyStatus() == CustomerState.disabled.ordinal()) {
            errors.rejectValue("customer.approvalStatus", "customer.disabled.submit.forbidden");
        }
        // 驗證名稱和運營許可證 add by cch
        if (errors.hasErrors()) {
            return;
        }
        validatorNameAndLicense(customer, errors);

        Long customerNumber = customer.getId();
        List<ContactPerson> contacts = ServiceBeanFactory.getContactService().findContactsByCustomerNumber(
                customerNumber);
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

        Opportunity opportunity = ServiceBeanFactory.getOpportunityService().findOpportunityByCustomerNumber(
                customerNumber);
        if (opportunity != null) {
            Double budget = opportunity.getBudget();
            if (budget != null && budget.doubleValue() < 0) {
                errors.rejectValue("opportunity.budget", "opportunity.budget.number");
            }
        }

        Qualification qualification = ServiceBeanFactory.getQualificationService().findByCustomerNumber(customerNumber);
        if (qualification != null) {
            ValidationUtils.invokeValidator(new QualificationValidator(), qualification, errors);
        }
    }
}
