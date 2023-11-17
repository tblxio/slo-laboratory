package io.tblx.fms.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "operators", schema = "fms")
data class Operator(
    @Id
    @Column("operator_id")
    val id: Long,

    @Column("company_name")
    val companyName: String,

    @Column("contact_name")
    val contactName: String? = null,

    @Column("contact_email")
    val contactEmail: String? = null,

    @Column("phone_number")
    val phoneNumber: String? = null,

    @Column("address")
    val address: String? = null
)
