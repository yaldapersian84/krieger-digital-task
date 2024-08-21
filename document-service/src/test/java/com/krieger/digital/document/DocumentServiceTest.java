package com.krieger.digital.document;

import com.krieger.digital.document.exc.NotFoundException;
import com.krieger.digital.document.model.Author;
import com.krieger.digital.document.model.Document;
import com.krieger.digital.document.model.Reference;
import com.krieger.digital.document.repository.AuthorRepository;
import com.krieger.digital.document.repository.DocumentRepository;
import com.krieger.digital.document.request.document.DocumentCreateRequest;
import com.krieger.digital.document.request.document.DocumentUpdateRequest;
import com.krieger.digital.document.request.reference.RefCreateRequest;
import com.krieger.digital.document.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DocumentService documentService;

    private Document document;
    private Author author;
    private RefCreateRequest refCreateRequest;
    private Reference reference;

    @BeforeEach
    void setUp() {
        author = new Author("Yalda", "AuthorName");
        reference = new Reference();
        reference.setTitle("ReferenceTitle");
        refCreateRequest = new RefCreateRequest();
        refCreateRequest.setTitle("ReferenceTitle");
        refCreateRequest.setAuthors("ReferenceAuthors");

        document = Document.builder()
                .title("DocumentTitle")
                .body("DocumentBody")
                .authors(Set.of(author))
                .references(Set.of(reference))
                .build();
    }

    @Test
    void testCreateDocument_Success() {

        DocumentCreateRequest request = new DocumentCreateRequest();
        request.setTitle("DocumentTitle");
        request.setBody("DocumentBody");
        request.setAuthors(Set.of("1"));
        request.setReferences(Set.of(refCreateRequest));

        when(authorRepository.findById("1")).thenReturn(Optional.of(author));
        when(modelMapper.map(refCreateRequest, Reference.class)).thenReturn(reference);
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        Document result = documentService.createDocument(request);

        assertNotNull(result);
        assertEquals("DocumentTitle", result.getTitle());
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    void testCreateDocument_InvalidAuthor_ThrowsNotFoundException() {
        DocumentCreateRequest request = new DocumentCreateRequest();
        request.setTitle("DocumentTitle");
        request.setBody("DocumentBody");
        request.setAuthors(Set.of("invalid-id"));

        when(authorRepository.findById("invalid-id")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> documentService.createDocument(request));
    }


    @Test
    void testUpdateDocument_DocumentNotFound_ThrowsNotFoundException() {
        DocumentUpdateRequest request = new DocumentUpdateRequest();
        request.setTitle("UpdatedTitle");

        when(documentRepository.findById("invalid-id")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> documentService.updateDocument("invalid-id", request));
    }

    @Test
    void testUpdateDocument_InvalidAuthor_ThrowsNotFoundException() {
        DocumentUpdateRequest request = new DocumentUpdateRequest();
        request.setTitle("UpdatedTitle");
        request.setAuthors(Set.of("invalid-id"));

        when(documentRepository.findById("1")).thenReturn(Optional.of(document));
        when(authorRepository.findById("invalid-id")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> documentService.updateDocument("1", request));
    }

    @Test
    void testGetDocument_Success() {
        when(documentRepository.findById("1")).thenReturn(Optional.of(document));

        Document result = documentService.getDocument("1");

        assertNotNull(result);
        assertEquals("DocumentTitle", result.getTitle());
    }

    @Test
    void testGetDocument_NotFound_ThrowsNotFoundException() {
        when(documentRepository.findById("invalid-id")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> documentService.getDocument("invalid-id"));
    }

    @Test
    void testGetAllDocuments_Success() {
        when(documentRepository.findAll()).thenReturn(List.of(document));
        List<Document> result = documentService.getAllDocuments();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteDocument_Success() {
        when(documentRepository.findById("1")).thenReturn(Optional.of(document));
        documentService.deleteDocument("1");
        verify(documentRepository, times(1)).delete(document);
    }

    @Test
    void testDeleteDocument_NotFound_ThrowsNotFoundException() {
        when(documentRepository.findById("invalid-id")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> documentService.deleteDocument("invalid-id"));
    }

}
