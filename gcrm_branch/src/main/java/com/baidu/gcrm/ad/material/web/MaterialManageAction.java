package com.baidu.gcrm.ad.material.web;

import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.content.web.helper.AdSolutionContentCondition;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView4Material;
import com.baidu.gcrm.ad.material.model.MaterialApprovalRecord;
import com.baidu.gcrm.ad.material.service.IMaterialManageService;
import com.baidu.gcrm.ad.material.vo.MaterialApplyDetailVO;
import com.baidu.gcrm.ad.material.vo.MaterialApproveInfoView;
import com.baidu.gcrm.ad.material.vo.MaterialDetailVO;
import com.baidu.gcrm.ad.material.web.validator.MaterialValidator;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply.MaterialApplyState;
import com.baidu.gcrm.ad.model.AdvertiseMaterialMenu;
import com.baidu.gcrm.ad.service.IAdvertiseMaterialMenuService;
import com.baidu.gcrm.ad.web.vo.AdvertiseMaterialMenuVO;
import com.baidu.gcrm.ad.web.vo.AdvertiseMaterialVO;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.web.helper.ActivityInfo;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.IFileUploadService;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.datarole.IGCrmDataRoleService;
import com.baidu.gcrm.common.excel.ExcelReader;
import com.baidu.gcrm.common.excel.PoiRowCallback;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.common.util.GcrmHelper;
import com.baidu.gcrm.common.util.HttpHeaderUtil;
import com.baidu.gcrm.data.bean.ADContent;
import com.baidu.gcrm.data.bean.MaterialAttachment;
import com.baidu.gcrm.data.service.FileGenerate;
import com.baidu.gcrm.user.model.User;

@Controller
@RequestMapping("/material")
public class MaterialManageAction extends ControllerHelper {
    @Autowired
    IFileUploadService fileUploadService;

    Logger logger = LoggerFactory.getLogger(MaterialManageAction.class);

    /**
     * 物料流程定义
     */
    private String processDefineId = GcrmConfig.getConfigValueByKey("material.process.defineId");

    @Autowired
    private IMaterialManageService materialManageService;
    
    @Autowired
    private IAdSolutionContentService adContentService;
    
    @Autowired
    IAdvertiseMaterialMenuService advertiseMaterialMenuService;

    @Autowired
    IGCrmDataRoleService gcrmDataRoleService;
    
    @Autowired
    @Qualifier("materialDefaultGenerator")
    FileGenerate materialGenerator;
    
    /****
     * 查询广告内容列表
     */
    @RequestMapping("/queryAdSolutionContent")
    @ResponseBody
    public JsonBean<Page<AdSolutionContentView4Material>> queryAdSolutionContent(@RequestBody
    AdSolutionContentCondition condition) {
        List<Long>feasibityUserIds= gcrmDataRoleService.findFeasiblityUserIdList(getUserId());
        condition.setOperatorIdList(feasibityUserIds);
        
        Page<AdSolutionContentView4Material> resultPage = materialManageService.findAdSolutionContendByCondition(
                condition, currentLocale);
        return JsonBeanUtil.convertBeanToJsonBean(resultPage);
    }

