package com.unitedvision.sangihe.controller.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import com.unitedvision.sangihe.configuration.test.TestConfig;
import com.unitedvision.sangihe.monev.entity.Program;
import com.unitedvision.sangihe.monev.entity.UnitKerja;
import com.unitedvision.sangihe.monev.entity.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.monev.repository.ProgramRepository;
import com.unitedvision.sangihe.monev.repository.UnitKerjaRepository;
import com.unitedvision.sangihe.monev.service.ProgramService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class ProgramControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Autowired
	private UnitKerjaRepository unitKerjaRepository;
	@Autowired
	private ProgramService programService;
	@Autowired
	private ProgramRepository programRepository;

	private UnitKerja unitKerja;
	private Program program;
	
	private Long id;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		unitKerja = new UnitKerja();
		unitKerja.setNama("Dinas Pariwisata");
		unitKerja.setSingkatan("DISPAR2");
		unitKerja.setTipe(TipeUnitKerja.DINAS);
		unitKerjaRepository.save(unitKerja);
		
		program = new Program(unitKerja);
		program.setNama("Pembangunan Object Wisata");
		program.setTahunAwal(2015);
		program.setTahunAkhir(2015);
		programService.simpan(program);
		
		assertEquals(1, programRepository.count());
		
		id = program.getId();
	}
	
	@Test
	public void test_tambah() throws Exception {
		this.mockMvc.perform(
				post(String.format("/program/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"nama\":\"Pengembangan Object Wisata\", "
						+ "\"tahunAwal\":\"2015\", "
						+ "\"tahunAkhir\":\"2015\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_tambah_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/program/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"nama\":\"Pembangunan Object Wisata\", "
						+ "\"tahunAwal\":\"2015\", "
						+ "\"tahunAkhir\":\"2015\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Program sudah terdaftar"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_hapus() throws Exception {
		this.mockMvc.perform(
				delete(String.format("/program/%d", id))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_get() throws Exception {
		this.mockMvc.perform(
				get(String.format("/program/%d", id))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"));
	}
	
	@Test
	public void test_get_not_found() throws Exception {
		this.mockMvc.perform(
				get(String.format("/program/%d", 1000001212))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Data tidak ditemukan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_get_all() throws Exception {
		this.mockMvc.perform(
				get("/program")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
	@Test
	public void test_get_by_unit_kerja() throws Exception {
		this.mockMvc.perform(
				get(String.format("/program/satker/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
	@Test
	public void test_get_by_unit_kerja_not_found() throws Exception {
		this.mockMvc.perform(
				get(String.format("/program/satker/%d", 1000001212))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Tidak ada data yang ditemukan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_cari() throws Exception {
		this.mockMvc.perform(
				get(String.format("/program/cari/%s", "Object"))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
}
