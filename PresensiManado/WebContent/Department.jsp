<%@page import="javax.persistence.UniqueConstraint"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../Style.css" rel="stylesheet" type="text/css" />
<title>Data Department - Sistem Presensi</title>
</head>

<body>
	<%@ page import="java.util.List" %>
	<%@ page import="edu.dkakunsi.ta.presensi.model.entity.Department" %>

  	<div id="logo">
  		<img src="../images/logo_manado.png" alt="Logo Manado" height="150px" width="175px" />
   	</div>

    <div id="header">
    	<p>Sistem Presensi Menggunakan Web Service</p>
    </div>

	<div id="container">
		<div id="tools">
        	<div id="judul1"><b>MENU</b></div>
        	<div id="search">
				<div id="judul2"><b>CARI</b></div>
				<form>
					<div id="searchDepartment">
						<input name="dept" type="text" value="Kode Departemen" />
						<input name="command" type="submit" value="CARI" />
					</div>
				</form>
            </div>
            <div id="kelola">
				<div id="judul2"><b>KELOLA</b></div>
				<div id="addButton">
					<a class="button" href="/PresensiManado/admin/Department?command=tambah">Tambah Departemen</a>
				</div>
            </div>
			<div id="nav">
				<div id="judul2"><b>NAVIGASI</b></div>
				<a class="button" href="/PresensiManado/admin/Department">Departemen</a><br /> 
				<a class="button" href="/PresensiManado/admin/Pegawai">Pegawai</a><br /> 
				<a class="button" href="/PresensiManado/admin/Presensi">Daftar Hadir</a><br /> 
				<a class="button" href="/PresensiManado/admin/Izin">Daftar Izin</a><br /> 
				<a class="button" href="/PresensiManado/admin/Lembur">Daftar Lembur</a><br /> 
				<a class="button" href="/PresensiManado/Login">Logout</a>
			</div>
		</div>
	    <div id="table">
			<%= request.getAttribute("msg") %>		
        	<table>
            	<tr>
                	<th width="250">KODE</th>
                	<th width="350">NAMA</th>
                 	<th width="100" colspan="2">ACTION</th>
                </tr>
                <% @SuppressWarnings("unchecked") List<Department> ls = (List<Department>)request.getAttribute("rsp"); %>
                <% for(Department d : ls) { %>
                    <tr>
                    	<td><%= d.getId()%></td>
                    	<td><%= d.getNama()%></td>
                      	<td class="action"><a class="button" href="/PresensiManado/admin/Department?command=ubah&kode=<%= d.getId() %>"><img src="../images/edit.png" /></a></td>
                      	<td class="action"><a class="button" href="/PresensiManado/admin/Department?command=hapus&kode=<%= d.getId() %>"><img src="../images/delete.png" /></a></td>
                    </tr>
                <% } %>
            </table>
  		</div>
	</div><br />

    <div id="footer">
    	<p>&nbsp;</p>
    </div>
</body>

</html>