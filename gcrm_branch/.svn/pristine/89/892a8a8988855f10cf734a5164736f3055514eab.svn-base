package com.baidu.gcrm.customer.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.web.helper.CompanySize;
import com.baidu.gcrm.customer.web.helper.CustomerType;

public class CustomerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Customer.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;

    //    ValidationUtils.rejectIfEmpty(errors, "companyName", "customer.companyName.required");
     //   ValidationUtils.rejectIfEmpty(errors, "companySize", "customer.companySize.required");
        
        CompanySize companySize = customer.getCompanySize();
        if(companySize ==null){
            errors.rejectValue("customer.companySize", "customer.companySize.required");
        }
        
        ValidationUtils.rejectIfEmpty(errors, "customer.businessType", "customer.businessType.required");
     //ValidationUtils.rejectIfEmpty(errors, "customer.registerTime", "customer.registerTime.required");
        ValidationUtils.rejectIfEmpty(errors, "customer.belongSales", "customer.belongSales.required");
        ValidationUtils.rejectIfEmpty(errors, "customer.country", "customer.country.required");
        // remove by cch at 20114-04-15
        // ValidationUtils.rejectIfEmpty(errors, "customer.businessLicense", "customer.businessLicense.required");
        ValidationUtils.rejectIfEmpty(errors, "customer.address", "customer.address.required");
        ValidationUtils.rejectIfEmpty(errors, "customer.url", "customer.url.required");
     //   ValidationUtils.rejectIfEmpty(errors, "customer.customerType", "customer.customerType.required");

        CustomerType customerType = customer.getCustomerType();
        if (customerType==null) {
            errors.rejectValue("customer.customerType", "customer.customerType.required");
        }
        Integer country = customer.getCountry();
        if (country != null && country == -1) {
            errors.rejectValue("customer.country", "customer.country.required");
        }
      if (CustomerType.offline.equals(customerType)) {
                ValidationUtils.rejectIfEmpty(errors, "customer.agentType", "customer.agentType.required");
                ValidationUtils.rejectIfEmpty(errors, "customer.agentRegional", "customer.agentRegional.required");
                ValidationUtils.rejectIfEmpty(errors, "customer.agentCountry", "customer.agentCountry.required");
            } else {
                ValidationUtils.rejectIfEmpty(errors, "customer.industry", "customer.industry.required");

                Integer industry = customer.getIndustry();
                if (industry != null && industry == -1) {
                    errors.rejectValue("customer.industry", "customer.industry.required");
                }

                if (CustomerType.nondirect.equals(customerType)) {
                    ValidationUtils.rejectIfEmpty(errors, "customer.agentCompany", "customer.agentCompany.required");
                    Long agentCompany = customer.getAgentCompany();
                    if (agentCompany != null && agentCompany == -1) {
                        errors.rejectValue("customer.agentCompany", "customer.agentCompany.required");
                    }
                }
            }
        }
}
