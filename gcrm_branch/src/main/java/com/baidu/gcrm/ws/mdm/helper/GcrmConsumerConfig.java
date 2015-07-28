package com.baidu.gcrm.ws.mdm.helper;

import java.util.HashMap;
import java.util.Map;

import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.tianlu.config.AppConfig;
import com.baidu.tianlu.config.ConsumerConfig;
import com.baidu.tianlu.protocol.ProtocolFactory;
import com.baidu.tianlu.rpc.dynamic.DynamicCallService;

public class GcrmConsumerConfig extends ConsumerConfig {
    
    private static final long serialVersionUID = 1093234578902235789L;
    
    private static Map<String,GcrmConsumerConfig> instanceMap = new HashMap<String, GcrmConsumerConfig>();
    
    private GcrmConsumerConfig(){
        
        
    }
    
    public static GcrmConsumerConfig getInstance(String serviceName){
        
        if(instanceMap.get(serviceName)!=null) {
            return instanceMap.get(serviceName);
        }
        
        GcrmConsumerConfig consumer = buildInstance(serviceName);
        instanceMap.put(serviceName, consumer);
        return consumer;
    }
    
    private static GcrmConsumerConfig buildInstance(String serviceName){
        
        GcrmConsumerConfig consumer = new GcrmConsumerConfig();
        AppConfig app = new AppConfig();
          app.setAppName(GcrmConfig.getConfigValueByKey("mdm.app.name"));
          app.setAppKey(GcrmConfig.getConfigValueByKey("mdm.app.key"));
          app.setMonitor(GcrmConfig.getConfigValueByKey("mdm.monitor"));
          app.setRegistry(GcrmConfig.getConfigValueByKey("mdm.registry"));
        
          consumer.setApp(app);
          consumer.setServiceName(serviceName);
          consumer.setProtocol(ProtocolFactory.HTTPPROTOBUF);
          consumer.setServiceInterface(DynamicCallService.class);
          consumer.setConnectTimeoutMillSecs(2000);
          consumer.setReadTimeoutMillSecs(0);
          consumer.setFetchTokenOnFailure(true);
          consumer.setFetchTokenOnStartUp(true);
        
        return consumer;
    }
}
