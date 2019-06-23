<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Recupera Tu Contraseña</h1>
	<h3>Ingrese su correo electronico</h3>
	
	<form:form action="validar-email" method="POST" modelAttribute="usuario">
					<label>Email:</label>
					<form:input path="email" id="email" type="email" class="form-control" />
					<br><br>
					<div class="g-recaptcha"
					 data-sitekey="6Ld39acUAAAAABo_LFnh7g1cWFHtSIso36UMkHQ3"></div>
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Recuperar Password</button>
				</form:form>
				<script src="https://www.google.com/recaptcha/api.js"></script>	
				
				<c:if test="${not empty error}">
			        <h4><span>${error}</span></h4>
			        <br>
		        </c:if>
</body>
</html>