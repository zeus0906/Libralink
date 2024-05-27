package com.api.gesboo.service;

import com.api.gesboo.entite.Book;
import com.api.gesboo.entite.Collection;
import com.api.gesboo.entite.CollectionType;
import com.api.gesboo.repository.BookRepository;
import com.api.gesboo.repository.CollectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CollectionService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    public void addToCollectionByName(String bookIsbn, String collectionType, Book book) {
        try {
            Book bookToAdd = bookRepository.findByIsbn(bookIsbn);
            Collection collection = (Collection) collectionRepository.findByType(CollectionType.valueOf(collectionType));

            if (bookToAdd != null && collection != null) {
                bookToAdd.addCollection(collection);
                collection.addBook(bookToAdd);
                bookRepository.save(bookToAdd);
                collectionRepository.save(collection);
            } else {
                throw new EntityNotFoundException("Book or collection not found");
            }
        } catch (EntityNotFoundException e) {
            // Gérer l'exception EntityNotFoundException
            Log log = null;
            log.error("Error adding book to collection: " + e.getMessage());
            // Renvoyer des informations d'erreur plus détaillées
        }
    }

    public Collection removeBookFromCollection(String collectionId, String bookId) throws EntityNotFoundException {
        Collection collection = collectionRepository.findById(Integer.valueOf(collectionId)).orElseThrow(() -> new EntityNotFoundException("Collection not found"));
        Book book = bookRepository.findById(Long.valueOf(bookId)).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        removeBookFromCollection(collection, book); // Appel à la méthode interne
        return collection;
    }

    private void removeBookFromCollection(Collection collection, Book book) {
        if (collection.getBooks().contains(book)) {
            collection.getBooks().remove(book);
            book.getCollections().remove(collection);
            collectionRepository.save(collection);
            bookRepository.save(book);
        } else {
            throw new EntityNotFoundException("Book not found in collection");
        }
    }


    public Set<Book> getBooksInCollection(String collectionId) throws EntityNotFoundException {
        Collection collection = collectionRepository.findById(Integer.valueOf(collectionId)).orElseThrow(() -> new EntityNotFoundException("Collection not found"));
        return getBooksInCollection(collection); // Appel à la méthode interne
    }

    private Set<Book> getBooksInCollection(Collection collection) {
        return new HashSet<>(collection.getBooks());
    }

    public List<Collection> getCollections() {
        return collectionRepository.findAll();
    }

}