    /**
     * 点击查看详细界面
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/{contentId}")
    public String showMaterialApplyView(@RequestParam("contentId")
    Long contentid, ModelMap model) {

        model.addAttribute("contentInfo", "");

        // 跳转物料修改界面
        return "/modifymaterial";
    }

    private static final String messagePrefix = "adSolution.content.material.approval.";

    @Autowired
    IBpmProcessService bpmProcessService;

    @RequestMapping("/approveview/{id}/{activityId}")
    @ResponseBody
    public JsonBean<MaterialApproveInfoView> showApproveInfoview(@PathVariable("id")
    Long id, @PathVariable("activityId")
    String activityId, HttpServletRequest request) {

        if (activityId == null || !activityId.contains("_")) {
            String activityIdMessage = MessageHelper.getMessage(messagePrefix + "activity.id.error", currentLocale);
            return JsonBeanUtil.convertBeanToJsonBean(null, activityIdMessage);
        }

        MaterialApproveInfoView infoView = new MaterialApproveInfoView();
        try {

            ActivityInfo activity = bpmProcessService.getActivityInfoByActivityId(activityId);
            
            if(id != null && !id.toString().equals(activity.getForeignKey())){
				return JsonBeanUtil.convertBeanToJsonBean(null, 
						MessageHelper.getMessage("operation.not.permit", currentLocale));
			}
			if(!activity.isRunning()){
				return JsonBeanUtil.convertBeanToJsonBean(null, "activity.complete.already",
						JsonBeanUtil.CODE_ERROR_IGNORE);
			}
            String performer = activity.getPerformer();

            if (StringUtils.isBlank(performer)) {
                String activityIdMessage = MessageHelper.getMessage(messagePrefix + "activity.nouser", currentLocale);
                return JsonBeanUtil.convertBeanToJsonBean(null, activityIdMessage);
            }

            if (performer.indexOf(getUuapUserName()) < 0) {
                // 如果当前用户与工作流对应的active操作人不对应
                LoggerHelper.info(getClass(), "任务参与人：{}", activity.getPerformer());
                String activityIdMessage = MessageHelper.getMessage(messagePrefix + "activity.user.norole",
                        currentLocale);
                return JsonBeanUtil.convertBeanToJsonBean(null, activityIdMessage);
            }

            infoView = buildMaterialApproveMain(id, activity.getParticipantId().name());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
        }

        return JsonBeanUtil.convertBeanToJsonBean(infoView);
    }

    /**
     * 构建物料审批详情页相关信息
     * 
     */
    private MaterialApproveInfoView buildMaterialApproveMain(Long id, String roleName) throws CRMBaseException {
        MaterialApproveInfoView materialApproveInfoView = materialManageService
                .findMaterialApproveInfoByMaterialApplyId(id, currentLocale);
        materialApproveInfoView.setTaskClose(false);
        materialApproveInfoView.setRole(roleName);
        return materialApproveInfoView;
    }

