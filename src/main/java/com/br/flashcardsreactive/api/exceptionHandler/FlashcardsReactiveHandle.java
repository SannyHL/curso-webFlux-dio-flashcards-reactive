package com.br.flashcardsreactive.api.exceptionHandler;

import com.br.flashcardsreactive.domain.exception.FlashcardsReactiveException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.br.flashcardsreactive.domain.exception.BaseErrorMessage.GENERIC_EXEPTION;


@Slf4j
public class FlashcardsReactiveHandle  extends  AbstractHandleException<FlashcardsReactiveException>{
    public FlashcardsReactiveHandle(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    Mono<Void> handleException(ServerWebExchange exchange, FlashcardsReactiveException exeption) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
                    return GENERIC_EXEPTION.getMessage();
                }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("==== FlashcardsReactiveException", exeption))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
