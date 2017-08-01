<%@ include file="home.jsp"%>

<script language="JavaScript" type="text/JavaScript" charset="utf-8">

	function navegar(acao) {
		formImprimir.action = acao;
		formImprimir.submit();
	}

	function verificar() {
		var msgComando = "${msgComando}";
		var msgAuxiliar = "${msgAuxiliar}";

		if(msgComando == 1) {
			formImprimir.action = "imprimirBoleto";
			formImprimir.imprimirBoleto.value = "true";
			formImprimir.submit();
		}
	}

	function imprimir() {
		formImprimir.imprimirBoleto.value = "true";
		formImprimir.action = "imprimirBoleto";
		formImprimir.submit();
	}
	
	function teste1() {
		window.open('http://www.google.com.br', '_blank');
    }

</script>
</head>
<body onload="verificar()">
	<form name=formImprimir method="POST" action="formImprimir" onSubmit="return false;">
      <input type="hidden" name="imprimirBoleto">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Imprimir Boleto</h4>
				</div>
				<div class="panel-body">
					
				</div>
				<div class="panel-footer btAcoes">
					<input class="btn btn-success" type="button" onclick="javascript:imprimir();" readonly="readonly" value='GERAR BOLETO' name="cmdImprimir"/>
				</div>
			</div>
		</div>
	</form>
</body>
</html>