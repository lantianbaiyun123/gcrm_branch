package com.baidu.gcrm.user.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.account.model.Account.AccountStatus;
import com.baidu.gcrm.account.model.Account.AccountType;
import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.user.web.helper.UserListBean;
import com.baidu.gcrm.user.web.vo.DataRightsListVO;
import com.baidu.gcrm.user.web.vo.DataRightsVO;
import com.baidu.gcrm.user.web.vo.OptionVO;
import com.baidu.gcrm.user.web.vo.UserConditionVO;
import com.baidu.gcrm.user.web.vo.UserDetailInfoVO;
import com.baidu.gcrm.user.web.vo.UserVO;

@Controller
@RequestMapping("/user")
public class UserAction extends ControllerHelper{
	private Logger logger = LoggerFactory.getLogger(UserAction.class);

	@Autowired
	IUserService userService;
	
	@Autowired
	IAccountService accountService;
	
	@Autowired
	IUserRightsService rightsService;
	
	/**
	 * 编辑用户
	 * @param userVO
	 * @return
	 */
	@RequestMapping("editUser/{ucId}")
	@ResponseBody
	public JsonBean<UserVO> editUser(@PathVariable("ucId") Long ucId){
		User user = userService.findByUcid(ucId);
		if (null == user) {
			return JsonBeanUtil.convertBeanToJsonBean(null, "no user found",JsonBeanUtil.CODE_ERROR_MESSAGE);
		}
		UserVO userVO = new UserVO();
		userVO.setRealName(user.getRealname());
		userVO.setUcid(user.getUcid());
		userVO.setUsername(user.getUsername());
		userVO.setDept(user.getDept());
		userVO.setEmail(user.getEmail());
		userVO.setSupervisor(user.getSupervisor());
		userVO.setPhoneNumber(user.getPhonenumber());
		return JsonBeanUtil.convertBeanToJsonBean(userVO);
	}
	
	/**
	 * 保存用户（新增、修改）
	 * @param userVO
	 * @return
	 */
	@RequestMapping("saveUser")
	@ResponseBody
	public JsonBean<UserVO> saveUser(@RequestBody UserVO userVO){
		Account account = new Account();
		User user = new User();
		boolean isOtherSysUcUser = false;
		try {
			// 修改用户信息
			if (null != userVO.getUcid()) {
				user = userService.findByUcid(userVO.getUcid());
			}
			user.setRealname(userVO.getRealName());
			user.setEmail(userVO.getEmail());
			if (user.getEmail().contains("@")) {
				user.setUuapName(user.getEmail().split("@")[0]);
			}
			user.setPhonenumber(userVO.getPhoneNumber());
			user.setDept(userVO.getDept());
			user.setSupervisor(userVO.getSupervisor());
			// 新增用户
			if (null == userVO.getUcid()) {
				// 判断用户名在CRM是否有重名
				user.setUsername(userVO.getUsername());
				if (userService.isUsernameExists(user)) {
					throw new CRMBaseException(101L);
				}
				
				account.setName(userVO.getUsername());
				account.setPwd(userVO.getPassword());
				account.setEmail(userVO.getEmail());
				account.setStatus(AccountStatus.ENABLE);
				account.setType(AccountType.INNER);
				generatePropertyForCreate(account);
				
				// 如果在其他产品线已经存在该用户名，则直接保存, 不走UC注册 
				Long ucId = accountService.findUcIdByUcName(userVO.getUsername());
				if (null != ucId && ucId != 0) {
					System.out.println("[UserAction] : reg account local==============================");
					account.setUcid(ucId);
					accountService.save(account);
					isOtherSysUcUser = false; // 其他产品线的UC用户
				}else {
					System.out.println("[UserAction] : reg account to UC==============================");
					accountService.regAccount(account);
				}
				
				user.setStatus(account.getStatus().ordinal());
				generatePropertyForCreate(user);
				user.setUcid(account.getUcid());
			}
			// 修改用户
			else {
				generatePropertyForUpdate(user);
			}
			userService.saveUser(user);
			
			// 其他系统的UC账号，则提示用户
			if (isOtherSysUcUser) {
				return JsonBeanUtil.convertBeanToJsonBean(null, MessageHelper.getMessage("account.register.success.message", currentLocale), JsonBeanUtil.CODE_SUCCESS_MESSAGE);
			}
		}catch (CRMBaseException e) {
			logger.error(MessageHelper.getMessage("account.register.error." + e.getErrorNo(), currentLocale));
			return JsonBeanUtil.convertBeanToJsonBean(null, MessageHelper.getMessage("account.register.error." + e.getErrorNo(), currentLocale),JsonBeanUtil.CODE_ERROR_MESSAGE);
		}
		
		// 新增用户时，绑定UC用户的安全中心邮箱
		try {
			if (null == userVO.getUcid()) {
				accountService.bindUCMail(account);
			}
		} catch (CRMBaseException e) {
			logger.error(MessageHelper.getMessage("account.register.usersecureinfo.error." + e.getErrorNo(), currentLocale));
			System.out.println(MessageHelper.getMessage("account.register.usersecureinfo.error." + e.getErrorNo(), currentLocale));
		}		
		return JsonBeanUtil.convertBeanToJsonBean(userVO);
	}
	
