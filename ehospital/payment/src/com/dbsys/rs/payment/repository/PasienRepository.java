package com.dbsys.rs.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.lib.entity.Pasien;

public interface PasienRepository extends JpaRepository<Pasien, Long> {

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Pasien p SET p.cicilan = :cicilan WHERE p.id = :id")
	void updateCicilan(@Param("id") Long id, @Param("cicilan") Long cicilan);

}
