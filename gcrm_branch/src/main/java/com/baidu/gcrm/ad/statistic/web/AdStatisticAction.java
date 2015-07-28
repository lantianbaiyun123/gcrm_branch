package com.baidu.gcrm.ad.statistic.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.ad.statistic.service.IAdContentStatisticService;
import com.baidu.gcrm.ad.statistic.web.helper.ExcelWriter;
import com.baidu.gcrm.ad.statistic.web.helper.RowCallback;
import com.baidu.gcrm.ad.statistic.web.helper.TitleCallback;
import com.baidu.gcrm.ad.statistic.web.vo.PublishAdContentVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickDataVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickListVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickStatDataVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishListVO;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.auth.SessionValueKey;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;

@Controller
@RequestMapping("/adStat")
public class AdStatisticAction extends ControllerHelper{
    
    @Autowired
    IAdContentStatisticService adStatisticService;
    @Autowired
    IAccountService accountService;
    
    @RequestMapping("/syncHao123")
    public void syncHao123(@RequestParam("from") String dayFrom,
            @RequestParam("to") String dayTo) {
        adStatisticService.syncHao123AdContentPublishData(dayFrom, dayTo);
    }
    
    @RequestMapping("/updatePublish")
    public void updatePublish() {
        adStatisticService.updatePublishDataInfo();
    }
    
    
    
    @RequestMapping("/queryAdStat")
    @ResponseBody
    public JsonBean<List<PublishClickDataVO>> queryAdStat(HttpSession session) {
        return JsonBeanUtil.convertBeanToJsonBean(
                adStatisticService.findByCustomerNumberAndLocale(
                        getCurrentCustomerNumber(session), currentLocale));
    }
    
    @RequestMapping("/queryAdPaltform")
    @ResponseBody
    public JsonBean<List<AdPlatform>> queryAdPaltform(HttpSession session) {
        return JsonBeanUtil.convertBeanToJsonBean(
                adStatisticService.findAdPlatformByCustomerNumberAndLocale(
                        getCurrentCustomerNumber(session), currentLocale));
    }
    
    @RequestMapping("/queryAd")
    @ResponseBody
    public JsonBean<PublishListVO> queryAd(HttpSession session, PublishListVO vo) {
        return JsonBeanUtil.convertBeanToJsonBean(
                adStatisticService.findListByCustomerNumberAndLocale(
                        getCurrentCustomerNumber(session),new Date(),
                currentLocale, vo));
    }
    
    @RequestMapping("/queryByContentNumber")
    @ResponseBody
    public JsonBean<PublishAdContentVO> queryByContentNumber(HttpSession session, PublishClickListVO vo) {
        return JsonBeanUtil.convertBeanToJsonBean(
                adStatisticService.findByAdContentNumber(
                vo.getAdContentNumber(), getCurrentCustomerNumber(session), currentLocale));
    }
    
    @RequestMapping("/queryAdStatDetail")
    @ResponseBody
    public JsonBean<PublishClickListVO> queryAdStatDetail(HttpSession session, PublishClickListVO vo) {
        return JsonBeanUtil.convertBeanToJsonBean(queryAdContentStatDetail(vo, getCurrentCustomerNumber(session)));
    }
    
    @RequestMapping("/downloadAdStatDetail")
    @ResponseBody
    public void downloadAdStatDetail(HttpSession session, PublishClickListVO vo, HttpServletResponse resp) {
        PublishClickListVO publishClickListVO = queryAdContentStatDetail(vo, getCurrentCustomerNumber(session));
        
        final List<PublishClickStatDataVO> adClickDatas = publishClickListVO.getAdClickDatas();
        if (adClickDatas == null || adClickDatas.size() < 1) {
            return;
        }
        resp.setContentType("application/vnd.ms-excel; charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=AdStatData.xls");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        TitleCallback titleCallback = new TitleCallback(){

            @Override
            public int writeTitle(HSSFSheet sheet) {
                HSSFRow titleRow = sheet.createRow(0);
                String dateTitle = MessageHelper.getMessage("ad.export.date", currentLocale);
                String dateClick = MessageHelper.getMessage("ad.export.click", currentLocale);
                String dateUV = MessageHelper.getMessage("ad.export.uv", currentLocale);
                titleRow.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(dateTitle);
                titleRow.createCell(1, Cell.CELL_TYPE_STRING).setCellValue(dateClick);
                titleRow.createCell(2, Cell.CELL_TYPE_STRING).setCellValue(dateUV);
                return 0;
            }
            
        };
        
        RowCallback rowProcessCallBack = new RowCallback(){
            @Override
            public void writeRow(Row row) {
                Cell dateCell = row.createCell(0, Cell.CELL_TYPE_STRING);
                Cell clickCell = row.createCell(1, Cell.CELL_TYPE_STRING);
                Cell uvCell = row.createCell(2, Cell.CELL_TYPE_STRING);
                int rowIndex = row.getRowNum();
                PublishClickStatDataVO currRowData = adClickDatas.get(rowIndex-1);
                dateCell.setCellValue(dateFormat.format(currRowData.getDate()));
                clickCell.setCellValue(currRowData.getClick().toString());
                uvCell.setCellValue(currRowData.getUv().toString());
            }
        };
        try {
            ExcelWriter.write(resp.getOutputStream(), adClickDatas.size(),
                    titleCallback, rowProcessCallBack, null);
        } catch (IOException e) {
        }
    }
    
