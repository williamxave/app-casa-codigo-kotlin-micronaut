package br.com.zup.autores

import javax.persistence.Embeddable

@Embeddable
class Endereco(
    enderecoResponse: EnderecoResponse,
    val numero: String
) {
    val cep = enderecoResponse.cep
    val logradouro = enderecoResponse.logradouro
    val complemento = enderecoResponse.complemento
    val bairro = enderecoResponse.bairro
}