package com.baidu.gcrm.customer.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.web.helper.CustomerType;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {
    List<Customer> findByCustomerType(CustomerType customerType);
    
    public List<Customer> findByCustomerTypeAndCompanyNameContaining(@Param("customerType")CustomerType customerType,@Param("companyName")String companyName );
    
    Customer findByCustomerNumber(Long customerNumber);

    /**
     * countBy在1.4.0已经成为标准方法了
     * 
     * @param customerNumber
     * @return
     */
    @Query("select count(*) from Customer c where c.customerNumber = ?")
    Long countByCustomerNumber(Long customerNumber);

    @Query("from Customer c where c.country = :countryId and c.businessLicense = :businessLicense and c.companyName = :companyName")
    List<Customer> findByCountryAndLiscenseAndName(@Param("countryId")Integer countryId, @Param("businessLicense")String liscense, @Param("companyName")String companyName);
    //根据国家+名称校验客户唯一性
    @Query("from Customer c where  c.approvalStatus <> 0 and c.companyStatus <> 2 and  c.country = :countryId  and c.companyName = :companyName")
    List<Customer> findByCountryAndName(@Param("countryId")Integer countryId, @Param("companyName")String companyName);
    //根据国家+运营许可证 验证运营许可证
    @Query("from Customer c where c.approvalStatus <> 0  and  c.companyStatus <> 2  and c.country = :countryId   and  c.businessLicense = :businessLicense")
    List<Customer> findByCountryAndLiscense(@Param("countryId")Integer countryId, @Param("businessLicense")String liscense);
    
    List<Customer> findByAgentCompany(Long id);

    List<Customer> findByCompanyName(String companyName);

    List<Customer> findByCompanyNameLikeAndBusinessTypeNotAndApprovalStatus(String companyName, Integer businessType,
            Integer approvalStatus, Pageable page);

    List<Customer> findByCompanyNameLikeAndCompanyStatusAndApprovalStatusAndCustomerTypeIn(String companyName,
            Integer customerState, Integer approvalStatus, Collection<CustomerType> customerType, Pageable page);
    
    List<Customer> findByCompanyNameLikeAndCompanyStatusAndApprovalStatus(String companyName, Integer customerState, Integer approvalStatus, Pageable page);
}
