package com.baidu.gcrm.ws.mdm.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.contact.service.IContactService;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.customer.web.helper.BusinessType;
import com.baidu.gcrm.customer.web.helper.CustomerType;
import com.baidu.gcrm.ws.mdm.IMDMClientProtobufService;
import com.baidu.gcrm.ws.mdm.IMDMRequestFacade;
import com.baidu.gcrm.ws.mdm.customer.vo.AddressVo;
import com.baidu.gcrm.ws.mdm.customer.vo.BusinessTypeVo;
import com.baidu.gcrm.ws.mdm.customer.vo.CommunicateVo;
import com.baidu.gcrm.ws.mdm.customer.vo.ContactVo;
import com.baidu.gcrm.ws.mdm.customer.vo.CustomerBaseVo;
import com.baidu.gcrm.ws.mdm.customer.vo.CustomerCompanyVo;
import com.baidu.gcrm.ws.mdm.customer.vo.SiteUrlVo;
import com.baidu.gcrm.ws.mdm.customer.vo.TradeVo;




/**
 * CRM推送客户数据到MDM
 *     
 * 项目名称：gcrm    
 * 类名称：MDMRequestFacade    
 * 类描述：    
 * 创建人：chenchunhui01    
 * 创建时间：2014年4月29日 下午2:51:18    
 * 修改人：chenchunhui01    
 * 修改时间：2014年4月29日 下午2:51:18    
 * 修改备注：    
 * @version     
 *
 */
@Service
public class MDMRequestFacade implements IMDMRequestFacade {

    @Autowired
    private  ICustomerService customerService;
   @Autowired
    IContactService contactService;
    @Autowired
    IMDMClientProtobufService mdmClientProtobufService;

    @Override
    public Long syncCustomer(Long customerId) throws CRMBaseException {
       Customer customer= customerService.findById(customerId);
       //customer.setCustomerNumber(null);
       CustomerBaseVo mdmVo = new CustomerBaseVo();
       converterCustomer2MdmVo(customer,mdmVo);
       //TODO 暂时先不穿联系人
       //  splitContact2MdmVo(customerId,mdmVo);
       
       Long result =1L;
       if(customer.getCustomerNumber()!=null){
           //变更增加账户推送         
           mdmClientProtobufService.syncModefiyCustomer2Mdm(mdmVo);
       } else {
           result=mdmClientProtobufService.syncNewCustomer2Mdm(mdmVo);
       }
       
        return result;
    }
    
    private void converterCustomer2MdmVo(Customer customer,CustomerBaseVo mdmVo){
        
        Long cusId = customer.getCustomerNumber();
        Integer mdmCustId;
        if(cusId!=null)
         mdmCustId = Integer.valueOf(cusId.toString());
        else mdmCustId=null;
        mdmVo.setCustId(mdmCustId);
        
        mdmVo.setCountry(customer.getCountry());

        mdmVo.setCustName(customer.getCompanyName());
        
        mdmVo.setCustStatus(customer.getCompanyStatus());
        
        mdmVo.setCustType(customer.getCustomerType().ordinal());
        
        mdmVo.setGmtCreate(customer.getCreateTime());
        
        mdmVo.setGmtModify(customer.getUpdateTime());
        
        mdmVo.setModifySys(3);
        mdmVo.setModifyUser(customer.getUpdateOperator()==null?"system":customer.getUpdateOperator().toString());
        
        CustomerCompanyVo customerCompanyVo = new CustomerCompanyVo();
        
        customerCompanyVo.setCustId(mdmCustId);
        
        customerCompanyVo.setCompanyName(customer.getCompanyName());
        Date registerDate = new Date();
        registerDate.setTime(customer.getRegisterTime().getTime());

        customerCompanyVo.setEstDate(registerDate);
        
        customerCompanyVo.setScope(customer.getBusinessScope());
        
        customerCompanyVo.setCoinType(customer.getCurrencyType());
        if(customer.getRegisterCapital()!=null)
            customerCompanyVo.setRegisteredCapital(customer.getRegisterCapital());
        
        customerCompanyVo.setScale(customer.getCompanySize().ordinal());
        
        mdmVo.setCustCompany(customerCompanyVo);
        

        
        List<BusinessTypeVo> businessTypes = new ArrayList<BusinessTypeVo>();
        
        BusinessTypeVo tempBusinessType = new BusinessTypeVo();
        
        String businessType = customer.getBusinessType();
        
        if(StringUtils.isNotBlank(businessType)){
            
            if(businessType.contains(BusinessType.CASH.name())) {
                tempBusinessType = new BusinessTypeVo();
                tempBusinessType.setCustId(mdmCustId);
                tempBusinessType.setBusinessType(BusinessType.CASH.ordinal());
                businessTypes.add(tempBusinessType);
            }
            if(businessType.contains(BusinessType.SALE.name())) {
                tempBusinessType = new BusinessTypeVo();
                tempBusinessType.setCustId(mdmCustId);
                tempBusinessType.setBusinessType(BusinessType.SALE.ordinal());
                businessTypes.add(tempBusinessType);
            }
            mdmVo.setBusinessTypeList(businessTypes);     
        }
//        
       
        if (!CustomerType.offline.equals(customer.getCustomerType())) {
            List<TradeVo> tradeList = new ArrayList<TradeVo>();

            TradeVo tadeVo = new TradeVo();
            tadeVo.setCustId(mdmCustId);
            tadeVo.setTradeId_1(4);
            tadeVo.setTradeType(customer.getIndustry());
            tradeList.add(tadeVo);
            mdmVo.setTradeList(tradeList);
        }

        List<AddressVo> addressList = new ArrayList<AddressVo>();
        AddressVo addressVo = new AddressVo();
        addressVo.setCustId(mdmCustId);
        addressVo.setAddressType(3);
        addressVo.setAddressDetail(customer.getAddress());
        addressList.add(addressVo);
        mdmVo.setAddressList(addressList);
        
        List<SiteUrlVo> urlList = new ArrayList<SiteUrlVo>();
        
        SiteUrlVo siteUrlVo = new SiteUrlVo();
        siteUrlVo.setCustId(mdmCustId);
        siteUrlVo.setSiteUrl(customer.getUrl());     
        urlList.add(siteUrlVo);
        mdmVo.setUrlList(urlList);
        
    }
    
