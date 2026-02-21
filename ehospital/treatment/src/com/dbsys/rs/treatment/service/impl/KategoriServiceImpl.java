package com.dbsys.rs.treatment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.lib.entity.KategoriTindakan;
import com.dbsys.rs.treatment.repository.KategoriRepository;
import com.dbsys.rs.treatment.service.KategoriService;

@Service
@Transactional(readOnly = true)
public class KategoriServiceImpl implements KategoriService {
	
	@Autowired
	private KategoriRepository kategoriRepository;

	@Override
	@Transactional(readOnly = false)
	public KategoriTindakan simpan(KategoriTindakan kategori) {
		return kategoriRepository.save(kategori);
	}

	@Override
	public List<KategoriTindakan> getAll() {
		return kategoriRepository.findAll();
	}

	@Override
	public KategoriTindakan getById(Long id) {
		return kategoriRepository.findOne(id);
	}

}
