package edu.dkakunsi.ta.presensi.model.rekapmodel;

import java.io.InputStream;
import java.util.Date;

import edu.dkakunsi.ta.presensi.model.entity.Pegawai;

public class RekapLembur extends Rekap {
	
	public RekapLembur() {}
	
	public RekapLembur(Pegawai pegawai) {
		this.setNip(pegawai.getNip());
		this.setNama(pegawai.getNama());
		this.setGolongan(pegawai.getGolongan());
		this.setSkpd(pegawai.getDepartment().getNama());
	}
	
	public RekapLembur(Pegawai pegawai, int jumlah) {
		this.setNip(pegawai.getNip());
		this.setNama(pegawai.getNama());
		this.setGolongan(pegawai.getGolongan());
		this.setSkpd(pegawai.getDepartment().getNama());
		this.setJumlah(jumlah);
	}

	public RekapLembur(Pegawai pegawai, int bulan, int tahun) {
		this.setNip(pegawai.getNip());
		this.setNama(pegawai.getNama());
		this.setGolongan(pegawai.getGolongan());
		this.setSkpd(pegawai.getDepartment().getNama());
		this.setBulan(bulan);
		this.setTahun(tahun);
		
		this.setJumlah(0);
	}

	public RekapLembur(String nip, String nama, String golongan, String skpd, Date tanggal, int bulan, int tahun) {
		this.setNip(nip);
		this.setNama(nama);
		this.setGolongan(golongan);
		this.setSkpd(skpd);
		this.setBulan(bulan);
		this.setTahun(tahun);
		
		this.setJumlah(0);
	}

	public RekapLembur(String nip, String nama, String golongan, String skpd, Date tanggal, int jumlah) {
		this.setNip(nip);
		this.setNama(nama);
		this.setGolongan(golongan);
		this.setSkpd(skpd);
		this.setJumlah(jumlah);
	}

	public static InputStream getRekap() {
		return RekapLembur.class.getResourceAsStream("/edu/dkakunsi/ta/presensi/report/Lembur.jasper");
	}
}
