package com.baidu.gcrm.ws.mdm.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.ws.mdm.DynamicClient;
import com.baidu.gcrm.ws.mdm.IMDMClientProtobufService;
import com.baidu.gcrm.ws.mdm.customer.vo.CustomerBaseVo;
import com.baidu.gcrm.ws.mdm.helper.GcrmConsumerConfig;
import com.baidu.gcrm.ws.mdm.helper.MdmServiceName;
import com.baidu.tianlu.config.ConsumerConfig;
import com.baidu.tianlu.rpc.DefaultRpcProxy;
import com.baidu.tianlu.rpc.RpcProxy;
import com.baidu.tianlu.rpc.dynamic.DynamicCallService;
@Service
public class MDMClientPbServiceImpl implements IMDMClientProtobufService {

   //mdm提供的接口服務

    
    public Long syncNewCustomer2Mdm(CustomerBaseVo customer) throws CRMBaseException{
          	
      ConsumerConfig consumer = GcrmConsumerConfig.getInstance(MdmServiceName.Create_Customer.getServiceName());
           
      RpcProxy rpcProxy = new DefaultRpcProxy(consumer);
      DynamicCallService sv = (DynamicCallService) rpcProxy.getObject();
      CustomerBaseVo resultCustomer =  (DynamicClient.invoke(sv,MdmServiceName.Create_Customer, customer, CustomerBaseVo.class));
      if(resultCustomer==null || resultCustomer.getCustId() ==null){
           throw new CRMBaseException("customer.sync.mdm.result.null","同步客户到主数据反馈编码为空，客户编码获取失败");
      }
      return  Long.valueOf( resultCustomer.getCustId());
    }
    
    public void syncModefiyCustomer2Mdm(CustomerBaseVo customer){
        ConsumerConfig consumer = GcrmConsumerConfig.getInstance(MdmServiceName.Update_Customer.getServiceName());
        
        RpcProxy rpcProxy = new DefaultRpcProxy(consumer);
        DynamicCallService sv = (DynamicCallService) rpcProxy.getObject();
        //TODO mdm确认接口反馈类型
        DynamicClient.invoke(sv,MdmServiceName.Update_Customer, customer, Map.class);
    }
}
