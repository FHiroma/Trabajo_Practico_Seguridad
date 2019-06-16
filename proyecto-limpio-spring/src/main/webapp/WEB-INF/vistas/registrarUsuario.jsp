<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/checkStrongPass.js"></script>
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet" >
<!-- Bootstrap theme -->
<link href="css/bootstrap-theme.min.css" rel="stylesheet">
<link href="css/checkStrongPassStyle.css" rel="stylesheet" />
</head>
<body>
	<div class="container">
		<div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<form:form action="registrar-usuario" method="POST" modelAttribute="usuario">
				<h3 class="form-signin-heading">Registro</h3>
				<hr class="colorgraph"></hr>
				<%--Elementos de entrada de datos, el elemento path debe indicar en que atributo del objeto usuario se guardan los datos ingresados--%>
				<form:input path="email" id="email" type="email" class="form-control" />
				<form:input path="password" type="password" id="password" class="form-control"/>     		  
				<div class="g-recaptcha"
					 data-sitekey="6Ld39acUAAAAABo_LFnh7g1cWFHtSIso36UMkHQ3"></div>
				<button class="btn btn-lg btn-primary btn-block" Type="Submit">Registrate</button>
				<div id="mensaje"></div>
			</form:form>
			<div class="error">${mensaje}</div>
		</div>
		<script src="https://www.google.com/recaptcha/api.js"></script>		
	</div>
</body>
</html>