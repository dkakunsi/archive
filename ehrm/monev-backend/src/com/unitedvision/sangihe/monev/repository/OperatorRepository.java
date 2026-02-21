package com.unitedvision.sangihe.monev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.monev.entity.Aplikasi;
import com.unitedvision.sangihe.monev.entity.Operator;
import com.unitedvision.sangihe.monev.entity.Pegawai;
import com.unitedvision.sangihe.monev.entity.Operator.Role;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

	List<Operator> findByPegawai(Pegawai pegawai);

	List<Operator> findByPegawai_Nip(String nip);

	List<Operator> findByAplikasiAndRole(Aplikasi aplikasi, Operator.Role role);

	List<Operator> findByAplikasi_KodeAndRole(String kode, Role operator);

}
