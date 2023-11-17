package io.tblx.fms.filters

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
@Order(1)
class LoggerFilter : WebFilter {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(LoggerFilter::class.java)
        val HEADERS_NOT_LOGGED: Set<String> = setOf("authorization", "cookie", "set-cookie")
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request = exchange.request

        if (request.path.toString().contains("health") || request.path.toString().contains("prometheus")) {
            return chain.filter(exchange)
        }

        LOG.info("Incoming request from: ${request.uri}, path: ${request.method} ${request.path}, headers: ${getHeadersList(request)}")
        return chain.filter(exchange)
    }

    private fun getHeadersList(request: ServerHttpRequest): Map<String, String> {
        return request.headers.keys
            .filterNot(HEADERS_NOT_LOGGED::contains)
            .toList()
            .associateBy(
                { it },
                { request.headers.getOrEmpty(it).toList().joinToString(prefix = "[", separator = ",", postfix = "]") }
            )
    }
}
