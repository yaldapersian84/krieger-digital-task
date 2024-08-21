package com.krieger.digital.document.repository;

import com.krieger.digital.document.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {
    List<Document> findByAuthors_Id(String authorId);
}