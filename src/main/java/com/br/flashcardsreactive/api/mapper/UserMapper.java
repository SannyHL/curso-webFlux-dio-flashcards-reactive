package com.br.flashcardsreactive.api.mapper;

import com.br.flashcardsreactive.api.controller.request.UserRequest;
import com.br.flashcardsreactive.api.controller.response.UserResponse;
import com.br.flashcardsreactive.domain.document.UserDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAlteracao", ignore = true)
    UserDocument toDocument(final UserRequest request);

    UserResponse toResponse(final UserDocument document);
}
