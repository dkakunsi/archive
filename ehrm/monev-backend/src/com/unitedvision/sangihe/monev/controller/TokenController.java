package com.unitedvision.sangihe.monev.controller;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.sangihe.monev.entity.Token;
import com.unitedvision.sangihe.monev.exception.ApplicationException;
import com.unitedvision.sangihe.monev.service.TokenService;
import com.unitedvision.sangihe.monev.util.EntityRestMessage;
import com.unitedvision.sangihe.monev.util.PasswordWrapper;

@Controller
@RequestMapping("/token")
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}")
	@ResponseBody
	public EntityRestMessage<Token> create(@PathVariable String nip, @RequestBody PasswordWrapper passwordWrapper) throws ApplicationException, PersistenceException {
		Token token = tokenService.create(nip, passwordWrapper.getPassword());
		
		return EntityRestMessage.create(token);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{tokenString}")
	@ResponseBody
	public EntityRestMessage<Token> lock(@PathVariable String tokenString) throws ApplicationException, PersistenceException {
		Token token = tokenService.lock(tokenString);
		
		return EntityRestMessage.create(token);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{tokenString}")
	@ResponseBody
	public EntityRestMessage<Token> get(@PathVariable String tokenString) throws ApplicationException, PersistenceException {
		Token token = tokenService.get(tokenString);
		
		return EntityRestMessage.create(token);
	}
}
