<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://code.jquery.com/jquery-2.2.4.js"
	integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
	crossorigin="anonymous"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

 <!-- Adicionando JQuery -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
</head>
<body>

<a href="acessoliberado.jsp">Inicio</a>
<a href="index.jsp">Sair</a>
	<center>
		<h1>Cadastro de usuário</h1>
		<h3 style="color: orange;">${msg}</h3>
	</center>

	<form action="UsuarioServlet" method="post" id="formUser"
		onsubmit="return validarCampos()" enctype="multipart/form-data" >
		<ul class="form-style-1">
			<li>
				<table style="width: 550px">
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
						value="${user.id}" class="field-long"></td>
							
						<td>Cep:</td>
					    <td><input type="text" id="cep" name="cep" onblur="consultaCep();"
				     	value="${user.cep}"></td>
					</tr>
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login"
						value="${user.login}"></td>
							
						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua" 
						value="${user.rua}"></td>
					</tr>

					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha"
						value="${user.senha}"></td>
						<td>Bairro:</td>
					    <td><input type="text" id="bairro" name="bairro" 
					    value="${user.bairro}"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome"
						value="${user.nome}"></td>
							
						<td>Cidade:</td>
					    <td><input type="text" id="cidade" name="cidade" 
					    value="${user.cidade}"></td>
					</tr>

					<tr>
					    <td>Estado:</td>
					    <td><input type="text" id="uf" name="uf" 
					    value="${user.uf}"></td>
					    
					    <td>Ativo:</td>
						<td><input type="checkbox" id= "ativo" name="ativo" ${user.ativo ? 'checked': ''} ></td>
					</tr>
					
					<tr>
						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge" 
						value="${user.ibge}"></td>
						
						<td>Sexo:</td>
						<td>
						<input type ="radio" ${user.sexo eq 'masculino'? 'checked': ''} name ="sexo" value ="masculino">Masculino
						<input type ="radio" ${user.sexo eq 'feminino'? 'checked': ''} name ="sexo" value ="feminino">Feminino
						
						</td>
						
					</tr>
					
					<tr>
						<td>Foto:</td>
						<td><input type= "file" name="foto" value="foto">

						</td>
						
						<td>Perfil:</td>
						<td>
						<select id="perfil" name="perfil">
							<option value="selecione">[---Selecione--]</option>
							<option ${user.perfil eq'analista'? 'selected':'' } value="analista">Analista de Sistemas</option>
							<option ${user.perfil eq'gerente'? 'selected':'' } value="gerente">Gerente</option>
							<option ${user.perfil eq'coordenador'? 'selected':'' } value="coordenador">coordenador</option>
						
						</select>
						
						
						</td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar">
						<input type="submit" value="Cancelar"
						onclick="document.getElementById('formUser').action = 'UsuarioServlet?acao=reset'"></td>
					</tr>
				</table>

			</li>
		</ul>
	</form>
	
	<form method="post" action="pesquisaServlet">
	<ul class="form-style-1">
	<li>
		<table>
			<tr>
				<td>Descrição</td>
				<td><input type="text" name="descricao" id="descricao"></td>
				<td><input type="submit" value="pesquisar"></td>
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
				<th>Login</th>
				<th>Foto</th>
				<th>Nome</th>
				<th>Cep</th>
				<th>Rua</th>
				<th>Bairro</th>
				<th>Cidade</th>
				<th>Uf</th>
				<th>Ibge</th>
				<th>Delete</th>
				<th>Editar</th>
				<th>Fone</th>
			</tr>
			
			
			<c:forEach items="${usuarios}" var="user">
				<tr>
					<td style="width: 150px"><c:out value="${user.id}">
						</c:out></td>
					<td style="width: 150px"><c:out value="${user.login}">
						</c:out></td>
					<c:if test="${user.fotoBase64Miniatura != null}">
					<td><a href="UsuarioServlet?acao=download&user=${user.id}"><img src='<c:out value="${user.fotoBase64Miniatura}" ></c:out>' alt="Imagem Usuario" width="40px" height="40px"></a></td>
					
					</c:if>
					
					<c:if test="${user.fotoBase64Miniatura == null}">
					<td><img src='resources/imagens/userPadrao.png' alt="Imagem Usuario" width="40px" height="40px"></td>
					
					</c:if>
					
					<td><c:out value="${user.nome}"></c:out></td>
					<td><c:out value="${user.cep}"></c:out></td>
					<td><c:out value="${user.rua}"></c:out></td>
					<td><c:out value="${user.bairro}"></c:out></td>
					<td><c:out value="${user.cidade}"></c:out></td>
					<td><c:out value="${user.uf}"></c:out></td>
					<td><c:out value="${user.ibge}"></c:out></td>

					<td><a  href="UsuarioServlet?acao=delete&user=${user.id}" onclick="return confirm('Deseja excluir?');" ><img
							src="resources/imagens/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px" >  </a></td>
					<td><a href="UsuarioServlet?acao=editar&user=${user.id}"><img
							alt="Editar" title="Editar" src="resources/imagens/editar.png"
							width="20px" height="20px"></a></td>
							
							<td><a href="TelefoneServlet?acao=listar&user=${user.id}"><img
							alt="Telefones" title="Telefones" src="resources/imagens/fone.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {

			if (document.getElementById("login").value == '') {
				alert("Informe um Login");
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert("Informe uma senha");
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert("Informe um nome");
				return false;

			} else if (document.getElementById("telefone").value == '') {
				alert("informe um telefone");
				return false;
			} else {
				return true;

			}

		}
		
		
		function consultaCep(){
			
			var cep = $("#cep").val();
			
			
			//Consulta o webservice viacep.com.br/
            $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
           

                if (!("erro" in dados)) {
                    //Atualiza os campos com os valores da consulta.
                    $("#rua").val(dados.logradouro);
                    $("#bairro").val(dados.bairro);
                    $("#cidade").val(dados.localidade);
                    $("#uf").val(dados.uf);
                    $("#ibge").val(dados.ibge);
                } //end if.
                else {
                    //CEP pesquisado não foi encontrado.
                    limpa_formulário_cep();
                    alert("CEP não encontrado.");
                }
                
            });
			
		}
      
	</script>

</body>
</html>