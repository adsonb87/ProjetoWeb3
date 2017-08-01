<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formConsCadastro.action = acao;
		formConsCadastro.submit();
	}

	function verificar() {
		var msgComando = "${ msgComando }";
		var msgAuxCartao = "${ msgAuxCartao }";
		var msgAuxUsuario = "${ msgAuxUsuario }";
		var msgAuxiliar = "${ msgAuxiliar }";
		var cpf = "${usuCpf}";
		var crdSnr = "${crdSnr}";
		var numCartao = "${numCartao}";
		
		switch(msgComando) {
			case "1":
				$('#modalConsulta').modal('show');
				break;
	
			case "2":
				formConsCadastro.cadVincular.value = "true";
				formConsCadastro.action = "vinculacao";
				formConsCadastro.submit();
				break;
	
			case "3":
				bootbox.alert(msgAuxUsuario,function(){
					formConsCadastro.consCadastro.value = "true";
					formConsCadastro.action = "cadastroUsuario";
					formConsCadastro.submit();
				})
				break;
	
			case "4":
				bootbox.alert(msgAuxiliar,function(){
					formConsCadastro.consCadastro.value = "true";
					formConsCadastro.action = "consultaCadastro";
					formConsCadastro.submit();
				})
		}
	}

	function validarCampos() {
		if (document.formConsCadastro.numeroCartao.value == "") {
			bootbox.alert("Para continuar, informe o Número do seu Cartão",function(){})
			return false;
		}

		if (document.formConsCadastro.cpf.value == "") {
			bootbox.alert("Para realizar a consulta é obrigatório informar: o CPF",function(){})
			return false;
		}

		var cpf = document.formConsCadastro.cpf;

		if (cpf.value != "") {
			var cpf_Int = parseInt(cpf.value);
			var valido = (!isNaN(cpf_Int) && (isValidoCNPJ(cpf) || isValidoCPF(cpf.value)));
			if (!valido) {
				bootbox.alert("CPF inválido!",function(){})
				return false;
			}
		}
		return true;
	}

	function limparCampos() {
		$('#numeroCartao').val('');
	}

	function consultarCadastro() {
		if (validarCampos()) {
			formConsCadastro.consCartao.value = "true";
			formConsCadastro.consCpf.value = "true";
			formConsCadastro.action = "consultaCadastro";
			formConsCadastro.submit();
		}
	}

	function goBack() {
		formConsCadastro.action = "inicio";
		formConsCadastro.submit();
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
	<form name=formConsCadastro method="POST" action="formConsCadastro" onSubmit="return false;">
      <input type="hidden" name="consCadastro" value="false">
      <input type="hidden" name="consCpf" value="false">
      <input type="hidden" name="consCartao" value="false">
      <input type="hidden" name="cadVincular" value="false">
      <input type="hidden" name="usuCpf">
      <input type="hidden" name="crdSnr">
      <input type="hidden" name="numCartao">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Consultar Cadastro</h4>
				</div>
				<div class="panel-body">
					<div class="row">
            			<div class="col-md-12">
							<div class="form-group has-success">
								<label class="control-label" for="numeroCartao">Número Cartão</label>
								<div class="input-group">
									<div class="input-group-addon">90.06</div>
							    	<input type="text" class="form-control" id="numeroCartao" name="numeroCartao">
						      	</div>
							</div>
	            		</div>
						<div class="col-md-12">
							<div class="form-group has-success">
								<label class="control-label" for="cpf">CPF</label>
								<input type="text" class="form-control" id="cpf" name="cpf">
							</div>
						</div>
					</div>
				</div>
				<div class="panel-footer btAcoes" style="text-align: center;">
					<input class="btn btn-success" type="submit" onclick="javascript:consultarCadastro();" readonly="readonly" value='CONTINUAR' name="cmdContinuar" >
					<input class="btn btn-success" type="button" onclick="javascript:limparCampos();" id="limpar" value="LIMPAR" readonly="readonly" name="cmdLimpar"/>
					<input class="btn btn-success" type="button" onclick="javascript:goBack();" readonly="readonly" id="voltar" value='VOLTAR' name="cmdVoltar"/>
				</div>
				<!-- Modal Consulta Cadastro -->
				<div class="modal fade" id="modalConsulta" tabindex="-1" role="dialog" aria-labelledby="modalConsulta" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <b class="modal-title" id="modalConsulta">Atenção!</b>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				        <div>
				        	<label>Cartão: </label>
				        	<label class="label-atencao" id="labelOk">${ msgAuxCartao }</label>
				        </div>
				        <div>
				        	<label>Usuário: </label>
				        	<label class="label-atencao" id="labelAten">${ msgAuxUsuario }</label>
				        </div>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-success" data-dismiss="modal">OK</button>
				      </div>
				    </div>
				  </div>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		if($('#labelOk').text() == '') {
			$('#labelOk').text('OK!');
			$('#labelOk').removeClass( "label-atencao" ).addClass( "label-ok" );
		}
		if($('#labelAten').text() == '') {
			$('#labelAten').text('OK!');
			$('#labelAten').removeClass( "label-atencao" ).addClass( "label-ok" );
		}
	</script>
	<script src="js/mask.js"></script>
</body>
</html>
