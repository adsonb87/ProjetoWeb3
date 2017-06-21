<%@ taglib uri="/WEB-INF/displaytag" prefix="display"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html>
<head>
	<!-- Avisando ao navegador sobre responsividade -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- /Avisando ao navegador sobre responsividade -->

	<!-- importando arquivos -->


	<!-- ARQUIVO/CONFIGURAÇÕES DO WEBAPP -->
	<link rel="manifest" href="jsp/manifest.json">
	<meta name="theme-color" content="#1f3a4d">
	<!-- /ARQUIVO/CONFIGURAÇÕES DO WEBAPP -->

	<!-- JS -->
	<script src="js/jquery/jquery-1.12.3.min.js" type="text/javascript"></script>
	<script src="js/jquery.mask.min.js"></script>
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.maskedinput.js"></script>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
	<script src="js/bootstrap/bootbox.min.js" charset="utf-8" ></script>
	<script type="text/javascript" src="http://www.google.com/jsapi"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
	<script type="text/javascript" src="js/filtros.js"></script>
	<script language="javascript" src="js/Calendario.js" type=""></script>
	<script language="javascript" src="js/utilitarios.js" charset="utf-8" type=""></script>
	<script language="javascript" src="js/bootstrap-select.min.js"  charset="utf-8"  type=""></script>	
	<!-- /JS -->

	<!-- CSS -->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/imagens.css" rel="stylesheet">
	<link href="css/style2.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link rel="stylesheet" href="css/bootstrap-select.min.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<link rel="stylesheet" type="text/css" href="css/reset.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
	<!-- /CSS -->
	<!-- /importando arquivos -->


	<!-- FAVICONS -->
	<link rel="shortcut icon" href="imagens/favicons/android-icon-192x192.png" />

	<link rel="apple-touch-icon" sizes="57x57" href="imagens/favicons/apple-icon-57x57.png">
	<link rel="apple-touch-icon" sizes="60x60" href="imagens/favicons/apple-icon-60x60.png">
	<link rel="apple-touch-icon" sizes="72x72" href="imagens/favicons/apple-icon-72x72.png">
	<link rel="apple-touch-icon" sizes="76x76" href="imagens/favicons/apple-icon-76x76.png">
	<link rel="apple-touch-icon" sizes="114x114" href="imagens/favicons/apple-icon-114x114.png">
	<link rel="apple-touch-icon" sizes="120x120" href="imagens/favicons/apple-icon-120x120.png">
	<link rel="apple-touch-icon" sizes="144x144" href="imagens/favicons/apple-icon-144x144.png">
	<link rel="apple-touch-icon" sizes="152x152" href="imagens/favicons/apple-icon-152x152.png">
	<link rel="apple-touch-icon" sizes="180x180" href="imagens/favicons/apple-icon-180x180.png">

	<link rel="icon" type="image/png" sizes="192x192"  href="imagens/favicons/android-icon-192x192.png">
	<link rel="icon" type="image/png" sizes="32x32" href="imagens/favicons/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="96x96" href="imagens/favicons/favicon-96x96.png">
	<link rel="icon" type="image/png" sizes="16x16" href="imagens/favicons/favicon-16x16.png">


	<meta name="msapplication-TileColor" content="#ffffff">
	<meta name="msapplication-TileImage" content="imagens/favicons/ms-icon-144x144.png">
	<!-- /FAVICONS -->

	<title>VEM Comum</title>
	<script type="text/javascript">
		function navegar(acao){
			formHome.action = acao;
			formHome.submit();
		}
	</script>
</head>
<body>
	<%@ include file="header.jsp"%>
</body>
<script type="text/javascript">
	verificacao();
</script>
</html>
