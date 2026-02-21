package com.unitedvision.sangihe.monev.service;

import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.monev.entity.Fisik;
import com.unitedvision.sangihe.monev.entity.Kegiatan;
import com.unitedvision.sangihe.monev.exception.FisikException;
import com.unitedvision.sangihe.monev.exception.WrongYearException;
import com.unitedvision.sangihe.monev.repository.FisikRepository;
import com.unitedvision.sangihe.monev.repository.KegiatanRepository;

@Service
@Transactional(readOnly = true)
public class FisikServiceImpl implements FisikService {

	@Autowired
	private FisikRepository fisikRepository;
	@Autowired
	private KegiatanRepository kegiatanRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Fisik simpan(Fisik fisik) throws FisikException, WrongYearException {
		fisik.validate();
		
		return fisikRepository.save(fisik);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Fisik simpan(Fisik fisik, Long idKegiatan) throws FisikException, WrongYearException {
		Kegiatan kegiatan = kegiatanRepository.findOne(idKegiatan);
		fisik.setKegiatan(kegiatan);

		return simpan(fisik);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Long id) {
		fisikRepository.delete(id);
	}

	@Override
	public Fisik get(Long id) {
		return fisikRepository.findOne(id);
	}

	@Override
	public Fisik get(Long id, Integer tahun, Month bulan) {
		return fisikRepository.findByKegiatan_IdAndTahunAndBulan(id, tahun, bulan);
	}
	
	@Override
	public List<Fisik> getByKegiatan(Long id) {
		return fisikRepository.findByKegiatan_Id(id);
	}

	@Override
	public List<Fisik> getByProgram(Long id) {
		return fisikRepository.findByKegiatan_Program_Id(id);
	}

}
