<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formCadUsuario.action = acao;
		formCadUsuario.submit();
	}

	function verificar() {
		var msgComando = "${msgComando}";
		var msgAuxiliar = "${msgAuxiliar}";
		
		if(msgComando == "1") {
			$('#cpf').attr("disabled", true);
			$('#nome').attr("disabled", true);
			$('#dataNascimento').attr("disabled", true);
			$('#nomeMae').attr("disabled", true);
			$('#realizarCadastro').val('ATUALIZAR');
		}
		
		if(msgComando == "2") {
			$("#realizarCadastro").prop("disabled",true);
			formCadUsuario.confirmar.value = "true";
			formCadUsuario.action = "solicitarCartao";
			formCadUsuario.submit();
		}	
		
		if(msgComando == "3") {
			bootbox.alert(msgAuxiliar,function(){
				formCadUsuario.action = "inicio";
				formCadUsuario.submit();
			})
		}
	}

	function validarCampos() {

		//VALIDANDO CPF
		if (document.formCadUsuario.cpf.value == "") {
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
		
		//VALIDANDO NOME
		var nome = document.formCadUsuario.nome.value.trim();
		var cont = nome.length;
		if (nome == "") {
			bootbox.alert("Para continuar, informe o seu Nome!",function(){})
			return false;
		} else if (cont < 3) {
			bootbox.alert("Para continuar, informe um nome válido!",function(){})
			return false;
		}
		
		//VALIDANDO DATA NASCIMENTO
		var data = document.formCadUsuario.dataNascimento.value;
		if(data == "") {
			bootbox.alert("Para continuar, informe sua Data de Nascimento!",function(){})
			return false;
		} else if (!validarData(data)) {
			bootbox.alert("Data de Nascimento inválida!",function(){})
			return false;
		}
		
		//VALIDANDO NOME MÃE
		var nome = document.formCadUsuario.nomeMae.value.trim();
		var cont = nome.length;
		if (nome == "") {
			bootbox.alert("Para continuar, informe o Nome da sua Mãe!",function(){})
			return false;
		} else if (cont < 3) {
			bootbox.alert("Para continuar, informe um nome da mãe válido!",function(){})
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
			bootbox.alert("Informe um número de telefone válido (DDD + NÚMERO).",function(){	})
			return false;
		}

		//VALIDANDO EMAIL
		var email = document.formCadUsuario.email.value;
		if (email != "") {
			if(!validarEmail(email)) {
				bootbox.alert("Para continuar, informe um Email válido",function(){})
				return false;
			}
		}
		
		if (document.formCadUsuario.cep.value == "") {
			bootbox.alert("Para continuar, informe o CEP!",function(){})
			return false;
		}
		if (document.formCadUsuario.logradouro.value == "") {
			bootbox.alert("Para continuar, informe o Logradouro!",function(){})
			return false;
		}
		if (document.formCadUsuario.bairro.value == "") {
			bootbox.alert("Para continuar, informe o Bairro!",function(){})
			return false;
		}
		if (document.formCadUsuario.uf.value == "") {
			bootbox.alert("Para continuar, informe a UF!",function(){})
			return false;
		}
		if (document.formCadUsuario.cidade.value == "") {
			bootbox.alert("Para continuar, informe a Cidade!",function(){})
			return false;
		}
		if (document.formCadUsuario.numero.value == "") {
			bootbox.alert("Para continuar, informe o Número da sua Residência!",function(){})
			return false;
		}

		return true;
	}

	function cadastrarUsuario() {
		if (validarCampos()) {
			$('#cpf').attr("disabled", false);
			$('#nome').attr("disabled", false);
			$('#dataNascimento').attr("disabled", false);
			$('#nomeMae').attr("disabled", false);
			$("#realizarCadastro").prop("disabled",true);
			formCadUsuario.usrIdOrigem.value = "${ usuario.usrIdOrigem }";
			formCadUsuario.cadastrar.value = "true";
			formCadUsuario.action = "cadastroUsuarioNovo";
			formCadUsuario.submit();
		}
	}

	function limparCampos() {
		$('#telefone').val('');
		$('#email').val('');
		$('#cep').val('');
		$('#logradouro').val('');
		$('#bairro').val('');
		$('select').val('');
		$('#uf').val('');
		$('#complemento').val('');
		$('#numero').val('');
	}
	
    function limparCampoEnd() {
    	$('#logradouro').val('');
		$('#bairro').val('');
		$('select').val('');
		$('#uf').val('');
    }

    function atualCampoEnd(conteudo) {
		if (!("erro" in conteudo)) {
			document.formCadUsuario.logradouro.value = (conteudo.logradouro).toUpperCase();
            document.formCadUsuario.bairro.value = (conteudo.bairro).toUpperCase();
            document.formCadUsuario.cidade.value = (conteudo.localidade).toUpperCase();
            document.formCadUsuario.uf.value = (conteudo.uf).toUpperCase();
		} else {
            bootbox.alert("CEP não encontrado.",function(){
            	limparCampoEnd();
            	document.formCadUsuario.cep.value = "";
            })
        }
    }
       
	//CONSULTA CEP
    function pesquisaCep(valor) {
		var cep = valor.replace(/\D/g, '');
        if (cep != "") {
            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;
            if(validacep.test(cep)) {
                //Preenche os campos com "..." enquanto consulta webservice.
                document.formCadUsuario.logradouro.value = "...";
                document.formCadUsuario.bairro.value = "...";
                document.formCadUsuario.cidade.value = "...";
                document.formCadUsuario.uf.value = "...";
                //Cria um elemento javascript.
                var script = document.createElement('script');
                //Sincroniza com o atualCampoEnd.
                script.src = '//viacep.com.br/ws/'+ cep + '/json/?callback=atualCampoEnd';
                //Insere script no documento e carrega o conteúdo.
                document.body.appendChild(script);
            } else {
				bootbox.alert("Formato de CEP inválido.",function(){
                	limparCampoEnd();
                })
            }
        } else {
            limparCampoEnd();
        }
    }

	function goBack() {
		formCadUsuario.consCadastro.value = "true";
		formCadUsuario.action = "consultaCPF";
		formCadUsuario.submit();
	}

	function sem_acento(e, args) {
		if (document.all) { var evt = event.keyCode;} // caso seja IE
		else { var evt = e.charCode; } // do contrário deve ser Mozilla
		var valid_chars = 'abcdefghijlmnopqrstuvxzwykABCDEFGHIJLMNOPQRSTUVXZWYK ' + args; // criando a lista de teclas permitidas
		var chr = String.fromCharCode(evt); // pegando a tecla digitada
		if (valid_chars.indexOf(chr) > -1) { return true; }
		// se a tecla estiver na lista de permissão permite-a
		// para permitir teclas como <BACKSPACE> adicionamos uma permissão para
		// códigos de tecla menores que 09 por exemplo (geralmente uso menores que 20)
		if (valid_chars.indexOf(chr) > -1 || evt < 9) { return true; } // se a tecla estiver na lista de permissão permite-a
		return false; // do contrário nega
	}

	function sem_acentoEmail(e, args) {
		if (document.all) { var evt = event.keyCode;} // caso seja IE
		else { var evt = e.charCode; } // do contrário deve ser Mozilla
		var valid_chars = 'abcdefghijlmnopqrstuvxzwykABCDEFGHIJLMNOPQRSTUVXZWYK.@0123456789_-' + args; // criando a lista de teclas permitidas
		var chr = String.fromCharCode(evt); // pegando a tecla digitada
		if (valid_chars.indexOf(chr) > -1) { return true; }
		// se a tecla estiver na lista de permissão permite-a
		// para permitir teclas como <BACKSPACE> adicionamos uma permissão para
		// códigos de tecla menores que 09 por exemplo (geralmente uso menores que 20)
		if (valid_chars.indexOf(chr) > -1 || evt < 9) { return true; } // se a tecla estiver na lista de permissão permite-a
		return false; // do contrário nega
	}

	function sem_acentoEndereco(e, args) {
		if (document.all) { var evt = event.keyCode;} // caso seja IE
		else { var evt = e.charCode; } // do contrário deve ser Mozilla
		var valid_chars = 'abcdefghijlmnopqrstuvxzwykABCDEFGHIJLMNOPQRSTUVXZWYK0123456789 ' + args; // criando a lista de teclas permitidas
		var chr = String.fromCharCode(evt); // pegando a tecla digitada
		if (valid_chars.indexOf(chr) > -1) { return true; } 
		// se a tecla estiver na lista de permissão permite-a
		// para permitir teclas como <BACKSPACE> adicionamos uma permissão para
		// códigos de tecla menores que 09 por exemplo (geralmente uso menores que 20)
		if (valid_chars.indexOf(chr) > -1 || evt < 9) { return true; } // se a tecla estiver na lista de permissão permite-a
		return false; // do contrário nega
	}
	
	function sem_letra(e, args) {
		if (document.all) { var evt = event.keyCode;} // caso seja IE
		else { var evt = e.charCode; } // do contrário deve ser Mozilla
		var valid_chars = '0123456789() ' + args; // criando a lista de teclas permitidas
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
	<form name=formCadUsuario method="POST" action="formCadUsuario" onSubmit="return false;">
      <input type="hidden" name="cadastrar">
      <input type="hidden" name="confirmar">
      <input type="hidden" name="consCadastro">
      <input type="hidden" name="usrIdOrigem">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Cadastrar Usuário</h4>
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
								<label class="control-label" for="nomeMae">Nome da Mãe</label>
								<input type="text" class="form-control" placeholder="Nome da Mãe" value="${ usuario.nomeMae }" aria-describedby="basic-addon1"
								id="nomeMae" name="nomeMae" onkeyup="maiuscula(this)" onkeypress="return sem_acento(event)" maxlength="100" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="telefone">Telefone</label>
								<input type="text" class="form-control" placeholder="DDD + Número" value="${ usuario.telefone }" aria-describedby="basic-addon1"
								id="telefone" name="telefone" onkeypress="maskTelefone(this, maskTel);" autocomplete="off" maxlength="13">
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="email">Email</label>
								<input type="text" class="form-control" placeholder="Informe seu E-mail" value="${ usuario.email }" aria-describedby="basic-addon1"
								id="email" name="email" onkeyup="maiuscula(this)" onkeypress="return sem_acentoEmail(event)" maxlength="50" autocomplete="off">
							</div>
						</div>
					</div>
										<div class="row">
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="cep">Cep</label>
								<input type="text" class="form-control" placeholder="Informe o CEP" value="${ usuario.endereco.cep }" aria-describedby="basic-addon1"
								id="cep" name="cep" onblur="pesquisaCep(this.value);" autocomplete="off">
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="logradouro">Logradouro</label>
								<input type="text" class="form-control" placeholder="Informe o seu Logradouro" value="${ usuario.endereco.logradouro }" aria-describedby="basic-addon1"
								id="logradouro" name="logradouro" onkeyup="maiuscula(this)" onkeypress="return sem_acentoEndereco(event)" maxlength="100" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="bairro">Bairro</label>
								<input type="text" class="form-control" placeholder="Informe o Bairro" value="${ usuario.endereco.bairro }" aria-describedby="basic-addon1"
								id="bairro" name="bairro" onkeyup="maiuscula(this)" onkeypress="return sem_acentoEndereco(event)" maxlength="50" autocomplete="off">
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="cidade">Cidade</label>
								<select class="form-control" data-style="btn-success" id="cidade" name="cidade">
								<option value="">.:SELECIONAR:.</option>
									<c:forEach var="cidades" items="${lcidade}">
										<option value="${ cidades }"
											<c:if test="${cidades == usuario.endereco.cidade.toUpperCase()}">selected</c:if>>${ cidades }</option>
									</c:forEach>
								</select>
							</div>
						</div>					
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="uf">UF</label>
								<input type="text" class="form-control" placeholder="Informe a UF" value="${ usuario.endereco.uf }" aria-describedby="basic-addon1"
								id="uf" name="uf" onkeyup="maiuscula(this)" onkeypress="return sem_acento(event)" maxlength="2" autocomplete="off">
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="complemento">Complemento</label>
								<input type="text" class="form-control" placeholder="Informe o Complemento" value="${ usuario.endereco.complemento }" aria-describedby="basic-addon1"
								id="complemento" name="complemento" onkeyup="maiuscula(this)" onkeyup="maiuscula(this)" onkeypress="return sem_acentoEndereco(event)" maxlength="100" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group has-success">
								<label class="control-label" for="numero">Número</label>
								<input type="text" class="form-control" placeholder="Número da residência" value="${ usuario.endereco.numero }" aria-describedby="basic-addon1"
								id="numero" name="numero" maxlength="8" onkeypress="return sem_letra(event)" autocomplete="off">
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
