package br.com.zup.autores

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest
internal class CadastraAutoresControllerTest2 {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @field:Inject
    lateinit var cepClient: CepClient

    @Test
    fun `deve cadastrar um novo autor`() {
        val enderecoResponse = EnderecoResponse(
            cep = "95765-000",
            logradouro = "Amazonas",
            complemento = "casa",
            bairro = "Sao Paulo",
            localidade = "RS",
            uf = "RS",
            ibge = "NULL",
            gia = "NULL",
            ddd = "52",
            siafi = "teste",
        )

        val novoAutorRequest = AutorRequest("william", "william@email.com", "programador", "95765-000", "1")
        Mockito.`when`(cepClient.buscaCep(novoAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))
        val request = HttpRequest.POST("/autores", novoAutorRequest)

        val response = client.toBlocking().exchange(request, Any::class.java)

        Assertions.assertEquals(HttpStatus.CREATED, response.status)
        Assertions.assertTrue(response.headers.contains("Location"))
        //Assertions.assertTrue(response.header("Location")!!.matches("/autores/\\d".toRegex()))
    }

    @MockBean(CepClient::class)
    fun mockEndereco(): CepClient {
        return Mockito.mock(CepClient::class.java)
    }
}