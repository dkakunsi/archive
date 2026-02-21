package edu.dkakunsi.ta.presensi.dao;

import java.util.Date;
import java.util.List;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.model.entity.Lembur;

public interface LemburDao {
	public void save(Lembur data) throws EntityException;
	public void delete(Lembur data) throws EntityException;
	public Lembur get(Object id) throws EntityException;
	
	public List<Lembur> getAll() throws EntityException;
	public List<Lembur> getByTanggal(Date tanggal) throws EntityException;
	public List<Lembur> getByPegawai(String nip) throws EntityException;
	public List<Lembur> getByPegawai(String nip, Date tanggalMulai, Date tanggalSelesai) throws EntityException;
	public List<Lembur> getByDepartment(String kode) throws EntityException;
	public List<Lembur> getByDepartment(String kode, Date date) throws EntityException;
	public List<Lembur> getByDepartment(String kode, Date tanggalMulai, Date tanggalSelesai) throws EntityException;
}
