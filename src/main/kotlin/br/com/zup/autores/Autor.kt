package br.com.zup.autores

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Autor(
    val nome: String,
    val email: String,
    var descricao: String,
    //@field:Embedded val endereco: Endereco
)  {

    @Id
    @GeneratedValue
    var id: Long? = null

    var criadoEm: LocalDateTime = LocalDateTime.now()
}