package edu.dkakunsi.ta.presensi.dao;

import java.util.List;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.model.entity.Department;

public interface DepartmentDao {
	public void save(Department data) throws EntityException;
	public void delete(Department data) throws EntityException;
	public Department get(Object id) throws EntityException;
	public List<Department> getAll() throws EntityException;
}
