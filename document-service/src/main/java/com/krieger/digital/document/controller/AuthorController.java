package com.krieger.digital.document.controller;

import com.krieger.digital.document.dto.AuthorDto;
import com.krieger.digital.document.request.author.AuthorCreateRequest;
import com.krieger.digital.document.request.author.AuthorUpdateRequest;
import com.krieger.digital.document.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/document-service/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final ModelMapper modelMapper;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(authorService.createAuthor(request), AuthorDto.class));

    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(authorService.getAuthor(id), AuthorDto.class));
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors().stream()
                .map(author -> modelMapper.map(author, AuthorDto.class)).toList());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable String id, @Valid @RequestBody AuthorUpdateRequest request) {
        return ResponseEntity.ok(modelMapper.map(authorService.updateAuthor(id, request), AuthorDto.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }
}