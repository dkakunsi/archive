<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="Style.css" rel="stylesheet" type="text/css" />
<title>Sistem Presensi - Login</title>
</head>

<body>
	<div id="header"></div>
	<center>
	<div id="container">
		<div id="login">
    	    <form action="/PresensiManado/Login" method="post">
		    <table width="200" border="0">
        	  <tr>
            	<td>USER</td>
            	<td>:</td>
            	<td><input name="user" type="text" value="" /></td>
	          </tr>
    	      <tr>
        	    <td>PASSWORD</td>
            	<td>:</td>
	            <td><input name="password" type="password" value="" /></td>
    	      </tr>
        	  <tr>
            	<td>&nbsp;</td>
	            <td>&nbsp;</td>
    	        <td><input name="command" type="submit" value="login" /></td>
	          </tr>
    	    </table>
        	</form>
		</div>
    </div>
    </center>
	<div id="footer">
		<div id="background">
			<img src="images/logo_manado.png" alt="" width="400px" height="400px" />
		</div>
	</div>
</body>
</html>
