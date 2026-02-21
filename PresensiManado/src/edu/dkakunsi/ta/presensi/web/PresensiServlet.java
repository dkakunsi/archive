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
import edu.dkakunsi.ta.presensi.model.entity.Presensi;
import edu.dkakunsi.ta.presensi.service.PresensiService;
import edu.dkakunsi.ta.presensi.util.DateTime;

/**
 * Servlet implementation class PresensiServlet
 */
@WebServlet("/admin/Presensi")
public class PresensiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired private PresensiService presensiService;
       
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = null;
		Object rsp = getAll();
		String msg = "";

		try {
			dis = request.getRequestDispatcher("/Presensi.jsp");
			String command = request.getParameter("command").toLowerCase();

			if(command.equals("cari")) {
				rsp = cari(request);
			} else if(command.equals("home")) {
				dis = request.getRequestDispatcher("/index.jsp");
			} else if(command.equals("close")) {
				presensiService.closeHarian();
			}
		} catch(EntityException | GeneralException | NullPointerException e) {
			msg = e.getMessage();
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("rsp", rsp);
		dis.include(request, response);
	}

	private List<Presensi> cari(HttpServletRequest request) throws EntityException, GeneralException {
		List<Presensi> ls = getAll();
		String nip = request.getParameter("nip");
		String dept = request.getParameter("dept");
		String tgl = request.getParameter("tanggal");

		if(!tgl.equals("") && !tgl.contains("hh") && !tgl.equals("hh-BB-tttt")) {
			if(!nip.equals("") && !nip.equals("NIP Pegawai")) {
				ls = presensiService.getByPegawai(nip);
			} else {
				if(!dept.equals("") && !dept.equals("Kode Departemen")) {
					ls = presensiService.getByDepartment(dept, new DateTime(tgl).getUtilDate());
				} else {
					ls = presensiService.getByTanggal(new DateTime(tgl).getUtilDate());
				}
			}
		} else {
			if(!nip.equals("") && !nip.equals("NIP Pegawai")) {
				ls = presensiService.getByPegawai(nip);
			} else {
				if(!dept.equals("") && !dept.equals("Kode Departemen")) {
					ls = presensiService.getByDepartment(dept);
				}
			}
		}

		return ls;
	}
	
	private List<Presensi> getAll() {
		try {
			return presensiService.getAll();
		} catch (EntityException e) {
			return new ArrayList<>();
		}
	}
}
