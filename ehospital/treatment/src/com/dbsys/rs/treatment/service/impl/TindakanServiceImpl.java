package com.dbsys.rs.treatment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.lib.Kelas;
import com.dbsys.rs.lib.entity.Tindakan;
import com.dbsys.rs.treatment.repository.TindakanRepository;
import com.dbsys.rs.treatment.service.TindakanService;

@Service
@Transactional(readOnly = true)
public class TindakanServiceImpl implements TindakanService {

	@Autowired
	private TindakanRepository tindakanRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Tindakan save(Tindakan tindakan) {
		return tindakanRepository.save(tindakan);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		tindakanRepository.delete(id);
	}

	@Override
	public List<Tindakan> getAll() {
		return tindakanRepository.findAll();
	}

	@Override
	public List<Tindakan> get(String keyword) {
		return tindakanRepository.findByKodeContainingOrNamaContaining(keyword, keyword);
	}

	@Override
	public Tindakan get(String nama, Kelas kelas) {
		return tindakanRepository.findByNamaAndKelas(nama, kelas);
	}
}
