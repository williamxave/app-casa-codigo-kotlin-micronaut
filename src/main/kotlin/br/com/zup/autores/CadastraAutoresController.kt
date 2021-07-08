package br.com.zup.autores

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutoresController(
    val autorRepository: AutorRepository,
    val cepClient: CepClient
) {

    @Post
    @Transactional
    fun cadastra(@Body @Valid novoAutor: AutorRequest): HttpResponse<Any> {
        return with(cepClient.buscaCep(novoAutor.cep)) {
            val possivelAutor = novoAutor.paraAutor(this.body()!!)
            autorRepository.save(possivelAutor)
                .run {
                    UriBuilder.of("/autores/{id}").expand(mutableMapOf(Pair("id", this.id)))
                }.let { HttpResponse.created(it) }
        }
    }

    //    val autor =  novoAutor.paraAutor()
    //    autorRepository.save(autor)
    //    val uri = UriBuilder.of("/autores/{id}").expand(mutableMapOf(Pair("id", autor.id)))
    //    return HttpResponse.created(uri)

    @Get
    fun busca(
        @QueryValue(defaultValue = "") email: String,
        pageable: Pageable
    ): MutableHttpResponse<Any> {
        if (autorRepository.existsByEmail(email)) {
//            return autorRepository.buscaPorEmail(email, pageable).run { map(::AutorResponse) }
//                .let { HttpResponse.ok(it) }
           // val possivelAutor = autorRepository.buscaPorEmail(email, pageable)
            val possivelAutor = autorRepository.findByEmail(email)
            return HttpResponse.ok(possivelAutor.map { autor -> AutorResponse(autor) })
        }
        // return autorRepository.findAll(pageable).run { map(::AutorResponse) }.let { HttpResponse.ok(it) }
        val autores = autorRepository.findAll(pageable)
        return HttpResponse.ok(autores.map { autor -> AutorResponse(autor) })
    }

    @Put("/{id}")
    fun atualiza(@PathVariable id: Long, descricao: String): HttpResponse<Any> {

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












