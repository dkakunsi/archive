package edu.dkakunsi.ta.presensi.web;

import java.io.IOException;
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
import edu.dkakunsi.ta.presensi.model.rekapmodel.Rekap;
import edu.dkakunsi.ta.presensi.service.IzinService;
import edu.dkakunsi.ta.presensi.service.LemburService;
import edu.dkakunsi.ta.presensi.service.PresensiService;
import edu.dkakunsi.ta.presensi.util.DateTime;

/**
 * Servlet implementation class RekapServlet
 */
@WebServlet("/admin/Rekap")
public class RekapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired PresensiService presensiService;
	@Autowired IzinService izinService;
	@Autowired LemburService lemburService;	

	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("/Rekap.jsp");;
		String type = "";
		String message = "Maaf, Rekap Belum Tersedia! Periksa Kriteria Pencarian";
		Object rsp = null;

		try {
			type = request.getParameter("type");

			if(type.equals("presensi")) {
				rsp = cariPresensi(request);
			} else if(type.equals("lembur")) {
				rsp = cariLembur(request);
			} else if(type.equals("cetak")) {
				dis = request.getRequestDispatcher("/Report");
			}
		} catch(NullPointerException | EntityException | GeneralException e) {
			message = e.getMessage();
		}

		request.setAttribute("type", type);
		request.setAttribute("rsp", rsp);
		request.setAttribute("message", message);
		dis.include(request, response);
	}

	private List<Rekap> cariPresensi(HttpServletRequest request) throws EntityException, GeneralException {
		return presensiService.rekap(request.getParameter("dept"), setDateTime(request));
	}

	private List<Rekap> cariLembur(HttpServletRequest request) throws EntityException, GeneralException {
		return lemburService.rekap(request.getParameter("dept"), setDateTime(request));
	}

	private DateTime setDateTime(HttpServletRequest request) throws GeneralException {
		DateTime dt = setDateTime(request.getParameter("tanggal"));
		request.setAttribute("bulan", dt.getBulan());
		request.setAttribute("tahun", dt.getTahun());
		request.setAttribute("dept", request.getParameter("dept"));

		return dt;
	}
	
	private DateTime setDateTime(String tanggal) throws GeneralException {
		if((tanggal!=null) && (!tanggal.equals("") && (!tanggal.contains("h"))))
			return new DateTime(tanggal);
		return new DateTime();
	}
}
