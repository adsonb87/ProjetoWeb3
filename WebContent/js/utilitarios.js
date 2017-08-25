function validarNumero(obj) {
	var valor = obj.value;
	var reInteger = /^\d+$/;
	var resultado = reInteger.test(valor);

	if (!resultado && obj.value != "") {
		alert("O campo só aceita números!");
		obj.value = valor.replace(/[a-zA-Z]/g, "");
		return false;
	}
	return true;
}

function validandoCPF(strCPF) {
	var Soma;
	var Resto;
	Soma = 0;
	if (strCPF == "00000000000")
		return false;

	for (i = 1; i <= 9; i++)
		Soma = Soma + parseInt(strCPF.substring(i - 1, i)) * (11 - i);
	Resto = (Soma * 10) % 11;

	if ((Resto == 10) || (Resto == 11))
		Resto = 0;
	if (Resto != parseInt(strCPF.substring(9, 10)))
		return false;

	Soma = 0;
	for (i = 1; i <= 10; i++)
		Soma = Soma + parseInt(strCPF.substring(i - 1, i)) * (12 - i);
	Resto = (Soma * 10) % 11;

	if ((Resto == 10) || (Resto == 11))
		Resto = 0;
	if (Resto != parseInt(strCPF.substring(10, 11)))
		return false;
	return true;
}
bootbox.setDefaults({
	  locale: "br",
	  show: true,
	  backdrop: true,
	  closeButton: true,
	  animate: true,
	  className: "my-modal"
	  
	});

function intervaloCorreto(objetoIni, objetoFim) {
	var retorno = true;
	var diaI, diaF, mesI, mesF, anoI, anoF, mensagemD;
	
	if (objetoIni.value != "" && objetoFim.value != "") {
		mensagemD = "Intervalo das Datas incorreto!";
		diaI = objetoIni.value.substring(0, 2)
		mesI = objetoIni.value.substring(3, 5)
		anoI = objetoIni.value.substring(6, 10)
	
		diaF = objetoFim.value.substring(0, 2)
		mesF = objetoFim.value.substring(3, 5)
		anoF = objetoFim.value.substring(6, 10)
	
		if (anoI > anoF) {
			bootbox.alert(mensagemD,function(){});
			retorno = false;
		} else if (anoI == anoF) {
			if (mesI > mesF) {
				bootbox.alert(mensagemD,function(){});
				retorno = false;
			} else if(mesI == mesF) {
				if (diaI > diaF) {
					bootbox.alert(mensagemD,function(){});
					retorno = false;
				}
			  }
		}
	}
	
	if(!retorno){
		objetoIni.focus();
	}
  return retorno;
}


function FormataData(event, objeto) {

	var tecla, tamanho;

	tecla = event.keyCode ? event.keyCode : event.which;

	if (tecla != 8) {
		tamanho = objeto.value.length;
		if (tamanho == 2 || tamanho == 5) {

			objeto.value = objeto.value + "/";
		}
	}

}

function FormataHrMM(event, objeto) {

	var tecla, tamanho;

	tecla = event.keyCode ? event.keyCode : event.which;
	if (tecla != 8) {
		tamanho = objeto.value.length;
		if (tamanho == 2) {
			objeto.value = objeto.value + ":";
		} else if (tamanho == 5) {
			objeto.value = objeto.value + " as ";
		} else if (tamanho == 11) {
			objeto.value = objeto.value + ":";
		}
	}

}

