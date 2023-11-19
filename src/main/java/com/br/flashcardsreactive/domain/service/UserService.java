package com.br.flashcardsreactive.domain.service;

import com.br.flashcardsreactive.domain.document.UserDocument;
import com.br.flashcardsreactive.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserDocument>save(final UserDocument document){
        return userRepository.save(document)
                .doFirst(() -> log.info("=== salvar documento {}", document));
    }
}
