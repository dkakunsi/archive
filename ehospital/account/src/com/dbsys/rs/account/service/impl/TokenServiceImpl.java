package com.dbsys.rs.account.service.impl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.account.repository.OperatorRepository;
import com.dbsys.rs.account.repository.TokenRepository;
import com.dbsys.rs.account.service.TokenService;
import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.entity.Operator;
import com.dbsys.rs.lib.entity.Token;
import com.dbsys.rs.lib.entity.Token.StatusToken;
import com.dbsys.rs.lib.UnauthenticatedAccessException;

@Service
@Transactional(readOnly = true)
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private OperatorRepository operatorRepository;

	@Override
	@Transactional(readOnly = false)
	public Token create(String username, String password) throws UnauthenticatedAccessException {
		Operator operator = operatorRepository.findByUsername(username);
		if (!operator.authenticate(password))
			throw new UnauthenticatedAccessException();
		
		Date tanggalBuat = DateUtil.getDate();
		Token token = new Token(tanggalBuat, operator);
		
		return tokenRepository.save(token);
	}

	@Override
	@Transactional(readOnly = false)
	public void lock(String tokenString) {
		tokenRepository.updateStatus(tokenString, StatusToken.KUNCI);
	}
}
