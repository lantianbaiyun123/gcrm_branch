package com.baidu.gcrm.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.account.model.Account.AccountStatus;
import com.baidu.gcrm.account.model.Account.AccountType;
import com.baidu.gcrm.account.rights.dao.IRightsRoleFunctionRepository;
import com.baidu.gcrm.account.rights.dao.IRightsRoleRepository;
import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IRightsSyncService;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.account.rights.vo.RightsUrlVO;
import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.ad.material.service.IMaterialManageService;
import com.baidu.gcrm.ad.material.vo.MaterialContentVO;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.auth.service.IUserDataRightService;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.uc.UpdUserResponse;
import com.baidu.gcrm.common.uc.service.LoginService;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.user.web.vo.UserVO;
import com.baidu.gcrm.valuelist.service.IValuelistWithCacheService;
import com.baidu.gcrm.valuelist.utils.ITableMetaDataManger;
import com.baidu.rigel.crm.rights.bo.AuthInfo;
import com.baidu.rigel.crm.rights.bo.Menu;
import com.baidu.rigel.crm.rights.bo.PosRoleInfo;
import com.baidu.rigel.crm.rights.bo.Position;
import com.baidu.rigel.crm.rights.bo.RoleInfo;
import com.baidu.rigel.crm.rights.bo.User;
import com.baidu.rigel.crm.rights.bo.UserPosRole;
import com.baidu.rigel.crm.rights.service.AuthExtServiceWrapper;
import com.baidu.rigel.crm.rights.service.MenuServiceWrapper;
import com.baidu.rigel.crm.rights.service.NewPositionExtServiceWrapper;
import com.baidu.rigel.crm.rights.service.NewRoleExtServiceWrapper;

@Controller
@RequestMapping("/test")
public class TestRightsAction extends ControllerHelper{
	
	@Autowired
	public IUserDataRightService userRightService;
	
	@Autowired
	private IUserRightsService rightsService;
	
	@Autowired
	private IMaterialManageService materialService;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private IRightsSyncService rightsSyncService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
    private I18nKVService i18nService; 
	
	@Autowired
    private ITableMetaDataManger tableManager;
	
	@Autowired
	private IRightsRoleRepository roleRepository;

	@Value("#{appproperties['cas.errno']}")
	private String errno;
	
	@Value("#{appproperties['cas.appid']}")
	private Integer appid;
	
	@Value("#{appproperties['newrights.sysunitid']}")
	private Integer sysunitid;
	
	@Value("#{appproperties['newrights.sysname']}")
	private String sysname;
	
	@Autowired
    IValuelistWithCacheService valuelistCacheService;
	
	@Autowired
	IRightsRoleFunctionRepository roleFunctionRepository;
	
	@RequestMapping
	public void index() {
		List<Object[]> resultList = roleFunctionRepository.findMenuCodesByUcId(7389310L);
		List<String> menuCodeList = new ArrayList<String>();
		for (Object[] row : resultList) {
			if (!menuCodeList.contains(row[0])) {
				menuCodeList.add(row[0].toString());
			}
			if (!menuCodeList.contains(row[1])) {
				menuCodeList.add(row[1].toString());
			}
		}
		for (String string : menuCodeList) {
			System.out.println(string);
		}
	}
	
//	public static void main(String[] args) {
//		Random random = new Random();
//		StringBuilder codeBuilder = new StringBuilder();
//		for(int i = 0; i < 6; ++i){
//			codeBuilder.append(random.nextInt(10));
//		}
//		System.out.println(codeBuilder.toString());
//		
//		 StringBuffer sb = new StringBuffer();
//	        for(int i=0;i<6;i++){ 
//	            sb.append((int)(Math.random()*10));
//	        } 
//	        System.out.println(sb.toString());
//	}
	
	public static void main(String[] args) {
		System.out.println(ParticipantConstants.countryAgent.name());
		System.out.println(ParticipantConstants.countryAgent.toString());
		System.out.println(LocaleConstants.en_US.toString().contains("en_US"));
	}
	
