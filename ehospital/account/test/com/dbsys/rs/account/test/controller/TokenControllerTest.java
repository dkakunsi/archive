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

import com.dbsys.rs.account.repository.TokenRepository;
import com.dbsys.rs.account.service.OperatorService;
import com.dbsys.rs.account.service.TokenService;
import com.dbsys.rs.account.service.UnitService;
import com.dbsys.rs.account.test.TestConfig;
import com.dbsys.rs.lib.UnauthenticatedAccessException;
import com.dbsys.rs.lib.entity.Operator;
import com.dbsys.rs.lib.entity.Token;
import com.dbsys.rs.lib.entity.Unit;
import com.dbsys.rs.lib.entity.Operator.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class TokenControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private long count;

	@Autowired
	private OperatorService operatorService;
	
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private TokenRepository tokenRepository;

	private Operator operator;
	private Token token;
	
	@Before
	public void setup() throws UnauthenticatedAccessException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = tokenRepository.count();
		
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
		operatorService.save(operator);

		token = tokenService.create("Username", "Password");
		
		assertEquals(count + 1, tokenRepository.count());
		
	}
	
	@Test
	public void testCreate() throws Exception {
		Thread.sleep(2000L);
		this.mockMvc.perform(
				post("/token")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\": \"Username\","
						+ "\"password\": \"Password\"}")
						
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 2, tokenRepository.count());
	}

	@Test
	public void testLock() throws Exception {
		this.mockMvc.perform(
				put(String.format("/token/%s", token.getKode()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

}
