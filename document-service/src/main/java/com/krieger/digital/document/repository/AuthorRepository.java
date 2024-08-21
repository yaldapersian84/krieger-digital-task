package com.krieger.digital.document.repository;

import com.krieger.digital.document.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> { }