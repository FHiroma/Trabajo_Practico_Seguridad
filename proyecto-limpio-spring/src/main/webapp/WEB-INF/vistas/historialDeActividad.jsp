<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Historial</title>
</head>
<body>
<a href="#" onclick="history.go(-1)">
<button>Atras</button> </a>	
<c:forEach items="${lista}" var="log">
	<h3>FechaHora: ${log.fechaHora}<br>Acción: <c:choose>
			        <c:when test="${log.codigo == 1}">Login.</c:when>
			        <c:when test="${log.codigo == 2}">Logout.</c:when>
			        <c:when test="${log.codigo == 3}">Se modificaron datos de la cuenta.</c:when>
			        <c:when test="${log.codigo == 4}">Se modificó el texto.</c:when>
			        <c:when test="${log.codigo == 5}">Se recuperó la contraseña</c:when>
			        <c:when test="${log.codigo == 6}">Se registró</c:when>		        
			        <c:otherwise>Unknown</c:otherwise>
    			</c:choose>
    </h3>
</c:forEach>
</body>
</html>