package com.api.gesboo.controller;


import com.api.gesboo.entite.Book;
import com.api.gesboo.entite.Categorie;
import com.api.gesboo.enums.CategorieType;
import com.api.gesboo.service.CollectionService;
import com.api.gesboo.service.OpenBookService;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class BookController {

    @Autowired
    private OpenBookService openBookService;

    @Autowired
    private CollectionService collectionService;

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
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = openBookService.getAllBooks();
        return ResponseEntity.ok(books);
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

    // Endpoint pour afficher un livre en récupérant le livre en fonction de son ISBN
    @GetMapping("/livre/{isbn}")
    public ResponseEntity<Book> lireLivreByIsbn(@PathVariable String isbn) {
        Book book = openBookService.lireLivreByIsbn(isbn);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/in-collections")
    public List<Book> getBooksInCollections() {
        return openBookService.getBooksInCollections();
    }


    @PostMapping("/{isbn}/collection")
    public ResponseEntity<Book> addBookToCollection(@PathVariable String isbn, @RequestParam CategorieType categorieType) {
        Book book = collectionService.addBookToCollection(isbn, categorieType);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/collections/{collectionId}/books/{bookId}")
    public ResponseEntity<Categorie> removeFromCollection(@PathVariable String collectionId, @PathVariable String bookId) {
        try {
            Categorie categorie = collectionService.removeBookFromCollection(collectionId, bookId);
            return ResponseEntity.ok(categorie);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/collections/{collectionId}/books")
    public ResponseEntity<Set<Book>> getBooksInCollection(@PathVariable String collectionId) {
        try {
            Set<Book> books = collectionService.getBooksInCollection(collectionId);
            return ResponseEntity.ok(books);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/collections")
    public ResponseEntity<List<Categorie>> getCollections() {
        List<Categorie> categories = collectionService.getCollections();
        return ResponseEntity.ok(categories);
    }


}
