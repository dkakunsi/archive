package com.unitedvision.sangihe.monev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.monev.entity.Program;
import com.unitedvision.sangihe.monev.entity.RekapProgram;
import com.unitedvision.sangihe.monev.entity.UnitKerja;
import com.unitedvision.sangihe.monev.repository.ProgramRepository;
import com.unitedvision.sangihe.monev.repository.RekapProgramRepository;
import com.unitedvision.sangihe.monev.repository.UnitKerjaRepository;

@Service
@Transactional(readOnly = true)
public class ProgramServiceImpl implements ProgramService {

	@Autowired
	private ProgramRepository programRepository;
	@Autowired
	private UnitKerjaRepository unitKerjaRepository;
	@Autowired
	private RekapProgramRepository rekapProgramRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Program simpan(Program program) {
		return programRepository.save(program);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Program simpan(Program program, Long idUnitKerja) {
		UnitKerja unitKerja = unitKerjaRepository.findOne(idUnitKerja);
		program.setUnitKerja(unitKerja);
		
		return simpan(program);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Long id) {
		programRepository.delete(id);
	}

	@Override
	public Program get(Long id) {
		return programRepository.findOne(id);
	}

	@Override
	public List<Program> get() {
		return programRepository.findAll();
	}

	@Override
	public List<Program> getByUnitKerja(Long id) {
		return programRepository.findByUnitKerja_Id(id);
	}

	@Override
	public List<Program> cari(String keyword) {
		return programRepository.findByNamaContaining(keyword);
	}

	@Override
	public RekapProgram rekapProgram(Long id) {
		return rekapProgramRepository.rekapSingle(id);
	}

	@Override
	public List<RekapProgram> rekap(Long tahun) {
		return rekapProgramRepository.rekap(tahun);
	}

	@Override
	public List<RekapProgram> rekap(Long tahun, String kode) {
		return rekapProgramRepository.rekap(tahun, kode);
	}

}
