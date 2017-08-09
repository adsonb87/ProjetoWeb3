<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formInicio.action = acao;
		formInicio.submit();
	}

	function verificar() {
		var msgComando = "${ msgComando }";
		var msgAuxiliar = "${ msgAuxiliar }";
		
		if(msgComando == "1") {
			$('#modalLoader').modal('hide');
			$('#modalLogin').modal('show');
			$('#msgLogin').css('display', 'block');
			$('#msgLogin').text(msgAuxiliar);
		}
		
		if(msgComando == "2") {
			formInicio.acompanhar.value = "true";
			formInicio.action = "acompanhamento";
			formInicio.submit();
		}
		
		if(msgComando == "3") {
			$('#modalLoader').modal('hide');
			$('#modalAtencao').modal('show');
		}
		
	}
	
	function modalLogin() {
		$('#modalLogin').modal('show');
	}

	function consultaCadastro() {
		formInicio.consCadastro.value = "true";
		formInicio.action = "consultaCadastro";
		formInicio.submit();
	}
	
	function consultaCPF() {
		formInicio.consCadastro.value = "true";
		formInicio.action = "consultaCPF";
		formInicio.submit();
	}
	
	function acessar() {
		$('#modalLoader').modal('show');
		$('#modalLogin').modal('hide');
		formInicio.login.value = $("#login").val();
		formInicio.password.value = $("#password").val();
		formInicio.consLogin.value = "true";
		formInicio.action = "login";
		formInicio.submit();
	}
	
	function cadastrarUser() {
		formInicio.cadastrarUser.value = "true";
		formInicio.action = "cadastroUser";
		formInicio.submit();
	}
	
	function esqueceuSenha() {
		
	}
	
	function cancelarAcesso() {
		formInicio.action = "inicio";
		formInicio.submit();	
	}
	
	function goBack() {
		formInicio.action = "inicio";
		formInicio.submit();
	}
	
	function mostraSenha() {
		var password = $('#password');
	    var passwordType = password.attr('type');
	    
	    if(passwordType == 'password') {
	    	password.attr('type', 'text');
	    } else {
	    	password.attr('type', 'password');
	    }
	}
	
	$(document).keypress(function(e) {
		if(e.which == 13) {
			acessar();
		}
	});

</script>
</head>
<body onload="verificar()">
	<form name=formInicio method="POST" action="formInicio" onSubmit="return false;">
    <input type="hidden" name="consCadastro">
    <input type="hidden" name="consLogin">
    <input type="hidden" name="cadastrarUser">
    <input type="hidden" name="acompanhar">
    <input type="hidden" name="login">
    <input type="hidden" name="password">
		<div class="container">
			<div class="jumbotron blocoInicial">
				<div class="row subBloco">
					<div class="col-xs-12 col-lg-6">
						<ul class="grid cs-style-4">
							<li>
								<figure>
									<img style="height: 249px;" src="imagens/cartoesComum.png" alt="img01">
									<figcaption>
										<h3>VOCÊ JÁ TEM NOSSO CARTÃO COMUM?</h3>
										<a onclick="javascript:consultaCadastro();" class="btn btn-success botaoMenu" href="#">Clique Aqui</a>
									</figcaption>
								</figure>
							</li>
						</ul>
					</div>
					<div class="col-xs-12 col-lg-6">
						<ul class="grid cs-style-4" style="float: right;">
							<li>
								<figure>
									<img style="height: 249px;" src="imagens/cartoesComum.png" alt="img01">
									<figcaption style="width: 50%;">
										<h3>VOCÊ AINDA NÃO TEM NOSSO CARTÃO COMUM?</h3>
										 <a onclick="javascript:consultaCPF();" class="btn btn-success botaoMenu" href="#">Clique Aqui</a> 
									</figcaption>
								</figure>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</form>
	<!-- Modal Login -->
	<div class="modal fade modalLogin" id="modalLogin" tabindex="-1" role="dialog" aria-labelledby="modalLogin" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-login" role="document">
	    <div class="modal-content">
	      <div class="modal-header headerLogin">
	      	<b class="modal-title" id="modalLogin">Login</b>
	      </div>
	      <div class="modal-body">
    		<div class="form-group has-success form-modal-login">
				<div class="input-group form-modal-login">
					<div class="input-group-addon">
						<span class="glyphicon glyphicon-user"></span>
					</div>
			    	<input type="text" class="form-control" id="login" name="login" placeholder="LOGIN">
		      	</div>
			</div>
      		<div class="form-group has-success form-modal-login">
				<div class="input-group input-login form-modal-login">
					<div class="input-group-addon">
						<span class="glyphicon glyphicon-eye-open" onclick="javascript:mostraSenha()"></span>
					</div>
					<input type="password" class="form-control" id="password" name="password" placeholder="SENHA">
				</div>
			</div>
			<div class="form-group form-modal-login">
				<p class="msg-login" id="msgLogin"></p>
			</div>
			<div class="form-group form-modal-login">
<!-- 				<div> -->
<!-- 					<a onclick="javascript:esqueceuSenha();" href="#" data-toggle="modal">ESQUECEU SUA SENHA?</a> -->
<!-- 				</div> -->
				<div>
					<a onclick="javascript:cadastrarUser();" href="#" data-toggle="modal">INSCREVA-SE</a>
				</div>
			</div>
		  </div>
	      <div class="modal-footer">
	      	<input class="btn btn-success" type="button" onclick="javascript:acessar();" readonly="readonly" value="ENTRAR" name="cmdEntrar" title="Entrar"/>
			<input class="btn btn-success" type="button" onclick="javascript:cancelarAcesso();" data-dismiss="modal" readonly="readonly" value="CANCELAR" name="cmdCancelar" title="Cancelar"/>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- Modal Atenção -->
	<div class="modal fade" id="modalAtencao" tabindex="-1" role="dialog" aria-labelledby="modalAtencao" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <b class="modal-title" id="modalAtencao">Atenção!</b>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <p>${ msgAuxiliar }</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-success" data-dismiss="modal" onclick="javascript:goBack();">OK</button>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- Modal Loader -->
	<div class="modal fade modalLogin" id="modalLoader" tabindex="-1" role="dialog" aria-labelledby="modalLoader" aria-hidden="true">
		<div class="modal-dialog modal-loading" role="document">
	        <div class="modal-content">
	            <div class="modal-body">
	                <div class="text-center">
	                    <img src="imagens/loader.svg" class="icon"/>
	                    <h5>CARREGANDO...</h5>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>	
<script src="js/mask.js"></script>
</body>
</html>
