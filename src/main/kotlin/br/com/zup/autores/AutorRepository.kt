package br.com.zup.autores

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import java.util.*

@Repository
interface AutorRepository : JpaRepository<Autor, Long> {
    fun findByEmailContaining(email: String, page: Pageable): Page<Autor>
}