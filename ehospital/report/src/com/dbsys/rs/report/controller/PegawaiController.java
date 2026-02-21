package com.dbsys.rs.report.controller;

import java.sql.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.RestMessage.Type;
import com.dbsys.rs.report.entity.RekapPegawai;
import com.dbsys.rs.report.service.PegawaiService;

@Controller
@RequestMapping("/pegawai")
public class PegawaiController {

	@Autowired
	private PegawaiService pegawaiService;

	@RequestMapping(method = RequestMethod.GET, value = "/dokter/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<RekapPegawai> rekapDokter(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<RekapPegawai> list = pegawaiService.rekapDokter(awal, akhir);
		ListEntityRestMessage<RekapPegawai> message = new ListEntityRestMessage<>();
		message.setList(list);
		message.setMessage("Berhasil!");
		message.setTipe(Type.LIST);
		
		return message;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/perawat/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<RekapPegawai> rekapPerawat(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<RekapPegawai> list = pegawaiService.rekapPerawat(awal, akhir);
		ListEntityRestMessage<RekapPegawai> message = new ListEntityRestMessage<>();
		message.setList(list);
		message.setMessage("Berhasil!");
		message.setTipe(Type.LIST);
		
		return message;
	}
}
