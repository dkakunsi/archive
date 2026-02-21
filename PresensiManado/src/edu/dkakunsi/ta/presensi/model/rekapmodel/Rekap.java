package edu.dkakunsi.ta.presensi.model.rekapmodel;

public abstract class Rekap {
	protected String nip;
	protected String nama;
	protected String golongan;
	protected String skpd;
	protected int jumlah;
	protected int bulan;
	protected int tahun;
	
	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getGolongan() {
		return golongan;
	}

	public void setGolongan(String golongan) {
		this.golongan = golongan;
	}

	public String getSkpd() {
		return skpd;
	}

	public void setSkpd(String skpd) {
		this.skpd = skpd;
	}	

	public int getJumlah() {
		return jumlah;
	}

	public void setJumlah(int jumlah) {
		this.jumlah = jumlah;
	}

	public int getBulan() {
		return bulan;
	}

	public void setBulan(int bulan) {
		this.bulan = bulan;
	}	

	public int getTahun() {
		return tahun;
	}

	public void setTahun(int tahun) {
		this.tahun = tahun;
	}	
}
