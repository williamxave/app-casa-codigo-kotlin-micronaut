package br.com.zup.autores

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
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

}
