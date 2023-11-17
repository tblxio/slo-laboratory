package io.tblx.fms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableConfigurationProperties
@EnableWebFlux
class FmsBackend

fun main(args: Array<String>) {
    runApplication<FmsBackend>(*args)
}
