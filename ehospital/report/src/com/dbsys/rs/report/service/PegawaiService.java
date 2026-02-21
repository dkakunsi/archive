package com.dbsys.rs.report.service;

import java.sql.Date;
import java.util.List;

import com.dbsys.rs.report.entity.RekapPegawai;

public interface PegawaiService {

	List<RekapPegawai> rekapDokter(Date awal, Date akhir);

	List<RekapPegawai> rekapPerawat(Date awal, Date akhir);

}
