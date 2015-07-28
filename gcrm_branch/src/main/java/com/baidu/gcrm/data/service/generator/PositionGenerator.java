package com.baidu.gcrm.data.service.generator;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.data.bean.PositionBean;
import com.baidu.gcrm.data.service.FileGenerator;
import com.thoughtworks.xstream.XStream;

@Service("PositionGenerator")
public class PositionGenerator extends FileGenerator {

	@Override
	protected String getLocalFileName() {
		return GcrmConfig.getConfigValueByKey("file.position.name");
	}

	@Override
	protected void alias(XStream xstream) {
		xstream.alias("Position", PositionBean.class);
		xstream.alias("Positions", List.class);
	}

}
