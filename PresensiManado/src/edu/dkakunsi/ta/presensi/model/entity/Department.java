package edu.dkakunsi.ta.presensi.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;


/**
 * The persistent class for the DEPARTMENT database table.
 * 
 */
@Entity
@Table(name="departemen")
@NamedQuery(name="getAllDepartment", query="SELECT d FROM Department d")
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;
	private String kode;
	private String nama;
	private List<Pegawai> pegawais;

	public Department() {
	}


	@Id
	@Column(name="kode", length=12)
	public String getId() {
		return this.kode;
	}

	public void setId(String kode) {
		this.kode = kode;
	}


	@Column(name="NAMA", nullable=false, length=255)
	public String getNama() {
		return this.nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}


	//bi-directional many-to-one association to Pegawai
	@XmlTransient
	@OneToMany(mappedBy="department", fetch=FetchType.LAZY)
	public List<Pegawai> getPegawais() {
		return this.pegawais;
	}

	public void setPegawais(List<Pegawai> pegawais) {
		this.pegawais = pegawais;
	}

	public Pegawai addPegawai(Pegawai pegawai) {
		getPegawais().add(pegawai);
		pegawai.setDepartment(this);

		return pegawai;
	}

	public Pegawai removePegawai(Pegawai pegawai) {
		getPegawais().remove(pegawai);
		pegawai.setDepartment(null);

		return pegawai;
	}
	
	public boolean equals(Object obj) {
		Department dept = (Department)obj;
		if(this.kode.equals(dept.kode))
			return true;
		return false;
	}

}