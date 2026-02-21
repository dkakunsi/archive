package edu.dkakunsi.ta.presensi.web.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Presensi;
import edu.dkakunsi.ta.presensi.model.entity.PrimaryKey;
import edu.dkakunsi.ta.presensi.model.rekapmodel.Rekap;
import edu.dkakunsi.ta.presensi.service.PresensiService;
import edu.dkakunsi.ta.presensi.util.DateTime;
import edu.dkakunsi.ta.presensi.util.Constant;
import edu.dkakunsi.ta.presensi.web.service.PresensiSEI;

@WebService(
        serviceName = "PresensiService",
        targetNamespace = Constant.TARGET_NAMESPACE,
        endpointInterface = "edu.dkakunsi.ta.presensi.web.service.PresensiSEI")
public class PresensiWs implements PresensiSEI {
	@Autowired private PresensiService presensiService;
	
	@Override
	public Presensi[] getByPegawai(String nip) 
			throws EntityException, GeneralException {
		return presensiService.toArray(
				presensiService.getByPegawai(nip));
	}

	@Override
	public Presensi[] getByPegawaiBulanan(String nip, int bulan, int tahun)
			throws EntityException, GeneralException {
		return presensiService.toArray(
				presensiService.getByPegawai(nip, bulan, tahun));
	}

	@Override
	public Presensi getById(String nip, int tanggal, int bulan, int tahun) 
			throws EntityException, GeneralException {
		return (Presensi)presensiService.get(
				new PrimaryKey(nip, new DateTime(tahun, bulan, tanggal).getUtilDate()));
	}

	@Override
	public Presensi getByIdLong(String nip, long tanggal) 
			throws EntityException, GeneralException {
		return (Presensi)presensiService.get(
				new PrimaryKey(nip, new DateTime(tanggal).getUtilDate()));
	}

	@Override
	public Presensi[] getByTanggal(int tanggal, int bulan, int tahun) 
			throws EntityException, GeneralException {
		return presensiService.toArray(
				presensiService.getByTanggal(new DateTime(tahun, bulan, tanggal).getUtilDate()));
	}

	@Override
	public Presensi[] getByTanggalLong(long tanggal) 
			throws EntityException, GeneralException {
		return presensiService.toArray(
				presensiService.getByTanggal(new DateTime(tanggal).getUtilDate()));
	}

	@Override
	public Presensi[] getByDepartment( String department)
			throws EntityException, GeneralException {
		return presensiService.toArray(
				presensiService.getByDepartment(department));
	}

	@Override
	public Presensi[] getByDepartmentTanggal(String department, int tanggal, int bulan, int tahun)
			throws EntityException, GeneralException {
		return presensiService.toArray(
				presensiService.getByDepartment(department, new DateTime(tahun, bulan, tanggal).getUtilDate()));
	}

	@Override
	public Presensi[] getByDepartmentTanggalLong(String department, long tanggal)
			throws EntityException, GeneralException {
		return presensiService.toArray(
				presensiService.getByDepartment(department, new DateTime(tanggal).getUtilDate()));
	}

	@Override
	public Presensi[] getByDepartmentBulanan(String department, int bulan, int tahun)
			throws EntityException, GeneralException {
		return presensiService.toArray(
				presensiService.getByDepartment(department, bulan, tahun));
	}

	@Override
	@Deprecated
	public String daftar(String nip, String clientCode) 
			throws EntityException, GeneralException {
		return presensiService.daftar(nip, clientCode);
	}
	
	@Override
	public String masuk(String nip, String clientCode)
			throws EntityException, GeneralException {
		return presensiService.masuk(nip, clientCode);
	}

	@Override
	public String keluar(String nip, String clientCode)
			throws EntityException, GeneralException {
		return presensiService.keluar(nip, clientCode);
	}

	@Override
	public Rekap[] rekap(String dept, int tanggal, int bulan, int tahun)
			throws EntityException, GeneralException {
		return presensiService.rekapArray(dept, new DateTime(tahun, bulan, tanggal));
	}
}
