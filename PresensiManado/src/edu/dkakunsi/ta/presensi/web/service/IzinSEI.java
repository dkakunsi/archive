package edu.dkakunsi.ta.presensi.web.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Izin;
import edu.dkakunsi.ta.presensi.util.Constant;

@WebService(targetNamespace = Constant.TARGET_NAMESPACE)
@SOAPBinding(style = Style.RPC)
public interface IzinSEI {
    @WebResult(name = "daftarIzin", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarIzin")
	@WebMethod
	Izin[] getByPegawai(
		@WebParam(partName = "nip", name = "nip")
		String nip
	) throws EntityException, GeneralException;
	
    @WebResult(name = "daftarIzin", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarIzin")
	@WebMethod
	Izin[] getByPegawaiBulanan(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;

    @WebResult(name = "daftarIzin", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarIzin")
	@WebMethod
	Izin[] getByTanggal(
		@WebParam(partName = "tanggal", name = "tanggal")
		int tanggal,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;

    @WebResult(name = "daftarIzin", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarIzin")
	@WebMethod
	Izin[] getByTanggalLong(
		@WebParam(partName = "tanggal", name = "tanggal")
		long tanggal
	) throws EntityException, GeneralException;

    @WebResult(name = "Izin", targetNamespace = Constant.TARGET_NAMESPACE, partName = "Izin")
	@WebMethod
	Izin getById(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "tanggal", name = "tanggal")
		int tanggal,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun,
		@WebParam(partName = "nomor", name = "nomor")
		int nomor
	) throws EntityException, GeneralException;

    @WebResult(name = "Izin", targetNamespace = Constant.TARGET_NAMESPACE, partName = "Izin")
	@WebMethod
	Izin getByIdLong(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "tanggal", name = "tanggal")
		long tanggal,
		@WebParam(partName = "nomor", name = "nomor")
		int nomor
	) throws EntityException, GeneralException;

    @WebResult(name = "daftarIzin", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarIzin")
	@WebMethod
	Izin[] getByDepartment(
		@WebParam(partName = "department", name = "department")
		String department
	) throws EntityException, GeneralException;

    @WebResult(name = "daftarIzin", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarIzin")
	@WebMethod
	Izin[] getByDepartmentTanggal(
		@WebParam(partName = "department", name = "department")
		String department,
		@WebParam(partName = "tanggal", name = "tanggal")
		int tanggal,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;
	
    @WebResult(name = "daftarIzin", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarIzin")
	@WebMethod
	Izin[] getByDepartmentTanggalLong(
		@WebParam(partName = "department", name = "department")
		String department,
		@WebParam(partName = "tanggal", name = "tanggal")
		long tanggal
	) throws EntityException, GeneralException;
    
    @WebResult(name = "daftarIzin", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarIzin")
	@WebMethod
	Izin[] getByDepartmentBulanan(
		@WebParam(partName = "department", name = "department")
		String department,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;

    @WebResult(name = "message", targetNamespace = Constant.TARGET_NAMESPACE, partName = "message")
	@WebMethod
	@Deprecated
	String daftar(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "tanggal", name = "clientCode")
		String clientCode
	) throws EntityException, GeneralException;
    
    @WebResult(name = "message", targetNamespace = Constant.TARGET_NAMESPACE, partName = "message")
	@WebMethod
	String masuk(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "tanggal", name = "clientCode")
		String clientCode
	) throws EntityException, GeneralException;
    
    @WebResult(name = "message", targetNamespace = Constant.TARGET_NAMESPACE, partName = "message")
	@WebMethod
	String keluar(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "tanggal", name = "clientCode")
		String clientCode
	) throws EntityException, GeneralException;
        
}
