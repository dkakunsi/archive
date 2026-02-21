package edu.dkakunsi.ta.presensi.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.model.rekapmodel.Rekap;
import edu.dkakunsi.ta.presensi.model.rekapmodel.RekapLembur;
import edu.dkakunsi.ta.presensi.model.rekapmodel.RekapPresensi;
import edu.dkakunsi.ta.presensi.service.LemburService;
import edu.dkakunsi.ta.presensi.service.PresensiService;
import edu.dkakunsi.ta.presensi.util.DateTime;

/**
 * Servlet implementation class RekapServlet
 */
@WebServlet("/admin/Report")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired PresensiService presensiService;
	@Autowired LemburService lemburService;	
    
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		byte[] report = null;
		String type = "", bulan = "", tahun = "", dept = "";
		
		try {
			type = request.getParameter("type");
			bulan = request.getParameter("bulan");
			tahun = request.getParameter("tahun");
			dept = request.getParameter("dept");

			if(type.equals("presensi")) {
				List<Rekap> ds = presensiService.rekap(dept, new DateTime(Integer.parseInt(tahun), Integer.parseInt(bulan), 0));
				report = JasperRunManager.runReportToPdf(RekapPresensi.getRekap(), null, new JRBeanCollectionDataSource(ds));
			} else if(type.equals("lembur")) {
				List<Rekap> ds = lemburService.rekap(dept, new DateTime(Integer.parseInt(tahun), Integer.parseInt(bulan), 0));
				report = JasperRunManager.runReportToPdf(RekapLembur.getRekap(), null, new JRBeanCollectionDataSource(ds));
			} else {
				throw new GeneralException("Source tidak ditemukan");
			}

			ServletOutputStream ouputStream = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setContentLength(report.length);
			ouputStream.write(report, 0, report.length);
		} catch(NullPointerException e) {
			PrintWriter out = response.getWriter();
			out.write("NullPointerException");
			out.write(e.getMessage());
		} catch (JRException e) {
			PrintWriter out = response.getWriter();
			out.write("JRException");
			out.write(e.getMessage());
		} catch (GeneralException e) {
			PrintWriter out = response.getWriter();
			out.write("GeneralException");
			out.write(e.getMessage());
		} catch (NumberFormatException e) {
			PrintWriter out = response.getWriter();
			out.write("NumberFormatException");
			out.write(e.getMessage());
		} catch (EntityException e) {
			PrintWriter out = response.getWriter();
			out.write("EntityException");
			out.write(e.getMessage());
		}
	}
}
