package com.dbsys.rs.account.service;

import java.util.List;

import com.dbsys.rs.lib.entity.Unit;

/**
 * Kelas untuk mengelola data unit.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface UnitService {

	/**
	 * Menyimpan data unit.
	 * 
	 * @param unitEntity
	 * 
	 * @return unit yang sudah disimpan
	 */
	Unit save(Unit unit);

	/**
	 * Mengambil data uit berdasarkan id.
	 * 
	 * @param id
	 * 
	 * @return unit
	 */
	Unit get(Long id);

	/**
	 * Menghapus unit berdasarkan id
	 * 
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * Mengambil semua unit.
	 * 
	 * @return daftar unit
	 */
	List<Unit> getAll();

}
