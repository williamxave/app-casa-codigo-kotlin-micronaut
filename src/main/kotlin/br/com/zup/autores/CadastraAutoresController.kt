package br.com.zup.autores

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/autores")
class CadastraAutoresController {

    @Post
    fun cadastra(@Body novoAutor: AutorRequest){
        println(novoAutor.toString())
    }
}