<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formCadUsuario.action = acao;
		formCadUsuario.submit();
	}

	function verificar() {
		var msgComando = "${msgComando}";
		var msgAuxiliar = "${msgAuxiliar}";
		var cpf = "${usuCpf}";
		var crdSnr = "${crdSnr}";
		var numCartao = "${numCartao}";

		switch(msgComando) {
			case "1":
				bootbox.alert(msgAuxiliar,function(){
					formCadUsuario.usuCpf.value = cpf;
					formCadUsuario.crdSnr.value = crdSnr;
					formCadUsuario.numCartao.value = numCartao;
					formCadUsuario.cadVincular.value = "true";
					formCadUsuario.action = "vinculacao";
					formCadUsuario.submit();
				})
				break;
	
			case "2":
				$('#realizarCadastro').val('ATUALIZAR');
				$('#cpf').attr("disabled", true);
				$('#nome').attr("disabled", true);
				$('#dataNascimento').attr("disabled", true);
				$('#nomeMae').attr("disabled", true);
				break;
	
			case "3":
				bootbox.alert(msgAuxiliar,function(){
					formCadUsuario.action = "consultaCadastro";
					formCadUsuario.submit();
				})
		}
	
	}
	
	function validarCampos() {

		//VALIDANDO NOME
		var nome = document.formCadUsuario.nome.value.trim();
		var cont = nome.length;
		if (nome == "") {
			bootbox.alert("Para continuar, informe o seu Nome!",function(){})
			return false;
		} else if (cont < 3) {
			bootbox.alert("Para continuar, informe um nome v�lido!",function(){})
			return false;
		}

		//VALIDANDO CPF
		if (document.formCadUsuario.cpf.value == "") {
			bootbox.alert("Para continuar, informe o seu CPF!",function(){})
			return false;
		} else {
			var cpf_Int = parseInt(cpf.value);
			var valido = (!isNaN(cpf_Int) && (isValidoCNPJ(cpf) || isValidoCPF(cpf.value)));
			if (!valido) {
				bootbox.alert("CPF inv�lido!",function(){})
				return false;
			}
		}
		
		//VALIDANDO DATA NASCIMENTO
		var data = document.formCadUsuario.dataNascimento.value;
		if(data == "") {
			bootbox.alert("Para continuar, informe sua Data de Nascimento!",function(){})
			return false;
		} else if (!validarData(data)) {
			bootbox.alert("Data de Nascimento inv�lida!",function(){})
			return false;
		}
		
		//VALIDANDO NOME M�E
		var nomeMae = document.formCadUsuario.nomeMae.value.trim();
		var contMae = nomeMae.length;
		if (nomeMae == "") {
			bootbox.alert("Para continuar, informe o Nome da sua M�e!",function(){})
			return false;
		} else if (contMae < 3) {
			bootbox.alert("Para continuar, informe um nome da m�e v�lido!",function(){})
			return false;
		}

		//VALIDANDO TELEFONE
		var telefone = document.formCadUsuario.telefone.value;
		var txt = telefone.replace(/[^\d]+/g,'');
		var cont = txt.length;
		if (telefone == "") {
			bootbox.alert("Para continuar, informe o seu Telefone!",function(){})
			return false;	
		} else if (cont < 10) {
			bootbox.alert("Informe um n�mero de telefone v�lido (DDD + N�MERO).",function(){	})
			return false;
		}

		//VALIDANDO EMAIL
		var email = document.formCadUsuario.email.value;
		if (email != "") {
			if(!validarEmail(email)) {
				bootbox.alert("Para continuar, informe um Email v�lido",function(){})
				return false;
			}
		}

		return true;
	}

	function cadastrarUsuario() {
		if (validarCampos()) {
			$("#realizarCadastro").prop("disabled",true);
			$('#cpf').attr("disabled", false);
			$('#nome').attr("disabled", false);
			$('#dataNascimento').attr("disabled", false);
			$('#nomeMae').attr("disabled", false);
			formCadUsuario.usrIdOrigem.value = "${ usuario.usrIdOrigem }";
			formCadUsuario.crdSnr.value = "${ usuario.cartao.crdSnr }";
			formCadUsuario.numCartao.value = "${ usuario.cartao.numCartao }";
			formCadUsuario.cadastrar.value = "true";
			formCadUsuario.action = "cadastroUsuario";
			formCadUsuario.submit();
		}
	}

	function limparCampos() {
		$('#telefone').val('');
		$('#email').val('');
	}

	function goBack() {
		formCadUsuario.action = "consultaCPF";
		formCadUsuario.submit();
	}

	function sem_acento(e, args) {
		if (document.all) { var evt = event.keyCode;} // caso seja IE
		else { var evt = e.charCode; } // do contr�rio deve ser Mozilla
		var valid_chars = 'abcdefghijlmnopqrstuvxzwykABCDEFGHIJLMNOPQRSTUVXZWYK ' + args; // criando a lista de teclas permitidas
		var chr = String.fromCharCode(evt); // pegando a tecla digitada
		if (valid_chars.indexOf(chr) > -1) { return true; }
		// se a tecla estiver na lista de permiss�o permite-a
		// para permitir teclas como <BACKSPACE> adicionamos uma permiss�o para
		// c�digos de tecla menores que 09 por exemplo (geralmente uso menores que 20)
		if (valid_chars.indexOf(chr) > -1 || evt < 9) { return true; } // se a tecla estiver na lista de permiss�o permite-a
		return false; // do contr�rio nega
	}

	function sem_acentoEmail(e, args) {
		if (document.all) { var evt = event.keyCode;} // caso seja IE
		else { var evt = e.charCode; } // do contr�rio deve ser Mozilla
		var valid_chars = 'abcdefghijlmnopqrstuvxzwykABCDEFGHIJLMNOPQRSTUVXZWYK.@0123456789_-' + args; // criando a lista de teclas permitidas
		var chr = String.fromCharCode(evt); // pegando a tecla digitada
		if (valid_chars.indexOf(chr) > -1) { return true; }
		// se a tecla estiver na lista de permiss�o permite-a
		// para permitir teclas como <BACKSPACE> adicionamos uma permiss�o para
		// c�digos de tecla menores que 09 por exemplo (geralmente uso menores que 20)
		if (valid_chars.indexOf(chr) > -1 || evt < 9) { return true; } // se a tecla estiver na lista de permiss�o permite-a
		return false; // do contr�rio nega
	}

	function sem_letra(e, args) {
		if (document.all) { var evt = event.keyCode;} // caso seja IE
		else { var evt = e.charCode; } // do contr�rio deve ser Mozilla
		var valid_chars = '0123456789() ' + args; // criando a lista de teclas permitidas
		var chr = String.fromCharCode(evt); // pegando a tecla digitada
		if (valid_chars.indexOf(chr) > -1) { return true; }
		// se a tecla estiver na lista de permiss�o permite-a
		// para permitir teclas como <BACKSPACE> adicionamos uma permiss�o para
		// c�digos de tecla menores que 09 por exemplo (geralmente uso menores que 20)
		if (valid_chars.indexOf(chr) > -1 || evt < 9) { return true; } // se a tecla estiver na lista de permiss�o permite-a
		return false; // do contr�rio nega
	}

	function maiuscula(z) {
		v = z.value.toUpperCase();
		z.value = v;
	}

