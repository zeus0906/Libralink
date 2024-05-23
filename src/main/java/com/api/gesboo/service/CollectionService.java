package com.api.gesboo.service;

import com.api.gesboo.entite.Book;
import com.api.gesboo.entite.BookCollection;
import com.api.gesboo.entite.CollectionType;
import com.api.gesboo.repository.BookRepository;
import com.api.gesboo.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    public Book addBookToCollection(String isbn, CollectionType collectionType) {
        // Recherchez le livre dans la base de données
        Book book = bookRepository.findByIsbn(isbn);

        // Recherchez la collection ou créez-la si elle n'existe pas
        Optional<BookCollection> collectionOptional = collectionRepository.findByType(collectionType);
        BookCollection collection;
        if (collectionOptional.isPresent()) {
            collection = collectionOptional.get();
        } else {
            collection = new BookCollection();
            collection.setType(collectionType);
        }

        // Vérifiez si le livre est déjà dans la collection pour éviter les doublons
        if (!collection.getBooks().contains(book)) {
            collection.getBooks().add(book);
            collectionRepository.save(collection);
        }

        return book;
    }

    public List<Book> getBooksByCollection(CollectionType collectionType) {
        Optional<BookCollection> collectionOptional = collectionRepository.findByType(collectionType);
        if (collectionOptional.isPresent()) {
            BookCollection collection = collectionOptional.get();
            return new ArrayList<>(collection.getBooks());
        }
        return Collections.emptyList();
    }

    public List<BookCollection> getAllCollections() {
        return collectionRepository.findAll();
    }

}
