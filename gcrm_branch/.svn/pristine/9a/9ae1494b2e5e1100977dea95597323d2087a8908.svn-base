package com.baidu.gcrm.ad.statistic.web.helper;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelWriter {
    
    private static Logger logger = LoggerFactory.getLogger(ExcelWriter.class);
    
    public static void write(OutputStream out, int totalRowNum,
            TitleCallback titleCallback, RowCallback callback,
            CloseCallback closeCallback) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            HSSFSheet sheet = workbook.createSheet();
            int currWriteRowIndex = -1;
            //write title
            if (titleCallback != null) {
                currWriteRowIndex = titleCallback.writeTitle(sheet);
            }
            
            if (currWriteRowIndex > -1) {
                currWriteRowIndex += 1;
                totalRowNum += currWriteRowIndex;
            } else {
                currWriteRowIndex = 0;
            }
            
            while (currWriteRowIndex < totalRowNum) {
                HSSFRow row = sheet.createRow(currWriteRowIndex);
                if (callback != null) {
                    callback.writeRow(row);
                }
                currWriteRowIndex++;
            }
            workbook.write(out);
        } catch (IOException e) {
            logger.error("ExcelWriter write file failed!", e);
        } finally {
            if (closeCallback != null) {
                closeCallback.close();
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                    out = null;
                } catch (IOException e) {
                    logger.error("ExcelWriter close file failed!", e);
                }
            }
        }
        
        
    }

}
