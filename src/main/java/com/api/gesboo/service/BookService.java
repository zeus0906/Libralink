package com.api.gesboo.service;

import com.api.gesboo.entite.Book;
import com.api.gesboo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private static final String URI_API= "https://www.googleapis.com/books/v1/volumes?q=";

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks() {

    }
}
