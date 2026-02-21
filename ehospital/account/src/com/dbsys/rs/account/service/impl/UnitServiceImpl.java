package com.dbsys.rs.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.account.repository.UnitRepository;
import com.dbsys.rs.account.service.UnitService;
import com.dbsys.rs.lib.entity.Unit;

@Service
@Transactional(readOnly = true)
public class UnitServiceImpl implements UnitService {

	@Autowired
	private UnitRepository unitRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Unit save(Unit unit) {
		return unitRepository.save(unit);
	}

	@Override
	public Unit get(Long id) {
		return unitRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		unitRepository.delete(id);
	}

	@Override
	public List<Unit> getAll() {
		return unitRepository.findAll();
	}
}
