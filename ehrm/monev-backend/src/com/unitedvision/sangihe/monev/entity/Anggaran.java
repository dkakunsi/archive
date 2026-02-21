package com.unitedvision.sangihe.monev.entity;

import java.time.Month;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unitedvision.sangihe.monev.exception.AnggaranException;
import com.unitedvision.sangihe.monev.exception.WrongYearException;

@Entity
@Table(name = "anggaran")
public class Anggaran {

	private Long id;
	private Integer tahun;
	private Month bulan;
	private Long rencana;
	private Long realisasi;
	
	private Kegiatan kegiatan;
	
	public Anggaran() {
		super();
		setRencana(0L);
		setRealisasi(0L);
	}

	public Anggaran(Kegiatan kegiatan) {
		this();
		setKegiatan(kegiatan);
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "tahun", nullable = false)
	public Integer getTahun() {
		return tahun;
	}

	public void setTahun(Integer tahun) {
		this.tahun = tahun;
	}

	@Column(name = "bulan", nullable = false)
	public Month getBulan() {
		return bulan;
	}

	public void setBulan(Month bulan) {
		this.bulan = bulan;
	}

	@Column(name = "rencana", nullable = false)
	public Long getRencana() {
		return rencana;
	}

	public void setRencana(Long rencana) {
		this.rencana = rencana;
	}

	@Column(name = "realisasi", nullable = false)
	public Long getRealisasi() {
		return realisasi;
	}

	public void setRealisasi(Long realisasi) {
		this.realisasi = realisasi;
	}

	@JsonBackReference("anggaran_kegiatan")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "kegiatan", nullable = false)
	public Kegiatan getKegiatan() {
		return kegiatan;
	}

	public void setKegiatan(Kegiatan kegiatan) {
		this.kegiatan = kegiatan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bulan == null) ? 0 : bulan.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kegiatan == null) ? 0 : kegiatan.hashCode());
		result = prime * result
				+ ((realisasi == null) ? 0 : realisasi.hashCode());
		result = prime * result + ((rencana == null) ? 0 : rencana.hashCode());
		result = prime * result + ((tahun == null) ? 0 : tahun.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anggaran other = (Anggaran) obj;
		if (bulan != other.bulan)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kegiatan == null) {
			if (other.kegiatan != null)
				return false;
		} else if (!kegiatan.equals(other.kegiatan))
			return false;
		if (realisasi == null) {
			if (other.realisasi != null)
				return false;
		} else if (!realisasi.equals(other.realisasi))
			return false;
		if (rencana == null) {
			if (other.rencana != null)
				return false;
		} else if (!rencana.equals(other.rencana))
			return false;
		if (tahun == null) {
			if (other.tahun != null)
				return false;
		} else if (!tahun.equals(other.tahun))
			return false;
		return true;
	}

	public void validate() throws AnggaranException, WrongYearException {
		if (kegiatan == null)
			throw new AnggaranException("Kegiatan belum dipilih");

		if (tahun < kegiatan.getTahunAwal() || tahun > kegiatan.getTahunAkhir())
			throw new WrongYearException("Tahun Anggaran tidak termasuk dalam jangkauan tahun program");

		if (rencana > kegiatan.getPaguAnggaran())
			throw new AnggaranException("Rencana anggaran melebihi anggaran kegiatan");
		
		if (realisasi > kegiatan.getPaguAnggaran())
			throw new AnggaranException("Realisasi anggaran melebihi anggaran kegiatan");
	}
}
