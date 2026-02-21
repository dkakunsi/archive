package com.unitedvision.sangihe.monev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unitedvision.sangihe.monev.entity.RekapProgram;

public interface RekapProgramRepository extends JpaRepository<RekapProgram, String> {

	@Query(nativeQuery = true,
		value = "SELECT uk.nama AS unit_kerja, p.nama AS program "
				+ ", (SELECT SUM(k.pagu_anggaran) FROM kegiatan k WHERE k.program = p.id) AS pagu_anggaran"
				+ ", (SELECT SUM(a.realisasi) FROM anggaran a INNER JOIN kegiatan k ON a.kegiatan = k.id WHERE k.program = p.id) AS realisasi_anggaran"
				+ ", (SELECT SUM(f.realisasi) FROM fisik f INNER JOIN kegiatan k ON f.kegiatan = k.id WHERE k.program = p.id) AS realisasi_fisik "
				+ "FROM program p INNER JOIN unit_kerja uk ON p.unit_kerja = uk.id "
				+ "WHERE p.id = :id ORDER BY pagu_anggaran DESC")
	RekapProgram rekapSingle(@Param("id") long id);
	
	@Query(nativeQuery = true,
			value = "SELECT uk.nama AS unit_kerja, p.nama AS program "
					+ ", (SELECT SUM(k.pagu_anggaran) FROM kegiatan k WHERE k.program = p.id) AS pagu_anggaran"
					+ ", (SELECT SUM(a.realisasi) FROM anggaran a INNER JOIN kegiatan k ON a.kegiatan = k.id WHERE k.program = p.id) AS realisasi_anggaran"
					+ ", (SELECT SUM(f.realisasi) FROM fisik f INNER JOIN kegiatan k ON f.kegiatan = k.id WHERE k.program = p.id) AS realisasi_fisik "
					+ "FROM program p INNER JOIN unit_kerja uk ON p.unit_kerja = uk.id "
					+ "WHERE p.tahun_awal >= :tahun AND p.tahun_akhir <= :tahun ORDER BY pagu_anggaran DESC")
	List<RekapProgram> rekap(@Param("tahun") long tahun);
	
	@Query(nativeQuery = true,
			value = "SELECT uk.nama AS unit_kerja, p.nama AS program "
					+ ", (SELECT SUM(k.pagu_anggaran) FROM kegiatan k WHERE k.program = p.id) AS pagu_anggaran"
					+ ", (SELECT SUM(a.realisasi) FROM anggaran a INNER JOIN kegiatan k ON a.kegiatan = k.id WHERE k.program = p.id) AS realisasi_anggaran"
					+ ", (SELECT SUM(f.realisasi) FROM fisik f INNER JOIN kegiatan k ON f.kegiatan = k.id WHERE k.program = p.id) AS realisasi_fisik "
					+ "FROM program p INNER JOIN unit_kerja uk ON p.unit_kerja = uk.id "
					+ "WHERE p.tahun_awal >= :tahun AND p.tahun_akhir <= :tahun AND uk.singkatan = :kode "
					+ "ORDER BY pagu_anggaran DESC")
	List<RekapProgram> rekap(@Param("tahun") long tahun, @Param("kode") String kode);
	
}
