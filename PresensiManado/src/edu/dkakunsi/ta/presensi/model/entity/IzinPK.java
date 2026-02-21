package edu.dkakunsi.ta.presensi.model.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * The primary key class for the izin database table.
 * 
 */
@Embeddable
public class IzinPK extends PrimaryKey {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nomor;

	public IzinPK() {}
	
	public IzinPK(String nip, Date tanggal, int nomor) {
		this.nip = nip;
		this.tanggal = tanggal;
		this.nomor = nomor;
	}

	@Override
	@Column(name="nip", unique=true, nullable=false, length=50)
	public String getNip() {
		return this.nip;
	}
	@Override
	public void setNip(String nip) {
		this.nip = nip;
	}

	@Override
	@Temporal(TemporalType.DATE)
	@Column(name="tanggal", unique=true, nullable=false)
	public Date getTanggal() {
		return this.tanggal;
	}
	@Override
	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	@Column(unique=true, nullable=false)
	public int getNomor() {
		return this.nomor;
	}
	public void setNomor(int nomor) {
		this.nomor = nomor;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IzinPK)) {
			return false;
		}
		IzinPK castOther = (IzinPK)other;
		return 
			this.nip.equals(castOther.nip)
			&& this.tanggal.equals(castOther.tanggal)
			&& (this.nomor == castOther.nomor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.nip.hashCode();
		hash = hash * prime + this.tanggal.hashCode();
		hash = hash * prime + this.nomor;
		
		return hash;
	}
}