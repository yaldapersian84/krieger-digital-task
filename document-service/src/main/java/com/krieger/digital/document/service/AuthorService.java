package com.krieger.digital.document.service;

import com.krieger.digital.document.exc.NotFoundException;
import com.krieger.digital.document.model.Author;
import com.krieger.digital.document.model.Document;
import com.krieger.digital.document.repository.AuthorRepository;
import com.krieger.digital.document.repository.DocumentRepository;
import com.krieger.digital.document.request.author.AuthorCreateRequest;
import com.krieger.digital.document.request.author.AuthorUpdateRequest;
import com.krieger.digital.document.request.notification.SendNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final DocumentRepository documentRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, SendNotificationRequest> kafkaTemplate;
    private final NewTopic topic;

    public Author createAuthor(AuthorCreateRequest request) {
        Author author = authorRepository.save(
                Author.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .build());
        sendNotification(author.getId(), "The author was created with  id: " + author.getId());
        return author;

    }

    public Author getAuthor(String id) {
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author not found with id: " + id));
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author updateAuthor(String authorId, AuthorUpdateRequest request) {

        Author toUpdate = findAuthorById(authorId);
        modelMapper.map(request, toUpdate);
        return authorRepository.save(toUpdate);

    }

    /**
     * Deletes an author and all associated documents from the database. After each document is successfully deleted,
     * a notification is sent with the document's ID.
     *
     * @param authorId the ID of the author to be deleted
     * @throws NotFoundException if the author with the given ID is not found
     */
    @Transactional
    public void deleteAuthor(String authorId) {
        List<Document> documents = documentRepository.findByAuthors_Id(authorId);

        documents.forEach(document -> {
            documentRepository.delete(document);

            // Register a callback to send the notification after the transaction commits successfully
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    sendNotification(document.getId(), "The document was deleted with  id: " + document.getId());
                }
            });
        });

        authorRepository.findById(authorId).ifPresentOrElse(authorRepository::delete, () -> {
            throw new NotFoundException("Author not found with id: " + authorId);
        });
    }

    protected Author findAuthorById(String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found with id: " + id));
    }

    protected void sendNotification(String id, String message) {
        SendNotificationRequest notification = SendNotificationRequest.builder()
                .message(message)
                .id(id).build();

        kafkaTemplate.send(topic.name(), notification);
    }
}
