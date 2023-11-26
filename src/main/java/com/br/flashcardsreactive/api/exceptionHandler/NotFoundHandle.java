package com.br.flashcardsreactive.api.exceptionHandler;

import com.br.flashcardsreactive.domain.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class NotFoundHandle extends  AbstractHandleException<NotFoundException>{

    public NotFoundHandle(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, NotFoundException exception) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.NOT_FOUND);
                    return exception.getMessage();
                }).map(message -> buildError(HttpStatus.NOT_FOUND, message))
                .doFirst(() -> log.error("==== NotFoundException", exception))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
