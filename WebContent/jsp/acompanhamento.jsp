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
		formConsultarCPF.submit();
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
						<div class="col-md-12">
							<div class="has-success">
								<label class="control-label">Nome: <b class="b-label">${ usuario.nome }</b></label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="has-success">
								<label class="control-label">CPF: <b class="b-label">${ usuario.cpfFormatado }</b></label>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">					
					<div class="row" style="margin: 0 0 0 0;">
						<c:if test="${not empty lEntidade}">
							<div id="div-grid" class="panel panel-primary" style="margin-top: 20px;">
								<div class="table-responsive">
									<display:table name="sessionScope.lEntidade" id="row" class="table table-striped " requestURI="/acompanhamento"	excludedParams="execCons" decorator="br.com.pe.urbana.wrapper.ConsultaWrapper">
										<display:column property="nossoNumero" class="col-lg-4" title="Número Documento"/>
										<display:column property="dataVencimentoFormatada" class="col-lg-3" title="Data Vencimento"/>
										<display:column property="valorFormatado" class="col-lg-3" title="Valor do Boleto"/>
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
