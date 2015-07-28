package com.baidu.gcrm.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.common.page.IPageQuery;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.user.dao.IUserRepositoryCustom;
import com.baidu.gcrm.user.web.helper.UserListBean;
import com.baidu.gcrm.user.web.vo.UserConditionVO;

@Repository
public class UserRepositoryCustomerImpl implements IUserRepositoryCustom {
	@PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IPageQuery mysqlPageQuery;
    
	@Override
	public Page<UserListBean> findByCondition(UserConditionVO condition) {
        Map<String, Object> params = new java.util.HashMap<String, Object>();
        StringBuilder sqlParam = processCondition(condition, params);
        String sqlJoin = "";
        if (null != condition.getRoleId()) {
        	sqlJoin = " INNER JOIN g_rights_user_role ur ON ur.uc_id = u.ucid ";
		}
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("SELECT DISTINCT u.id, u.ucid, u.realname, u.email, u.username, u.status from g_user u ")
              .append(sqlJoin)
              .append(" where 1=1 ")
        	  .append(sqlParam)
  	          .append(" order by u.status desc, u.last_update_time desc, u.create_time desc ");

        mysqlPageQuery.executePageQuery(entityManager, sqlStr, params, condition);

        return condition;
    }
	
	@Override
	public Map<String, List<String>> getAllUserAndRoleName() {
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
		Query query = entityManager.createQuery("SELECT  CONCAT(u.username,',',u.realname,',',p.posName), r.roleDesc from RightsUserRole ur,RightsRole r,User u, RightsUserPosition up, RightsPosition p "
				+ "where ur.roleId = r.roleId and u.ucid = ur.ucId and up.posId = p.posId and up.ucId = u.ucid and u.status = 1 order by r.roleTag, p.posTag");
		List<Object[]> resultList = query.getResultList();
		for (Object[] obj : resultList) {
			if (resultMap.containsKey(obj[1])) {
				List<String> usernameList = resultMap.get(obj[1]);
				usernameList.add(obj[0].toString());
			}else {
				List<String> nameList = new ArrayList<String>();
				nameList.add(obj[0].toString());
				resultMap.put(obj[1].toString(), nameList);
			}
		}
		return resultMap;
	}
	
	private StringBuilder processCondition(UserConditionVO condition, Map<String, Object> params) {
		StringBuilder sqlParam = new StringBuilder();
		String realName = condition.getRealName();
		String email = condition.getEmail();
		String ucName = condition.getUcName();
		Long roleId = condition.getRoleId();
		
		if (StringUtils.isNotBlank(realName)) {
			sqlParam.append(" AND u.realname like :realName ");
			params.put("realName", "%" + realName + "%");
		}
		if (StringUtils.isNotBlank(email)) {
			sqlParam.append(" AND u.email like :email ");
			params.put("email", "%" + email + "%");
		}
		if (StringUtils.isNotBlank(ucName)) {
			sqlParam.append(" AND u.username like :ucName ");
			params.put("ucName", "%" + ucName + "%");
		}
		if (null != roleId) {
			sqlParam.append(" AND ur.role_id = :roleId");
			params.put("roleId", roleId);
		}
		return sqlParam;
	}

}
