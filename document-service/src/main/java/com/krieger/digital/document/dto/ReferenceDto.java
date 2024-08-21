package com.krieger.digital.document.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReferenceDto {
    private String authors;
    private String title;
    private int publicationYear;
    private String journalName;
    private int volume;
    private int issue;
    private String pages;
    private String doi;
}
