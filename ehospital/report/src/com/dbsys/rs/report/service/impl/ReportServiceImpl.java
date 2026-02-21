package com.dbsys.rs.report.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.report.entity.RekapKembali;
import com.dbsys.rs.report.entity.RekapPelayanan;
import com.dbsys.rs.report.entity.RekapTagihanPelayanan;
import com.dbsys.rs.report.entity.RekapTagihanPemakaian;
import com.dbsys.rs.report.entity.RekapStokBarang;
import com.dbsys.rs.report.entity.RekapTagihan;
import com.dbsys.rs.report.entity.RekapUnit;
import com.dbsys.rs.report.repository.RekapKembaliRepository;
import com.dbsys.rs.report.repository.RekapPemakaianRepository;
import com.dbsys.rs.report.repository.RekapStokRepository;
import com.dbsys.rs.report.repository.RekapPelayananRepository;
import com.dbsys.rs.report.repository.RekapUnitRepository;
import com.dbsys.rs.report.service.ReportService;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

	@Autowired
	private RekapUnitRepository rekapUnitRepository;
	@Autowired
	private RekapStokRepository rekapStokRepository;
	@Autowired
	private RekapPelayananRepository rekapPelayananRepository;
	@Autowired
	private RekapPemakaianRepository rekapPemakaianRepository;
	@Autowired
	private RekapKembaliRepository rekapKembaliRepository;
	
	@Override
	public List<RekapUnit> rekapUnit(Date awal, Date akhir) {
		return rekapUnitRepository.rekap(awal, akhir);
	}

	@Override
	public List<RekapStokBarang> rekapStok(Date awal, Date akhir) {
		return rekapStokRepository.rekap(awal, akhir);
	}

	@Override
	public List<RekapTagihan> rekapTagihan(Long pasien) {
		List<RekapTagihanPelayanan> listPelayanan = rekapPelayananRepository.rekap(pasien);
		List<RekapTagihanPemakaian> listPemakaian = rekapPemakaianRepository.rekap(pasien);
		List<RekapKembali> listKembali = rekapKembaliRepository.rekap(pasien);

		for (RekapKembali kembali : listKembali) {
			for (RekapTagihanPemakaian pemakaian : listPemakaian) {
				if (kembali.getNama().equals(pemakaian.getNama())) {
					pemakaian.substract(kembali.getJumlah());
				}
			}
		}
		
		List<RekapTagihan> listTagihan = new ArrayList<>();
		listTagihan.addAll(listPelayanan);
		listTagihan.addAll(listPemakaian);
		
		return listTagihan;
	}

	@Override
	public List<RekapTagihan> rekapTagihanPemakaian(Date awal, Date akhir) {
		List<RekapTagihanPemakaian> listPemakaian = rekapPemakaianRepository.rekap(awal, akhir);
		List<RekapKembali> listKembali = rekapKembaliRepository.rekap(awal, akhir);

		for (RekapKembali kembali : listKembali) {
			for (RekapTagihanPemakaian pemakaian : listPemakaian) {
				if (kembali.getNama().equals(pemakaian.getNama())) {
					pemakaian.substract(kembali.getJumlah());
				}
			}
		}
		
		List<RekapTagihan> listTagihan = new ArrayList<>();
		listTagihan.addAll(listPemakaian);
		
		return listTagihan;
	}

	@Override
	public List<RekapPelayanan> rekapPelayanan(Date awal, Date akhir) {
		return rekapPelayananRepository.rekap(awal, akhir);
	}
}
