package br.com.zup.carros

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Controller
@Validated
class CarroController {

    @Post("/api/carro")
    fun cria(@Body @Valid novoCarro: Carro): HttpResponse<Any> {
        return HttpResponse.created(novoCarro)
    }
}
