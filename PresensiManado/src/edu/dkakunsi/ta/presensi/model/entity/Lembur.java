package edu.dkakunsi.ta.presensi.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the lembur database table.
 * 
 */
@XmlRootElement
@Entity
@Table(name="lembur")
@NamedQueries({
	@NamedQuery(name="getAllLembur", query="SELECT l FROM Lembur l"),
	@NamedQuery(name="getByTanggalLembur", query="SELECT l FROM Lembur l WHERE l.id.tanggal = :tanggal"),
	@NamedQuery(name="getByPegawaiLembur", query="SELECT l FROM Lembur l WHERE l.id.nip = :nip"),
	@NamedQuery(name="getByPegawaiBulananLembur", query="SELECT l FROM Lembur l WHERE l.id.tanggal BETWEEN :tanggalMulai AND :tanggalSelesai AND l.id.nip = :nip"),
	@NamedQuery(name="getByDepartmentLembur", query="SELECT l FROM Lembur l WHERE l.presensi.pegawai.department.id = :department"),
	@NamedQuery(name="getByDepartment&TanggalLembur", query="SELECT l FROM Lembur l WHERE l.id.tanggal = :tanggal AND l.presensi.pegawai.department.id = :department"),
	@NamedQuery(name="getByDepartmentBulananLembur", query="SELECT l FROM Lembur l WHERE l.id.tanggal BETWEEN :tanggalMulai AND :tanggalSelesai AND l.presensi.pegawai.department.id = :department")
})
public class Lembur implements Serializable {
	private static final long serialVersionUID = 1L;
	private PrimaryKey id;
	private int jumlah;
	private Presensi presensi;

	public Lembur() {
	}

	@EmbeddedId
	public PrimaryKey getId() {
		return this.id;
	}

	public void setId(PrimaryKey id) {
		this.id = id;
	}

	@Column(name="jumlah", nullable=false, length=11)
	public int getJumlah() {
		return this.jumlah;
	}

	public void setJumlah(int jumlah) {
		this.jumlah = jumlah;
	}

	//bi-directional one-to-one association to Presensi
	@XmlTransient
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="nip", referencedColumnName="nip", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="tanggal", referencedColumnName="tanggal", nullable=false, insertable=false, updatable=false)
		})
	public Presensi getPresensi() {
		return this.presensi;
	}

	public void setPresensi(Presensi presensi) {
		try {
			this.presensi = presensi;
			presensi.setLembur(this);
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
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
				jumlah;
	}
}