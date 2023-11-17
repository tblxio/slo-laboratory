package io.tblx.fms.repositories

import io.tblx.fms.models.Operator
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Flux

interface OperatorRepository : ReactiveSortingRepository<Operator, Long>, ReactiveCrudRepository<Operator, Long> {
    fun findAllBy(pageable: Pageable): Flux<Operator>
}
