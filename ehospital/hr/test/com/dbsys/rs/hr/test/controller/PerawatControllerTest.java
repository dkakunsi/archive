package com.dbsys.rs.hr.test.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.dbsys.rs.hr.repository.PegawaiRepository;
import com.dbsys.rs.hr.service.PegawaiService;
import com.dbsys.rs.hr.test.TestConfig;
import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.entity.Pegawai;
import com.dbsys.rs.lib.entity.Penduduk.Kelamin;
import com.dbsys.rs.lib.entity.Perawat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PerawatControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private long count;

	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	
	private Pegawai pegawai;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = pegawaiRepository.count();
		
		pegawai = new Perawat();
		pegawai.setAgama("Kristen");
		pegawai.setDarah("O");
		pegawai.setKelamin(Kelamin.PRIA);
		pegawai.setNama("Apoteker");
		pegawai.setNik("Nik");
		pegawai.setNip("Nip");
		pegawai.setTanggalLahir(DateUtil.getDate());
		pegawai.setTelepon("Telepon");
		pegawai = pegawaiService.save(pegawai);
		
		assertEquals(count + 1, pegawaiRepository.count());
	}
	
	@Test
	public void testSave() throws Exception {
		this.mockMvc.perform(
				post("/pegawai")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"agama\": \"Kristen\","
						+ "\"darah\": \"O\","
						+ "\"kelamin\": \"PRIA\","
						+ "\"nama\":\"Apoteker 2\","
						+ "\"nik\":\"nik 2\","
						+ "\"nip\":\"nip 2\","
						+ "\"tanggalLahir\":\"1991-12-05\","
						+ "\"telepon\":\"telepon 2\","
						+ "\"tipePegawai\": \"PERAWAT\"}")
						
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.tipe").value("PERAWAT"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 2, pegawaiRepository.count());
	}

	@Test
	public void testGetAll() throws Exception {
		this.mockMvc.perform(
				get("/pegawai")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testCariNama() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pegawai/keyword/%s", pegawai.getNama()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testCariNip() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pegawai/keyword/%s", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetAllPerawat() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pegawai/class/%s", Perawat.class.getSimpleName()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testCariPerawatNama() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pegawai/keyword/%s/class/%s", pegawai.getNama(), Perawat.class.getSimpleName()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testCariPerawatNip() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pegawai/keyword/%s/class/%s", pegawai.getNip(), Perawat.class.getSimpleName()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}
}
