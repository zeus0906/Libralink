package com.api.gesboo.controller;

import com.api.gesboo.entite.Book;
import com.api.gesboo.service.OpenBookService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    private OpenBookService openBookService;

    @GetMapping("/books/{isbn}")
    public Book getBookByISBN(@PathVariable String isbn) {
        return openBookService.saveBookDetails(isbn);
    }


}
