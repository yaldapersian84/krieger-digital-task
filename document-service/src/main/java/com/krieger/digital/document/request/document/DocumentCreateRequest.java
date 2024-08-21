package com.krieger.digital.document.request.document;

import com.krieger.digital.document.request.reference.RefCreateRequest;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class DocumentCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Body is required")
    private String body;

    @Valid
    @NotEmpty(message = "Author IDs should not be empty")
    private Set<String> authors;

    @Valid
    @NotEmpty(message = "References should not be empty")
    private Set<RefCreateRequest> references;

}
