package com.baidu.gcrm.bpm.service.handler;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.bpm.service.IAssignmentHandler;
import com.baidu.gwfp.ws.dto.ParticipantInfo;
/**
 * 
 *     
 * 项目名称：gcrm    
 * 类名称：ProcessInstanceStarterHandler    
 * 类描述：    流程实例启动 分配Handler
 * 创建人：chenchunhui01    
 * 创建时间：2014年6月27日 上午11:34:23    
 * 修改人：chenchunhui01    
 * 修改时间：2014年6月27日 上午11:34:23    
 * 修改备注：    
 * @version     
 *
 */
@Service("processInstanceStarterHandler")
public class ProcessInstanceStarterHandler implements IAssignmentHandler {

    @Override
    public void assign(ParticipantInfo part,String performerName,Map<String,Object>params){
        
        part.setResourceIds(new String[]{performerName});
    }

}
