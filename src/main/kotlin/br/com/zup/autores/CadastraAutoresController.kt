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
    fun busca(): HttpResponse<List<AutorResponse>> {
        val autor = autorRepository?.findAll()
            .let { autores ->
                autores.map { autor ->
                    AutorResponse(autor)
                }
            }.run {
                if (this.isEmpty()) {
                    return HttpResponse.notFound()
                }
                return HttpResponse.ok(this)
            }
    }

    @Put("/{id}")
    fun atualiza(@PathVariable id: Long, descricao: String): HttpResponse<Any> =
        autorRepository.findById(id).also {
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
        autorRepository.findById(id).also {
            if (it.isEmpty) {
                return HttpResponse.notFound()
            }
        }.run {
            autorRepository.delete(this.get())
            return HttpResponse.ok()
        }
    }
}












