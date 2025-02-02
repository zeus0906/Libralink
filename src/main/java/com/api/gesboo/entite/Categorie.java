package com.api.gesboo.entite;

import com.api.gesboo.entite.Book.Book;
import com.api.gesboo.enums.CategorieType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Collection")

public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategorieType type;

    @ManyToMany
    @JoinTable(
            name = "collection_book",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @JsonBackReference
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
            book.getCategories().add(this); // Établir la relation inverse
        }
    }

    public void removeBook(Book book) {
        if (books.contains(book)) {
            books.remove(book);
            book.getCategories().remove(this); // Rompre la relation inverse
        }
    }

    public Set<Book> getBooks() {
        return new HashSet<>(books); // Renvoyer une copie
    }

}
