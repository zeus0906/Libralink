package com.api.gesboo.controller;

import com.api.gesboo.entite.Book;
import com.api.gesboo.entite.BookCollection;
import com.api.gesboo.entite.CollectionType;
import com.api.gesboo.service.OpenBookService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private OpenBookService openBookService;

    // Endpoint pour afficher un livre de OpenLibrary en fonction de l'ISBN
    @GetMapping("/{isbn}")
    public JsonObject getBookByISBN(@PathVariable String isbn) {
        return openBookService.getBookByISBN(isbn);
    }

    // Endpoint pour sauvegarder un livre dans la BD en récuperant dans OpenLibrary en fonction de l'ISBN
    @PostMapping("books/{isbn}")
    public Book addBook(@PathVariable String isbn) {
        return openBookService.saveBookDetails(isbn);
    }

    // Endpoint pour afficher la liste des livres
    @GetMapping("/listBooks")
    public List<Book> getAllBooks() {
        return openBookService.getAllBooks();
    }

    // Endpoint pour rechercher des livres par ISBN
    @GetMapping("/search/isbn")
    public Book searchBookByISBN(@RequestParam String isbn) {
        return openBookService.findBookByISBN(isbn);
    }

    // Endpoint pour rechercher des livres par titre
    @GetMapping("/search/title")
    public List<Book> searchBooksByTitle(@RequestParam String title) {
        return openBookService.findBookByTitle(title);
    }

    // Endpoint pour rechercher des livres par auteur
    @GetMapping("/search/author")
    public List<Book> searchBooksByAuthor(@RequestParam String author) {
        return openBookService.findBookByAuthor(author);
    }

    // Nouveau endpoint pour rechercher des livres par différents critères
    @GetMapping("/search")
    public Book searchBook(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {
        return openBookService.searchBook(isbn, title, author);
    }

    // Afficher un livre grace a son ISBN
    @GetMapping(value = "Afichage/{Isbn}")
    public ResponseEntity<JsonObject> lireBookByISBN(@PathVariable String isbn) {
        JsonObject book = openBookService.getBookByISBN(isbn);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/{isbn}/collections/{collectionType}")
//    public ResponseEntity<?> addBookToCollection(
//            @PathVariable String isbn,
//            @PathVariable CollectionType collectionType) {
//        Book book = openBookService.addBookToCollection(isbn, collectionType);
//        if (book != null) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/collections")
//    public List<BookCollection> getAllCollections() {
//        return openBookService.getAllCollections();
//    }

}
