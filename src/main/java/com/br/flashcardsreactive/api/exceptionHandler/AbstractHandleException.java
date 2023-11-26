package com.br.flashcardsreactive.api.exceptionHandler;

import com.br.flashcardsreactive.api.controller.response.ProblemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;


@RequiredArgsConstructor
public abstract class AbstractHandleException <T extends  Exception>{

    private final ObjectMapper objectMapper;

    abstract Mono<Void> handleException(final ServerWebExchange exchange, final T exeption);

    protected Mono<Void> writeResponse(ServerWebExchange exchange, ProblemResponse problemResponse) {
        return exchange.getResponse()
                .writeWith(Mono.fromCallable(() -> new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(problemResponse))));
    }

    protected  void prepareExchange(final ServerWebExchange exchange, final HttpStatus httpStatus){
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
    }

    protected ProblemResponse buildError(final HttpStatus status, String errorDescription){
        return ProblemResponse
                .builder()
                .status(status.value())
                .errorDescription(errorDescription)
                .time(OffsetDateTime.now())
                .build();

    }
}
