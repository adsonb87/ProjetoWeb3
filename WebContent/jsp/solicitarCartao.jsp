<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formSolicitacao.action = acao;
		formSolicitacao.submit();
	}

	function verificar() {
		var msgComando = "${msgComando}";
		var msgAuxiliar = "${msgAuxiliar}";

		if(msgComando == 1) {
			bootbox.alert(msgAuxiliar,function(){
				formSolicitacao.action = "inicio";
				formSolicitacao.submit();
			})
		}
	}

	function solicitarCartao() {
		formSolicitacao.usrId.value = "${ usuario.usrId }";
		formSolicitacao.idUsuario.value = "${ usuario.id }";
		formSolicitacao.solicitar.value = "true";
		formSolicitacao.action = "solicitarCartao";
		formSolicitacao.submit();	
	}
	
	function cancelar() {
		formSolicitacao.action = "inicio";
		formSolicitacao.submit();
	}

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
	<form name=formSolicitacao method="POST" action="formSolicitacao" onSubmit="return false;">
      <input type="hidden" name="solicitar" value="false">
      <input type="hidden" name="usrId">
      <input type="hidden" name="idUsuario">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Solicitar Cartão</h4>
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
			                				<img src="imagens/comum-avatar.png" alt="img01" class="img-responsive">
			                			</div>	
							                <div class=" col-md-9 col-lg-9 "> 
							                	<table class="table table-user-information">
							                    	<tbody>
														<tr>
									                    	<td>CPF:</td>
									                        <td>
									                        	${ usuario.cpf }
									                        </td>
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
									                    	<td>Rua:</td>
									                        <td>
									                        	<c:if test="${empty usuario.endereco.logradouro}">Não Informado</c:if>
																<c:if test="${not empty usuario.endereco.logradouro }">${ usuario.endereco.logradouro }</c:if>
									                        </td>
								                      	</tr>
								                      	<tr>
								                      		<td>Número:</td>
								                      		<td>								                      			
								                      			<c:if test="${empty usuario.endereco.numero}">Não Informado</c:if>
																<c:if test="${not empty usuario.endereco.numero }">${ usuario.endereco.numero }</c:if>
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
													</tbody>
							                  	</table>	
							                </div>
										</div>
									</div>
								<div class="panel-footer" style="text-align: center;">
									<input class="btn btn-success" type="button" onclick="javascript:solicitarCartao();" readonly="readonly" value='SOLICITAR' name="cmdConfirmar	" >
									<input class="btn btn-success" type="button" onclick="javascript:cancelar();" readonly="readonly" id="canc" value='CANCELAR' name="cmdCancelar"/>
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
