package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client(value = "\${url.api.cep}")
interface CepClient {

    @Get("/{cep}/json")
    fun buscaCep(@PathVariable cep: String) : HttpResponse<EnderecoResponse>
}