function isValidoCNPJ(obj) {
	var cnpj = obj.value;
	cnpj = cnpj.replace(".", "");
	cnpj = cnpj.replace(".", "");
	cnpj = cnpj.replace("-", "");
	cnpj = cnpj.replace("/", "");
	var numeros, digitos, soma, i, resultado, pos, tamanho, digitos_iguais;
	digitos_iguais = 1;
	if (cnpj.length < 14 && cnpj.length < 15)
		return false;
	for (i = 0; i < cnpj.length - 1; i++)
		if (cnpj.charAt(i) != cnpj.charAt(i + 1)) {
			digitos_iguais = 0;
			break;
		}
	if (!digitos_iguais) {
		tamanho = cnpj.length - 2
		numeros = cnpj.substring(0, tamanho);
		digitos = cnpj.substring(tamanho);
		soma = 0;
		pos = tamanho - 7;
		for (i = tamanho; i >= 1; i--) {
			soma += numeros.charAt(tamanho - i) * pos--;
			if (pos < 2)
				pos = 9;
		}
		resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
		if (resultado != digitos.charAt(0))
			return false;
		tamanho = tamanho + 1;
		numeros = cnpj.substring(0, tamanho);
		soma = 0;
		pos = tamanho - 7;
		for (i = tamanho; i >= 1; i--) {
			soma += numeros.charAt(tamanho - i) * pos--;
			if (pos < 2)
				pos = 9;
		}
		resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
		if (resultado != digitos.charAt(1))
			return false;
		return true;
	} else
		return false;
}

function isValidoCPF(cpf) {

	cpf = cpf.replace(/[^\d]+/g, '');

	if (cpf == '')
		return false;

	// Elimina CPFs invalidos conhecidos
	if (cpf.length != 11 || cpf == "00000000000" || cpf == "11111111111"
			|| cpf == "22222222222" || cpf == "33333333333"
			|| cpf == "44444444444" || cpf == "55555555555"
			|| cpf == "66666666666" || cpf == "77777777777"
			|| cpf == "88888888888" || cpf == "99999999999")
		return false;

	// Valida 1o digito
	add = 0;
	for (i = 0; i < 9; i++)
		add += parseInt(cpf.charAt(i)) * (10 - i);
	rev = 11 - (add % 11);
	if (rev == 10 || rev == 11)
		rev = 0;
	if (rev != parseInt(cpf.charAt(9)))
		return false;

	// Valida 2o digito
	add = 0;
	for (i = 0; i < 10; i++)
		add += parseInt(cpf.charAt(i)) * (11 - i);
	rev = 11 - (add % 11);
	if (rev == 10 || rev == 11)
		rev = 0;
	if (rev != parseInt(cpf.charAt(10)))
		return false;

	return true;

}

function Mascara(o, f) {
	v_obj = o;
	v_fun = f;
	setTimeout("execmascara()", 1);
}

function execmascara() {
	v_obj.value = v_fun(v_obj.value)
}
function apenasNumeroseVirgulas(v) {
	return v.replace(/[^0-9,]/g, "");
}

function apenasNumerosDatas(v) {
	return v.replace(/[^0-9]/g, "");
}

function apenasNumeros(v) {
	return v.replace(/\D/g, "");
}

function validarData(data) {
	var RegExPattern = /^((((0?[1-9]|[12]\d|3[01])[\.\-\/](0?[13578]|1[02])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|[12]\d|30)[\.\-\/](0?[13456789]|1[012])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|1\d|2[0-8])[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|(29[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|[12]\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|1\d|2[0-8])02((1[6-9]|[2-9]\d)?\d{2}))|(2902((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$/;
	if (!((data.match(RegExPattern)) && (data!=''))) {
		return false;
	}
	return true;
}

function validarEmail(email) {
	var usuario = email.substring(0, email.indexOf("@"));
	var dominio = email.substring(email.indexOf("@")+ 1, email.length);
	if (!((usuario.length >=1) &&
	    (dominio.length >= 3) &&
	    (usuario.search("@") == -1) &&
	    (dominio.search("@") == -1) &&
	    (usuario.search(" ") == -1) &&
	    (dominio.search(" ") == -1) &&
	    (dominio.search(".") != -1) &&
	    (dominio.indexOf(".") >= 1)&&
	    (dominio.lastIndexOf(".") < dominio.length - 1))) {
		return false;
	}
	return true;
}
