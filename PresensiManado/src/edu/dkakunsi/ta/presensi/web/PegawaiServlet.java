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
import edu.dkakunsi.ta.presensi.model.entity.Pegawai;
import edu.dkakunsi.ta.presensi.service.DepartmentService;
import edu.dkakunsi.ta.presensi.service.PegawaiService;

/**
 * Servlet implementation class PegawaiServlet
 */
@WebServlet("/admin/Pegawai")
public class PegawaiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired private PegawaiService pegawaiService;
	@Autowired private DepartmentService departmentService;
       
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = null;
		Object rsp = null;
		String msg = "";

		try{
			dis = request.getRequestDispatcher("/Pegawai.jsp");
			String command = request.getParameter("command");
			String nip = "";

			switch(command.toLowerCase()){
				case "tambah" :
					dis = request.getRequestDispatcher("/FormPegawai.jsp");
					break;
				case "ubah" :
					dis = request.getRequestDispatcher("/FormPegawai.jsp");
					nip = request.getParameter("nip");
					rsp = cari(nip);
					break;
				case "cari" :
					String dept = request.getParameter("dept");
					nip = request.getParameter("nip");
					
					if(!nip.equals("") && !nip.equals("NIP Pegawai")) {
						List<Pegawai> ls = new ArrayList<>();
						ls.add(cari(nip));
						rsp = ls;
					} else {
						if(!dept.equals("") && !dept.equals("Kode Departemen")) {
							rsp = pegawaiService.getByDepartment(dept);
						} else {
							rsp = getAll();
						}
					}
					break;
				case "simpan" :
					simpan(request);
					msg = "data berhasil disimpan";
					rsp = getAll();
					break;
				case "hapus" :
					nip = request.getParameter("nip");
					pegawaiService.delete(pegawaiService.get(nip));
					msg = "Pegawai dengan nip : '" + nip + "' berhasil dihapus";
					rsp = getAll();
					break;
				case "home" :
					dis = request.getRequestDispatcher("/index.jsp");
					break;
				default : rsp = getAll();
					break;
			}
		} catch(EntityException | GeneralException e) {
			msg = e.getMessage();
			rsp = getAll();
		} catch(NullPointerException e) {
			rsp = getAll();
		}

		request.setAttribute("msg", msg);
		request.setAttribute("rsp", rsp);
		dis.include(request, response);
	}

	private void simpan(HttpServletRequest request) throws EntityException, GeneralException {
		Pegawai pegawai = new Pegawai();
		pegawai.setNip(request.getParameter("nip"));
		pegawai.setNama(request.getParameter("nama"));
		pegawai.setGolongan(request.getParameter("golongan"));
		pegawai.setDepartment(departmentService.get(request.getParameter("department")));
		
		pegawaiService.save(pegawai);
	}
	
	private Pegawai cari(String nip) throws EntityException, GeneralException {
		return pegawaiService.get(nip);
	}
	
	private List<Pegawai> getAll() {
		try {
			return pegawaiService.getAll();
		} catch (EntityException e) {
			return new ArrayList<>();
		}
	}
	
	
}
