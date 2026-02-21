package com.dbsys.rs.lib.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.dbsys.rs.lib.entity.Pelayanan;
import com.dbsys.rs.lib.entity.Tagihan;

public class TagihanTest {

	@Test
	public void test_IsPersisted_True() {
		Tagihan tagihan = new Pelayanan();
		tagihan.setId(1L);
		
		assertTrue(tagihan.isPersisted());
	}

	@Test
	public void test_IsPersisted_False_Null() {
		Tagihan tagihan = new Pelayanan();
		
		assertFalse(tagihan.isPersisted());
	}

	@Test
	public void test_IsPersisted_False_Zero() {
		Tagihan tagihan = new Pelayanan();
		tagihan.setId(0L);
		
		assertFalse(tagihan.isPersisted());
	}
	
	@Test
	public void test() {
		Long l = 200000l;
		Long l2 = (l * 25 / 100);
		
		assertEquals(new Long(50000), l2);
	}
}
