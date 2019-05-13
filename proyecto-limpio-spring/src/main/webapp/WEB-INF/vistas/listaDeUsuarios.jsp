<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:if test="${not empty mensaje1}">
		        <h2>Usuario ${mensaje} ha sido activado.</h2>
		        <br>
</c:if>	
<c:if test="${not empty mensaje2}">
		        <h2>Usuario ${mensaje2} ha sido desactivado.</h2>
		        <br>
</c:if>	

<c:forEach items="${lista}" var="usuario">
	<h1>Email: ${usuario.email}</h1><br>
		<c:if test="${usuario.estado == false}">
		        <h4><a href="http://localhost:8080/proyecto-limpio-spring/activar-usuario?id=${usuario.id}">Activar</a></h4>
		        <br>
        </c:if>
        <c:if test="${usuario.estado == true}">
		        <h4><a href="http://localhost:8080/proyecto-limpio-spring/desactivar-usuario?id=${usuario.id}">Desactivar</a></h4>
		        <br>
        </c:if>	
</c:forEach>
</body>
</html>