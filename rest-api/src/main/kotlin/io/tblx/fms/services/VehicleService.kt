package io.tblx.fms.services

import io.tblx.fms.exceptions.InvalidFleetOperatorRelationException
import io.tblx.fms.models.Fleet
import io.tblx.fms.models.Vehicle
import io.tblx.fms.repositories.VehicleRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

interface VehicleService {
    fun getPaginatedByOperatorAndFleet(operatorId: Long, fleetId: Long, pageable: Pageable): Flux<Vehicle>
    fun getVehicleByOperatorAndFleetAndId(operatorId: Long, fleetId: Long, vehicleId: Long): Mono<Vehicle>
    fun getTotalByOperatorAndFleet(operatorId: Long, fleetId: Long): Mono<Long>
}

@Service
class VehicleServiceImpl(
    private val fleetService: FleetService,
    private val vehicleRepository: VehicleRepository
) : VehicleService {
    override fun getPaginatedByOperatorAndFleet(operatorId: Long, fleetId: Long, pageable: Pageable): Flux<Vehicle> =
        getFleetByOperator(operatorId, fleetId)
            .flatMapMany { vehicleRepository.findAllByFleetId(fleetId, pageable) }

    override fun getTotalByOperatorAndFleet(operatorId: Long, fleetId: Long): Mono<Long> =
        getFleetByOperator(operatorId, fleetId)
            .flatMap { vehicleRepository.countAllByFleetId(fleetId) }

    override fun getVehicleByOperatorAndFleetAndId(operatorId: Long, fleetId: Long, vehicleId: Long): Mono<Vehicle> =
        getFleetByOperator(operatorId, fleetId)
            .flatMap { vehicleRepository.findByIdAndFleetId(vehicleId, fleetId) }

    private fun getFleetByOperator(operatorId: Long, fleetId: Long): Mono<Fleet> =
        fleetService
            // Check if the fleet is associated to the correct operator
            .getByOperatorAndId(operatorId, fleetId)
            .switchIfEmpty { Mono.error(InvalidFleetOperatorRelationException()) }
}
