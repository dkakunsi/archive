package com.dbsys.rs.lib.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("APOTEKER")
public class Apoteker extends Pegawai {
	
	public Apoteker() {
		super("APOTEKER");
	}
}
