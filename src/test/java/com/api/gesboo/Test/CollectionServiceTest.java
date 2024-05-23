package com.api.gesboo.Test;



import com.api.gesboo.entite.Book;
import com.api.gesboo.entite.BookCollection;
import com.api.gesboo.entite.CollectionType;
import com.api.gesboo.repository.BookRepository;
import com.api.gesboo.repository.CollectionRepository;
import com.api.gesboo.service.CollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class CollectionServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CollectionRepository collectionRepository;

    @InjectMocks
    private CollectionService collectionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBookToCollection_BookExists_CollectionExists() {
        String isbn = "9780975568606";
        CollectionType collectionType = CollectionType.FAVORIS;

        Book book = new Book();
        book.setIsbn(isbn);

        BookCollection collection = new BookCollection();
        collection.setType(collectionType);
        collection.setBooks(new HashSet<>());

        when(bookRepository.findByIsbn(isbn)).thenReturn(book);
        when(collectionRepository.findByType(collectionType)).thenReturn(Optional.of(collection));

        Book result = collectionService.addBookToCollection(isbn, collectionType);

        assertNotNull(result);
        assertEquals(isbn, result.getIsbn());
        assertTrue(collection.getBooks().contains(book));

        verify(collectionRepository).save(collection);
    }

    @Test
    public void testAddBookToCollection_BookExists_CollectionDoesNotExist() {
        String isbn = "9780975568606";
        CollectionType collectionType = CollectionType.FAVORIS;

        Book book = new Book();
        book.setIsbn(isbn);

        when(bookRepository.findByIsbn(isbn)).thenReturn(book);
        when(collectionRepository.findByType(collectionType)).thenReturn(Optional.empty());

        Book result = collectionService.addBookToCollection(isbn, collectionType);

        assertNotNull(result);
        assertEquals(isbn, result.getIsbn());

        ArgumentCaptor<BookCollection> collectionCaptor = ArgumentCaptor.forClass(BookCollection.class);
        verify(collectionRepository).save(collectionCaptor.capture());

        BookCollection savedCollection = collectionCaptor.getValue();
        assertEquals(collectionType, savedCollection.getType());
        assertTrue(savedCollection.getBooks().contains(book));
    }

    @Test
    public void testAddBookToCollection_BookDoesNotExist() {
        String isbn = "9780975568606";
        CollectionType collectionType = CollectionType.FAVORIS;

        when(bookRepository.findByIsbn(isbn)).thenReturn(null);

        Book result = collectionService.addBookToCollection(isbn, collectionType);

        assertNull(result);
        verify(collectionRepository, never()).save(any(BookCollection.class));
    }
}
