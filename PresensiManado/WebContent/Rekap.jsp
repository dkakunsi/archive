<%@page import="edu.dkakunsi.ta.presensi.util.DateTime"%>
<%@page import="edu.dkakunsi.ta.presensi.model.rekapmodel.Rekap"%>
<%@page import="java.util.List" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../Style.css" rel="stylesheet" type="text/css" />
<title>Data Presensi - Sistem Presensi</title>
</head>

<body>
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
					<div id="searchNip">
						<select name="type">  
							<option value="">-Pilih-</option>  
							<option value="presensi">Presensi</option>  
							<option value="lembur">Lembur</option>  
						</select>
 					</div>
					<div id="searchDepartment">
						<input name="dept" type="text" value="Kode Departemen" />
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
				<a class="button"
					href="/PresensiManado/admin/Report?type=<%=request.getAttribute("type")%>&bulan=<%=request.getAttribute("bulan")%>&tahun=<%=request.getAttribute("tahun")%>&dept=<%=request.getAttribute("dept")%>">
					Cetak</a><br /> 
		    </div>
        </div>
	    <div id="table">
       		<%
       		String type = (String)request.getAttribute("type");
       		@SuppressWarnings("unchecked")
       		List<Rekap> ls = (List<Rekap>)request.getAttribute("rsp");
       		if(((ls!=null) && (ls.size()>0)) && (type.equals("presensi") || type.equals("izin") || type.equals("lembur"))) { %>
	        	<table border="1">
		    		<tr>
   	   				  	<th width="100" scope="col">NIP</th>
		  				<th width="200" scope="col">NAMA</th>
   					  	<th width="10" scope="col">GOLONGAN</th>
	  					<th width="200" scope="col">SKPD</th>
			  			<th width="150" scope="col">JUMLAH</th>
    	   			</tr>
       				<% for(Rekap rp : ls) { %>
		    			<tr>
   	   					  	<td><%= rp.getNip() %></td>
   						  	<td><%= rp.getNama() %></td>
   					  		<td><%= rp.getGolongan() %></td>
	    				  	<td><%= rp.getSkpd() %></td>
		       				<td><%= rp.getJumlah() %></td>
       					</tr>
       				<% } %>
       	   		</table>
    			<% } else { %>
        		<%= request.getAttribute("message") %>
        		<% } %>
        </div>
    </div>
	<br /><br />

    <div id="footer"></div>
</body>
</html>