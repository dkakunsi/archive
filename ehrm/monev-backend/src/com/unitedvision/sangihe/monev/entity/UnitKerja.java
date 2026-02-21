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

@Entity
@Table(name = "unit_kerja")
public class UnitKerja {

	public enum TipeUnitKerja {
		SEKRETARIAT,
		DINAS,
		BADAN,
		UPT,
		BIDANG,
		BAGIAN,
		SEKSI,
		KEASISTENAN
	}
	
	private long id;
	private String nama;
	private TipeUnitKerja tipe;
	private String singkatan;
	private UnitKerja parent;

	public UnitKerja() {
		super();
	}
	
	public UnitKerja(UnitKerja unitKerja) {
		this();
		setParent(unitKerja);
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent")
	public UnitKerja getParent() {
		return parent;
	}

	public void setParent(UnitKerja parent) {
		this.parent = parent;
	}

	@Transient
	public Long getIdParent() {
		if (parent == null)
			return 0L;
		return parent.getId();
	}
	
	public void setIdParent(Long idParent) {
		if (parent == null)
			parent = new UnitKerja();
		parent.setId(idParent);
	}
	
	@Column(name = "nama", nullable = false)
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "tipe", nullable = false)
	public TipeUnitKerja getTipe() {
		return tipe;
	}

	public void setTipe(TipeUnitKerja tipe) {
		this.tipe = tipe;
	}

	@Column(name = "singkatan")
	public String getSingkatan() {
		return singkatan;
	}

	public void setSingkatan(String singkatan) {
		this.singkatan = singkatan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result
				+ ((singkatan == null) ? 0 : singkatan.hashCode());
		result = prime * result + ((tipe == null) ? 0 : tipe.hashCode());
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
		UnitKerja other = (UnitKerja) obj;
		if (id != other.id)
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (singkatan == null) {
			if (other.singkatan != null)
				return false;
		} else if (!singkatan.equals(other.singkatan))
			return false;
		if (tipe != other.tipe)
			return false;
		return true;
	}

}
