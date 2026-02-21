package edu.dkakunsi.ta.presensi.web.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Pegawai;
import edu.dkakunsi.ta.presensi.service.PegawaiService;
import edu.dkakunsi.ta.presensi.util.Constant;
import edu.dkakunsi.ta.presensi.web.service.PegawaiSEI;

@WebService(
        serviceName = "PegawaiService",
        targetNamespace = Constant.TARGET_NAMESPACE,
        endpointInterface = "edu.dkakunsi.ta.presensi.web.service.PegawaiSEI")
public class PegawaiWs implements PegawaiSEI {
	@Autowired private PegawaiService pegawaiSvc;
	
	@Override
	public void save(Pegawai pegawai)
			throws EntityException, GeneralException {
		pegawaiSvc.save(pegawai);
	}

	@Override
	public void delete(Pegawai pegawai)
			throws EntityException, GeneralException {
		pegawaiSvc.delete(pegawai);
	}

	@Override
	public Pegawai getById(String nip)
			throws EntityException, GeneralException {
		return pegawaiSvc.get(nip);
	}

	@Override
	public Pegawai[] getByDepartment(String skpd)
			throws EntityException, GeneralException {
		return pegawaiSvc.toArray(
				pegawaiSvc.getByDepartment(skpd));
	}

	@Override
	public Pegawai[] getAll() 
			throws EntityException {
		return pegawaiSvc.toArray(
				pegawaiSvc.getAll());
	}
}
