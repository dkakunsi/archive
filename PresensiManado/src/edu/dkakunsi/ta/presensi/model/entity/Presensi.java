package edu.dkakunsi.ta.presensi.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import edu.dkakunsi.ta.presensi.util.TimeAdapter;

import java.sql.Time;
import java.util.List;


/**
 * The persistent class for the presensi database table.
 * 
 */
@XmlRootElement
@Entity
@Table(name="presensi")
@NamedQueries({
	@NamedQuery(name="getAllPresensi", query="SELECT p FROM Presensi p"),
	@NamedQuery(name="getByTanggalPresensi", query="SELECT p FROM Presensi p WHERE p.id.tanggal = :tanggal"),
	@NamedQuery(name="getByPegawaiPresensi", query="SELECT p FROM Presensi p WHERE p.id.nip = :nip"),
	@NamedQuery(name="getByPegawaiBulananPresensi", query="SELECT p FROM Presensi p WHERE p.id.tanggal BETWEEN :tanggalMulai AND :tanggalSelesai AND p.id.nip = :nip"),
	@NamedQuery(name="getByDepartmentPresensi", query="SELECT p FROM Presensi p WHERE p.pegawai.department.id = :department"),
	@NamedQuery(name="getByDepartment&TanggalPresensi", query="SELECT p FROM Presensi p WHERE p.pegawai.department.id = :department AND p.id.tanggal = :tanggal"),
	@NamedQuery(name="getByDepartmentBulananPresensi", query="SELECT p FROM Presensi p WHERE p.id.tanggal BETWEEN :tanggalMulai AND :tanggalSelesai AND p.pegawai.department.id = :department"),
	@NamedQuery(name="getNullInKeluar", query="SELECT p FROM Presensi p WHERE p.keluar IS NULL")
})
public class Presensi implements Serializable {
	private static final long serialVersionUID = 1L;
	private PrimaryKey id;
	private Time keluar;
	private String keterangan;
	private Time masuk;
	private List<Izin> izins;
	private Lembur lembur;
	private Pegawai pegawai;

	public Presensi() {}

	@EmbeddedId
	public PrimaryKey getId() {
		return this.id;
	}

	public void setId(PrimaryKey id) {
		this.id = id;
	}

	@XmlJavaTypeAdapter(TimeAdapter.class)
	@Column(name="masuk", nullable=false)
	public Time getMasuk() {
		return this.masuk;
	}

	public void setMasuk(Time masuk) {
		this.masuk = masuk;
	}

	@XmlJavaTypeAdapter(TimeAdapter.class)
	@Column(name="keluar", nullable=true)
	public Time getKeluar() {
		return this.keluar;
	}

	public void setKeluar(Time keluar) {
		this.keluar = keluar;
	}

	@Column(name="keterangan", nullable=true, length=255)
	public String getKeterangan() {
		return this.keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	//bi-directional many-to-one association to Izin
	@XmlTransient
	@OneToMany(targetEntity=Izin.class, mappedBy="presensi",
			cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public List<Izin> getIzins() {
		return this.izins;
	}

	public void setIzins(List<Izin> izins) {
		this.izins = izins;
	}

	public Izin addIzin(Izin izin) {
		getIzins().add(izin);
		izin.setPresensi(this);

		return izin;
	}

	public Izin removeIzin(Izin izin) {
		getIzins().remove(izin);
		izin.setPresensi(null);

		return izin;
	}

	//bi-directional one-to-one association to Lembur
	@XmlTransient
	@OneToOne(mappedBy="presensi", fetch=FetchType.EAGER)
	public Lembur getLembur() {
		return this.lembur;
	}

	public void setLembur(Lembur lembur) {
		this.lembur = lembur;
	}
	
	//bi-directional many-to-one association to Pegawai
	@XmlTransient
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="nip", referencedColumnName="nip", nullable=false, insertable=false, updatable=false)
	public Pegawai getPegawai() {
		return this.pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
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
				keluar + " - " +
				masuk + " - " +
				keterangan;
	}
}