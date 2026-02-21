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

import com.dbsys.rs.lib.Kelas;
import com.dbsys.rs.lib.Penanggung;
import com.dbsys.rs.lib.entity.KategoriTindakan;
import com.dbsys.rs.lib.entity.Tindakan;
import com.dbsys.rs.lib.entity.Tindakan.SatuanTindakan;
import com.dbsys.rs.treatment.repository.KategoriRepository;
import com.dbsys.rs.treatment.repository.TindakanRepository;
import com.dbsys.rs.treatment.service.KategoriService;
import com.dbsys.rs.treatment.service.TindakanService;
import com.dbsys.rs.treatment.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class TindakanControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private long count;
	private long kategoriCount;
	
	@Autowired
	private KategoriService kategoriService;
	@Autowired
	private KategoriRepository kategoriRepository;
	@Autowired
	private TindakanService tindakanService;
	@Autowired
	private TindakanRepository tindakanRepository;

	private Tindakan tindakan;
	private KategoriTindakan kategori;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = tindakanRepository.count();
		kategoriCount = kategoriRepository.count();

		kategori = new KategoriTindakan();
		kategori.setNama("Kategori 1");
		kategoriService.simpan(kategori);
		assertEquals(kategoriCount + 1, kategoriRepository.count());
		
		tindakan = new Tindakan();
		tindakan.setKategori(kategori);
		tindakan.setKelas(Kelas.I);
		tindakan.setKeterangan("Keterangan");
		tindakan.setKode("Kode 1");
		tindakan.setNama("Nama 1");
		tindakan.setSatuan(SatuanTindakan.TINDAKAN);
		tindakan.setPenanggung(Penanggung.BPJS);
		tindakan.setTarif(500000l);
		tindakan = tindakanService.save(tindakan);
		assertEquals(count + 1, tindakanRepository.count());
	}
	
	@Test
	public void testSimpan() throws Exception {
		assertNotEquals(new Long(0), kategori.getId());
		
		this.mockMvc.perform(
				post("/tindakan")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"kategori\": {"
						+ "\"id\":\"" + kategori.getId() + "\","
						+ "\"nama\":\"" + kategori.getNama() + "\""
						+ "},"
						+ "\"kelas\":\"I\","
						+ "\"keterangan\":\"Keterangan\","
						+ "\"kode\":\"Kode 2\","
						+ "\"nama\":\"Nama 2\","
						+ "\"satuan\":\"TINDAKAN\","
						+ "\"penanggung\":\"UMUM\","
						+ "\"tarif\":\"1000000\""
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(kategoriCount + 1, kategoriRepository.count());
		assertEquals(count + 2, tindakanRepository.count());
	}

	@Test
	public void testDelete() throws Exception {
		this.mockMvc.perform(
				delete(String.format("/tindakan/%s", tindakan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetAll() throws Exception {
		this.mockMvc.perform(
				get("/tindakan")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testCariKode() throws Exception {
		this.mockMvc.perform(
				get(String.format("/tindakan/keyword/%s", tindakan.getKode()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testCariNama() throws Exception {
		this.mockMvc.perform(
				get(String.format("/tindakan/keyword/%s", tindakan.getNama()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

}
