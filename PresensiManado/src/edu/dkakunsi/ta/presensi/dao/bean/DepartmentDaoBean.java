package edu.dkakunsi.ta.presensi.dao.bean;

import org.springframework.stereotype.Repository;

import edu.dkakunsi.ta.presensi.dao.DepartmentDao;
import edu.dkakunsi.ta.presensi.model.entity.Department;

@Repository("departmentDao")
public class DepartmentDaoBean extends GenericDao<Department> implements DepartmentDao {
}
