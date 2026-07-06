package com.example.demo.service;

import com.example.demo.dto.request.AuthenticationRequest;


public interface AuthenticationService {

    boolean authentication(AuthenticationRequest request);
}
