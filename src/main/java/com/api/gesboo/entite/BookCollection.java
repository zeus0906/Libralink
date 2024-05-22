package com.api.gesboo.entite;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Table(name = "BookCollection")
@ToString
@Entity
public class BookCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private CollectionType type;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            name = "collection_book",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    @JsonManagedReference // Indique que c'est la propriété parente à sérialiser
    private Set<Book> books;

    // Constructeur
    public BookCollection() {
        this.books = new HashSet<>();
    }

}
