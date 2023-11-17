package io.tblx.fms.services

import io.tblx.fms.models.Operator
import io.tblx.fms.repositories.OperatorRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface OperatorService {
    fun getPaginated(pageable: Pageable): Flux<Operator>
    fun getById(operatorId: Long): Mono<Operator>
    fun getTotal(): Mono<Long>
}

@Service
class OperatorServiceImpl(private val operatorRepository: OperatorRepository) : OperatorService {
    override fun getPaginated(pageable: Pageable): Flux<Operator> =
        operatorRepository.findAllBy(pageable)

    override fun getById(operatorId: Long): Mono<Operator> =
        operatorRepository.findById(operatorId)

    override fun getTotal(): Mono<Long> =
        operatorRepository.count()
}
