package com.baidu.gcrm.ad.material.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.dao.IAccountRepository;
import com.baidu.gcrm.ad.content.dao.IAdSolutionContentRepositoryCustom;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.content.web.helper.AdSolutionContentCondition;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView4Material;
import com.baidu.gcrm.ad.dao.AdvertiseMaterialApplyRepository;
import com.baidu.gcrm.ad.dao.AdvertiseMaterialRepository;
import com.baidu.gcrm.ad.dao.AdvertiseSolutionRepository;
import com.baidu.gcrm.ad.material.dao.IMaterialMenuRepository;
import com.baidu.gcrm.ad.material.dao.MaterialApproveRecordRepository;
import com.baidu.gcrm.ad.material.model.MaterialApprovalRecord;
import com.baidu.gcrm.ad.material.service.IMaterialManageService;
import com.baidu.gcrm.ad.material.vo.MaterialApplyDetailVO;
import com.baidu.gcrm.ad.material.vo.MaterialApplyVO;
import com.baidu.gcrm.ad.material.vo.MaterialApproveInfoView;
import com.baidu.gcrm.ad.material.vo.MaterialContentVO;
import com.baidu.gcrm.ad.material.vo.MaterialDetailVO;
import com.baidu.gcrm.ad.material.vo.MaterialDetailVO.MaterialSaveType;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterial.MaterialFileType;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply.MaterialApplyState;
import com.baidu.gcrm.ad.model.AdvertiseMaterialMenu;
import com.baidu.gcrm.bpm.dao.IProcessNameI18nRepository;
import com.baidu.gcrm.bpm.model.ProcessNameI18n;
import com.baidu.gcrm.bpm.service.IAssignmentHandler;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.service.IBpmProcessStartService;
import com.baidu.gcrm.bpm.service.IGCrmTaskInfoService;
import com.baidu.gcrm.bpm.vo.CompleteActivityResponse;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.bpm.vo.RemindRequest;
import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.vo.StartProcessResponse;
import com.baidu.gcrm.bpm.web.helper.BaseStartProcessBean;
import com.baidu.gcrm.bpm.web.helper.CompleteBaseActivityReq;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.common.random.IRandomCheckCallback;
import com.baidu.gcrm.common.random.IRandomStringService;
import com.baidu.gcrm.common.random.RandomType;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.log.service.ModifyRecordConstant;
import com.baidu.gcrm.log.service.ModifyRecordService;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.resource.position.dao.PositionRepository;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionMaterialType;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gwfp.ws.exception.GWFPException;

@Service
public class MaterialManageServiceImpl implements IMaterialManageService {
    private Logger logger = LoggerFactory.getLogger(MaterialManageServiceImpl.class);

    @Autowired
    private AdvertiseMaterialApplyRepository adMaterialApplyRepository;
    
    @Autowired
    private IAdSolutionContentService contentService;

    @Autowired
    private AdvertiseSolutionRepository adSolutionDao;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    IAccountRepository accountRespository;

    @Autowired
    private AdvertiseMaterialRepository adMaterialRepository;

    @Autowired
    IAdSolutionContentRepositoryCustom adSolutionContentRepositoryCustom;

    @Autowired
    IMaterialMenuRepository materialMenuRepository;

    @Autowired
    private I18nKVService i18nKVService;

    @Autowired
    AdvertisingPlatformServiceImpl adPlatformService;

    @Autowired
    @Qualifier("baseProcessServiceImpl")
    IBpmProcessStartService baseProcessService;

    @Autowired
    private MaterialApproveRecordRepository materialApproveRecordDao;

    @Autowired
    private IProcessNameI18nRepository processNameDao;

    @Autowired
    private ModifyRecordService modifyRecordService;

    @Autowired
    IBpmProcessService processService;

    @Autowired
    IPublishService publishService;

    private static final String messagePrefix = "adSolution.content.material.approval.";

    @Autowired
    IRandomStringService randomService;

    @Autowired
    IGCrmTaskInfoService gcrmTaskInfoService;

