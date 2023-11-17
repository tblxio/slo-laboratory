package io.tblx.fms.services

import io.tblx.fms.models.Fleet
import io.tblx.fms.repositories.FleetRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface FleetService {
    fun getPaginatedByOperator(operatorId: Long, pageable: Pageable): Flux<Fleet>
    fun getByOperatorAndId(operatorId: Long, fleetId: Long): Mono<Fleet>
    fun getTotalByOperator(operatorId: Long): Mono<Long>
}

@Service
class FleetServiceImpl(private val fleetRepository: FleetRepository) : FleetService {
    override fun getPaginatedByOperator(operatorId: Long, pageable: Pageable): Flux<Fleet> =
        fleetRepository.findAllByOperatorId(operatorId, pageable)

    override fun getByOperatorAndId(operatorId: Long, fleetId: Long): Mono<Fleet> =
        fleetRepository.findByIdAndOperatorId(fleetId, operatorId)

    override fun getTotalByOperator(operatorId: Long): Mono<Long> =
        fleetRepository.countAllByOperatorId(operatorId)
}
