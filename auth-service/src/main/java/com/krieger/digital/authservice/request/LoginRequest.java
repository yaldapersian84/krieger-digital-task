package com.krieger.digital.authservice.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
}
