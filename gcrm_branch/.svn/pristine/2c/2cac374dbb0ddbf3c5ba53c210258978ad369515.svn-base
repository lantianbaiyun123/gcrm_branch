package com.baidu.gcrm.publish.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.common.IFileUploadService;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.publish.model.ForcePublishProof;
import com.baidu.gcrm.publish.model.ForcePublishProof.ProofType;
import com.baidu.gcrm.publish.service.IPublishDateService;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.publish.web.vo.ProofListVO;
import com.baidu.gcrm.publish.web.vo.ProofVO;

@Controller
@RequestMapping("/publication")
public class PublishOperationAction {
	@Autowired
	IPublishService publishService;
	@Autowired
	IPublishDateService publishDateService;
	@Autowired
	IFileUploadService fileUploadService;
	
	@RequestMapping("/publish/{id}")
	@ResponseBody
	public JsonBean<Object> publish(@PathVariable("id")Long id) {
		try {
			publishDateService.publish(id, RequestThreadLocal.getLoginUserId());
			return JsonBeanUtil.convertBeanToJsonBean(null);
		} catch (Exception e) {
			return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
		}
	}
	
	@RequestMapping("/unpublish/{id}")
	@ResponseBody
	public JsonBean<Object> unpublish(@PathVariable("id")Long id) {
		try {
			publishDateService.unpublish(id, RequestThreadLocal.getLoginUserId());
			return JsonBeanUtil.convertBeanToJsonBean(null);
		} catch (Exception e) {
			return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
		}
	}
	
	@RequestMapping("/forcePublish")
	@ResponseBody
	public JsonBean<Object> forcePublish(@RequestBody ProofListVO proofListVO) {
		try {
			List<ForcePublishProof> prooves = new ArrayList<ForcePublishProof>();
			for (ProofVO proofVO : proofListVO.getProofList()) {
				prooves.add(generateForcePublishProof(proofVO));
			}
			publishDateService.forcePublish(proofListVO.getId(), RequestThreadLocal.getLoginUserId(), prooves);
			return JsonBeanUtil.convertBeanToJsonBean(null);
		} catch (Exception e) {
			return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
		}
	}
	
	private ForcePublishProof generateForcePublishProof(ProofVO proofVO) {
		ForcePublishProof proof = new ForcePublishProof();
		ProofType type = proofVO.getType();
		proof.setType(type);
		switch (type) {
			case none :
				break;
			case online_approval :
				proof.setApprovalUrl(proofVO.getApprovalUrl());
				break;
			case offline_attachment :
				proof.setDownloadFilename(proofVO.getDownloadFilename());
				proof.setUploadFilename(proofVO.getUploadFilename());
				proof.setFileUrl(proofVO.getFileUrl());
				break;
			default :
				throw new CRMRuntimeException("force.proof.type.invalid");
		}
		
		return proof;
	}

	@RequestMapping("/terminate/{number}")
	@ResponseBody
	public JsonBean<Object> terminate(@PathVariable("number")String number) {
		try {
			publishService.terminateAll(number, RequestThreadLocal.getLoginUserId());
			return JsonBeanUtil.convertBeanToJsonBean(null);
		} catch (Exception e) {
			return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
		}
	}
	
	@RequestMapping("/materialUpdate/{number}")
	@ResponseBody
	public JsonBean<Object> materialUpdate(@PathVariable("number")String number) {
		try {
			publishService.updateMaterial(number, RequestThreadLocal.getLoginUserId());
			return JsonBeanUtil.convertBeanToJsonBean(null);
		} catch (Exception e) {
			return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
		}
	}
}
