package edu.dkakunsi.ta.presensi.dao;

import java.util.Date;
import java.util.List;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.model.entity.Izin;

public interface IzinDao {
	public void save(Izin data) throws EntityException;
	public void delete(Izin data) throws EntityException;
	public Izin get(Object id) throws EntityException;
	
	public List<Izin> getAll() throws EntityException;
	public List<Izin> getByTanggal(Date tanggal) throws EntityException;
	public List<Izin> getByPegawai(String nip) throws EntityException;
	public List<Izin> getByPegawai(String nip, Date tanggalMulai, Date tanggalSelesai) throws EntityException;
	public List<Izin> getByDepartment(String kode) throws EntityException;
	public List<Izin> getByDepartment(String kode, Date date) throws EntityException;
	public List<Izin> getByDepartment(String kode, Date tanggalMulai, Date tanggalSelesai) throws EntityException;
}
