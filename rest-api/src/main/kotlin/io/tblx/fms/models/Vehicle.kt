package io.tblx.fms.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table(name = "vehicles", schema = "fms")
data class Vehicle(
    @Id
    @Column("vehicle_id")
    val id: Long,

    @Column("fleet_id")
    val fleetId: Long,

    @Column("license_plate")
    val licensePlate: String,

    @Column("status")
    val status: VehicleStatus,

    @Column("last_service_date")
    val lastServiceDate: LocalDate
)

enum class VehicleStatus {
    Operational, InMaintenance, Retired
}
