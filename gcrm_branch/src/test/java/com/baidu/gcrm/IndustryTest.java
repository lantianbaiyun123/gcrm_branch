package com.baidu.gcrm;

import java.sql.ResultSet;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.valuelistcache.model.Industry;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;


@Ignore
public class IndustryTest extends BaseTestContext {
	@Autowired
	private AbstractValuelistCacheService<Industry> industryServiceImpl;

	@Before
	public void init() throws Exception {
		TestUtils.initDatabase(dataSource, "datas/g_industry.xml");
	}

	@Test
	public void updateTnRecordMarkFailedBecauseInvalidChangedNum() throws Exception {
		List<Industry> is = industryServiceImpl.getAll();
		ResultSet rs = super.dataSource.getConnection()
		.prepareStatement("select * from g_industry").executeQuery();
//		while(rs.next()){
//			System.out.println(rs.getInt(1));
//		}
//		System.out.println("*************"+is.size());
	}

}
