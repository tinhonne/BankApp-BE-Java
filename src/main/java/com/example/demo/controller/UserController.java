package com.example.demo.controller;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.UserCreateResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserCreateResponse>> createUser(@Valid @RequestBody UserCreateRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(userService.createUser(request)));
    }

}