	@RequestMapping("syncAllUserDataRights")
	public void syncAllUserDataRights(){
		List<com.baidu.gcrm.user.model.User> users = userService.findAll();
		for (com.baidu.gcrm.user.model.User user : users) {
			rightsSyncService.syncUserDataRights(user.getUcid(), user.getUsername());
		}
	}
	
	@RequestMapping("testPosUser/{ucId}")
	public void testPosUser(@PathVariable("ucId") Long ucId){
		List<com.baidu.gcrm.user.model.User> users = rightsService.findPosUserByRoleTag(ParticipantConstants.dept_manager, ucId);
		for (com.baidu.gcrm.user.model.User user : users) {
			System.out.println(user.getUsername() + "  " +user.getRealname());
		}
	}
	
	@RequestMapping("getUserLeader/{ucId}")
	public void getUserLeader(@PathVariable("ucId") Long ucId){
		List<com.baidu.gcrm.user.model.User> leaderList = userRightService.getLeaderListByUcId(ucId);
		if (null != leaderList) {
			for (com.baidu.gcrm.user.model.User user : leaderList) {
				System.out.println("username: " + user.getUsername() + " realname: " + user.getRealname());
			}
		}
	}
	
	@RequestMapping("refreshRoleCache")
	public void refreshRoleCache(){
		List<RightsRole> roleList = roleRepository.findAll();
		for (RightsRole role : roleList) {
			List<LocaleVO> i18nDataList = new ArrayList<LocaleVO>();
			LocaleVO localeUS = new LocaleVO();
			localeUS.setLocale(LocaleConstants.en_US);
			localeUS.setValue(role.getRoleTag());
			LocaleVO localeCN = new LocaleVO();
			localeCN.setLocale(LocaleConstants.zh_CN);
			localeCN.setValue(role.getRoleDesc());
			i18nDataList.add(localeUS);
			i18nDataList.add(localeCN);
			i18nService.deleteById(RightsRole.class, role.getId());
	        i18nService.save(RightsRole.class, role.getId(), i18nDataList);
	        valuelistCacheService.refreshCache(tableManager.getTableMetaData("g_rights_role"));
	        valuelistCacheService.refreshCache(tableManager.getTableMetaData("g_i18n"));
		}
	}
	
	@RequestMapping("forbidden/{ucids}")
	public void forbiddenAccount(@PathVariable("ucids") String ucids){
		if (!ucids.contains(",")) {
			return;
		}
		String [] ucidArray = ucids.split(",");
		for (String ucid : ucidArray) {
			UpdUserResponse updUserResponse = loginService.setAllow(Integer.valueOf(ucid), AccountStatus.DISABLE.ordinal());
			if (null != updUserResponse) {
				System.out.println("forbidden user result:  errcode:" + updUserResponse.getErr_no() + "  msg:" + updUserResponse.getMsg());
			}
		}
	}
	
	@RequestMapping("getUsersByRoleId/{roleId}")
	public void getUsersByRoleId(@PathVariable("roleId") Long roleId){
		List<com.baidu.gcrm.user.model.User> userList = rightsService.findUsersByRoleId(roleId);
		for (com.baidu.gcrm.user.model.User user : userList) {
			System.out.println(user.getUsername()+"  "+user.getRealname());
		}
	}
	
	@RequestMapping("getJsonBean")
	@ResponseBody
	public JsonBean<UserVO> getJsonBean(){
		UserVO userVO = new UserVO();
		userVO.setDept("流程信息管理部");
		userVO.setEmail("gcrm@baidu.com");
		userVO.setPassword("123123");
		userVO.setPhoneNumber("01093948382");
		userVO.setRealName("张三");
		userVO.setSupervisor("领导");
		userVO.setUsername("zhangsan");
		return JsonBeanUtil.convertBeanToJsonBean(userVO);
	}
	
