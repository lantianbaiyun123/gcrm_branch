package com.baidu.gcrm.ws.mdm;

import java.util.HashMap;
import java.util.Map;

import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.ws.mdm.customer.vo.CustomerBaseVo;
import com.baidu.gcrm.ws.mdm.helper.MdmServiceName;
import com.baidu.rigel.protobuf.mapper.DynamicMessageMapper;
import com.baidu.tianlu.rpc.ConsumerContext;
import com.baidu.tianlu.rpc.dynamic.DynamicCallService;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.DynamicMessage;

/**
 * PB调用工具
 * 
 * @author chenchunhui01
 * @version 2014年4月16日 下午10:35:53
 */
public class DynamicClient {
//应用配置信息

    public static <T> T invoke (DynamicCallService sv ,MdmServiceName service,CustomerBaseVo customer ,Class<T> clazz) {
        Descriptor descriptor = ConsumerContext.get( service.getServiceName()).getArgDescriptor("execute");
          
        DynamicMessage input = null;
        Map<String,CustomerBaseVo> param = new HashMap<String,CustomerBaseVo>();
		param.put("cust",customer);
        
        try {
    		input = DynamicMessageMapper.toDynamicMessage(param, descriptor);
           //input = DynamicMessageMapper.toDynamicMessage(customer, descriptor);
        } catch (Exception e) {
            LoggerHelper.err(DynamicClient.class, e.getMessage(), e);
        }
        //执行调用
        try {
        DynamicMessage dynamicMessage = sv.execute(input, "execute");
       
        //执行调用结束
                //执行结果转换
        //Object o = DynamicMessageMapper.toMap(dynamicMessage);
        //输出结果
        T custResult =DynamicMessageMapper.toObject(dynamicMessage, clazz);
             
        return custResult;
        } catch (Exception e) {
            LoggerHelper.err(DynamicClient.class, e.getMessage(), e);
        }
        return null;
    }
	
}
