package com.unitedvision.sangihe.monev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unitedvision.sangihe.monev.entity.Pegawai;
import com.unitedvision.sangihe.monev.entity.UnitKerja;

public interface PegawaiRepository extends JpaRepository<Pegawai, Long> {

	Pegawai findByNip(String nip);

	@Query("FROM Pegawai p WHERE p.unitKerja = ?1 OR p.unitKerja.parent = ?1")
	List<Pegawai> findByUnitKerja(UnitKerja unitKerja);
	List<Pegawai> findByUnitKerja_Id(Long idUnitKerja);
	List<Pegawai> findByUnitKerjaIn(List<UnitKerja> daftarSubUnitKerja);

	@Query("FROM Pegawai pg WHERE pg.nip LIKE CONCAT('%', :keyword, '%') OR pg.penduduk.nama LIKE CONCAT('%', :keyword, '%')")
	List<Pegawai> findByNipContainingOrPenduduk_NamaContaining(@Param("keyword") String keyword);

	List<Pegawai> findByNipIn(List<String> daftarPegawai);

	@Query("FROM Pegawai pg WHERE pg.nip = ?1 OR pg.penduduk.nama = ?1")
	Pegawai findByNipOrPenduduk_Nama(String keyword);

}