	@RequestMapping("register/{ucname}")
	public void register(@PathVariable("ucname") String ucname)
			throws CRMBaseException {
		Account account = new Account();
		com.baidu.gcrm.user.model.User user = new com.baidu.gcrm.user.model.User();
		try {
			// 判断用户名是否重名
			user.setUsername(ucname);
			if (userService.isUsernameExists(user)) {
				throw new CRMBaseException(101L);
			}
			account.setName(ucname);
			account.setPwd("Crm888");
			account.setEmail("zhanglei35@baidu.com");
			account.setStatus(AccountStatus.ENABLE);
			account.setType(AccountType.INNER);
			generatePropertyForCreate(account);
			accountService.regAccount(account);
			
			user.setStatus(account.getStatus().ordinal());
			generatePropertyForCreate(user);
			user.setUcid(account.getUcid());
			userService.saveUser(user);
		} catch (Exception e) {
			if (e instanceof CRMBaseException) {
				Long errno = ((CRMBaseException) e).getErrorNo();
				System.out.println(MessageHelper.getMessage("account.register.error." + errno, currentLocale));
				System.out.println("errno:            "+ errno);
			}
			e.printStackTrace();
		}

		// 绑定UC用户的安全中心邮箱
		try {
			accountService.bindUCMail(account);
		} catch (CRMBaseException e) {
			System.out
					.println(MessageHelper.getMessage(
							"account.register.usersecureinfo.error." + e.getErrorNo(),
							currentLocale));
			e.printStackTrace();
		}
	}
	
