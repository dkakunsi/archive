package com.dbsys.rs.lib.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.entity.Operator.Role;
import com.dbsys.rs.lib.entity.Unit.TipeUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representasi tabel token.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Entity
@Table(name = "token")
public class Token {

	public enum StatusToken {
		AKTIF, KUNCI
	}

	protected String kode;
	protected Operator operator;
	protected Date tanggalBuat;
	protected Date tanggalExpire;
	protected StatusToken status;
	
	public Token() {
		super();
	}

	public Token(Date tanggalBuat, Operator operator) {
		super();
		setOperator(operator);
		setTanggalBuat(tanggalBuat);
		generateExpire();
		generateKode();
		activate();
	}

	@Id
	@Column(name = "kode")
	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	/**
	 * Mengambil operator.
	 * 
	 * @return operator
	 */
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "operator")
	public Operator getOperator() {
		return operator;
	}

	/**
	 * Mengatur operator.
	 * 
	 * @param operator
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	@Column(name = "tanggal_buat")
	public Date getTanggalBuat() {
		return tanggalBuat;
	}

	public void setTanggalBuat(Date tanggalBuat) {
		this.tanggalBuat = tanggalBuat;
	}

	@Column(name = "tanggal_expire")
	public Date getTanggalExpire() {
		return tanggalExpire;
	}

	public void setTanggalExpire(Date tanggalExpire) {
		this.tanggalExpire = tanggalExpire;
	}

	@Column(name = "status")
	public StatusToken getStatus() {
		return status;
	}

	public void setStatus(StatusToken status) {
		this.status = status;
	}

	/**
	 * Mengecek apakah token sudah expire.
	 * 
	 * @return true jika sudah expire, selain itu false
	 */
	@JsonIgnore
	@Transient
	public boolean isExpire() {
		return tanggalExpire.before(DateUtil.getDate());
	}

	/**
	 * Menambah masa expire token sejumlah i.
	 * 
	 * @param i
	 */
	@Transient
	public void extend(int i) {
		tanggalExpire = DateUtil.add(tanggalExpire, 1);
	}

	@Transient
	private void generateExpire() {
		Date date = DateUtil.add(tanggalBuat, 2);
		setTanggalExpire(date);
	}

	@Transient
	private void generateKode() {
		Time time = DateUtil.getTime();
		String kode = String.format("%s-%s-%s", operator.hashCode(), tanggalBuat.hashCode(), time.hashCode());
		setKode(kode);
	}

	public void activate() {
		setStatus(StatusToken.AKTIF);
	}
	
	public void lock() {
		setStatus(StatusToken.KUNCI);
	}

	@Transient
	@JsonIgnore
	public boolean isLock() {
		return status.equals(StatusToken.KUNCI);
	}

	@Transient
	@JsonIgnore
	public boolean isExtensible() {
		return tanggalExpire.equals(DateUtil.getDate());
	}
	
	@Transient
	public Role getRole() {
		return operator.getRole();
	}
	
	public void setRole(Role role){ }

	@Transient
	public String getNama() {
		return operator.getNama();
	}
	
	public void setNama(String nama) { }

	@Transient
	public TipeUnit getTipe() {
		return operator.getUnit().getTipe();
	}
	
	public void setTipe(TipeUnit tipe) { }

	@Transient
	public String getNamaUnit() {
		return operator.getUnit().getNama();
	}
	
	public void setNamaUnit(String namaUnit) { }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kode == null) ? 0 : kode.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((tanggalBuat == null) ? 0 : tanggalBuat.hashCode());
		result = prime * result
				+ ((tanggalExpire == null) ? 0 : tanggalExpire.hashCode());
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
		Token other = (Token) obj;
		if (kode == null) {
			if (other.kode != null)
				return false;
		} else if (!kode.equals(other.kode))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (status != other.status)
			return false;
		if (tanggalBuat == null) {
			if (other.tanggalBuat != null)
				return false;
		} else if (!tanggalBuat.equals(other.tanggalBuat))
			return false;
		if (tanggalExpire == null) {
			if (other.tanggalExpire != null)
				return false;
		} else if (!tanggalExpire.equals(other.tanggalExpire))
			return false;
		return true;
	}
}
