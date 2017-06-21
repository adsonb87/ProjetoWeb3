<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formInicio.action = acao;
		formInicio.submit();
	}

	function verificar() {
		var msgComando = "<%=request.getAttribute("msgComando")%>";
		var msgAuxiliar = "<%=request.getAttribute("msgAuxiliar")%>";

		if(msgComando == 1) {
			bootbox.alert(msgAuxiliar,function(){
				formInicio.usuCpf.value = cpf
				formInicio.consCadastro.value= "true";
				formInicio.action = "cadastroUsuario";
				formInicio.submit();
			})
		}
		
		if(msgComando == 2){
			formInicio.usuCpf.value = cpf
			formInicio.consCadastro.value= "true";
			formInicio.action = "cadastroUsuario";
			formInicio.submit();
		}
	}

	function consultarCartao() {
		formInicio.consCartao.value = "true";
		formInicio.action = "consultaCartao";
		formInicio.submit();
	}
	
	function consultarCadastro() {
		formInicio.consultarCadastro.value = "true";
		formInicio.action = "consultaCPF";
		formInicio.submit();
	}

</script>
</head>
<body onload="verificar()">
	<form name=formInicio method="POST" action="formInicio" onSubmit="return false;">
    <input type="hidden" name="consultarCadastro" value="false">
    <input type="hidden" name="consCartao" value="false">
	<div class="container">
			<div class="jumbotron blocoInicial">
				<div class="row subBloco">
					<div class="col-xs-12 col-lg-6">
						<ul class="grid cs-style-4">
							<li>
								<figure>
									<img style="height: 249px;" src="imagens/cartoesComum.png" alt="img01">
									<figcaption>
										<h3>J� POSSUI CART�O COMUM?</h3>
										<a onclick="javascript:consultarCartao();" class="btn btn-success botaoMenu" href="#">Clique Aqui</a>
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
										<h3>AINDA N�O POSSUI CART�O COMUM?</h3>
										 <a onclick="javascript:consultarCadastro();" class="btn btn-success botaoMenu" href="#">Clique Aqui</a>
									</figcaption>
								</figure>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>