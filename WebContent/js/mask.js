jQuery(function($){
	$("#cpf").mask("999.999.999-99");
	$("#cep").mask("99999-999");
	$("#numeroCartao").mask("99999999-9");
	$("#dataNascimento").mask("99/99/9999");
	$("#login").mask("999.999.999-99");
});

function maskTelefone(obj, fun){
    v_obj = obj
    v_fun = fun
    setTimeout("execmask()",1)
}
function execmask(){
    v_obj.value = v_fun(v_obj.value)
}
function maskTel(v){
    v = v.replace(/\D/g,"");             	  //Remove tudo o que não é dígito
    v = v.replace(/^(\d{2})(\d)/g,"($1)$2"); //Coloca parênteses em volta dos dois primeiros dígitos
    //v = v.replace(/(\d)(\d{4})$/,"$1-$2");    //Coloca hífen entre o quarto e o quinto dígitos
    return v;
}