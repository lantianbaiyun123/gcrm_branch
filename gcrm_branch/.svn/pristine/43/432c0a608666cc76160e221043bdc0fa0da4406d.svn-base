package com.baidu.gcrm.occupation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.baidu.gcrm.occupation.dao.IPositionOccupationRepository;
import com.baidu.gcrm.occupation.dao.IPositionOccupationRepositoryCustom;
import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.occupation.model.PositionOccupation.OccupationStatus;

@Ignore
public class OccupationDaoTest extends BaseTestContext {
	@Autowired
	private IPositionOccupationRepositoryCustom occupationCustomDao;
	@Autowired
	private IPositionOccupationRepository occupationDao;

	@Before
	public void init() throws Exception {
		TestUtils.initDatabase(dataSource, "datas/g_position_occupation.xml");
	}
	
	@Test
	public void findOccupiedByPositionAndDateBetweenTest() {
		Date from = DateUtils.getString2Date("2014-01-27");
		Date to = DateUtils.getString2Date("2014-02-05");
		List<PositionOccupation> occupations = occupationDao.findOccupiedByPositionAndDateBetween(1l, from, to);
		Assert.assertEquals(4, occupations.size());
		Assert.assertEquals(",22222222,66666666,", occupations.get(0).getCurScheduleNumber());
		occupations = occupationDao.findOccupiedByPositionAndDateBetween(2l, from, to);
		Assert.assertEquals(2, occupations.size());
		Assert.assertEquals(",99999999,", occupations.get(0).getCurScheduleNumber());
	}
	
	@Test
	public void findByCurScheduleNumberFazzyLikeTest() {
		List<PositionOccupation> occupations = occupationCustomDao.findByCurScheduleNumberFazzyLike("66666666");
		Assert.assertEquals(3, occupations.size());
		occupations = occupationCustomDao.findByCurScheduleNumberFazzyLike("6666666");
		Assert.assertEquals(0, occupations.size());
	}
	
	@Test
	public void getFarthestDateByPositionTest() throws ParseException {
		Date date = occupationCustomDao.getFarthestDateByPosition(2l);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date when = df.parse("2014-01-29");
		Assert.assertTrue(date.after(when));
		Assert.assertEquals(df.parse("2014-02-01"), date);
		date = occupationCustomDao.getFarthestDateByPosition(3l);
		Assert.assertNull(date);
	}
	
	@Test
	public void getReservedDateBetweenTest() {
		Date from = DateUtils.getString2Date("2014-01-28");
		Date to = DateUtils.getString2Date("2014-02-01");
		List<Date> reservedDates1 = occupationCustomDao.getReservedDateBetween(2l, from, to);
		Assert.assertEquals(1, reservedDates1.size());
		Assert.assertEquals(DateUtils.getString2Date("2014-01-30"), reservedDates1.get(0));
	}
	
	@Test
	public void getReservedPositionCountBetweenTest() {
		Date from = DateUtils.getString2Date("2014-01-28");
		Date to = DateUtils.getString2Date("2014-02-03");
		Map<Long, Long> map = occupationCustomDao.getReservedPositionCountBetween(1l, from, to);
		Assert.assertEquals(4, map.size());
		Assert.assertEquals(2, map.get(new Long(3l)).intValue());
		Assert.assertEquals(1, map.get(new Long(4l)).intValue());
		Assert.assertEquals(1, map.get(new Long(5l)).intValue());
		Assert.assertEquals(1, map.get(new Long(6l)).intValue());
		map = occupationCustomDao.getReservedPositionCountBetween(2l, from, to);
		Assert.assertEquals(1, map.size());
		Assert.assertEquals(1, map.get(new Long(10l)).intValue());
	}
	
	@Test
	public void getDateMapByDateBetweenTest() {
		Date from = DateUtils.getString2Date("2014-01-28");
		Date to = DateUtils.getString2Date("2014-02-03");
		Map<Long, Date> map = occupationCustomDao.getDateMapByDateBetween(2l, from, to);
		Assert.assertEquals(5, map.size());
		Assert.assertEquals(from, map.get(new Long(8l)));
	}
	
	@Test
	public void findOccupationByDateInTest() {
		Date date1 = DateUtils.getString2Date("2014-01-28");
		Date date2 = DateUtils.getString2Date("2014-02-01");
		List<Date> dates = new ArrayList<Date>();
		dates.add(date1);
		dates.add(date2);
		
		List<PositionOccupation> occupations = occupationDao.findOccupationByDateIn(2l, dates);
		Assert.assertEquals(2, occupations.size());
		Assert.assertEquals(date2, occupations.get(1).getDate());
	}
	
	@Test
	public void updateStatusTest() {
		int count = occupationDao.updateStatus(OccupationStatus.DISABLED, DateUtils.getString2Date("2014-01-28"), 2l);
		Assert.assertEquals(5, count);
		
		count = occupationDao.updateStatus(OccupationStatus.DISABLED, DateUtils.getString2Date("2014-01-28"), 1l);
		Assert.assertEquals(7, count);
		
		PositionOccupation occupation = occupationDao.findOne(5l);
		Assert.assertEquals(OccupationStatus.DISABLED, occupation.getStatus());
	}
}
