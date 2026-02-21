package edu.dkakunsi.ta.presensi.web.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.exception.PulangException;
import edu.dkakunsi.ta.presensi.model.entity.Lembur;
import edu.dkakunsi.ta.presensi.model.entity.PrimaryKey;
import edu.dkakunsi.ta.presensi.model.rekapmodel.Rekap;
import edu.dkakunsi.ta.presensi.service.LemburService;
import edu.dkakunsi.ta.presensi.util.DateTime;
import edu.dkakunsi.ta.presensi.util.Constant;
import edu.dkakunsi.ta.presensi.web.service.LemburSEI;

@WebService(
        serviceName = "LemburService",
        targetNamespace = Constant.TARGET_NAMESPACE,
        endpointInterface = "edu.dkakunsi.ta.presensi.web.service.LemburSEI")
public class LemburWs implements LemburSEI {
	@Autowired private LemburService lemburService;

	@Override
	public Lembur[] getByPegawai(String nip) 
			throws EntityException, GeneralException {
		return lemburService.toArray(
				lemburService.getByPegawai(nip));
	}

	@Override
	public Lembur[] getByPegawaiBulanan(String nip, int bulan, int tahun)
			throws EntityException, GeneralException {
		return lemburService.toArray(
				lemburService.getByPegawai(nip, bulan, tahun));
	}

	@Override
	public Lembur[] getByTanggal(int tanggal, int bulan, int tahun) 
			throws EntityException, GeneralException {
		return lemburService.toArray(
				lemburService.getByTanggal(new DateTime(tahun, bulan, tanggal).getUtilDate()));
	}

	@Override
	public Lembur[] getByTanggalLong(long tanggal) 
			throws EntityException, GeneralException {
		return lemburService.toArray(
				lemburService.getByTanggal(new DateTime(tanggal).getUtilDate()));
	}

	@Override
	public Lembur[] getByDepartment(String department)
			throws EntityException, GeneralException {
		return lemburService.toArray(
				lemburService.getByDepartment(department));
	}

	@Override
	public Lembur[] getByDepartmentTanggal(String department, int tanggal, int bulan, int tahun)
			throws EntityException, GeneralException {
		return lemburService.toArray(
				lemburService.getByDepartment(department, new DateTime(tahun, bulan, tanggal).getUtilDate()));
	}

	@Override
	public Lembur[] getByDepartmentTanggalLong(String department, long tanggal)
			throws EntityException, GeneralException {
		return lemburService.toArray(
				lemburService.getByDepartment(department, new DateTime(tanggal).getUtilDate()));
	}

	public Lembur[] getByDepartmentBulanan(String department, int bulan, int tahun)
			throws EntityException, GeneralException {
		return lemburService.toArray(
				lemburService.getByDepartment(department, bulan, tahun));
	}

	@Override
	public Lembur getById(String nip, int tanggal, int bulan, int tahun) 
			throws EntityException, GeneralException {
		return (Lembur)lemburService.get(
				new PrimaryKey(nip, new DateTime(tahun, bulan, tanggal).getUtilDate()));
	}

	@Override
	public Lembur getByIdLong(String nip, long tanggal) 
			throws EntityException, GeneralException {
		return (Lembur)lemburService.get(
				new PrimaryKey(nip, new DateTime(tanggal).getUtilDate()));
	}

	@Override
	public String daftar(String nip, String clientCode) 
			throws GeneralException, EntityException, PulangException {
		return lemburService.daftar(nip, clientCode);
	}

	public Rekap[] rekap(String dept, int tanggal, int bulan, int tahun)
			throws GeneralException, EntityException, PulangException {
		return lemburService.rekapArray(dept, new DateTime(tahun, bulan, tanggal));
	}
	
	
}