    @RequestMapping("/view")
    @ResponseBody
    public JsonBean<MaterialDetailVO> view(@RequestParam("contentId") Long contentId, HttpServletRequest request) {
    	if (null == adContentService.findOne(contentId)) {
    		String errMsg = MessageHelper.getMessage("adSolution.content.material.content.not.exist", currentLocale);
    		return JsonBeanUtil.convertBeanToJsonBean(null, errMsg, JsonBeanUtil.CODE_MESSAGE);
		}
        MaterialDetailVO materialDetailVO = null;
        try {
            materialDetailVO = materialManageService.findMaterialDetailVoByContentId(Long.valueOf(contentId), currentLocale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonBeanUtil.convertBeanToJsonBean(materialDetailVO);
    }

    @RequestMapping("/queryMaterialApplyDetail")
    @ResponseBody
    public JsonBean<MaterialApplyDetailVO> queryMaterialApplyDetailById(@RequestParam("applyId")
    Long applyId, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<String, String>();
        MaterialApplyDetailVO applyDetailVO = null;
        try {
            applyDetailVO = materialManageService.findMaterialApplyDetailVOById(applyId, currentLocale);
            if (null == applyDetailVO.getMaterialApply()) {
                errors.put("material.load.apply.error", "material.load.apply.error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonBeanUtil.convertBeanToJsonBean(applyDetailVO, errors);
    }

    @RequestMapping("/approve")
    @ResponseBody
    public JsonBean<String> approve(@RequestBody
    MaterialApprovalRecord materialApprovalRecord) {
        try {
            User currentOperator = getCurrentUser();
            materialManageService.saveAndCompleteApproval(materialApprovalRecord, currentOperator, currentLocale);
        } catch (Exception e) {
            logger.error("审批物料异常", e);
            return JsonBeanUtil.convertBeanToErrorJsonBean("error", e.getMessage());
        }
        return JsonBeanUtil.convertBeanToJsonBean("Success");
    }

    @RequestMapping("/delete/{applyId}")
    @ResponseBody
    public JsonBean<String> deleteMaterialApply(@PathVariable("applyId")
    Long applyId) {
        try {
            if (applyId != null) {
                materialManageService.delete(applyId);
            }
        } catch (Exception e) {
            logger.error("删除物料错误", e);
            return JsonBeanUtil.convertBeanToErrorJsonBean("error", e.getMessage());
        }
        return JsonBeanUtil.convertBeanToJsonBean("Success");
    }
    /**
     * 功能描述： 查看审批记录 创建人：cch 创建时间：2014-4-22 19:38:47 修改人：cch 修改时间：2014-4-22
     * 19:38:47 修改备注： 参数： @param materialApplyId 参数：
     * 
     * @return
     * @version
     */
    @RequestMapping("/findApproveRecord")
    @ResponseBody
    public Object findApproveRecord(@RequestParam("materialApplyId")
    String materialApplyId) {
        List<MaterialApprovalRecord> recordList = new ArrayList<MaterialApprovalRecord>();
        if (StringUtils.isNotBlank(materialApplyId)) {
            recordList = materialManageService.findRecordByMaterialApplyId(Long.valueOf(materialApplyId),
                    processDefineId, currentLocale);
        }
        return JsonBeanUtil.convertBeanToJsonBean(recordList);
    }

    /**
     * 功能描述： 查看申请单修改记录 创建人：cch 创建时间：2014-4-22 19:38:47 修改人：cch 修改时间：2014-4-22
     * 19:38:47 修改备注： 参数：
     * 
     * @param materialApplyId
     *            参数： @return
     * 
     * @version
     */
    @RequestMapping("/findModifyRecord")
    @ResponseBody
    public Object findChangeHistoryRecord(@RequestParam("materialApplyId")
    String materialApplyId) {
        List<Map<String, Object>> recordList = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotBlank(materialApplyId)) {
            try {
                recordList = materialManageService.findChangeHistoryRecord(materialApplyId, currentLocale);
            } catch (CRMBaseException e) {
                logger.error(e.getMessage(), e);
                return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
            }
        }
        return JsonBeanUtil.convertBeanToJsonBean(recordList);
    }

    @ExceptionHandler(Exception.class)
    public String materialManageExceptionHandler(Exception exception) {

        logger.error("物料管理出现异常：", exception);

        return "exception";
    }

    /**
     * 上传物料图片、嵌入代码文件
     * 
     * @author zhanglei35
     * @param advertiseMaterial
     * @param resp
     */
    @RequestMapping("/uploadMaterialFile")
    @ResponseBody
    public void uploadAdSolutionContentMaterial(AdvertiseMaterial advertiseMaterial, HttpServletResponse resp) {
        String jsonStr = null;
        PrintWriter writer = null;
        resp.setContentType("text/html; charset=UTF-8");
        try {
            writer = resp.getWriter();
            MultipartFile file = advertiseMaterial.getMaterialFile();
            if (file != null && !file.isEmpty()) {
                String uploadFileName = new StringBuilder().append("/").append(UUID.randomUUID().toString()).toString();
                String fileUrl = fileUploadService.upload(uploadFileName, file.getBytes());
                AdvertiseMaterialVO vo = new AdvertiseMaterialVO();
                vo.setFileUrl(fileUrl);
                vo.setUploadFileName(uploadFileName);
                vo.setDownloadFileName(file.getOriginalFilename());
                vo.setFileSize(Integer.valueOf(String.valueOf(file.getSize() < 1024 ? 1 : file.getSize() / 1024))); // 小于1kb约等于1kb
                BufferedImage sourceImg = ImageIO.read(file.getInputStream());
                if (null != sourceImg) {
                    vo.setPicWidth(sourceImg.getWidth());
                    vo.setPicHeight(sourceImg.getHeight());
                }
                jsonStr = JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(vo));
            } else {
                jsonStr = JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(null,
                        MessageHelper.getMessage("adSolution.content.material.attachment.empty", currentLocale)));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            jsonStr = JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(null, "upload fail"));
        }
        if (writer != null) {
            writer.println(jsonStr);
        }
    }
    
    /**
     * 上传图片类型物料下拉菜单附件
     * 
     * @author gaofuchun
     * @param advertiseMaterialMenu
     * @param resp
     */
    @RequestMapping("/uploadMaterialMenuFile")
    @ResponseBody
    public void uploadAdSolutionContentMaterialMenu(AdvertiseMaterialMenu advertiseMaterialMenu, HttpServletResponse resp) {
        String jsonStr = null;
        PrintWriter writer = null;
        resp.setContentType("text/html; charset=UTF-8");
        try {
            writer = resp.getWriter();
            MultipartFile file = advertiseMaterialMenu.getMaterialFile();
            if (file != null && !file.isEmpty()) {
                String uploadFileName = new StringBuilder().append("/").append(UUID.randomUUID().toString()).toString();
                String fileUrl = fileUploadService.upload(uploadFileName, file.getBytes());
                AdvertiseMaterialMenuVO vo = new AdvertiseMaterialMenuVO();
                vo.setFileUrl(fileUrl);
                vo.setUploadFileName(uploadFileName);
                vo.setDownloadFileName(file.getOriginalFilename());
                vo.setFileSize(Integer.valueOf(String.valueOf(file.getSize() < 1024 ? 1 : file.getSize() / 1024))); // 小于1kb约等于1kb
                BufferedImage sourceImg = ImageIO.read(file.getInputStream());
                if (null != sourceImg) {
                    vo.setPicWidth(sourceImg.getWidth());
                    vo.setPicHeight(sourceImg.getHeight());
                }
                jsonStr = JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(vo));
            } else {
                jsonStr = JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(null,
                        MessageHelper.getMessage("adSolution.content.material.attachment.empty", currentLocale)));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            jsonStr = JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(null, "upload fail"));
        }
        if (writer != null) {
            writer.println(jsonStr);
        }
    }


