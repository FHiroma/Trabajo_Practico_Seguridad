<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Seguridad en aplicaciones Web</title>
 <link href="css/bootstrap.min.css" rel="stylesheet" >
 <link href="css/bootstrap-theme.min.css" rel="stylesheet">
</head>
<body>
				<div class = "container">
				<div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
								<h3>Tu mensaje guardado: ${mensaje}</h3>
								<br>
								<a href="http://localhost:8080/proyecto-limpio-spring/homeUser">
								<button class="btn btn-lg btn-primary btn-block"/>Volver al Home</button></a>
				</div></div>


		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>