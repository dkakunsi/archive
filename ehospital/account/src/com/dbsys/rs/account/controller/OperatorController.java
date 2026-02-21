package com.dbsys.rs.account.controller;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.account.service.OperatorService;
import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.EntityRestMessage;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.RestMessage;
import com.dbsys.rs.lib.entity.Operator;

@Controller
@RequestMapping("/operator")
public class OperatorController {
	
	@Autowired
	private OperatorService operatorService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Operator> save(@RequestBody Operator operator) throws ApplicationException, PersistenceException {
		operator = operatorService.save(operator);
		return EntityRestMessage.createOperator(operator);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage delete(@PathVariable Long id) throws ApplicationException, PersistenceException {
		operatorService.delete(id);
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<Operator> getAll() throws ApplicationException, PersistenceException {
		List<Operator> list = operatorService.getAll();
		return ListEntityRestMessage.createListOperator(list);
	}
}
