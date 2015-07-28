package com.baidu.gcrm.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.gcrm.common.PatternUtil;

public class HttpHeaderUtil {
    
    private static Logger logger = LoggerFactory.getLogger(HttpHeaderUtil.class);
    
    /**
     * 设置文件下载头信息
     * @param downloadFileName
     * @param request
     * @param response
     * @param isDirectDownload 是否直接下载图片，不在浏览器打开
     */
    public static void setDownloadHeader(String downloadFileName,
            HttpServletRequest request, HttpServletResponse response, boolean isDirectDownload) {
        
        try {
            if (PatternUtil.isBlank(downloadFileName)) {
                return;
            }
            String fileType = downloadFileName.substring(downloadFileName.lastIndexOf(".") + 1).toLowerCase();
            response.setCharacterEncoding("UTF-8");
            String userAgent = request.getHeader("User-Agent").toLowerCase();
            if (PatternUtil.isImageFile(downloadFileName) && !isDirectDownload) {
                if (userAgent.indexOf("msie") != -1) {
                    response.setContentType("application/octet-stream");
                } else {
                    response.setContentType("image/" + fileType);
                }
            } else {
                response.setContentType("application/octet-stream");
                if (downloadFileName.length() > 70) {
                    downloadFileName = new StringBuilder()
                        .append(downloadFileName.substring(0, 10))
                        .append("....")
                        .append(fileType).toString();
                }
                if (userAgent.indexOf("firefox") != -1) {
                    response.setHeader("Content-Disposition", new StringBuilder().append("attachment; filename*=utf8''").append(URLEncoder.encode(downloadFileName, "UTF-8")).toString());
                } else {
                    response.setHeader("Content-Disposition", new StringBuilder().append("attachment; filename=").append(URLEncoder.encode(downloadFileName, "UTF-8")).toString());
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("set download header error!", e);
        }
        
    }

}
