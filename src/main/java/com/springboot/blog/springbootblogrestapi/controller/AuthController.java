package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.Payloads.JWTAuthResponse;
import com.springboot.blog.springbootblogrestapi.Payloads.LoginDto;
import com.springboot.blog.springbootblogrestapi.Payloads.RegisterDto;
import com.springboot.blog.springbootblogrestapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "CRUD REST APIs for Auth Resource"
)
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(value = {"/login","signin"})
    @Operation(
            summary = "Login REST API",
            description = "This api is used to login using email or username"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping(value = {"/register","/signup"})
    @Operation(
            summary = "Register REST API",
            description = "This API is used to register new user"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus 201 CREATED"
    )
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
