package com.unitedvision.sangihe.monev.service;

import java.util.List;

import com.unitedvision.sangihe.monev.entity.Program;
import com.unitedvision.sangihe.monev.entity.RekapProgram;

public interface ProgramService {

	Program simpan(Program program);

	Program simpan(Program program, Long idUnitKerja);
	
	void hapus(Long id);
	
	Program get(Long id);
	
	List<Program> get();
	
	List<Program> getByUnitKerja(Long id);
	
	List<Program> cari(String keyword);

	RekapProgram rekapProgram(Long id);

	List<RekapProgram> rekap(Long tahun);

	List<RekapProgram> rekap(Long tahun, String kode);
}
