package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutoresController(val autorRepository: AutorRepository) {

    @Post
    fun cadastra(@Body @Valid novoAutor: AutorRequest) = novoAutor.paraAutor().also(autorRepository::save)
    //val autor = novoAutor.paraAutor()
    //autorRepository.save(autor)

    @Get
    fun busca(): HttpResponse<List<AutorResponse>> {
        val resposta = autorRepository.findAll()
            .let { autores ->
                autores.map { autor ->
                    AutorResponse(autor)
                }
            }.run {
                return HttpResponse.ok(this)
            }
    }
}






