package com.dbsys.rs.treatment.test.controller;

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

import com.dbsys.rs.lib.entity.KategoriTindakan;
import com.dbsys.rs.treatment.repository.KategoriRepository;
import com.dbsys.rs.treatment.service.KategoriService;
import com.dbsys.rs.treatment.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class KategoriControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private long count;
	
	@Autowired
	private KategoriService kategoriService;
	@Autowired
	private KategoriRepository kategoriRepository;

	private KategoriTindakan kategori;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = kategoriRepository.count();
		
		kategori = new KategoriTindakan();
		kategori.setNama("Kategori 1");
		kategoriService.simpan(kategori);
		assertEquals(count + 1, kategoriRepository.count());
		
		KategoriTindakan kategori2 = new KategoriTindakan(kategori);
		kategori2.setNama("Kategori 2");
		kategoriService.simpan(kategori2);
		assertEquals(count + 2, kategoriRepository.count());
	}
	
	@Test
	public void testSimpan() throws Exception {
		this.mockMvc.perform(
				post("/kategori")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nama\": \"Kategori 3\"}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 3, kategoriRepository.count());
	}
	
	@Test
	public void testSimpanSubKategori() throws Exception {
		this.mockMvc.perform(
				post("/kategori")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nama\": \"Kategori 3\","
						+ "\"parent\": {"
						+ "\"id\":\"" + kategori.getId() + "\","
						+ "\"nama\":\"Kategori 2\""
						+ "}"
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 3, kategoriRepository.count());
	}

	@Test
	public void testGetById() throws Exception {
		this.mockMvc.perform(
				get(String.format("/kategori/%s", kategori.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetAll() throws Exception {
		this.mockMvc.perform(
				get("/kategori")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

}
