package com.krieger.digital.document.controller;


import com.krieger.digital.document.dto.DocumentDto;
import com.krieger.digital.document.request.document.DocumentCreateRequest;
import com.krieger.digital.document.request.document.DocumentUpdateRequest;
import com.krieger.digital.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/document-service/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DocumentDto> createDocument(@Valid @RequestBody DocumentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(documentService.createDocument(request), DocumentDto.class));

    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getDocumentById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(documentService.getDocument(id), DocumentDto.class));
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments().stream()
                .map(Document -> modelMapper.map(Document, DocumentDto.class)).toList());

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable String id ,@Valid @RequestBody DocumentUpdateRequest request) {
        return ResponseEntity.ok(modelMapper.map(documentService.updateDocument(id, request), DocumentDto.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok().build();
    }
}