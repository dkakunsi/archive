package edu.dkakunsi.ta.presensi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.dkakunsi.ta.presensi.dao.DepartmentDao;
import edu.dkakunsi.ta.presensi.dao.PegawaiDao;
import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Pegawai;
import edu.dkakunsi.ta.presensi.service.interceptor.Interceptor;

@Service("pegawaiService")
@Transactional(readOnly=true)
public class PegawaiService {
	@Autowired private PegawaiDao dao;
	@Autowired private DepartmentDao departmentDao;
	@Autowired private Interceptor interceptor;

	@Transactional(readOnly=false)
	public void save(Pegawai pegawai) 
			throws EntityException, GeneralException {
		interceptor.notNull(pegawai);
		dao.save(pegawai);
	}

	@Transactional(readOnly=false)
	public void delete(Pegawai pegawai) 
			throws EntityException, GeneralException {
		interceptor.notNull(pegawai);
		dao.delete(pegawai);
	}

	public List<Pegawai> getByDepartment(String skpd) 
			throws EntityException, GeneralException {
		interceptor.notNull(skpd);
		return dao.getByDepartment(skpd);
	}

	public Pegawai get(String nip) 
			throws EntityException, GeneralException {
		interceptor.notNull(nip);
		return dao.get(nip);
	}

	public List<Pegawai> getAll() throws EntityException {
		return dao.getAll();
	}

	public Pegawai[] toArray(List<Pegawai> ls) {
		Pegawai[] arr = new Pegawai[ls.size()];
		int counter = 0;
		
		for(Pegawai p : ls) {
			arr[counter] = p;
			counter++;
		}
		return arr;
	}
}
