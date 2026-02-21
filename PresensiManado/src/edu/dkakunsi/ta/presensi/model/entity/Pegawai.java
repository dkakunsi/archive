package edu.dkakunsi.ta.presensi.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the PEGAWAI database table.
 * 
 */
@Entity
@Table(name="pegawai")
@NamedQueries({
	@NamedQuery(name="getAllPegawai", query="SELECT p FROM Pegawai p"),
	@NamedQuery(name="getByDepartmentPegawai", query="SELECT p FROM Pegawai p WHERE p.department.id = :department")
})
public class Pegawai implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nip;
	private String nama;
	private String golongan;
	private Department department;
	private List<Presensi> presensis;

	public Pegawai() {
	}


	@Id
	@Column(name="NIP", length=50)
	public String getNip() {
		return this.nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}


	@Column(name="NAMA", nullable=false, length=255)
	public String getNama() {
		return this.nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}
	
	@Column(name="GOLONGAN", nullable=true, length=255)
	public String getGolongan() {
		return this.golongan;
	}
	
	public void setGolongan(String golongan) {
		this.golongan = golongan;
	}

	//bi-directional many-to-one association to Department
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="departemen")
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	//bi-directional many-to-one association to Presensi
	@XmlTransient
	@OneToMany(targetEntity=Presensi.class, mappedBy="pegawai",
			cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public List<Presensi> getPresensis() {
		return this.presensis;
	}
	
	public void setPresensis(List<Presensi> presensis) {
		this.presensis = presensis;
	}
	
	public Presensi addPresensi(Presensi presensi) {
		getPresensis().add(presensi);
		presensi.setPegawai(this);
		
		return presensi;
	}
	
	public Presensi removePresensi(Presensi presensi) {
		getPresensis().remove(presensi);
		presensi.setPegawai(null);
		
		return presensi;
	}
	
	@Override
	public boolean equals(Object obj) {
		Pegawai other = (Pegawai)obj;
		if(other.getNip().equals(nip))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return nip + " " +
				nama + " " +
				golongan + " " +
				department.getId();
	}

}