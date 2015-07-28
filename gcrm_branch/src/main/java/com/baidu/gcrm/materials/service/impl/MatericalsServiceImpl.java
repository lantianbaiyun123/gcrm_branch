package com.baidu.gcrm.materials.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.IFileUploadService;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.customer.dao.CustomerRepository;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.log.model.ModifyRecord;
import com.baidu.gcrm.log.model.ModifyRecord.OperateType;
import com.baidu.gcrm.log.service.ModifyRecordService;
import com.baidu.gcrm.materials.dao.AttachmentDao;
import com.baidu.gcrm.materials.model.Attachment;
import com.baidu.gcrm.materials.service.MatericalsService;

@Service
public class MatericalsServiceImpl implements MatericalsService {
    private static Logger log = LoggerFactory.getLogger(MatericalsServiceImpl.class);

    private final static String MODIFY_RECORD_ADD_ATTACHMENT="modify.record.add.attachment";
    private final static String MODIFY_RECORD_DEL_ATTACHMENT="modify.record.del.attachment";
    
    
    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private CustomerRepository customerDao;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private ModifyRecordService modifyRecordService;
    
    @Override
    public Attachment saveAttachment(Attachment attachment,boolean isNeedRecordHistory) {
        Long customerId = attachment.getCustomerNumber();
        if (attachment.isDelete()) {// 需要删除的数据
            if (attachment.getId() > 0) {// db已存在，需要删除的数据
                deleteAttachment(attachment.getId());
            } else {// db不存在，删除云上的文件
                deleteFile(attachment.getTempUrl());
            }
                        
        } else {// 需要保存的数据
            
            if(isNeedRecordHistory){
                if(attachment.getId() > 0){
                    modifyRecordService.saveModifyRecord(Attachment.class, attachment);
                } else {
                    List<ModifyRecord> modifyRecordList = new ArrayList<ModifyRecord>();
                    modifyRecordList.add( buildSpecialModifyRecord(customerId,attachment.getName(),MODIFY_RECORD_ADD_ATTACHMENT ,OperateType.insert));
                    modifyRecordService.saveModifyRecord(modifyRecordList);
                }
            }
            
            attachment= attachmentDao.save(attachment);
        }
      return attachment;
    }

    @Override
    public void deleteAttachment(Long id) {
        Attachment attachment = attachmentDao.findOne(id);
        try {
            if (null != attachment) {
                
                Long customerId = attachment.getCustomerNumber();

                attachmentDao.delete(attachment);
                fileUploadService.deleteFile(attachment.getTempUrl());
                
                List<ModifyRecord> modifyRecordList = new ArrayList<ModifyRecord>();
                modifyRecordList.add( buildSpecialModifyRecord(customerId,MODIFY_RECORD_DEL_ATTACHMENT ,attachment.getName(),OperateType.delete));
                modifyRecordService.saveModifyRecord(modifyRecordList);
                
            } else {
                log.error("=====delete unexit file, id is " + id);
            }
        } catch (Exception e) {
            log.error("=====delete attachment error" + e.getMessage());
        }
    }
    
    private ModifyRecord buildSpecialModifyRecord(Long parentId,String content,String parameter,OperateType operateType){
        ModifyRecord record = new ModifyRecord();
        record.setModifiedDataId(parentId);
        record.setTableName(Customer.class.getSimpleName());
        record.setModifyField("attachment");
        record.setOperateType(operateType);
        record.setNewValue(content);
        record.setOldValue(parameter);
        record.setCreateOperator(RequestThreadLocal.getLoginUserId());
        record.setCreateTime(new Date());
        
        return record;
    }
    
    
    @Override
    public void deleteAttachmentByCustomerId(Long customerId){
        try{
            attachmentDao.deleteByCustomerNumber(customerId);
        }catch(Exception e){
            LoggerHelper.err(getClass(), "根据客户ID删除资质文件异常", e);
        }
    }
    @Override
    public boolean uploadFile(Attachment attachment) {
        String tempUrl = "";
        String url = "";
        tempUrl = "/" + UUID.randomUUID().toString();

        try {
            url = fileUploadService.upload(tempUrl, attachment.getBytes());
        } catch (Exception e) {
            log.error("=====uplaodFile failed:" + e.getMessage());
            return false;
        }

        attachment.setTempUrl(tempUrl);
        attachment.setUrl(url);
        return true;
    }

    @Override
    public void deleteFile(String url) {
        try {
            fileUploadService.deleteFile(url);
        } catch (Exception e) {
            log.error("=====删除云上数据出错：" + e.getMessage());
        }

    }

//    @Override
//    public boolean saveAttachments(List<Attachment> attachments, Long customerId) {
//
//        // 查找customerNumber
//        for (Attachment attachment : attachments) {
//            attachment.setCustomerNumber(customerId);
//        }
//        try {
//            Customer customer = customerDao.findOne(customerId);
//            // 如果已经审核的客户，则重新审核
//            CustomerApproveState approvalStatus = CustomerApproveState.valueOf(customer.getApprovalStatus());
//            if (approvalStatus == CustomerApproveState.approving) {
//                throw new CRMBaseException("approvingcannotmodify");
//            }
//            
//            boolean isNeed2Reapproval = need2Reapproval(attachments);
//
//            if (isNeed2Reapproval && CustomerApproveState.approved.equals(approvalStatus) ) {
//                customer.setApprovalStatus(CustomerApproveState.saving.ordinal());
//                customer.setCompanyStatus(CustomerState.waiting_take_effect.ordinal());
//                customerDao.save(customer);
//            }
//
//            return isNeed2Reapproval;
//        } catch (Exception e) {
//            log.error("save attachment failed:" + e.getMessage());
//        }
//        return false;
//    }
//
//    private boolean need2Reapproval( List<Attachment> attachments) {
//       
//        boolean isChanged = false;
//        for (Attachment attachment : attachments) {
//            if (StringUtils.isNotEmpty(attachment.getTempUrl())) {
//                if (!isChanged) {
//                    Long id = attachment.getId();
//                    if (id == null || id <= 0) {
//                        isChanged = true;
//                    } else {
//                        Attachment attachmentInDb = this.findById(id);
//                        if (attachment.getType() != attachmentInDb.getType()) {
//                            isChanged = true;
//                        }
//                    }
//                }
//                if(isChanged){
//                    modifyRecordService.saveModifyRecord(Attachment.class, attachment);
//                }
//                this.saveAttachment(attachment);
//            }
//        }
//        return isChanged;
//    }

    @Override
    public List<Attachment> findByCustomerNumber(Long customerNumber) {

        return attachmentDao.findByCustomerNumber(customerNumber);
    }

    @Override
    public Attachment findById(Long id) {
        return attachmentDao.findOne(id);
    }

}
