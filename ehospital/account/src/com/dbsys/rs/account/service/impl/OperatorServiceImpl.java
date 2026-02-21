package com.dbsys.rs.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.account.repository.OperatorRepository;
import com.dbsys.rs.account.service.OperatorService;
import com.dbsys.rs.lib.entity.Operator;

@Service
@Transactional(readOnly = true)
public class OperatorServiceImpl implements OperatorService {

	@Autowired
	private OperatorRepository operatorRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Operator save(Operator operator) {
		return operatorRepository.save(operator);
	}

	@Override
	public Operator get(String username) {
		return operatorRepository.findByUsername(username);
	}

	public Operator get(Long id) {
		return operatorRepository.findOne(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		operatorRepository.delete(id);
	}

	@Override
	public List<Operator> getAll() {
		return operatorRepository.findAll();
	}
}
