package io.tblx.fms.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table(name = "drivers", schema = "fms")
data class Driver(
    @Id
    @Column("driver_id")
    val id: Long,

    @Column("operator_id")
    val operatorId: Long,

    @Column("first_name")
    val firstName: String,

    @Column("last_name")
    val lastName: String,

    @Column("license_number")
    val licenseNumber: String,

    @Column("date_of_birth")
    val dateOfBirth: LocalDate,

    @Column("hire_date")
    val hireDate: LocalDate
)
