package edu.dkakunsi.ta.presensi.model.rekapmodel;

import org.springframework.beans.factory.annotation.Autowired;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Pegawai;
import edu.dkakunsi.ta.presensi.service.IzinService;

public class RekapIzin extends Rekap {
	@Autowired private IzinService service;

	public RekapIzin() {}
	
	public RekapIzin(String nip, String nama, String golongan, String skpd) {
		this.setNip(nip);
		this.setNama(nama);
		this.setGolongan(golongan);
		this.setSkpd(skpd);
	}
	
	public RekapIzin(Pegawai pegawai) {
		this.setNip(pegawai.getNip());
		this.setNama(pegawai.getNama());
		this.setGolongan(pegawai.getGolongan());
		this.setSkpd(pegawai.getDepartment().getNama());
	}

	public RekapIzin(Pegawai pegawai, int bulan, int tahun) {
		this.setNip(pegawai.getNip());
		this.setNama(pegawai.getNama());
		this.setGolongan(pegawai.getGolongan());
		this.setSkpd(pegawai.getDepartment().getNama());
		this.setBulan(bulan);
		this.setTahun(tahun);
		
		try {
			this.setJumlah(service.getByPegawai(nip, bulan, tahun).size());
		} catch (EntityException | GeneralException e) {
			this.setJumlah(0);
		}
	}
}
