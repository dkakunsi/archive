package com.dbsys.rs.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class RekapStokBarang {

	private String nama;
	private Long jumlahMasuk;
	private Long jumlahKeluar;
	private Long jumlahKembali;
	private Long jumlahSupply;
	private Long jumlahResep;
	
	private Long jumlahPemakaian;

	@Id
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "jumlah_masuk")
	public Long getJumlahMasuk() {
		return jumlahMasuk;
	}

	public void setJumlahMasuk(Long jumlahMasuk) {
		this.jumlahMasuk = jumlahMasuk;
	}

	@Column(name = "jumlah_keluar")
	public Long getJumlahKeluar() {
		return jumlahKeluar;
	}

	public void setJumlahKeluar(Long jumlahKeluar) {
		this.jumlahKeluar = jumlahKeluar;
	}

	@Column(name = "jumlah_kembali")
	public Long getJumlahKembali() {
		return jumlahKembali;
	}

	public void setJumlahKembali(Long jumlahKembali) {
		this.jumlahKembali = jumlahKembali;
	}

	@Column(name = "jumlah_supply")
	public Long getJumlahSupply() {
		return jumlahSupply;
	}

	public void setJumlahSupply(Long jumlahSupply) {
		this.jumlahSupply = jumlahSupply;
	}

	@Column(name = "jumlah_resep")
	public Long getJumlahResep() {
		return jumlahResep;
	}

	public void setJumlahResep(Long jumlahResep) {
		this.jumlahResep = jumlahResep;
	}

	@Transient
	public Long getJumlahPemakaian() {
		return jumlahPemakaian;
	}

	public void setJumlahPemakaian(Long jumlahPemakaian) {
		this.jumlahPemakaian = jumlahPemakaian;
	}
}
