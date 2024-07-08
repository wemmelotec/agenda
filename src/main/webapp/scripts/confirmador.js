/**
 * Confirmação de exclusão de um contato
 */

function confirmar(id){
	let resposta = confirm("Confirmar a exclusão deste contato?")
	if(resposta === true){
		//teste de recebimento
		//alert(id)
		window.location.href = "delete?id="+id
	}
}