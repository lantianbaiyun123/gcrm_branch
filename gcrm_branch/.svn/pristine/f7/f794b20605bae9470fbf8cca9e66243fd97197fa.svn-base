package com.baidu.gcrm.occupation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.TestUtils;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.occupation.dao.IADPositionDateRelationRepository;
import com.baidu.gcrm.occupation.dao.IADPositionDateRelationRepositoryCustom;
import com.baidu.gcrm.occupation.model.AdvertisePositionDateRelation;

@Ignore
public class OccupationRelationTest extends BaseTestContext {
	@Autowired
	private IADPositionDateRelationRepositoryCustom relationCustomDao;
	@Autowired
	private IADPositionDateRelationRepository relationDao;

	@Before
	public void init() throws Exception {
		TestUtils.initDatabase(dataSource, "datas/g_position_occupation.xml");
	}
	
	@Test
	public void findAdcontentIdsByPositionOccIdInTest() {
		List<Long> positionOccIds = new ArrayList<Long>();
		positionOccIds.add(1l);
		positionOccIds.add(2l);
		positionOccIds.add(3l);
		List<Long> ids = relationCustomDao.findAdcontentIdsByPositionOccIdIn(positionOccIds);
		Assert.assertEquals(6, ids.size());
		List<AdvertisePositionDateRelation> relations = relationDao.findByPositionOccIdIn(positionOccIds);
		Assert.assertEquals(6, relations.size());
	}

	
	@Test
	public void findIdlePositionOccIdsTest() {
		List<Long> positionOccIds = new ArrayList<Long>();
		positionOccIds.add(1l);
		positionOccIds.add(2l);
		positionOccIds.add(3l);
		positionOccIds.add(8l);
		positionOccIds.add(9l);
		List<Long> occIds = relationCustomDao.findIdlePositionOccIds(positionOccIds);
		Assert.assertEquals(4, occIds.size());
		Assert.assertTrue(occIds.contains(new Long(2l)));
		Assert.assertTrue(occIds.contains(new Long(9l)));
	}
	
	@Test
	public void getCountGroupByPositionOccIdTest() {
		List<Long> positionOccIds = new ArrayList<Long>();
		positionOccIds.add(1l);
		positionOccIds.add(2l);
		positionOccIds.add(8l);
		positionOccIds.add(9l);
		positionOccIds.add(10l);
		positionOccIds.add(3l);
		Map<Long, Long> occIds = relationCustomDao.getCountGroupByPositionOccId(positionOccIds);
		Assert.assertEquals(4, occIds.size());
		Assert.assertEquals(2, occIds.get(new Long(2l)).intValue());
		Assert.assertEquals(2, occIds.get(new Long(9l)).intValue());
		Assert.assertEquals(3, occIds.get(new Long(10l)).intValue());
		Assert.assertEquals(4, occIds.get(new Long(3l)).intValue());
	}
	
	@Test
	public void getBusyCountGroupByPositionOccIdTest() {
		List<Long> positionOccIds = new ArrayList<Long>();
		positionOccIds.add(2l);
		positionOccIds.add(3l);
		positionOccIds.add(6l);
		positionOccIds.add(7l);
		positionOccIds.add(10l);
		Map<Long, Long> occIds = relationCustomDao.getBusyCountGroupByPositionOccId(positionOccIds);
		Assert.assertEquals(4, occIds.size());
		Assert.assertEquals(2, occIds.get(new Long(3l)).intValue());
		Assert.assertEquals(3, occIds.get(new Long(6l)).intValue());
		Assert.assertEquals(3, occIds.get(new Long(7l)).intValue());
		Assert.assertEquals(1, occIds.get(new Long(10l)).intValue());
	}
	
	@Test
	public void getMaxIdleOccupationDateTest() {
		Date maxIdleDate = relationCustomDao.getMaxIdleOccupationDate(1l);
		Assert.assertEquals(DateUtils.getString2Date("2014-01-29"), maxIdleDate);
		maxIdleDate = relationCustomDao.getMaxIdleOccupationDate(2l);
		Assert.assertEquals(DateUtils.getString2Date("2014-01-29"), maxIdleDate);
	}
	
	@Test
	public void getMaxOccupiedOccupationDateTest() {
		Date maxIdleDate = relationCustomDao.getMaxOccupiedOccupationDate(1l);
		Assert.assertEquals(DateUtils.getString2Date("2014-02-03"), maxIdleDate);
		maxIdleDate = relationCustomDao.getMaxOccupiedOccupationDate(2l);
		Assert.assertEquals(DateUtils.getString2Date("2014-02-01"), maxIdleDate);
	}
}
