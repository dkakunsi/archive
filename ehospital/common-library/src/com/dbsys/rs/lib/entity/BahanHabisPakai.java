package com.dbsys.rs.lib.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BHP")
public class BahanHabisPakai extends Barang {

	public BahanHabisPakai() {
		super("BHP");
	}
}
