package com.unitedvision.sangihe.monev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.monev.entity.Program;

public interface ProgramRepository extends JpaRepository<Program, Long> {

	List<Program> findByUnitKerja_Id(Long id);

	List<Program> findByNamaContaining(String keyword);

}
