<jsp:useBean id="calcula" class="beans.BeanCursoJsp"
	type="beans.BeanCursoJsp" scope="page" />



<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="resources/css/estilo.css">
<title>Aula</title>
</head>
<body>



	<form action="LoginServlet" method="post" class="login-form">

		<div class="login-page">
			<div class="form">

				Login:
				 <input type="text" name="login"> 
				 <br>
				  Senha: 
				  <input
					type="password" name="senha"> 
					<br>
					 <button type="submit"value="Logar">Logar</button>
			</div>
		</div>
	</form>
</body>
</html>