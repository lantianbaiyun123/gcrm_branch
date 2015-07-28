package com.baidu.gcrm.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.TestUtils;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.data.model.ADContentDataSample;
import com.baidu.gcrm.data.model.ADContentStatistics;
import com.baidu.gcrm.data.service.IADContentDataStatisService;

@Ignore
public class ADContentDataSampleServiceTest extends BaseTestContext {

    @Autowired
    IADContentDataStatisService adContentDataStatisService;

    @Autowired
    IAdSolutionContentService adSolutionContentService;

    @Before
    public void init() throws Exception {
        TestUtils.initDatabase(dataSource, "datas/g_data_sample.xml");
    }

    @Test
    @Rollback(false)
    public void testUpdateAdcontentDataStatistics() {
        List<ADContentDataSample> contentSamples = new ArrayList<ADContentDataSample>();

        ADContentDataSample contentSample = null;

        contentSample = new ADContentDataSample();
        contentSample.setAdContentId(6288L);
        contentSamples.add(contentSample);

        contentSample = new ADContentDataSample();
        contentSample.setAdContentId(6298L);
        contentSamples.add(contentSample);
        
        contentSample = new ADContentDataSample();
        contentSample.setAdContentId(6380L);
        contentSamples.add(contentSample);

        adContentDataStatisService.updateADContentStatistics(contentSamples);

        ADContentStatistics adContentStatistics = null;
        AdSolutionContent content = adSolutionContentService.findOne(6288L);
		adContentStatistics =
                adContentDataStatisService.findByAdContentId(content.getId());
        Assert.assertTrue(adContentStatistics.getAccumulatedAmount() == 20);
        Assert.assertTrue(adContentStatistics.getAccumulatedDays() == 6);
        Assert.assertTrue(adContentStatistics.getImpressions() == 14);
        Assert.assertTrue(adContentStatistics.getClicks() == 20);
        Assert.assertTrue(adContentStatistics.getUv() == 26);
        Assert.assertTrue(adContentStatistics.getClickUV() == 32);

        content = adSolutionContentService.findOne(6298L);
        adContentStatistics =
                adContentDataStatisService.findByAdContentId(content.getId());
        Assert.assertTrue(adContentStatistics.getAccumulatedAmount() == 11);
        Assert.assertTrue(adContentStatistics.getAccumulatedDays() == 1);
        Assert.assertTrue(adContentStatistics.getImpressions() == 11);
        Assert.assertTrue(adContentStatistics.getClicks() == 22);
        Assert.assertTrue(adContentStatistics.getUv() == 33);
        Assert.assertTrue(adContentStatistics.getClickUV() == 44);
        
        content = adSolutionContentService.findOne(6380L);
        adContentStatistics =
                adContentDataStatisService.findByAdContentId(content.getId());
        Assert.assertTrue(adContentStatistics.getAccumulatedAmount() == 1);
        Assert.assertTrue(adContentStatistics.getAccumulatedDays() == 1);
        Assert.assertTrue(adContentStatistics.getImpressions() == 1);
        Assert.assertTrue(adContentStatistics.getClicks() == 2);
        Assert.assertTrue(adContentStatistics.getUv() == 3);
        Assert.assertTrue(adContentStatistics.getClickUV() == 4);

    }
}
