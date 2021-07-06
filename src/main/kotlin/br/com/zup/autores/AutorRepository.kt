package br.com.zup.autores

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

@Repository
interface AutorRepository : JpaRepository<Autor, Long> {
    fun findByEmailContains(email: String, page: Pageable): Page<Autor>
}