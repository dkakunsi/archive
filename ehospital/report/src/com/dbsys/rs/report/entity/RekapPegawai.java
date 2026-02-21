package com.dbsys.rs.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class RekapPegawai {

    private Long id;
    private String nama;
    private String tindakan;
    private Long tarif;
    private Long jumlah;
    private Long total;

    public RekapPegawai() {
		super();
	}

    @Id
    @Column
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @Column
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
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

	@Transient
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
