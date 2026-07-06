package com.example.demo.mapper;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.response.UserCreateResponse;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface UserMapping {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserCreateRequest request);

    UserCreateResponse toResponse(User user);

}
