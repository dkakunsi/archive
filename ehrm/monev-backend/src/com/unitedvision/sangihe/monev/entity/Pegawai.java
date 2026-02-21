package com.unitedvision.sangihe.monev.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.monev.util.DateUtil;

@Entity
@Table(name = "pegawai")
public class Pegawai {

	private long id;
	private String nip;
	private Penduduk penduduk;
	private UnitKerja unitKerja;
	private String password;
	
	private List<Operator> daftarOperator = new ArrayList<>();
	private List<Token> daftarToken = new ArrayList<>();
	private List<Operator> listOperator = new ArrayList<>();

	public Pegawai() {
		super();
		penduduk = new Penduduk();
	}

	public Pegawai(UnitKerja unitKerja) {
		this();
		setUnitKerja(unitKerja);
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "nip", unique = true, nullable = false)
	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "penduduk")
	public Penduduk getPenduduk() {
		return penduduk;
	}

	public void setPenduduk(Penduduk penduduk) {
		this.penduduk = penduduk;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_kerja", nullable = false)
	public UnitKerja getUnitKerja() {
		return unitKerja;
	}

	public void setUnitKerja(UnitKerja unitKerja) {
		this.unitKerja = unitKerja;
	}

	@JsonIgnore
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String getPasswordStr() {
		return "****";
	}
	
	public void setPasswordStr(String password) {
		setPassword(password);
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Operator> getDaftarOperator() {
		return daftarOperator;
	}

	public void setDaftarOperator(List<Operator> daftarOperator) {
		this.daftarOperator = daftarOperator;
	}

	@Transient
	public List<Operator> getListOperator() {
		return listOperator;
	}

	public void setListOperator(List<Operator> daftarOperator) {
		this.listOperator = daftarOperator;
		this.daftarOperator = daftarOperator;
	}

	public void addOperator(Operator operator) {
		operator.setPegawai(this);
		listOperator.add(operator);
		daftarOperator.add(operator);
	}

	public void removeOperator(Operator operator) {
		operator.setPegawai(null);
		listOperator.remove(operator);
		daftarOperator.remove(operator);
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Token> getDaftarToken() {
		return daftarToken;
	}

	public void setDaftarToken(List<Token> daftarToken) {
		this.daftarToken = daftarToken;
	}

	@JsonIgnore
	@Transient
	public String getNik() {
		return penduduk.getNik();
	}

	public void setNik(String nik) {
		penduduk.setNik(nik);
	}

	@JsonIgnore
	@Transient
	public String getNama() {
		return penduduk.getNama();
	}

	public void setNama(String nama) {
		penduduk.setNama(nama);
	}

	@JsonIgnore
	@Transient
	public Date getTanggalLahir() {
		return penduduk.getTanggalLahir();
	}

	public void setTanggalLahir(Date tanggalLahir) {
		penduduk.setTanggalLahir(tanggalLahir);
	}

	@JsonIgnore
	@Transient
	public String getTanggalLahirStr() {
		return DateUtil.toStringDate(getTanggalLahir(), "-");
	}

	public void setTanggalLahirStr(String tanggalLahir) {
		Date date = DateUtil.getDate(tanggalLahir, "-");
		
		setTanggalLahir(date);
	}
	
	@JsonIgnore
	@Transient
	public String getEmail() {
		return penduduk.getEmail();
	}

	public void setEmail(String email) {
		penduduk.setEmail(email);
	}

	@JsonIgnore
	@Transient
	public String getTelepon() {
		return penduduk.getTelepon();
	}

	public void setTelepon(String telepon) {
		penduduk.setTelepon(telepon);
	}
	
	@JsonIgnore
	@Transient
	public Long getIdPenduduk() {
		return penduduk.getId();
	}
	
	public void setIdPenduduk(Long idPenduduk) {
		penduduk.setId(idPenduduk);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nip == null) ? 0 : nip.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((penduduk == null) ? 0 : penduduk.hashCode());
		result = prime * result
				+ ((unitKerja == null) ? 0 : unitKerja.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pegawai other = (Pegawai) obj;
		if (daftarOperator == null) {
			if (other.daftarOperator != null)
				return false;
		} else if (!daftarOperator.equals(other.daftarOperator))
			return false;
		if (daftarToken == null) {
			if (other.daftarToken != null)
				return false;
		} else if (!daftarToken.equals(other.daftarToken))
			return false;
		if (id != other.id)
			return false;
		if (nip == null) {
			if (other.nip != null)
				return false;
		} else if (!nip.equals(other.nip))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (penduduk == null) {
			if (other.penduduk != null)
				return false;
		} else if (!penduduk.equals(other.penduduk))
			return false;
		if (unitKerja == null) {
			if (other.unitKerja != null)
				return false;
		} else if (!unitKerja.equals(other.unitKerja))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Pegawai [id=" + id + ", nip=" + nip + ", penduduk=" + penduduk
				+ ", unitKerja=" + unitKerja + ", password=" + password + "]";
	}
}
