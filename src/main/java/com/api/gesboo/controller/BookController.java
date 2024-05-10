package com.api.gesboo.controller;

import com.api.gesboo.entite.Book;
import com.api.gesboo.service.GoogleBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    private GoogleBookService googleBookService;

    @GetMapping("/books/{isbn}")
    public Book getBookByISBN(@PathVariable String isbn) {
        return googleBookService.searchBookByISBN(isbn);
    }

    @PostMapping("/books")
    public void saveBook(@RequestBody String isbn) {
        googleBookService.saveBookFromGoogleBook(isbn);
    }
}
