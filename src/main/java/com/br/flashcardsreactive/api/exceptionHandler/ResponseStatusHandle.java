package com.br.flashcardsreactive.api.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.br.flashcardsreactive.domain.exception.BaseErrorMessage.GENERIC_NOT_FOUND;

@Slf4j
public class ResponseStatusHandle  extends  AbstractHandleException<ResponseStatusException>{

    public ResponseStatusHandle(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, ResponseStatusException exception) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.NOT_FOUND);
                    return GENERIC_NOT_FOUND.getMessage();
                }).map(message -> buildError(HttpStatus.NOT_FOUND, message))
                .doFirst(() -> log.error("==== ResponseStatusException", exception))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
