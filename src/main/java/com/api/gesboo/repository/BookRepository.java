package com.api.gesboo.repository;


import com.api.gesboo.entite.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
    List<Book> findByAuthorsContainingIgnoreCase(String author);
    List<Book> findByTitleContainingIgnoreCase(String title);

}
