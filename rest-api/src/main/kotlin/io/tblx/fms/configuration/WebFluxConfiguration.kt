package io.tblx.fms.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer

/**
 * Web flux configuration allowing to use the object mapper defined in [[ObjectMapperConfiguration]]
 */
@Configuration
class WebFluxConfiguration(
    private val objectMapper: ObjectMapper
) : WebFluxConfigurer {
    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.defaultCodecs()
            .apply {
                jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper))
                jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
            }
    }

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        super.configureArgumentResolvers(configurer)
        configurer.addCustomResolver(ReactivePageableHandlerMethodArgumentResolver())
    }
}
