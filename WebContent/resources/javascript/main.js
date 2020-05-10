let formulario = document.forms.formulario;
formulario.addEventListener('submit', function(event){
	
	event.preventDefault();
	
	var dados = {};
	dados.nome = formulario.nome.value;
	dados.valor = formulario.valor.value;
	
	var dadosTexto = JSON.stringify(dados);
	
	var xhr = new XMLHttpRequest();
	
	xhr.open('POST', 'ajaxServlet');
	xhr.setRequestHeader("Content-Type", "application/json");
	
	xhr.send(dadosTexto);
	
	
});

