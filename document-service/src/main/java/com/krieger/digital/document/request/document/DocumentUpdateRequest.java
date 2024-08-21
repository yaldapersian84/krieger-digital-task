package com.krieger.digital.document.request.document;

import com.krieger.digital.document.request.reference.RefCreateRequest;
import lombok.Data;

import java.util.Set;

@Data
public class DocumentUpdateRequest {
    private String title;
    private String body;
    private Set<String> authors;
    private Set<RefCreateRequest> references;
}