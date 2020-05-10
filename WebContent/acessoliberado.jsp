<jsp:useBean id="calcula" class="beans.BeanCursoJsp" type="beans.BeanCursoJsp" scope="page"/>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<jsp:setProperty property="*" name="calcula"/>
<h3>Seja bem vindo ao sistema em jsp</h3>

<a href="UsuarioServlet?acao=listartodos"><img src="resources/imagens/usuario.png" width="100px" height="100px" title="Cadastro de Usuarios" alt="Cadastro de Usuarios"></a>

<a href="ProdutosServlet?acao=listartodos"><img src="resources/imagens/produto.png" width="100px" height="100px" title="Cadastro de Produtos" alt="Cadastro de Produtos"></a>



</body>
</html>