    private PublishClickListVO queryAdContentStatDetail(PublishClickListVO vo, Long customerNumber) {
        Date from = vo.getFrom();
        Date to = vo.getTo();
        if (from == null || to == null) {
            Calendar startDate =  Calendar.getInstance();
            startDate.add(Calendar.MONTH, -1);
            from = startDate.getTime();
            to = new Date();
            vo.setFrom(from);
            vo.setTo(to);
        }
        
        return adStatisticService.findClickListByDateRangeAndAdContentNumber(vo, customerNumber);
    }
    
    @RequestMapping("/downloadAllAdStat")
    @ResponseBody
    public void downloadAdStatList(HttpSession session, PublishListVO vo, HttpServletResponse resp) {
        vo.setPageSize(-1);
        vo.setPageNumber(-1);
        PublishListVO res = adStatisticService.findListByCustomerNumberAndLocale(getCurrentCustomerNumber(session),new Date(),currentLocale, vo);
        final List<PublishAdContentVO> adClickDatas = res.getAdDatas();
        if (adClickDatas == null || adClickDatas.size() < 1) {
            return;
        }
        resp.setContentType("application/vnd.ms-excel; charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=AdStatData.xls");
        TitleCallback titleCallback = new TitleCallback(){         
            
            @Override
            public int writeTitle(HSSFSheet sheet) {
                HSSFRow titleRow = sheet.createRow(0);
                String adcontentnum = MessageHelper.getMessage("schedulecondition.querytype.adcontentnum", currentLocale);
                String advertiser = MessageHelper.getMessage("advertise.content.advertiser.name", currentLocale);
                String site = MessageHelper.getMessage("resource.site", currentLocale);
                String position = MessageHelper.getMessage("resource.position.position", currentLocale);
                String period = MessageHelper.getMessage("occupation.period", currentLocale);
                String budget = MessageHelper.getMessage("advertise.content.quotation.budget", currentLocale);
                String billModel = MessageHelper.getMessage("advertise.content.quotation.billModel.name", currentLocale);
                String priceType = MessageHelper.getMessage("advertise.content.quotation.priceType.unit", currentLocale);
                String description = MessageHelper.getMessage("advertise.content.description", currentLocale);
                
                titleRow.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(adcontentnum);
                titleRow.createCell(1, Cell.CELL_TYPE_STRING).setCellValue(advertiser);
                titleRow.createCell(2, Cell.CELL_TYPE_STRING).setCellValue(site);
                titleRow.createCell(3, Cell.CELL_TYPE_STRING).setCellValue(position);
                titleRow.createCell(4, Cell.CELL_TYPE_STRING).setCellValue(period);
                titleRow.createCell(5, Cell.CELL_TYPE_STRING).setCellValue(budget);
                titleRow.createCell(6, Cell.CELL_TYPE_STRING).setCellValue(billModel);
                titleRow.createCell(7, Cell.CELL_TYPE_STRING).setCellValue(priceType);
                titleRow.createCell(8, Cell.CELL_TYPE_STRING).setCellValue(description);
                titleRow.createCell(9, Cell.CELL_TYPE_STRING).setCellValue("URL");
                return 0;
            }
            
        };
        
        RowCallback rowProcessCallBack = new RowCallback(){
            @Override
            public void writeRow(Row row) {
                Cell adcontentnum = row.createCell(0, Cell.CELL_TYPE_STRING);
                Cell advertiser = row.createCell(1, Cell.CELL_TYPE_STRING);
                Cell site = row.createCell(2, Cell.CELL_TYPE_STRING);
                Cell position = row.createCell(3, Cell.CELL_TYPE_STRING);
                Cell period = row.createCell(4, Cell.CELL_TYPE_STRING);
                Cell budget = row.createCell(5, Cell.CELL_TYPE_STRING);
                Cell billModel = row.createCell(6, Cell.CELL_TYPE_STRING);
                Cell priceType = row.createCell(7, Cell.CELL_TYPE_STRING);
                Cell description = row.createCell(8, Cell.CELL_TYPE_STRING);
                Cell url = row.createCell(9, Cell.CELL_TYPE_STRING);
                
                int rowIndex = row.getRowNum();
                PublishAdContentVO currRowData = adClickDatas.get(rowIndex-1);
                adcontentnum.setCellValue(currRowData.getAdContentNumber());
                advertiser.setCellValue(currRowData.getAdvertiser());
                site.setCellValue(currRowData.getSiteName());
                position.setCellValue(currRowData.getPositionName());
                period.setCellValue(currRowData.getOccupationTime());
                budget.setCellValue(currRowData.getBudget() == null ? "" : currRowData.getBudget().toString());
                billModel.setCellValue(currRowData.getBillingModelName());
                priceType.setCellValue(currRowData.getCustomerQuote());
                description.setCellValue(currRowData.getDesc());
                url.setCellValue(currRowData.getUrl());
            }
        };
        try {
            ExcelWriter.write(resp.getOutputStream(), adClickDatas.size(), titleCallback, rowProcessCallBack, null);
        } catch (IOException e) {
        }
    }
    
    private Long getCurrentCustomerNumber(HttpSession session){
        Long customerNumber = null;
        if(session.getAttribute(SessionValueKey.CURR_CUSTOMER_NUM) != null){
            customerNumber = (Long)session.getAttribute(SessionValueKey.CURR_CUSTOMER_NUM);
        }
        // is necessary ?
        if(customerNumber == null){
            if(session.getAttribute(SessionValueKey.CURR_UCID) != null){
                Account account = accountService.findByUcid((Long)session.getAttribute(SessionValueKey.CURR_UCID));
                if(account != null){
                    customerNumber = account.getCustomerNumber();
                }
            }
        }
        return customerNumber;
    }
    
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));  
    }  
    
}