	/**
	 * 查看用户详情
	 * @param ucId
	 * @return
	 */
	@RequestMapping("view/{ucId}")
	@ResponseBody
	public JsonBean<UserDetailInfoVO> viewDetail(@PathVariable("ucId") Long ucId){
		UserDetailInfoVO userDetail = new UserDetailInfoVO();
		userService.findUserDetailInfo(userDetail, ucId, currentLocale);
		return JsonBeanUtil.convertBeanToJsonBean(userDetail);
	}
	
	/**
	 * 更改用户状态（生效、作废）
	 * @param userVO
	 * @return
	 */
	@RequestMapping("changeStatus")
	@ResponseBody
	public JsonBean<UserVO> changeStatus(@RequestBody UserVO userVO){
		User user = userService.findByUcid(userVO.getUcid());
		if (null == user) {
			return JsonBeanUtil.convertBeanToJsonBean(null, "user not found!",JsonBeanUtil.CODE_ERROR_MESSAGE); 
		}
		if (userVO.getStatus().equals(AccountStatus.ENABLE.toString())) {
			user.setStatus(AccountStatus.DISABLE.ordinal());
		}else if (userVO.getStatus().equals(AccountStatus.DISABLE.toString())) {
			user.setStatus(AccountStatus.ENABLE.ordinal());
		}
		try {
			Account account = accountService.findByUcid(user.getUcid());
			accountService.updateAccountStatus(AccountStatus.valueOf(user.getStatus()), account);
		} catch (CRMBaseException e) {
			logger.error(e.getMessage(), e);
			return JsonBeanUtil.convertBeanToJsonBean(null, MessageHelper.getMessage("account.register.error." + e.getErrorNo(), currentLocale),JsonBeanUtil.CODE_ERROR_MESSAGE);
		}
		userService.saveUser(user);
		return JsonBeanUtil.convertBeanToJsonBean(userVO);
	}
	
	/**
	 * 加载查询条件数据
	 * @return
	 */
	@RequestMapping("initQueryView")
    @ResponseBody
    public JsonBean<Map<String, Object>> initQueryView() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<OptionVO> roleOptionVOList = new ArrayList<OptionVO>();
        List<RightsRole> roleList = rightsService.findSysAllRole(currentLocale);
        for (RightsRole rightsRole : roleList) {
        	OptionVO  optionVO = new OptionVO();
        	optionVO.setId(rightsRole.getRoleId());
        	optionVO.setI18nName(rightsRole.getI18nName());
        	roleOptionVOList.add(optionVO);
		}
//        Collections.sort(roleOptionVOList, new Comparator<OptionVO>() {
//        	@Override
//        	public int compare(OptionVO o1, OptionVO o2) {
//        		if (null == o1 || null == o2) {
//					return 0;
//				}
//        		Locale locale = CommonHelper.convertLocale(currentLocale);
//        		RuleBasedCollator collator = (RuleBasedCollator)Collator.getInstance(locale);
//        		return collator.compare(o1.getI18nName(), o2.getI18nName());
//        	}
//		});
        resultMap.put("allRoles", roleOptionVOList);
        return JsonBeanUtil.convertBeanToJsonBean(resultMap);
    } 
	
	/**
	 * 查询用户列表
	 * @param condition
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public JsonBean<Page<UserListBean>> query(@RequestBody UserConditionVO condition){
		Page<UserListBean> resultPage = userService.findByCondition(condition, currentLocale);
		return JsonBeanUtil.convertBeanToJsonBean(resultPage);
	}
	
	@RequestMapping("getRoleList")
	@ResponseBody
	public JsonBean<List<RightsRole>> getRoleList(@RequestParam("username") String username){
		User user = userService.findByUsername(username);
		if (null == user) {
			return JsonBeanUtil.convertBeanToJsonBean(null);
		}
		List<RightsRole> roleList = rightsService.findUserRolesByUcId(user.getUcid());
		return JsonBeanUtil.convertBeanToJsonBean(roleList);
	}
	
	@RequestMapping("editDataRights")
	@ResponseBody
	public JsonBean<DataRightsVO> editDataRights(@RequestParam("username") String username, @RequestParam("roleTag") String roleTag){
		DataRightsVO result = userService.findUserDataRightsVO(username, roleTag, currentLocale);
		return JsonBeanUtil.convertBeanToJsonBean(result);
	}
	
	@RequestMapping("saveDataRights")
	@ResponseBody
	public JsonBean saveDataRights(@RequestBody DataRightsListVO dataRightList){
		userService.saveUserDataRights(dataRightList.getDataRightsVOList());
		return JsonBeanUtil.convertBeanToJsonBean(null);
	}
}
