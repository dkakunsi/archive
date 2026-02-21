package edu.dkakunsi.ta.presensi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.dkakunsi.ta.presensi.dao.DepartmentDao;
import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Department;
import edu.dkakunsi.ta.presensi.service.interceptor.Interceptor;

@Service("departmentService")
@Transactional(readOnly=true)
public class DepartmentService {
	@Autowired private DepartmentDao dao;
	@Autowired private Interceptor interceptor;
	
	@Transactional(readOnly=false)
	public void save(Department skpd) 
			throws EntityException, GeneralException {
		interceptor.notNull(skpd);
		dao.save(skpd);
	}

	@Transactional(readOnly=false)
	public void delete(Department skpd) 
			throws EntityException, GeneralException {
		interceptor.notNull(skpd);
		dao.delete(skpd);
	}

	public List<Department> getAll() throws EntityException {
		return dao.getAll();
	}

	public Department get(String kode) 
			throws EntityException, GeneralException {
		interceptor.notNull(kode);
		return dao.get(kode);
	}
}
