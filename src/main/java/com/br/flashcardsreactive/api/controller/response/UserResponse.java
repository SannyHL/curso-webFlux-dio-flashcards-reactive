package com.br.flashcardsreactive.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record UserResponse(@JsonProperty("id")
                           String id,
                           @JsonProperty("nome")
                           String nome,
                           @JsonProperty("email")
                           String email) {

    @Builder(toBuilder = true)
    public UserResponse{}
}
