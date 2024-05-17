package com.api.gesboo.repository;

import com.api.gesboo.entite.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
    List<Book> findByAuthorsContainingIgnoreCase(String author);
    List<Book> findByTitleContainingIgnoreCase(String title);

}
