package com.krieger.digital.document.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "document")
public class Document extends BaseEntity {

    private String title;
    private String body;


    @ManyToMany
    @JoinTable(
            name = "document_authors",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reference> references;

}
