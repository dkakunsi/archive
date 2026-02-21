package edu.dkakunsi.ta.presensi.service.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.PrimaryKey;
import edu.dkakunsi.ta.presensi.service.PresensiService;
import edu.dkakunsi.ta.presensi.util.DateTime;

@Component("presensiInterceptor")
class PresensiInterceptor {
	@Autowired private PresensiService presensiSvc;

	public void intercept(String nip) throws GeneralException {
		checkPresensi(nip);		
	}
	
	public void checkPresensi(String param) throws GeneralException {
		try {
			presensiSvc.get(new PrimaryKey(param, new DateTime().getSqlDate()));
		} catch (EntityException e) {
			throw new GeneralException("Maaf! Anda belum mengisi daftar hadir");
		}		
	}
}
