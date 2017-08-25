<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formSolicitar.action = acao;
		formSolicitar.submit();
	}

	function verificar() {
		var msgComando = "${msgComando}";
		var msgAuxiliar = "${msgAuxiliar}";
	
		switch(msgComando) {
		case "1":
			formSolicitar.action = "imprimirBoleto";
			formSolicitar.imprimir.value = "true";
			formSolicitar.submit();
			break;

		case "2":
			bootbox.alert(msgAuxiliar,function(){
				history.go(-1);
			})
			break;
			
		case "3":
			$("#loader").modal("hide");
			bootbox.alert(msgAuxiliar,function(){
				formSolicitar.cadastrarUser.value = "true";
				formSolicitar.action = "cadastroUser";
				formSolicitar.submit();
			})
			break;

		case "4":
			bootbox.alert(msgAuxiliar,function(){
				formSolicitar.action = "inicio";
				formSolicitar.submit();
			})
			break;
		}
	}
	
	function confirmarSolic() {
		$('#modalAtencao').modal('show');
	}

	function solicitarCartao() {
		$("#loader").modal("show");
		$("#btnOk").prop("disabled",true);
		formSolicitar.usrIdOrigem.value = "${ usuario.usrIdOrigem }";
		formSolicitar.solicitar.value = "true";
		formSolicitar.action = "solicitarCartao";
		formSolicitar.submit();
	}
		
	function alterarCadastro() {
		formSolicitar.usrIdOrigem.value = "${ usuario.usrIdOrigem }";
		formSolicitar.alterarCad.value = "true";
		formSolicitar.action = "cadastroUsuarioNovo";
		formSolicitar.submit();
	}

	function cancelar() {
		formSolicitar.action = "inicio";
		formSolicitar.submit();
	}
	
	$(document).keypress(function(e) {
		if(e.which == 13) confirmarSolic();
	});
	
	$(document).keydown(function(e) {
	    if (e.keyCode == 27) return false;
	});
	
</script>
</head>
<body onload="verificar()">
	<form name=formSolicitar method="POST" action="formSolicitar" onSubmit="return false;">
      <input type="hidden" name="imprimir">
      <input type="hidden" name="solicitar">
      <input type="hidden" name="alterarCad">
      <input type="hidden" name="altCadastro">
      <input type="hidden" name="cadastrarUser">
      <input type="hidden" name="usrIdOrigem">
      <input type="hidden" name="nome" value="${ usuario.nome }">
      <input type="hidden" name="cpf" value="${ usuario.cpf }"> 
	  <input type="hidden" name="dataNascimento" value="${ usuario.dataNascimento }">
	  <input type="hidden" name="nomeMae" value="${ usuario.nomeMae }">
	  <input type="hidden" name=telefone value="${ usuario.telefone }">
	  <input type="hidden" name="email" value="${ usuario.email }">
	  <input type="hidden" name="cep" value="${ usuario.endereco.cep }">
	  <input type="hidden" name="logradouro" value="${ usuario.endereco.logradouro }">
	  <input type="hidden" name="bairro" value="${ usuario.endereco.bairro }">
	  <input type="hidden" name="cidade" value="${ usuario.endereco.cidade }">
	  <input type="hidden" name="uf" value="${ usuario.endereco.uf }">
	  <input type="hidden" name="complemento" value="${ usuario.endereco.complemento }">
	  <input type="hidden" name="numero" value="${ usuario.endereco.numero }">
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
			              			<h3 class="panel-title">${ usuario.nome }</h3>
			            		</div>
			            		<div class="panel-body">
			              			<div class="row">
			                			<div class="col-md-3 col-lg-3 " align="center">
			                				<img src="imagens/comum-avatar.png" alt="img01" class="img-rounded img-responsive">
			                			</div>
							                <div class=" col-md-9 col-lg-9 ">
							                	<table class="table table-user-information">
							                    	<tbody>
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
								                        <tr>
								                      		<td>Cep:</td>
								                      		<td>
																<c:if test="${empty usuario.endereco.cep}">Não Informado</c:if>
																<c:if test="${not empty usuario.endereco.cep }">${ usuario.endereco.cep }</c:if>
															</td>
								                      	</tr>
								                      	<tr>
								                      		<td>Logradouro:</td>
								                      		<td>
																<c:if test="${empty usuario.endereco.logradouro}">Não Informado</c:if>
																<c:if test="${not empty usuario.endereco.logradouro }">${ usuario.endereco.logradouro }</c:if>
															</td>
								                      	</tr>
								                      	<tr>
								                      		<td>Bairro:</td>
								                      		<td>
																<c:if test="${empty usuario.endereco.bairro}">Não Informado</c:if>
																<c:if test="${not empty usuario.endereco.bairro }">${ usuario.endereco.bairro }</c:if>
															</td>
								                      	</tr>
								                      	<tr>
								                      		<td>Cidade:</td>
								                      		<td>
																<c:if test="${empty usuario.endereco.cidade}">Não Informado</c:if>
																<c:if test="${not empty usuario.endereco.cidade }">${ usuario.endereco.cidade }</c:if>
															</td>
								                      	</tr>
								                      	<tr>
								                      		<td>UF:</td>
								                      		<td>
																<c:if test="${empty usuario.endereco.uf}">Não Informado</c:if>
																<c:if test="${not empty usuario.endereco.uf }">${ usuario.endereco.uf }</c:if>
															</td>
								                      	</tr>
								                      	<tr>
								                      		<td>Complemento:</td>
								                      		<td>
																<c:if test="${empty usuario.endereco.complemento}">Não Informado</c:if>
																<c:if test="${not empty usuario.endereco.complemento }">${ usuario.endereco.complemento }</c:if>
															</td>
								                      	</tr>
								                      	<tr>
								                      		<td>Número:</td>
								                      		<td>
																<c:if test="${empty usuario.endereco.numero}">Não Informado</c:if>
																<c:if test="${not empty usuario.endereco.numero }">${ usuario.endereco.numero }</c:if>
															</td>
								                      	</tr>
													</tbody>
							                  	</table>
							                </div>
										</div>
									</div>
								<div class="panel-footer btAcoes">
									<input type="button" class="btn btn-success" onclick="javascript:confirmarSolic();" id="btnOk" value="CONFIRMAR">
									<input type="button" class="btn btn-warning" onclick="javascript:alterarCadastro();" id="btnEdit" value="ALTERAR">
									<input type="button" class="btn btn-danger" onclick="javascript:cancelar();" id="btnCanc" value="CANCELAR">
								</div>
							</div>
		          		</div>
		        	</div>
		    	</div>
			</div>
		</div>
	</form>	
	<!-- Modal Atenção -->
	<div class="modal fade" id="modalAtencao" tabindex="-1" role="dialog" aria-labelledby="modalAtencao" aria-hidden="true">
		<div class="modal-dialog modalLogin" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<b class="modal-title" id="modalAtencao">Atenção!</b>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>Deseja realmente confirmar a compra do cartão Comum?</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" data-dismiss="modal" onclick="javascript:solicitarCartao();">SIM</button>
					<button type="button" class="btn btn-success" data-dismiss="modal" onclick="javascript:cancelar();">NÃO</button>
				</div>
			</div>
		</div>	
	</div>
	<!-- Modal Loader -->	
	<div class="modal fade modalLogin" id="loader" tabindex="-1" role="dialog" aria-labelledby="modalLoader" aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-loader">
			<img src="imagens/loader-boleto.svg" class="icon"/>
		</div>
	</div>
</body>
</html>
