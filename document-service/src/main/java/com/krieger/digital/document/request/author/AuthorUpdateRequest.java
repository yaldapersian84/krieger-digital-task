package com.krieger.digital.document.request.author;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AuthorUpdateRequest {
    private String firstName;
    private String lastName;
}
