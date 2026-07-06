package com.example.demo.service;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.response.UserCreateResponse;

public interface UserService {
    UserCreateResponse createUser(UserCreateRequest request);
}
