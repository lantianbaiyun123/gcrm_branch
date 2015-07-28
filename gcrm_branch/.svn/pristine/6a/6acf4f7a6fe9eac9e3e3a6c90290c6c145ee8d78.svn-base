package com.baidu.gcrm.bpm.service.handler;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.bpm.service.IAssignmentHandler;
import com.baidu.gcrm.user.model.User;
import com.baidu.gwfp.ws.dto.ParticipantInfo;

@Service("defaultAssignmentHandler")
public class DefaultAssignmentHandler implements IAssignmentHandler {
    @Autowired
    IUserRightsService userRightsService;

    @Override
    public void assign(ParticipantInfo part, String performerName, Map<String, Object> params) {
        String participantId = part.getParticipantId();
        String[] userNames = getUserNamesByParticipantId(participantId);
        if (userNames != null) {
            part.setResourceIds(userNames);
        }
    }

    private String[] getUserNamesByParticipantId(String participantId) {
        String[] userNames;

        List<User> users = userRightsService.findUserByRoleTag(participantId);
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
