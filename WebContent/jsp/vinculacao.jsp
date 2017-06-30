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
		formVinculacao.numCartao.value = "${ numCartao }";
		formVinculacao.usrId.value = "${ cartao.usuario.usrId }";
		formVinculacao.idUsuario.value = "${ cartao.usuario.id }";
		formVinculacao.vincular.value = "true";
		formVinculacao.action = "vinculacao";
		formVinculacao.submit();
	}

	function cancelar() {
		formVinculacao.action = "inicio";
		formVinculacao.submit();
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
	<form name=formVinculacao method="POST" action="formVinculacao" onSubmit="return false;">
      <input type="hidden" name="vincular" value="false">
      <input type="hidden" name="numCartao">
      <input type="hidden" name="usrId">
      <input type="hidden" name="idUsuario">
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
			              			<h3 class="panel-title">${ numCartao }</h3>
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
									                        <td>
									                        	<p name="nome" value="${ cartao.usuario.nome }">${ cartao.usuario.nome }</p>
									                        </td>
								                      	</tr>
														<tr>
									                    	<td>CPF:</td>
									                        <td>
									                        	<p name="cpf" value="${ cartao.usuario.cpf }">${ cartao.usuario.cpf }</p>
									                        </td>
								                      	</tr>
								                      	<tr>
								                      		<td>Telefone:</td>
								                      		<td>
																<p name=telefone value="${ cartao.usuario.telefone }">
																	<c:if test="${empty cartao.usuario.telefone}">Não Informado</c:if>
																	<c:if test="${not empty cartao.usuario.telefone }">${ cartao.usuario.telefone }</c:if>
																</p>
								                      		</td>
								                      	</tr>
								                      	<tr>
								                      		<td>Email:</td>
								                      		<td>
																<p name="email" value="${ cartao.usuario.email }">
																	<c:if test="${empty cartao.usuario.email}">Não Informado</c:if>
																	<c:if test="${not empty cartao.usuario.email }">${ cartao.usuario.email }</c:if>
																</p>
															</td>
								                      	</tr>
								                      	<tr>
									                    	<td>Rua:</td>
									                        <td>
									                        	<p name="logradouro" value="${ cartao.usuario.endereco.logradouro }">
									                        		<c:if test="${empty cartao.usuario.endereco.logradouro}">Não Informado</c:if>
																	<c:if test="${not empty cartao.usuario.endereco.logradouro }">${ cartao.usuario.endereco.logradouro }</c:if>
									                        	</p>
									                        </td>
								                      	</tr>
								                      	<tr>
								                      		<td>Número:</td>
								                      		<td>
								                      			<p name="numero" value="${ cartao.usuario.endereco.numero }">
								                      				<c:if test="${empty cartao.usuario.endereco.numero}">Não Informado</c:if>
																	<c:if test="${not empty cartao.usuario.endereco.numero }">${ cartao.usuario.endereco.numero }</c:if>
								                      			</p>
								                      		</td>
								                      	</tr>
								                      	<tr>
								                      		<td>Cidade:</td>
								                      		<td>
								                      			<p name="cidade" value="${ cartao.usuario.endereco.cidade }">
								                      				<c:if test="${empty cartao.usuario.endereco.cidade}">Não Informado</c:if>
																	<c:if test="${not empty cartao.usuario.endereco.cidade }">${ cartao.usuario.endereco.cidade }</c:if>
								                      			</p>
								                      		</td>
								                      	</tr>
								                      	<tr>
								                      		<td>UF:</td>
								                      		<td>
								                      			<p name="uf" value="${ cartao.usuario.endereco.uf }">
								                      				<c:if test="${empty cartao.usuario.endereco.uf}">Não Informado</c:if>
																	<c:if test="${not empty cartao.usuario.endereco.uf }">${ cartao.usuario.endereco.uf }</c:if>
								                      			</p>
								                      		</td>
								                      	</tr>
								                      	<tr>
								                      		<td>Complemento:</td>
								                      		<td>
								                      			<p name="complemento" value="${ cartao.usuario.endereco.complemento }">
								                      				<c:if test="${empty cartao.usuario.endereco.complemento}">Não Informado</c:if>
																	<c:if test="${not empty cartao.usuario.endereco.complemento }">${ cartao.usuario.endereco.complemento }</c:if>
								                      			</p>
								                      		</td>
								                      	</tr>
													</tbody>
							                  	</table>
							                </div>
										</div>
									</div>
								<div class="panel-footer btAcoes" style="text-align: center;">
									<input class="btn btn-success" type="button" onclick="javascript:vincularCartao();" readonly="readonly" value='CONFIRMAR' name="cmdConfirmar	" >
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
