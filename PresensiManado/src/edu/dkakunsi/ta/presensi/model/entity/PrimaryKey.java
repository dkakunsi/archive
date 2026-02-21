package edu.dkakunsi.ta.presensi.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class PrimaryKey implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	protected String nip;
	protected Date tanggal;

	public PrimaryKey() { }
	
	public PrimaryKey(String nip, Date tanggal) {
		this.nip = nip;
		this.tanggal = tanggal;
	}

	@Column(name="nip", unique=true, nullable=false, length=50)
	public String getNip() {
		return this.nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="tanggal", unique=true, nullable=false)
	public Date getTanggal() {
		return this.tanggal;
	}
	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PrimaryKey)) {
			return false;
		}
		PrimaryKey castOther = (PrimaryKey)other;
		return 
			this.nip.equals(castOther.nip)
			&& this.tanggal.equals(castOther.tanggal);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.nip.hashCode();
		hash = hash * prime + this.tanggal.hashCode();
		
		return hash;
	}
	
	public String toString() {
		return nip + " & " + tanggal.toString();
	}

}
