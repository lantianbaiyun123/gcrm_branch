package com.baidu.gcrm.data.service.generator;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.data.bean.ADContent;
import com.baidu.gcrm.data.bean.MaterialAttachment;
import com.baidu.gcrm.data.service.FileGenerator;
import com.thoughtworks.xstream.XStream;

@Service("ADContentGenerator")
public class ADContentGenerator extends FileGenerator {

	@Override
	protected String getLocalFileName() {
		return GcrmConfig.getConfigValueByKey("file.adcompaign.name");
	}

	@Override
	protected void alias(XStream xStream) {
		xStream.alias("ADContent", ADContent.class);
		xStream.alias("MaterialAttachment", MaterialAttachment.class);
		xStream.alias("ADContents", List.class);
	}
}
