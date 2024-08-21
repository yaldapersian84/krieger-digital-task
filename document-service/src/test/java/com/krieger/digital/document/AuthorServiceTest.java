package com.krieger.digital.document;

import com.krieger.digital.document.exc.NotFoundException;
import com.krieger.digital.document.model.Author;
import com.krieger.digital.document.model.Document;
import com.krieger.digital.document.repository.AuthorRepository;
import com.krieger.digital.document.repository.DocumentRepository;
import com.krieger.digital.document.request.author.AuthorCreateRequest;
import com.krieger.digital.document.request.author.AuthorUpdateRequest;
import com.krieger.digital.document.request.notification.SendNotificationRequest;
import com.krieger.digital.document.service.AuthorService;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private KafkaTemplate<String, SendNotificationRequest> kafkaTemplate;

    @Mock
    private NewTopic topic;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    public void init() {
        openMocks(this);
        TransactionSynchronizationManager.initSynchronization();
    }

    @AfterEach
    public void clear() {
        TransactionSynchronizationManager.clear();
    }

    @Test
    void testCreateAuthor() {

        AuthorCreateRequest request = new AuthorCreateRequest("Yalda", "Ghasemi");
        Author savedAuthor = new Author("Yalda", "Ghasemi");

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);
        Author result = authorService.createAuthor(request);

        assertNotNull(result);
        assertEquals("Yalda", result.getFirstName());
        assertEquals("Ghasemi", result.getLastName());
        verify(authorRepository, times(1)).save(any(Author.class));
        verify(kafkaTemplate, times(1)).send(eq(topic.name()), any(SendNotificationRequest.class));
    }

    @Test
    void testGetAuthor_AuthorExists() {

        Author author = new Author("Yalda", "Ghasemi");
        when(authorRepository.findById("1")).thenReturn(Optional.of(author));

        Author result = authorService.getAuthor("1");

        assertNotNull(result);
        assertEquals("Yalda", result.getFirstName());
        assertEquals("Ghasemi", result.getLastName());
    }

    @Test
    void testGetAuthor_AuthorNotFound() {

        when(authorRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> authorService.getAuthor("1"));
    }

    @Test
    void testGetAllAuthors() {
        List<Author> authors = Arrays.asList(
                new Author("Yalda", "Ghasemi"),
                new Author("Maria", "Keyyl")
        );
        when(authorRepository.findAll()).thenReturn(authors);
        List<Author> result = authorService.getAllAuthors();
        assertEquals(2, result.size());
        assertEquals("Yalda", result.get(0).getFirstName());
        assertEquals("Ghasemi", result.get(0).getLastName());
        assertEquals("Maria", result.get(1).getFirstName());
        assertEquals("Keyyl", result.get(1).getLastName());
    }

    @Test
    void testUpdateAuthor() {
        Author existingAuthor = new Author("Yalda", "Ghasemi");
        AuthorUpdateRequest updateRequest = new AuthorUpdateRequest("UpdatedYalda", "UpdatedGhasemi");
        Author updatedAuthor = Author.builder().firstName("UpdatedYalda").lastName("UpdatedGhasemi").build();

        when(authorRepository.findById("1")).thenReturn(Optional.of(existingAuthor));
        when(modelMapper.map(any(AuthorUpdateRequest.class), eq(Author.class))).thenReturn(updatedAuthor);
        when(authorRepository.save(existingAuthor)).thenReturn(updatedAuthor);

        Author updatedAuthorAct = authorService.updateAuthor("1", updateRequest);

        assertEquals("UpdatedYalda", updatedAuthorAct.getFirstName());
        assertEquals("UpdatedGhasemi", updatedAuthorAct.getLastName());
        verify(authorRepository, times(1)).save(existingAuthor);
    }

    @Test
    @Transactional
    void testDeleteAuthor_AuthorExists() {

        Author existingAuthor = new Author("Yalda", "Ghasemi");
        Document document = new Document("DocumentTitle", "DocumentBody", Set.of(existingAuthor), Set.of());
        List<Document> documents = List.of(document);

        when(authorRepository.findById("1")).thenReturn(Optional.of(existingAuthor));
        when(documentRepository.findByAuthors_Id("1")).thenReturn(documents);

        authorService.deleteAuthor("1");

        verify(documentRepository, times(1)).delete(document);
        verify(authorRepository, times(1)).delete(existingAuthor);
    }

    @Test
    void testDeleteAuthor_AuthorNotFound() {

        when(authorRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.deleteAuthor("1"));
    }
}

