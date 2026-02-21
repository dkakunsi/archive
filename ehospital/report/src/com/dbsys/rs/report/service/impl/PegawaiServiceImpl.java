package com.dbsys.rs.report.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.report.entity.RekapPegawai;
import com.dbsys.rs.report.repository.RekapPegawaiRepository;
import com.dbsys.rs.report.service.PegawaiService;

@Service
@Transactional(readOnly = true)
public class PegawaiServiceImpl implements PegawaiService {

	@Autowired
	private RekapPegawaiRepository rekapPegawaiRepository;
	
	@Override
	public List<RekapPegawai> rekapDokter(Date awal, Date akhir) {
		return rekapPegawaiRepository.rekap(awal, akhir, "DOKTER");
	}

	@Override
	public List<RekapPegawai> rekapPerawat(Date awal, Date akhir) {
		return rekapPegawaiRepository.rekap(awal, akhir, "PERAWAT");
	}

}
