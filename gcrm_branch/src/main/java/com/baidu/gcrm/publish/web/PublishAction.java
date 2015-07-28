package com.baidu.gcrm.publish.web;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.IFileUploadService;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.auth.service.IUserDataRightService;
import com.baidu.gcrm.common.datarole.IGCrmDataRoleService;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.publish.dao.IPublishDateRepository;
import com.baidu.gcrm.publish.dao.IPublishRepository;
import com.baidu.gcrm.publish.model.ForcePublishProof;
import com.baidu.gcrm.publish.model.ForcePublishProof.ProofType;
import com.baidu.gcrm.publish.model.PublishMailType;
import com.baidu.gcrm.publish.model.PublishRecord;
import com.baidu.gcrm.publish.service.IForcePublishProofService;
import com.baidu.gcrm.publish.service.IPublishListService;
import com.baidu.gcrm.publish.service.IPublishMailService;
import com.baidu.gcrm.publish.service.IPublishRecordService;
import com.baidu.gcrm.publish.web.utils.PublishCondition;
import com.baidu.gcrm.publish.web.utils.PublishDoneCondition;
import com.baidu.gcrm.publish.web.vo.PublishDoneListVO;
import com.baidu.gcrm.publish.web.vo.PublishOwnerListVO;
import com.baidu.gcrm.quote.web.utils.AdvertisingPlatformComparator;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform.AdPlatformBusinessType;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;

@Controller
@RequestMapping("/publish")
public class PublishAction extends ControllerHelper{
	private static Logger log = LoggerFactory.getLogger(PublishAction.class);
	@Autowired
	private IPublishListService publishListService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IFileUploadService fileUploadService;
	@Autowired
	private IUserDataRightService userDataRightService;
	@Autowired
	IPublishMailService publishMailService;
	@Autowired
	IForcePublishProofService forcePublishProofService;
	@Autowired
	IPublishRecordService publishRecordService;
	
	@Autowired
	IPublishRepository publishDao;
	@Autowired
	IPublishDateRepository publishDateDao;
	
	 @Autowired
	 private IGCrmDataRoleService gcrmDataRoleService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/findPublishList")
	@ResponseBody
	public Object findPublishList(@RequestBody PublishCondition condition){
		//加数据权限控制
		List<Long> ucidList = gcrmDataRoleService.findFeasiblityUserIdList(getUserId());
		condition.setOperatorIdList(ucidList);
		return JsonBeanUtil.convertBeanToJsonBean(publishListService
				.findPublishList(condition, this.currentLocale));
	}
	
	@RequestMapping("/suggestUser")
	@ResponseBody
	public Object suggestUser(String userName){
		if(StringUtils.isNotBlank(userName)){
			userName = "%" + userName + "%";
		}else{
			return JsonBeanUtil.convertBeanToJsonBean(null);
		}
		return JsonBeanUtil.convertBeanToJsonBean(userService
				.findByName(userName));
	}
	
	@RequestMapping("/findPublishDateByPublishNumber")
	@ResponseBody
	public Object findPublishDateByPublishNumber(String publishNumber,String operateDate) {
		Date date=  null;
		if(StringUtils.isNotBlank(operateDate)){
			date = DateUtils.getString2Date(DateUtils.YYYY_MM_DD, operateDate);
		}
		return JsonBeanUtil.convertBeanToJsonBean(publishListService
				.findPublishDetailByPublishNumber(publishNumber, date));
	}
	
