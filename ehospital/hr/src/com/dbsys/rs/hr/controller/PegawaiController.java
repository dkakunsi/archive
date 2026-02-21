package com.dbsys.rs.hr.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.hr.service.PegawaiService;
import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.EntityRestMessage;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.RestMessage;
import com.dbsys.rs.lib.entity.Apoteker;
import com.dbsys.rs.lib.entity.Dokter;
import com.dbsys.rs.lib.entity.Pegawai;
import com.dbsys.rs.lib.entity.Pekerja;
import com.dbsys.rs.lib.entity.Perawat;

@Controller
@RequestMapping("/pegawai")
public class PegawaiController {

	@Autowired
	private PegawaiService pegawaiService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Pegawai> save(@RequestBody Pegawai pegawai) throws ApplicationException, PersistenceException {
		pegawai = pegawaiService.save(pegawai);
		return EntityRestMessage.createPegawai(pegawai);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<Pegawai> getAll() throws ApplicationException, PersistenceException {
		List<Pegawai> list = pegawaiService.getPegawai();
		return ListEntityRestMessage.createListPegawai(list);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/class/{class}")
	@ResponseBody
	public ListEntityRestMessage<Pegawai> getAll(@PathVariable("class") String cls) throws ApplicationException, PersistenceException {
		List<?> list = null;
		
		if (Dokter.class.getSimpleName().equals(cls)) {
			list = pegawaiService.getDokter();
		} else if (Perawat.class.getSimpleName().equals(cls)) {
			list = pegawaiService.getPerawat();
		} else if (Apoteker.class.getSimpleName().equals(cls)) {
			list = pegawaiService.getApoteker();
		} else if (Pekerja.class.getSimpleName().equals(cls)) {
			list = pegawaiService.getPekerja();
		} else {
			throw new ApplicationException("Class tidak terdaftar");
		}
		
		List<Pegawai> listPegawai = new ArrayList<>();
		for (Object object : list)
			listPegawai.add((Pegawai) object);
		
		return ListEntityRestMessage.createListPegawai(listPegawai);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keyword/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<Pegawai> get(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<Pegawai> list = pegawaiService.getPegawai(keyword);
		return ListEntityRestMessage.createListPegawai(list);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keyword/{keyword}/class/{class}")
	@ResponseBody
	public ListEntityRestMessage<Pegawai> get(@PathVariable String keyword, @PathVariable("class") String cls) throws ApplicationException, PersistenceException {
		List<?> list = null;
		
		if (Dokter.class.getSimpleName().equals(cls)) {
			list = pegawaiService.getDokter(keyword);
		} else if (Perawat.class.getSimpleName().equals(cls)) {
			list = pegawaiService.getPerawat(keyword);
		} else if (Apoteker.class.getSimpleName().equals(cls)) {
			list = pegawaiService.getApoteker(keyword);
		} else if (Pekerja.class.getSimpleName().equals(cls)) {
			list = pegawaiService.getPekerja(keyword);
		} else {
			throw new ApplicationException("Class tidak terdaftar");
		}
		
		List<Pegawai> listPegawai = new ArrayList<>();
		for (Object object : list)
			listPegawai.add((Pegawai) object);
		
		return ListEntityRestMessage.createListPegawai(listPegawai);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage hapus(@PathVariable Long id) throws ApplicationException, PersistenceException {
		pegawaiService.hapus(id);
		return RestMessage.success();
	}
}
