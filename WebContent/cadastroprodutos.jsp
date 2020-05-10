<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <script src="https://code.jquery.com/jquery-2.2.4.js" integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI=" crossorigin="anonymous"></script>
 <script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
  <script src="resources/javascript/jquery.maskMoney.min.js" type="text/javascript"></script>
<script src="resources/javascript/main.js"></script>
 
 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>Cadastro de produtos</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>

<a href="acessoliberado.jsp">Inicio</a>
<a href="index.jsp">Sair</a>
	<center>
		<h1>Cadastro de Produtos</h1>
		<h3 style="color: orange;">${msg}</h3>
	</center>

	<form name="formulario" action="ajaxServlet" method="post" id="formUser" onsubmit="return validarCampos()">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${prod.id}" class="field-long"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome"
							value="${prod.nome}"></td>
					</tr>

					<tr>
						<td>Quantidade:</td>
						<td><input type="text" id="quantidade" name="quantidade"  maxlength="7" 
							value="${prod.quantidade}"></td>
					</tr>
					<tr>
						<td>Valor:</td>
						<td><input type="text" id="valor" name="valor"  data-thousands="." data-decimal=","
							value="${prod.valorAux}"></td>
					</tr>
					
					
					<tr>
						<td>Categoria:</td>
						<td>
							<select id="categorias" name="categoria_id">
								<c:forEach items="${categorias}" var="cat">
									<option value="${cat.id}" id="${cat.id}" ${cat.id == prod.categoria_id ? 'selected' : '' }>
										${cat.nome}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
		
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> <input
							type="submit" value="Cancelar"
							onclick="document.getElementById('formUser').action = 'ProdutosServlet?acao=reset'"></td>
					</tr>
				</table>

			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Produtos cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Quantidade</th>
				<th>Valor</th>
				<th>Delete</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${produtos}" var="prod">
				<tr>
					<td style="width: 150px"><c:out value="${prod.id}">
						</c:out></td>
					<td style="width: 150px"><c:out value="${prod.nome}">
						</c:out></td>
					<td><c:out value="${prod.quantidade}"></c:out></td>
					<td><fmt:formatNumber type="number" minFractionDigits="2" value="${prod.valor}"/></td>

					<td><a href="ProdutosServlet?acao=delete&prod=${prod.id}" onclick=" return confirm('Deseja excluir?');"><img
							src="resources/imagens/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>
					<td><a href="ProdutosServlet?acao=editar&prod=${prod.id}"><img
							alt="Editar" title="Editar" src="resources/imagens/editar.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
		<script type="text/javascript">
		function validarCampos() {

			if (document.getElementById("nome").value == '') {
				alert("Informe um nome");
				return false;
			} else if (document.getElementById("quantidade").value == '') {
				alert("Informe uma quantidade");
				return false;
			} else if (document.getElementById("valor").value == '') {
				alert("Informe um valor");
				return false;
			} else {
				return true;

			}

		}
		
		$(document).ready(function() {
			  $("#quantidade").keyup(function() {
			      $("#quantidade").val(this.value.match(/[0-9]*/));
			  });
			});
	</script>
	
	

</body>
<script>

$(function() {
    $('#valor').maskMoney();

  });
  


</script>
</html>