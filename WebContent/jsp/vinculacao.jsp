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
				formVinculacao.action = "consultaCartao";
				formVinculacao.submit();
			})
		}

		if(msgComando == 2){
			bootbox.alert(msgAuxiliar,function(){
				formVinculacao.numCartao.value = numCartao;
				formVinculacao.consCartao.value = "true";
				formVinculacao.action = "consultaCPF";
				formVinculacao.submit();
			})
		}
	}

	function validarCampos() {
		if (document.formVinculacao.numero.value == "") {
			bootbox.alert("Para continuar, informe o Número do seu Cartão",function(){})
			return false;
		}
		return true;
	}

	function limparCampos() {
		$('#numero').val('');
	}

	function consultarCartao() {
		if (validarCampos()) {
			formVinculacao.consultar.value = "true";
			formVinculacao.action = "consultaCartao";
			formVinculacao.submit();	
		}
	}
	
	function goBack() {
		formVinculacao.action = "inicio";
		formVinculacao.submit();
	}
	
	jQuery(function($){
		$("#numero").mask("99.99.99999999-9");
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
      <input type="hidden" name="consultar" value="false">
      <input type="hidden" name="consCartao" value="false">
      <input type="hidden" name="numCartao">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Vincular</h4>
				</div>
				<div class="panel-body">
					<div class="row">
            			<div class="col-md-12">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon1">Número Cartão:</span>
								<input type="text" class="form-control" placeholder="XX.XX.XXXXXXXX-X" aria-describedby="basic-addon1"
								id="numero" name="numero">
							</div>
						</div>
					</div>
				</div>
				<div class="panel-footer" style="text-align: center;">
					<input class="btn btn-success" type="button" onclick="javascript:consultarCartao();" readonly="readonly" value='CONTINUAR' name="cmdContinuar" >
					<input class="btn btn-success" type="button" onclick="javascript:limparCampos();" id="limpar" value="LIMPAR" readonly="readonly" name="cmdLimpar"/>
					<input class="btn btn-success" type="button" onclick="javascript:goBack();" readonly="readonly" id="voltar" value='VOLTAR' name="cmdVoltar"/>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
