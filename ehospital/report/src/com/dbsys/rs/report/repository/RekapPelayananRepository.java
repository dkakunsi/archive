package com.dbsys.rs.report.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.report.entity.RekapPelayanan;
import com.dbsys.rs.report.entity.RekapTagihanPelayanan;

public interface RekapPelayananRepository extends JpaRepository<RekapTagihanPelayanan, Long> {

	@Query(nativeQuery = true,
		value = "SELECT "
			+ "p.id as id, "
			+ "p.pasien as pasien, "
			+ "u.nama as unit, "
			+ "u.tipe as tipe_unit, "
			+ "t.nama as nama, "
			+ "t.penanggung as penanggung, "
			+ "sum(p.jumlah) as jumlah, "
			+ "t.tarif as tarif, "
			+ "sum(p.biaya_tambahan) as tambahan "
			+ "from pelayanan p "
			+ "inner join tindakan t on t.id = p.tindakan "
			+ "inner join unit u on u.id = p.unit "
			+ "where p.pasien = :pasien "
			+ "group by p.tindakan, p.unit")
	List<RekapTagihanPelayanan> rekap(@Param("pasien") Long pasien);
	
	@Query(nativeQuery = true,
		value = "SELECT "
			+ "p.id AS id, "
			+ "u.nama AS unit, "
			+ "t.nama AS tindakan, "
			+ "t.tarif AS tarif,"
			+ "COUNT(p.jumlah) AS jumlah, "
			+ "SUM(p.biaya_tambahan) AS tambahan "
			+ "FROM pelayanan p "
			+ "INNER JOIN unit u ON p.unit = u.id "
			+ "INNER JOIN tindakan t ON p.tindakan = t.id "
			+ "WHERE p.tanggal BETWEEN :awal AND :akhir "
			+ "GROUP BY unit, tindakan")
	List<RekapPelayanan> rekap(@Param("awal") Date awal, @Param("akhir") Date akhir);
	
}
