package com.krieger.digital.userservice.dto;

import com.krieger.digital.userservice.enums.Role;
import lombok.Data;

@Data
public class AuthUserDto {
    private String id;
    private String username;
    private String password;
    private Role role;
}