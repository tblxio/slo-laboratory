package io.tblx.fms.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(schema = "fms", name = "fleets")
data class Fleet(
    @Id
    @Column("fleet_id")
    val id: Long,

    @Column("operator_id")
    val operatorId: Int,

    @Column("fleet_name")
    val fleetName: String,

    @Column("description")
    val description: String? = null
)
