package com.api.gesboo.service;

import com.api.gesboo.entite.Book.Book;
import com.api.gesboo.entite.Book.Collection;
import com.api.gesboo.entite.Book.CollectionType;
import com.api.gesboo.repository.BookRepository;
import com.api.gesboo.repository.CollectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CollectionService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Transactional
    public Book addBookToCollection(String isbn, CollectionType collectionType) {
        // Recherchez le livre dans la base de données
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            return null; // Retournez null si le livre n'existe pas dans la base de données
        }

        // Recherchez la collection ou créez-la si elle n'existe pas
        Optional<Collection> collectionOptional = collectionRepository.findByType(collectionType);
        Collection collection;
        if (collectionOptional.isPresent()) {
            collection = collectionOptional.get();
        } else {
            collection = new Collection();
            collection.setType(collectionType);
            collectionRepository.save(collection); // Sauvegarder la nouvelle collection pour obtenir un ID
        }

        // Ajoutez le livre à la collection si ce n'est pas déjà fait
        if (!collection.getBooks().contains(book)) {
            collection.addBook(book); // Utiliser la méthode addBook pour gérer les relations bidirectionnelles

            // Essayez d'enregistrer la collection dans la base de données
            try {
                collectionRepository.save(collection);
                System.out.println("Collection " + collection.getType() + " enregistrée avec succès");
            } catch (Exception e) {
                System.out.println("Échec de l'enregistrement de la collection " + collection.getType() + " : " + e.getMessage());
                throw e; // Relancez l'exception pour une gestion au niveau supérieur
            }
        }

        return book;
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
