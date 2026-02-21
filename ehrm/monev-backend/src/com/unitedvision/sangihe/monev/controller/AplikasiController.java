package com.unitedvision.sangihe.monev.controller;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.sangihe.monev.configuration.ApplicationConfig;
import com.unitedvision.sangihe.monev.exception.ApplicationException;
import com.unitedvision.sangihe.monev.util.RestMessage;

@Controller
@RequestMapping("/aplikasi")
public class AplikasiController {

	@RequestMapping(method = RequestMethod.GET, value = "/kode")
	@ResponseBody
	public RestMessage getKode() throws ApplicationException, PersistenceException {
		String kode = ApplicationConfig.KODE_APLIKASI;
		
		return RestMessage.create(kode);
	}
}
