package com.dbsys.rs.report.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.report.entity.RekapUnit;

public interface RekapUnitRepository extends JpaRepository<RekapUnit, Long> {

	@Query(nativeQuery = true, 
			value = "SELECT DISTINCT(u.nama), "
				+ "(SELECT COUNT(*) FROM pelayanan in_p WHERE in_p.unit = u.id AND in_p.tanggal BETWEEN :awal AND :akhir) as 'jumlah', "
				+ "(SELECT SUM(in_p.jumlah * t.tarif + in_p.biaya_tambahan) "
					+ "FROM pelayanan in_p INNER JOIN tindakan t ON in_p.tindakan = t.id WHERE in_p.unit = u.id AND in_p.tanggal BETWEEN :awal AND :akhir) as 'total' "
				+ "FROM unit u INNER JOIN pelayanan p ON p.unit = u.id WHERE p.tanggal BETWEEN :awal AND :akhir")
	List<RekapUnit> rekap(@Param("awal") Date awal, @Param("akhir") Date akhir);

}
