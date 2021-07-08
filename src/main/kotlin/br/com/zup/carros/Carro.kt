package br.com.zup.carros

import javax.persistence.*

@Entity
class Carro(
    //@field:NotBlank
    val nome: String,
    // @field:NotBlank @field:Placa
    val placa: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}