	@RequestMapping("/findPublishDetailByPublishNumber")
	@ResponseBody
	public JsonBean<List<PublishRecord>> findPublishDetailByPublishNumber(@RequestParam("publishNumber")String number) {
		List<PublishRecord> records = publishRecordService.findByPublishNumber(number);
		if (records == null) {
			return JsonBeanUtil.convertBeanToJsonBean(null, "publich.record.not.exist");
		}
		return JsonBeanUtil.convertBeanToJsonBean(records);
	}
//	public JsonBean<PublishDateListVO> findPublishDetail(Long id) {
//        PublishRecord record = publishRecordService.findById(id);
//        if (record == null) {
//            return JsonBeanUtil.convertBeanToJsonBean(null, "publich.record.not.exist");
//        }
//        PublishDateListVO publishDates = publishListService.findPublishDatesByPublishAndMaterialNumber(record.getPublishNumber(), record.getMaterialNumber());
//        
//        return JsonBeanUtil.convertBeanToJsonBean(publishDates);
//    }
	@RequestMapping("/findPublishDoneList")
	@ResponseBody
	public JsonBean<Page<PublishDoneListVO>> findPublishDoneList(@RequestBody PublishDoneCondition condition) {

	    return JsonBeanUtil.convertBeanToJsonBean(publishListService
				.findPublishDoneList(condition, this.currentLocale));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/findConditionResult")
	@ResponseBody
	public Object findConditionResult(){
		PublishCondition condition = new PublishCondition();
		//加数据权限控制
		List<Long> ucidList = gcrmDataRoleService.findFeasiblityUserIdList(getUserId());
		condition.setOperatorIdList(ucidList);
		return JsonBeanUtil.convertBeanToJsonBean(publishListService
				.findConditionResult(condition, this.currentLocale));
	}
	
	@RequestMapping("/findPublishOwner")
	@ResponseBody
	public JsonBean<List<PublishOwnerListVO>> findPublishOwner(Long platformId) {
		return JsonBeanUtil.convertBeanToJsonBean(publishListService
				.findPublishOwnerListByPlatformId(platformId,currentLocale));
	}
	
	@RequestMapping("/findPlatform")
	@ResponseBody
	public Object findPlatform() {
		List<AdvertisingPlatform> list = userDataRightService
				.getPlatformListByUcId(RequestThreadLocal.getLoginUserId(),currentLocale);
		List<AdvertisingPlatform> result = new ArrayList<AdvertisingPlatform>();
		
		if(CollectionUtils.isNotEmpty(list)){
			for(AdvertisingPlatform obj : list){
				if(AdPlatformBusinessType.liquidate.ordinal() == obj.getBusinessType()){
					result.add(obj);
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(result)){
        	Collections.sort(result, new AdvertisingPlatformComparator());
        }
		return JsonBeanUtil.convertBeanToJsonBean(result);
	}
	
	@RequestMapping("/deletePublishOwner")
	@ResponseBody
	public Object deletePublishOwner(Long id,Long ucid){
		publishListService.deletePublishOwner(id, ucid);
		return JsonBeanUtil.convertBeanToJsonBean(null);
	}
	
	@RequestMapping("/addPublishOwner")
	@ResponseBody
	public Object addPublishOwner(Long id,Long channelId,Long ucid){
		if(id != null && publishListService.checkAddUcidRe(id, ucid)){
			String message = MessageHelper.getMessage("publish.addPublishOwner.ucidRepeat", this.currentLocale);
			return JsonBeanUtil.convertBeanToJsonBean(id,message,JsonBeanUtil.CODE_MESSAGE);
		}
		
		Long res = publishListService.addPublishOwner(id, ucid,channelId);
		return JsonBeanUtil.convertBeanToJsonBean(res);
	}
	@RequestMapping("/remindersPublish/{publishid}")
	@ResponseBody
	public 	JsonBean<String>remindersPublish(@PathVariable("publishid")Long publishid){
		Map<String,String> errors = new HashMap<String, String>();
		try{
			publishMailService.remindersContetnByMail(publishid, currentLocale);
		}catch (Exception e) {
			errors.put("reminders.publish.mail.error", "reminders.publish.mail.error");		
			return JsonBeanUtil.convertBeanToJsonBean(null,errors);
			
		}
		
		return JsonBeanUtil.convertBeanToJsonBean("success");
		
	}
	@RequestMapping("/collectionMail")
	@ResponseBody
	public 	JsonBean<String>collectionMail(){
		try{
		publishMailService.findPulishMail(currentLocale, PublishMailType.onlineCollection);
		publishMailService.findPulishMail(currentLocale, PublishMailType.materCollection);

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return JsonBeanUtil.convertBeanToJsonBean("success");	
	}
	
	@RequestMapping("/downProofMaterial/{proofid}")
	@ResponseBody
	public void downProofMaterial(HttpServletResponse response,HttpServletRequest request,
			@PathVariable("proofid") Long proofid){
		if(proofid == null){
        	return;
        }
		ForcePublishProof material = forcePublishProofService.findById(proofid);
		if(material == null || !ProofType.offline_attachment.equals(material.getType())){
			return;
		}
		String fileName = material.getDownloadFilename();
		if (!PatternUtil.isBlank(fileName) && fileName.contains(".")) {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1)
					.toLowerCase();
			if(!fileType.endsWith("jpg") &&
					!fileType.endsWith("png") && 
					!fileType.endsWith("gif") && 
					!fileType.endsWith("jpeg") && 
					!fileType.endsWith("bmp") ){
				try {
					response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ));
				} catch (UnsupportedEncodingException e) {
					log.error("=====download quotation material fail===="
							+ e.getMessage());
				}
			}else{
				String userAgent = request.getHeader("user-agent").toLowerCase();
				if (userAgent.contains("msie")) {
					response.setContentType("application/octet-stream");
				}else{
					response.setContentType("image/" + fileType);
				}
			}
		}

		try {
			FileCopyUtils.copy(fileUploadService.download(material.getUploadFilename()),
					response.getOutputStream());
		} catch (Exception e) {
			log.error("=====download quotation material fail===="
					+ e.getMessage());
		}
	}
}
