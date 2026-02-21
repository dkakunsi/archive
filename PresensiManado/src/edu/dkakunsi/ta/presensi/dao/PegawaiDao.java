package edu.dkakunsi.ta.presensi.dao;

import java.util.List;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.model.entity.Pegawai;

public interface PegawaiDao {
	public void save(Pegawai data) throws EntityException;
	public void delete(Pegawai data) throws EntityException;
	public Pegawai get(Object id) throws EntityException;
	
	public List<Pegawai> getAll() throws EntityException;
	public List<Pegawai> getByDepartment(String kode) throws EntityException;
}
