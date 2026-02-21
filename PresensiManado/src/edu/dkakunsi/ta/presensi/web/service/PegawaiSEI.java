package edu.dkakunsi.ta.presensi.web.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Pegawai;
import edu.dkakunsi.ta.presensi.util.Constant;

@WebService(targetNamespace = Constant.TARGET_NAMESPACE)
@SOAPBinding(style = Style.RPC)
public interface PegawaiSEI {
	@WebMethod
	void save(
		@WebParam(partName = "pegawai", name = "pegawai")
		Pegawai pegawai
	) throws EntityException, GeneralException;

	@WebMethod
	void delete(
		@WebParam(partName = "pegawai", name = "pegawai")
		Pegawai pegawai
	) throws EntityException, GeneralException;
    
    @WebResult(name = "pegawai", targetNamespace = Constant.TARGET_NAMESPACE, partName = "pegawai")
	@WebMethod
	Pegawai getById(
		@WebParam(partName = "nip", name = "nip")
		String nip
	) throws EntityException, GeneralException;
	
    @WebResult(name = "daftarPegawai", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarPegawai")
	@WebMethod
	Pegawai[] getByDepartment(
		@WebParam(partName = "skpd", name = "skpd")
		String skpd
	) throws EntityException, GeneralException;
    
    @WebResult(name = "daftarPegawai", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarPegawai")
	@WebMethod
	Pegawai[] getAll() throws EntityException, GeneralException;
}
