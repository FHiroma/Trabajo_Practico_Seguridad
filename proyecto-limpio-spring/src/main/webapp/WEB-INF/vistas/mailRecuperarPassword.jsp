<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Usuario de Email: ${token.usuario.email} </h1>
	<h3>Si usted solicito el cambio de su password haga click en el link:</h3>
	
	<a href="http://localhost:8080/proyecto-limpio-spring/solicitar-cambio-clave?id=${token.usuario.id}&&token=${token.token}">
	Activar</a>

</body>
</html>