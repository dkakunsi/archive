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
import com.unitedvision.sangihe.monev.entity.Program;
import com.unitedvision.sangihe.monev.entity.RekapProgram;
import com.unitedvision.sangihe.monev.entity.UnitKerja;
import com.unitedvision.sangihe.monev.entity.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.monev.repository.ProgramRepository;
import com.unitedvision.sangihe.monev.repository.UnitKerjaRepository;
import com.unitedvision.sangihe.monev.service.ProgramService;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class ProgramServiceTest {

	@Autowired
	private ProgramService programService; 
	@Autowired
	private UnitKerjaRepository unitKerjaRepository;
	@Autowired
	private ProgramRepository programRepository;
	
	private UnitKerja unitKerja;
	private Program program;
	
	private Long id;
	
	@Before
	public void setUp() throws Exception {
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
		
		// assertEquals(1, programRepository.count());
		
		id = program.getId();
	}
	
	@Test
	public void test_tambah() {
		Program program = new Program();
		program.setNama("Pembangunan Object Wisata 2");
		program.setTahunAwal(2015);
		program.setTahunAkhir(2015);
		programService.simpan(program, unitKerja.getId());
		
		assertEquals(2, programRepository.count());
	}

	@Test(expected = PersistenceException.class)
	public void test_simpan_duplicate() {
		Program program = new Program(unitKerja);
		program.setNama("Pembangunan Object Wisata");
		program.setTahunAwal(2015);
		program.setTahunAkhir(2015);
		programService.simpan(program);
	}

	@Test
	public void test_hapus() {
		programService.hapus(id);
		
		assertEquals(0, programRepository.count());
	}
	
	@Test
	public void test_get() {
		Program program = programService.get(id);

		assertNotNull(program);
		assertEquals(this.program, program);
	}
	
	@Test
	public void test_get_all() {
		List<Program> list = programService.get();
		
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void test_get_by_unit_kerja() {
		List<Program> list = programService.getByUnitKerja(unitKerja.getId());
		
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void test_cari() {
		List<Program> list = programService.cari("Pembangunan");
		
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}

	@Test
	public void test_rekap_single() {
		RekapProgram rekap = programService.rekapProgram(1L);
		
		assertNotNull(rekap);
		assertEquals("Pengelolaan Data Elektronik", rekap.getNamaUnitKerja());
		assertEquals("Pengadaan Jaringan Internet", rekap.getNamaProgram());
		assertEquals(new Long(120000000), rekap.getPaguAnggaran());
		assertEquals(new Long(8000000), rekap.getRealisasiAnggaran());
		assertEquals(new Integer(10), rekap.getRealisasiFisik());
	}
	
	@Test
	public void test_rekap_by_tahun() {
		List<RekapProgram> rekap = programService.rekap(2015L);
		
		assertNotNull(rekap);
		assertNotEquals(0, rekap.size());
		
		System.out.println("HERE:");
		for (RekapProgram rp : rekap)
			System.out.println(rp);
	}
	
	@Test
	public void test_rekap_by_tahun_and_unit_kerja() {
		List<RekapProgram> rekap = programService.rekap(2015L, "BPDE");
		
		assertNotNull(rekap);
		assertNotEquals(0, rekap.size());
		
		System.out.println("HERE:");
		for (RekapProgram rp : rekap)
			System.out.println(rp);
	}
}
