package edu.dkakunsi.ta.presensi.web.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.exception.PulangException;
import edu.dkakunsi.ta.presensi.model.entity.Lembur;
import edu.dkakunsi.ta.presensi.model.rekapmodel.Rekap;
import edu.dkakunsi.ta.presensi.util.Constant;

@WebService(targetNamespace = Constant.TARGET_NAMESPACE)
@SOAPBinding(style = Style.RPC)
public interface LemburSEI {
    @WebResult(name = "daftarLembur", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarLembur")
	@WebMethod
	Lembur[] getByPegawai(
		@WebParam(partName = "nip", name = "nip")
		String nip
	) throws EntityException, GeneralException;
	
    @WebResult(name = "daftarLembur", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarLembur")
	@WebMethod
	Lembur[] getByPegawaiBulanan(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;

    @WebResult(name = "daftarLembur", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarLembur")
	@WebMethod
	Lembur[] getByTanggal(
		@WebParam(partName = "tanggal", name = "tanggal")
		int tanggal,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;
	
    @WebResult(name = "daftarLembur", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarLembur")
	@WebMethod
	Lembur[] getByTanggalLong(
		@WebParam(partName = "tanggal", name = "tanggal")
		long tanggal
	) throws EntityException, GeneralException;

    @WebResult(name = "daftarLembur", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarLembur")
	@WebMethod
	Lembur[] getByDepartment(
		@WebParam(partName = "department", name = "department")
		String department
	) throws EntityException, GeneralException;

    @WebResult(name = "daftarLembur", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarLembur")
	@WebMethod
	Lembur[] getByDepartmentTanggal(
		@WebParam(partName = "department", name = "department")
		String department,
		@WebParam(partName = "tanggal", name = "tanggal")
		int tanggal,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;
	
    @WebResult(name = "daftarLembur", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarLembur")
	@WebMethod
	Lembur[] getByDepartmentTanggalLong(
		@WebParam(partName = "department", name = "department")
		String department,
		@WebParam(partName = "tanggal", name = "tanggal")
		long tanggal
	) throws EntityException, GeneralException;
    
    @WebResult(name = "daftarLembur", targetNamespace = Constant.TARGET_NAMESPACE, partName = "daftarLembur")
	@WebMethod
	Lembur[] getByDepartmentBulanan(
		@WebParam(partName = "department", name = "department")
		String department,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;

    @WebResult(name = "lembur", targetNamespace = Constant.TARGET_NAMESPACE, partName = "lembur")
	@WebMethod
	Lembur getById(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "tanggal", name = "tanggal")
		int tanggal,
		@WebParam(partName = "bulan", name = "bulan")
		int bulan,
		@WebParam(partName = "tahun", name = "tahun")
		int tahun
	) throws EntityException, GeneralException;

    @WebResult(name = "lembur", targetNamespace = Constant.TARGET_NAMESPACE, partName = "lembur")
	@WebMethod
	Lembur getByIdLong(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "tanggal", name = "tanggal")
		long tanggal
	) throws EntityException, GeneralException;

    @WebResult(name = "message", targetNamespace = Constant.TARGET_NAMESPACE, partName = "message")
	@WebMethod
	String daftar(
		@WebParam(partName = "nip", name = "nip")
		String nip,
		@WebParam(partName = "clientCode", name = "clientCode")
		String clientCode
	) throws GeneralException, EntityException, PulangException;
    
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
	) throws GeneralException, EntityException, PulangException;
    
}
