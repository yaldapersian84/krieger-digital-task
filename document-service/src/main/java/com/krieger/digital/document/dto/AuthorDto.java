package com.krieger.digital.document.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDto {
    private String id;
    private String firstName;
    private String lastName;
}
