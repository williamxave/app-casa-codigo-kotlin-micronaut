package br.com.zup.autores

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutoresController {

    @Post
    fun cadastra(@Body @Valid novoAutor: AutorRequest){
        println(novoAutor.toString())
    }
}