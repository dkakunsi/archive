package com.dbsys.rs.treatment.controller;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.EntityRestMessage;
import com.dbsys.rs.lib.Kelas;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.RestMessage;
import com.dbsys.rs.lib.entity.Tindakan;
import com.dbsys.rs.treatment.service.TindakanService;

@Controller
@RequestMapping("/tindakan")
public class TindakanController {

	@Autowired
	private TindakanService tindakanService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Tindakan> save(@RequestBody Tindakan tindakan) throws ApplicationException, PersistenceException {
		tindakan = tindakanService.save(tindakan);
		return EntityRestMessage.createTindakan(tindakan);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage delete(@PathVariable Long id) throws ApplicationException, PersistenceException {
		tindakanService.delete(id);
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/nama/{nama}/kelas/{kelas}")
	@ResponseBody
	public EntityRestMessage<Tindakan> get(@PathVariable String nama, @PathVariable Kelas kelas) throws ApplicationException, PersistenceException {
		Tindakan tindakan = tindakanService.get(nama, kelas);
		return EntityRestMessage.createTindakan(tindakan);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keyword/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<Tindakan> get(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<Tindakan> list = tindakanService.get(keyword);
		return ListEntityRestMessage.createListTindakan(list);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<Tindakan> getAll() throws ApplicationException, PersistenceException {
		List<Tindakan> list = tindakanService.getAll();
		return ListEntityRestMessage.createListTindakan(list);
	}
}
