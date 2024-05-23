package com.api.gesboo.entite;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "BookCollection")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class BookCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private CollectionType type;


    @ManyToMany
    @JoinTable(
            name = "collection_book",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    @JsonManagedReference
    private Set<Book> books = new HashSet<>();

}
