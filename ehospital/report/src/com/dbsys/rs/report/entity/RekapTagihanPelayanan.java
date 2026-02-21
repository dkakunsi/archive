package com.dbsys.rs.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.dbsys.rs.lib.entity.Unit.TipeUnit;

@Entity
public class RekapTagihanPelayanan extends RekapTagihan {

	private String unit;
	private TipeUnit tipe;

	public RekapTagihanPelayanan() {
		super();
	}

	@Column
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "tipe_unit")
	public TipeUnit getTipe() {
		return tipe;
	}

	public void setTipe(TipeUnit tipe) {
		this.tipe = tipe;
	}

}
