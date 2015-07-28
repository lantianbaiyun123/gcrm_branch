package com.baidu.gcrm.common.datarole;

import java.util.List;

import com.baidu.gcrm.user.model.User;

public interface IGCrmDataRoleService {
    /**
     * 
     * 功能描述:获取可查看用户数据的用户列表
     * findFeasiblityUserList
     * @创建人:	 chenchunhui01
     * @创建时间: 	2014年6月10日 下午6:05:43     
     * @param user
     * @return   
     * @return List<User>  
     * @exception   
     * @version
     */
    public List<User> findFeasiblityUserList(User user);
    /**
    * 功能描述：   根据ucid查询数据使用的用户列表
    * 创建人：yudajun    
    * 创建时间：2014-6-11 上午11:27:59   
    * 修改人：yudajun
    * 修改时间：2014-6-11 上午11:27:59   
    * 修改备注：   
    * 参数： @param ucid
    * 参数： @return
    * @version
     */
    public List<Long> findFeasiblityUserIdList(Long ucid);
}
