package com.baidu.gcrm.quote.approval.record.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.mail.QuoteFinishContent;
import com.baidu.gcrm.quote.approval.record.service.IQtProcessEndUpWordService;
@Service
public class QtProcessEndUpWordServiceImpl implements
		IQtProcessEndUpWordService {
	Logger logger = LoggerFactory.getLogger(QtProcessEndUpWordServiceImpl.class);

	@Override
	public byte[] poiWordTableReplace(String sourceFile, 
			QuoteFinishContent content,StringBuffer priceType) throws Exception {		
		FileInputStream in = new FileInputStream(sourceFile);
        HWPFDocument hwpf = new HWPFDocument(in);
        Range range = hwpf.getRange();// 得到文档的读取范围
        TableIterator it = new TableIterator(range);
        ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        try{
        // 迭代文档中的表格
        while (it.hasNext()) {
        	
            Table tb = (Table) it.next();
            // 迭代行，默认从0开始
            for (int i = 0; i < tb.numRows(); i++) {            	
                TableRow tr = tb.getRow(i);
                // 迭代列，默认从0开始
                for (int j = 0; j < tr.numCells(); j++) {
                    TableCell td = tr.getCell(j);// 取得单元格              
                    // 取得单元格的内容
                    for (int k = 0; k < td.numParagraphs(); k++) {
                        Paragraph para = td.getParagraph(k);                                      
                        String s = para.text();
                        if(s.contains("对应投放平台"))
                        {
                        	para.replaceText("对应投放平台", content.getPlatFormName().toString());
                        } 
                        if(s.contains("标杆价的生效日期"))
                        {
                        	para.replaceText("标杆价的生效日期", content.getValidStartTime().toString());
                        } 
                        if(s.contains("标杆价的失效日期"))
                        {
                        	para.replaceText("标杆价的失效日期", content.getValidEndTime().toString());
                        } 
                        if(s.contains("标杆价中政策说明部分"))
                        {
                        	para.replaceText("标杆价中政策说明部分", content.getPriceMessage().toString());
                        } 
                        if(s.contains("放入审批记录"))
                        {
                        	para.replaceText("放入审批记录", content.getRecordList());
                        } 
                        if(s.contains("详细的标杆价部分（即表格部分）"))
                        {
                        	para.replaceText("详细的标杆价部分（即表格部分）", priceType.toString());
                        } 

                    } // end for
                } // end for
            } // end for
        } // end while      
        hwpf.write(out); 
		}finally{
			if(in!=null){
				in.close();	
			}
			if(out!=null){
				out.close();
			}
		}
        return out.toByteArray();
        
	}
}
