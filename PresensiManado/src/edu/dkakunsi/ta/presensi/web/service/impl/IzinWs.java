package edu.dkakunsi.ta.presensi.web.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Izin;
import edu.dkakunsi.ta.presensi.model.entity.IzinPK;
import edu.dkakunsi.ta.presensi.service.IzinService;
import edu.dkakunsi.ta.presensi.util.DateTime;
import edu.dkakunsi.ta.presensi.util.Constant;
import edu.dkakunsi.ta.presensi.web.service.IzinSEI;

@WebService(
        serviceName = "IzinService",
        targetNamespace = Constant.TARGET_NAMESPACE,
        endpointInterface = "edu.dkakunsi.ta.presensi.web.service.IzinSEI")
public class IzinWs implements IzinSEI {
	@Autowired private IzinService izinService;

	@Override
	public Izin[] getByPegawai(String nip) throws EntityException, GeneralException {
		return izinService.toArray(
				izinService.getByPegawai(nip));
	}

	@Override
	public Izin[] getByPegawaiBulanan(String nip, int bulan, int tahun) throws EntityException, GeneralException {
		return izinService.toArray(
				izinService.getByPegawai(nip, bulan, tahun));
	}

	@Override
	public Izin[] getByTanggal(int tanggal, int bulan, int tahun) throws EntityException, GeneralException {
		return izinService.toArray(
				izinService.getByTanggal(new DateTime(tahun, bulan, tanggal).getUtilDate()));
	}

	@Override
	public Izin[] getByTanggalLong(long tanggal) throws EntityException, GeneralException {
		return izinService.toArray(
				izinService.getByTanggal(new DateTime(tanggal).getUtilDate()));
	}

	@Override
	public Izin getById(String nip, int tanggal, int bulan, int tahun, int nomor) throws EntityException, GeneralException {
		return (Izin)izinService.get(
				new IzinPK(nip, new DateTime(tahun, bulan, tanggal).getUtilDate(), nomor));
	}

	@Override
	public Izin getByIdLong(String nip, long tanggal, int nomor) throws EntityException, GeneralException {
		return (Izin)izinService.get(
				new IzinPK(nip, new DateTime(tanggal).getUtilDate(), nomor));
	}

	@Override
	public Izin[] getByDepartment( String department) throws EntityException, GeneralException {
		return izinService.toArray(
				izinService.getByDepartment(department));
	}

	@Override
	public Izin[] getByDepartmentTanggal(String department, int tanggal, int bulan, int tahun) throws EntityException, GeneralException {
		return izinService.toArray(
				izinService.getByDepartment(department, new DateTime(tahun, bulan, tanggal).getUtilDate()));
	}

	@Override
	public Izin[] getByDepartmentTanggalLong(String department, long tanggal) throws EntityException, GeneralException {
		return izinService.toArray(
				izinService.getByDepartment(department, new DateTime(tanggal).getUtilDate()));
	}

	@Override
	public Izin[] getByDepartmentBulanan(String department, int bulan, int tahun) throws EntityException, GeneralException {
		return izinService.toArray(
				izinService.getByDepartment(department, bulan, tahun));
	}

	@Override
	@Deprecated
	public String daftar(String nip, String clientCode) throws EntityException, GeneralException {
		return izinService.daftar(nip, clientCode);
	}
	
	@Override
	public String masuk(String nip, String clientCode) throws EntityException, GeneralException {
		return izinService.masuk(nip, clientCode);
	}

	@Override
	public String keluar(String nip, String clientCode) throws EntityException, GeneralException {
		return izinService.keluar(nip, clientCode);
	}
}
