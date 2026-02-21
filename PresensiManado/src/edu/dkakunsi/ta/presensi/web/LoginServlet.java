package edu.dkakunsi.ta.presensi.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getSession().removeAttribute("kode");
		request.getSession().invalidate();
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String pass = request.getParameter("password");
		
		RequestDispatcher dis = null;
		if((user != null && user.equals("admin")) && (pass != null && pass.equals("12345"))) {
			request.getSession().setAttribute("kode", "admin");
			dis = request.getRequestDispatcher("/index.jsp");
		} else {
			dis = request.getRequestDispatcher("/Login.jsp");
		}
		
		dis.include(request, response);
	}

}
