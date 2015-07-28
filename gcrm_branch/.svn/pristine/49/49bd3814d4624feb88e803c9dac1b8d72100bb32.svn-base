package com.baidu.gcrm.data.service.generator;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.GcrmConfig;

@Service("materialDefaultGenerator")
public class MaterialDefaultGenerator extends ADContentGenerator {

    @Override
    protected String getLocalFileName() {
        return GcrmConfig.getConfigValueByKey("file.materialdefault.name");
    }

}
