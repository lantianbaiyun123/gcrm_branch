package com.baidu.gcrm.bpm.service.handler;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.bpm.service.IAssignmentHandler;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.user.model.User;
import com.baidu.gwfp.ws.dto.ParticipantInfo;
@Service("directSupervisorAssignmentHandler")
public class DirectSupervisorAssignmentHandler  implements IAssignmentHandler {
    @Autowired
    IUserRightsService userRightsService;

    @Override
    public void assign(ParticipantInfo part, String performerName, Map<String, Object> params) {
        String[] userNames = getUserNames();
        if (userNames != null) {
            part.setResourceIds(userNames);
        }
    }

    private String[] getUserNames(){
        String[] userNames;

        List<User> users = userRightsService.findDirectLeaderByUcId(RequestThreadLocal.getLoginUserId());
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
