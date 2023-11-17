package org.springframework.data.r2dbc.repository.support

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

object PageableExecutionUtils {
    fun <T> Flux<T>.paged(pageable: Pageable, totalSupplier: Mono<Long>): Mono<Page<T>> =
        this.collectList()
            .flatMap { ReactivePageableExecutionUtils.getPage(it, pageable, totalSupplier) }
}
