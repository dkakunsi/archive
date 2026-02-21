<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Form Pegawai - Sistem Presensi</title>
</head>

<body>
	<%@ page import="edu.dkakunsi.ta.presensi.model.entity.Pegawai" %>
	<div class="header">
	</div>
	<div class="container">
	  <% Pegawai p = (Pegawai)request.getAttribute("rsp"); %>
	  <% if(p != null) { %>
	      <form action="/PresensiManado/admin/Pegawai" method="get">
		  <table width="200" border="0">
    	    <tr>
        	  <td>NIP</td>
	          <td>:</td>
    	      <td><input name="nip" type="text" value="<%= p.getNip() %>" /></td>
        	</tr>
        	<tr>
        	  <td>NAMA</td>
        	  <td>:</td>
        	  <td><input name="nama" type="text" value="<%= p.getNama() %>" /></td>
        	</tr>
        	<tr>
        	  <td>GOLONGAN</td>
        	  <td>:</td>
        	  <td><input name="golongan" type="text" value="<%= p.getGolongan() %>" /></td>
        	</tr>
        	<tr>
        	  <td>DEPARTEMEN</td>
        	  <td>:</td>
        	  <td><input name="department" type="text" value="<%= p.getDepartment().getId() %>" /></td>
        	</tr>
        	<tr>
        	  <td colspan="2">&nbsp;</td>
        	  <td>
        	  	<input name="command" type="submit" value="batal" />
        	  	<input name="command" type="submit" value="simpan" />
        	  </td>
        	</tr>
      	</table>
      	</form>
      <% } else { %>
	      <form action="/PresensiManado/admin/Pegawai" method="get">
		  <table width="200" border="0">
    	    <tr>
        	  <td>NIP</td>
	          <td>:</td>
    	      <td><input name="nip" type="text" /></td>
        	</tr>
        	<tr>
        	  <td>NAMA</td>
        	  <td>:</td>
        	  <td><input name="nama" type="text" /></td>
        	</tr>
        	<tr>
        	  <td>GOLONGAN</td>
        	  <td>:</td>
        	  <td><input name="golongan" type="text" /></td>
        	</tr>
        	<tr>
        	  <td>DEPARTEMEN</td>
        	  <td>:</td>
        	  <td><input name="department" type="text" /></td>
        	</tr>
        	<tr>
        	  <td colspan="2">&nbsp;</td>
        	  <td>
        	  	<input name="command" type="submit" value="batal" />
        	  	<input name="command" type="submit" value="simpan" />
        	  </td>
        	</tr>
      	</table>
      	</form>
      <% } %>
    </div>
	<div class="footer"></div>
</body>
</html>
