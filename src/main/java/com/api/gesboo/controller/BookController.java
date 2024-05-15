package com.api.gesboo.controller;

import com.api.gesboo.entite.Book;
import com.api.gesboo.service.OpenBookService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private OpenBookService openBookService;

    @GetMapping("/books/{isbn}")
    public JsonObject getBookByISBN(@PathVariable String isbn) {
        return openBookService.getBookByISBN(isbn);
    }

    @PostMapping("books/{isbn}")
    public Book addBook(@PathVariable String isbn) {
        return openBookService.saveBookDetails(isbn);
    }

//    @GetMapping("/listBooks")
//    public List<Book> listBooks() {
//        return openBookService.listeBook();
//    }
}
