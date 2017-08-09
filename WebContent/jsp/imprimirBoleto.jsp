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
			$('#modalAtencao').modal('show');
		}
	}

	function imprimir() {
		formImprimir.nome.value = "${ usuario.nome }";
		formImprimir.imprimirBoleto.value = "true";
		formImprimir.action = "imprimirBoleto";
		formImprimir.submit();
	}
	
	function cadastrarUser() {
		formImprimir.cadastrarUser.value = "true";
		formImprimir.action = "cadastroUser";
		formImprimir.submit();
	}
	
	function cancelar() {
		formImprimir.action = "inicio";
		formImprimir.submit();
	}

</script>
</head>
<body onload="verificar()">
	<form name=formImprimir method="POST" action="formImprimir" onSubmit="return false;">
      <input type="hidden" name="imprimirBoleto">
      <input type="hidden" name="cadastrarUser">
      <input type="hidden" name="nome">
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Emitir Boleto</h4>
				</div>
				<div class="panel-body">
					<div class="row">
       					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
				            <div class="well well-sm">
				                <div class="row">
				                    <div class="col-md-12" style="text-align: center;">
				                        <c:if test="${not empty usuario }">
				                        	<h5>
					                        	${ usuario.nome }
					                        </h5>
					                        <small>
												<cite><i class="glyphicon glyphicon-map-marker"></i> ${ usuario.enderecoFormatado }</cite>
											</small>
											<c:if test="${not empty usuario.cpfFormatado }">
												<p id="dadosUsu"><i class="glyphicon glyphicon-credit-card"></i> CPF: ${ usuario.cpfFormatado }</p>
											</c:if>
											<c:if test="${not empty usuario.email }">
												<p id="dadosUsu"><i class="glyphicon glyphicon-envelope"></i> ${ usuario.email }</p>
											</c:if>
											<c:if test="${not empty usuario.dataNascimento }">
												<p id="dadosUsu"><i class="glyphicon glyphicon-calendar"></i> ${ usuario.dataNascimento }</p>
											</c:if>
											<c:if test="${not empty usuario.telefoneFormatado }">
												<p id="dadosUsu"><i class="glyphicon glyphicon-phone"></i> ${ usuario.telefoneFormatado }</p>
											</c:if>
				                        </c:if>
				                    </div>
				                </div>
				            </div>
				        </div>
					</div>
				</div>
				<div class="panel-footer btAcoes">
					<input class="btn btn-success" type="button" onclick="javascript:imprimir();" readonly="readonly" id="gerar" value='IMPRIMIR BOLETO' name="cmdImprimir"/>
				</div>
			</div>
		</div>
	</form>
	<!-- Modal Atenção -->
	<div class="modal fade" id="modalAtencao" tabindex="-1" role="dialog" aria-labelledby="modalAtencao" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog modalLogin" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<b class="modal-title" id="modalAtencao">Atenção!</b>
					<button type="button" class="close"  onclick="javascript:cancelar();">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
	        		<p> Seu cartão já foi solicitado, para acompanhar o status do seu pedido, imprimir segunda via do boleto, basta cadastrar uma senha no nosso portal.</p>
	        		<p> Deseja cadastrar a senha agora? </p>
	        	</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" data-dismiss="modal" onclick="javascript:cadastrarUser();">SIM</button>
					<button type="button" class="btn btn-success" data-dismiss="modal" onclick="javascript:cancelar();">NÃO</button>
				</div>
			</div>
		</div>	
	</div>
</body>
</html>