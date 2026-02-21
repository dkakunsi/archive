package com.dbsys.rs.report.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class RekapKembali extends RekapTagihan {

	@Override
	@Transient
	public Long getTambahan() {
		return super.getTambahan();
	}
	
	@Override
	public void setTambahan(Long tambahan) {
		super.setTambahan(tambahan);
	}
}
