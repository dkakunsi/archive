package com.unitedvision.sangihe.monev.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RekapProgram {

	private String namaUnitKerja;
	private String namaProgram;
	private Long paguAnggaran;
	private Long realisasiAnggaran;
	private Integer realisasiFisik;
	
	public RekapProgram() {
		super();
	}

	@Column(name = "unit_kerja")
	public String getNamaUnitKerja() {
		return namaUnitKerja;
	}

	public void setNamaUnitKerja(String namaUnitKerja) {
		this.namaUnitKerja = namaUnitKerja;
	}

	@Id
	@Column(name = "program")
	public String getNamaProgram() {
		return namaProgram;
	}

	public void setNamaProgram(String namaProgram) {
		this.namaProgram = namaProgram;
	}

	@Column(name = "pagu_anggaran")
	public Long getPaguAnggaran() {
		return paguAnggaran;
	}

	public void setPaguAnggaran(Long paguAnggaran) {
		this.paguAnggaran = paguAnggaran;
	}

	@Column(name = "realisasi_anggaran")
	public Long getRealisasiAnggaran() {
		return realisasiAnggaran;
	}

	public void setRealisasiAnggaran(Long realisasiAnggaran) {
		this.realisasiAnggaran = realisasiAnggaran;
	}

	@Column(name = "realisasi_fisik")
	public Integer getRealisasiFisik() {
		return realisasiFisik;
	}

	public void setRealisasiFisik(Integer realisasiFisik) {
		this.realisasiFisik = realisasiFisik;
	}

	@Override
	public String toString() {
		return "RekapProgram [namaUnitKerja=" + namaUnitKerja
				+ ", namaProgram=" + namaProgram + ", paguAnggaran="
				+ paguAnggaran + ", realisasiAnggaran=" + realisasiAnggaran
				+ ", realisasiFisik=" + realisasiFisik + "]";
	}
}
