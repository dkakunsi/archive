package com.unitedvision.sangihe.monev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unitedvision.sangihe.monev.entity.RekapKegiatan;

public interface RekapKegiatanRepository extends JpaRepository<RekapKegiatan, String> {

	@Query(nativeQuery = true,
		value = "SELECT uk.nama as unit_kerja, p.nama as program, k.nama as kegiatan, k.pagu_anggaran as pagu_anggaran"
				+ ", (SELECT SUM(a.realisasi) FROM anggaran a WHERE a.kegiatan = k.id) as realisasi_anggaran"
				+ ", (SELECT SUM(f.realisasi) FROM fisik f WHERE f.kegiatan = k.id) as realisasi_fisik "
				+ "FROM kegiatan k "
				+ "INNER JOIN program p ON k.program = p.id "
				+ "INNER JOIN unit_kerja uk ON p.unit_kerja = uk.id "
				+ "WHERE k.id = :id")
	RekapKegiatan rekapKegiatan(@Param("id") Long id);

	@Query(nativeQuery = true,
			value = "SELECT uk.nama as unit_kerja, p.nama as program, k.nama as kegiatan, k.pagu_anggaran as pagu_anggaran"
					+ ", (SELECT SUM(a.realisasi) FROM anggaran a WHERE a.kegiatan = k.id) as realisasi_anggaran"
					+ ", (SELECT SUM(f.realisasi) FROM fisik f WHERE f.kegiatan = k.id) as realisasi_fisik "
				+ "FROM kegiatan k "
				+ "INNER JOIN program p ON k.program = p.id "
				+ "INNER JOIN unit_kerja uk ON p.unit_kerja = uk.id "
				+ "WHERE p.tahun_awal >= :tahun AND p.tahun_akhir <= :tahun AND uk.singkatan = :kode "
				+ "ORDER BY unit_kerja, program, pagu_anggaran")
	List<RekapKegiatan> rekap(@Param("tahun") Long tahun, @Param("kode") String kode);

	@Query(nativeQuery = true,
			value = "SELECT uk.nama as unit_kerja, p.nama as program, k.nama as kegiatan, k.pagu_anggaran as pagu_anggaran"
				+ ", (SELECT SUM(a.realisasi) FROM anggaran a WHERE a.kegiatan = k.id) as realisasi_anggaran"
				+ ", (SELECT SUM(f.realisasi) FROM fisik f WHERE f.kegiatan = k.id) as realisasi_fisik "
				+ "FROM kegiatan k "
				+ "INNER JOIN program p ON k.program = p.id "
				+ "INNER JOIN unit_kerja uk ON p.unit_kerja = uk.id "
				+ "WHERE p.tahun_awal >= :tahun AND p.tahun_akhir <= :tahun AND p.id = :id "
				+ "ORDER BY unit_kerja, program, pagu_anggaran")
	List<RekapKegiatan> rekap(@Param("tahun") Long tahun, @Param("id") Long id);

	@Query(nativeQuery = true,
			value = "SELECT uk.nama as unit_kerja, p.nama as program, k.nama as kegiatan, k.pagu_anggaran as pagu_anggaran"
				+ ", (SELECT SUM(a.realisasi) FROM anggaran a WHERE a.kegiatan = k.id) as realisasi_anggaran"
				+ ", (SELECT SUM(f.realisasi) FROM fisik f WHERE f.kegiatan = k.id) as realisasi_fisik "
				+ "FROM kegiatan k "
				+ "INNER JOIN program p ON k.program = p.id "
				+ "INNER JOIN unit_kerja uk ON p.unit_kerja = uk.id "
				+ "WHERE p.tahun_awal >= :tahun AND p.tahun_akhir <= :tahun "
				+ "ORDER BY unit_kerja, program, pagu_anggaran")
	List<RekapKegiatan> rekap(@Param("tahun") Long tahun);

	@Query(nativeQuery = true,
			value = "SELECT uk.nama as unit_kerja"
				+ ", p.nama as sub_program"
				+ ", k.nama as kegiatan"
				+ ", k.pagu_anggaran as pagu_anggaran"
				+ ", (SELECT SUM(a.rencana) FROM anggaran a WHERE a.kegiatan = k.id AND a.tahun = :tahun AND a.bulan BETWEEN :awal AND :akhir) as rencana_anggaran"
				+ ", (SELECT SUM(a.realisasi) FROM anggaran a WHERE a.kegiatan = k.id AND a.tahun = :tahun AND a.bulan BETWEEN :awal AND :akhir) as realisasi_anggaran"
				+ ", (SELECT SUM(f.realisasi) FROM fisik f WHERE f.kegiatan = k.id AND f.tahun = :tahun AND f.bulan BETWEEN :awal AND :akhir) as realisasi_fisik "
				+ ", (SELECT pro.nama FROM program pro Where pro.id = p.parent) as program "
				+ "FROM kegiatan k "
				+ "INNER JOIN program p ON k.program = p.id "
				+ "INNER JOIN unit_kerja uk ON p.unit_kerja = uk.id "
				+ "WHERE p.tahun_awal >= :tahun AND p.tahun_akhir <= :tahun "
				+ "ORDER BY unit_kerja, program, pagu_anggaran")
	List<RekapKegiatan> rekap(@Param("tahun") Long tahun, @Param("awal") int awal, @Param("akhir") int akhir);

}
