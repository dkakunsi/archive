package edu.dkakunsi.ta.presensi.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import edu.dkakunsi.ta.presensi.util.TimeAdapter;

import java.sql.Time;


/**
 * The persistent class for the izin database table.
 * 
 */
@Entity
@Table(name="izin")
@NamedQueries({
	@NamedQuery(name="getAllIzin", query="SELECT i FROM Izin i"),
	@NamedQuery(name="getByTanggalIzin", query="SELECT i FROM Izin i where i.id.tanggal = :tanggal"),
	@NamedQuery(name="getByPegawaiIzin", query="SELECT i FROM Izin i where i.id.nip = :nip"),
	@NamedQuery(name="getByPegawaiBulananIzin", query="SELECT i FROM Izin i WHERE i.id.tanggal BETWEEN :tanggalMulai AND :tanggalSelesai AND i.id.nip = :nip"),
	@NamedQuery(name="getByDepartmentIzin", query="SELECT i FROM Izin i WHERE i.presensi.pegawai.department.id = :department"),
	@NamedQuery(name="getByDepartment&TanggalIzin", query="SELECT i FROM Izin i where i.id.tanggal = :tanggal AND i.presensi.pegawai.department.id = :department"),
	@NamedQuery(name="getByDepartmentBulananIzin", query="SELECT i FROM Izin i WHERE i.id.tanggal BETWEEN :tanggalMulai AND :tanggalSelesai AND i.presensi.pegawai.department.id = :department")
})
@XmlRootElement
public class Izin implements Serializable {
	private static final long serialVersionUID = 1L;
	private IzinPK id;
	private Time keluar;
	private String keterangan;
	private Time masuk;
	private Presensi presensi;

	public Izin() {
	}

	@EmbeddedId
	public IzinPK getId() {
		return this.id;
	}

	public void setId(IzinPK id) {
		this.id = id;
	}

	@XmlJavaTypeAdapter(TimeAdapter.class)
	@Column(name="keluar", nullable=false)
	public Time getKeluar() {
		return this.keluar;
	}

	public void setKeluar(Time keluar) {
		this.keluar = keluar;
	}

	@XmlJavaTypeAdapter(TimeAdapter.class)
	@Column(name="masuk", nullable=true)
	public Time getMasuk() {
		return this.masuk;
	}

	public void setMasuk(Time masuk) {
		this.masuk = masuk;
	}

	@Column(name="keterangan", nullable=true, length=50)
	public String getKeterangan() {
		return this.keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	//bi-directional many-to-one association to Presensi
	@XmlTransient
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="nip", referencedColumnName="nip", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="tanggal", referencedColumnName="tanggal", nullable=false, insertable=false, updatable=false)
		})
	public Presensi getPresensi() {
		return this.presensi;
	}

	public void setPresensi(Presensi presensi) {
		this.presensi = presensi;
	}

	@Override
	public boolean equals(Object obj) {
		Presensi other = (Presensi)obj;
		if(other.getId().equals(id))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return id.getNip() + " - " +
				id.getTanggal() + " - " +
				id.getNomor() + " - " +
				masuk + " - " +
				keluar + " - " +
				keterangan;
	}
}