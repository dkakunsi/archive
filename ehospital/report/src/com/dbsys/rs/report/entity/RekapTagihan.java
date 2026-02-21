package com.dbsys.rs.report.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.dbsys.rs.lib.Penanggung;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "tipeTagihan"
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = RekapTagihanPelayanan.class, name = "PELAYANAN"),
	@JsonSubTypes.Type(value = RekapTagihanPemakaian.class, name = "PEMAKAIAN"),
	@JsonSubTypes.Type(value = RekapKembali.class, name = "KEMBALI")
})
@MappedSuperclass
public class RekapTagihan {

	private Long id;
	private String pasien;
	private String nama;
	private Penanggung penanggung;
	private Long jumlah;
	private Long tarif;
	private Long tambahan;
	
	private String tipeTagihan;
	
	public RekapTagihan() {
		super();
	}

	@Transient
	public String getTipeTagihan() {
		return tipeTagihan;
	}

	public void setTipeTagihan(String tipeTagihan) {
		this.tipeTagihan = tipeTagihan;
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
	public String getPasien() {
		return pasien;
	}

	public void setPasien(String pasien) {
		this.pasien = pasien;
	}

	@Column
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column
	public Penanggung getPenanggung() {
		return penanggung;
	}

	public void setPenanggung(Penanggung penanggung) {
		this.penanggung = penanggung;
	}

	@Column
	public Long getJumlah() {
		return jumlah;
	}

	public void setJumlah(Long jumlah) {
		this.jumlah = jumlah;
	}

	@Column
	public Long getTarif() {
		return tarif;
	}

	public void setTarif(Long tarif) {
		this.tarif = tarif;
	}

	@Column
	public Long getTambahan() {
		return tambahan;
	}

	public void setTambahan(Long tambahan) {
		this.tambahan = tambahan;
	}
}
