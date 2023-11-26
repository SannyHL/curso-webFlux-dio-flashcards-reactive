package com.br.flashcardsreactive.api.exceptionHandler;

import com.br.flashcardsreactive.api.controller.response.ErrorFieldResponse;
import com.br.flashcardsreactive.api.controller.response.ProblemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;

import static com.br.flashcardsreactive.domain.exception.BaseErrorMessage.GENERIC_BAD_REQUEST;
@Slf4j
public class ConstraintViolationHandle extends  AbstractHandleException<ConstraintViolationException>{

    public ConstraintViolationHandle(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    Mono<Void> handleException(ServerWebExchange exchange, ConstraintViolationException exeption) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, HttpStatus.BAD_REQUEST);
                    return GENERIC_BAD_REQUEST.getMessage();
                }).map(message -> buildError(HttpStatus.BAD_REQUEST, message))
                .flatMap(response -> buildParamErrorMessage(response, exeption))
                .doFirst(() -> log.error("==== ConstraintViolationException", exeption))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

    private  Mono<ProblemResponse> buildParamErrorMessage(final ProblemResponse response, final ConstraintViolationException exception){
        return Flux.fromIterable(exception.getConstraintViolations())
                .map(constraintViolation -> ErrorFieldResponse.builder()
                        .name(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().toString())
                        .message(constraintViolation.getMessage()).build())
                .collectList()
                .map(problemResponses -> response.toBuilder().fields(problemResponses).build());
    }
}
