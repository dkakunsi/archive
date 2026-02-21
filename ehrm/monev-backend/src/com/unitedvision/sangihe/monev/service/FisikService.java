package com.unitedvision.sangihe.monev.service;

import java.time.Month;
import java.util.List;

import com.unitedvision.sangihe.monev.entity.Fisik;
import com.unitedvision.sangihe.monev.exception.FisikException;
import com.unitedvision.sangihe.monev.exception.WrongYearException;

public interface FisikService {
	
	Fisik simpan(Fisik fisik) throws FisikException, WrongYearException;

	Fisik simpan(Fisik fisik, Long idKegiatan) throws FisikException, WrongYearException;
	
	void hapus(Long id);
	
	Fisik get(Long id);

	Fisik get(Long id, Integer tahun, Month bulan);
	
	List<Fisik> getByKegiatan(Long id);
	
	List<Fisik> getByProgram(Long id);

}
