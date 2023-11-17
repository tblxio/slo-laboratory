package io.tblx.fms.repositories

import io.tblx.fms.models.Driver
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface DriverRepository : ReactiveSortingRepository<Driver, Long>, ReactiveCrudRepository<Driver, Long> {
    fun findAllByOperatorId(operatorId: Long, pageable: Pageable): Flux<Driver>
    fun findByIdAndOperatorId(id: Long, operatorId: Long): Mono<Driver>
    fun countAllByOperatorId(operatorId: Long): Mono<Long>
}
