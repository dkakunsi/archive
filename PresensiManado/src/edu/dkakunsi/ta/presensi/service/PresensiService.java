package edu.dkakunsi.ta.presensi.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.dkakunsi.ta.presensi.dao.IzinDao;
import edu.dkakunsi.ta.presensi.dao.LemburDao;
import edu.dkakunsi.ta.presensi.dao.PresensiDao;
import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.exception.PulangException;
import edu.dkakunsi.ta.presensi.model.entity.Izin;
import edu.dkakunsi.ta.presensi.model.entity.Lembur;
import edu.dkakunsi.ta.presensi.model.entity.Pegawai;
import edu.dkakunsi.ta.presensi.model.entity.Presensi;
import edu.dkakunsi.ta.presensi.model.entity.PrimaryKey;
import edu.dkakunsi.ta.presensi.model.rekapmodel.Rekap;
import edu.dkakunsi.ta.presensi.model.rekapmodel.RekapPresensi;
import edu.dkakunsi.ta.presensi.service.interceptor.Interceptor;
import edu.dkakunsi.ta.presensi.util.DateTime;
import edu.dkakunsi.ta.presensi.util.TimeChecker;

@Service("presensiService")
@Transactional(readOnly=true)
public class PresensiService {
	@Autowired private PresensiDao dao;
	@Autowired private LemburDao lemburDao;
	@Autowired private IzinDao izinDao;
	@Autowired PegawaiService pegawaiService;
	@Autowired private Interceptor interceptor;

	@Transactional(readOnly=false)
	public void save(Presensi presensi) 
			throws EntityException, GeneralException {
		interceptor.notNull(presensi);
		dao.save(presensi);
	}

	@Transactional(readOnly=false)
	public void delete(Presensi presensi) 
			throws EntityException, GeneralException {
		interceptor.notNull(presensi);
		dao.delete(presensi);
	}

	public Presensi get(PrimaryKey pk) 
			throws EntityException, GeneralException {
		interceptor.notNull(pk);
		return dao.get(pk);
	}

	public List<Presensi> getAll() throws EntityException {
		return dao.getAll();
	}

	public List<Presensi> getByPegawai(String nip) 
			throws EntityException, GeneralException {
		interceptor.notNull(nip);
		return dao.getByPegawai(nip);
	}

	public List<Presensi> getByPegawai(String nip, int bulan, int tahun)
			throws EntityException, GeneralException {
		interceptor.notNull(nip, bulan, tahun);

		Date tanggalMulai = new DateTime(tahun, bulan, 1).getUtilDate();
		Date tanggalSelesai = new DateTime(tahun, bulan, 31).getUtilDate();

		return dao.getByPegawai(nip, tanggalMulai, tanggalSelesai);
	}

	public List<Presensi> getByTanggal(Date tanggal) 
			throws EntityException, GeneralException {
		interceptor.notNull(tanggal);
		return dao.getByTanggal(tanggal);
	}

	public List<Presensi> getByDepartment(String department)
			throws EntityException, GeneralException {
		interceptor.notNull(department);
		return dao.getByDepartment(department);
	}

	public List<Presensi> getByDepartment(String department, Date tanggal)
			throws EntityException, GeneralException {
		interceptor.notNull(department, tanggal);
		return dao.getByDepartment(department, tanggal);
	}
	
	public List<Presensi> getByDepartment(String department, int bulan, int tahun)
			throws EntityException, GeneralException {
		interceptor.notNull(department, bulan, tahun);

		Date tanggalMulai = new DateTime(tahun, bulan, 1).getUtilDate();
		Date tanggalSelesai = new DateTime(tahun, bulan, 31).getUtilDate();

		return dao.getByDepartment(department, tanggalMulai, tanggalSelesai);
	}

	@Deprecated
	@Transactional(readOnly=false)
	public String daftar(String nip, String clientCode)  throws EntityException, GeneralException {
		interceptor.validasi(nip, clientCode);

		String msg = "";

		try {
			msg = masuk(nip, clientCode);
		} catch (EntityException e) {
			msg = keluar(nip, clientCode);
		}
		
		return msg;
	}

	@Transactional(readOnly=false)
	public String masuk(String nip, String clientCode) throws GeneralException, EntityException {
		interceptor.validasi(nip, clientCode);

		Time waktuDatang = new DateTime().getTime();
		String keterangan = TimeChecker.checkWaktuDatang(waktuDatang);

		Presensi presensi = new Presensi();
		PrimaryKey primaryKey = new PrimaryKey(nip, new DateTime().getUtilDate());
		presensi.setId(primaryKey);
		presensi.setMasuk(waktuDatang);
		presensi.setKeterangan(keterangan);
		
		save(presensi);
		return keterangan;
	}

	@Transactional(readOnly=false)
	public String keluar(String nip, String clientCode) throws GeneralException, EntityException {
		interceptor.validasi(nip, clientCode);

		Time waktuPulang = new DateTime().getTime();
		String keterangan;
		try {
			keterangan = TimeChecker.checkWaktuPulang(waktuPulang);
		} catch (PulangException e) {
			keterangan = "Pulang lebih awal";
		}

		Presensi presensi = get(new PrimaryKey(nip, new DateTime().getUtilDate()));
		Lembur lembur = presensi.getLembur();
		
		if(lembur != null) {
			int jumlahLembur = closeLembur(lembur, waktuPulang);
			keterangan = keterangan + " Anda lembur " + jumlahLembur + " jam\nHati-hati di jalan!" ;
		}
		presensi.setKeluar(waktuPulang);
		presensi.setKeterangan(presensi.getKeterangan() + ";" +keterangan);

		save(presensi);
		return keterangan;
	}
	
	@Transactional(readOnly=false)
	public void closeHarian() throws EntityException, GeneralException {
		List<Presensi> lsPresensi = dao.getAllEmptyPulang();
		
		for(Presensi p : lsPresensi) {
			if(p.getLembur() == null) {
				List<Izin> lsIzin = p.getIzins();
				
				if((lsIzin!=null) && (lsIzin.size()>0)) {
					Izin i = lsIzin.get(lsIzin.size()-1);
					
					if(i.getMasuk() == null) {
						p.setKeluar(i.getKeluar());
						i.setKeterangan("; tidak kembali");
						izinDao.save(i);
					} else {
						
						p.setKeterangan("; tidak mengisi absen pulang");
					}					
				} else {
					p.setKeterangan("; tidak mengisi absen pulang");
				}
				
				dao.save(p);
			}
		}
	}

	@Transactional(readOnly=false)
	private int closeLembur(Lembur lembur, Time waktuPulang) throws EntityException, GeneralException {
		interceptor.notNull(lembur, waktuPulang);

		long time = waktuPulang.getTime() - TimeChecker.getWaktuPulangInMillis();
		int jumlah = (int)(time/1000) / 3600;
		lembur.setJumlah(jumlah);
		
		lemburDao.save(lembur);

		return jumlah;
	}
	
	public List<Rekap> rekap(String dept, DateTime dt)
			throws EntityException, GeneralException {
		List<Rekap> ls = new ArrayList<>();
		for(Pegawai p : pegawaiService.getByDepartment(dept)) {
			Rekap rp = new RekapPresensi(p, getByPegawai(p.getNip(), dt.getBulan(), dt.getTahun()).size());
			ls.add(rp);
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

	public Presensi[] toArray(List<Presensi> ls) {
		Presensi[] arr = new Presensi[ls.size()];
		int counter = 0;
		
		for(Presensi s : ls) {
			arr[counter] = (Presensi)s;
			counter++;
		}
		return arr;
	}
}
