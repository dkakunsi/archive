package edu.dkakunsi.ta.presensi.web.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Presensi;
import edu.dkakunsi.ta.presensi.model.rekapmodel.Rekap;
import edu.dkakunsi.ta.presensi.util.Constant;

@WebService(targetNamespace = Constant.TARGET_NAMESPACE)
@SOAPBinding(style = Style.RPC)
public interface PresensiSEI {
    @WebResult(name = "daftarPresensi", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarPersensi")
	@WebMethod
	Presensi[] getByPegawai(
        @WebParam(partName = "nip", name = "nip")
		String nip
	) throws EntityException, GeneralException;
	
    @WebResult(name = "daftarPresensi", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarPersensi")
	@WebMethod
	Presensi[] getByPegawaiBulanan(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;

    @WebResult(name = "daftarPresensi", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarPersensi")
	@WebMethod
	Presensi[] getByTanggal(
		@WebParam(partName = "tanggal", name = "tanggal")
		int tanggal,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;
	
    @WebResult(name = "daftarPresensi", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarPersensi")
	@WebMethod
	Presensi[] getByTanggalLong(
		@WebParam(partName = "tanggal", name = "tanggal")
		long tanggal
	) throws EntityException, GeneralException;

    @WebResult(name = "daftarPresensi", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarPersensi")
	@WebMethod
	Presensi[] getByDepartment(
		@WebParam(partName = "department", name = "department")
		String department
	) throws EntityException, GeneralException;

    @WebResult(name = "daftarPresensi", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarPersensi")
	@WebMethod
	Presensi[] getByDepartmentTanggal(
		@WebParam(partName = "department", name = "department")
		String department,
		@WebParam(partName = "tanggal", name = "tanggal")
		int tanggal,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;
	
    @WebResult(name = "daftarPresensi", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarPersensi")
	@WebMethod
	Presensi[] getByDepartmentTanggalLong(
		@WebParam(partName = "department", name = "department")
		String department,
		@WebParam(partName = "tanggal", name = "tanggal")
		long tanggal
	) throws EntityException, GeneralException;
    
    @WebResult(name = "daftarPresensi", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarPersensi")
	@WebMethod
	Presensi[] getByDepartmentBulanan(
		@WebParam(partName = "department", name = "department")
		String department,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;

    @WebResult(name = "Presensi", targetNamespace = Constant.TARGET_NAMESPACE, partName = "Persensi")
	@WebMethod
	Presensi getById(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "tanggal", name = "tanggal")
		int tanggal,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;

    @WebResult(name = "Presensi", targetNamespace = Constant.TARGET_NAMESPACE, partName = "Persensi")
	@WebMethod
	Presensi getByIdLong(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "tanggal", name = "tanggal")
		long tanggal
	) throws EntityException, GeneralException;

    @WebResult(name = "message", targetNamespace = Constant.TARGET_NAMESPACE, partName = "message")
	@WebMethod
	@Deprecated
	String daftar(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "clientCode", name = "clientCode")
		String clientCode
	) throws EntityException, GeneralException;
    
    @WebResult(name = "message", targetNamespace = Constant.TARGET_NAMESPACE, partName = "message")
	@WebMethod
	String masuk(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "clientCode", name = "clientCode")
		String clientCode
	) throws EntityException, GeneralException;

    @WebResult(name = "message", targetNamespace = Constant.TARGET_NAMESPACE, partName = "message")
	@WebMethod
	String keluar(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "clientCode", name = "clientCode")
		String clientCode
	) throws EntityException, GeneralException;

    @WebResult(name = "message", targetNamespace = Constant.TARGET_NAMESPACE, partName = "message")
	@WebMethod
	Rekap[] rekap(
			@WebParam(partName = "departemen", name = "departemen")
			String dept,
			@WebParam(partName = "tanggal", name = "tanggal")
			int tanggal,
			@WebParam(partName = "bulan", name = "bulan")
			int bulan,
			@WebParam(partName = "tahun", name = "tahun")
			int tahun
			)throws EntityException, GeneralException;
}
