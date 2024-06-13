package com.api.gesboo.service;


import com.api.gesboo.entite.Book;
import com.api.gesboo.entite.Categorie;
import com.api.gesboo.enums.CategorieType;
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
    public Book addBookToCollection(String isbn, CategorieType categorieType) {
        // Recherchez le livre dans la base de données
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            return null; // Retournez null si le livre n'existe pas dans la base de données
        }

        // Recherchez la collection ou créez-la si elle n'existe pas
        Optional<Categorie> collectionOptional = collectionRepository.findByType(categorieType);
        Categorie categorie;
        if (collectionOptional.isPresent()) {
            categorie = collectionOptional.get();
        } else {
            categorie = new Categorie();
            categorie.setType(categorieType);
            collectionRepository.save(categorie); // Sauvegarder la nouvelle collection pour obtenir un ID
        }

        // Ajoutez le livre à la collection si ce n'est pas déjà fait
        if (!categorie.getBooks().contains(book)) {
            categorie.addBook(book); // Utiliser la méthode addBook pour gérer les relations bidirectionnelles

            // Essayez d'enregistrer la collection dans la base de données
            try {
                collectionRepository.save(categorie);
                System.out.println("Collection " + categorie.getType() + " enregistrée avec succès");
            } catch (Exception e) {
                System.out.println("Échec de l'enregistrement de la collection " + categorie.getType() + " : " + e.getMessage());
                throw e; // Relancez l'exception pour une gestion au niveau supérieur
            }
        }

        return book;
    }


    public Categorie removeBookFromCollection(String collectionId, String bookId) throws EntityNotFoundException {
        Categorie categorie = collectionRepository.findById(Integer.valueOf(collectionId)).orElseThrow(() -> new EntityNotFoundException("Collection not found"));
        Book book = bookRepository.findById(Long.valueOf(bookId)).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        removeBookFromCollection(categorie, book); // Appel à la méthode interne
        return categorie;
    }

    private void removeBookFromCollection(Categorie categorie, Book book) {
        if (categorie.getBooks().contains(book)) {
            categorie.getBooks().remove(book);
            book.getCategories().remove(categorie);
            collectionRepository.save(categorie);
            bookRepository.save(book);
        } else {
            throw new EntityNotFoundException("Book not found in collection");
        }
    }


    public Set<Book> getBooksInCollection(String collectionId) throws EntityNotFoundException {
        Categorie categorie = collectionRepository.findById(Integer.valueOf(collectionId)).orElseThrow(() -> new EntityNotFoundException("Collection not found"));
        return getBooksInCollection(categorie); // Appel à la méthode interne
    }

    private Set<Book> getBooksInCollection(Categorie categorie) {
        return new HashSet<>(categorie.getBooks());
    }

    public List<Categorie> getCollections() {
        return collectionRepository.findAll();
    }

}
