package com.dbsys.rs.report.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.report.entity.RekapTagihanPemakaian;

public interface RekapPemakaianRepository extends JpaRepository<RekapTagihanPemakaian, Long> {

	@Query(nativeQuery = true,
		value = "SELECT p.id as id, p.pasien as pasien, b.nama as nama, b.penanggung as penanggung, "
			+ "b.harga as tarif, sum(p.jumlah) as jumlah, sum(p.biaya_tambahan) as tambahan "
			+ "from pemakaian p "
			+ "inner join barang b on b.id = p.barang "
			+ "where p.pasien = :pasien "
			+ "group by p.barang")
	List<RekapTagihanPemakaian> rekap(@Param("pasien") Long pasien);

	@Query(nativeQuery = true,
			value = "SELECT p.id as id, p.pasien as pasien, b.nama as nama, b.penanggung as penanggung, "
				+ "b.harga as tarif, sum(p.jumlah) as jumlah, sum(p.biaya_tambahan) as tambahan "
				+ "from pemakaian p "
				+ "inner join barang b on b.id = p.barang "
				+ "where p.tanggal BETWEEN :awal AND :akhir "
				+ "group by p.barang")
	List<RekapTagihanPemakaian> rekap(@Param("awal") Date awal, @Param("akhir") Date akhir);
	
}
