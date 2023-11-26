package com.br.flashcardsreactive.api.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.br.flashcardsreactive.domain.exception.BaseErrorMessage.GENERIC_METHOD_NOT_ALLOW;

@Slf4j
public class MethodNotAllowedHandle extends AbstractHandleException<MethodNotAllowedException>{
    public MethodNotAllowedHandle(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, MethodNotAllowedException exception) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.METHOD_NOT_ALLOWED);
                    return GENERIC_METHOD_NOT_ALLOW.params(exchange.getRequest().getMethod().name()).getMessage();
                }).map(message -> buildError(HttpStatus.METHOD_NOT_ALLOWED, message))
                .doFirst(() -> log.error("==== MethodNotAllowedException: Method [{}] is not allower at [{}]",
                        exchange.getRequest().getMethod(), exchange.getRequest().getPath().value(), exception))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
