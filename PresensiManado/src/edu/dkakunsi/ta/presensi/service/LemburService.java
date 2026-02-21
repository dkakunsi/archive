package edu.dkakunsi.ta.presensi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.dkakunsi.ta.presensi.dao.LemburDao;
import edu.dkakunsi.ta.presensi.dao.PresensiDao;
import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.exception.PulangException;
import edu.dkakunsi.ta.presensi.model.entity.Lembur;
import edu.dkakunsi.ta.presensi.model.entity.Pegawai;
import edu.dkakunsi.ta.presensi.model.entity.Presensi;
import edu.dkakunsi.ta.presensi.model.entity.PrimaryKey;
import edu.dkakunsi.ta.presensi.model.rekapmodel.Rekap;
import edu.dkakunsi.ta.presensi.model.rekapmodel.RekapLembur;
import edu.dkakunsi.ta.presensi.service.interceptor.Interceptor;
import edu.dkakunsi.ta.presensi.util.DateTime;

@Service("lemburService")
@Transactional(readOnly=true)
public class LemburService {
	@Autowired private LemburDao dao;
	@Autowired private PresensiDao presensiDao;
	@Autowired private Interceptor interceptor;
	@Autowired private PegawaiService pegawaiService;

	@Transactional(readOnly=false)
	public void save(Lembur lembur) throws EntityException, GeneralException {
		interceptor.notNull(lembur);
		dao.save(lembur);
	}

	@Transactional(readOnly=false)
	public void delete(Lembur lembur) throws EntityException, GeneralException {
		interceptor.notNull(lembur);
		dao.delete(lembur);
	}

	public Lembur get(PrimaryKey pk) throws EntityException, GeneralException {
		interceptor.notNull(pk);
		return dao.get(pk);
	}

	public List<Lembur> getAll() throws EntityException {
		return dao.getAll();
	}

	public List<Lembur> getByPegawai(String nip) throws EntityException, GeneralException {
		interceptor.notNull(nip);
		return dao.getByPegawai(nip);
	}

	public List<Lembur> getByPegawai(String nip, int bulan, int tahun) throws EntityException, GeneralException {
		interceptor.notNull(nip, bulan, tahun);

		Date tanggalMulai = new DateTime(tahun, bulan, 1).getUtilDate();
		Date tanggalSelesai = new DateTime(tahun, bulan, 31).getUtilDate();

		return dao.getByPegawai(nip, tanggalMulai, tanggalSelesai);
	}

	public List<Lembur> getByTanggal(Date tanggal) throws EntityException, GeneralException {
		interceptor.notNull(tanggal);
		return dao.getByTanggal(tanggal);
	}

	public List<Lembur> getByDepartment(String department) throws EntityException, GeneralException {
		interceptor.notNull(department);
		return dao.getByDepartment(department);
	}

	public List<Lembur> getByDepartment(String department, Date date) throws EntityException, GeneralException {
		interceptor.notNull(department, date);
		return dao.getByDepartment(department, date);
	}

	public List<Lembur> getByDepartment(String department, int bulan, int tahun) throws EntityException, GeneralException {
		interceptor.notNull(department, bulan, tahun);

		Date tanggalMulai = new DateTime(tahun, bulan, 1).getUtilDate();
		Date tanggalSelesai = new DateTime(tahun, bulan, 31).getUtilDate();
		
		return dao.getByDepartment(department, tanggalMulai, tanggalSelesai);
	}

	@Transactional(readOnly=false)
	public String daftar(String nip, String clientCode) throws GeneralException, EntityException, PulangException {
		interceptor.mustHome(nip, clientCode);
		
		PrimaryKey pk = new PrimaryKey(nip, new DateTime().getUtilDate());
		Presensi presensi = presensiDao.get(pk);

		Lembur lembur = new Lembur();
		lembur.setId(presensi.getId());
		lembur.setPresensi(presensi);
	
		save(lembur);
		presensiDao.save(presensi);
		
		return "Selamat Bekerja " + presensi.getPegawai().getNama();
	}

	public List<Rekap> rekap(String dept, DateTime dt)
			throws EntityException, GeneralException {
		List<Rekap> ls = new ArrayList<>();
		for(Pegawai p : pegawaiService.getByDepartment(dept)) {
			int jumlah = 0;
			for(Lembur l : getByPegawai(p.getNip(), dt.getBulan(), dt.getTahun())) {
				jumlah += l.getJumlah();
			}

			Rekap rl = new RekapLembur(p, jumlah);
			ls.add(rl);
		}
		return ls;
	}

	public Rekap[] rekapArray(String dept, DateTime dt)
			throws EntityException, GeneralException {
		List<Rekap> ls = rekap(dept, dt);
		Rekap[] arr = new Rekap[ls.size()];
		int counter = 0;

		for(Rekap r : ls) {
			arr[counter] = (Rekap)r;
			counter++;
		}
		return arr;
	}

	public Lembur[] toArray(List<Lembur> ls) {
		Lembur[] arr = new Lembur[ls.size()];
		int counter = 0;
		
		for(Lembur s : ls) {
			arr[counter] = (Lembur)s;
			counter++;
		}
		return arr;
	}
}
