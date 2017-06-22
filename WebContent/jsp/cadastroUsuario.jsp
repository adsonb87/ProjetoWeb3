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
		var numCartao = "${numCartao}";
	
		if(msgComando == 1){
			bootbox.alert(msgAuxiliar,function(){
				formCadUsuario.usuCpf.value = cpf;
				formCadUsuario.numCartao.value = numCartao;
				formCadUsuario.cadVincular.value= "true";
				formCadUsuario.action = "vinculacao";
				formCadUsuario.submit();
			})
		}
		
		if(msgComando == 2){
			bootbox.alert(msgAuxiliar,function(){
				formCadUsuario.numCartao.value = numCartao;
				formCadUsuario.action = "inicio";
				formCadUsuario.submit();
			})
		}
		
	}
	
	function validarEmail(email) {
		usuario = email.value.substring(0, email.value.indexOf("@"));
		dominio = email.value.substring(email.value.indexOf("@")+ 1, email.value.length);
		if (!(usuario.length >=1) &&
		    (dominio.length >=3) && 
		    (usuario.search("@")==-1) && 
		    (dominio.search("@")==-1) &&
		    (usuario.search(" ")==-1) && 
		    (dominio.search(" ")==-1) &&
		    (dominio.search(".")!=-1) &&      
		    (dominio.indexOf(".") >=1)&& 
		    (dominio.lastIndexOf(".") < dominio.length - 1)) {
			bootbox.alert("Para continuar, informe um Email válido",function(){})
		}
	}

	function validarCampos() {
		
		//Validando NOME
		var nome = document.formCadUsuario.nome.value.trim();
		if (nome == "") {
			bootbox.alert("Para continuar, informe o seu Nome!",function(){})
			return false;
		} else {
			var cont = nome.length;
			if(cont < 3){
				bootbox.alert("Para continuar, informe um nome válido!",function(){})
				return false;
			}
		}
		
		//Validando CPF
		if (document.formCadUsuario.cpf.value != "") {
			var cpf_Int = parseInt(cpf.value);
			var valido = (!isNaN(cpf_Int) && (isValidoCNPJ(cpf) || isValidoCPF(cpf.value)));
			if (!valido) {
				bootbox.alert("CPF inválido!",function(){})
				return false;
			}
		} else {
			bootbox.alert("Para continuar, informe o seu CPF!",function(){})
			return false;
		}
		
		//Validando TELEFONE
		if (document.formCadUsuario.telefone.value == "")  {
			bootbox.alert("Para continuar, informe o seu Telefone!",function(){})
			return false;
		} else {
			var txt = $('#telefone').val().replace(/[^\d]+/g,'');
			var cont = txt.length;
			if(cont < 10){
				bootbox.alert("Informe um número de telefone válido (DDD + NÚMERO).",function(){	})
				return false;
			}
		}
		
		//Validando EMAIL
		var email = document.formCadUsuario.email.value;
		if (email == "") {
			bootbox.alert("Para continuar, informe o seu E-mail!",function(){})
			return false;
		} else {
			var usuario = email.substring(0, email.indexOf("@"));
			var dominio = email.substring(email.indexOf("@")+ 1, email.length);
			if (!((usuario.length >=1) &&
			    (dominio.length >= 3) && 
			    (usuario.search("@") == -1) && 
			    (dominio.search("@") == -1) &&
			    (usuario.search(" ") == -1) && 
			    (dominio.search(" ") == -1) &&
			    (dominio.search(".") != -1) &&      
			    (dominio.indexOf(".") >= 1)&& 
			    (dominio.lastIndexOf(".") < dominio.length - 1))) {
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
			formCadUsuario.usrId.value = "${ usuario.usrId }";
			formCadUsuario.cadastrar.value = "true";
			formCadUsuario.action = "cadastroUsuario";
			formCadUsuario.submit();
		}
	}
	
	function limparCampos() {
		$('#nome').val('');
		$('#telefone').val('');
		$('#cep').val('');
		$('#logradouro').val('');
		$('#bairro').val('');
		$('#uf').val('');
		$('select').val('');
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
	
	function sem_acentoEnderecoo(e, args) {
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
      <input type="hidden" name="cadastrar" value="fasle">
      <input type="hidden" name="atualCadastro" value="false">
      <input type="hidden" name="cadVincular" value="false">
      <input type="hidden" name="usrId">
      <input type="hidden" name="usuCpf">
      <input type="hidden" name="numCartao" value="${ numCartao }">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Cadastrar Usuário</h4>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1">Nome:</span>
								<input type="text" class="form-control" placeholder="Informe o Nome" value="${ usuario.nome }" aria-describedby="basic-addon1"
								id="nome" name="nome" onkeyup="maiuscula(this)" onkeypress="return sem_acento(event)" maxlength="100" autocomplete="off">
							</div>
						</div>
           				<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1">CPF:</span>
								<input type="text" class="form-control" placeholder="Informe o CPF" value="${ usuario.cpf }" aria-describedby="basic-addon1"
								id="cpf" name="cpf">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1">Telefone:</span>
								<input type="text" class="form-control" placeholder="DDD + Número" value="${ usuario.telefone }" aria-describedby="basic-addon1"
								id="telefone" name="telefone" onkeypress="maskTelefone(this, maskTel);"  autocomplete="off" maxlength="15">
							</div>
						</div>
						<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1">Email:</span>
								<input type="text" class="form-control" placeholder="Digite seu E-mail" value="${ usuario.email }" aria-describedby="basic-addon1"
								id="email" name="email" onkeyup="maiuscula(this)" onkeypress="return sem_acentoEmail(event)" maxlength="50" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1">CEP:</span>
								<input type="text" class="form-control" placeholder="Informe o CEP" value="${ usuario.endereco.cep }" aria-describedby="basic-addon1"
								id="cep" name="cep" onblur="pesquisaCep(this.value);" autocomplete="off">
							</div>
						</div>
						<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1">Logradouro:</span>
								<input type="text" class="form-control" placeholder="Informe o seu Logradouro" value="${ usuario.endereco.logradouro }" aria-describedby="basic-addon1"
								id="logradouro" name="logradouro" onkeyup="maiuscula(this)" onkeypress="return sem_acentoEnderecoo(event)" maxlength="100" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1">Bairro:</span>
								<input type="text" class="form-control" placeholder="Informe o Bairro" value="${ usuario.endereco.bairro }" aria-describedby="basic-addon1"
								id="bairro" name="bairro" onkeyup="maiuscula(this)" onkeypress="return sem_acentoEnderecoo(event)" maxlength="50" autocomplete="off">
							</div>
						</div>
						<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1" >Cidade:</span>
								<select class="form-control" data-style="btn-success" id="cidade" name="cidade">
								<option value="">.:SELECIONAR:.</option>
									<c:forEach var="cidades" items="${lcidade}">
										<option value="${ cidades }"
											<c:if test="${cidades == usuario.endereco.cidade}">selected</c:if>>${ cidades }</option>
									</c:forEach>
								</select>
							</div>
						</div>					
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1">UF:</span>
								<input type="text" class="form-control" placeholder="Informe a UF" value="${ usuario.endereco.uf }" aria-describedby="basic-addon1"
								id="uf" name="uf" onkeyup="maiuscula(this)" onkeypress="return sem_acento(event)" maxlength="2" autocomplete="off">
							</div>
						</div>	
						<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1">Complemento:</span>
								<input type="text" class="form-control" placeholder="Informe o Bairro" value="${ usuario.endereco.complemento }" aria-describedby="basic-addon1"
								id="complemento" name="complemento" onkeyup="maiuscula(this)" onkeyup="maiuscula(this)" onkeypress="return sem_acentoEnderecoo(event)" maxlength="100" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="input-group has-success">
								<span class="input-group-addon" id="basic-addon1">Número:</span>
								<input type="text" class="form-control" placeholder="Informe o Bairro" value="${ usuario.endereco.numero }" aria-describedby="basic-addon1"
								id="numero" name="numero" onkeypress="return sem_letra(event)" maxlength="10" autocomplete="off">
							</div>
						</div>
					</div>
				</div>
				<div class="panel-footer" style="text-align: center;">
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
