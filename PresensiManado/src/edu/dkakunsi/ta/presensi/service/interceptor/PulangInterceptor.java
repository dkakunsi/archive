package edu.dkakunsi.ta.presensi.service.interceptor;

import org.springframework.stereotype.Component;

import edu.dkakunsi.ta.presensi.exception.PulangException;
import edu.dkakunsi.ta.presensi.util.DateTime;
import edu.dkakunsi.ta.presensi.util.TimeChecker;

@Component("pulangInterceptor")
class PulangInterceptor {

	public void intercept() throws PulangException {
		TimeChecker.checkWaktuPulang(new DateTime().getTime());
	}
}
