package com.br.flashcardsreactive.api.controller;

import com.br.flashcardsreactive.api.controller.request.UserRequest;
import com.br.flashcardsreactive.api.controller.response.UserResponse;
import com.br.flashcardsreactive.api.mapper.UserMapper;
import com.br.flashcardsreactive.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> save(@Validated @RequestBody final UserRequest request){
        return userService.save(userMapper.toDocument(request))
                .doFirst(() -> log.info("=== User Salvo no banco {}", request))
                .map(userMapper::toResponse);
    }
}
