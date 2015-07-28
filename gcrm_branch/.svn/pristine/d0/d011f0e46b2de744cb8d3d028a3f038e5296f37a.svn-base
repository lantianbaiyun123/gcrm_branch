package com.baidu.gcrm.report.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.digester3.Digester;

import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;

/**
 * 读取report配置文件，生成reportconfig对象
 * 
 * @author yangjianguo
 * 
 */
public class ReportConfigReader {
    private Map<String, ReportConfig> configMap = new HashMap<String, ReportConfig>();

    public Map<String, ReportConfig> loadXMLConfigFromReportList() {
        InputStream in = null;
        Digester digester = new Digester();
        digester.setValidating(false);

        initDigester(digester);
        String configDir = this.getClass().getClassLoader().getResource("reports/xml/").getPath();
        File files = new File(configDir);
        if (files.isDirectory()) {
            File[] reportFiles = files.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".xml");
                }

            });
            for (File file : reportFiles) {
                try {
                    in = new FileInputStream(file);
                    digester.push(this);
                    digester.parse(in);
                    in.close();
                } catch (Exception e) {
                    LoggerHelper.err(getClass(), "解析report文件发生错误，文件名：" + file.getName(), e);
                    throw new CRMRuntimeException(e,"解析report文件发生错误，文件名：{0}",file.getName());
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            LoggerHelper.err(getClass(), "关闭report文件流失败，文件名：" + file.getName(), e);
                        }
                    }
                }
            }
        }

        return configMap;
    }

    public void addReportConfig(ReportConfig config) {
        if (config.getId() == null)
            throw new RuntimeException("Invalid report, no id specified in XML");

        configMap.put(config.getId(), config);
    }

    private void initDigester(Digester digester) {
        digester.addObjectCreate("reports/report", ReportConfig.class);
        digester.addSetProperties("reports/report");

        digester.addObjectCreate("reports/report/dataSource", DataSourceConfig.class);
        digester.addSetProperties("reports/report/dataSource");// set attributes
        digester.addSetNext("reports/report/dataSource", "addDataSourceConfig");

        digester.addObjectCreate("reports/report/dataSource/query", QueryConfig.class);
        digester.addSetProperties("reports/report/dataSource/query");
        digester.addBeanPropertySetter("reports/report/dataSource/query");
        digester.addSetNext("reports/report/dataSource/query", "addQueryConfig");

        digester.addObjectCreate("reports/report/dataSource/inputBindings/inputBinding", InputBinding.class);
        digester.addSetProperties("reports/report/dataSource/inputBindings/inputBinding");
        digester.addSetNext("reports/report/dataSource/inputBindings/inputBinding", "addInputBinding");

        digester.addObjectCreate("reports/report/dataSource/columns/column", ColumnConfig.class);
        digester.addSetProperties("reports/report/dataSource/columns/column");
        digester.addSetNext("reports/report/dataSource/columns/column", "addColumnConfig");

        digester.addSetNext("reports/report/dataSource", "addDataSourceConfig");
        digester.addObjectCreate("reports/report/transformer", TransformerConfig.class);
        digester.addSetProperties("reports/report/transformer");
        digester.addObjectCreate("reports/report/transformer/templates/template", TemplateConfig.class);
        digester.addSetProperties("reports/report/transformer/templates/template");
        digester.addSetNext("reports/report/transformer/templates/template", "addTemplateConfig");
        digester.addSetNext("reports/report/transformer", "addTransformerConfig");
        digester.addSetNext("reports/report", "addReportConfig");

    }

}