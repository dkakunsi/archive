package edu.dkakunsi.ta.presensi.service.interceptor;

import org.springframework.stereotype.Component;

import edu.dkakunsi.ta.presensi.exception.GeneralException;

@Component("nullInterceptor")
class NullableInterceptor {

	public void intercept(Object... args) throws GeneralException {
		
		for(Object obj : args) {
			checkNull(obj);
		}
	}
	
	public void checkNull(Object obj) throws GeneralException {
		if(obj == null)
			throw new GeneralException("Parameter tidak boleh 'null' atau kosong");
	}
}
