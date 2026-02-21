package com.unitedvision.sangihe.monev.controller;

import java.time.Month;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.sangihe.monev.entity.Fisik;
import com.unitedvision.sangihe.monev.exception.ApplicationException;
import com.unitedvision.sangihe.monev.service.FisikService;
import com.unitedvision.sangihe.monev.util.EntityRestMessage;
import com.unitedvision.sangihe.monev.util.ListEntityRestMessage;
import com.unitedvision.sangihe.monev.util.RestMessage;

@Controller
@RequestMapping("/fisik")
public class FisikController {

	@Autowired
	private FisikService fisikService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{idKegiatan}")
	@ResponseBody
	public RestMessage simpan(@PathVariable Long idKegiatan, @RequestBody Fisik fisik) throws ApplicationException, PersistenceException {
		fisikService.simpan(fisik, idKegiatan);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage hapus(@PathVariable Long id) throws ApplicationException, PersistenceException {
		fisikService.hapus(id);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public EntityRestMessage<Fisik> get(@PathVariable Long id) throws ApplicationException, PersistenceException {
		Fisik fisik = fisikService.get(id);
		
		return EntityRestMessage.create(fisik);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/kegiatan/{id}/tahun/{tahun}/bulan/{bulan}")
	@ResponseBody
	public EntityRestMessage<Fisik> get(@PathVariable Long id, @PathVariable Integer tahun, @PathVariable Month bulan) throws ApplicationException, PersistenceException {
		Fisik fisik = fisikService.get(id, tahun, bulan);
		
		return EntityRestMessage.create(fisik);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/program/{id}")
	@ResponseBody
	public ListEntityRestMessage<Fisik> getByProgram(@PathVariable Long id) throws ApplicationException, PersistenceException {
		List<Fisik> daftarFisik = fisikService.getByProgram(id);
		
		return ListEntityRestMessage.createListFisik(daftarFisik);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/kegiatan/{id}")
	@ResponseBody
	public ListEntityRestMessage<Fisik> getByKegiatan(@PathVariable Long id) throws ApplicationException, PersistenceException {
		List<Fisik> daftarFisik = fisikService.getByKegiatan(id);
		
		return ListEntityRestMessage.createListFisik(daftarFisik);
	}
}
