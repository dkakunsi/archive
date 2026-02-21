package com.dbsys.rs.lib.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;
import java.time.Month;

import org.junit.Test;

import com.dbsys.rs.lib.DateUtil;

public class DateTest {
	
	@Test
	public void testGetDate() {
		Date date1 = DateUtil.getDate(2015, Month.DECEMBER, 12);
		Date now = DateUtil.getDate();
		
		assertEquals(date1, now);
	}
	
	@Test
	public void test() {
		Time t1 = DateUtil.getTime(6, 0, 0);
		Time t2 = DateUtil.getTime(7, 59, 0);
		
		int h = DateUtil.calculate(t1, t2);
		
		assertEquals(2, h);
	}
}
