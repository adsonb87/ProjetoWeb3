<%@page import="java.util.List"%>

<header>
	<div class="container">
		<div class="row">
			<div class="cabecalho col-xs-12 col-lg-12 text-center">
				<div class="row">
					<div  class="col-xs-8 col-md-4 col-lg-4 text-left logo">
						<a href="inicio">
							<img src="imagens/logo_vem1.png" class="img-responsive">
						</a>
					</div>
					<div class="col-xs-4 col-md-2 col-lg-2 menuHeader">
						<c:if test="${servlet == 'inicio'}">
							<a href="#" data-toggle="modal" onclick="modalLogin()" title="Logar">
								<span id="btnLogin" class="glyphicon glyphicon-user glyphiconLogin"></span>
							</a>
						</c:if>
						<c:if test="${servlet == 'acompanhamento'}">
							<a href="inicio" title="Logout">
								<span id="btnLogout" class="glyphicon glyphicon-remove glyphiconLogin"></span>
							</a>
						</c:if>
					</div>
					<div class="col-xs-12 col-md-6 col-lg-6 text-center produto">
						<h3>CADASTRO DO VEM COMUM</h3>
					</div>
				</div>
			</div>
		</div>
	</div>
</header>
