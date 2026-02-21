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
import edu.dkakunsi.ta.presensi.model.entity.Izin;
import edu.dkakunsi.ta.presensi.service.IzinService;
import edu.dkakunsi.ta.presensi.util.DateTime;

/**
 * Servlet implementation class IzinServlet
 */
@WebServlet("/admin/Izin")
public class IzinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired private IzinService izinService;
       
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = null;
		Object rsp = null;
		String msg = "";
		
		try {
			dis = request.getRequestDispatcher("/Izin.jsp");
			String command = request.getParameter("command").toLowerCase();

			if(command.equals("cari")) {
				rsp = cari(request);
			} else if(command.equals("home")) {
				dis = request.getRequestDispatcher("/index.jsp");
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

	private List<Izin> cari(HttpServletRequest request) throws EntityException, GeneralException {
		List<Izin> ls = new ArrayList<>();
		String nip = request.getParameter("nip");
		String dept = request.getParameter("dept");
		String tgl = request.getParameter("tanggal");
		
		if(!tgl.equals("") && !tgl.contains("hh") && !tgl.equals("hh-BB-tttt")) {
			if(!nip.equals("") && !nip.equals("NIP Pegawai")) {
				ls = izinService.getByPegawai(nip);
			} else {
				if(!dept.equals("") && !dept.equals("Kode Departemen")) {
					ls = izinService.getByDepartment(dept, new DateTime(tgl).getUtilDate());
				} else {
					ls = izinService.getByTanggal(new DateTime(tgl).getUtilDate());
				}
			}
		} else {
			if(!nip.equals("") && !nip.equals("NIP Pegawai")) {
				ls = izinService.getByPegawai(nip);
			} else {
				if(!dept.equals("") && !dept.equals("Kode Departemen")) {
					ls = izinService.getByDepartment(dept);
				} else {
					ls = getAll();
				}
			}
		}
		
		return ls;
	}
	
	private List<Izin> getAll() {
		try {
			return izinService.getAll();
		} catch (EntityException e) {
			return new ArrayList<>();
		}
	}
}
