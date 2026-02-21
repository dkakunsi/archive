package edu.dkakunsi.ta.presensi.web.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Department;
import edu.dkakunsi.ta.presensi.service.DepartmentService;
import edu.dkakunsi.ta.presensi.util.Constant;
import edu.dkakunsi.ta.presensi.web.service.DepartmentSEI;

@WebService(
        serviceName = "DepartmentService",
        targetNamespace = Constant.TARGET_NAMESPACE,
        endpointInterface = "edu.dkakunsi.ta.presensi.web.service.DepartmentSEI")
public class DepartmentWs implements DepartmentSEI {
	@Autowired private DepartmentService departmentSvc;
	
	@Override
	public void save(Department skpd) throws EntityException, GeneralException {
		departmentSvc.save(skpd);
	}

	@Override
	public void delete(Department skpd) throws EntityException, GeneralException {
		departmentSvc.delete(skpd);
	}

	@Override
	public Department[] getAll() throws EntityException {
		List<Department> lsDept = departmentSvc.getAll();
		Department[] arr = new Department[lsDept.size()];
		int counter = 0;
		for(Department d : lsDept) {
			arr[counter] = d;
			counter++;
		}
		return arr;
	}

	@Override
	public Department getById(String id) throws EntityException, GeneralException {
		return departmentSvc.get(id);
	}

}
