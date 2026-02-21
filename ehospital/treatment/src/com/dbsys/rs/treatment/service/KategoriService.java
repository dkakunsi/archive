package com.dbsys.rs.treatment.service;

import java.util.List;

import com.dbsys.rs.lib.entity.KategoriTindakan;

/**
 * Interface untuk mengelola data kategori.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface KategoriService {

	/**
	 * Simpan kategori.
	 * 
	 * @param kategori
	 * 
	 * @return kategori
	 */
	KategoriTindakan simpan(KategoriTindakan kategori);

	/**
	 * Mengambil semua kategori.
	 * 
	 * @return daftar kategori
	 */
	List<KategoriTindakan> getAll();

	/**
	 * Mengambil kategori berdasarkan id.
	 * 
	 * @param id
	 * 
	 * @return kategori
	 */
	KategoriTindakan getById(Long id);

}
