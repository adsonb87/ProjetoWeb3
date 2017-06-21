// FILTRO PROJETO
$(".projetoCheck,.projetoCheck>p").click(function() {
	if($('.nomeProjeto').css("display") != "none"){				
		$('.nomeProjeto').css("display","none");
		$('thead th:nth(0)').css("display","none");	
	}else {
		$('.nomeProjeto').css("display","");
		$('thead th:nth(0)').css("display","");	
	}
});
// FILTRO REDE
$('.redeCheck,.redeCheck>p').click(function() {
	if($('.nomeRede').css("display") != "none"){				
		$('.nomeRede').css("display","none");
		$('thead th:nth(1)').css("display","none");	
	}else {
		$('.nomeRede').css("display","");
		$('thead th:nth(1)').css("display","");	
	}
});
// FILTRO CREDENCIADO
$('.credCheck,.credCheck>p').click(function() {
	if($('.nomeCredenciado').css("display") != "none"){				
		$('.nomeCredenciado').css("display","none");
		$('thead th:nth(2)').css("display","none");	
	}else {
		$('.nomeCredenciado').css("display","");
		$('thead th:nth(2)').css("display","");	
	}
});
// FILTRO LOJA
$('.lojaCheck,.lojaCheck>p').click(function() {
	if($('.nomeLoja').css("display") != "none"){				
		$('.nomeLoja').css("display","none");
		$('thead th:nth(3)').css("display","none");	
	}else {
		$('.nomeLoja').css("display","");
		$('thead th:nth(3)').css("display","");	
	}
});
// FILTRO CANAL
$('.canalCheck,.canalCheck>p').click(function() {
	if($('.nomeCanal').css("display") != "none"){				
		$('.nomeCanal').css("display","none");
		$('thead th:nth(4)').css("display","none");	
	}else {
		$('.nomeCanal').css("display","");
		$('thead th:nth(4)').css("display","");	
	}
});
// FILTRO STATUS
$('.statusCheck,.statusCheck>p').click(function() {
	if($('.statusTerminal').css("display") != "none"){				
		$('.statusTerminal').css("display","none");
		$('thead th:nth(5)').css("display","none");	
	}else {
		$('.statusTerminal').css("display","");
		$('thead th:nth(5)').css("display","");	
	}
});
// FILTRO AÇÕES
$('.dataCheck,.dataCheck>p').click(function() {
	if($('.regDate').css("display") != "none"){				
		$('.regDate').css("display","none");
		$('thead th:nth(6)').css("display","none");	
	}else {
		$('.regDate').css("display","");
		$('thead th:nth(6)').css("display","");	
	}
});