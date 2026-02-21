package edu.dkakunsi.ta.presensi.service.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.exception.PulangException;

@Component("interceptor")
public class Interceptor {
	@Autowired private NullableInterceptor i1;
	@Autowired private PresensiInterceptor i2;
	@Autowired private PulangInterceptor i3;
	@Autowired private ValidationInterceptor i4;

	public void notNull(Object... args) throws GeneralException {
		i1.intercept(args);
	}
	
	public void mustPresent(String nip, String clientCode) throws GeneralException {
		validasi(nip, clientCode);
		i2.intercept(nip);
	}
	
	public void mustHome(String nip, String clientCode) throws PulangException, GeneralException {
		mustPresent(nip, clientCode);
		i3.intercept();
	}
	
	public void validasi(String nip, String clientCode) throws GeneralException {
		notNull(nip, clientCode);
		i4.intercept(nip, clientCode);
	}
}
