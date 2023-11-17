package io.tblx.fms.repositories

import io.tblx.fms.models.Vehicle
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface VehicleRepository : ReactiveSortingRepository<Vehicle, Long>, CrudRepository<Vehicle, Long> {
    fun findAllByFleetId(fleetId: Long, pageable: Pageable): Flux<Vehicle>
    fun findByIdAndFleetId(id: Long, fleetId: Long): Mono<Vehicle>
    fun countAllByFleetId(fleetId: Long): Mono<Long>
}
