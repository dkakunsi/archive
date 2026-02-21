package com.dbsys.rs.lib.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EKSTERNAL")
public class StokEksternal extends Stok {

	public StokEksternal() {
		super("EKSTERNAL");
	}
}