</script>
</head>
<body onload="verificar()">
	<form name=formCadUsuario method="POST" action="formCadUsuario" onSubmit="return false;">
      <input type="hidden" name="cadastrar" value="fasle">
      <input type="hidden" name="consCadastro" value="false">
      <input type="hidden" name="cadVincular" value="false">
      <input type="hidden" name="usrIdOrigem">
      <input type="hidden" name="usuCpf">
      <input type="hidden" name="crdSnr">
      <input type="hidden" name="numCartao">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Cadastrar Usu�rio</h4>
				</div>
				<div class="panel-body">
					<div class="row">
           				<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="cpf">CPF</label>
								<input type="text" class="form-control" placeholder="Informe o CPF" value="${ usuario.cpf }" aria-describedby="basic-addon1"
								id="cpf" name="cpf">
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="nome">Nome</label>
								<input type="text" class="form-control" placeholder="Informe o Nome" value="${ usuario.nome }" aria-describedby="basic-addon1"
								id="nome" name="nome" onkeyup="maiuscula(this)" onkeypress="return sem_acento(event)" maxlength="100" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="dataNascimento">Data de Nascimento</label>
								<input type="text" class="form-control" placeholder="Data Nascimento" value="${ usuario.dataNascimento }" aria-describedby="basic-addon1"
								id="dataNascimento" name="dataNascimento" onkeypress="return sem_letra(event)" autocomplete="off">
							</div>
						</div>
           				<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="nomeMae">Nome da M�e</label>
								<input type="text" class="form-control" placeholder="Nome da M�e" value="${ usuario.nomeMae }" aria-describedby="basic-addon1"
								id="nomeMae" name="nomeMae" onkeyup="maiuscula(this)" onkeypress="return sem_acento(event)" maxlength="100" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="telefone">Telefone</label>
								<input type="text" class="form-control" placeholder="DDD + N�mero" value="${ usuario.telefone }" aria-describedby="basic-addon1"
								id="telefone" name="telefone" onkeypress="maskTelefone(this, maskTel);" autocomplete="off" maxlength="13">
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="email">Email</label>
								<input type="text" class="form-control" placeholder="Digite seu E-mail" value="${ usuario.email }" aria-describedby="basic-addon1"
								id="email" name="email" onkeyup="maiuscula(this)" onkeypress="return sem_acentoEmail(event)" maxlength="50" autocomplete="off">
							</div>
						</div>
					</div>
				</div>
				<div class="panel-footer btAcoes">
					<input class="btn btn-success" type="submit" onclick="javascript:cadastrarUsuario();" readonly="readonly" id="realizarCadastro" value='CADASTRAR' name="cmdCadastrar"/>
					<input class="btn btn-success" type="button" onclick="javascript:limparCampos();" id="limpar" value="LIMPAR" readonly="readonly" name="cmdLimpar"/>
					<input class="btn btn-success" type="button" onclick="javascript:goBack();" readonly="readonly" id="voltar" value='VOLTAR' name="cmdVoltar"/>
				</div>
			</div>
		</div>
	</form>
<script src="js/mask.js"></script>
</body>
</html>