    @Autowired
    IUserService userService;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 根据动态查询条件查询广告内容（物料管理使用) add by cch at 2014-04-19
     * 
     */
    @Override
    public Page<AdSolutionContentView4Material> findAdSolutionContendByCondition(AdSolutionContentCondition condition,
            LocaleConstants locale) {
        Page<AdSolutionContentView4Material> page = null;
        try {
            page = adSolutionContentRepositoryCustom.finSolutionContentByConditon(condition);
            processOtherInfo(page, locale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    /**
     * 拼裝投放位置信息 将平台 站点 频道 区域 位置 拼接于一个字段
     * 
     */
    private void processOtherInfo(PageWrapper<AdSolutionContentView4Material> page, LocaleConstants locale) {
        StringBuffer throwPlace = new StringBuffer();
        List<AdSolutionContentView4Material> resultContents = page.getContent();
        String i18nValue;
        for (AdSolutionContentView4Material content : resultContents) {
            throwPlace.delete(0, throwPlace.length());
            Long platformId = content.getThrowPlatformId();
            i18nValue = adPlatformService.getByIdAndLocale(platformId, locale).getI18nName();
            throwPlace.append(i18nValue);

            Long siteId = content.getThrowSiteId();
            if (!buildThrowPlace(throwPlace, Site.class, siteId, locale)) {
                return;
            }
            Long channelId = content.getThrowChannelId();
            if (!buildThrowPlace(throwPlace, Position.class, channelId, locale)) {
                return;
            }
            Long areaId = content.getThrowAreaId();
            if (!buildThrowPlace(throwPlace, Position.class, areaId, locale)) {
                return;
            }
            Long positionId = content.getThrowPositionId();
            if (!buildThrowPlace(throwPlace, Position.class, positionId, locale)) {
                return;
            }

            content.setThrowPlace(throwPlace.toString());
        }
    }

    /**
     * 拼接投放位置
     */
    private boolean buildThrowPlace(StringBuffer throwPlace, @SuppressWarnings("rawtypes") Class classz, Long id,
            LocaleConstants locale) {
        String i18nValue = i18nKVService.getI18Info(classz, id, locale);
        if (StringUtils.isBlank(i18nValue)) {
            return false;
        }
        throwPlace.append("/" + i18nValue);
        return true;
    }

    @Override
    public List<MaterialApplyVO> findMaterialApplyListByContentId(Long contentId) {
        List<MaterialApplyVO> applyVoList = new LinkedList<MaterialApplyVO>();
        List<AdvertiseMaterialApply> materialApplyList = adMaterialApplyRepository.findByContentIdInOrder(contentId);
        for (AdvertiseMaterialApply apply : materialApplyList) {
            applyVoList.add(this.processApplyVo(apply));
        }
        return applyVoList;
    }

    @Override
    public MaterialDetailVO findMaterialDetailVoByContentId(Long contentId, LocaleConstants locale) throws Exception {
        // 设置物料关联的广告内容VO
        MaterialDetailVO materialDetailVo = new MaterialDetailVO();
        MaterialContentVO contentVO = this.findAdContentVoById(contentId, locale);
        materialDetailVo.setMaterialContentVo(contentVO);
        // 如果当前广告内容有关联物料单，则变更，否则为新增
        if (isHasMaterialApply(contentId)) {
            materialDetailVo.setMaterialSaveType(MaterialSaveType.change.ordinal());
        } else {
            materialDetailVo.setMaterialSaveType(MaterialSaveType.create.ordinal());
        }
        // 设置物料单列表
        materialDetailVo.setMaterialApplyVoList(this.findMaterialApplyListByContentId(contentId));
        return materialDetailVo;
    }

    /**
     * 判断当前广告内容是否有关联的物料单
     * 
     * @param contentId
     * @return
     */
    private boolean isHasMaterialApply(Long contentId) {
        return adMaterialApplyRepository.findCountByContentId(contentId) > 0 ? true : false;
    }

    /**
     * 装载物料单VO
     * 
     * @param materialApply
     * @return
     */
    private MaterialApplyVO processApplyVo(AdvertiseMaterialApply materialApply) {
        MaterialApplyVO applyVo = new MaterialApplyVO();
        if (null == materialApply) {
            return applyVo;
        }
        applyVo.setApplyId(materialApply.getId());
        applyVo.setNumber(materialApply.getNumber());
        applyVo.setApplyState(materialApply.getApplyState().ordinal());
        applyVo.setCreateTime(materialApply.getCreateTime());
        applyVo.setCreateOperator(materialApply.getCreateOperator());
        User user = userService.findByUcid(materialApply.getCreateOperator());
        if (user != null) {
            applyVo.setCreateOperatorName(user.getRealname());
        }
        // 是否有审批记录
        int hasRecords = 0;
        Long recordCount = materialApproveRecordDao.findRecordCountByMaterialApplyId(materialApply.getId());
        if (recordCount > 0) {
            hasRecords = 1;
        }
        applyVo.setHasRecords(hasRecords);

        return applyVo;
    }

    @Override
    public MaterialApplyDetailVO findMaterialApplyDetailVOById(Long applyId, LocaleConstants locale) {
        AdvertiseMaterialApply materialApply = adMaterialApplyRepository.findOne(applyId);
        return this.processApplyDetailVo(materialApply, locale);
    }

    /**
     * 根据物料申请单ID查询物料审批界面详细信息
     * 
     */
    public MaterialApproveInfoView findMaterialApproveInfoByMaterialApplyId(Long id, LocaleConstants locale) {
        MaterialApproveInfoView materialApproveInfoView = new MaterialApproveInfoView();
        AdvertiseMaterialApply materialApply = adMaterialApplyRepository.findById(id);
        materialApproveInfoView.setMaterialApplyVO(this.processApplyVo(materialApply));
        materialApproveInfoView.setMaterialApplyDetailVO(this.processApplyDetailVo(materialApply, locale));
        Long contentId = materialApply.getAdSolutionContentId();
        materialApproveInfoView.setMaterialContentVO(this.findAdContentVoById(contentId, locale));
        return materialApproveInfoView;
    }

    /**
     * 装载物料单详情VO
     * 
     * @param materialApply 物料单BO
     * @param locale
     * @return
     */
    private MaterialApplyDetailVO processApplyDetailVo(AdvertiseMaterialApply materialApply, LocaleConstants locale) {
        MaterialApplyDetailVO applyVo = new MaterialApplyDetailVO();
        if (null == materialApply) {
            return applyVo;
        }
        // taskinfo转换
        String taskInfo = gcrmTaskInfoService.convertTaskInfo(RemindType.material, materialApply.getTaskInfo(), locale);
        materialApply.setTaskInfo(taskInfo);
        applyVo.setMaterialApply(materialApply);
        applyVo.setApplyState(materialApply.getApplyState().ordinal());

        List<AdvertiseMaterial> imageList =
                adMaterialRepository.findByMaterialApplyIdAndFileType(materialApply.getId(), MaterialFileType.IMAGE);
        for (AdvertiseMaterial imageFile : imageList) {
            imageFile.setDownloadUrl(getDownloadURL(imageFile.getId()));
        }
        applyVo.getMaterialApply().setMaterialList(imageList);

        // 图片类型物料下拉菜单
        List<AdvertiseMaterialMenu> materialMenuList =
                materialMenuRepository.findByMaterialApplyIdOrderByIdAsc(materialApply.getId());
        for (AdvertiseMaterialMenu materialMenu : materialMenuList) {
            materialMenu.setDownloadUrl(getMaterialMenuDownloadURL(materialMenu.getId()));
        }
        applyVo.getMaterialApply().setMaterialMenuList(materialMenuList);

        return applyVo;
    }

    private String getDownloadURL(Long id) {
        String moduleName = RequestThreadLocal.getContentPath();

        StringBuffer requestURL = RequestThreadLocal.getRequestURL();
        if (requestURL == null || requestURL.length() == 0) {
            requestURL.append(GcrmConfig.getConfigValueByKey("app.url"));
        }
        int endPos = requestURL.indexOf(moduleName);
        StringBuffer url = new StringBuffer();
        url.append(requestURL.substring(0, endPos + moduleName.length()));
        url.append("/material/downloadMaterialFile?materialFileId=");
        url.append(id);
        return url.toString();
    }

    private String getMaterialMenuDownloadURL(Long id) {
        String moduleName = RequestThreadLocal.getContentPath();

        StringBuffer requestURL = RequestThreadLocal.getRequestURL();
        if (requestURL == null || requestURL.length() == 0) {
            requestURL.append(GcrmConfig.getConfigValueByKey("app.url"));
        }
        int endPos = requestURL.indexOf(moduleName);
        StringBuffer url = new StringBuffer();
        url.append(requestURL.substring(0, endPos + moduleName.length()));
        url.append("/material/downloadMaterialMenuFile/false?materialMenuFileId=");
        url.append(id);
        return url.toString();
    }
    
    @Override
    public MaterialContentVO findAdContentVoById(Long contentId, LocaleConstants locale) {
        AdSolutionContent adContent = contentService.findOne(contentId);
        if (null == adContent) {
            return null;
        }
        StringBuilder positionName =
                new StringBuilder(adPlatformService.getByIdAndLocale(adContent.getProductId(), locale).getI18nName());
        positionName.append("/").append(i18nKVService.getI18Info(Site.class, adContent.getSiteId(), locale))
                .append("/").append(i18nKVService.getI18Info(Position.class, adContent.getChannelId(), locale))
                .append("/").append(i18nKVService.getI18Info(Position.class, adContent.getAreaId(), locale))
                .append("/").append(i18nKVService.getI18Info(Position.class, adContent.getPositionId(), locale));
        adContent.setPositionName(positionName.toString());
        return processAdContentVo(adContent);
    }

    @Override
    public MaterialContentVO findAdContVoMaterByContId(Long contentId) {
        AdSolutionContent adContent = contentService.findOne(contentId);
        return findAdContVoMaterByCont(adContent);
    }

    @Override
    public MaterialContentVO findAdContVoMaterByCont(AdSolutionContent content) {
        // 如果物料信息完全为空，返回null
        if (isMaterEmptyByPosMaterType(content)) {
            return null;
        }
        MaterialContentVO contentVO = new MaterialContentVO();
        contentVO.setNumber(content.getNumber());
        contentVO.setAdContentId(content.getId());
        fillContentVOMaterial(contentVO, content);
        // 设置物料信息是否完整字段
        contentVO.setMaterialFull(isMaterFullByPosMaterType(content));
        return contentVO;
    }

    @Override
    public MaterialContentVO findFullAdContVoMaterByContent(AdSolutionContent content) {
        MaterialContentVO contentVO = new MaterialContentVO();
        contentVO.setNumber(content.getNumber());
        contentVO.setAdContentId(content.getId());
        fillContentVOMaterial(contentVO, content);
        // 设置物料信息是否完整字段
        contentVO.setMaterialFull(isMaterFullByPosMaterType(content));
        if (!contentVO.isMaterialFull()) {
            return null;
        }
        return contentVO;
    }

    @Override
    public MaterialContentVO findAdContVoMaterByMaterNumber(String materNumber) {
        AdvertiseMaterialApply materialApply = adMaterialApplyRepository.findByNumber(materNumber);
        AdSolutionContent adContent = contentService.findOne(materialApply.getAdSolutionContentId());
        if (isMaterEmptyByPosMaterType(adContent)) {
            return null;
        }
        MaterialContentVO contentVO = new MaterialContentVO();
        Position position = positionRepository.findOne(adContent.getPositionId());
        contentVO.setMaterialType(position.getMaterialType().ordinal());
        fillContentVOMaterial(contentVO, materialApply);
        return contentVO;
    }

    /**
     * 填充广告内容VO中的审批通过的物料信息
     * 
     * @param contentVO
     * @param adContent
     */
    private void fillContentVOMaterial(MaterialContentVO contentVO, AdSolutionContent adContent) {
        // 设置位置类型等相关字段
        Position position = positionRepository.findOne(adContent.getPositionId());
        contentVO.setMaterialType(position.getMaterialType().ordinal());
        contentVO.setAreaRequired(position.getAreaRequired());
        contentVO.setSize(position.getSize());
        contentVO.setTextlinkLength(position.getTextlinkLength());
        // 查找审批通过的物料单，根据其ID找到物料的文件
        List<AdvertiseMaterialApply> materialApplyList =
                adMaterialApplyRepository.findMaterialApplyByContentIdAndState(adContent.getId(),
                        MaterialApplyState.confirm);
        if (CollectionUtils.isNotEmpty(materialApplyList)) {
            AdvertiseMaterialApply apply = materialApplyList.get(0);
            List<AdvertiseMaterial> imageList =
                    adMaterialRepository.findByMaterialApplyIdAndFileType(apply.getId(), MaterialFileType.IMAGE);
            for (AdvertiseMaterial imageFile : imageList) {
                imageFile.setDownloadUrl(getDownloadURL(imageFile.getId()));
            }
            // 图片类型物料下拉菜单
            List<AdvertiseMaterialMenu> materialMenuList = materialMenuRepository.
                    findByMaterialApplyIdOrderByIdAsc(apply.getId());
            for (AdvertiseMaterialMenu materialMenu : materialMenuList) {
                materialMenu.setDownloadUrl(getMaterialMenuDownloadURL(materialMenu.getId()));
            }
            contentVO.setMaterialMenuList(materialMenuList);
            contentVO.setMaterialList(imageList);
            contentVO.setMaterialNumber(apply.getNumber());
            contentVO.setMaterialUrl(apply.getMaterialUrl());
            contentVO.setMaterialTitle(apply.getMaterialTitle());
            contentVO.setMaterialEmbedCodeContent(apply.getMaterialEmbedCodeContent());
            contentVO.setMonitorUrl(apply.getMonitorUrl());
            contentVO.setMaterialFileType(apply.getMaterialFileType());
            contentVO.setMaterialPeriodDescription(apply.getPeriodDescription());
        }
    }

    /**
     * 填充广告内容VO中的物料信息
     * 
     * @param contentVO
     * @param materialApply
     */
    private void fillContentVOMaterial(MaterialContentVO contentVO, AdvertiseMaterialApply materialApply) {
        contentVO.setMaterialTitle(materialApply.getMaterialTitle());
        contentVO.setMaterialUrl(materialApply.getMaterialUrl());
        contentVO.setMaterialNumber(materialApply.getNumber());
        contentVO.setMaterialFileType(materialApply.getMaterialFileType());
        contentVO.setMaterialEmbedCodeContent(materialApply.getMaterialEmbedCodeContent());
        contentVO.setMonitorUrl(materialApply.getMonitorUrl());
        List<AdvertiseMaterial> imageList =
                adMaterialRepository.findByMaterialApplyIdAndFileType(materialApply.getId(), MaterialFileType.IMAGE);
        for (AdvertiseMaterial imageFile : imageList) {
            imageFile.setDownloadUrl(getDownloadURL(imageFile.getId()));
        }
        contentVO.setMaterialList(imageList);
    }

    @Override
    public boolean isMaterFullByPosMaterType(Long contentId) {
        AdSolutionContent adContent = contentService.findOne(contentId);
        return isMaterFullByPosMaterType(adContent);
    }

    @Override
    public boolean isMaterFullByPosMaterType(AdSolutionContent content) {
        if (null == content.getPositionId()) {
            return false;
        }
        return contentService.isContentMaterialFull(content);
    }

    @Override
    public boolean isMaterEmptyByPosMaterType(Long contentId) {
        MaterialContentVO contentVO = new MaterialContentVO();
        AdSolutionContent adContent = contentService.findOne(contentId);
        fillContentVOMaterial(contentVO, adContent);
        Position position = positionRepository.findOne(adContent.getPositionId());
        if (position.getMaterialType() == PositionMaterialType.textlink) {
            // 文字链类型的物料必填字段：title, url
            if (!StringUtils.isEmpty(contentVO.getMaterialTitle()) || !StringUtils.isEmpty(contentVO.getMaterialUrl())
                    || (null != contentVO.getMaterialList() && contentVO.getMaterialList().size() > 0)) {
                return false;
            }
        } else if (position.getMaterialType() == PositionMaterialType.image) {
            // 图片类型的物料必填字段：imageList, url
            if ((null != contentVO.getMaterialList() && contentVO.getMaterialList().size() > 0)
                    || !StringUtils.isEmpty(contentVO.getMaterialUrl())) {
                return false;
            }
        } else if (position.getMaterialType() == PositionMaterialType.image_and_textlink) {
            // 图片类型的物料必填字段：imageList, url, title
            if ((null != contentVO.getMaterialList() && contentVO.getMaterialList().size() > 0)
                    || !StringUtils.isEmpty(contentVO.getMaterialUrl())
                    || !StringUtils.isEmpty(contentVO.getMaterialTitle())) {
                return false;
            }
        }
        // 其他物料类型或者无任何信息
        return true;
    }

    @Override
    public boolean isMaterEmptyByPosMaterType(AdSolutionContent content) {
       return !isMaterFullByPosMaterType(content);
    }

    /**
     * 装载物料详情页的广告内容VO
     * 
     * @param adContent
     * @return
     */
    private MaterialContentVO processAdContentVo(AdSolutionContent adContent) {
        MaterialContentVO contentVo = new MaterialContentVO();
        if (null == adContent) {
            return contentVo;
        }
        contentVo.setAdvertiser(adContent.getAdvertiser());
        contentVo.setAdContentId(adContent.getId());
        contentVo.setOperatorName(adSolutionDao.findOperatorNameById(adContent.getAdSolutionId()));
        contentVo.setDescription(adContent.getDescription());
        contentVo.setApprovalStatus(adContent.getApprovalStatus());
        contentVo.setPeriodDescription(adContent.getPeriodDescription());
        contentVo.setPositionName(adContent.getPositionName());
        contentVo.setNumber(adContent.getNumber());
        // 查找审批通过的物料单，根据其ID找到物料的文件
        fillContentVOMaterial(contentVo, adContent);
        contentVo.setMaterialEmpty(isMaterEmptyByPosMaterType(adContent));
        contentVo.setMaterialFull(isMaterFullByPosMaterType(adContent));
        return contentVo;
    }

    // 启动物料审批
    // @Override
    public StartProcessResponse submitProcess(AdvertiseMaterialApply advertiseMaterialApply, User operaterUser)
            throws CRMBaseException {
        BaseStartProcessBean startBean = new BaseStartProcessBean();
        prepare(startBean, operaterUser, advertiseMaterialApply);
        StartProcessResponse respone = null;
        try {
            // 发起流程
            respone = baseProcessService.startProcess(startBean);

            MaterialApprovalRecord record = generateApprovalRecord(advertiseMaterialApply, operaterUser, respone);
            // 更新申请单状态
            String taskInfo =
                    gcrmTaskInfoService.getTaskInfo(respone.getActivities(), "material.main.approval.submit.task");
            changeStatus4MaterialApply(record, operaterUser, AdvertiseMaterialApply.MaterialApplyState.submit, taskInfo);
            // 保持审批记录
            saveApprovalRecordVO(record, operaterUser);

        } catch (Exception e) {
            logger.error("提交物料申请单，发起审批流程失败", e);
        }
        return respone;
    }

    /**
     * 
     * 功能描述: generateApprovalRecord 创建人: chenchunhui01 创建时间: 2014年4月24日 下午3:56:33
     * 
     * @param advertiseMaterialApply
     * @param operaterUser
     * @param processResponse
     * @return
     * @return MaterialApprovalRecord
     * @exception
     * @version
     */
    private MaterialApprovalRecord generateApprovalRecord(AdvertiseMaterialApply advertiseMaterialApply,
            User operaterUser, StartProcessResponse processResponse) {
        MaterialApprovalRecord record = new MaterialApprovalRecord();
        record.setAdMaterialApplyId(advertiseMaterialApply.getId());
        record.setActDefId(processResponse.getActDefId());
        record.setActivityId(processResponse.getFirstActivityId());
        record.setProcessId(processResponse.getProcessId());
        return record;
    }

    // 启动前的准备(封裝startBean，此处可以提取为公共功能）TODO
    private void prepare(BaseStartProcessBean startBean, User operaterUser,
            AdvertiseMaterialApply advertiseMaterialApply) {

        Long materialApplyId = advertiseMaterialApply.getId();
        startBean.setStartUser(operaterUser.getUsername());

        String processDefId = GcrmConfig.getConfigValueByKey("material.process.defineId");
        startBean.setPackageId(GcrmConfig.getConfigValueByKey("material.package.id"));
        startBean.setProcessDefineId(processDefId);
        startBean.setProcessStartBeanName("MaterialProcessStartBean");
        ProcessNameI18n processNameI18n =
                processNameDao.findByProcessDefIdAndLocale(processDefId, LocaleConstants.en_US.name());
        if (processNameI18n != null) {
            startBean.setProcessName(processNameI18n.getProcessName());
        }
        Map<String, Object> activityDataMap = new HashMap<String, Object>();
        activityDataMap.put("materialApplyId", materialApplyId);
        startBean.setActivityDataMap(activityDataMap);
        startBean.setForeignKey(materialApplyId.toString());

        // 根据广告内容权限设置pm
        Long contentId = advertiseMaterialApply.getAdSolutionContentId();
        AdSolutionContent adSolutionContent = contentService.findOne(contentId);
        if (adSolutionContent != null) {
            Set<String> platformIds = new HashSet<String>();
            platformIds.add(adSolutionContent.getProductId().toString());
            Map<String, Object> customParams = new HashMap<String, Object>();
            customParams.put("platformIds", platformIds);
            startBean.setCustomParams(customParams);
            startBean.putAssignmentHandler2Map(ParticipantConstants.pm,
                    applicationContext.getBean("accordingPlatformAssignmentHandler", IAssignmentHandler.class));
        }
        startBean.putAssignmentHandler2Map(ParticipantConstants.direct_supervisor,
                applicationContext.getBean("directSupervisorAssignmentHandler", IAssignmentHandler.class));

    }

    /**
     * 点击审批按钮操作
     */
    @Override
    public void saveAndCompleteApproval(MaterialApprovalRecord approvalRecord, User operaterUser,
            LocaleConstants currentLocale) throws CRMBaseException {

        MaterialApplyState applyState = null;
        AdvertiseMaterialApply advertiseMaterialApply =
                adMaterialApplyRepository.findById(approvalRecord.getAdMaterialApplyId());
        if (advertiseMaterialApply == null) {
            LoggerHelper.err(getClass(), "审批时发现物料不存在！物料编号：{}", approvalRecord.getAdMaterialApplyId());
        }

        CompleteBaseActivityReq req = generateCompleteActivityReq(approvalRecord, operaterUser);
        CompleteActivityResponse response = baseProcessService.completeActivity(req);
        // 判断审批通过OR驳回
        Integer approvalStatus = approvalRecord.getApprovalStatus();
        boolean refused = isRefused(approvalStatus);
        String taskInfo = null;
        if (refused) {
            String operaterUserName = "";
            if (operaterUser != null) {
                operaterUserName = operaterUser.getRealname();
            } else {
                operaterUserName = approvalRecord.getCreateOperator().toString();
            }

            List<String> otherInfos = new ArrayList<String>();
            otherInfos.add(approvalRecord.getApprovalSuggestion());
            taskInfo = gcrmTaskInfoService.getTaskInfo(operaterUserName, "material.main.approval.refuse", otherInfos);
            applyState = AdvertiseMaterialApply.MaterialApplyState.refused;

            String processId = approvalRecord.getProcessId();
            try {
                processService.terminateProcess(processId, operaterUserName, approvalRecord.getApprovalSuggestion());
            } catch (GWFPException e) {
                LoggerHelper.err(getClass(), "打回流程失败，流程id：{}，任务id：{}，执行人：{}", processId,
                        approvalRecord.getActivityId(), operaterUserName);
                throw new CRMRuntimeException("activity.complete.error");
            }

        } else {

            if (response.isProcessFinish()) {
                applyState = AdvertiseMaterialApply.MaterialApplyState.confirm;
                taskInfo = "";
            } else {
                // 处理并行节点
                String souceTaskInfo = advertiseMaterialApply.getTaskInfo();
                String actDef = approvalRecord.getActDefId();
                taskInfo = gcrmTaskInfoService.removeDoneNodeInfo(souceTaskInfo, actDef);

            }
        }
        // 更改申请单状态
        changeStatus4MaterialApply(operaterUser, applyState, taskInfo, advertiseMaterialApply);
        // 记录审批记录
        saveApprovalRecordVO(approvalRecord, operaterUser);
        if (AdvertiseMaterialApply.MaterialApplyState.confirm.equals(applyState)) {
            lockScheduleAndCreatePublishIfSatisfied(advertiseMaterialApply.getAdSolutionContentId(),
                    advertiseMaterialApply.getNumber());
        }
    }

    /**
     * 广告内容是确认状态并且有PO号或者广告方案有有效的合同，锁定排期单并生成上线单
     */
    private void lockScheduleAndCreatePublishIfSatisfied(Long contentId, String materialNumber) {
        AdSolutionContent content = contentService.findOne(contentId);
        boolean isSatisfied = publishService.tryLockScheduleAndCreatePublish(content);
        if (isSatisfied) {
            LoggerHelper.info(getClass(), "物料审核通过后，锁定排期单并生成上线单。");
        }
    }

    /**
     * 構建完结活动节点反馈信息
     */
    private CompleteBaseActivityReq generateCompleteActivityReq(MaterialApprovalRecord approvalRecord, User user) {
        CompleteBaseActivityReq req = new CompleteBaseActivityReq();
        req.setActivityId(approvalRecord.getActivityId());
        req.setProcessId(approvalRecord.getProcessId());
        req.setPerformer(user.getUuapName());
        req.setApproved(approvalRecord.getApprovalStatus());
        req.setReason(approvalRecord.getApprovalSuggestion());
        return req;
    }

    /**
     * 更新物料申请单状态（审批通过、审批退回） changeStatus4MaterialApply
     * 
     * 创建人: chenchunhui01 创建时间: 2014年4月23日 下午8:50:21
     * 
     * @param record
     * @param user
     * @param applyState
     * @param taskInfo
     * @return void
     * @exception
     */
    private void changeStatus4MaterialApply(MaterialApprovalRecord record, User user,
            AdvertiseMaterialApply.MaterialApplyState applyState, String taskInfo) {
        Long materialApplyId = record.getAdMaterialApplyId();
        // 查看物料申请
        AdvertiseMaterialApply advertiseMaterialApply = adMaterialApplyRepository.findById(materialApplyId);
        changeStatus4MaterialApply(user, applyState, taskInfo, advertiseMaterialApply);

    }

    private void changeStatus4MaterialApply(User user, AdvertiseMaterialApply.MaterialApplyState applyState,
            String taskInfo, AdvertiseMaterialApply advertiseMaterialApply) {
        if (applyState != null)
            advertiseMaterialApply.setApplyState(applyState);
        if (null != taskInfo)
            advertiseMaterialApply.setTaskInfo(taskInfo);
        advertiseMaterialApply.setUpdateOperator(user.getUcid());
        advertiseMaterialApply.setUpdateTime(new Date());
        logger.info("(updateMaterialTime)更新物料单状态时间:" + new Date());
        adMaterialApplyRepository.save(advertiseMaterialApply);

        // 如果申請單狀態变为确认 更新其他申请单状态为作废
        if (applyState == MaterialApplyState.confirm) {
            afterMaterialApplyPassed(advertiseMaterialApply.getId(), user);
        }
    }

    /**
     * 判断是否拒绝退回
     */
    private boolean isRefused(Integer approvalStatus) {
        return approvalStatus == 0;
    }

    @Override
    public List<MaterialApprovalRecord> findRecordByMaterialApplyId(Long materialApplyId, String processDefineId,
            LocaleConstants currentLocale) {
        List<MaterialApprovalRecord> materialApprovalRecords = null;
        List<Object> tempResultList =
                materialApproveRecordDao.findRecordByMaterialApplyId(processDefineId, currentLocale, materialApplyId);
        materialApprovalRecords = foramtApproveRecord(tempResultList);
        return materialApprovalRecords;
    }

    /**
     * 格式化审批记录，封装activityName,operaterName 功能描述: foramtApproveRecord 创建人: chenchunhui01 创建时间: 2014年4月30日 上午10:38:18
     * 
     * @param tempResultList
     * @return
     * @return List<MaterialApprovalRecord>
     * @exception
     * @version
     */
    private List<MaterialApprovalRecord> foramtApproveRecord(List<Object> tempResultList) {
        if (tempResultList == null) {
            return null;
        }
        List<MaterialApprovalRecord> materialApprovalRecords = new ArrayList<MaterialApprovalRecord>();
        Object[] tempObject = null;
        for (Object object : tempResultList) {
            tempObject = (Object[]) object;
            MaterialApprovalRecord record = (MaterialApprovalRecord) tempObject[0];
            String operateName = (String) tempObject[1];
            String taskName = (String) tempObject[2];
            record.setCreater(operateName);
            record.setTaskName(taskName);
            materialApprovalRecords.add(record);
        }
        return materialApprovalRecords;
    }

    /**
     * 保存物料审批记录
     */
    public void saveApprovalRecordVO(MaterialApprovalRecord vo, User operaterUser) throws CRMBaseException {
        try {
            vo.setCreateOperator(operaterUser.getUcid());
            vo.setCreateTime(new Date());
            materialApproveRecordDao.save(vo);
        } catch (Exception e) {
            logger.error("根据物料单单号创建修改记录异常", e);
            throw new CRMBaseException("根据物料单单号创建修改记录异常", e);
        }
    }

    /**
     * 
     */
    @Override
    public List<Map<String, Object>> findChangeHistoryRecord(String materialApplyId, LocaleConstants locale)
            throws CRMBaseException {
        try {
            List<Map<String, Object>> historyRecordList =
                    modifyRecordService.findModifyRecord(AdvertiseMaterialApply.class.getSimpleName(),
                            Long.valueOf(materialApplyId), locale);
            convertMaterialHistoryRecord(historyRecordList);
            return historyRecordList;
        } catch (Exception e) {
            logger.error("根据物料单单号查询修改记录异常", e);
            throw new CRMBaseException("根据物料单单号查询修改记录异常", e);
        }
    }

    /**
     * 
     * 功能描述:
     * 
     * 创建人: chenchunhui01 创建时间: 2014年5月8日 上午10:34:25
     * 
     * @param historyRecordList
     * @return void
     * @exception
     * @version
     */
    private void convertMaterialHistoryRecord(List<Map<String, Object>> historyRecordList) {

        for (Map<String, Object> historyRecord : historyRecordList) {
            processOperator(historyRecord);
        }
    }

    private void processOperator(Map<String, Object> record) {
        Object operator = record.get(ModifyRecordConstant.CREATEOPERATOR_KEY);
        if (operator != null) {
            User user = userService.findByUcid(((Number) operator).longValue());
            if (user != null) {
                record.put(ModifyRecordConstant.CREATEOPERATOR_KEY, user.getRealname());
            }

        }
    }

    /**
     * 根据物料文件ID查询物料文件
     * 
     * @author zhanglei35
     * @param id
     * @return
     */
    @Override
    public AdvertiseMaterial findMaterialFileById(Long id) {
        return adMaterialRepository.findOne(id);
    }

    /**
     * 根据物料文件ID删除物料文件
     * 
     * @author zhanglei35
     * @param id
     */
    @Override
    public void deleteMateialFileById(Long id) {
        adMaterialRepository.delete(id);
    }

    /**
     * 保存物料单详细内容
     * 
     * @author zhanglei35
     * @param applyDetailVO
     * @throws Exception
     */
    @Override
    public MaterialDetailVO saveMaterialApplyDetail(MaterialApplyDetailVO applyDetailVO, User user,
            LocaleConstants locale, String processDefineId) throws Exception {
        MaterialDetailVO detailVO = new MaterialDetailVO();
        try {
            // 保存物料单信息
            AdvertiseMaterialApply materialApply = applyDetailVO.getMaterialApply();
            // 如果是非新增，则保存修改记录
            if (null != materialApply && null != materialApply.getId()) {
                modifyRecordService.saveModifyRecord(materialApply);
            }
            // 新增需要设置物料编号number
            else {
                materialApply.setNumber(genrateMaterialApplyNumber());
            }
            adMaterialApplyRepository.save(materialApply);

            Long contentId = materialApply.getAdSolutionContentId();
            // 恢复或者变更物料单时，对于没有审批通过的物料单全部变为作废状态, 并调用工作流收回接口
            if (applyDetailVO.getMaterialSaveType() == MaterialSaveType.recovery.ordinal()
                    || applyDetailVO.getMaterialSaveType() == MaterialSaveType.change.ordinal()) {
                cancelOtherMaterialApply(materialApply.getId(), contentId, user, locale, processDefineId);
            }
            // 删除原来的主物料文件信息
            adMaterialRepository.deleteByContentIdAndApplyId(contentId, materialApply.getId());
            // 保存主物料文件信息
            List<AdvertiseMaterial> fileList = applyDetailVO.getMaterialApply().getMaterialList();

            for (AdvertiseMaterial file : fileList) {
                // 只有fileURL不为空的时候才保存
                if (StringUtils.isEmpty(file.getFileUrl())) {
                    continue;
                }
                file.setId(null);
                file.setAdSolutionContentId(contentId);
                file.setMaterialApplyId(materialApply.getId());
                file.setMaterialFileType(MaterialFileType.IMAGE);
                adMaterialRepository.save(file);
                file.setDownloadUrl(getDownloadURL(file.getId()));
            }
            // 删除主物料下拉菜单信息
            materialMenuRepository.deleteByMaterialApplyId(materialApply.getId());
            // 保存主物料下拉菜单信息
            List<AdvertiseMaterialMenu> materialMenuList = materialApply.getMaterialMenuList();
            if (CollectionUtils.isNotEmpty(materialMenuList)) {
                for (AdvertiseMaterialMenu materialMenu : materialMenuList) {
                    // 只有fileURL不为空的时候才保存
                    if (StringUtils.isEmpty(materialMenu.getFileUrl())) {
                        continue;
                    }
                    materialMenu.setId(null);
                    materialMenu.setMaterialApplyId(materialApply.getId());
                    materialMenuRepository.save(materialMenu);
                    materialMenu.setDownloadUrl(getMaterialMenuDownloadURL(materialMenu.getId()));
                }
            }
            // 发起审批
            StartProcessResponse response = submitProcess(materialApply, user);
            if (null == response) {
                LoggerHelper.info(getClass(), "发起物料审批流程失败，物料单id：{}，流程id：{}", materialApply.getId(), "");
            } else {
                LoggerHelper.info(getClass(), "发起物料审批流程成功，物料单id：{}，流程id：{}", materialApply.getId(),
                        response.getProcessId());
            }
            // 设置VO
            detailVO.setMaterialApplyVoList(this.findMaterialApplyListByContentId(materialApply
                    .getAdSolutionContentId()));
            detailVO.setMaterialApply(materialApply);
        } catch (Exception e) {
            throw e;
        }
        return detailVO;
    }
    
    private void saveMainMaterial(AdvertiseMaterialApply apply) {
        adMaterialRepository.deleteByContentIdAndApplyId(apply.getAdSolutionContentId(), apply.getId());
        List<AdvertiseMaterial> materials = apply.getMaterialList();
        if (CollectionUtils.isNotEmpty(materials)) {
            for (AdvertiseMaterial material : materials) {
                if (StringUtils.isEmpty(material.getFileUrl())) {
                    continue;
                }
                material.setId(null);
                material.setAdSolutionContentId(apply.getAdSolutionContentId());
                material.setMaterialApplyId(apply.getId());
                material.setMaterialFileType(MaterialFileType.IMAGE);
                adMaterialRepository.save(material);
                material.setDownloadUrl(getDownloadURL(material.getId()));
            }
        }
    }

    private void saveMenuMaterial(AdvertiseMaterialApply apply) {
        materialMenuRepository.deleteByMaterialApplyId(apply.getId());
        List<AdvertiseMaterialMenu> materialMenuList = apply.getMaterialMenuList();
        if (CollectionUtils.isNotEmpty(materialMenuList)) {
            for (AdvertiseMaterialMenu materialMenu : materialMenuList) {
                if (StringUtils.isEmpty(materialMenu.getFileUrl())) {
                    continue;
                }
                materialMenu.setId(null);
                materialMenu.setMaterialApplyId(apply.getId());
                materialMenuRepository.save(materialMenu);
                materialMenu.setDownloadUrl(getMaterialMenuDownloadURL(materialMenu.getId()));
            }
        }
    }

    /**
     * 内容入口新增或者修改物料的入口，总是随着内容提交一起变化，状态为MaterialApplyState.create， 内容申通通过后，直接修改状态为confirm
     * @param content
     */
    public void saveMaterialApplyFromContent(AdSolutionContent content) {
        if (content == null || content.getId() == null) {
            return;
        }
        List<AdvertiseMaterialApply> applys = content.getMaterialApplyList();
        if (CollectionUtils.isNotEmpty(applys)) {
            for (AdvertiseMaterialApply apply : applys) {
                if (apply.getId() == null || !content.getId().equals(apply.getAdSolutionContentId())) {
                    deleteOldIfExists(content);
                    apply.setId(null);
                    apply.setNumber(genrateMaterialApplyNumber());
                } else {
                    modifyRecordService.saveModifyRecord(apply);
                    apply.setUpdateOperator(RequestThreadLocal.getLoginUserId());
                    apply.setUpdateTime(new Date());
                }
                apply.setCreateOperator(RequestThreadLocal.getLoginUserId());
                apply.setCreateTime(new Date());
                apply.setAdSolutionContentId(content.getId());
                apply.setApplyState(MaterialApplyState.create);
                adMaterialApplyRepository.save(apply);
                saveMainMaterial(apply);
                saveMenuMaterial(apply);
            }
        }else{
            deleteOldIfExists(content); 
        }
    }

    private void deleteOldIfExists(AdSolutionContent content) {
        List<AdvertiseMaterialApply> temps=adMaterialApplyRepository.findMaterialApplyByContentIdAndState(content.getId(), MaterialApplyState.create);
        if(CollectionUtils.isNotEmpty(temps)){
            for(AdvertiseMaterialApply temp:temps){
                delete(temp);
            }
        }
    }
  
    /**
     * 广告内容的其他未审批通过的物料单状态均置为作废，并调用工作流收回接口
     * 
     * @param applyId
     * @param contentId
     * @throws Exception
     */
    private void cancelOtherMaterialApply(Long applyId, Long contentId, User user, LocaleConstants locale,
            String processDefineId) throws Exception {
        try {
            applyId = applyId == null ? 0 : applyId;
            List<AdvertiseMaterialApply> materialApplyList =
                    adMaterialApplyRepository.findMaterialApplyByExcludeIdAndContentId(applyId, contentId);
            for (AdvertiseMaterialApply apply : materialApplyList) {
                // 未审批通过的物料单才会变为作废, 已经作废的也要排除
                if (apply.getApplyState() != MaterialApplyState.confirm
                        && apply.getApplyState() != MaterialApplyState.cancel) {
                    withdrawMaterialApplyProcessById(apply, user, locale, processDefineId, MaterialApplyState.cancel,
                            messagePrefix + "cancel");
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 作废其他审核完成的物料单（当前物料单审核通过后）
     * 
     * @param applyId
     * @param contentId
     * @param user
     * @throws Exception
     */
    private void cancelOtherConfirmedMaterialApply(Long applyId, Long contentId, User user) throws Exception {
        try {
            applyId = applyId == null ? 0 : applyId;
            List<AdvertiseMaterialApply> materialApplyList =
                    adMaterialApplyRepository.findMaterialApplyByContentIdAndState(contentId,
                            MaterialApplyState.confirm);
            for (AdvertiseMaterialApply materialApply : materialApplyList) {
                if (materialApply.getId().equals(applyId)) {
                    continue;
                }
                materialApply.setTaskInfo("");
                materialApply.setUpdateOperator(user.getUcid());
                materialApply.setUpdateTime(new Date());
                materialApply.setApplyState(MaterialApplyState.cancel);
                adMaterialApplyRepository.save(materialApply);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 生产物料单编号
     * 
     * @author zhanglei35
     * @return
     */
    public String genrateMaterialApplyNumber() {
        try {
            return randomService.random(8, RandomType.random_material_apply, new IRandomCheckCallback() {
                @Override
                public boolean exists(String randomStr) {
                    AdvertiseMaterialApply exists = adMaterialApplyRepository.findByNumber(randomStr);
                    if (exists != null) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "不能生成物料单号", e);
            throw new CRMRuntimeException(e.getMessage());
        }
    }

    /**
     * 物料单审批通过后<br/>
     * 1. 回写广告内容，填充物料信息<br/>
     * 2. 把其他物料单状态变为作废
     * 
     * @author zhanglei35
     * @param applyId
     */
    @Override
    public void afterMaterialApplyPassed(Long applyId, User user) {
        try {
            AdvertiseMaterialApply materialApply = adMaterialApplyRepository.findOne(applyId);
            // 回写广告内容，填充物料信息
            adSolutionContentRepositoryCustom.updateContentMaterialById(materialApply,
                    materialApply.getAdSolutionContentId(), new Date(), user.getUcid());
            // 把其他物料单状态变为作废
            cancelOtherConfirmedMaterialApply(applyId, materialApply.getAdSolutionContentId(), user);
            // 触发物料上线任务
            publishService.triggerMaterialPublish(materialApply.getAdSolutionContentId(), materialApply.getNumber(),
                    user.getUcid());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 根据物料单ID将物料单状态改为作废<br/>
     * 1. 若审批通过→作废：则广告内容中无生效的物料信息，从当前物料中去掉<br/>
     * 2. 其他状态→作废：则变更物料状态为作废
     * 
     * @author zhanglei35
     * @param applyId
     * @throws Exception
     */
    @Override
    public MaterialDetailVO cancelMaterialApplyById(Long applyId, User user, LocaleConstants locale,
            String processDefineId) throws Exception {
        MaterialDetailVO detailVO = new MaterialDetailVO();
        try {
            AdvertiseMaterialApply materialApply = adMaterialApplyRepository.findOne(applyId);

            // 撤销审批流，同时更新物料单状态为作废
            withdrawMaterialApplyProcessById(materialApply, user, locale, processDefineId, MaterialApplyState.cancel,
                    messagePrefix + "cancel");

            // 更新广告内容
            List<Object[]> result =
                    adMaterialApplyRepository.findContentStateCountByIdAndState(applyId, MaterialApplyState.confirm);
            if (result.size() < 1) {
                return detailVO;
            }
            // 如没有审批通过的物料单，则清空广告内容的物料信息
            if (Integer.valueOf(result.get(0)[0].toString()) < 1) {
                AdvertiseMaterialApply emptyMaterialApply = new AdvertiseMaterialApply();
                adSolutionContentRepositoryCustom.updateContentMaterialById(emptyMaterialApply,
                        Long.valueOf(result.get(0)[1].toString()), new Date(), user.getUcid());
            }
            // 设置详情页VO
            detailVO = findMaterialDetailVoByContentId(materialApply.getAdSolutionContentId(), locale);
        } catch (Exception e) {
            throw e;
        }
        return detailVO;
    }

    /**
     * 撤销物料单的审批，更改物料单状态为待提交，调用工作流收回接口，同时记录物料单的taskinfo
     * 
     * 
     * @author zhanglei35
     * @param applyId
     * @throws Exception
     */
    @Override
    public MaterialDetailVO withdrawMaterialApplyProcessById(AdvertiseMaterialApply materialApply, User user,
            LocaleConstants locale, String processDefineId, MaterialApplyState state, String taskPrefix)
            throws Exception {
        MaterialDetailVO detailVO = new MaterialDetailVO();

        try {
            if (null == materialApply || StringUtils.isEmpty(String.valueOf(materialApply.getId()))) {
                return detailVO;
            }
            // 如果只传入ID，则需要查询申请单
            if (null == materialApply || StringUtils.isEmpty(materialApply.getNumber())) {
                materialApply = adMaterialApplyRepository.findOne(materialApply.getId());
            }
            // 记录物料单的原来的状态
            MaterialApplyState oldState = materialApply.getApplyState();

            // 更新物料单的状态，记录审批信息taskinfo
            materialApply.setApplyState(state);
            materialApply.setTaskInfo(gcrmTaskInfoService.getTaskInfo(user.getRealname(), taskPrefix));
            materialApply.setUpdateOperator(user.getUcid());
            materialApply.setUpdateTime(new Date());
            adMaterialApplyRepository.save(materialApply);

            // 调用工作流收回接口(只有在物料单的状态为审核中的时候才可以收回)
            if (oldState == MaterialApplyState.submit) {
                List<Object> objects =
                        materialApproveRecordDao.findRecordByMaterialApplyId(processDefineId, locale,
                                materialApply.getId());
                if (objects.size() > 0) {
                    Object[] row = (Object[]) objects.get(objects.size() - 1);
                    MaterialApprovalRecord record = (MaterialApprovalRecord) row[0];
                    String processId = record == null ? "" : record.getProcessId();
                    processService.withdrawAndTerminateProcess(processId, user.getUuapName(), StringUtils.EMPTY);
                }
            }

            // 设置VO
            detailVO.setMaterialApplyVoList(this.findMaterialApplyListByContentId(materialApply
                    .getAdSolutionContentId()));
            detailVO.setMaterialApply(materialApply);
        } catch (Exception e) {
            throw e;
        }
        return detailVO;
    }

    /**
     * 广告方案审核通过时，对于有物料的广告内容，更新其物料单状态为通过
     * 
     * @param adSolutionId
     */
    @Override
    public void updateMaterApplyStateAfterSolutionApproved(Long adSolutionId) throws CRMBaseException {
        List<AdSolutionContent> contentList = contentService.findByAdSolutionId(adSolutionId);
        List<AdvertiseMaterialApply> allApply = new ArrayList<AdvertiseMaterialApply>();
        List<AdvertiseMaterialApply> applys = null;
        for (AdSolutionContent content : contentList) {
            applys =
                    adMaterialApplyRepository.findMaterialApplyByContentIdAndState(content.getId(),
                            MaterialApplyState.create);
            if (CollectionUtils.isNotEmpty(applys)) {
                for (AdvertiseMaterialApply apply : applys) {
                    apply.setApplyState(MaterialApplyState.confirm);
                    apply.setUpdateOperator(content.getUpdateOperator());
                    apply.setUpdateTime(new Date());
                }
                allApply.addAll(applys);
            }
        }
        if (CollectionUtils.isNotEmpty(allApply)) {
            adMaterialApplyRepository.save(allApply);
        }
    }

    /**
     * 
     * 功能描述: 物料催办 remindersContentByMail
     * 
     * @创建人: chenchunhui01
     * @创建时间: 2014年5月24日 下午12:48:00
     * @param id
     * @param currentLocale
     * @return void
     * @exception
     * @version
     */
    @Override
    public void remindersContentByMail(Long id, LocaleConstants currentLocale) {
        RemindRequest request = generateRemindRequest(RemindType.material, id.toString());
        processService.remindByForeignKey(request);
    }

    private RemindRequest generateRemindRequest(RemindType type, String key) {
        int email = 0;
        RemindRequest request = new RemindRequest();
        request.setReminder(RequestThreadLocal.getLoginUuapName());
        request.setNotifyType(email);
        request.setType(type);
        request.setKey(key);
        return request;
    }

    public AdvertiseMaterialMenu saveMaterialMenu(AdvertiseMaterialMenu materialMenu) {
        return materialMenuRepository.save(materialMenu);
    }

    public List<AdvertiseMaterialMenu> findMaterialMenusByApplyId(Long applyId) {
        return materialMenuRepository.findByMaterialApplyIdOrderByIdAsc(applyId);
    }

    public void delete(AdvertiseMaterialApply adMaterialApply) {
        adMaterialRepository.deleteByContentIdAndApplyId(adMaterialApply.getAdSolutionContentId(),
                adMaterialApply.getId());
        materialMenuRepository.deleteByMaterialApplyId(adMaterialApply.getId());
        adMaterialApplyRepository.delete(adMaterialApply);

    }

    public void delete(Long adMaterialApplyId) {
        adMaterialRepository.deleteByMaterialApplyId(adMaterialApplyId);
        materialMenuRepository.deleteByMaterialApplyId(adMaterialApplyId);
        adMaterialApplyRepository.delete(adMaterialApplyId);

    }

    public void initMaterialByCondition() {
        List<AdSolutionContent> contents = contentService.findAllWithoutMaterialApply();
        if (CollectionUtils.isNotEmpty(contents)) {
            for (AdSolutionContent content : contents) {
                if (isContentMaterFull(content)) {// 物料信息完整的，生成物料单
                    generateMaterialApplyAndUpdateMaterial(content);
                }
            }
        }
    }

    private void generateMaterialApplyAndUpdateMaterial(AdSolutionContent content) {
        AdvertiseMaterialApply apply = new AdvertiseMaterialApply();
        apply.setApplyState(MaterialApplyState.create);
        apply.setNumber(genrateMaterialApplyNumber());
        apply.setMaterialTitle(content.getMaterialTitle());
        apply.setMaterialUrl(content.getMaterialUrl());
        apply.setMaterialEmbedCodeContent(content.getMaterialEmbedCodeContent());
        apply.setMaterialFileType(content.getMaterialFileType());
        apply.setMonitorUrl(content.getMonitorUrl());
        apply.setAdSolutionContentId(content.getId());
        apply.setCreateTime(new Date());
        apply.setCreateOperator(content.getCreateOperator());
        apply.setUpdateOperator(content.getUpdateOperator());
        apply.setUpdateTime(new Date());
        adMaterialApplyRepository.save(apply);
        List<AdvertiseMaterial> materialFileList = adMaterialRepository.findNoApplyFileByContentId(content.getId());
        if (CollectionUtils.isNotEmpty(materialFileList)) {
            for (AdvertiseMaterial mFile : materialFileList) {
                mFile.setMaterialApplyId(apply.getId());
                if (null == mFile.getMaterialFileType()) {
                    mFile.setMaterialFileType(MaterialFileType.IMAGE);
                }
                adMaterialRepository.save(mFile);
            }
        }

    }

    private boolean isContentMaterFull(AdSolutionContent content) {
        Position position = positionRepository.findOne(content.getPositionId());
        Integer materialFileType = content.getMaterialFileType();
        if (position.getMaterialType() == PositionMaterialType.textlink) {
            // 文字链类型的物料必填字段：title, url
            return StringUtils.isNotBlank(content.getMaterialTitle())
                    && StringUtils.isNotBlank(content.getMaterialUrl());
        } else if (position.getMaterialType() == PositionMaterialType.image) {
            // 图片类型的物料必填字段： url
            if (materialFileType != null && materialFileType.intValue() == 2) { // 物料文件类型为代码时，调整为有代码内容无附件
                return StringUtils.isNotBlank(content.getMaterialEmbedCodeContent());
            } else {
                return StringUtils.isNotBlank(content.getMaterialUrl());
            }
        } else if (position.getMaterialType() == PositionMaterialType.image_and_textlink) {
            // 图片类型的物料必填字段：url, title
            if (materialFileType != null && materialFileType.intValue() == 2) {// 物料文件类型为代码时，调整为有代码内容无附件
                return StringUtils.isNotBlank(content.getMaterialTitle())
                        && StringUtils.isNotBlank(content.getMaterialEmbedCodeContent());
            } else {
                return StringUtils.isNotBlank(content.getMaterialUrl())
                        && StringUtils.isNotBlank(content.getMaterialTitle());
            }
        }
        // 其他物料类型或者信息不完整
        return false;
    }

}
