<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Telefones</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

 
</head>
<body>

<a href="acessoliberado.jsp">Inicio</a>
<a href="index.jsp">Sair</a>
	<center>
		<h1>Cadastro de telefones</h1>
		<h3 style="color: orange;">${msg}</h3>
	</center>

	<form action="TelefoneServlet" method="post" id="formUser"
		onsubmit="return validarCampos()">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${userEscolhido.id}"></td>
						
						</tr>
						
						<tr>
						<td>Nome:</td>
						<td><input type="text" readonly="readonly" id="nome" name="nome" value="${userEscolhido.nome}"></td>
						
						</tr>
						<tr>
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero"></td>
						
						</tr>
						<tr>
						<td>Tipo:</td>
						<td>
						
						<select id="tipo" name="tipo">
						<option>Casa</option>
						<option>Celular</option>
						<option>Contato</option>
						
						</select>
						
						</td>
					
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"></td>
					</tr>
				</table>

			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Usuários cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Número</th>
				<th>Tipo</th>
				<th>Excluir</th>
			
			</tr>
			<c:forEach items="${telefones}" var="fone">
				<tr>
					<td style="width: 150px"><c:out value="${fone.id}">
						</c:out></td>
					<td style="width: 150px"><c:out value="${fone.numero}">
						</c:out></td>
					<td><c:out value="${fone.tipo}"></c:out></td>
					
					<td><a href="TelefoneServlet?acao=delete&user=${fone.id}" onclick="return confirm('Deseja excluir?');"><img
							src="resources/imagens/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {

			if (document.getElementById("numero").value == '') {
				alert("Informe um Numero");
				return false;

			}
		
		}
      
	</script>

</body>
</html>