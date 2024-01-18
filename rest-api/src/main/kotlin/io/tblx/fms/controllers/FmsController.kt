package io.tblx.fms.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import io.tblx.fms.exceptions.InvalidFleetOperatorRelationException
import io.tblx.fms.models.Driver
import io.tblx.fms.models.Fleet
import io.tblx.fms.models.Operator
import io.tblx.fms.models.Vehicle
import io.tblx.fms.services.DriverService
import io.tblx.fms.services.FleetService
import io.tblx.fms.services.OperatorService
import io.tblx.fms.services.VehicleService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.support.PageableExecutionUtils.paged
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import java.lang.IllegalStateException

/**
 * Controller that handles CRUD operations for operators, fleets, drivers and vehicles.
 * * Exposes REST endpoints for fetching paginated lists of resources as well as individual resources by ID.
 * * Implements error handling logic such as:
 * - Mapping various exceptions to appropriate HTTP error statuses
 * - Catching invalid sort parameters to avoid 500 errors
*/
@Tag(name = "FMS")
interface FmsController {
    companion object {
        const val OPERATOR_ID_PARAM: String = "operatorId"
    }

    @Operation(summary = "Fetch operators in a paginated fashion")
    @GetMapping
    @Annotations.PageableParameter
    fun getOperators(
        @Parameter(hidden = true) pageable: Pageable
    ): Mono<Page<Operator>>

    @Operation(summary = "Get operator by id")
    @GetMapping("/{${Params.OPERATOR_ID}}")
    @Annotations.OperatorIdParameter
    fun getOperator(@PathVariable(Params.OPERATOR_ID) id: Long): Mono<Operator>

    @Operation(summary = "Get fleets associated to an operator")
    @GetMapping("/{${Params.OPERATOR_ID}}/fleets")
    @Annotations.OperatorIdParameter
    @Annotations.PageableParameter
    fun getFleetsByOperator(
        @PathVariable(Params.OPERATOR_ID) operatorId: Long,
        @Parameter(hidden = true) pageable: Pageable
    ): Mono<Page<Fleet>>

    @Operation(summary = "Get a fleet by ID associated to an operator")
    @GetMapping("/{${Params.OPERATOR_ID}}/fleets/{${Params.FLEET_ID}}")
    @Annotations.OperatorIdParameter
    @Annotations.FleetIdParameter
    fun getFleetByIdAndOperator(
        @PathVariable(Params.OPERATOR_ID) operatorId: Long,
        @PathVariable(Params.FLEET_ID) fleetId: Long
    ): Mono<Fleet>

    @Operation(summary = "Get drivers associated to an operator")
    @GetMapping("/{${Params.OPERATOR_ID}}/drivers")
    @Annotations.OperatorIdParameter
    @Annotations.PageableParameter
    fun getDriversByOperator(
        @PathVariable(Params.OPERATOR_ID) operatorId: Long,
        @Parameter(hidden = true) pageable: Pageable
    ): Mono<Page<Driver>>

    @Operation(summary = "Get a driver by ID associated to an operator")
    @GetMapping("/{${Params.OPERATOR_ID}}/drivers/{${Params.DRIVER_ID}}")
    @Annotations.OperatorIdParameter
    @Annotations.DriverIdParameter
    fun getDriverByIdAndOperator(
        @PathVariable(Params.OPERATOR_ID) operatorId: Long,
        @PathVariable(Params.DRIVER_ID) driverId: Long
    ): Mono<Driver>

    @Operation(summary = "Get vehicles of a fleet and operator")
    @GetMapping("/{${Params.OPERATOR_ID}}/fleets/{${Params.FLEET_ID}}/vehicles")
    @Annotations.OperatorIdParameter
    @Annotations.FleetIdParameter
    @Annotations.PageableParameter
    fun getVehiclesByFleetAndOperator(
        @PathVariable(Params.OPERATOR_ID) operatorId: Long,
        @PathVariable(Params.FLEET_ID) fleetId: Long,
        @Parameter(hidden = true) pageable: Pageable
    ): Mono<Page<Vehicle>>

