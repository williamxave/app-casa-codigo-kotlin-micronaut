package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client(value = "\${url.api.cep}")
interface CepClient {

    //@Get("/{cep}/json")
    @Get(consumes = [ MediaType.APPLICATION_XML ],value = "/{cep}/xml") //  Para consumir xml
    fun buscaCep(@PathVariable cep: String) : HttpResponse<EnderecoResponse>
}