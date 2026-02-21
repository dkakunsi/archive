package com.dbsys.rs.lib.entity;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("INTERNAL")
public class StokInternal extends Stok {
	
	private Unit unit;
	
	public StokInternal() {
		super("INTERNAL");
		setJenis(JenisStok.KELUAR);
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "unit")
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	@Override
	public void setJenis(JenisStok jenis) {
		jenis = JenisStok.KELUAR;
		super.setJenis(jenis);
	};

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StokInternal other = (StokInternal) obj;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}
}
