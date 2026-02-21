package edu.dkakunsi.ta.presensi.service.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Department;
import edu.dkakunsi.ta.presensi.model.entity.Pegawai;
import edu.dkakunsi.ta.presensi.service.DepartmentService;
import edu.dkakunsi.ta.presensi.service.PegawaiService;

@Component("validationInterceptor")
class ValidationInterceptor {
	@Autowired private PegawaiService pegawaiSvc;
	@Autowired private DepartmentService departmentSvc;

	public void intercept(String nip, String clientCode) throws GeneralException {
		Department d = doAuthentication(clientCode);
		Pegawai p = doValidation(nip);
		compare(d, p.getDepartment());
	}
	
	public Pegawai doValidation(String nip) throws GeneralException {
		try {
			return pegawaiSvc.get(nip);
		} catch (EntityException e) {
			throw new GeneralException("Maaf! Anda tidak terdaftar");
		}
	}
	
	public Department doAuthentication(String kode) throws GeneralException {
		try {
			return departmentSvc.get(kode);
		} catch (EntityException e) {
			throw new GeneralException("Maaf! SKPD tidak terdaftar");
		}
	}
	
	public void compare(Department dept1, Department dept2) throws GeneralException {
		if(!dept1.equals(dept2))
			throw new GeneralException("Isilah absen di kantor anda!");
	}
}
