package com.krieger.digital.document.service;

import com.krieger.digital.document.exc.NotFoundException;
import com.krieger.digital.document.model.Author;
import com.krieger.digital.document.model.Document;
import com.krieger.digital.document.model.Reference;
import com.krieger.digital.document.repository.AuthorRepository;
import com.krieger.digital.document.repository.DocumentRepository;
import com.krieger.digital.document.request.document.DocumentCreateRequest;
import com.krieger.digital.document.request.document.DocumentUpdateRequest;
import com.krieger.digital.document.request.reference.RefCreateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new Document after validating the provided author IDs.
     *
     * @param request The {@link DocumentCreateRequest} containing the document details.
     * @return The saved {@link Document} entity.
     * @throws NotFoundException If any of the provided author IDs are invalid.
     */
    public Document createDocument(DocumentCreateRequest request) {
        // Validate authors and map references
        Set<Author> validAuthors = validateAndFetchAuthors(request.getAuthors());
        Set<Reference> references = mapReferences(request.getReferences());

        Document document = Document.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .authors(validAuthors)
                .references(references)
                .build();

        return documentRepository.save(document);
    }

    /**
     * Updates an existing Document after validating the provided author IDs.
     *
     * @param request The {@link DocumentUpdateRequest} containing the updated document details.
     * @return The updated {@link Document} entity.
     * @throws NotFoundException If the document is not found or any of the provided author IDs are invalid.
     */
    public Document updateDocument(String id, DocumentUpdateRequest request) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Document not found with ID: " + id));

        if (request.getAuthors() != null) {
            Set<Author> validAuthors = validateAndFetchAuthors(request.getAuthors());
            document.setAuthors(validAuthors);
        }

        // Update references if they are provided
        if (request.getReferences() != null) {
            document.getReferences().clear(); // Clear the existing collection
            Set<Reference> references = mapReferences(request.getReferences());
            document.getReferences().addAll(references); // Add the new references
        }

        document.setTitle(request.getTitle());
        document.setBody(request.getBody());


        return documentRepository.save(document);
    }

    /**
     * Validates the provided author IDs and fetches the corresponding {@link Author} entities.
     *
     * @param authorIds The set of author IDs to validate.
     * @return A set of valid {@link Author} entities.
     * @throws NotFoundException If any of the provided author IDs are invalid.
     */
    private Set<Author> validateAndFetchAuthors(Set<String> authorIds) {
        Set<Author> validAuthors = new HashSet<>();
        for (String authorId : authorIds) {
            Optional<Author> authorOptional = authorRepository.findById(authorId);

            if (authorOptional.isPresent()) {
                validAuthors.add(authorOptional.get());
            } else {
                throw new NotFoundException("Invalid author ID: " + authorId);
            }
        }
        return validAuthors;
    }

    /**
     * Maps a set of {@link RefCreateRequest} to a set of {@link Reference} entities.
     *
     * @param refCreateRequests The set of {@link RefCreateRequest} to map.
     * @return A set of mapped {@link Reference} entities.
     */
    private Set<Reference> mapReferences(Set<RefCreateRequest> refCreateRequests) {
        return refCreateRequests.stream()
                .map(referenceDto -> modelMapper.map(referenceDto, Reference.class))
                .collect(Collectors.toSet());
    }

    public Document getDocument(String id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Document not found with id: " + id));
    }


    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public void deleteDocument(String id) {
        documentRepository.findById(id)
                .ifPresentOrElse(documentRepository::delete,
                        () -> {
                            throw new NotFoundException("Document not found with id: " + id);
                        });
    }


}