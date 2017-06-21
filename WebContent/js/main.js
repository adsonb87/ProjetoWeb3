

function Mudarestado(el) {
    var display = document.getElementById(el).style.display;
    if(display == "inline-block")
        document.getElementById(el).style.display = 'none';
    else
        document.getElementById(el).style.display = 'inline-block';
}

function navegar(acao){
	formHome.action = acao;
	formHome.submit();
}

function validandoCampos() {
	if (document.formHome.login.value == "") {
		bootbox.alert("√â necess√°rio informar o Login!",function(){})
		return false;
	}
	if (document.formHome.password.value == "") {
		bootbox.alert("√â necess√°rio informar a Senha!",function(){})
		return false;
	}
	return true;

}


function acessar() {
	if (validandoCampos()) {
		formHome.execCons.value = "true";
		formHome.action = "login";
		formHome.submit();
	}
}

function consultar() {
	formHome.action = "consulta";
	formHome.submit();
}

function consultaCPF() {
	formHome.action = "consultaCPF";
	formHome.submit();
}

function modalExame() {
	$('#modalExame').modal('show');
}

function modalLogin() {
	$('#modalLogin').modal('show');
}

function modalInfo() {
	$('#modalInfo').modal('show');
}

$(document).ready(function() {
	$('#modalLogin').on('hidden.bs.modal', function() {
		formHome.login.value = "";
		formHome.password.value = "";
	})
});

function aceitarTermo(combo) {
	if (combo.checked) {
		$('#aceito').removeClass('disabled');
	} else {
		$('#aceito').addClass('disabled');
	}
}

function modalAviso() {
	$('#modalAviso').modal('show');
}

function Enter(e) {
	if (e.keyCode == 13) {
		acessar();
	}
}

function verificacao() {
		var msg = "<%=request.getAttribute('msgLogin')%>";
		if (msg.length > 4) {
			$('#modalLogin').modal('show');
		}
	}

function pesquisaNaTabela() {
  //FunÁ„o para pesquisa din‚mica em tabelas - INÕCIO
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
  //FunÁ„o para pesquisa din‚mica em tabelas - FIM
}
