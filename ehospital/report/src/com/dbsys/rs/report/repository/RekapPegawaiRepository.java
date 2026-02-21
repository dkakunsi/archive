package com.dbsys.rs.report.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.report.entity.RekapPegawai;

public interface RekapPegawaiRepository extends JpaRepository<RekapPegawai, Long> {

	@Query(nativeQuery = true,
		value = "SELECT "
			+ "pl.id AS id, "
			+ "pd.nama AS nama, "
			+ "t.nama AS tindakan, "
			+ "t.tarif AS tarif, "
			+ "SUM(pl.jumlah) AS jumlah "
			+ "FROM pelayanan pl "
			+ "INNER JOIN pegawai p ON pl.pelaksana = p.id "
			+ "INNER JOIN penduduk pd ON p.penduduk = pd.id "
			+ "INNER JOIN tindakan t ON pl.tindakan = t.id "
			+ "WHERE pl.tanggal BETWEEN :awal AND :akhir AND p.tipe = :cls "
			+ "GROUP BY pl.pelaksana, pl.tindakan")
	List<RekapPegawai> rekap(@Param("awal") Date awal, @Param("akhir") Date akhir, @Param("cls") String cls);

}
