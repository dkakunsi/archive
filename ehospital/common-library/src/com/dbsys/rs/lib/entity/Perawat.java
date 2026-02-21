package com.dbsys.rs.lib.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PERAWAT")
public class Perawat extends Pegawai {
	
	public Perawat() {
		super("PERAWAT");
	}
}
