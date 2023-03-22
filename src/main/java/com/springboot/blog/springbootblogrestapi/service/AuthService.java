package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.Payloads.LoginDto;
import com.springboot.blog.springbootblogrestapi.Payloads.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
