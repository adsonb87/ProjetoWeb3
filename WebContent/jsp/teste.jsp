<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="js/bootstrap-select.min.js"></script>	
<link rel="stylesheet" href="css/bootstrap-select.min.css">
	
<body onload="verificacao()">
	<form name=formTeste method="POST" action="alteracao">
		<div class="vinculo">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon1" style="padding-top:10px">Credenciado:</span>
								<div class="bs-docs-example">
									<select class="form-control" id="idCredenciado" name="idCredenciado">
										<option value="">.:SELECIONAR:.</option>
										<c:forEach var="cred" items="${lcred}">						
											<option value="${ cred.idCredenciado }">${cred.nomeCredenciado}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>	
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon1">Rede:</span>
								<div class="bs-docs-example">
									<select class="form-control" id="filtroRede" name="filtroRede">
										<option value="">.:SELECIONAR:.</option>
										<c:forEach var="red" items="${lred}">
										<option value="${ red.idRede }" >${red.nomeRede}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon1">Projeto:</span>
								<div class="bs-docs-example">
									<select class="form-control" id="idProjeto" name="idProjeto">
										<option value="">.:SELECIONAR:.</option>
										<c:forEach var="prj" items="${lprj}">										
										<option value="${ prj.idProjeto }" >${prj.nomeProjeto}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>
	</form>
</body>