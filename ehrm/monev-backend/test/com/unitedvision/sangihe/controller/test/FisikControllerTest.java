package com.unitedvision.sangihe.controller.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.Month;

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
import com.unitedvision.sangihe.monev.entity.Fisik;
import com.unitedvision.sangihe.monev.entity.Kegiatan;
import com.unitedvision.sangihe.monev.entity.Program;
import com.unitedvision.sangihe.monev.entity.UnitKerja;
import com.unitedvision.sangihe.monev.entity.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.monev.exception.FisikException;
import com.unitedvision.sangihe.monev.exception.WrongYearException;
import com.unitedvision.sangihe.monev.repository.FisikRepository;
import com.unitedvision.sangihe.monev.repository.UnitKerjaRepository;
import com.unitedvision.sangihe.monev.service.FisikService;
import com.unitedvision.sangihe.monev.service.KegiatanService;
import com.unitedvision.sangihe.monev.service.ProgramService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class FisikControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Autowired
	private FisikService fisikService;
	@Autowired
	private KegiatanService kegiatanService;
	@Autowired
	private ProgramService programService; 
	@Autowired
	private UnitKerjaRepository unitKerjaRepository;
	@Autowired
	private FisikRepository fisikRepository;
	
	private UnitKerja unitKerja;
	private Program program;
	private Kegiatan kegiatan;
	private Fisik fisik;
	
	private Long id;
	
	@Before
	public void setup() throws FisikException, WrongYearException {
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

		fisik = new Fisik(kegiatan);
		fisik.setTahun(2015);
		fisik.setBulan(Month.JANUARY);
		fisik.setRealisasi(10);
		fisikService.simpan(fisik);
		
		assertEquals(1, fisikRepository.count());
		
		id = fisik.getId();
	}
	
	@Test
	public void test_simpan() throws Exception {
		this.mockMvc.perform(
				post(String.format("/fisik/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2015\", "
						+ "\"bulan\":\"FEBRUARY\", "
						+ "\"realisasi\":\"10\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_simpan_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/fisik/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2015\", "
						+ "\"bulan\":\"JANUARY\", "
						+ "\"realisasi\":\"10\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Fisik sudah terdaftar"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_simpan_salah_tahun() throws Exception {
		this.mockMvc.perform(
				post(String.format("/fisik/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2016\", "
						+ "\"bulan\":\"JANUARY\", "
						+ "\"realisasi\":\"10\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Tahun realisasi tidak termasuk dalam jangkauan tahun program"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_simpan_salah_realisasi() throws Exception {
		this.mockMvc.perform(
				post(String.format("/fisik/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2015\", "
						+ "\"bulan\":\"JANUARY\", "
						+ "\"realisasi\":\"101\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Realisasi melebihi 100%"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_simpan_with_foto() throws Exception {
		this.mockMvc.perform(
				post(String.format("/fisik/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tahun\":\"2015\", "
						+ "\"bulan\":\"FEBRUARY\", "
						+ "\"realisasi\":\"10\", "
						+ "\"daftarFoto\": "
						+ "["
						+ "{ \"location\": \"http://picasa.com/sangihe/01/001.jpg\"}, "
						+ "{ \"location\": \"http://picasa.com/sangihe/01/002.jpg\"}"
						+ "]"
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_tambah_foto_list() throws Exception {
		this.mockMvc.perform(
				put(String.format("/fisik/%d/foto", id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("["
						+ "{ \"location\": \"http://picasa.com/sangihe/01/001.jpg\"}, "
						+ "{ \"location\": \"http://picasa.com/sangihe/01/002.jpg\"}"
						+ "]")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"));
	}
	
	@Test
	public void test_tambah_foto() throws Exception {
		this.mockMvc.perform(
				put(String.format("/fisik/%d/foto/location/", id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"location\": \"http://picasa.com/sangihe/01/001.jpg\"}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"));
	}
	
	@Test
	public void test_hapus() throws Exception {
		this.mockMvc.perform(
				delete(String.format("/fisik/%d", id))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_get() throws Exception {
		this.mockMvc.perform(
				get(String.format("/fisik/kegiatan/%d/tahun/%d/bulan/%s", kegiatan.getId(), 2015, "JANUARY"))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"));
	}
	
	@Test
	public void test_get_by_id() throws Exception {
		this.mockMvc.perform(
				get(String.format("/fisik/%d", id))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"));
	}
	
	@Test
	public void test_get_by_program() throws Exception {
		this.mockMvc.perform(
				get(String.format("/fisik/program/%d", program.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
	@Test
	public void test_get_by_kegiatan() throws Exception {
		this.mockMvc.perform(
				get(String.format("/fisik/kegiatan/%d", kegiatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
}
