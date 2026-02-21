package com.dbsys.rs.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.lib.entity.Token;
import com.dbsys.rs.lib.entity.Token.StatusToken;

public interface TokenRepository extends JpaRepository<Token, String> {

	/**
	 * Kunci token.
	 * 
	 * @param tokenString
	 * @param lock
	 */
	@Modifying
	@Query("UPDATE Token token SET token.status = :status WHERE token.kode = :kode")
	void updateStatus(@Param("kode") String kode, @Param("status") StatusToken status);

}
