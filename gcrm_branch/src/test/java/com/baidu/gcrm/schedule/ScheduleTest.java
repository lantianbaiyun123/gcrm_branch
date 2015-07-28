package com.baidu.gcrm.schedule;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.TestUtils;
import com.baidu.gcrm.schedule.dao.IScheduleRepository;
import com.baidu.gcrm.schedule.dao.IScheduleRepositoryCustom;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;

@Ignore
public class ScheduleTest extends BaseTestContext {
	@Autowired
	private IScheduleRepositoryCustom scheduleCustomDao;
	@Autowired
	private IScheduleRepository scheduleDao;
	
	@Before
	public void init() throws Exception {
		TestUtils.initDatabase(dataSource, "datas/g_schedule.xml");
	}

	@Test
	public void testGetNumbersByPositionAndStatus() {
		List<String> numbers = scheduleCustomDao.findNumbersByPositionAndStatus(1l, ScheduleStatus.confirmed);
		Assert.assertEquals(2, numbers.size());
		Assert.assertEquals("22222222", numbers.get(0));
	}

}
