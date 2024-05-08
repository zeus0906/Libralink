package com.api.gesboo.controller;

import com.api.gesboo.GoogleBookApi.VolumeInfo;
import com.api.gesboo.service.GoogleBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    private GoogleBookService googleBookService;

    @GetMapping("/books/{isbn}")
    public VolumeInfo getBookByISBN(@PathVariable String isbn) {
        return googleBookService.searchBookByISBN(isbn);
    }
}
