package io.tblx.fms.controllers

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

object Params {
    const val DRIVER_ID: String = "driverId"
    const val FLEET_ID: String = "fleetId"
    const val OPERATOR_ID: String = "operatorId"
    const val VEHICLE_ID: String = "vehicleId"
}

object Annotations {

    @Parameter(
        name = Params.DRIVER_ID,
        `in` = ParameterIn.PATH,
        description = "ID of the driver",
        required = true,
        example = "1"
    )
    annotation class DriverIdParameter

    @Parameter(
        name = Params.FLEET_ID,
        `in` = ParameterIn.PATH,
        description = "ID of the fleet",
        required = true,
        example = "1"
    )
    annotation class FleetIdParameter

    @Parameter(
        name = Params.OPERATOR_ID,
        `in` = ParameterIn.PATH,
        description = "ID of the operator",
        required = true,
        example = "1"
    )
    annotation class OperatorIdParameter

    @Parameter(
        name = Params.VEHICLE_ID,
        `in` = ParameterIn.PATH,
        description = "ID of the vehicle",
        required = true,
        example = "1"
    )
    annotation class VehicleIdParameter

    @Parameters(
        value = [
            Parameter(
                name = "page",
                `in` = ParameterIn.QUERY,
                description = "Results page you want to retrieve (0..N)",
                required = false,
                example = "0"
            ),
            Parameter(
                name = "size",
                `in` = ParameterIn.QUERY,
                description = "Number of records per page.",
                required = false,
                example = "50"
            ),
            Parameter(
                name = "sort",
                `in` = ParameterIn.QUERY,
                description = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.",
                required = false,
                array = ArraySchema(
                    schema = Schema(
                        type = "string"
                    )
                )
            )
        ]
    )
    annotation class PageableParameter
}
