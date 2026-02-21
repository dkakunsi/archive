package com.dbsys.rs.hr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.lib.entity.Apoteker;
import com.dbsys.rs.lib.entity.Dokter;
import com.dbsys.rs.lib.entity.Pegawai;
import com.dbsys.rs.lib.entity.Pekerja;
import com.dbsys.rs.lib.entity.Perawat;

public interface PegawaiRepository extends JpaRepository<Pegawai, Long> {

	/**
	 * Mengambil semua dokter.
	 * 
	 * @return daftar dokter
	 */
	@Query("FROM Dokter dokter")
	List<Dokter> findAllDokter();

	@Query("FROM Dokter d WHERE d.nip LIKE %:keyword% OR d.penduduk.nama LIKE %:keyword%")
	List<Dokter> findDokter(@Param("keyword") String keyword);

	/**
	 * Mengambil semua perawat.
	 * 
	 * @return daftar perawat
	 */
	@Query("FROM Perawat perawat")
	List<Perawat> findAllPerawat();

	@Query("FROM Perawat p WHERE p.nip LIKE %:keyword% OR p.penduduk.nama LIKE %:keyword%")
	List<Perawat> findPerawat(@Param("keyword") String keyword);

	/**
	 * Mengambil semua apoteker.
	 * 
	 * @return daftar apoteker
	 */
	@Query("FROM Apoteker apoteker")
	List<Apoteker> findAllApoteker();

	@Query("FROM Apoteker a WHERE a.nip LIKE %:keyword% OR a.penduduk.nama LIKE %:keyword%")
	List<Apoteker> findApoteker(@Param("keyword") String keyword);

	/**
	 * Mengambil semua pekerja.
	 * 
	 * @return daftar pekerja
	 */
	@Query("FROM Pekerja pekerja")
	List<Pekerja> findAllPekerja();

	@Query("FROM Pekerja p WHERE p.nip LIKE %:keyword% OR p.penduduk.nama LIKE %:keyword%")
	List<Pekerja> findPekerja(@Param("keyword") String keyword);

	List<Pegawai> findByNipContainingOrPenduduk_NamaContaining(String nip, String nama);

}
