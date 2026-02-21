package com.dbsys.rs.account.test.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.dbsys.rs.account.repository.UnitRepository;
import com.dbsys.rs.account.service.UnitService;
import com.dbsys.rs.account.test.TestConfig;
import com.dbsys.rs.lib.UnauthenticatedAccessException;
import com.dbsys.rs.lib.entity.Unit;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class UnitControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private long count;

	@Autowired
	private UnitService unitService;
	@Autowired
	private UnitRepository unitRepository;
	
	private Unit unit;
	
	@Before
	public void setup() throws UnauthenticatedAccessException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = unitRepository.count();
		
		unit = new Unit();
		unit.setNama("Unit");
		unit.setBobot(1f);
		unit.setTipe(Unit.TipeUnit.APOTEK_FARMASI);
		unit = unitService.save(unit);
		
		assertEquals(count + 1, unitRepository.count());
	}
	
	@Test
	public void testSimpan() throws Exception {
		this.mockMvc.perform(
				post("/unit")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nama\": \"Unit 1\","
						+ "\"bobot\": \"1\","
						+ "\"tipe\": \"APOTEK_FARMASI\"}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 2, unitRepository.count());
	}

	@Test
	public void testDelete() throws Exception {
		this.mockMvc.perform(
				delete(String.format("/unit/%s", unit.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGet() throws Exception {
		this.mockMvc.perform(
				get(String.format("/unit/%s", unit.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.tipe").value("APOTEK_FARMASI"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetAll() throws Exception {
		this.mockMvc.perform(
				get("/unit")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}
}
