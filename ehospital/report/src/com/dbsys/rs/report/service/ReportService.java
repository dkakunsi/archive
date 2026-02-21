package com.dbsys.rs.report.service;

import java.sql.Date;
import java.util.List;

import com.dbsys.rs.report.entity.RekapPelayanan;
import com.dbsys.rs.report.entity.RekapStokBarang;
import com.dbsys.rs.report.entity.RekapTagihan;
import com.dbsys.rs.report.entity.RekapUnit;

public interface ReportService {

	List<RekapUnit> rekapUnit(Date awal, Date akhir);

	List<RekapStokBarang> rekapStok(Date awal, Date akhir);

	List<RekapTagihan> rekapTagihan(Long pasien);

	List<RekapTagihan> rekapTagihanPemakaian(Date awal, Date akhir);

	List<RekapPelayanan> rekapPelayanan(Date awal, Date akhir);
	
}
