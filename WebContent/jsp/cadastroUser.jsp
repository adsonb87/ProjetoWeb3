<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formCadUser.action = acao;
		formCadUser.submit();
	}

	function verificar() {
		var msgComando = "${msgComando}";
		var msgAuxiliar = "${msgAuxiliar}";
		
		switch(msgComando) {
			case "1":
				bootbox.alert(msgAuxiliar,function(){
					formCadUser.acompanhar.value = "true";
					formCadUser.action = "acompanhamento";
					formCadUser.submit();
				})
				break;
			
			case "2":
				bootbox.alert(msgAuxiliar,function(){
					formCadUser.cadastrarUser.value = "true";
					formCadUser.action = "cadastroUser";
					formCadUser.submit();
				})
				break;
		}
	}

	function validarCampos() {

		//VALIDANDO CPF
		if (document.formCadUser.cpf.value == "") {
			bootbox.alert("Para continuar, informe o seu CPF!",function(){})
			return false;
		} else {
			var cpf_Int = parseInt(cpf.value);
			var valido = (!isNaN(cpf_Int) && (isValidoCNPJ(cpf) || isValidoCPF(cpf.value)));
			if (!valido) {
				bootbox.alert("CPF inválido!",function(){})
				return false;
			}
		}
		
		// VALIDANDO PASSWORD
		var pass1 = document.formCadUser.password1.value;
		var pass2 = document.formCadUser.password2.value;
		var cont1 = pass1.length;
		var cont2 = pass2.length;
		if (pass1 == "" || pass2 == "") {
			bootbox.alert("Para continuar, informe a senha!",function(){})
			return false;
		} else if((cont1 || cont2) < 8){
			bootbox.alert("A senha deve conter pelo menos 8 caracteres",function(){})
			return false;
		} else {
			if(pass1 != pass2){
				bootbox.alert("As senhas não conferem",function(){})
				return false;
			}
		}
		return true;
	}

	function cadastrarUsuario() {
		if (validarCampos()) {
			formCadUser.cadastrar.value = "true";
			formCadUser.action = "cadastroUser";
			formCadUser.submit();
		}
	}

	function limparCampos() {
		$('#cpf').val('');
		$('#password1').val('');
		$('#password2').val('');
	}

	function goBack() {
		formCadUser.action = "inicio";
		formCadUser.submit();
	}
	
	function mostraSenha() {
		var password = $('#password1');
	    var passwordType = password.attr('type');
	    
	    if(passwordType == 'password') {
	    	password.attr('type', 'text');
	        $('#oculta').text('Ocultar senha');
	    } else {
	    	password.attr('type', 'password');
	        $('#oculta').text('Mostrar senha');
	    }
	}
	
	function validarSenha() {
		var pass1 = $("#password1").val();
		var pass2 = $("#password2").val();
		
		if(pass1.length < 8 && pass1 != ""){
			$("#pass1").css("display", "block");
		} else {
			$("#pass1").css("display", "none");
		}
		
		if((pass2 != "") && (pass1 != pass2)) {
			$("#pass2").css("display", "block");
		} else {
			$("#pass2").css("display", "none");
		}
	}

	function sem_acento(e, args) {
		if (document.all) { var evt = event.keyCode;} // caso seja IE
		else { var evt = e.charCode; } // do contrário deve ser Mozilla
		var valid_chars = 'abcdefghijlmnopqrstuvxzwykABCDEFGHIJLMNOPQRSTUVXZWYK123456789 ' + args; // criando a lista de teclas permitidas
		var chr = String.fromCharCode(evt); // pegando a tecla digitada
		if (valid_chars.indexOf(chr) > -1) { return true; }
		// se a tecla estiver na lista de permissão permite-a
		// para permitir teclas como <BACKSPACE> adicionamos uma permissão para
		// códigos de tecla menores que 09 por exemplo (geralmente uso menores que 20)
		if (valid_chars.indexOf(chr) > -1 || evt < 9) { return true; } // se a tecla estiver na lista de permissão permite-a
		return false; // do contrário nega
	}
	
	function sem_espaco(e, args) {
		if (document.all) { var evt = event.keyCode;} // caso seja IE
		else { var evt = e.charCode; } // do contrário deve ser Mozilla
		var valid_chars = 'abcdefghijlmnopqrstuvxzwykABCDEFGHIJLMNOPQRSTUVXZWYK123456789' + args; // criando a lista de teclas permitidas
		var chr = String.fromCharCode(evt); // pegando a tecla digitada
		if (valid_chars.indexOf(chr) > -1) { return true; }
		// se a tecla estiver na lista de permissão permite-a
		// para permitir teclas como <BACKSPACE> adicionamos uma permissão para
		// códigos de tecla menores que 09 por exemplo (geralmente uso menores que 20)
		if (valid_chars.indexOf(chr) > -1 || evt < 9) { return true; } // se a tecla estiver na lista de permissão permite-a
		return false; // do contrário nega
	}

	function maiuscula(z) {
		v = z.value.toUpperCase();
		z.value = v;
	}

</script>
</head>
<body onload="verificar()">
	<form name=formCadUser method="POST" action="formCadUser" onSubmit="return false;">
      <input type="hidden" name="cadastrar">
      <input type="hidden" name="cadastrarUser">
      <input type="hidden" name="acompanhar">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Cadastrar Usuário</h4>
				</div>
				<div class="panel-body">
					<div class="row">
	          			<div class="col-md-12">
							<div class="form-group has-success">
								<label class="control-label" for="cpf">CPF</label>
								<input type="text" class="form-control" placeholder="Informe o CPF" aria-describedby="basic-addon1" id="cpf" name="cpf">
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group has-success">
								<label class="control-label" for="password">Senha</label>
								<input type="password" class="form-control" placeholder="Senha" aria-describedby="basic-addon1"
								id="password1" name="password1" maxlength="20" autocomplete="off" onkeypress="return sem_espaco(event)"
								onblur="javascript:validarSenha();">
								<p class="pass-erro" id="pass1"><span class="glyphicon glyphicon-remove"></span> Mínimo de 8 caracteres </p>
								<a id="oculta" onclick="javascript:mostraSenha();">Mostra Senha</a>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group has-success">
								<input type="password" class="form-control" placeholder="Confirmar Senha" aria-describedby="basic-addon1"
								id="password2" name="password2" maxlength="20" autocomplete="off" onkeypress="return sem_espaco(event)"
								onblur="javascript:validarSenha();">
								<p class="pass-erro" id="pass2"><span class="glyphicon glyphicon-remove"></span>As senhas não conferem</p>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-footer btAcoes">
					<input class="btn btn-success" type="submit" onclick="javascript:cadastrarUsuario();" readonly="readonly" id="cadUser" value='CADASTRAR' name="cmdCadastrar"/>
					<input class="btn btn-success" type="button" onclick="javascript:limparCampos();" readonly="readonly"  id="limpar" value="LIMPAR" name="cmdLimpar"/>
					<input class="btn btn-success" type="button" onclick="javascript:goBack();" readonly="readonly" id="voltar" value='VOLTAR' name="cmdVoltar"/>
				</div>
			</div>
		</div>
	</form>
<script src="js/mask.js"></script>
</body>
</html>
