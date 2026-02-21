package com.unitedvision.sangihe.monev.repository;

import java.time.Month;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.monev.entity.Fisik;
import com.unitedvision.sangihe.monev.entity.Kegiatan;

public interface FisikRepository extends JpaRepository<Fisik, Long> {

	List<Fisik> findByKegiatan_Id(Long id);

	List<Fisik> findByKegiatan_Program_Id(Long id);

	Fisik findByKegiatan_IdAndTahunAndBulan(Long id, Integer tahun, Month bulan);

	List<Fisik> findByKegiatan(Kegiatan kegiatan);

}
