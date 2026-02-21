package com.dbsys.rs.account.test.controller;

import static org.junit.Assert.assertEquals;
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

import com.dbsys.rs.account.repository.OperatorRepository;
import com.dbsys.rs.account.service.OperatorService;
import com.dbsys.rs.account.service.UnitService;
import com.dbsys.rs.account.test.TestConfig;
import com.dbsys.rs.lib.entity.Operator;
import com.dbsys.rs.lib.entity.Unit;
import com.dbsys.rs.lib.entity.Operator.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class OperatorControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private long count;

	@Autowired
	private OperatorService operatorService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private OperatorRepository operatorRepository;

	private Operator operator;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = operatorRepository.count();
		
		Unit unit = new Unit();
		unit.setNama("Unit");
		unit.setBobot(1f);
		unit.setTipe(Unit.TipeUnit.APOTEK_FARMASI);
		unitService.save(unit);
		
		operator = new Operator();
		operator.setUnit(unit);
		operator.setNama("Operator");
		operator.setPassword("Password");
		operator.setRole(Role.ADMIN);
		operator.setUsername("Username");
		operator = operatorService.save(operator);
		
		assertEquals(count + 1, operatorRepository.count());
	}

	@Test
	public void testTambahSuccess() throws Exception {
		this.mockMvc.perform(
				post("/operator")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nama\": \"Operator 2\","
						+ "\"password\": \"Password 2\","
						+ "\"role\":\"ADMIN\","
						+ "\"username\":\"Username 2\","
						+ "\"unit\":{"
						+ "\"id\": \"" + operator.getUnit().getId() + "\","
						+ "\"nama\":\"" + operator.getUnit().getNama() + "\","
						+ "\"bobot\": \"" + operator.getUnit().getBobot() + "\","
						+ "\"tipe\":\"" + operator.getUnit().getTipe() + "\"}"
						+ "}")
						
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 2, operatorRepository.count());
	}
	
	@Test
	public void testGetAll() throws Exception {
		this.mockMvc.perform(
				get("/operator")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testDelete() throws Exception {
		this.mockMvc.perform(
				delete(String.format("/operator/%s", operator.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}
}
