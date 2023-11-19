package com.br.flashcardsreactive.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record UserRequest(@JsonProperty("nome")
                          String nome,
                          @JsonProperty("email")
                          String email) {

    @Builder(toBuilder = true)
    public UserRequest{}
}
