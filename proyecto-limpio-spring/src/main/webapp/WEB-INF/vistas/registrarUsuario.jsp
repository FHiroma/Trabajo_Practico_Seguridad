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
	<h1> Hola</h1>
	
	<form:form action="registrarUsuario" method="POST" modelAttribute="usuario">
					<%--Elementos de entrada de datos, el elemento path debe indicar en que atributo del objeto usuario se guardan los datos ingresados--%>
					<form:input path="email" id="email" type="email"  class="form-control"/>
					<br><br>
					<form:input path="password" type="password" id="password"  class="form-control"/> 
					<br><br>
					<form:input path="nombre" type="text" id="nombre" placeholder="opcional" class="form-control"/>
					<br><br>
					<form:input path="apellido" type="text" id="apellido" placeholder="opcional" class="form-control"/>
					<br><br> 
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Registrarse</button>
	</form:form>
</body>
</html>