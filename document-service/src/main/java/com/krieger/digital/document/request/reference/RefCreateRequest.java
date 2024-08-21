package com.krieger.digital.document.request.reference;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefCreateRequest {


    @NotBlank(message = "authors is required")
    private String authors;
    @NotBlank(message = "Title is required")
    private String title;
    private int publicationYear;
    private String journalName;
    private int volume;
    private int issue;
    private String pages;
    private String doi;
}

