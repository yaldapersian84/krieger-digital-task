package com.krieger.digital.document.model;

import lombok.*;

import javax.persistence.Entity;

@Entity(name = "reference")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reference extends BaseEntity {
    private String authors;
    private String title;
    private int publicationYear;
    private String journalName;
    private int volume;
    private int issue;
    private String pages;
    private String doi;
}
