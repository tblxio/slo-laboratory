package io.tblx.fms.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Global configuration of the object mapper used in the application.
 * In particular, this object mapper handles java.time.* and serializes them as ISO Strings instead of timestamps.
 */
@Configuration
class ObjectMapperConfiguration {
    @Bean
    fun objectMapper(): ObjectMapper =
        JsonMapper.builder()
            .addModules(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build()
}
