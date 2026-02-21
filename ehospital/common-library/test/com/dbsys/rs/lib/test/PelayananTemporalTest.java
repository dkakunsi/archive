package com.dbsys.rs.lib.test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.time.Month;

import org.junit.Test;

import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.entity.PelayananTemporal;

public class PelayananTemporalTest {

	@Test
	public void testHitungJumlahHari() {
		PelayananTemporal tagihan = new PelayananTemporal();
		tagihan.setTanggal(DateUtil.getDate(2015, Month.DECEMBER, 10));
		tagihan.setJamMasuk(DateUtil.getTime(6, 0, 0));
		
		assertEquals(new Integer(3), tagihan.hitungJumlahHari());
	}

	@Test
	public void testHitungJumlahJam() {
		PelayananTemporal tagihan = new PelayananTemporal();
		tagihan.setTanggal(DateUtil.getDate());
		tagihan.setJamMasuk(DateUtil.getTime(1, 0, 0));
		
		assertEquals(new Integer(9), tagihan.hitungJumlahJam());
	}
	
	@Test
	public void testGetHourSameDay() {
		Date date1 = DateUtil.getDate();
		Date date2 = date1;
		
		Time time1 = DateUtil.getTime(21, 0, 0);
		Time time2 = DateUtil.getTime(23, 0, 0);
		
		PelayananTemporal pelayanan = new PelayananTemporal();
		Integer i = pelayanan.getJumlahJam(date1, date2, time1, time2);
		
		assertEquals(new Integer(2), i);
	}
	
	@Test
	public void testGetHourDifferentDay_LessThan24Hours() {
		Date date1 = DateUtil.getDate();
		Date date2 = DateUtil.add(date1, 1);
		
		Time time1 = DateUtil.getTime(21, 0, 0);
		Time time2 = DateUtil.getTime(3, 0, 0);
		
		PelayananTemporal pelayanan = new PelayananTemporal();
		Integer i = pelayanan.getJumlahJam(date1, date2, time1, time2);
		
		assertEquals(new Integer(6), i);
	}
	
	@Test
	public void testGetHourDifferentDay_MoreThan24Hours() {
		Date date1 = DateUtil.getDate();
		Date date2 = DateUtil.add(date1, 1);
		
		Time time1 = DateUtil.getTime(21, 0, 0);
		Time time2 = DateUtil.getTime(22, 0, 0);
		
		PelayananTemporal pelayanan = new PelayananTemporal();
		Integer i = pelayanan.getJumlahJam(date1, date2, time1, time2);
		
		assertEquals(new Integer(25), i);
	}
	
	@Test
	public void testGetHourDifferentDay_Equals24Hours() {
		Date date1 = DateUtil.getDate();
		Date date2 = DateUtil.add(date1, 1);
		
		Time time1 = DateUtil.getTime(21, 0, 0);
		Time time2 = DateUtil.getTime(21, 0, 0);
		
		PelayananTemporal pelayanan = new PelayananTemporal();
		Integer i = pelayanan.getJumlahJam(date1, date2, time1, time2);
		
		assertEquals(new Integer(24), i);
	}

}
