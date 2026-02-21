package edu.dkakunsi.ta.presensi.dao;

import java.util.Date;
import java.util.List;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.model.entity.Presensi;

public interface PresensiDao {
	public void save(Presensi data) throws EntityException;
	public void delete(Presensi data) throws EntityException;
	public Presensi get(Object id) throws EntityException;

	public List<Presensi> getAll() throws EntityException;
	public List<Presensi> getByTanggal(Date tanggal) throws EntityException;
	public List<Presensi> getAllEmptyPulang() throws EntityException;
	public List<Presensi> getByPegawai(String nip) throws EntityException;
	public List<Presensi> getByPegawai(String nip, Date tanggalMulai, Date tanggalSelesai) throws EntityException;
	public List<Presensi> getByDepartment(String kode) throws EntityException;
	public List<Presensi> getByDepartment(String kode, Date date) throws EntityException;
	public List<Presensi> getByDepartment(String kode, Date tanggalMulai, Date tanggalSelesai) throws EntityException;
}
