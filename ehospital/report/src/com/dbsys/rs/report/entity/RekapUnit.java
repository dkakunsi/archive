package com.dbsys.rs.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RekapUnit {

	private String nama;
	private Long jumlah;
	private Long total;

	@Id
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}
	
	public void setNama(String nama) {
		this.nama = nama;
	}
	
	@Column(name = "jumlah")
	public Long getJumlah() {
		return jumlah;
	}
	
	public void setJumlah(Long jumlah) {
		this.jumlah = jumlah;
	}
	
	@Column(name = "total")
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}
}
