package com.br.flashcardsreactive.api.exceptionHandler;


import com.br.flashcardsreactive.domain.exception.FlashcardsReactiveException;
import com.br.flashcardsreactive.domain.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;

@Component
@Order(-2)
@Slf4j
@AllArgsConstructor
public class ApiExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        return Mono.error(ex)
                .onErrorResume(MethodNotAllowedException.class, e ->
                        new MethodNotAllowedHandle(objectMapper).handleException(exchange, e))
                .onErrorResume(NotFoundException.class, e ->
                        new NotFoundHandle(objectMapper).handleException(exchange, e))
                .onErrorResume(ConstraintViolationException.class, e ->
                        new ConstraintViolationHandle(objectMapper).handleException(exchange, e))
                .onErrorResume(WebExchangeBindException.class, e ->
                        new WebExchangeBindHandle(objectMapper, messageSource).handleException(exchange, e))
                .onErrorResume(ResponseStatusException.class, e ->
                        new ResponseStatusHandle(objectMapper).handleException(exchange, e))
                .onErrorResume(FlashcardsReactiveException.class, e ->
                        new FlashcardsReactiveHandle(objectMapper).handleException(exchange, e))
                .onErrorResume(Exception.class, e ->  new GenericHandle(objectMapper).handleException(exchange, e))
                .onErrorResume(JsonProcessingException.class, e ->
                        new JsonProcessingHandle(objectMapper).handleException(exchange, e))
                .then();
    }



}
