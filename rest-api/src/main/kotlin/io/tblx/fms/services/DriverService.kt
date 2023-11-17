package io.tblx.fms.services

import io.tblx.fms.models.Driver
import io.tblx.fms.repositories.DriverRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DriverService {
    fun getPaginatedByOperator(operatorId: Long, pageable: Pageable): Flux<Driver>
    fun getByOperatorAndId(operatorId: Long, fleetId: Long): Mono<Driver>
    fun getTotalByOperator(operatorId: Long): Mono<Long>
}

@Service
class DriverServiceImpl(private val driverRepository: DriverRepository) : DriverService {
    override fun getPaginatedByOperator(operatorId: Long, pageable: Pageable): Flux<Driver> =
        driverRepository.findAllByOperatorId(operatorId, pageable)

    override fun getByOperatorAndId(operatorId: Long, fleetId: Long): Mono<Driver> =
        driverRepository.findByIdAndOperatorId(fleetId, operatorId)

    override fun getTotalByOperator(operatorId: Long): Mono<Long> =
        driverRepository.countAllByOperatorId(operatorId)
}
