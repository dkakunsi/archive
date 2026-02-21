package com.dbsys.rs.account.service;

import java.util.List;

import com.dbsys.rs.lib.entity.Operator;

/**
 * Service untuk mengelola data operator.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface OperatorService {

	/**
	 * Menyimpan operator.
	 * 
	 * @param operatorEntity
	 * 
	 * @return operator yang berhasil disimpan
	 */
	Operator save(Operator operator);

	/**
	 * Mengambil data operator berdasarkan username.
	 * 
	 * @param username
	 * 
	 * @return operator
	 */
	Operator get(String username);

	/**
	 * Menghapus data operator berdasarkan id.
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Mengambil semua operator.
	 * 
	 * @return daftar operator
	 */
	List<Operator> getAll();

}
