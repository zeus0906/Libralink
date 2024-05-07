package com.api.gesboo.controller;

import com.api.gesboo.entite.VolumeInfo;
import com.api.gesboo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books/{isbn}")
    public VolumeInfo getBookByISBN(@PathVariable String isbn) {
        return bookService.searchBookByISBN(isbn);
    }
}
