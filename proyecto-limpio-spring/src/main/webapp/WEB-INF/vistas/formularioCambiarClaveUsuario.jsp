<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Ingrese su nueva Contraseña</h2>
	
	<form action="cambiar-clave" method="POST">
			    	<h3 class="form-signin-heading">Seguridad de Aplicaciones</h3>
					<hr class="colorgraph"><br>

					<%--Elementos de entrada de datos, el elemento path debe indicar en que atributo del objeto usuario se guardan los datos ingresados--%>
					
					<form name="token" id="token" type="text" value="${token}" class="form-control" />
					
					<label>Ingrese la password</label>
					<input name="password" id="password" type="password" class="form-control"/>     		  
					<br><br>
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>cambiar clave</button>
	</form>
</body>
</html>