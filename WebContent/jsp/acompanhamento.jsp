<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formConsultarCPF.action = acao;
		formConsultarCPF.submit();
	}

	function verificar() {
		var msgComando = "${msgComando}";
		var msgAuxiliar = "${msgAuxiliar}";

		switch(msgComando) {
			
		}
	}
	
	function visualizarBoleto(idCobranca) {
		formConsultarCPF.idCobranca.value = idCobranca;
		formConsultarCPF.exibir.value = "true";
		formConsultarCPF.action = "exibirBoleto";
		formConsultarCPF.setAttribute("target", "_blank");
		formConsultarCPF.submit();
		formConsultarCPF.removeAttribute("target");
	}

	function goBack() {
		formConsultarCPF.action = "inicio";
		formConsultarCPF.submit();
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
	<form name=formConsultarCPF method="POST" action="formConsultarCPF" onSubmit="return false;">
      <input type="hidden" name="idCobranca">
      <input type="hidden" name="exibir">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Registro de Pagamentos</h4>
				</div>
				<div class="panel-body">
					<div class="row">
       					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
				            <div class="well well-sm">
				                <div class="row">
				                    <div class="col-md-12" style="text-align: center;">
				                        <h4>
				                        	${ usuario.nome }
				                        </h4>
				                        <small>
											<cite><i class="glyphicon glyphicon-map-marker"></i> ${ usuario.enderecoFormatado }</cite>
										</small>
										<c:if test="${not empty usuario.cpfFormatado }">
											<p id="dadosUsu"><i class="glyphicon glyphicon-credit-card"></i> CPF: ${ usuario.cpfFormatado }</p>
										</c:if>
										<c:if test="${not empty usuario.email }">
											<p id="dadosUsu"><i class="glyphicon glyphicon-envelope"></i> ${ usuario.email }</p>
										</c:if>
										<c:if test="${not empty usuario.dataNascimento }">
											<p id="dadosUsu"><i class="glyphicon glyphicon-calendar"></i> ${ usuario.dataNascimento }</p>
										</c:if>
										<c:if test="${not empty usuario.telefoneFormatado }">
											<p id="dadosUsu"><i class="glyphicon glyphicon-phone"></i> ${ usuario.telefoneFormatado }</p>
										</c:if>
				                    </div>
				                </div>
				            </div>
				        </div>
					</div>
					<div class="row margin-table">
						<c:if test="${not empty lEntidade}">
							<div id="div-grid" class="panel panel-table">
								<div class="table-responsive">
									<display:table name="sessionScope.lEntidade" id="row" class="table table-striped " requestURI="/acompanhamento"	excludedParams="execCons" decorator="br.com.pe.urbana.wrapper.ConsultaWrapper">
										<display:column property="nossoNumero" class="col-lg-4" title="Nº Documento"/>
										<display:column property="dataVencimentoFormatada" class="col-lg-3" title="Data Vencimento"/>
										<display:column property="valorFormatado" class="col-lg-3" title="Valor"/>
										<display:column property="statusCobranca" class="col-lg-1" title="Status"/>
										<display:column property="exibirBoleto" class="col-lg-1" title="Visualizar"/>
									</display:table>
								</div>
							</div>
						</c:if>
					</div>
				</div>
				<div class="panel-footer btAcoes">
					<input class="btn btn-success" type="button" onclick="javascript:goBack();" readonly="readonly" id="voltar" value='VOLTAR' name="cmdVoltar"/>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
