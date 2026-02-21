package com.dbsys.rs.hr.service;

import java.util.List;

import com.dbsys.rs.lib.entity.Apoteker;
import com.dbsys.rs.lib.entity.Dokter;
import com.dbsys.rs.lib.entity.Pegawai;
import com.dbsys.rs.lib.entity.Pekerja;
import com.dbsys.rs.lib.entity.Perawat;

/**
 * Interface untuk mengelola data pegawai.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface PegawaiService {

	/**
	 * Menyimpa data pegawai
	 * 
	 * @param pegawaiEntity
	 * 
	 * @return egawai yang sudah tersimpan
	 */
	Pegawai save(Pegawai pegawai);

	/**
	 * Mengambil semua dokter.
	 * 
	 * @return daftar dokter
	 */
	List<Dokter> getDokter();

	/**
	 * Mengambil semua dokter sesuai keyword.
	 * 
	 * @param keyword
	 * 
	 * @return daftar dokter
	 */
	List<Dokter> getDokter(String keyword);

	/**
	 * Mengambil semua perawat
	 * 
	 * @return daftar perawat
	 */
	List<Perawat> getPerawat();

	/**
	 * mengambil semua perawat sesuai keyword.
	 * 
	 * @param keyword
	 * 
	 * @return daftar perawat
	 */
	List<Perawat> getPerawat(String keyword);
	
	/**
	 * Mengambil semua apoteker.
	 * 
	 * @return daftar apoteker
	 */
	List<Apoteker> getApoteker();


	/**
	 * Mengambil semua apoteker berdasarkan keyword.
	 * 
	 * @param keyword
	 * 
	 * @return daftar apoteker
	 */
	List<Apoteker> getApoteker(String keyword);

	/**
	 * Mengambil semua pekerja
	 * 
	 * @return daftar pekerja
	 */
	List<Pekerja> getPekerja();

	/**
	 * Mengambil semua pekerja sesuai keyword.
	 * 
	 * @param keyword
	 * 
	 * @return daftar pekerja
	 */
	List<Pekerja> getPekerja(String keyword);

	/**
	 * Mengambil semua data pegawai.
	 * 
	 * @return daftar pegawai
	 */
	List<Pegawai> getPegawai();

	/**
	 * Mengambil semua pekerja sesuai keyword.
	 * 
	 * @param keyword
	 * 
	 * @return daftar pegawai
	 */
	List<Pegawai> getPegawai(String keyword);

	void hapus(Long id);

}
