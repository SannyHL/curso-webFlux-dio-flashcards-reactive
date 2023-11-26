package com.br.flashcardsreactive.api.exceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.br.flashcardsreactive.domain.exception.BaseErrorMessage.GENERIC_METHOD_NOT_ALLOW;


@Slf4j
public class JsonProcessingHandle extends  AbstractHandleException<JsonProcessingException>{
    public JsonProcessingHandle(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, JsonProcessingException exeption) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.METHOD_NOT_ALLOWED);
                    return GENERIC_METHOD_NOT_ALLOW.getMessage();
                }).map(message -> buildError(HttpStatus.METHOD_NOT_ALLOWED, message))
                .doFirst(() -> log.error("==== JsonProcessingException: Failed to map exception for the request {}",
                        exchange.getRequest().getPath().value(), exeption))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
