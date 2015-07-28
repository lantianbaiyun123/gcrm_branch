package com.baidu.gcrm.customer.web.validator;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.web.helper.CustomerBean;
import com.baidu.gcrm.customer.web.helper.CustomerType;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;
import com.baidu.gcrm.valuelistcache.model.Country;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

/**
 * 项目名称：gcrm 类名称：CustomerAddValidator 类描述： 客户信息验证基础类验证 创建人：chenchunhui01
 * 创建时间：2014年4月15日 上午9:58:07 修改人：chenchunhui01 修改时间：2014年4月15日 上午9:58:07 修改备注：
 * 
 * @version
 * 
 */
public abstract class CustomerBaseValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CustomerBean.class);
    }

    /**
     * 
     * validatorNameAndLicense(验证客户名称和运营许可证)
     */
    protected void validatorNameAndLicense(Customer customer, Errors errors) {
        /**
         * 
         * 1.国家+名称 验证重复 2.提示客户名称不能重复 3.如果输入运营许可证？国家+运营许可证 验证不能重复
         * 
         */

        Integer countryId = customer.getCountry();
        List<Customer> existCustomers4Name = ServiceBeanFactory.getCustomerService().findByCountryAndName(
                customer.getCountry(), customer.getCompanyName());
        Long customerId = customer.getId();

        if (existCustomers4Name != null) {
            if (existCustomers4Name.size() > 1) {
                errors.rejectValue("customer.companyName", "customer.companyName.notunique");
            } else if (existCustomers4Name.size() == 1) {
                Long tempCustomerId =  existCustomers4Name.get(0).getId();
                if (customerId == null || !tempCustomerId.equals(customerId)){
                    errors.rejectValue("customer.companyName", "customer.companyName.notunique");
                }
                   
            }
        }
        if (errors.hasErrors()) {
            return;
        }
        // 如果運營許可不為空
        String liscenseStr = customer.getBusinessLicense();
        if (StringUtils.isBlank(liscenseStr)) {
            return;
        }
        List<Customer> existCustomers4License = ServiceBeanFactory.getCustomerService().findByCountryAndLiscense(
                countryId, liscenseStr);

        if (existCustomers4License != null) {
            if (existCustomers4License.size() > 1) {
                errors.rejectValue("customer.businessLicense", "customer.businessLicense.notunique");
            } else if (existCustomers4License.size() == 1) {
                Long tempCustomerId =existCustomers4License.get(0).getId();
                if (customerId == null || !tempCustomerId.equals(customerId))
                    errors.rejectValue("customer.businessLicense", "customer.businessLicense.notunique");
            }
        }

    }

/**
 * 
 * 功能描述:验证代理类型与代理国家的对应关系
 * validatorAgentTypeAndAgentCountry
 * @创建人:	 chenchunhui01
 * @创建时间: 	2014年7月4日 下午3:19:30     
 * @param customer
 * @param errors   
 * @return void  
 * @exception   
 * @version
 */
    protected void validatorAgentTypeAndAgentCountry(Customer customer, Errors errors){
        if(!CustomerType.offline.equals(customer.getCustomerType())){
            return;
        }
        
        if(customer.getAgentRegional() !=null&&StringUtils.isNotBlank(customer.getAgentCountry())){
            AgentRegional agentRegional =ServiceBeanFactory.getAgentRegionalService().getByIdAndLocale(customer.getAgentRegional(), RequestThreadLocal.getLocale());
            
            String[] agentCountryIds = customer.getAgentCountry().split(",");
            
            Set<Country> agentCountry = agentRegional.getAgentCountries();
            boolean isLegal =false;
            for(String countryId:agentCountryIds){
                isLegal =false;
                for(Country country:agentCountry){
                    if(country.getId().toString().endsWith(countryId)){
                        isLegal =true;
                        break;
                    }
                }
                
                if(!isLegal){
                    errors.rejectValue("customer.agentCountry", "customer.agentCountry.error");
                    break;
                }
            }        
        }       
    }
}
