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
			case "1":
				bootbox.alert(msgAuxiliar,function(){
					formConsultarCPF.consCadastro.value= "true";
					formConsultarCPF.action = "consultaCPF";
					formConsultarCPF.submit();
				})
				break;

			case "2":
				bootbox.alert(msgAuxiliar,function(){
					formConsultarCPF.altCadastro.value= "true";
					formConsultarCPF.action = "cadastroUsuarioNovo";
					formConsultarCPF.submit();
				})
				break;
				
			case "3":
				bootbox.alert(msgAuxiliar,function(){
					formConsultarCPF.novoCadastro.value= "true";
					formConsultarCPF.action = "cadastroUsuarioNovo";
					formConsultarCPF.submit();
				})
		}
	}


	function validarCampos() {
		if (document.formConsultarCPF.cpf.value == "") {
			bootbox.alert("Para realizar a consulta é obrigatório informar: o CPF",function(){})
			return false;
		}

		var cpf = document.formConsultarCPF.cpf;

		if (cpf.value != "") {
			var cpf_Int = parseInt(cpf.value);
			var valido = (!isNaN(cpf_Int) && (isValidoCNPJ(cpf) || isValidoCPF(cpf.value)));
			if (!valido) {
				bootbox.alert("CPF inválido!",function(){
					formConsultarCPF.action = "consultaCPF";
					formConsultarCPF.submit();
				})
				return false;
			}
		}

		return true;
	}

	function limparCampos() {
		$('#cpf').val('');
	}

	function consultarUsuario() {
		if (validarCampos()) {
			formConsultarCPF.consultar.value = "true";
			formConsultarCPF.action = "consultaCPF";
			formConsultarCPF.submit();
		}
	}

	function goBack() {
		formConsultarCPF.action = "inicio";
		formConsultarCPF.submit();
	}

	function goBackInicio() {
		formConsultarCPF.action = "inicio";
		formConsultarCPF.submit();
	}

	jQuery(function($){
		$("#cpf").mask("999.999.999-99");
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
	<form name=formConsultarCPF method="POST" action="formConsultarCPF" onSubmit="return false;">
      <input type="hidden" name="consultar">
      <input type="hidden" name="consCadastro">
      <input type="hidden" name="altCadastro">
      <input type="hidden" name="novoCadastro">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Informe seu CPF</h4>
				</div>
				<div class="panel-body">
					<div class="row">
            			<div class="col-md-12">
							<div class="form-group has-success">
								<label class="control-label" for="cpf">CPF</label>
								<input type="text" class="form-control" placeholder="Informe o CPF" value="${ usuario.cpf }" aria-describedby="basic-addon1"
								id="cpf" name="cpf">
							</div>
						</div>
					</div>
				</div>
				<div class="panel-footer btAcoes">
					<input class="btn btn-success" type="submit" onclick="javascript:consultarUsuario();" readonly="readonly" value='CONTINUAR' name="cmdContinuar"/>
					<input class="btn btn-success" type="button" onclick="javascript:limparCampos();" id="limpar" value="LIMPAR" readonly="readonly" name="cmdLimpar"/>
					<input class="btn btn-success" type="button" onclick="javascript:goBack();" readonly="readonly" id="voltar" value='VOLTAR' name="cmdVoltar"/>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
