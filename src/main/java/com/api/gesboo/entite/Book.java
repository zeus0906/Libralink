package com.api.gesboo.entite;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long isbn;
    private String title;
    private String author;
    private String publisher;
    private String sommaire;
    private String genre;
    private String langue;
    private int pages;

}
