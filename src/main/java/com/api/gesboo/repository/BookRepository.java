package com.api.gesboo.repository;

import com.api.gesboo.entite.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByIsbn(String isbn);

//    List<Book> findAll(List<Book> books);

}
