package com.dbsys.rs.report.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.report.entity.RekapStokBarang;

public interface RekapStokRepository extends JpaRepository<RekapStokBarang, Long> {

	@Query(nativeQuery = true, 
			value = "SELECT DISTINCT(b.nama), "
				+ "(SELECT SUM(in_s.jumlah) FROM stok in_s WHERE in_s.barang = b.id AND in_s.tipe = 'EKSTERNAL' AND in_s.jenis = '0' AND in_s.tanggal BETWEEN :awal AND :akhir) as 'jumlah_masuk', "
				+ "(SELECT SUM(in_s.jumlah) FROM stok in_s WHERE in_s.barang = b.id AND in_s.tipe = 'EKSTERNAL' AND in_s.jenis = '1' AND in_s.tanggal BETWEEN :awal AND :akhir) as 'jumlah_keluar', "
				+ "(SELECT SUM(in_s.jumlah) FROM stok in_s WHERE in_s.barang = b.id AND in_s.tipe = 'KEMBALI' AND in_s.tanggal BETWEEN :awal AND :akhir) as 'jumlah_kembali', "
				+ "(SELECT SUM(in_s.jumlah) FROM stok in_s WHERE in_s.barang = b.id AND in_s.tipe = 'INTERNAL' AND in_s.tanggal BETWEEN :awal AND :akhir) as 'jumlah_supply', "
				+ "(SELECT SUM(p.jumlah) FROM pemakaian p WHERE p.barang = b.id AND p.tanggal BETWEEN :awal AND :akhir) as 'jumlah_resep' "
				+ "FROM barang b INNER JOIN stok s ON s.barang = b.id")
	List<RekapStokBarang> rekap(@Param("awal") Date awal, @Param("akhir") Date akhir);

}
