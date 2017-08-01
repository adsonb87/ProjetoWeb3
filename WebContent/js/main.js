function navegar(acao){
	formHome.action = acao;
	formHome.submit();
}

function verificacao() {
		var msg = "<%=request.getAttribute('msgLogin')%>";
		if (msg.length > 4) {
			$('#modalLogin').modal('show');
		}
	}

function pesquisaNaTabela() {
  //Fun��o para pesquisa din�mica em tabelas - IN�CIO
    $(".form-control").keyup(function(){
               //pega o css da tabela
                var tabela = $(this).attr('alt');
                if( $(this).val() != ""){
                    $("."+tabela+" tbody>tr").hide();
                    $("."+tabela+" td:contains-ci('" + $(this).val() + "')").parent("tr").show();
                } else{
                    $("."+tabela+" tbody>tr").show();
                }
            });
      $.extend($.expr[":"], {
            "contains-ci": function(elem, i, match, array) {
                return (elem.textContent || elem.innerText || $(elem).text() || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
            }
        });
  //Fun��o para pesquisa din�mica em tabelas - FIM
}
