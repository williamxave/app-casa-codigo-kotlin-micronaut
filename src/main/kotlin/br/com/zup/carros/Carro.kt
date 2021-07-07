package br.com.zup.carros

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class Carro(
    @field:NotBlank val nome: String?,
    @field:NotBlank @field:Placa val placa: String?
){

}