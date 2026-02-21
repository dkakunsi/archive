package com.dbsys.rs.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbsys.rs.lib.entity.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

	/**
	 * Mengambil operator berdasarkan username.
	 * 
	 * @param username
	 * 
	 * @return operator
	 */
	Operator findByUsername(String username);

}
