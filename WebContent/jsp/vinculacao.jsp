<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formVinculacao.action = acao;
		formVinculacao.submit();
	}

	function verificar() {
		var msgComando = "${msgComando}";
		var msgAuxiliar = "${msgAuxiliar}";

		if(msgComando == 1) {
			bootbox.alert(msgAuxiliar,function(){
				formVinculacao.action = "inicio";
				formVinculacao.submit();
			})
		}
	}

	function vincularCartao() {
		formVinculacao.usrIdCartao.value = "${ usuario.cartao.usrIdCartao }";
		formVinculacao.usrIdOrigem.value = "${ usuario.usrIdOrigem }";
		formVinculacao.vincular.value = "true";
		formVinculacao.action = "vinculacao";
		formVinculacao.submit();
	}
	
	function alterarCadastro() {
		formVinculacao.usrIdOrigem.value = "${ usuario.usrIdOrigem }";
		formVinculacao.numCartao.value = "${ usuario.cartao.numCartao }";
		formVinculacao.crdSnr.value = "${ usuario.cartao.crdSnr }";
		formVinculacao.altCadastro.value = "true";
		formVinculacao.action = "cadastroUsuario";
		formVinculacao.submit();
	}

	function cancelar() {
		formVinculacao.action = "inicio";
		formVinculacao.submit();
	}
	
	$(document).ready(function() {
		$('[data-toggle="tooltip"]').tooltip();
	});

	function sem_letra(e, args) {
		if (document.all) { var evt = event.keyCode;} // caso seja IE
		else { var evt = e.charCode; } // do contrário deve ser Mozilla
		var valid_chars = '0123456789' + args; // criando a lista de teclas permitidas
		var chr = String.fromCharCode(evt); // pegando a tecla digitada
		if (valid_chars.indexOf(chr) > -1) { return true; }
		// se a tecla estiver na lista de permissão permite-a
		// para permitir teclas como <BACKSPACE> adicionamos uma permissão para
		// códigos de tecla menores que 09 por exemplo (geralmente uso menores que 20)
		if (valid_chars.indexOf(chr) > -1 || evt < 9) { return true; } // se a tecla estiver na lista de permissão permite-a
		return false; // do contrário nega
	}
	
</script>
</head>
<body onload="verificar()">
	<form name=formVinculacao method="POST" action="formVinculacao" onSubmit="return false;">
      <input type="hidden" name="vincular">
      <input type="hidden" name="altCadastro">
      <input type="hidden" name="numCartao">
      <input type="hidden" name="crdSnr">
      <input type="hidden" name="idUsuario">
      <input type="hidden" name="usrIdCartao">
      <input type="hidden" name="usrIdOrigem">
      <input type="hidden" name="nome" value="${ usuario.nome }">
      <input type="hidden" name="cpf" value="${ usuario.cpf }"> 
	  <input type="hidden" name="dataNascimento" value="${ usuario.dataNascimento }">
	  <input type="hidden" name="nomeMae" value="${ usuario.nomeMae }">
	  <input type="hidden" name=telefone value="${ usuario.telefone }">
	  <input type="hidden" name="email" value="${ usuario.email }">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Confirmando os Dados</h4>
				</div>
				<div class="panel-body">
					<div class="row">
       					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
   							<div class="panel panel-success">
			            		<div class="panel-heading">
			              			<h3 class="panel-title">90.06.${ usuario.cartao.numCartao }</h3>
			            		</div>
			            		<div class="panel-body">
			              			<div class="row">
			                			<div class="col-md-3 col-lg-3 " align="center">
			                				<img src="imagens/comum-avatar.png" alt="img01" class="img-responsive">
			                			</div>
							                <div class=" col-md-9 col-lg-9 ">
							                	<table class="table table-user-information">
							                    	<tbody>
							                    		<tr>
									                        <td>Nome:</td>
									                        <td>${ usuario.nome }</td>
								                      	</tr>
														<tr>
									                    	<td>CPF:</td>
										                    <td>${ usuario.cpf }</td>
								                      	</tr>
								                      	<tr>
									                    	<td>Data Nascimento:</td>
									                        <td>${ usuario.dataNascimento }</td>
								                      	</tr>
								                      	<tr>
									                        <td>Nome da Mãe:</td>
									                        <td>${ usuario.nomeMae }</td>
								                      	</tr>
								                      	<tr>
								                      		<td>Telefone:</td>
								                      		<td>
																<c:if test="${empty usuario.telefone}">Não Informado</c:if>
																<c:if test="${not empty usuario.telefone }">${ usuario.telefone }</c:if>
								                      		</td>
								                      	</tr>
								                      	<tr>
								                      		<td>Email:</td>
								                      		<td>
																<c:if test="${empty usuario.email}">Não Informado</c:if>
																<c:if test="${not empty usuario.email }">${ usuario.email }</c:if>
															</td>
								                      	</tr>
													</tbody>
							                  	</table>
							                </div>
										</div>
									</div>
								<div class="panel-footer panel-vinculacao">
									<a onclick="return vincularCartao();" data-original-title="Confirmar" data-toggle="tooltip" type="button" class="btn btn-success">
										<i class="glyphicon glyphicon glyphicon-ok"></i></a>
		                        	<a onclick="return alterarCadastro();" data-original-title="Editar" data-toggle="tooltip" type="button" class="btn btn-warning">
		                        		<i class="glyphicon glyphicon-edit"></i></a>
		                           	<a onclick="return cancelar();" data-original-title="Cancelar" data-toggle="tooltip" type="button" class="btn btn-danger">
		                           		<i class="glyphicon glyphicon-remove"></i></a>
		                    	</div>
			          		</div>
			        	</div>
			    	</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
