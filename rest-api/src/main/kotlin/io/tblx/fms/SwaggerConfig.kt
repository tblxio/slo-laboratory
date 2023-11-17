package io.tblx.fms

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("swagger-ui")
class SwaggerConfig {
    /**
     * Definition / configuration for swagger.
     *
     * @return docket that describes the swagger configuration
     */
    @Bean
    fun api(): OpenAPI {
        return OpenAPI().info(apiInfo())
    }

    private fun apiInfo(): Info {
        return Info()
            .title("FMS Backend")
            .description("Implementation of a backend to access FMS Data")
    }
}
