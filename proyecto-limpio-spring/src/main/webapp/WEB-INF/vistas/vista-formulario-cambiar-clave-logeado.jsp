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

	<h1> Actualizar Contraseña </h1>
	
		<form action="cambiar-clave-logeado" method="POST">
					<label>Ingrese Su Contraseña Actual</label>
					<input name="password" id="password" type="password" class="form-control" />
					<br><br>
					<label>Ingrese Su Nueva Password:</label>
					<input name="nvopass" id="nvopass" type="password"  class="form-control"/>     		  
					<br><br>
					<label>Repita Su Contraseña:</label>
					<input name="repeticion" id="repeticion" type="password"  class="form-control"/>     		  
					<br><br>
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Modificar</button>
		</form>
						  
</body>
</html>