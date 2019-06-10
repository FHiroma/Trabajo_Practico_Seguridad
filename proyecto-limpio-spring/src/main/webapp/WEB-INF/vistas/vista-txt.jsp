<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
	<!-- Bootstrap core CSS -->
	    <link href="css/bootstrap.min.css" rel="stylesheet" >
	    <!-- Bootstrap theme -->
	    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
	</head>
	<body>
<div class = "container">
	<div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<form:form action="texto-ok" method="POST" modelAttribute="usuario">
			<form:input path="text" type="text" class="form-control" />		
			<br>		
			<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Enviar</button>
			</form:form>
			<br><br><br>
			<a href="http://localhost:8080/proyecto-limpio-spring/homeUser">
			<button class="btn btn-lg btn-danger btn-block"/>Atrás</button></a>
	</div>
</div>
		
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</body>
</html>
