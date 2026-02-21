package com.unitedvision.sangihe.monev.service;

import java.time.Month;
import java.util.List;

import com.unitedvision.sangihe.monev.entity.Anggaran;
import com.unitedvision.sangihe.monev.exception.AnggaranException;
import com.unitedvision.sangihe.monev.exception.WrongYearException;

public interface AnggaranService {

	Anggaran simpan(Anggaran anggaran) throws AnggaranException, WrongYearException;

	Anggaran simpan(Anggaran anggaran, Long idKegiatan) throws AnggaranException, WrongYearException;
	
	Anggaran realisasi(Long id, Long jumlahRealisasi) throws AnggaranException, WrongYearException;
	
	void hapus(Long id);
	
	Anggaran get(Long id);
	
	Anggaran get(Long idKegiatan, Integer tahun, Month bulan);
	
	List<Anggaran> getByProgram(Long id);
	
	List<Anggaran> getByKegiatan(Long id);
}