	@RequestMapping("bindMail/{ucid}")
	public void bindMail(@PathVariable("ucid") Long ucid){
		Account account = new Account();
		account.setUcid(ucid);
		account.setEmail("zhanglei35@baidu.com");
		try {
			accountService.bindUCMail(account);
		} catch (CRMBaseException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getAllRole")
	public void getAllRole(){
		NewRoleExtServiceWrapper roleServiceWrapper = ServiceBeanFactory.getRoleService();
		List<RoleInfo> roleList = roleServiceWrapper.getRolesBatch(appid, sysunitid, null);
		for (RoleInfo roleInfo : roleList) {
			System.out.println(" roletag: " + roleInfo.getRoletag() 
					+ "               roleid: " + roleInfo.getRoleid()
					+ "               desc: " + roleInfo.getDescr());
		}
	}
	
	@RequestMapping("getAllPos")
	public void getAllPos(){
		NewPositionExtServiceWrapper positionService = ServiceBeanFactory.getNewPositionService();
		List<Position> posList = positionService.getAllPositionList(appid);
		for (Position position : posList) {
			System.out.println(" postag: " + position.getPostag()
					+ "               posname: " + position.getPosname() 
					+ "               posid: " + position.getPosid()
					+ "               parentPos: " + position.getPosparentnames() + "   "+position.getPosparentids()
					+"                delmark: " +position.getDelmark());
		}
	}
	
	
	@RequestMapping("getSubPos/{posId}")
	public void getSubPos(@PathVariable("posId") Long posId) throws CRMBaseException{
		NewPositionExtServiceWrapper positionService = ServiceBeanFactory.getNewPositionService();
		List<Position> posList = positionService.getAllSonPositionList(posId, false, -1, false, appid);
		for (Position position : posList) {
			System.out.println(" postag: " + position.getPostag() 
					+ "               posname: " + position.getPosname() 
					+ "               posid: " + position.getPosid()
					+ "               parentPos: " + position.getPosparentnames() + "   "+position.getPosparentids()
					+"                delmark: " +position.getDelmark());
		}
	}
	
	@RequestMapping("getAllRoleUser/{roleTag}")
	public void getAllRoleUser(@PathVariable("roleTag") String roleTag){
		NewRoleExtServiceWrapper roleServiceWrapper = ServiceBeanFactory.getRoleService();
		List<User> userList = roleServiceWrapper.getUserListByRoleTag(roleTag, appid);
		for (User user : userList) {
			System.out.println(" getUcname: " + user.getUcname() + "               getUcid: " + user.getUcid());
		}
	}
	
	@RequestMapping("getAllUserRole/{uname}")
	public void getAllUserRole(@PathVariable("uname") String uname){
		com.baidu.gcrm.user.model.User user = userService.findByUsername(uname);
		NewPositionExtServiceWrapper positionService = ServiceBeanFactory.getNewPositionService();
		List<UserPosRole> posRoleListByappid = positionService.getPosRoleListByUcid(user.getUcid(), appid);
		for (UserPosRole upr : posRoleListByappid) {
			List<PosRoleInfo> posRoleInfoList = upr.getRoleinfo();
			for (PosRoleInfo posRoleInfo : posRoleInfoList) {
				System.out.println(" roleTag: " + posRoleInfo.getRoletag() + "               roleName: " + posRoleInfo.getRolename() + "               roleId: " + posRoleInfo.getRoleid());
			}
		}
	}
	
	@RequestMapping("getAllUserPos/{uname}")
	public void getAllUserPos(@PathVariable("uname") String uname){
		com.baidu.gcrm.user.model.User user = userService.findByUsername(uname);
		NewPositionExtServiceWrapper positionService = ServiceBeanFactory.getNewPositionService();
		List<UserPosRole> posRoleListByappid = positionService.getPosRoleListByUcid(user.getUcid(), appid);
		for (UserPosRole upr : posRoleListByappid) {
			System.out.println(" posname: " + upr.getPosname() + "               posid: " + upr.getPosid());
		}
	}
	
	@RequestMapping("getRoleFunc/{roleTag}")
	public void getRoleFunc(@PathVariable("roleTag") String roleTag){
		NewRoleExtServiceWrapper roleServiceWrapper = ServiceBeanFactory.getRoleService();
		List<AuthInfo> authList = roleServiceWrapper.getAuthListByRoleTag(roleTag, appid);
		for (AuthInfo auth : authList) {
			System.out.println(auth.getAuthtag() + "               url: " + auth.getAuthvalue() 
					+ "               getDescr: " + auth.getDescr() + "               getAuthid: " + auth.getAuthid() 
					+ "               getAuthtype: " + auth.getAuthtype());
		}
	}
	
	@RequestMapping("getAllFunc/{sysunitid}")
	public void getAllFunc(@PathVariable("sysunitid") Long id){
		long unitid = null == id ? sysunitid : id;
		AuthExtServiceWrapper authService = ServiceBeanFactory.getAuthService();
		List<AuthInfo> authInfos = authService.getAuthListByUnitId(unitid);

		for (AuthInfo auth : authInfos) {
			System.out.println(auth.getAuthtag() + "               url: " + auth.getAuthvalue() 
					+ "               getDescr: " + auth.getDescr() + "               getAuthid: " + auth.getAuthid() 
					+ "               getAuthtype: " + auth.getAuthtype());
		}
	}
	
	@RequestMapping("getAllMenu")
	public void getAllMenu(){
		MenuServiceWrapper menuService = ServiceBeanFactory.getMenuService();
		List<Menu> menuList = menuService.getAllEnabledMenuByAppName(sysname);
		for (Menu menu : menuList) {
			System.out.println("authTag: "+menu.getAuthTag() + " name:   "+ menu.getName()+ " url:  "+ menu.getUrl());
			List<Menu> childMenuList = menu.getChildren();
			for (Menu childMenu : childMenuList) {
				System.out.println("childMen-------authTag: "+childMenu.getAuthTag() + " name:   "+ childMenu.getName()+ " url:  "+ childMenu.getUrl());
			}
		}
	}
	
	@RequestMapping("getParentPos/{posId}")
	public void getParentPos(@PathVariable("posId") Long posId){
		NewPositionExtServiceWrapper positionService = ServiceBeanFactory.getNewPositionService();
		List<Position> posList = positionService.getParentPositionList(posId, -1, appid);
		for (Position position : posList) {
			System.out.println(" postag: " + position.getPostag()
					+ "               posname: " + position.getPosname() 
					+ "               posid: " + position.getPosid()
					+ "               parentPos: " + position.getPosparentnames() + "   "+position.getPosparentids()
					+"                delmark: " +position.getDelmark());
		}
	}
	
	@RequestMapping("userDisable/{uname}")
	public void updateUcUserStatus(@PathVariable("uname") String uname){
		Account account = accountService.findByName(uname);
		try {
			accountService.updateAccountStatus(AccountStatus.DISABLE, account);
		} catch (CRMBaseException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("userEnable/{uname}")
	public void updateUcUserStatus2(@PathVariable("uname") String uname){ 
		System.out.println("++++++++++++++sucid:   "+(accountService.findUcIdByUcName(uname)));
		Account account = accountService.findByName(uname);
		try {
			accountService.updateAccountStatus(AccountStatus.ENABLE, account);
		} catch (CRMBaseException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("changeUserPwd")
	public void changeAccountPwd(@RequestParam("uname") String uname, @RequestParam("pwd") String newPwd){
		Account account = accountService.findByName(uname);
		try {
			accountService.changeAccountPwd(account.getUcid(), newPwd);
		} catch (CRMBaseException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("findLeaderPos/{uname}")
	public void findLeaderAndPositionByUname(@PathVariable("uname") String uname){
		com.baidu.gcrm.user.model.User user = userService.findByUsername(uname);
		List<com.baidu.gcrm.user.model.User> result = rightsService.findDirectLeaderByUcId(user.getUcid());
	}
	
	@RequestMapping("findUrl/{uname}")
	public void findUrl(@PathVariable("uname") String uname){
		com.baidu.gcrm.user.model.User user = userService.findByUsername(uname);
		RightsUrlVO result = rightsService.findUserUrlVOs(user.getUcid());
		System.out.println();
	}
	
	
	@RequestMapping("syncAllDataToDB")
	public void syncAllDataToDB(){
		try {
			rightsSyncService.syncAllDataToDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("syncRole")
	public void syncAllRoleDataToDB(){
		try {
			rightsSyncService.syncAllRightsRolesToDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getAllCache/{tableName}")
	public void getAllCache(@PathVariable("tableName") String tableName){
		List<Map<String, String>> mapList = valuelistCacheService.getAllFromCache(tableName);
		for (Map<String, String> map : mapList) {
			Set<Entry<String, String>> entrySet = map.entrySet();
			for (Entry<String, String> entry : entrySet) {
				System.out.println("cache: "+ entry.getKey() + "            " + entry.getValue());
			}
		}
	}
	
	
	@RequestMapping("after/{id}")
	public void testAfterSolutionApproved(@PathVariable("id") Long id){
//		materialService.saveMaterialApplyAfterSolutionApproved(adSolutionId);
		com.baidu.gcrm.user.model.User user = getCurrentUser();
		try {
			materialService.afterMaterialApplyPassed(id, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("fm/{id}")
	public void findAdContVoMaterByContId(@PathVariable("id") Long id){
		MaterialContentVO result = materialService.findAdContVoMaterByContId(id);
		System.out.println(result.getMaterialTitle());
	}
	
	@RequestMapping("getURLCode")
	public void findLoginUserURLCode(HttpServletRequest request){
		RightsUrlVO rightsURL = RequestThreadLocal.getLoginUserURLCodes();
		System.out.println(rightsURL.toString());
	}
	
	@RequestMapping("getURL")
	public void findLoginUserURL(HttpServletRequest request){
		List<String> urlList = RequestThreadLocal.getLoginUserURLs();
		System.out.println(urlList.toString());
	}
	
	private com.baidu.gcrm.user.model.User getCurrentUser() {
		com.baidu.gcrm.user.model.User user = new com.baidu.gcrm.user.model.User();
        user.setUcid(getUserId());
        user.setUsername(getUserName());
        user.setRealname(getChineseName());
        user.setRole("Saler");
        return user;
    }

 

}
