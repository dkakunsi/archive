<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="Style.css" rel="stylesheet" type="text/css" />
<title>Sistem Presensi Manado</title>
</head>

<body>
  	<div id="logo">
  		<img src="images/logo_manado.png" alt="Logo Manado" height="150px" width="175px" />
   	</div>

    <div id="header">
    	<p>Sistem Presensi Menggunakan Web Service</p>
    </div>
	
    <div id="container">
		<div id="tools">
	    	<div id="judul1"><b>MENU</b></div>
        	<div id="search" />
		    <div id="nav">
		    	<div id="judul2"><b>NAVIGASI</b></div>
				<a class="button" href="/PresensiManado/admin/Department">Departemen</a><br /> 
				<a class="button" href="/PresensiManado/admin/Pegawai">Pegawai</a><br /> 
				<a class="button" href="/PresensiManado/admin/Presensi">Daftar Hadir</a><br /> 
				<a class="button" href="/PresensiManado/admin/Izin">Daftar Izin</a><br /> 
				<a class="button" href="/PresensiManado/admin/Lembur">Daftar Lembur</a><br /> 
				<a class="button" href="/PresensiManado/Login.jsp">Login</a>
		    </div>
        </div>
	    <div id="table">
        </div>
    </div>
	<br /><br />

    <div id="footer"></div>
</body>
</html>