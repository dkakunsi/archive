package com.unitedvision.sangihe.monev.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class RekapKegiatan {

	private String unitKerja;
	private String program;
	private String subProgram;
	private String kegiatan;
	private Long paguAnggaran;
	private Long rencanaAnggaran;
	private Long realisasiAnggaran;
	private Integer realisasiFisik;
	
	public RekapKegiatan() {
		super();
	}

	@Column(name = "unit_kerja")
	public String getUnitKerja() {
		return unitKerja;
	}

	public void setUnitKerja(String unitKerja) {
		this.unitKerja = unitKerja;
	}

	@Column(name = "program")
	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	@Column(name = "sub_program")
	public String getSubProgram() {
		return subProgram;
	}

	public void setSubProgram(String subProgram) {
		this.subProgram = subProgram;
	}

	@Id
	@Column(name = "kegiatan")
	public String getKegiatan() {
		return kegiatan;
	}

	public void setKegiatan(String kegiatan) {
		this.kegiatan = kegiatan;
	}

	@Column(name = "pagu_anggaran")
	public Long getPaguAnggaran() {
		return paguAnggaran;
	}

	public void setPaguAnggaran(Long paguAnggaran) {
		this.paguAnggaran = paguAnggaran;
	}

	@Column(name = "rencana_anggaran")
	public Long getRencanaAnggaran() {
		return rencanaAnggaran;
	}

	public void setRencanaAnggaran(Long rencanaAnggaran) {
		this.rencanaAnggaran = rencanaAnggaran;
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
	
	@Transient
	public Long getDeviasiAnggaran() {
		if (rencanaAnggaran == null)
			rencanaAnggaran = 0L;
		if (realisasiAnggaran == null)
			realisasiAnggaran = 0L;
		
		return rencanaAnggaran - realisasiAnggaran;
	}

	@Override
	public String toString() {
		return "RekapKegiatan ["
				+ "unitKerja=" + unitKerja
				+ ", program=" + program
				+ ", subProgram=" + subProgram
				+ ", kegiatan=" + kegiatan
				+ ", paguAnggaran=" + paguAnggaran
				+ ", rencanaAnggaran=" + rencanaAnggaran
				+ ", realisasiAnggaran=" + realisasiAnggaran
				+ ", realisasiFisik=" + realisasiFisik
				+ "]";
	}
}