    private void splitContact2MdmVo(Long customerId,CustomerBaseVo mdmVo){
        List<ContactPerson> sourceContactList =  contactService.findContactsByCustomerId(customerId);
        if(sourceContactList ==null || sourceContactList.size() ==0){
            return;
        }
        ContactVo contactVo =null;
        Map<Integer,Integer> contactBusinessCodePool = new HashMap<Integer, Integer>();
        for(ContactPerson contactPerson:sourceContactList){
            contactVo = new ContactVo();
            contactVo.setCustId(Integer.valueOf(customerId.toString()));
            contactVo.setContactName(contactPerson.getName());
            contactVo.setContactType(generateContactNum(contactPerson,contactBusinessCodePool));
              
            contactVo.setDept(contactPerson.getDepartment());
            contactVo.setGender(contactPerson.getGender().ordinal());
            contactVo.setPost(contactPerson.getPositionName());
            splitCommunicate2ContactVo(contactPerson,contactVo);
            mdmVo.addContact(contactVo);
        }
        
      }
      
      private int generateContactNum(ContactPerson contactPerson,Map<Integer,Integer> contactBusinessCodePool){
        
          int seq =0;
          Integer key = (contactPerson.getIsDecisionMaker().getWeight()+contactPerson.getIsLegalPerson().getWeight() +3)%4;
          
          if(contactBusinessCodePool.containsKey(key)){
              seq = contactBusinessCodePool.get(key);
              seq ++;
          
          }
          contactBusinessCodePool.put(key, seq);
          return key*10+seq;
      }
      
    
    private void splitCommunicate2ContactVo(ContactPerson contactPerson,ContactVo contactVo){
        
        String email = contactPerson.getEmail();
        
        if(StringUtils.isNotBlank(email)){
            CommunicateVo  communicateVo = new CommunicateVo();
            communicateVo.setCommType(CommunicateVo.CommType.email.ordinal());
            communicateVo.setCommNo(email);     
            contactVo.addCommunicate(communicateVo);
        }
        
        String mobile = contactPerson.getMobile();
        
        if(StringUtils.isNotBlank(mobile)){
            CommunicateVo  communicateVo = new CommunicateVo();
            communicateVo.setCommType(CommunicateVo.CommType.mobile_phone.ordinal());
            communicateVo.setCommNo(mobile);     
            contactVo.addCommunicate(communicateVo);
        }
        
        
        String telephone = contactPerson.getTelephone();
        
        if(StringUtils.isNotBlank(telephone)){
            CommunicateVo  communicateVo = new CommunicateVo();
            communicateVo.setCommType(CommunicateVo.CommType.landline_phone.ordinal());
            communicateVo.setCommNo(telephone);     
            contactVo.addCommunicate(communicateVo);
        }
    }

}
