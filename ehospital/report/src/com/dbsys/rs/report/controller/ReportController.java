package com.dbsys.rs.report.controller;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.RestMessage.Type;
import com.dbsys.rs.report.entity.RekapStokBarang;
import com.dbsys.rs.report.entity.RekapUnit;
import com.dbsys.rs.report.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@RequestMapping(method = RequestMethod.GET, value = "/unit/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<RekapUnit> rekapUnit(@PathVariable String awal, @PathVariable String akhir) throws ApplicationException, PersistenceException {
		List<RekapUnit> list = reportService.rekapUnit(DateUtil.getDate(awal), DateUtil.getDate(akhir));
		ListEntityRestMessage<RekapUnit> message = new ListEntityRestMessage<>();
		message.setList(list);
		message.setMessage("Berhasil!");
		message.setTipe(Type.LIST);
		
		return message;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/stok/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<RekapStokBarang> rekapStok(@PathVariable String awal, @PathVariable String akhir) throws ApplicationException, PersistenceException {
		List<RekapStokBarang> list = reportService.rekapStok(DateUtil.getDate(awal), DateUtil.getDate(akhir));
		ListEntityRestMessage<RekapStokBarang> message = new ListEntityRestMessage<>();
		message.setList(list);
		message.setMessage("Berhasil!");
		message.setTipe(Type.LIST);
		
		return message;
	}
}