    /**
     * 删除物料图片、嵌入代码文件
     * 
     * @author zhanglei35
     * @param fileId
     * @return
     */
    // @RequestMapping("/deleteMaterialFile/{materialId}")
    // @ResponseBody
    // public JsonBean<AdvertiseMaterialVO>
    // deleteAdSolutionContentMaterial(@PathVariable("materialId") Long fileId)
    // {
    // try {
    // if (fileId != null) {
    // AdvertiseMaterial existsAdvertiseMaterial =
    // materialManageService.findMaterialFileById(fileId);
    // try {
    // fileUploadService.deleteFile(existsAdvertiseMaterial.getUploadFileName());
    // } catch (Exception e) {
    // logger.error("删除云端文件出错" + e.getMessage());
    // }
    // materialManageService.deleteMateialFileById(fileId);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // return JsonBeanUtil.convertBeanToJsonBean(null, "delete fail");
    // }
    // return JsonBeanUtil.convertBeanToJsonBean(null);
    // }

    /**
     * 下载物料文件，图片直接打开
     * 
     * @param response
     * @param materialFileId
     */
    @RequestMapping("downloadMaterialFile")
    public void downloadMaterialFile(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("materialFileId")
            Long materialFileId) {
        AdvertiseMaterial material = materialManageService.findMaterialFileById(materialFileId);
        try {
            String downloadFileName = material.getDownloadFileName();
            if (PatternUtil.isBlank(downloadFileName)) {
                return;
            }
            HttpHeaderUtil.setDownloadHeader(downloadFileName, request, response, false);
            FileCopyUtils.copy(fileUploadService.download(material.getUploadFileName()), response.getOutputStream());
        } catch (Exception e) {
            logger.error("=====download advertise material file fail====" + e.getMessage());
        }
    }
    
    /**
     * 下载物料文件，直接下载
     * 
     * @param response
     * @param materialFileId
     */
    @RequestMapping("downloadMaterialFileDirect")
    public void downloadMaterialFileDirect(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("materialFileId")
            Long materialFileId) {
        AdvertiseMaterial material = materialManageService.findMaterialFileById(materialFileId);
        try {
            String downloadFileName = material.getDownloadFileName();
            if (PatternUtil.isBlank(downloadFileName)) {
                return;
            }
            HttpHeaderUtil.setDownloadHeader(downloadFileName, request, response, true);
            FileCopyUtils.copy(fileUploadService.download(material.getUploadFileName()), response.getOutputStream());
        } catch (Exception e) {
            logger.error("=====download advertise material file fail====" + e.getMessage());
        }
    }
    
    /**
     * 下载图片类型物料下拉菜单文件
     * 
     * @param response
     * @param response
     * @param materialMenuFileId
     * @param applyId
     */
    @RequestMapping("downloadMaterialMenuFile/{isDirectDownload}")
    public void downloadMaterialMenuFile(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("materialMenuFileId")
            Long materialMenuFileId, @PathVariable("isDirectDownload") Boolean isDirectDownload) {
        AdvertiseMaterialMenu materialMenu = advertiseMaterialMenuService.findMaterialMenuById(materialMenuFileId);
        try {
            String downloadFileName = materialMenu.getDownloadFileName();
            if (PatternUtil.isBlank(downloadFileName)) {
                return;
            }
            HttpHeaderUtil.setDownloadHeader(downloadFileName, request, response, isDirectDownload);
            FileCopyUtils.copy(fileUploadService.download(materialMenu.getUploadFileName()), response.getOutputStream());
        } catch (Exception e) {
            logger.error("=====download advertise material menu file fail====" + e.getMessage());
        }
    }
    
