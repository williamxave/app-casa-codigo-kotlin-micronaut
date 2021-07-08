package br.com.zup.autores

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import java.util.*

@Repository
interface AutorRepository : JpaRepository<Autor, Long> {

    //fun findByEmailContains(email: String, page: Pageable): Page<Autor>

    @Query(
        "SELECT e FROM Autor e WHERE e.email = :email",
        countQuery = "SELECT count(e) FROM Autor e WHERE e.email = :email"
    )
    fun buscaPorEmail(email: String, page: Pageable): Page<Autor>

    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Optional<Autor>
}