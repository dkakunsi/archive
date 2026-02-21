package com.unitedvision.sangihe.monev.controller;

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

import com.unitedvision.sangihe.monev.entity.Program;
import com.unitedvision.sangihe.monev.entity.RekapProgram;
import com.unitedvision.sangihe.monev.exception.ApplicationException;
import com.unitedvision.sangihe.monev.service.ProgramService;
import com.unitedvision.sangihe.monev.util.EntityRestMessage;
import com.unitedvision.sangihe.monev.util.ListEntityRestMessage;
import com.unitedvision.sangihe.monev.util.RestMessage;

@Controller
@RequestMapping("/program")
public class ProgramController {

	@Autowired
	private ProgramService programService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{idUnitKerja}")
	@ResponseBody
	public RestMessage simpan(@PathVariable Long idUnitKerja, @RequestBody Program program) throws ApplicationException, PersistenceException {
		programService.simpan(program, idUnitKerja);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage hapus(@PathVariable Long id) throws ApplicationException, PersistenceException {
		programService.hapus(id);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public EntityRestMessage<Program> get(@PathVariable Long id) throws ApplicationException, PersistenceException {
		Program program = programService.get(id);
		
		return EntityRestMessage.create(program);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<Program> get() throws ApplicationException, PersistenceException {
		List<Program> daftarProgram = programService.get();
		
		return ListEntityRestMessage.createListProgram(daftarProgram);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/satker/{id}")
	@ResponseBody
	public ListEntityRestMessage<Program> getByUnitKerja(@PathVariable Long id) throws ApplicationException, PersistenceException {
		List<Program> daftarProgram = programService.getByUnitKerja(id);
		
		return ListEntityRestMessage.createListProgram(daftarProgram);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/cari/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<Program> cari(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<Program> daftarProgram = programService.cari(keyword);
		
		return ListEntityRestMessage.createListProgram(daftarProgram);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/rekap/cetak")
	public ModelAndView rekapProgram(@PathVariable Long id, Map<String, RekapProgram> model) {
		try {
			RekapProgram rekap = programService.rekapProgram(id);

			model.put("rekap", rekap);
			
			return new ModelAndView("rekapSingleProgram", model);
		} catch (PersistenceException e) {
			return new ModelAndView("pdfException", model);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/rekap")
	@ResponseBody
	public EntityRestMessage<RekapProgram> rekapProgramView(@PathVariable Long id) throws ApplicationException, PersistenceException {
		RekapProgram rekap = programService.rekapProgram(id);
		
		return EntityRestMessage.create(rekap);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/cetak")
	public ModelAndView rekap(@PathVariable Long tahun, Map<String, Object> model) {
		try {
			List<RekapProgram> rekap = programService.rekap(tahun);

			model.put("tahun", tahun);
			model.put("rekap", rekap);
			
			return new ModelAndView("rekapProgram", model);
		} catch (PersistenceException e) {
			return new ModelAndView("pdfException", model);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}")
	@ResponseBody
	public ListEntityRestMessage<RekapProgram> rekapView(@PathVariable Long tahun) throws ApplicationException, PersistenceException {
		List<RekapProgram> rekap = programService.rekap(tahun);
		
		return ListEntityRestMessage.createListRekapProgram(rekap);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/satker/{kode}/cetak")
	public ModelAndView rekap(@PathVariable String kode, @PathVariable Long tahun, Map<String, Object> model) {
		try {
			List<RekapProgram> rekap = programService.rekap(tahun, kode);

			model.put("tahun", tahun);
			model.put("rekap", rekap);
			
			return new ModelAndView("rekapProgram", model);
		} catch (PersistenceException e) {
			return new ModelAndView("pdfException", model);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/satker/{kode}")
	@ResponseBody
	public ListEntityRestMessage<RekapProgram> rekapView(@PathVariable String kode, @PathVariable Long tahun) throws ApplicationException, PersistenceException {
		List<RekapProgram> rekap = programService.rekap(tahun, kode);
		
		return ListEntityRestMessage.createListRekapProgram(rekap);
	}
}
