package com.br.flashcardsreactive.api.exceptionHandler;

import com.br.flashcardsreactive.api.controller.response.ErrorFieldResponse;
import com.br.flashcardsreactive.api.controller.response.ProblemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.br.flashcardsreactive.domain.exception.BaseErrorMessage.GENERIC_BAD_REQUEST;
@Slf4j

public class WebExchangeBindHandle extends  AbstractHandleException<WebExchangeBindException>{

    private final MessageSource messageSource;
    public WebExchangeBindHandle(ObjectMapper objectMapper, MessageSource messageSource) {
        super(objectMapper);
        this.messageSource = messageSource;
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, WebExchangeBindException exception) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.BAD_REQUEST);
                    return GENERIC_BAD_REQUEST.getMessage();
                }).map(message -> buildError(HttpStatus.BAD_REQUEST, message))
                .flatMap(response -> buildParamErrorMessage(response, exception))
                .doFirst(() -> log.error("==== WebExchangeBindException", exception))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

    private  Mono<ProblemResponse> buildParamErrorMessage(final ProblemResponse response, final WebExchangeBindException exception){
        return Flux.fromIterable(exception.getAllErrors())
                .map(objectError -> ErrorFieldResponse.builder()
                        .name(objectError instanceof FieldError fieldError ? fieldError.getField(): objectError.getObjectName())
                        .message(messageSource.getMessage(objectError, LocaleContextHolder.getLocale()))
                        .build())
                .collectList()
                .map(problemResponses -> response.toBuilder().fields(problemResponses).build());
    }
}
