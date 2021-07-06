package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutoresController(val autorRepository: AutorRepository) {

    @Post
    fun cadastra(@Body @Valid novoAutor: AutorRequest): HttpResponse<Any> = novoAutor.paraAutor()
        .let(autorRepository::save)
        .let { autor ->
            val uri = UriBuilder.of("/autores/{id}").expand(mutableMapOf(Pair("id", autor.id)))
            return HttpResponse.created(uri)
        }

    //    val autor =  novoAutor.paraAutor()
    //    autorRepository.save(autor)
    //    val uri = UriBuilder.of("/autores/{id}").expand(mutableMapOf(Pair("id", autor.id)))
    //    return HttpResponse.created(uri)

    @Get
    fun busca(@QueryValue(defaultValue = "") email: String): HttpResponse<Any> {
        autorRepository.findByEmail(email)
            .let {
                if (it.isPresent) {
                    return HttpResponse.ok(AutorResponse(it.get()))
                }
            }.let {
                val autor = autorRepository.findAll()
                    .let { autores ->
                        autores.map { autor ->
                            AutorResponse(autor)
                        }
                    }.let {
                        return HttpResponse.ok(it)
                    }
                return HttpResponse.notFound()
            }
    }
//        if (email.isBlank()) {
//            val autores = autorRepository.findAll()
//            val resposta = autores.map { autor -> AutorResponse(autor) }
//            return HttpResponse.ok(resposta)
//        }
//        val possivelAutor =  autorRepository.findByEmail(email)
//        if(possivelAutor.isEmpty){
//            return HttpResponse.notFound()
//        }
//        return HttpResponse.ok(AutorResponse(possivelAutor.get()))

    @Put("/{id}")
    fun atualiza(@PathVariable id: Long, descricao: String): HttpResponse<Any> =
        autorRepository.findById(id)
            .also {
                if (it.isEmpty) {
                    return HttpResponse.notFound()
                }
            }.run {
                get().descricao = descricao
                autorRepository.update(get())
            }.let { autor ->
                return HttpResponse.ok(AutorResponse(autor))
            }

    @Delete("/{id}")
    fun deletar(@PathVariable id: Long): HttpResponse<Any> {
        autorRepository.findById(id)
            .also {
                if (it.isEmpty) {
                    return HttpResponse.notFound()
                }
            }.run {
                autorRepository.delete(this.get())
                return HttpResponse.ok()
            }
    }

}












