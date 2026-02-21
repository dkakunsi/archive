package edu.dkakunsi.ta.presensi.model.rekapmodel;

import java.io.InputStream;

import edu.dkakunsi.ta.presensi.model.entity.Pegawai;

public class RekapPresensi extends Rekap {

	public RekapPresensi() { }
	
	public RekapPresensi(Pegawai pegawai) {
		this.setNip(pegawai.getNip());
		this.setNama(pegawai.getNama());
		this.setGolongan(pegawai.getGolongan());
		this.setSkpd(pegawai.getDepartment().getNama());
	}

	public RekapPresensi(Pegawai pegawai, int jumlah) {
		this.setNip(pegawai.getNip());
		this.setNama(pegawai.getNama());
		this.setGolongan(pegawai.getGolongan());
		this.setSkpd(pegawai.getDepartment().getNama());
		this.setJumlah(jumlah);
	}

	public RekapPresensi(String nip, String nama, String golongan, String skpd, int jumlahPresensi) {
		this.setNip(nip);
		this.setNama(nama);
		this.setGolongan(golongan);
		this.setSkpd(skpd);
		this.setJumlah(jumlahPresensi);
	}
	
	public static InputStream getRekap() {
		return RekapPresensi.class.getResourceAsStream("/edu/dkakunsi/ta/presensi/report/Presensi.jasper");
	}

}
