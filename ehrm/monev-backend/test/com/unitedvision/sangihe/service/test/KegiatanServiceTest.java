package com.unitedvision.sangihe.service.test;

import static org.junit.Assert.*;

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
import com.unitedvision.sangihe.monev.entity.Kegiatan;
import com.unitedvision.sangihe.monev.entity.Program;
import com.unitedvision.sangihe.monev.entity.UnitKerja;
import com.unitedvision.sangihe.monev.entity.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.monev.repository.KegiatanRepository;
import com.unitedvision.sangihe.monev.repository.UnitKerjaRepository;
import com.unitedvision.sangihe.monev.service.KegiatanService;
import com.unitedvision.sangihe.monev.service.ProgramService;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class KegiatanServiceTest {
	
	@Autowired
	private KegiatanService kegiatanService;
	@Autowired
	private ProgramService programService; 
	@Autowired
	private UnitKerjaRepository unitKerjaRepository;
	@Autowired
	private KegiatanRepository kegiatanRepository;
	
	private UnitKerja unitKerja;
	private Program program;
	private Kegiatan kegiatan;
	
	private Long id;

	@Before
	public void setup() {
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
		
		assertEquals(1, kegiatanRepository.count());
		
		id = kegiatan.getId();
	}
	
	@Test
	public void test_simpan() {
		kegiatan = new Kegiatan();
		kegiatan.setNama("Pengembangan Pantai Pananuareng 2");
		kegiatan.setPaguAnggaran(100000000L);
		kegiatanService.simpan(kegiatan, program.getId());
		
		assertEquals(2, kegiatanRepository.count());
	}

	@Test(expected = PersistenceException.class)
	public void test_simpan_duplicate() {
		kegiatan = new Kegiatan(program);
		kegiatan.setNama("Pengembangan Pantai Pananuareng");
		kegiatan.setPaguAnggaran(100000000L);
		kegiatanService.simpan(kegiatan);
	}
	
	@Test
	public void test_hapus() {
		kegiatanService.hapus(id);
		
		assertEquals(0, kegiatanRepository.count());
	}
	
	@Test
	public void test_get() {
		System.out.println("BEGIN");
		Kegiatan kegiatan = kegiatanService.get(id);
		System.out.println("DONE");
		
		assertNotNull(kegiatan);
		assertEquals(this.kegiatan, kegiatan);
	}
	
	@Test
	public void test_get_all() {
		List<Kegiatan> list = kegiatanService.get();
		
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void test_get_by_unit_kerja() {
		List<Kegiatan> list = kegiatanService.getByUnitKerja(unitKerja.getId());
		
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void test_get_by_program() {
		List<Kegiatan> list = kegiatanService.getByProgram(program.getId());
		
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void test_cari() {
		List<Kegiatan> list = kegiatanService.cari("Pananuareng");
		
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}
}
