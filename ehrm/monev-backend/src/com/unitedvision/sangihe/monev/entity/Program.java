package com.unitedvision.sangihe.monev.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "program")
public class Program {

	private Long id;
	private String nama;
	private Integer tahunAwal;
	private Integer tahunAkhir;

	private UnitKerja unitKerja;
	private Program parent;
	
	public Program() {
		super();
	}

	public Program(UnitKerja unitKerja) {
		super();
		setUnitKerja(unitKerja);
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nama", nullable = false)
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "tahun_awal", nullable = false)
	public Integer getTahunAwal() {
		return tahunAwal;
	}

	public void setTahunAwal(Integer tahunAwal) {
		this.tahunAwal = tahunAwal;
	}

	@Column(name = "tahun_akhir", nullable = false)
	public Integer getTahunAkhir() {
		return tahunAkhir;
	}

	public void setTahunAkhir(Integer tahunAkhir) {
		this.tahunAkhir = tahunAkhir;
	}

	@JsonBackReference("program_satker")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_kerja", nullable = false)
	public UnitKerja getUnitKerja() {
		return unitKerja;
	}
	
	@Transient
	public String getNamaUnitKerja() {
		return unitKerja.getNama();
	}
	
	public void setNamaUnitKerja(String namaUnitKerja) {}

	public void setUnitKerja(UnitKerja unitKerja) {
		this.unitKerja = unitKerja;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent")
	public Program getParent() {
		return parent;
	}

	public void setParent(Program parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result
				+ ((tahunAkhir == null) ? 0 : tahunAkhir.hashCode());
		result = prime * result
				+ ((tahunAwal == null) ? 0 : tahunAwal.hashCode());
		result = prime * result
				+ ((unitKerja == null) ? 0 : unitKerja.hashCode());
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
		Program other = (Program) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (tahunAkhir == null) {
			if (other.tahunAkhir != null)
				return false;
		} else if (!tahunAkhir.equals(other.tahunAkhir))
			return false;
		if (tahunAwal == null) {
			if (other.tahunAwal != null)
				return false;
		} else if (!tahunAwal.equals(other.tahunAwal))
			return false;
		if (unitKerja == null) {
			if (other.unitKerja != null)
				return false;
		} else if (!unitKerja.equals(other.unitKerja))
			return false;
		return true;
	}
}
