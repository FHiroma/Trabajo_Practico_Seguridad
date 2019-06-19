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
	<h1>Ingrese los datos que desea actualizar de su perfil</h1>
	
	<form:form action="modificar-datos-usuario" method="POST" modelAttribute="usuario">
			    	<h3 class="form-signin-heading">Cargue sus datos</h3>
					<hr class="colorgraph"><br>
					<label>Ingrese Su Nombre:</label>
					<form:input path="nombre" id="nombre" type="text" class="form-control" />
					<br><br>
					<label>Ingrese Su Apellido:</label>
					<form:input path="apellido" id="apellido" type="text"  class="form-control"/>     		  
					<br><br>
					<label>Ingrese Su Nueva Password:</label>
					<form:input path="password" id="password" type="password"  class="form-control"/>     		  
					<br><br>
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Modificar</button>
	</form:form>
	
	<a href="http://localhost:8080/proyecto-limpio-spring/actualizar-password"> 
				<h3 class="form-signin-heading">Actualizar Contraseña</h3> </a>
</body>
</html>