package edu.dkakunsi.ta.presensi.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.entity.Department;
import edu.dkakunsi.ta.presensi.service.DepartmentService;

/**
 * Servlet implementation class DepartmentServlet
 */
@WebServlet("/admin/Department")
public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired private DepartmentService departmentService;
       
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = null;
		Object rsp = null;
		String msg = "";

		try {
			dis = request.getRequestDispatcher("/Department.jsp");
			String command = request.getParameter("command");
			String kode = "";
			
			switch(command.toLowerCase()){
				case "tambah" :
					dis = request.getRequestDispatcher("/FormDepartemen.jsp");
					break;
				case "ubah" :
					dis = request.getRequestDispatcher("/FormDepartemen.jsp");
					kode = request.getParameter("kode");
					rsp = cari(kode);
					break;
				case "cari" :
					kode = request.getParameter("dept");
					
					if(!kode.equals("") || !kode.equals("Kode Departemen")) {
						List<Department> ls = new ArrayList<>();
						ls.add(cari(kode));
						rsp = ls;
					} else {
						rsp = getAll();
					}
					break;
				case "simpan" :
					simpan(request);
					msg = "Data berhasil disimpan";
					rsp = getAll();
					break;
				case "hapus" :
					kode = request.getParameter("kode");
					departmentService.delete(departmentService.get(kode));
					msg = "Department dengan kode : '" + kode + "' berhasil dihapus";
					rsp = getAll();
					break;
				case "home" :
					dis = request.getRequestDispatcher("/index.jsp");
					break;
				default : rsp = getAll();
					break;
			}
		}catch(EntityException | GeneralException e) {
			msg = e.getMessage();
			rsp = getAll();
		} catch(NullPointerException e) {
			rsp = getAll();
		}

		request.setAttribute("rsp", rsp);
		request.setAttribute("msg", msg);
		dis.include(request, response);
	}

	private void simpan(HttpServletRequest request) throws EntityException, GeneralException {
		Department department = new Department();
		department.setId(request.getParameter("kode"));
		department.setNama(request.getParameter("nama"));
		
		departmentService.save(department);
	}
	
	private Department cari(String kode) throws EntityException, GeneralException {
		return departmentService.get(kode);
	}
	
	private List<Department> getAll() {
		try {
			return departmentService.getAll();
		} catch (EntityException e) {
			return new ArrayList<>();
		}
	}
}
