package io.tblx.fms.repositories

import io.tblx.fms.models.Fleet
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface FleetRepository : ReactiveSortingRepository<Fleet, Long>, ReactiveCrudRepository<Fleet, Long> {
    fun findAllByOperatorId(operatorId: Long, pageable: Pageable): Flux<Fleet>
    fun findByIdAndOperatorId(id: Long, operatorId: Long): Mono<Fleet>
    fun countAllByOperatorId(operatorId: Long): Mono<Long>
}
