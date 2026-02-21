package com.unitedvision.sangihe.monev.repository;

import java.time.Month;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.unitedvision.sangihe.monev.entity.Anggaran;
import com.unitedvision.sangihe.monev.entity.Kegiatan;

public interface AnggaranRepository extends JpaRepository<Anggaran, Long> {

	@Modifying
	@Query("UPDATE Anggaran a SET a.realisasi = ?2 WHERE a.id = ?1")
	void updateRealisasi(Long id, Long jumlahRealisasi);

	Anggaran findByKegiatan_IdAndTahunAndBulan(Long idKegiatan, Integer tahun, Month bulan);

	List<Anggaran> findByKegiatan_Program_Id(Long id);

	List<Anggaran> findByKegiatan_Id(Long id);

	List<Anggaran> findByKegiatan(Kegiatan kegiatan);

}
