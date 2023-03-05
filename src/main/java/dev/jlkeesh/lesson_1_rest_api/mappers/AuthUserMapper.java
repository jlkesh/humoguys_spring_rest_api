package dev.jlkeesh.lesson_1_rest_api.mappers;

import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthUser;
import dev.jlkeesh.lesson_1_rest_api.dto.auth.UserRegisterDTO;
import lombok.NonNull;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthUser fromCreateDto(@NonNull UserRegisterDTO dto);
}
