<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formInicio.action = acao;
		formInicio.submit();
	}

	function verificar() {
		
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

</script>
</head>
<body onload="verificar()">
	<form name=formInicio method="POST" action="formInicio" onSubmit="return false;">
    <input type="hidden" name="consCadastro" value="false">
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
<!-- 									 <a onclick="" class="btn btn-success botaoMenu" href="#">Clique Aqui</a> -->
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
