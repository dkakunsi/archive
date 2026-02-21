package edu.dkakunsi.ta.presensi.web.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Department;
import edu.dkakunsi.ta.presensi.util.Constant;

@WebService(targetNamespace = Constant.TARGET_NAMESPACE)
@SOAPBinding(style = Style.RPC)
public interface DepartmentSEI {
	@WebMethod
	void save(
		@WebParam(partName = "skpd", name = "skpd")
		Department skpd
	) throws EntityException, GeneralException;

    @WebMethod
	void delete(
		@WebParam(partName = "skpd", name = "skpd")
		Department skpd
			) throws EntityException, GeneralException;

    @WebResult(name = "department", targetNamespace = Constant.TARGET_NAMESPACE, partName = "department")
    @WebMethod
	Department getById(
		@WebParam(partName = "id", name = "id")
		String id
			) throws EntityException, GeneralException;

    @WebResult(name = "listDepartment", targetNamespace = Constant.TARGET_NAMESPACE, partName = "listDepartment")
    @WebMethod
	Department[] getAll() throws EntityException, GeneralException;
}
