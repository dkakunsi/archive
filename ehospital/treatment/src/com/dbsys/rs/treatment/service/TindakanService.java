package com.dbsys.rs.treatment.service;

import java.util.List;

import com.dbsys.rs.lib.Kelas;
import com.dbsys.rs.lib.entity.Tindakan;

/**
 * Interface untuk mengelola data tindakan.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface TindakanService {

	/**
	 * Menyimpan tindakan.
	 * 
	 * @param tindakanEntity
	 * 
	 * @return tindakan yang sudah tersimpan
	 */
	Tindakan save(Tindakan tindakan);

	/**
	 * Menghapus tindakan berdasarkan id.
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Mengambil semua tindakan.
	 * 
	 * @return daftar tindakan
	 */
	List<Tindakan> getAll();

	/**
	 * Mengambil semua tindakan berdasarkan keyword.
	 * 
	 * @param keyword
	 * 
	 * @return daftar tindakan
	 */
	List<Tindakan> get(String keyword);

	/**
	 * 
	 * @param nama
	 * @param kelas
	 * @return
	 */
	Tindakan get(String nama, Kelas kelas);

}
