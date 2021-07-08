package br.com.zup.carros

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class CarroControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @field:Inject
    lateinit var carroRepository: CarroRepository

    @BeforeEach
    internal fun setUp() {
      carroRepository.deleteAll()
    }

    @Test
    fun `deve cadastrar um novo carro`() {

        val carro = Carro("Gol Bomba", "AAA9981")

        val request = HttpRequest.POST("api/carro", carro)

        val response = client.toBlocking().exchange(request, Carro::class.java)

        Assertions.assertEquals(HttpStatus.CREATED, response.status)
        Assertions.assertEquals(carro.placa, response.body()!!.placa)
        Assertions.assertEquals(carro.nome, response.body()!!.nome)

    }

    @Test
    fun `verifica se a placa existe no banco`(){

        val carro = Carro("Gol Bomba", "AAA9981")
        carroRepository.save(carro)

        val placa = carroRepository.existsByPlaca("AAA9981")

        Assertions.assertTrue(placa)
    }
}