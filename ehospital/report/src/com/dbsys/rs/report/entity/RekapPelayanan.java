package com.dbsys.rs.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class RekapPelayanan {

	private Long id;
	private String unit;
	private String tindakan;
	private Long tarif;
	private Long jumlah;
	private Long tambahan;
	
	// tidak persistent
	private Long total;

	@Id
	@Column
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column
	public String getTindakan() {
		return tindakan;
	}

	public void setTindakan(String tindakan) {
		this.tindakan = tindakan;
	}

	@Column
	public Long getTarif() {
		return tarif;
	}

	public void setTarif(Long tarif) {
		this.tarif = tarif;
	}

	@Column
	public Long getJumlah() {
		return jumlah;
	}

	public void setJumlah(Long jumlah) {
		this.jumlah = jumlah;
	}

	@Column
	public Long getTambahan() {
		return tambahan;
	}

	public void setTambahan(Long tambahan) {
		this.tambahan = tambahan;
	}

	@Transient
	public Long getTotal() {
		hitungTotal();
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
		hitungTotal();
	}
	
	private void hitungTotal() {
		total = jumlah * tarif + tambahan;
	}
}
