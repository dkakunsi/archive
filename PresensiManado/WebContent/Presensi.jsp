<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../Style.css" rel="stylesheet" type="text/css" />
<title>Data Presensi - Sistem Presensi</title>
</head>

<body>
	<%@ page import="edu.dkakunsi.ta.presensi.model.entity.Presensi" %>
	<%@ page import="java.util.List" %>

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
					</div>
					<div id="searchNip">
						<input name="nip" type="text" value="NIP Pegawai" />
					</div>
					<div id="searchTanggal">
						<input name="tanggal" type="text" value="hh-BB-tttt" />
						<input name="command" type="submit" value="CARI" />
					</div>
				</form>
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
			<div id="control">
				<div id="judul2"><b>CONTROL</b></div>
				<a class="button" href="/PresensiManado/admin/Rekap?type=0">Rekap</a><br /> 
				<a class="button" href="/PresensiManado/admin/Presensi?command=close">Tutup Absensi</a><br /> 
		    </div>
        </div>
	    <div id="table">
		    <%
		    Object msg = request.getAttribute("msg");
	    	if(msg != null) {
		    %>
			<%= msg %>
			<% } %>
        	<table border="1">
	    		<tr>
       			  	<th width="100" scope="col">NIP</th>
		  			<th width="200" scope="col">NAMA</th>
		  			<th width="150" scope="col">TANGGAL</th>
		  			<th width="150" scope="col">JAM MASUK</th>
		  			<th width="150" scope="col">JAM PULANG</th>
		  			<th width="150" scope="col">KETERANGAN</th>
        		</tr>
		    	<% @SuppressWarnings("unchecked") List<Presensi> ls = (List<Presensi>)request.getAttribute("rsp"); %>
		    	<% for(Presensi p : ls) { %>
	        		<tr>
    	    		    <td><%= p.getId().getNip() %></td>
        	  			<td><%= p.getPegawai().getNama() %></td>
          				<td><%= p.getId().getTanggal() %></td>
          				<td><%= p.getMasuk() %></td>
       				  	<td><%= p.getKeluar() %></td>
       				  	<td><%= p.getKeterangan() %></td>
       				</tr>
       		  <% } %>
   		  </table>
        </div>
    </div>
	<br /><br />

    <div id="footer"></div>
</body>
</html>