    @Operation(summary = "Get a vehicle by ID of a fleet and operator")
    @GetMapping("/{${Params.OPERATOR_ID}}/fleets/{${Params.FLEET_ID}}/vehicles/{${Params.VEHICLE_ID}}")
    @Annotations.OperatorIdParameter
    @Annotations.FleetIdParameter
    @Annotations.VehicleIdParameter
    fun getVehicleByIdAndFleetAndOperator(
        @PathVariable(Params.OPERATOR_ID) operatorId: Long,
        @PathVariable(Params.FLEET_ID) fleetId: Long,
        @PathVariable(Params.VEHICLE_ID) vehicleId: Long
    ): Mono<Vehicle>
}

@RestController
@RequestMapping("/operators")
class FmsControllerImpl(
    private val operatorService: OperatorService,
    private val fleetService: FleetService,
    private val vehicleService: VehicleService,
    private val driverService: DriverService
) : FmsController {

    companion object {
        private val LOG = LoggerFactory.getLogger(FmsControllerImpl::class.java)
    }

    override fun getOperators(pageable: Pageable): Mono<Page<Operator>> =
        operatorService
            .getPaginated(pageable)
            .paged(pageable, operatorService.getTotal())
            .recoverSortError()

    override fun getOperator(id: Long): Mono<Operator> =
        operatorService
            .getById(id)
            .doOnError { LOG.error("Get operator failed.", it) }
            .notFoundIfEmpty()

    override fun getFleetsByOperator(operatorId: Long, pageable: Pageable): Mono<Page<Fleet>> =
        fleetService
            .getPaginatedByOperator(operatorId, pageable)
            .paged(pageable, fleetService.getTotalByOperator(operatorId))
            .recoverSortError()

    override fun getFleetByIdAndOperator(operatorId: Long, fleetId: Long): Mono<Fleet> =
        fleetService
            .getByOperatorAndId(operatorId, fleetId)
            .doOnError { LOG.error("Get fleet failed.", it) }
            .notFoundIfEmpty()

    override fun getDriversByOperator(operatorId: Long, pageable: Pageable): Mono<Page<Driver>> =
        driverService
            .getPaginatedByOperator(operatorId, pageable)
            .paged(pageable, driverService.getTotalByOperator(operatorId))
            .recoverSortError()

    override fun getDriverByIdAndOperator(operatorId: Long, driverId: Long): Mono<Driver> =
        driverService
            .getByOperatorAndId(operatorId, driverId)
            .doOnError { LOG.error("Get driver failed.", it) }
            .notFoundIfEmpty()

    override fun getVehiclesByFleetAndOperator(operatorId: Long, fleetId: Long, pageable: Pageable): Mono<Page<Vehicle>> =
        vehicleService
            .getPaginatedByOperatorAndFleet(operatorId, fleetId, pageable)
            .paged(pageable, vehicleService.getTotalByOperatorAndFleet(operatorId, fleetId))
            .onErrorMap(InvalidFleetOperatorRelationException::class.java) {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND
                )
            }
            .recoverSortError()

    override fun getVehicleByIdAndFleetAndOperator(operatorId: Long, fleetId: Long, vehicleId: Long): Mono<Vehicle> =
        vehicleService
            .getVehicleByOperatorAndFleetAndId(operatorId, fleetId, vehicleId)
            .onErrorMap(InvalidFleetOperatorRelationException::class.java) {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND
                )
            }
            .notFoundIfEmpty()

    /**
     * If the user inputs an invalid `sort` parameter, R2DBC throws an `IllegalStateException`.
     * Here we recover it since it should not be a server error.
     */
    private fun <T> Mono<Page<T>>.recoverSortError(): Mono<Page<T>> =
        this.onErrorMap { error ->
            when (error) {
                // Catch invalid `sort` to avoid 500
                is IllegalStateException ->
                    ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid 'sort' parameter")

                is ResponseStatusException ->
                    error

                else -> {
                    LOG.error("Get operators failed.", error)
                    error
                }
            }
        }

    /**
     * For operations that are supposed to return 1 element, returns 404 if the result is empty.
     */
    private fun <T> Mono<T>.notFoundIfEmpty(): Mono<T> =
        this.switchIfEmpty(
            Mono.error(
                ResponseStatusException(
                    HttpStatus.NOT_FOUND
                )
            )
        )
}
