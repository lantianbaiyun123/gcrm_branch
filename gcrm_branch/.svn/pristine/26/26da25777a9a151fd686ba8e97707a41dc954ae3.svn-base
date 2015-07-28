package com.baidu.gcrm.bpm.service.handler;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.bpm.service.IAssignmentHandler;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.user.model.User;
import com.baidu.gwfp.ws.dto.ParticipantInfo;
@Service("chainResponsibilityAssignmentHandler")
public class ChainResponsibilityAssignmentHandler implements IAssignmentHandler {
    @Autowired
    IUserRightsService userRightsService;

    @Override
    public void assign(ParticipantInfo part, String performerName, Map<String, Object> params) {
        String participantId = part.getParticipantId();
        String[] userNames = getUserNamesByParticipantId(participantId,performerName);
        if (userNames != null) {
            part.setResourceIds(userNames);
        }
    }
    /**
     * 
     * 功能描述:performerName为uapname如果使用需转换为uapname
     * getUserNamesByParticipantId
     * @创建人:	 chenchunhui01
     * @创建时间: 	2014年6月26日 上午11:22:01     
     * @param participantId
     * @param performerName(暂不使用)
     * @return   
     * @return String[]  
     * @exception   
     * @version
     */
    private String[] getUserNamesByParticipantId(String participantId ,String performerName) {
        String[] userNames;

        List<User> users = userRightsService.findPosUserByRoleTag(ParticipantConstants.valueOf(participantId), RequestThreadLocal.getLoginUserId());
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }

        userNames = new String[users.size()];
        int i = 0;
        for (User user : users) {
            userNames[i++] = user.getUuapName();
        }
        return userNames;
    }
}
