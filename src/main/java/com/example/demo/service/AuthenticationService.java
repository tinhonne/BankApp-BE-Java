package com.example.demo.service;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.IntrospectRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;


public interface AuthenticationService {

    AuthenticationResponse authentication(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
