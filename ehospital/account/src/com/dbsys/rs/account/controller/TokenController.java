package com.dbsys.rs.account.controller;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.account.service.TokenService;
import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.Credential;
import com.dbsys.rs.lib.EntityRestMessage;
import com.dbsys.rs.lib.RestMessage;
import com.dbsys.rs.lib.entity.Token;
import com.dbsys.rs.lib.entity.Unit;

/**
 * Controller untuk meng-handle request pada url /token/**, 
 * berhubungan dengan token untuk autentikasi dan autorisasi operator.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Controller
@RequestMapping( "/token" )
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	/**
	 * Meng-handle request untuk login.
	 * 
	 * @param credential
	 * 
	 * @return token, jika username dan password valid
	 * 
	 * @throws ApplicationException
	 * @throws PersistenceException
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Token> create(@RequestBody Credential credential) throws ApplicationException, PersistenceException {
		Token token = tokenService.create(credential.getUsername(), credential.getPassword());
		return EntityRestMessage.createToken(token);
	}

	/**
	 * Meng-handle request untuk logout.
	 * 
	 * @param tokenString
	 * 
	 * @return pesan berhasil
	 * 
	 * @throws ApplicationException
	 * @throws PersistenceException
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{tokenString}")
	@ResponseBody
	public RestMessage lock(@PathVariable String tokenString) throws ApplicationException, PersistenceException {
		tokenService.lock(tokenString);
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/test/test")
	@ResponseBody
	public RestMessage test() throws ApplicationException, PersistenceException {
		return RestMessage.success(Unit.TipeUnit.UGD.toString());
	}
}
