package com.unitedvision.sangihe.service.test;

import static org.junit.Assert.*;

import java.time.Month;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.monev.configuration.ApplicationConfig;
import com.unitedvision.sangihe.monev.entity.Anggaran;
import com.unitedvision.sangihe.monev.entity.Kegiatan;
import com.unitedvision.sangihe.monev.entity.Program;
import com.unitedvision.sangihe.monev.entity.UnitKerja;
import com.unitedvision.sangihe.monev.entity.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.monev.exception.AnggaranException;
import com.unitedvision.sangihe.monev.exception.WrongYearException;
import com.unitedvision.sangihe.monev.repository.AnggaranRepository;
import com.unitedvision.sangihe.monev.repository.KegiatanRepository;
import com.unitedvision.sangihe.monev.repository.UnitKerjaRepository;
import com.unitedvision.sangihe.monev.service.AnggaranService;
import com.unitedvision.sangihe.monev.service.KegiatanService;
import com.unitedvision.sangihe.monev.service.ProgramService;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class AnggaranServiceTest {
	
	@Autowired
	private KegiatanService kegiatanService;
	@Autowired
	private ProgramService programService; 
	@Autowired
	private UnitKerjaRepository unitKerjaRepository;
	@Autowired
	private AnggaranRepository anggaranRepository;
	@Autowired
	private AnggaranService anggaranService;
	@Autowired
	private KegiatanRepository kegiatanRepository;
	
	private UnitKerja unitKerja;
	private Program program;
	private Kegiatan kegiatan;
	private Anggaran anggaran;
	
	private Long id;

	@Before
	public void setup() throws AnggaranException, WrongYearException {
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
		
		assertEquals(1, anggaranRepository.count());
		
		id = anggaran.getId();
	}
	
	@Test
	public void test_simpan() throws AnggaranException, WrongYearException {
		anggaran = new Anggaran();
		anggaran.setTahun(2015);
		anggaran.setBulan(Month.FEBRUARY);
		anggaran.setRencana(10000000L);
		anggaranService.simpan(anggaran, kegiatan.getId());
	}
	
	@Test(expected = PersistenceException.class)
	public void test_simpan_duplicate() throws AnggaranException, WrongYearException {
		anggaran = new Anggaran(kegiatan);
		anggaran.setTahun(2015);
		anggaran.setBulan(Month.JANUARY);
		anggaran.setRencana(10000000L);
		anggaranService.simpan(anggaran);
	}
	
	@Test(expected = WrongYearException.class)
	public void test_simpan_wrong_year() throws AnggaranException, WrongYearException {
		anggaran = new Anggaran(kegiatan);
		anggaran.setTahun(2016);
		anggaran.setBulan(Month.FEBRUARY);
		anggaran.setRencana(10000000L);
		anggaranService.simpan(anggaran);
	}
	
	@Test(expected = AnggaranException.class)
	public void test_simpan_rencana_melebihi_pagu() throws AnggaranException, WrongYearException {
		anggaran = new Anggaran(kegiatan);
		anggaran.setTahun(2015);
		anggaran.setBulan(Month.FEBRUARY);
		anggaran.setRencana(150000000L);
		anggaranService.simpan(anggaran);
	}
	
	@Test(expected = AnggaranException.class)
	public void test_simpan_total_rencana_melebihi_pagu() throws AnggaranException, WrongYearException {
		anggaran = new Anggaran(kegiatan);
		anggaran.setTahun(2015);
		anggaran.setBulan(Month.FEBRUARY);
		anggaran.setRencana(10000000L);
		anggaranService.simpan(anggaran);

		anggaran = new Anggaran(kegiatan);
		anggaran.setTahun(2015);
		anggaran.setBulan(Month.MARCH);
		anggaran.setRencana(100000000L);
		anggaranService.simpan(anggaran);
	}
	
	@Test(expected = AnggaranException.class)
	public void test_simpan_realisasi_melebihi_pagu() throws AnggaranException, WrongYearException {
		anggaran = new Anggaran(kegiatan);
		anggaran.setTahun(2015);
		anggaran.setBulan(Month.FEBRUARY);
		anggaran.setRencana(1000000L);
		anggaran.setRealisasi(150000000L);
		anggaranService.simpan(anggaran);
	}
	
	@Test(expected = AnggaranException.class)
	public void test_simpan_total_realisasi_melebihi_pagu() throws AnggaranException, WrongYearException {
		anggaran = new Anggaran(kegiatan);
		anggaran.setTahun(2015);
		anggaran.setBulan(Month.FEBRUARY);
		anggaran.setRencana(1000000L);
		anggaran.setRealisasi(100000000L);
		anggaranService.simpan(anggaran);

		anggaran = new Anggaran(kegiatan);
		anggaran.setTahun(2015);
		anggaran.setBulan(Month.MARCH);
		anggaran.setRencana(1000000L);
		anggaran.setRealisasi(10000000L);
		anggaranService.simpan(anggaran);
	}
	
	@Test
	public void test_realisasi() throws AnggaranException, WrongYearException {
		Anggaran anggaran = anggaranService.realisasi(id, 8000000L);
		
		assertNotNull(anggaran);
		assertEquals(this.anggaran.getId(), anggaran.getId());
		assertEquals(new Long(8000000), anggaran.getRealisasi());
		
		assertEquals(1, anggaranRepository.count());
	}
	
	@Test(expected = AnggaranException.class)
	public void test_realisasi_melebihi_pagu() throws AnggaranException, WrongYearException {
		anggaranService.realisasi(id, 150000000L);
	}
	
	@Test(expected = AnggaranException.class)
	public void test_total_realisasi_melebihi_pagu() throws AnggaranException, WrongYearException {
		anggaranService.realisasi(id, 10000000L);

		anggaran = new Anggaran(kegiatan);
		anggaran.setTahun(2015);
		anggaran.setBulan(Month.FEBRUARY);
		anggaran.setRencana(1000000L);
		anggaranService.simpan(anggaran);

		anggaranService.realisasi(anggaran.getId(), 100000000L);
	}
	
	@Test
	public void test_hapus() {
		anggaranService.hapus(id);
		
		assertEquals(0, anggaranRepository.count());
	}
	
	@Test
	public void test_get() {
		Anggaran anggaran = anggaranService.get(id);

		assertNotNull(anggaran);
		assertEquals(this.anggaran, anggaran);
	}
	
	@Test
	public void test_get_by_program() {
		List<Anggaran> list = anggaranService.getByProgram(program.getId());
		
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void test_get_by_kegiatan() {
		List<Anggaran> list = anggaranService.getByKegiatan(kegiatan.getId());
		
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void test_get_kegiatan_with_anggaran() {
		System.out.println("BEGIN");
		Kegiatan kegiatan = kegiatanService.get(this.kegiatan.getId());
		System.out.println("DONE");
		
		assertNotNull(kegiatan);
		assertEquals(this.kegiatan, kegiatan);
//		assertEquals(1, kegiatan.getDaftarAnggaran().size());
	}
}
