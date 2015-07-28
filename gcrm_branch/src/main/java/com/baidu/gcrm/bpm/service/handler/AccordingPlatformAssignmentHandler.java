package com.baidu.gcrm.bpm.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.approval.service.IParticipantService;
import com.baidu.gcrm.bpm.service.IAssignmentHandler;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gwfp.ws.dto.ParticipantInfo;
@Service("accordingPlatformAssignmentHandler")

public class AccordingPlatformAssignmentHandler implements IAssignmentHandler {
    
    private static String CUSTOM_PARAMS_PLATFORMIDS ="platformIds";
    @Autowired
    private IParticipantService participantService;
    @Autowired
    private IUserService userService;
    @Override
    public void assign(ParticipantInfo part, String performerName, Map<String, Object> params) {
        Set<String> platformIds =(Set<String>) params.get(CUSTOM_PARAMS_PLATFORMIDS);
        List<String> usernames4Pm;
        if(platformIds.size()>0){
            usernames4Pm = participantService.getUnamesByDescAndInKeysAndPartId(platformIds, DescriptionType.platform, ParticipantConstants.pm_leader.toString());
            if(usernames4Pm!=null && usernames4Pm.size()>0){
                usernames4Pm = userService.findUuapNameByName(usernames4Pm);
            }
        }else{
            usernames4Pm = new ArrayList<String>();
        }
        String[] userNames =usernames4Pm.toArray(new String[usernames4Pm.size()]);

        if (userNames != null) {
            part.setResourceIds(userNames);
        }
    }

}