    /**
     * 保存物料信息（新增、变更、修改、恢复）
     * 
     * @author zhanglei35
     * @param applyDetailVO
     * @param bindingResult
     * @return @RequestBody
     */
    @RequestMapping("/saveMaterialApplyDetail")
    @ResponseBody
    public synchronized JsonBean<MaterialDetailVO> saveMaterialApplyDetail(@RequestBody
    MaterialApplyDetailVO applyDetailVO, BindingResult bindingResult) {
        MaterialDetailVO detailVO = new MaterialDetailVO();
        try {
            // 输入校验
            ValidationUtils.invokeValidator(new MaterialValidator(), applyDetailVO, bindingResult);
            if (bindingResult.hasErrors()) {
                return JsonBeanUtil.convertBeanToJsonBean(detailVO, super.collectErrors(bindingResult),
                        JsonBeanUtil.CODE_ERROR);
            }
            if (null == applyDetailVO.getMaterialApply()) {
                return JsonBeanUtil.convertBeanToJsonBean(detailVO,
                        MessageHelper.getMessage("material.save.apply.error", currentLocale));
            }
            generatePropertyForCreateOrUpdateAll(applyDetailVO);
            User user = getCurrentUser();
            detailVO = materialManageService.saveMaterialApplyDetail(applyDetailVO, user, currentLocale,
                    processDefineId);
            return JsonBeanUtil.convertBeanToJsonBean(detailVO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return JsonBeanUtil.convertBeanToJsonBean(detailVO, e.getMessage());
        }
    }

    /**
     * 生成对象的操作人、操作时间
     * 
     * @author zhanglei35
     * @param applyDetailVO
     */
    private void generatePropertyForCreateOrUpdateAll(MaterialApplyDetailVO applyDetailVO) {
        AdvertiseMaterialApply materialApply = applyDetailVO.getMaterialApply();
        // 所有操作（新增、变更、修改、恢复）的物料单均为待提交，在工作流提交成功后，变为审核中
        materialApply.setApplyState(MaterialApplyState.create);
        if (null == materialApply.getId()) {
            generatePropertyForCreate(materialApply);
        } else {
            generatePropertyForUpdate(materialApply);
        }
        List<AdvertiseMaterial> materialFileList = applyDetailVO.getMaterialApply().getMaterialList();
        //materialFileList.add(applyDetailVO.getCodeFile());
        for (AdvertiseMaterial file : materialFileList) {
            if (null == file.getId()) {
                generatePropertyForCreate(file);
            } else {
                generatePropertyForUpdate(file);
            }
        }
        
        List<AdvertiseMaterialMenu> materialMenuList = applyDetailVO.getMaterialApply().getMaterialMenuList();
        for (AdvertiseMaterialMenu file : materialMenuList) {
            if (null == file.getId()) {
                generatePropertyForCreate(file);
            } else {
                generatePropertyForUpdate(file);
            }
        }
    }

    /**
     * 作废物料单
     * 
     * @author zhanglei35
     * @param applyId
     * @return
     */
    @RequestMapping("/cancelMaterialApply/{applyId}")
    @ResponseBody
    public JsonBean<MaterialDetailVO> cancelMaterialApply(@PathVariable("applyId")
    Long applyId, HttpServletRequest request) {
        MaterialDetailVO detailVO = new MaterialDetailVO();
        if (StringUtils.isEmpty(String.valueOf(applyId))) {
            return JsonBeanUtil.convertBeanToJsonBean(detailVO);
        }
        try {
            User user = getCurrentUser();
            detailVO = materialManageService.cancelMaterialApplyById(applyId, user, currentLocale, processDefineId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonBeanUtil.convertBeanToJsonBean(detailVO);
    }

    /**
     * 取消在审的物料申请单
     * 
     * @param applyId
     * @return
     */
    @RequestMapping("/withdrawMaterialApply/{applyId}")
    @ResponseBody
    public JsonBean<MaterialDetailVO> withdrawMaterialApplyProcess(@PathVariable("applyId")
    Long applyId) {
        MaterialDetailVO detailVO = new MaterialDetailVO();
        if (StringUtils.isEmpty(String.valueOf(applyId))) {
            return JsonBeanUtil.convertBeanToJsonBean(detailVO);
        }
        try {
            User user = getCurrentUser();
            AdvertiseMaterialApply materialApply = new AdvertiseMaterialApply();
            materialApply.setId(applyId);
            detailVO = materialManageService.withdrawMaterialApplyProcessById(materialApply, user, currentLocale,
                    processDefineId, MaterialApplyState.create, messagePrefix + "withdraw");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonBeanUtil.convertBeanToJsonBean(detailVO);
    }

    @RequestMapping("/reminders/{id}")
    @ResponseBody
    public JsonBean<String> remindersMaterial(@PathVariable("id")
    Long materialId, HttpServletRequest request) {
        materialManageService.remindersContentByMail(materialId, currentLocale);
        return JsonBeanUtil.convertBeanToJsonBean("success");

    }

    @RequestMapping("/importDefault")
    @ResponseBody
    public JsonBean<String> doImportFile(MultipartHttpServletRequest multipartRequest) {
        String logmessage = "导入默认广告,时间:%s,操作人:%s,文件名:%s,导入成功:%s";
        Iterator<String> it = multipartRequest.getFileNames();
        MultipartFile mpf = null;
        String fileName = null;
        while (it.hasNext()) {
            fileName = it.next();
            mpf = multipartRequest.getFile(fileName);
            break;
        }
        final List<ADContent> contents = new ArrayList<ADContent>();

        try {
            ExcelReader.read(mpf.getInputStream(), new PoiRowCallback() {
                long i = 1l;
                String dateStr = DateUtils.getCurrentFormatDate(DateUtils.YYMMDD);
                List<MaterialAttachment> mas = null;

                @Override
                public void call(Row row) {
                    if (row.getRowNum() == 0) {
                        return;
                    } else {
                        ADContent content = new ADContent();
                        content.setId(i++);// 应该保证此处ID与正常广告内容id不重复
                        content.setPositionNumber(GcrmHelper.getPositionNumber(getStringCell(0, row)
                                .getStringCellValue()));
                        content.setMaterialUrl(getStringCell(3, row).getStringCellValue());
                        content.setMaterialTitle(getStringCell(4, row).getStringCellValue());
                        content.setMaterialType(getStringCell(5, row).getStringCellValue());
                        content.setMaterialCodeContent(getStringCell(6, row).getStringCellValue());
                        MaterialAttachment ma =
                                new MaterialAttachment(getStringCell(7, row).getStringCellValue(), generateFileName(
                                        new StringBuilder(dateStr).append("-").append(content.getPositionNumber())
                                                .append("-").append(content.getId()).toString(), getStringCell(7, row)
                                                .getStringCellValue(), getStringCell(8, row).getStringCellValue()));
                        mas = new ArrayList<MaterialAttachment>();
                        mas.add(ma);
                        content.setMaterialAttachments(mas);
                        contents.add(content);
                    }

                }
            });
            materialGenerator.generateFile(contents);
            materialGenerator.generateMD5File();
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "生成同步给ecom的默认物料文件出错:", e);
            LoggerHelper.info(getClass(), String.format(logmessage, new Date(), getUserName(), fileName, 0));
            return JsonBeanUtil.convertBeanToJsonBean(null,
                    MessageHelper.getMessage("generate.defaultmaterial.error", getCurrentLocale()),
                    JsonBeanUtil.CODE_ERROR_MESSAGE);
        }
        LoggerHelper.info(getClass(), String.format(logmessage, new Date(), getUserName(), fileName, 1));
        return JsonBeanUtil.convertBeanToJsonBean("success");
    }

    private String generateFileName(String fileRule, String fileUrl, String fileName) {
        String name = "";
        if (StringUtils.isNotBlank(fileName)) {
            name = fileName;
        } else {
            String fileSuffix = "";
            if (StringUtils.isNotBlank(fileUrl) && fileUrl.lastIndexOf(".") > -1) {
                fileSuffix = fileUrl.substring(fileUrl.lastIndexOf("."));
            }
            name = fileRule + fileSuffix;
        }
        return name;
    }

    @RequestMapping("/initOnlineMaterial")
    @ResponseBody
    public JsonBean<String> initMaterial() {
        materialManageService.initMaterialByCondition();
        return JsonBeanUtil.convertBeanToJsonBean("success");
    }

    private Cell getStringCell(int index, Row row) {
        Cell cell = row.getCell(index, Row.CREATE_NULL_AS_BLANK);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell;
    }

    /**
     * 
     * 功能描述:获取当前用户 getCurrentUser 创建人: chenchunhui01 创建时间: 2014年5月7日 下午3:35:23
     * 
     * @return
     * @return User
     * @exception
     * @version
     */
    private User getCurrentUser() {
        User user = new User();
        user.setUcid(getUserId());
        user.setUsername(getUserName());
        user.setUuapName(getUuapUserName());
        user.setRealname(getChineseName());
        user.setRole("Saler");
        return user;
    }
}