<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formConsultaCartao.action = acao;
		formConsultaCartao.submit();
	}

	function verificar() {
		var msgComando = "${msgComando}";
		var msgAuxiliar = "${msgAuxiliar}";
		var numCartao = "${numCartao}";

		if(msgComando == 1) {
			bootbox.alert(msgAuxiliar,function(){
				formConsultaCartao.action = "consultaCartao";
				formConsultaCartao.submit();
			})
		}

		if(msgComando == 2){
			bootbox.alert(msgAuxiliar,function(){
				formConsultaCartao.numCartao.value = numCartao;
				formConsultaCartao.consCartao.value = "true";
				formConsultaCartao.action = "consultaCPF";
				formConsultaCartao.submit();
			})
		}
	}

	function validarCampos() {
		if (document.formConsultaCartao.numero.value == "") {
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
			formConsultaCartao.consultar.value = "true";
			formConsultaCartao.action = "consultaCartao";
			formConsultaCartao.submit();	
		}
	}
	
	function goBack() {
		formConsultaCartao.action = "inicio";
		formConsultaCartao.submit();
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
	<form name=formConsultaCartao method="POST" action="formConsultaCartao" onSubmit="return false;">
      <input type="hidden" name="consultar" value="false">
      <input type="hidden" name="consCartao" value="false">
      <input type="hidden" name="numCartao">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Passo 1 de 4 - Informe o número do Cartão</h4>
				</div>
				<div class="panel-body">
					<div class="row">
            			<div class="col-md-12">
							<div class="input-group has-success">
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
