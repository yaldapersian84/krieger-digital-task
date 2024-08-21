package com.krieger.digital.document.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentDto {
    private String id;
    private String title;
    private String body;
    private List<AuthorDto> authors;
    private List<ReferenceDto> references;

}
