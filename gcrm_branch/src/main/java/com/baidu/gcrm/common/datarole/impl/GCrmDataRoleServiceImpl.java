package com.baidu.gcrm.common.datarole.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.common.auth.service.IUserDataRightService;
import com.baidu.gcrm.common.datarole.IGCrmDataRoleService;
import com.baidu.gcrm.common.datarole.vo.RoleClassfiy;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gson.Gson;
import com.baidu.gson.JsonParseException;
import com.baidu.gson.JsonParser;
import com.baidu.gson.reflect.TypeToken;
@Service
public class GCrmDataRoleServiceImpl implements IGCrmDataRoleService {

    @Autowired
    private IUserRightsService userRightsService;
    @Autowired
    private IUserDataRightService userDataRightService;
    
	@Autowired
	private IUserService userService;
    
  //  private static String Rloe_Classfiy_Key="dataRight.role.Rloe_Classfiy_Key";
    //数据格式 [{"classfiyId":1,"roleList":["salar","salar_leader","salar1","salar_leader1"]},{"classfiyId":2,"roleList":["salar","salar_leader","salar1","salar_leader1"]}]

   
    //private static String Max_Role_Key="dataRight.role.Max_Role_Key";

    private String maxRoleVaule;
    
    private List<String> maxRoleList;
    
    public String getMaxRoleVaule() {
        return maxRoleVaule;
    }
    
    @Value("#{appproperties['dataRight.role.Max_Role_Key']}")
    public void setMaxRoleVaule(String maxRoleVaule) {
        this.maxRoleVaule = maxRoleVaule;
         maxRoleList =Arrays.asList(maxRoleVaule.split(","));
    }

   
    private String roleClassfiyJsonValue;
    
    
    public String getRoleClassfiyJsonValue() {
        return roleClassfiyJsonValue;
    }
    @Value("#{appproperties['dataRight.role.Rloe_Classfiy_Key']}")
    public void setRoleClassfiyJsonValue(String roleClassfiyJsonValue) {
        this.roleClassfiyJsonValue = roleClassfiyJsonValue;

        if (isGoodJson(roleClassfiyJsonValue)) {
            Gson gson = new Gson();
            Set<RoleClassfiy> roleClassfiySet = gson.fromJson(roleClassfiyJsonValue,
                    new TypeToken<Set<RoleClassfiy>>() {
                    }.getType());
            this.roleClassfiySet = roleClassfiySet;
        }
    }

    private Set<RoleClassfiy> roleClassfiySet;
    
    /**
     * 
     * 功能描述:获取可查看用户数据的用户列表
     * findFeasiblityUserList
     * @创建人:     chenchunhui01
     * @创建时间:   2014年6月10日 下午6:05:43     
     * @param user
     * @return   
     * @return List<User>  
     * @exception   
     * @version
     */
    public List<User> findFeasiblityUserList(User user) {
        List<User> resultUserList = new ArrayList<User>();
        List<RightsRole> rightsRoles = userRightsService.findUserRolesByUcId(user.getUcid());
        if(isMaxRole(rightsRoles)){
            return null;
        }
        if(CollectionUtils.isEmpty(rightsRoles)){
        	resultUserList.add(user);
    		return resultUserList;
    	}
        List<RoleClassfiy> containsRoleClassfiys = getContainsRoleClassfiy(rightsRoles);
        
        for(RoleClassfiy roleClassfiy:containsRoleClassfiys){
            switch(roleClassfiy.getClassfiyId()){
            case 1:
                if(!resultUserList.contains(user)){
                    resultUserList.add(user);
                }
                break;
            case 2:
               List<User> userList = userDataRightService.getSubUserListByUcId(user.getUcid());
               if(userList != null && userList.size() > 0) {
                   resultUserList.addAll(userList);
               }
               if(!resultUserList.contains(user)){
                   resultUserList.add(user);
               }
                break;
            case 3:
                List<User> leaderList = userDataRightService.getLeaderListByUcId(user.getUcid());
				if (leaderList != null && leaderList.size() > 0) {
                    resultUserList.addAll(leaderList);
                }
				if (!resultUserList.contains(user)){
                    resultUserList.add(user);
                }
                 break;
            default:
                ;
            }
        }
        
        return resultUserList;
    }
	/**
	 * 
	 * 功能描述: 判断是否具有查询全部数据的权限
	 * isMaxRole
	 * @创建人:	 chenchunhui01
	 * @创建时间: 	2014年6月10日 下午6:06:44     
	 * @param rightsRoles
	 * @return   
	 * @return boolean  
	 * @exception   
	 * @version
	 */
    private boolean  isMaxRole(List<RightsRole> rightsRoles){
 //       String maxRoleStr = GcrmConfig.getConfigValueByKey(Max_Role_Key);
        
        for(RightsRole rightsRole:rightsRoles){
            for(String roleTag:maxRoleList){
            if(StringUtils.equals(roleTag, rightsRole.getRoleTag())){
                return true;
            }
            }
        }

        return false;
    }
    /**
     * 
     * 功能描述:根据用户所以具有的角色查询所具备的数据分类
     * getContainsRoleClassfiy
     * @创建人:	 chenchunhui01
     * @创建时间: 	2014年6月10日 下午6:07:15     
     * @param rightsRoles
     * @return   
     * @return List<RoleClassfiy>  
     * @exception   
     * @version
     */
    @SuppressWarnings("unchecked")
    private List<RoleClassfiy> getContainsRoleClassfiy(List<RightsRole> rightsRoles){
    	List<RoleClassfiy> resultRoleClassfiy=new ArrayList<RoleClassfiy>();
//        String roleClassfiyJsonStr = GcrmConfig.getConfigValueByKey(Rloe_Classfiy_Key);
//        if(!isGoodJson(roleClassfiyJsonStr)){
//        	return resultRoleClassfiy;
//        }
//        Gson gson = new Gson();
//        Set<RoleClassfiy> roleClassfiySet = gson.fromJson(roleClassfiyJsonStr, new TypeToken<Set<RoleClassfiy>>(){}.getType());

    	if(roleClassfiySet ==null){
    	    return resultRoleClassfiy;
    	}
        boolean hadfind;
        for(RoleClassfiy roleClassfiy :roleClassfiySet){
            hadfind= false;
            for(String roleTag:roleClassfiy.getRoleList()){
                for(RightsRole rightsRole:rightsRoles){
                    if(StringUtils.equals(roleTag,rightsRole.getRoleTag())){
                        resultRoleClassfiy.add(roleClassfiy);
                        hadfind =true;
                        break;
                    }
                }
                if(hadfind){
                    break;
                }
            }
            
        }
        return resultRoleClassfiy;
    }

	@Override
	public List<Long> findFeasiblityUserIdList(Long ucid) {
		List<Long> result = new ArrayList<Long>();
		if(ucid != null){
			User user = userService.findByUcid(ucid);
			if(user != null){
				List<User> userList = findFeasiblityUserList(user);
				if(CollectionUtils.isNotEmpty(userList)){
					for(User obj : userList){
						if (!result.contains(obj.getUcid())) {
							result.add(obj.getUcid());
						}
					}
				}
			}
		}
		return result;
	}
	
	private static boolean isGoodJson(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	} 
}
