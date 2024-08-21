package com.krieger.digital.authservice.dto;

import com.krieger.digital.authservice.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private Role role;
}
