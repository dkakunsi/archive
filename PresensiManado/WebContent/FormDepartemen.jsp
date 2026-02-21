<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Form Departemen - Sistem Presensi</title>
</head>

<body>
	<%@ page import="edu.dkakunsi.ta.presensi.model.entity.Department" %>
	<div class="header">
	</div>
	<div class="container">
	  <% Department dept = (Department)request.getAttribute("rsp"); %>
	  <% if(dept != null) { %>
        <form action="/PresensiManado/admin/Department" method="get">
	    <table width="200" border="0">
          <tr>
            <td>KODE</td>
            <td>:</td>
            <td><input name="kode" type="text" value="<%= dept.getId() %>" /></td>
          </tr>
          <tr>
            <td>NAMA</td>
            <td>:</td>
            <td><input name="nama" type="text" value="<%= dept.getNama() %>" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td><input name="command" type="submit" value="batal" />
          	  <input name="command" type="submit" value="simpan" /></td>
          </tr>
        </table>
        </form>
      <% } else { %>
        <form action="/PresensiManado/admin/Department" method="get">
	    <table width="200" border="0">
          <tr>
            <td>KODE</td>
            <td>:</td>
            <td><input name="kode" type="text" /></td>
          </tr>
          <tr>
            <td>NAMA</td>
            <td>:</td>
            <td><input name="nama" type="text" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td><input name="command" type="submit" value="batal" />
            	<input name="command" type="submit" value="simpan" /></td>
          </tr>
        </table>
        </form>
      <% } %>
    </div>
	<div class="footer"></div>
</body>
</html>
