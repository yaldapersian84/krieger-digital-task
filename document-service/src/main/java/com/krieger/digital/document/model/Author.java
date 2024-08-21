package com.krieger.digital.document.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "author")
public class Author extends BaseEntity {

    private String firstName;

    private String lastName;

}
