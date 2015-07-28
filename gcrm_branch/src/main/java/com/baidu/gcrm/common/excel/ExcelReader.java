package com.baidu.gcrm.common.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.gcrm.common.exception.CRMBaseException;

public class ExcelReader {
    
    private static Logger logger = LoggerFactory.getLogger(ExcelReader.class);
    
    public static void read(InputStream in,PoiRowCallback rowCallback) throws CRMBaseException{
        try {
            if (rowCallback == null) {
                return;
            }
            Workbook wb = WorkbookFactory.create(in);
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                rowCallback.call(row);
            }
        } catch (Exception e) {
            logger.error("read excel file faild!", e);
            throw new CRMBaseException(e);
            
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close excel file stream faild!", e);
                }
            }
        }
        
    }
}
