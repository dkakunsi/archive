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
import com.dbsys.rs.report.entity.RekapPelayanan;
import com.dbsys.rs.report.entity.RekapTagihan;
import com.dbsys.rs.report.service.ReportService;

@Controller
@RequestMapping("/tagihan")
public class TagihanController {
	
	@Autowired
	private ReportService reportService;

	@RequestMapping(method = RequestMethod.GET, value = "/pasien/{pasien}")
	@ResponseBody
	public ListEntityRestMessage<RekapTagihan> rekap(@PathVariable Long pasien) throws ApplicationException, PersistenceException {
		List<RekapTagihan> list = reportService.rekapTagihan(pasien);
		ListEntityRestMessage<RekapTagihan> message = new ListEntityRestMessage<>();
		message.setList(list);
		message.setMessage("Berhasil!");
		message.setTipe(Type.LIST);
		
		return message;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pemakaian/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<RekapTagihan> rekapPemakaian(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<RekapTagihan> list = reportService.rekapTagihanPemakaian(awal, akhir);
		ListEntityRestMessage<RekapTagihan> message = new ListEntityRestMessage<>();
		message.setList(list);
		message.setMessage("Berhasil!");
		message.setTipe(Type.LIST);
		
		return message;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pelayanan/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<RekapPelayanan> rekapPelayanan(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<RekapPelayanan> list = reportService.rekapPelayanan(awal, akhir);
		ListEntityRestMessage<RekapPelayanan> message = new ListEntityRestMessage<>();
		message.setList(list);
		message.setMessage("Berhasil!");
		message.setTipe(Type.LIST);
		
		return message;
	}
}
