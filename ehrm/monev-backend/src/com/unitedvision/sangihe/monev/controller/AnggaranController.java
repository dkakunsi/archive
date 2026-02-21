package com.unitedvision.sangihe.monev.controller;

import java.time.Month;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.sangihe.monev.entity.Anggaran;
import com.unitedvision.sangihe.monev.exception.ApplicationException;
import com.unitedvision.sangihe.monev.service.AnggaranService;
import com.unitedvision.sangihe.monev.util.EntityRestMessage;
import com.unitedvision.sangihe.monev.util.ListEntityRestMessage;
import com.unitedvision.sangihe.monev.util.RestMessage;

@Service
@RequestMapping("/anggaran")
public class AnggaranController {

	@Autowired
	private AnggaranService anggaranService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{idKegiatan}")
	@ResponseBody
	public RestMessage simpan(@PathVariable Long idKegiatan, @RequestBody Anggaran anggaran) throws ApplicationException, PersistenceException {
		anggaranService.simpan(anggaran, idKegiatan);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{id}/realisasi/{realisasi}")
	@ResponseBody
	public RestMessage realisasi(@PathVariable Long id, @PathVariable Long realisasi) throws ApplicationException, PersistenceException {
		anggaranService.realisasi(id, realisasi);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage hapus(@PathVariable Long id) throws ApplicationException, PersistenceException {
		anggaranService.hapus(id);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/kegiatan/{id}/tahun/{tahun}/bulan/{bulan}")
	@ResponseBody
	public EntityRestMessage<Anggaran> get(@PathVariable Long id, @PathVariable Integer tahun, @PathVariable Month bulan) throws ApplicationException, PersistenceException {
		Anggaran anggaran = anggaranService.get(id, tahun, bulan);
		
		return EntityRestMessage.create(anggaran);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public EntityRestMessage<Anggaran> get(@PathVariable Long id) throws ApplicationException, PersistenceException {
		Anggaran anggaran = anggaranService.get(id);
		
		return EntityRestMessage.create(anggaran);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/program/{id}")
	@ResponseBody
	public ListEntityRestMessage<Anggaran> getByProgram(@PathVariable Long id) throws ApplicationException, PersistenceException {
		List<Anggaran> daftarAnggaran = anggaranService.getByProgram(id);
		
		return ListEntityRestMessage.createListAnggaran(daftarAnggaran);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/kegiatan/{id}")
	@ResponseBody
	public ListEntityRestMessage<Anggaran> getByKegiatan(@PathVariable Long id) throws ApplicationException, PersistenceException {
		List<Anggaran> daftarAnggaran = anggaranService.getByKegiatan(id);
		
		return ListEntityRestMessage.createListAnggaran(daftarAnggaran);
	}
}
