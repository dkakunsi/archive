package com.unitedvision.sangihe.monev.controller;

import java.time.Month;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.unitedvision.sangihe.monev.entity.Kegiatan;
import com.unitedvision.sangihe.monev.entity.RekapKegiatan;
import com.unitedvision.sangihe.monev.exception.ApplicationException;
import com.unitedvision.sangihe.monev.service.KegiatanService;
import com.unitedvision.sangihe.monev.util.EntityRestMessage;
import com.unitedvision.sangihe.monev.util.ListEntityRestMessage;
import com.unitedvision.sangihe.monev.util.RestMessage;

@Controller
@RequestMapping("/kegiatan")
public class KegiatanController {

	@Autowired
	private KegiatanService kegiatanService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{idProgram}")
	@ResponseBody
	public RestMessage simpan(@PathVariable Long idProgram, @RequestBody Kegiatan kegiatan) throws ApplicationException, PersistenceException {
		kegiatanService.simpan(kegiatan, idProgram);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage hapus(@PathVariable Long id) throws ApplicationException, PersistenceException {
		kegiatanService.hapus(id);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public EntityRestMessage<Kegiatan> get(@PathVariable Long id) throws ApplicationException, PersistenceException {
		Kegiatan kegiatan = kegiatanService.get(id);
		
		return EntityRestMessage.create(kegiatan);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<Kegiatan> get() throws ApplicationException, PersistenceException {
		List<Kegiatan> daftarKegiatan = kegiatanService.get();
		
		return ListEntityRestMessage.createListKegiatan(daftarKegiatan);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/satker/{id}")
	@ResponseBody
	public ListEntityRestMessage<Kegiatan> getByUnitKerja(@PathVariable Long id) throws ApplicationException, PersistenceException {
		List<Kegiatan> daftarKegiatan = kegiatanService.getByUnitKerja(id);
		
		return ListEntityRestMessage.createListKegiatan(daftarKegiatan);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/program/{id}")
	@ResponseBody
	public ListEntityRestMessage<Kegiatan> getByProgram(@PathVariable Long id) throws ApplicationException, PersistenceException {
		List<Kegiatan> daftarKegiatan = kegiatanService.getByProgram(id);
		
		return ListEntityRestMessage.createListKegiatan(daftarKegiatan);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/cari/{keyword}") 
	@ResponseBody
	public ListEntityRestMessage<Kegiatan> cari(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<Kegiatan> daftarKegiatan = kegiatanService.cari(keyword);
		
		return ListEntityRestMessage.createListKegiatan(daftarKegiatan);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/cetak")
	public ModelAndView rekap(@PathVariable Long tahun, Map<String, Object> model) {
		try {
			List<RekapKegiatan> rekap = kegiatanService.rekap(tahun);
			
			model.put("rekap", rekap);
			model.put("tahun", tahun);
			
			return new ModelAndView("rekapKegiatan", model);
		} catch (PersistenceException e) {
			return new ModelAndView("pdfException", model);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}")
	@ResponseBody
	public ListEntityRestMessage<RekapKegiatan> rekapView(@PathVariable Long tahun) throws ApplicationException, PersistenceException {
		List<RekapKegiatan> rekap = kegiatanService.rekap(tahun);
		
		return ListEntityRestMessage.createListRekapKegiatan(rekap);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/satker/{kode}/cetak")
	public ModelAndView rekap(@PathVariable Long tahun, @PathVariable String kode, Map<String, Object> model) {
		try {
			List<RekapKegiatan> rekap = kegiatanService.rekap(tahun, kode);
			
			model.put("rekap", rekap);
			model.put("tahun", tahun);
			
			return new ModelAndView("rekapKegiatan", model);
		} catch (PersistenceException e) {
			return new ModelAndView("pdfException", model);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/satker/{kode}")
	@ResponseBody
	public ListEntityRestMessage<RekapKegiatan> rekapView(@PathVariable String kode, @PathVariable Long tahun) throws ApplicationException, PersistenceException {
		List<RekapKegiatan> rekap = kegiatanService.rekap(tahun, kode);
		
		return ListEntityRestMessage.createListRekapKegiatan(rekap);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/program/{id}/cetak")
	public ModelAndView rekap(@PathVariable Long tahun, @PathVariable Long id, Map<String, Object> model) {
		try {
			List<RekapKegiatan> rekap = kegiatanService.rekap(tahun, id);
			
			model.put("rekap", rekap);
			model.put("tahun", tahun);
			
			return new ModelAndView("rekapKegiatan", model);
		} catch (PersistenceException e) {
			return new ModelAndView("pdfException", model);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/program/{id}")
	@ResponseBody
	public ListEntityRestMessage<RekapKegiatan> rekapView(@PathVariable Long tahun, @PathVariable Long id) throws ApplicationException, PersistenceException {
		List<RekapKegiatan> rekap = kegiatanService.rekap(tahun, id);
		
		return ListEntityRestMessage.createListRekapKegiatan(rekap);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/bulan/{bulan}")
	@ResponseBody
	public ListEntityRestMessage<RekapKegiatan> rekapView(@PathVariable Long tahun, @PathVariable Month bulan) throws ApplicationException, PersistenceException {
		List<RekapKegiatan> rekap = kegiatanService.rekap(tahun, bulan);
		
		return ListEntityRestMessage.createListRekapKegiatan(rekap);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/bulan/{bulan}/cetak")
	public ModelAndView rekap(@PathVariable Long tahun, @PathVariable Month bulan, Map<String, Object> model) {
		try {
			List<RekapKegiatan> rekap = kegiatanService.rekap(tahun, bulan);
			
			model.put("rekap", rekap);
			model.put("tahun", tahun);
			model.put("bulan", bulan);
			
			return new ModelAndView("rekapKegiatan", model);
		} catch (PersistenceException e) {
			return new ModelAndView("pdfException", model);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/rekap/cetak")
	public ModelAndView rekapKegiatan(@PathVariable Long id, Map<String, Object> model) {
		try {
			RekapKegiatan rekap = kegiatanService.rekapKegiatan(id);
			
			model.put("rekap", rekap);
			
			return new ModelAndView("rekapSingleKegiatan", model);
		} catch (PersistenceException e) {
			return new ModelAndView("pdfException", model);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/rekap")
	@ResponseBody
	public EntityRestMessage<RekapKegiatan> rekapKegiatanView(@PathVariable Long id) throws ApplicationException, PersistenceException {
		RekapKegiatan rekap = kegiatanService.rekapKegiatan(id);
		
		return EntityRestMessage.create(rekap);
	}
}
