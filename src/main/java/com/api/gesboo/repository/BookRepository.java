package com.api.gesboo.repository;

import com.api.gesboo.entite.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
