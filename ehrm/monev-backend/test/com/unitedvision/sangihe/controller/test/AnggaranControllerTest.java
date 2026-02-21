package com.unitedvision.sangihe.controller.test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Month;

import org.junit.Before;
import org.junit.Ignore;
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
import com.unitedvision.sangihe.monev.entity.Anggaran;
import com.unitedvision.sangihe.monev.entity.Kegiatan;
import com.unitedvision.sangihe.monev.entity.Program;
import com.unitedvision.sangihe.monev.entity.UnitKerja;
import com.unitedvision.sangihe.monev.entity.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.monev.exception.AnggaranException;
import com.unitedvision.sangihe.monev.exception.WrongYearException;
import com.unitedvision.sangihe.monev.repository.AnggaranRepository;
import com.unitedvision.sangihe.monev.repository.UnitKerjaRepository;
import com.unitedvision.sangihe.monev.service.AnggaranService;
import com.unitedvision.sangihe.monev.service.KegiatanService;
import com.unitedvision.sangihe.monev.service.ProgramService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class AnggaranControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Autowired
	private AnggaranService anggaranService;
	@Autowired
	private KegiatanService kegiatanService;
	@Autowired
	private ProgramService programService; 
	@Autowired
	private UnitKerjaRepository unitKerjaRepository;
	@Autowired
	private AnggaranRepository anggaranRepository;
	
	private UnitKerja unitKerja;
	private Program program;
	private Kegiatan kegiatan;
	private Anggaran anggaran;
	
	private Long id;
	private Long id2;
	
	@Before
	public void setup() throws AnggaranException, WrongYearException {
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

		kegiatan = new Kegiatan(program);
		kegiatan.setNama("Pengembangan Pantai Pananuareng");
		kegiatan.setPaguAnggaran(100000000L);
		kegiatanService.simpan(kegiatan);

		anggaran = new Anggaran(kegiatan);
		anggaran.setTahun(2015);
		anggaran.setBulan(Month.JANUARY);
		anggaran.setRencana(10000000L);
		anggaranService.simpan(anggaran);

		Anggaran anggaran2 = new Anggaran(kegiatan);
		anggaran2.setTahun(2015);
		anggaran2.setBulan(Month.MAY);
		anggaran2.setRencana(10000000L);
		anggaranService.simpan(anggaran2);
		
		assertEquals(2, anggaranRepository.count());
		
		id = anggaran.getId();
		id2 = anggaran2.getId();
	}
	
	@Test
	public void test_simpan() throws Exception {
		this.mockMvc.perform(
				post(String.format("/anggaran/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2015\", "
						+ "\"bulan\":\"FEBRUARY\", "
						+ "\"rencana\":\"10000000\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_simpan_salah_tahun() throws Exception {
		this.mockMvc.perform(
				post(String.format("/anggaran/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2016\", "
						+ "\"bulan\":\"FEBRUARY\", "
						+ "\"rencana\":\"10000000\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Tahun Anggaran tidak termasuk dalam jangkauan tahun program"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_simpan_rencana_melebihi_pagu() throws Exception {
		this.mockMvc.perform(
				post(String.format("/anggaran/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2015\", "
						+ "\"bulan\":\"FEBRUARY\", "
						+ "\"rencana\":\"110000000\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Rencana anggaran melebihi anggaran kegiatan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_simpan_total_rencana_melebihi_pagu() throws Exception {
		this.mockMvc.perform(
				post(String.format("/anggaran/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2015\", "
						+ "\"bulan\":\"FEBRUARY\", "
						+ "\"rencana\":\"95000000\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Total rencana anggaran melebihi anggaran kegiatan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_simpan_realisasi_melebihi_pagu() throws Exception {
		this.mockMvc.perform(
				post(String.format("/anggaran/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2015\", "
						+ "\"bulan\":\"FEBRUARY\", "
						+ "\"rencana\":\"10000000\", "
						+ "\"realisasi\":\"110000000\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Realisasi anggaran melebihi anggaran kegiatan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_simpan_total_realisasi_melebihi_pagu() throws Exception {
		this.mockMvc.perform(
				post(String.format("/anggaran/%d/realisasi/%d", id, 8000000))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				post(String.format("/anggaran/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2015\", "
						+ "\"bulan\":\"FEBRUARY\", "
						+ "\"rencana\":\"10000000\", "
						+ "\"realisasi\":\"99000000\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Total realisasi anggaran melebihi anggaran kegiatan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_simpan_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/anggaran/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2015\", "
						+ "\"bulan\":\"JANUARY\", "
						+ "\"rencana\":\"10000000\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Anggaran sudah terdaftar"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_realisasi() throws Exception {
		this.mockMvc.perform(
				post(String.format("/anggaran/%d/realisasi/%d", id, 8000000))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Ignore
	@Test
	public void test_realisasi_melebihi_anggaran() throws Exception {
		this.mockMvc.perform(
				post(String.format("/anggaran/%d/realisasi/%d", id, 100000000))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Realisasi anggaran melebihi anggaran kegiatan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_total_realisasi_melebihi_anggaran() throws Exception {
		this.mockMvc.perform(
				post(String.format("/anggaran/%d/realisasi/%d", id, 8000000))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
		this.mockMvc.perform(
				post(String.format("/anggaran/%d/realisasi/%d", id2, 99000000))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Total realisasi anggaran melebihi anggaran kegiatan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_hapus() throws Exception {
		this.mockMvc.perform(
				delete(String.format("/anggaran/%d", id))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_get() throws Exception {
		this.mockMvc.perform(
				get(String.format("/anggaran/kegiatan/%d/tahun/%d/bulan/%s", kegiatan.getId(), 2015, "JANUARY"))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"));
	}
	
	@Test
	public void test_get_by_id() throws Exception {
		this.mockMvc.perform(
				get(String.format("/anggaran/%d", id))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"));
	}
	
	@Test
	public void test_get_by_program() throws Exception {
		this.mockMvc.perform(
				get(String.format("/anggaran/program/%d", program.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
	@Test
	public void test_get_by_kegiatan() throws Exception {
		this.mockMvc.perform(
				get(String.format("/anggaran/kegiatan/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
}
