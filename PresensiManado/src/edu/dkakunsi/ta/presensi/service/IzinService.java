package edu.dkakunsi.ta.presensi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.dkakunsi.ta.presensi.dao.IzinDao;
import edu.dkakunsi.ta.presensi.dao.PresensiDao;
import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Izin;
import edu.dkakunsi.ta.presensi.model.entity.IzinPK;
import edu.dkakunsi.ta.presensi.model.entity.Presensi;
import edu.dkakunsi.ta.presensi.model.entity.PrimaryKey;
import edu.dkakunsi.ta.presensi.service.interceptor.Interceptor;
import edu.dkakunsi.ta.presensi.util.DateTime;

@Service("izinService")
@Transactional(readOnly=true)
public class IzinService {
	@Autowired private IzinDao dao;
	@Autowired private PresensiDao presensiDao;
	@Autowired private Interceptor interceptor;

	@Transactional(readOnly=false)
	public void save(Izin izin) throws EntityException, GeneralException {
		interceptor.notNull(izin);
		dao.save(izin);
	}

	@Transactional(readOnly=false)
	public void delete(Izin izin) throws EntityException, GeneralException {
		interceptor.notNull(izin);
		dao.delete(izin);
	}

	public Izin get(IzinPK pk)  throws EntityException, GeneralException {
		interceptor.notNull(pk);
		return dao.get(pk);
	}

	public List<Izin> getAll() throws EntityException {
		return dao.getAll();
	}

	public List<Izin> getByPegawai(String nip)  throws EntityException, GeneralException {
		interceptor.notNull(nip);
		return dao.getByPegawai(nip);
	}

	public List<Izin> getByPegawai(String nip, int bulan, int tahun) throws EntityException, GeneralException {
		interceptor.notNull(nip, bulan, tahun);

		Date tanggalMulai = new DateTime(tahun, bulan, 1).getUtilDate();
		Date tanggalSelesai = new DateTime(tahun, bulan, 31).getUtilDate();

		return dao.getByPegawai(nip, tanggalMulai, tanggalSelesai);
	}
	
	public List<Izin> getByTanggal(Date tanggal)  throws EntityException, GeneralException {
		interceptor.notNull(tanggal);
		return dao.getByTanggal(tanggal);
	}

	public List<Izin> getByDepartment(String department) throws EntityException, GeneralException {
		interceptor.notNull(department);
		return dao.getByDepartment(department);
	}

	public List<Izin> getByDepartment(String department, Date date) throws EntityException, GeneralException {
		interceptor.notNull(department, date);
		return dao.getByDepartment(department, date);
	}

	public List<Izin> getByDepartment(String department, int bulan, int tahun) throws EntityException, GeneralException {
		interceptor.notNull(department, bulan, tahun);

		Date tanggalMulai = new DateTime(tahun, bulan, 1).getUtilDate();
		Date tanggalSelesai = new DateTime(tahun, bulan, 31).getUtilDate();
		
		return dao.getByDepartment(department, tanggalMulai, tanggalSelesai);
	}

	@Transactional(readOnly=false)
	private void createIzin(Presensi presensi, int index) throws EntityException, GeneralException {
		interceptor.notNull(presensi, index);

		Izin izin = new Izin();
        IzinPK izinPk = new IzinPK(presensi.getPegawai().getNip(), new DateTime().getUtilDate(), index++);
        izin.setId(izinPk);
        izin.setKeluar(new DateTime().getTime());
        presensi.addIzin(izin);
        
        save(izin);
        presensiDao.save(presensi);
    }

	@Deprecated
	@Transactional(readOnly=false)
	public String daftar(String nip, String clientCode) throws EntityException, GeneralException {
		interceptor.mustPresent(nip, clientCode);

		PrimaryKey pk = new PrimaryKey(nip, new DateTime().getUtilDate());
		Presensi presensi = presensiDao.get(pk);

		List<Izin> lsIzin = presensi.getIzins();
		if((lsIzin != null) && (lsIzin.size() > 0)) {
			int index = lsIzin.size();
			
			Izin izin = lsIzin.get(index-1);
			if(izin.getMasuk() == null) {
				izin.setMasuk(new DateTime().getTime());
				save(izin);
				return "Selamat datang";
			} else if(index >= lsIzin.size()) {
				this.createIzin(presensi, index);
			}
		} else {
			this.createIzin(presensi, 0);
		}

		return "Jangan lupa kembali";
	}

	@Transactional(readOnly=false)
	public String masuk(String nip, String clientCode) throws GeneralException, EntityException {
		interceptor.mustPresent(nip, clientCode);

		PrimaryKey pk = new PrimaryKey(nip, new DateTime().getUtilDate());
		Presensi presensi = presensiDao.get(pk);
		String returnMessage = "";

		List<Izin> lsIzin = presensi.getIzins();
		if((lsIzin != null) && (lsIzin.size() > 0)) {
			int index = lsIzin.size();
			
			Izin izin = lsIzin.get(index-1);
			if(izin.getMasuk() == null) {
				izin.setMasuk(new DateTime().getTime());
				save(izin);
				returnMessage = "Selamat datang";
			} else {
				returnMessage = "Anda tidak minta izin keluar";
			}
		} else {
			returnMessage = "Anda tidak minta izin keluar";
		}

		return returnMessage;
	}

	@Transactional(readOnly=false)
	public String keluar(String nip, String clientCode) throws GeneralException, EntityException {
		interceptor.mustPresent(nip, clientCode);

		PrimaryKey pk = new PrimaryKey(nip, new DateTime().getUtilDate());
		Presensi presensi = presensiDao.get(pk);
		createIzin(presensi, 0);

		return "Jangan lupa kembali";
	}

	public Izin[] toArray(List<Izin> ls) {
		Izin[] arr = new Izin[ls.size()];
		int counter = 0;
		
		for(Izin s : ls) {
			arr[counter] = (Izin)s;
			counter++;
		}
		return arr;
	}

}
